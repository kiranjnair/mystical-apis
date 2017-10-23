package hack;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import hack.dal.ScheduleRepository;
import hack.intg.interaction.InteractionReply;
import hack.intg.interaction.InteractionRequest;
import hack.intg.interaction.InteractionService;
import hack.model.Schedule;
import io.swagger.annotations.Api;

@Api(value = "Drug  REST API", description = "Drug REST API project")
@RestController
public class ConversationController {
	
	@Autowired
	InteractionService iService;

	private static Logger logger = LoggerFactory.getLogger(ConversationController.class);
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

	{
		jacksonObjectMapper.setSerializationInclusion(Include.ALWAYS);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Autowired
	private ScheduleRepository scheduleRepository;

	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/interactions/{useruid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDrugIds(@PathVariable(value = "useruid") String useruid) throws JsonProcessingException {
		List<Schedule> scheduleList = scheduleRepository.findByuseruid(useruid);
		List<String> drugIds = new ArrayList<String>();
		for(Schedule schedule:scheduleList) {
			drugIds.add(schedule.getRxnormId());
			
			
		}
		String drugIdString = StringUtils.collectionToDelimitedString(drugIds, "+");
		logger.info("checking intertin "+drugIdString);
		InteractionRequest irequest = new InteractionRequest();
		irequest.setRxcuis(drugIdString);
		String json ;
		try {
			InteractionReply reply = (InteractionReply)iService.invoke(irequest);
			json = reply.getRawJson();
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.PARTIAL_CONTENT);
		}
	
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	

}
