package hack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import hack.dal.ScheduleRepository;
import hack.intg.conversation.ConversationReply;
import hack.intg.conversation.ConversationRequest;
import hack.intg.conversation.ConversationService;
import hack.model.Schedule;
import hack.model.converse.Parameters;
import hack.model.converse.RequestModel;
import hack.model.converse.ResponseModel;
import io.swagger.annotations.Api;

@Api(value = "Drug  REST API", description = "Drug REST API project")
@RestController
public class ConversationController {
	
	@Autowired
	ConversationService cService;
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	private static Logger logger = LoggerFactory.getLogger(ConversationController.class);
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

	{
		jacksonObjectMapper.setSerializationInclusion(Include.ALWAYS);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/d3/sendtoapi", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseModel converse(@RequestBody RequestModel rModel) throws JsonProcessingException {
		ConversationRequest request = new ConversationRequest();
		request.setrModel(rModel);
		ConversationReply reply = null;
		try {
			reply = (ConversationReply)cService.invoke(request);
			logger.debug(" Conversation Response {}",reply.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseModel resModel = reply.getResponseModel();
		if(resModel!=null){
			if (resModel.getResult()!=null && resModel.getResult().getParameters()!=null){
				//saveDrugSchedule(resModel);
			}
		}
		return resModel;
		
	}



	private void saveDrugSchedule(ResponseModel resModel) {
		Parameters parameters = resModel.getResult().getParameters();
		Schedule schedule =  new Schedule();
		schedule.setUseruid("bob");
		schedule.setDrugname(parameters.getMedicine());
		schedule.setRxnormId("NA");
		schedule.setFreqency(parameters.getRecurrence());
		schedule.setTime(parameters.getTime());
		schedule.setDate(parameters.getDate());
		
		schedule = scheduleRepository.save(schedule);
		
		
	}
	

}
