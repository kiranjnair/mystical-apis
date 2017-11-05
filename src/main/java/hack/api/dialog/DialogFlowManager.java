package hack.api.dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import hack.dal.ContextParamsRepository;
import hack.dal.DrugRepository;
import hack.dal.UserHealthTipsRepository;
import hack.dal.UserInteractionRepository;
import hack.dialog.common.ConverseTypeEnum;
import hack.dialog.common.DialogFlowConstants;
import hack.dialog.common.DialogFlowUtil;
import hack.dialog.model.RequestModel;
import hack.dialog.model.ResponseModel;
import hack.dialog.model.UserHealthTips;
import hack.dialog.model.UserInteraction;
import hack.exception.AppException;
import hack.interaction.model.FullInteractionType;
import hack.interaction.model.FullInteractionTypeGroup;
import hack.interaction.model.InteractionConcept;
import hack.interaction.model.InteractionData;
import hack.interaction.model.InteractionPair;
import hack.intg.dialog.DialogFlowReply;
import hack.intg.dialog.DialogFlowRequest;
import hack.intg.dialog.DialogFlowService;
import hack.intg.interaction.InteractionReply;
import hack.intg.interaction.InteractionRequest;
import hack.intg.interaction.InteractionService;
import hack.model.Drug;
import hack.model.converse.ContextParameters;
import hack.model.converse.Result;

@Component
public class DialogFlowManager {

	@Autowired
	DialogFlowService dfService;

	@Autowired
	private ContextParamsRepository contextParamsRepository;

	@Autowired
	private DrugRepository drugRepository;
	@Autowired
	private UserInteractionRepository userInteractionRepository;
	@Autowired
	private UserHealthTipsRepository userHealthTipsRepository;

	@Autowired
	InteractionService iService;
	private String userid = "bob";

	private static Logger logger = LoggerFactory.getLogger(DialogFlowManager.class);

	public ResponseModel apiRouting(RequestModel reqModel, ResponseModel resModel, boolean hasContext)
			throws AppException, JsonProcessingException {

		String conversetype = reqModel.getConverseType();
		logger.info("Routing API call to {}", conversetype);
		if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.converse) {
			resModel = routeConverseAPI(reqModel, resModel, hasContext);
		}

		if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.schedule) {
			resModel = routeScheduleAPI(reqModel, resModel, hasContext);
		}
		if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.interaction) {
			resModel = routeInteractionAPI(reqModel, resModel, hasContext);
		}
		if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.healthtips) {
			resModel = routeHealthTipsAPI(reqModel, resModel, hasContext);
		}

		return resModel;

	}

	public ResponseModel handleResponse(RequestModel reqModel, ResponseModel resModel)
			throws AppException, JsonProcessingException {
		logger.debug("Inside handleResponse {}{}", reqModel, resModel);

		if (resModel != null && resModel.getResult() != null) {
			Result result = resModel.getResult();

			// Get converse type from Converse Intent
			if (resModel.getResult().getParameters() != null
					&& resModel.getResult().getAction().equals("Converse.Get")) {
				String converseType = resModel.getResult().getParameters().getConversetype();
				reqModel.setConverseType(converseType);
				resModel = apiRouting(reqModel, resModel, false);
			}

			else if (result.getAction().equalsIgnoreCase("Interaction.Get")) {
				if (result.getParameters() != null || StringUtils.isEmpty(result.getParameters().getChoicetype())) {
					if (result.getParameters().getChoicetype().equalsIgnoreCase("Yes")) {
						logger.debug("Inside Interaction Choice Yes");
						List<UserInteraction> userInteractions = userInteractionRepository.findByUserid(userid);
						if(userInteractions!=null && userInteractions.size()>0) {
							String interactionSpeech = getInteractionSpeech(userInteractions.get(userInteractions.size()-1));
							logger.info("Interactin data {}", interactionSpeech);
							resModel.getResult().getFulfillment().setSpeech(interactionSpeech);						
						}
	
					} else if (result.getParameters().getChoicetype().equalsIgnoreCase("No")) {
						logger.debug("Inside Interaction Choice No");
						resModel.getResult().getFulfillment().setSpeech("Alright.Enjoy rest of your day!Good Bye");

					}

				}
			}

			else if (result.getAction().equalsIgnoreCase("Healthtips.Get")) {
				String tipsSpeech = "";
				if (resModel.getResult().getParameters().getChoicetype().equals("yes")) {
					List<UserHealthTips> userHealthTipList = userHealthTipsRepository.findByUserid(userid);
					if (userHealthTipList != null && userHealthTipList.size() > 0) {
						List<String> userHealthTips = userHealthTipList.get(userHealthTipList.size()-1).getHealthTips();
						DialogFlowUtil.removeDuplicates(userHealthTips, false);
						tipsSpeech = String.join("", userHealthTips);

					} else {
						tipsSpeech = "No tips available.Please check at a later time";

					}
				}
				List<UserInteraction> userInteractions = userInteractionRepository.findByUserid(userid);
				if (userInteractions != null && userInteractions.size() > 0) {
					tipsSpeech += "Hold on! It appears there are some interactions among your medications.Please consult your doctor on this.Would you like to hear more about it?";
					resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.interaction.name());
				}

				resModel.getResult().getFulfillment().setSpeech(tipsSpeech);

			}

			// Handle responses once converse type is set and context is available
			if (resModel.getResult().getContexts().size() > 0) {
				resModel = this.handleContext(reqModel, resModel);

			}
		}

		return resModel;
	}

	public ResponseModel handleContext(RequestModel reqModel, ResponseModel resModel) {
		logger.debug("Inside handleContext {}{}", reqModel, resModel);
		Result result = resModel.getResult();
		ContextParameters cParameters = result.getContexts().get(0).getParameters();

		if (result.getAction().equalsIgnoreCase("reminder.cancel")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.healthtips.name());
				resModel.getResult().getFulfillment().setSpeech(resModel.getResult().getFulfillment().getSpeech()
						+ "By the way, I can help you with some tips to improve your health.Would you be interested?");
			}
		}

		// Save scheduled Drug
		if (result.getAction().equalsIgnoreCase("reminder.confirm")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				try {
					cParameters.setUserid(userid);
					cParameters = contextParamsRepository.save(cParameters);
					saveTipsAndInteractions(userid);
					logger.debug("Saved Context params {} ", cParameters);
				} catch (Exception e) {
					logger.debug("Error saving user data {}", e);
				}
			}
		}

		if (result.getAction().equalsIgnoreCase("previouscontext")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				String altQuery = result.getFulfillment().getSpeech();
				if (altQuery != null) {
					reqModel.setQuery(altQuery);
					logger.info("Rerouting request with alternate query {}", reqModel);
					resModel = routeHealthTipsAPI(reqModel, resModel, false);
				}
			}
		}

		return resModel;
	}

	private String getInteractionSpeech(UserInteraction userInteraction) {
		StringBuilder builder = new StringBuilder();
		if (userInteraction != null) {
			if (userInteraction.getInteractionData() != null) {
				List<FullInteractionTypeGroup> fitGroups = userInteraction.getInteractionData()
						.getFullInteractionTypeGroup();
				if (fitGroups != null && fitGroups.size() > 0) {
					for (FullInteractionTypeGroup fitGroup : fitGroups) {
						if (fitGroup.getSourceName().equalsIgnoreCase("DrugBank")) {
							List<FullInteractionType> fitList = fitGroup.getFullInteractionType();
							if (fitList != null && fitList.size() > 0) {
								builder.append("I found ").append(fitList.size());
								if (fitList.size() > 1) {
									builder.append(" interactions.");

								} else {
									builder.append(" interaction.");
								}

								builder.append(
										"This is for informational purpose only.You should consult with your doctor for any decisions regarding your medications.");

								for (FullInteractionType fit : fitList) {
									List<InteractionPair> iPairs = fit.getInteractionPair();
									for (InteractionPair iPair : iPairs) {
										List<InteractionConcept> iConcepts = iPair.getInteractionConcept();
										// String drug1,drug2;
										if (iConcepts != null && iConcepts.size() == 2) {// Expecting in pair
											String origDrug1 = fit.getMinConcept().get(0).getName();
											String drug1 = iConcepts.get(0).getMinConceptItem().getName();											
											String origDrug2 = fit.getMinConcept().get(1).getName();
											String drug2 = iConcepts.get(1).getMinConceptItem().getName();
											if(!origDrug1.equalsIgnoreCase(drug1)) {
												drug1 = origDrug1+" also known as "+drug1;
												
											}
											if(!origDrug2.equalsIgnoreCase(drug2)) {
												drug2 = origDrug2+" also known as "+drug2;
												
											}
	
											String severity = (iPair.getSeverity().equalsIgnoreCase("N/A"))
													? "not available"
													: iPair.getSeverity();
											String description = iPair.getDescription();
											builder.append("Found an interaction between ").append(drug1)
													.append(" and ").append(drug2);
											builder.append(". Severity is ").append(severity);
											builder.append(". Here are the details. ").append(description);
										}

									}

								}
								builder.append(
										"Your doctor can confirm if this is something you need to be concerned about.");
								builder.append(
										"Alright! You can come back and schedule medicines or learn about healthtips any time.You have a wonderful day ahead!Thank you!");

							}

						}

					}

				}

			}

		}
		return builder.toString();
	}

	private void saveTipsAndInteractions(String userid2) {
		List<String> userMeds = new ArrayList<String>();
		List<String> healthTips = new ArrayList<String>();
		List<Drug> drugList = new ArrayList<Drug>();
		List<String> rxNormIds = new ArrayList<String>();
		UserInteraction userInteraction = null;
		String foodInteraction,sideEffects,healthTip1,healthTip2;
		List<ContextParameters> cParams = contextParamsRepository.findByUserid(userid);
		if (cParams != null && cParams.size() > 0) {
			for (ContextParameters param : cParams) {
				userMeds.add(param.getMedicine());
				logger.info("From saved medlist for bob: {}", param.getMedicine());
			}
			DialogFlowUtil.removeDuplicates(userMeds, true);
			for (String userMed : userMeds) {
				logger.info("Getting med from drugsdb {}", userMed);
				drugList.addAll(drugRepository.findBydrugname(userMed));

			}
			Iterator<Drug> iterator = drugList.iterator();
			Drug drug;
			while (iterator.hasNext()) {
				drug = iterator.next();
				foodInteraction = drug.getFoodinteraction();
				if(!StringUtils.isEmpty(foodInteraction)) {
					foodInteraction = DialogFlowUtil.formatPeriod(foodInteraction);
					healthTips.add("Do not take "+drug.getDrugname()+" along with "+foodInteraction);
					
				}
				sideEffects = drug.getSideeffects();
				if(!StringUtils.isEmpty(sideEffects)) {
					sideEffects = DialogFlowUtil.formatPeriod(sideEffects);
					healthTips.add("Some people may experience following side effects. "+sideEffects);
					
				}
				healthTip1 = drug.getHealthtip1();
				healthTip1 = DialogFlowUtil.formatPeriod(healthTip1);
				healthTip2 = drug.getHealthtip2();
				healthTip2 = DialogFlowUtil.formatPeriod(healthTip2);
				
				healthTips.add(healthTip1);
				healthTips.add(healthTip2);
				rxNormIds.add(drug.getRxnormId());// getting Id for interaction
			}
			UserHealthTips userHealthTips = new UserHealthTips(userid, healthTips,new Date());
			userHealthTipsRepository.save(userHealthTips);
			logger.info("User healthtips saved {}", userHealthTips);
			String drugIdString = StringUtils.collectionToDelimitedString(rxNormIds, "+");
			InteractionRequest irequest = new InteractionRequest();
			irequest.setRxcuis(drugIdString);
			try {
				InteractionReply reply = (InteractionReply) iService.invoke(irequest);
				if (reply != null) {
					InteractionData iData = reply.getInteractionData();
					if (iData != null && iData.getFullInteractionTypeGroup() != null
							&& iData.getFullInteractionTypeGroup().size() > 0) {
						logger.info("Interaction service has data");
						List<UserInteraction> userInteractions = userInteractionRepository.findByUserid(userid);
																												
																												
																												
						if (userInteractions == null || userInteractions.size() == 0) {
							userInteraction = new UserInteraction();
						}
						userInteraction.setUserid(userid);
						userInteraction.setCreatedTime(new Date());
						userInteraction.setInteractionData(iData);
						userInteractionRepository.save(userInteraction);

						logger.info("Saved Interaction {}", userInteraction);
					}
				}

			} catch (Exception e) {
				e.getMessage();
			}
		}
	}



	public ResponseModel routeHealthTipsAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		logger.debug("Inside routeHealthTipsAPI {}{}", reqModel, resModel);
		try {
			DialogFlowRequest request = new DialogFlowRequest();
			request.setToken(DialogFlowConstants.HEALTHTIPS_TOKEN);
			if (!hasContext)
				reqModel.setQuery(DialogFlowConstants.HEALTHTIPS);
			request.setRequestModel(reqModel);
			DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
			resModel = reply.getResponseModel();
			resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.healthtips.name());
			resModel = handleResponse(reqModel, resModel);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Conversation Reply null! " + e.getMessage());
		}

		return resModel;

	}

	public ResponseModel routeInteractionAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		logger.debug("Inside routeInteractionAPI {}{}", reqModel, resModel);
		try {
			DialogFlowRequest request = new DialogFlowRequest();
			request.setToken(DialogFlowConstants.INTERACTION_TOKEN);
			if (!hasContext)
				reqModel.setQuery(DialogFlowConstants.INTERACTION);
			request.setRequestModel(reqModel);
			DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
			resModel = reply.getResponseModel();
			resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.interaction.name());
			resModel = handleResponse(reqModel, resModel);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Conversation Reply null! " + e.getMessage());
		}
		return resModel;

	}

	public ResponseModel routeScheduleAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		logger.debug("Inside routeScheduleAPI {}{}", reqModel, resModel);
		try {
			DialogFlowRequest request = new DialogFlowRequest();
			request.setToken(DialogFlowConstants.SCHEDULER_TOKEN);
			if (!hasContext)
				reqModel.setQuery(DialogFlowConstants.SCHEDULE_A_MEDICINE);
			request.setRequestModel(reqModel);
			DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
			resModel = reply.getResponseModel();
			resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.schedule.name());
			resModel = handleResponse(reqModel, resModel);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Conversation Reply null! " + e.getMessage());
		}
		return resModel;

	}

	public ResponseModel routeConverseAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		DialogFlowReply reply = null;
		DialogFlowRequest request = new DialogFlowRequest();
		request.setToken(DialogFlowConstants.CONVERSE_TOKEN);
		request.setRequestModel(reqModel);

		try {
			logger.info(" routeConverseAPI->DialogFlow Request Received: {}", reqModel);
			reply = (DialogFlowReply) dfService.invoke(request);
			logger.info(" routeConverseAPI->DialogFlow Response Received: {}", reply.toString());
			resModel = reply.getResponseModel();
			resModel = handleResponse(reqModel, resModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Conversation Reply null! " + e.getMessage());
		}

		return resModel;

	}

}
