package hack;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import hack.dal.ScheduleRepository;
import hack.model.Schedule;
import io.swagger.annotations.Api;

@Api(value = "Schedule  REST API", description = "Scheduler REST API project")
@RestController
public class ScheduleController {

	private static Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

	{
		jacksonObjectMapper.setSerializationInclusion(Include.NON_EMPTY);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/schedule/drugname/{drugname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getByDrug(@PathVariable(value = "drugname") String drugname) throws JsonProcessingException {
		drugname =  StringUtils.capitalize(drugname);
		List<Schedule> ScheduleList = scheduleRepository.findBydrugname(drugname);
		String json = jacksonObjectMapper.writeValueAsString(ScheduleList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/schedule/ampm/{ampm}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getByAMPM(@PathVariable(value = "ampm") String ampm) throws JsonProcessingException {
		ampm =  StringUtils.capitalize(ampm);
			List<Schedule> ScheduleList = scheduleRepository.findByampm(ampm);
		String json = jacksonObjectMapper.writeValueAsString(ScheduleList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/schedule/time/{time}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getByTime(@PathVariable(value = "time") String time) throws JsonProcessingException {
		time =  StringUtils.capitalize(time);
			List<Schedule> ScheduleList = scheduleRepository.findBytime(time);
		String json = jacksonObjectMapper.writeValueAsString(ScheduleList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/schedule/useruid/{useruid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getByUser(@PathVariable(value = "time") String time) throws JsonProcessingException {
		time =  StringUtils.capitalize(time);
			List<Schedule> ScheduleList = scheduleRepository.findBytime(time);
		String json = jacksonObjectMapper.writeValueAsString(ScheduleList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/d3/schedule/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody Schedule schedule) throws JsonProcessingException {

		schedule = scheduleRepository.save(schedule);
		String json = jacksonObjectMapper.writeValueAsString(schedule);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/schedule/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listAll() throws JsonProcessingException {
		List<Schedule> ScheduleList = scheduleRepository.findAll();
		for (Schedule Schedule : ScheduleList) {
			System.out.println(Schedule);
		}
		String json = jacksonObjectMapper.writeValueAsString(ScheduleList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
}

}
