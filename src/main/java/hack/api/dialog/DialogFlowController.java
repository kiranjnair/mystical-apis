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

import hack.dialog.common.ConverseTypeEnum;
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
		if (ConverseTypeEnum.valueOf(converseType) == ConverseTypeEnum.converse) {
			responseModel = dialogManager.routeConverseAPI(requestModel, null, false);
		}
		responseModel = apiRouting(requestModel, responseModel);

		return responseModel;

	}

	private ResponseModel apiRouting(RequestModel reqModel, ResponseModel resModel)
			throws AppException, JsonProcessingException {

		String conversetype = null;
		boolean hasContext = true;

		// Set to default converseType if not available
		conversetype = (StringUtils.isEmpty(reqModel.getConverseType())) ? "converse" : reqModel.getConverseType();
		logger.info("Routing API call to {}",conversetype);

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


}
