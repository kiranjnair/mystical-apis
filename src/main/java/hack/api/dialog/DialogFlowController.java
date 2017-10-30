package hack.api.dialog;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hack.dialog.common.ConverseTypeEnum;
import hack.intg.dialog.DialogFlowService;
import hack.dialog.model.RequestModel;
import hack.dialog.model.ResponseModel;
import hack.exception.AppException;
import hack.model.converse.Context;
import hack.model.converse.ContextParameters;
import hack.model.converse.Fullfillment;
import hack.model.converse.Result;
import io.swagger.annotations.Api;


@Api(value = "Dialog Flow Controller", description = "Dialog Flow API for conversation")
@RestController
public class DialogFlowController {
  
  @Autowired
  DialogFlowService dfService;
  @Autowired
  DialogFlowManager dialogManager;
  
  private static Logger logger = LoggerFactory.getLogger(DialogFlowController.class);
  private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

  {
    jacksonObjectMapper.setSerializationInclusion(Include.ALWAYS);
    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/v1/d3/sendtoapi",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseModel converse(@RequestBody RequestModel requestModel)
      throws AppException, JsonProcessingException {
    ResponseModel responseModel = null;
    String converseType = (StringUtils.isEmpty(requestModel.getConverseType())) ? "converse"
        : requestModel.getConverseType();
    if (ConverseTypeEnum.valueOf(converseType) == ConverseTypeEnum.converse) {
      responseModel = dialogManager.routeConverseAPI(requestModel);
    }
    responseModel = apiRouting(requestModel, responseModel);

    return responseModel;

  }


  private ResponseModel apiRouting(RequestModel reqModel, ResponseModel resModel)
      throws AppException, JsonProcessingException {

    String conversetype = null;
    Result result = null;
    boolean hasContext = false;
    
    // Handle Context responses
    if (resModel != null && resModel.getResult() != null
        && resModel.getResult().getContexts() != null
        && resModel.getResult().getContexts().size() > 0) {
      resModel = dialogManager.handleContext(reqModel, resModel);
      return resModel;
    } 
    //Get converse type from Converse Intent
    if (resModel != null && resModel.getResult() != null
        && resModel.getResult().getParameters() != null
        && resModel.getResult().getAction().equals("Converse.Get")) {
      result = resModel.getResult();
      conversetype = result.getParameters().getConversetype();
      hasContext = false;
    }
  //Get converse type from Specific context sent back from UI
    else {
      conversetype = reqModel.getConverseType();
      hasContext = true;
    }
    //Set to default converseType if not available
    conversetype = (StringUtils.isEmpty(conversetype)) ? "converse" : conversetype;

    if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.schedule) {
      resModel = dialogManager.routeScheduleAPI(reqModel, resModel, hasContext);
    }
    if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.interaction) {
      resModel = dialogManager.routeInteractionAPI(reqModel, resModel, hasContext);
    }
    if (ConverseTypeEnum.valueOf(conversetype) == ConverseTypeEnum.healthtips) {
      resModel = dialogManager.routeHealthTipsAPI(reqModel, resModel, hasContext);
    }


    return resModel;

  }
  
  

 
   
  // Create JUNITS
  private ResponseModel createDummyYesResModel() {
    ResponseModel model = new ResponseModel();
    Result result = new Result();
    result.setAction("previouscontext");
    Context context = new Context();
    ContextParameters cParams = new ContextParameters();
    cParams.setMyaction("reminder.set");
    context.setParameters(cParams);
    List<Context> contexts = new ArrayList<Context>();
    contexts.add(context);
    result.setContexts(contexts);
    Fullfillment fulfillment = new Fullfillment();
    fulfillment.setSpeech("Schedule a Meddicne");
    result.setFulfillment(fulfillment);
    model.setResult(result);
    logger.debug("Dummy Model Yes {}", model);


    return model;
  }

  private ResponseModel createDummyNoResModel() {
    ResponseModel model = new ResponseModel();
    Result result = new Result();
    result.setAction("reminder.cancel");
    Context context = new Context();
    ContextParameters cParams = new ContextParameters();
    cParams.setMyaction("reminder.set");
    context.setParameters(cParams);
    List<Context> contexts = new ArrayList<Context>();
    contexts.add(context);
    result.setContexts(contexts);
    Fullfillment fulfillment = new Fullfillment();
    fulfillment.setSpeech("Schedule a Medicne");
    result.setFulfillment(fulfillment);
    model.setResult(result);
    logger.debug("Dummy Model Yes {}", model);


    return model;
  }

}

