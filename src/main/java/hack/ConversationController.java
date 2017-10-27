package hack;

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
import hack.model.converse.RequestModel;
import hack.model.converse.ResponseModel;
import hack.model.converse.Result;
import io.swagger.annotations.Api;

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

	@RequestMapping(method = RequestMethod.POST, value = "/v1/d3/sendtoapi", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseModel converse(@RequestBody RequestModel reqModel) throws AppException,JsonProcessingException {
		ConversationRequest request = new ConversationRequest();
		request.setrModel(reqModel);
		ConversationReply reply = null;
		try {
			reply = (ConversationReply) cService.invoke(request);
			logger.debug(" Conversation Response {}", reply.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseModel resModel = reply.getResponseModel();
		if (resModel != null) {
			if (resModel.getResult() != null && resModel.getResult().getParameters() != null) {
				resModel = apiRouting(reqModel,resModel);
			}
		}
		return resModel;

	}

	private ResponseModel apiRouting(RequestModel reqModel,ResponseModel resModel) throws AppException, JsonProcessingException {
		Result result = resModel.getResult();
		Context[] contexts = result.getContexts();
		ContextParameters cParameters = contexts[0].getParameters();
		
		//Save scheduled Drug
		if (result.getAction().equalsIgnoreCase("reminder.confirm")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				cParameters = contextParamsRepository.save(cParameters);
				logger.debug("Saved Context params {} ", cParameters);

			}

		}
		if (result.getAction().equalsIgnoreCase("previouscontext")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				String altQuery = result.getFulfillment().getSpeech();
				if(altQuery!=null){
					RequestModel newRequestModel= new RequestModel();
					BeanUtils.copyProperties(reqModel, newRequestModel);
					newRequestModel.setQ(altQuery);
					logger.info("Set new query to request",newRequestModel);
					resModel = converse(newRequestModel);
				}
			}
		}

		if (result.getAction().equalsIgnoreCase("reminder.cancel")) {
			if (cParameters.getMyaction() != null && cParameters.getMyaction().equalsIgnoreCase("reminder.set")) {
				result.set_healthTipsAvailable(true);
				result.set_interactionAvailable(true);
				result.set_interactionQuestion("Hmm..It seems there is some interaction amoung your current medications. Would you like to know more about it?");
				result.set_healthTipQuestion("Are you interested in getting some tips on your health conditions?");
				
			}
		}
		
		return resModel;

	}

}

