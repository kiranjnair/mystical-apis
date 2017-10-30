package hack.api.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import hack.dal.ContextParamsRepository;
import hack.dialog.common.ConverseTypeEnum;
import hack.dialog.common.DialogFlowConstants;
import hack.exception.AppException;
import hack.intg.dialog.DialogFlowReply;
import hack.intg.dialog.DialogFlowRequest;
import hack.intg.dialog.DialogFlowService;
import hack.dialog.model.RequestModel;
import hack.dialog.model.ResponseModel;
import hack.model.converse.ContextParameters;
import hack.model.converse.Result;

@Component
public class DialogFlowManager {
 
  @Autowired
  DialogFlowService dfService; 

  @Autowired
  private ContextParamsRepository contextParamsRepository;

  private static Logger logger = LoggerFactory.getLogger(DialogFlowManager.class);

  public ResponseModel handleContext(RequestModel reqModel, ResponseModel resModel) {
    Result result = resModel.getResult();
    ContextParameters cParameters = result.getContexts().get(0).getParameters();

    // Save scheduled Drug
    if (result.getAction().equalsIgnoreCase("reminder.confirm")) {
      if (cParameters.getMyaction() != null
          && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
        try {
          cParameters = contextParamsRepository.save(cParameters);
          logger.debug("Saved Context params {} ", cParameters);
        } catch (Exception e) {
          logger.debug("Error saving schedule context param " + e.getMessage());
        }
      }
    }

    if (result.getAction().equalsIgnoreCase("previouscontext")) {
      if (cParameters.getMyaction() != null
          && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
        String altQuery = result.getFulfillment().getSpeech();
        if (altQuery != null) {
          RequestModel newRequestModel = new RequestModel();
          BeanUtils.copyProperties(reqModel, newRequestModel);
          // newRequestModel.setQuery(altQuery);
          logger.info("Rerouting request with alternate query {}", newRequestModel);
          resModel = routeInteractionAPI(newRequestModel, resModel,false);
        }
      }
    }

    if (result.getAction().equalsIgnoreCase("reminder.cancel")) {
      if (cParameters.getMyaction() != null
          && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
        result.set_healthTipsAvailable(true);
        result.set_interactionAvailable(true);
        result.set_interactionQuestion(
            "Hmm..It seems there is some interaction amoung your current medications. Would you like to know more about it?");
        result.set_healthTipQuestion(
            "Are you interested in getting some tips on your health conditions?");
        resModel.setResult(result);
      } 
    }
    return resModel;
  }


  public ResponseModel routeHealthTipsAPI(RequestModel reqModel, ResponseModel resModel,boolean hasContext) {
    DialogFlowRequest request = new DialogFlowRequest();
    request.setToken(DialogFlowConstants.HEALTHTIPS_TOKEN);
    if(!hasContext)
    reqModel.setQuery(DialogFlowConstants.HEALTHTIPS);
    request.setRequestModel(reqModel);
    DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
    ResponseModel responseModel = reply.getResponseModel();
    responseModel.getResult().getParameters().setConversetype(ConverseTypeEnum.healthtips.name());
    return responseModel;

  }

  public ResponseModel routeInteractionAPI(RequestModel reqModel, ResponseModel resModel,boolean hasContext) {
    DialogFlowRequest request = new DialogFlowRequest();
    request.setToken(DialogFlowConstants.INTERACTION_TOKEN);
    if(!hasContext)
    reqModel.setQuery(DialogFlowConstants.INTERACTION);
    request.setRequestModel(reqModel);
    DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
    ResponseModel responseModel = reply.getResponseModel();
    responseModel.getResult().getParameters().setConversetype(ConverseTypeEnum.interaction.name());
    return responseModel;

  }

  public ResponseModel routeScheduleAPI(RequestModel reqModel, ResponseModel resModel,boolean hasContext) {
    DialogFlowRequest request = new DialogFlowRequest();
    request.setToken(DialogFlowConstants.SCHEDULER_TOKEN);
    if(!hasContext)
       reqModel.setQuery(DialogFlowConstants.SCHEDULE_A_MEDICINE);
    request.setRequestModel(reqModel);
    DialogFlowReply reply = (DialogFlowReply) dfService.invoke(request);
    ResponseModel responseModel = reply.getResponseModel();
    responseModel.getResult().getParameters().setConversetype(ConverseTypeEnum.schedule.name());
    return responseModel;

  }


  public ResponseModel routeConverseAPI(RequestModel requestModel) {
    DialogFlowReply reply = null;
    DialogFlowRequest request = new DialogFlowRequest();
    request.setToken(DialogFlowConstants.CONVERSE_TOKEN);
    request.setRequestModel(requestModel);

    try {
      logger.info(" routeConverseAPI->DialogFlow Request Received: {}", requestModel);
      reply = (DialogFlowReply) dfService.invoke(request);
      logger.info(" routeConverseAPI->DialogFlow Response Received: {}", reply.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (reply == null) {
      throw new AppException("Conversation Reply null! ");
    }
    return reply.getResponseModel();

  }

}
