package hack;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hack.dal.ContextParamsRepository;
import hack.exception.AppException;
import hack.intg.conversation.ConversationReply;
import hack.intg.conversation.ConversationRequest;
import hack.intg.conversation.ConversationService;
import hack.model.converse.Context;
import hack.model.converse.ContextParameters;
import hack.model.converse.Fullfillment;
import hack.model.converse.RequestModel;
import hack.model.converse.ResponseModel;
import hack.model.converse.Result;
import io.swagger.annotations.Api;

/**
 * @author kiranjnair
 *
 */
@Api(value = "Dialog flow Controller API", description = "Dialog API project")
@RestController
public class ConversationController {

  @Autowired
  ConversationService cService;

  @Autowired
  private ContextParamsRepository contextParamsRepository;

  private static Logger logger = LoggerFactory.getLogger(ConversationController.class);
  private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

  {
    jacksonObjectMapper.setSerializationInclusion(Include.ALWAYS);
    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/v1/d3/sendtoapi",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseModel converse(@RequestBody RequestModel reqModel)
      throws AppException, JsonProcessingException {
    ConversationRequest request = new ConversationRequest();
    request.setrModel(reqModel);
    
    ConversationReply reply = null;
    try {
      logger.info(" Conversation Request Received: {}", reqModel);
      reply = (ConversationReply) cService.invoke(request);
      logger.info(" Conversation Response from DialogFlow {}", reply.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (reply == null) {
      throw new AppException("Conversation Reply null! ");
    }
    ResponseModel resModel = reply.getResponseModel();
    // resModel = createDummyYesResModel();
    // resModel = createDummyNoResModel();
    if (resModel != null) {
      if (resModel.getResult() != null) {
        resModel = apiRouting(reqModel, resModel);
      }
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
    fulfillment.setSpeech("Schedule a Meddicne");
    result.setFulfillment(fulfillment);
    model.setResult(result);
    logger.debug("Dummy Model Yes {}", model);


    return model;
  }

  private ResponseModel apiRouting(RequestModel reqModel, ResponseModel resModel)
      throws AppException, JsonProcessingException {
    Result result = resModel.getResult();
    List<Context> contexts = result.getContexts();
    if (contexts == null || contexts.isEmpty()) {
      logger.debug("contexts null {}", contexts);
      return resModel;

    }

    ContextParameters cParameters = contexts.get(0).getParameters();
    if (cParameters == null) {
      logger.debug("cParameters null {}", cParameters);
      return resModel;
    }

    // Save scheduled Drug
    if (result.getAction().equalsIgnoreCase("reminder.confirm")) {
      if (cParameters.getMyaction() != null
          && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
        try{
          cParameters = contextParamsRepository.save(cParameters);
          logger.debug("Saved Context params {} ", cParameters);
        }catch(Exception e){
          logger.debug("Error saving context param "+e.getMessage());
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
          newRequestModel.setQuery(altQuery);
          logger.info("Rerouting request with alternate query {}", newRequestModel);
          resModel = converse(newRequestModel);
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

      }
    }

    return resModel;

  }

}

