package hack.api.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import hack.dal.ContextParamsRepository;
import hack.dal.DrugRepository;
import hack.dialog.common.ConverseTypeEnum;
import hack.dialog.common.DialogFlowConstants;
import hack.exception.AppException;
import hack.intg.dialog.DialogFlowReply;
import hack.intg.dialog.DialogFlowRequest;
import hack.intg.dialog.DialogFlowService;
import hack.dialog.model.RequestModel;
import hack.dialog.model.ResponseModel;
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

	private static Logger logger = LoggerFactory.getLogger(DialogFlowManager.class);

	public ResponseModel handleResponse(RequestModel reqModel, ResponseModel resModel) {
		logger.debug("Inside handleResponse {}{}",reqModel,resModel);
		// Handle responses once converse type is set and context is available
		if (resModel != null && resModel.getResult() != null && resModel.getResult().getContexts() != null
				&& resModel.getResult().getContexts().size() > 0) {
			resModel = this.handleContext(reqModel, resModel);

		}

		// Get converse type from Converse Intent
		if (resModel != null && resModel.getResult() != null && resModel.getResult().getParameters() != null
				&& resModel.getResult().getAction().equals("Converse.Get")) {
			String conversetype = resModel.getResult().getParameters().getConversetype();
			reqModel.setConverseType(conversetype);
			resModel = routeScheduleAPI(reqModel, resModel, false);
		}
		return resModel;
	}

	public ResponseModel handleContext(RequestModel reqModel, ResponseModel resModel) {
		logger.debug("Inside handleContext {}{}",reqModel,resModel);
		Result result = resModel.getResult();
		ContextParameters cParameters = result.getContexts().get(0).getParameters();

		// Save scheduled Drug
		if (result.getAction().equalsIgnoreCase("reminder.confirm")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				try {
					cParameters.setUserid("bob");
					cParameters = contextParamsRepository.save(cParameters);
					logger.debug("Saved Context params {} ", cParameters);
				} catch (Exception e) {
					logger.debug("Error saving schedule context param " + e.getMessage());
				}
			}
		}

		if (result.getAction().equalsIgnoreCase("previouscontext")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				String altQuery = result.getFulfillment().getSpeech();
				if (altQuery != null) {
					RequestModel newRequestModel = new RequestModel();
					BeanUtils.copyProperties(reqModel, newRequestModel);
					// newRequestModel.setQuery(altQuery);
					logger.info("Rerouting request with alternate query {}", newRequestModel);
					resModel = routeInteractionAPI(newRequestModel, resModel, false);
				}
			}
		}

		if (result.getAction().equalsIgnoreCase("reminder.cancel")) {
			if (result.getParameters().getConversetype().equalsIgnoreCase("schedule")) {
				resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.healthtips.name());
				resModel.getResult().getFulfillment().setSpeech(resModel.getResult().getFulfillment().getSpeech()
						+ "By the way, I can help you with some tips to improve your health.Would you be interested?");
			}
		}

		if (result.getAction().equalsIgnoreCase("Interaction.Get")) {
			if (result.getParameters() != null && result.getParameters().getChoicetype() != null
					&& result.getParameters().getChoicetype().equalsIgnoreCase("Yes")) {
				logger.debug("INTERACTTION");
				resModel.getResult().getFulfillment().setSpeech(
						"You have interactions that may adversly affect the effectiveness.Please consult your doctor");
			}

		}
		return resModel;
	}

	public ResponseModel routeHealthTipsAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		logger.debug("Inside routeHealthTipsAPI {}{}",reqModel,resModel);
		try {
			DialogFlowRequest request = new DialogFlowRequest();
			List<String> userMeds = new ArrayList<String>();
			List<String> healthTips = new ArrayList<String>();
			List<Drug> drugList = new ArrayList<Drug>();

			request.setToken(DialogFlowConstants.HEALTHTIPS_TOKEN);
			if (!hasContext)
				reqModel.setQuery(DialogFlowConstants.HEALTHTIPS);
			request.setRequestModel(reqModel);
			DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
			resModel = reply.getResponseModel();

			if (resModel.getResult() != null && resModel.getResult().getParameters() != null) {
				if (resModel.getResult().getParameters().getChoicetype().equals("yes")) {
					List<ContextParameters> cParams = contextParamsRepository.findByUserid("bob");
					if (cParams != null && cParams.size() > 0) {
						for (ContextParameters param : cParams) {
							userMeds.add(param.getMedicine());
							logger.info("From saved medlist for bob: {}", param.getMedicine());
						}
						for (String userMed : userMeds) {
							userMed = StringUtils.capitalize(userMed);
							drugList.addAll(drugRepository.findBydrugname(userMed));

						}
						Iterator<Drug> iterator = drugList.iterator();
						Drug drug;
						while (iterator.hasNext()) {
							drug = iterator.next();
							healthTips.add(drug.getHealthtip1());
							healthTips.add(drug.getHealthtip2());

						}

					}
					resModel.getResult().set_healthTipsAvailable(true);
					String healthTipsString = StringUtils.collectionToDelimitedString(healthTips, ".");
					resModel.getResult().getFulfillment().setSpeech(healthTipsString);
					resModel.getResult().getParameters().setConversetype(ConverseTypeEnum.healthtips.name());

				}
			}
			resModel = handleResponse(reqModel, resModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Conversation Reply null! " + e.getMessage());
		}

		return resModel;

	}

	public ResponseModel routeInteractionAPI(RequestModel reqModel, ResponseModel resModel, boolean hasContext) {
		logger.debug("Inside routeInteractionAPI {}{}",reqModel,resModel);
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
		logger.debug("Inside routeScheduleAPI {}{}",reqModel,resModel);
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
