package hack.api.dialog;

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

import hack.dialog.model.RequestModel;
import hack.dialog.model.ResponseModel;
import hack.exception.AppException;
import hack.intg.dialog.DialogFlowService;
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

	@RequestMapping(method = RequestMethod.POST, value = "/v1/d3/sendtoapi", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseModel converse(@RequestBody RequestModel requestModel)
			throws AppException, JsonProcessingException {
		ResponseModel responseModel = null;
		String converseType = (StringUtils.isEmpty(requestModel.getConverseType())) ? "converse"
				: requestModel.getConverseType();
		requestModel.setConverseType(converseType);
		boolean hasContext = (converseType.equalsIgnoreCase("converse")) ? false : true;
		if(!hasContext) {
			//Reset all context if type is converse
			requestModel.setResetContexts("true");			
		}
		
		responseModel = dialogManager.apiRouting(requestModel, responseModel, hasContext);
		logger.info("Speech from API =>[{}] {}", responseModel.getResult().getParameters().getConversetype(),
				responseModel.getResult().getFulfillment().getSpeech());

		return responseModel;

	}

}
