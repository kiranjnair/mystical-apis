package hack;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import hack.dal.PersonRepository;
import hack.model.Person;
import io.swagger.annotations.Api;

@Api(value = "Demo REST API", description = "Sample/demo REST API project to show various use cases.")
@RestController
public class MongoRestController {

	private static Logger logger = LoggerFactory.getLogger(MongoRestController.class);
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

	{
		jacksonObjectMapper.setSerializationInclusion(Include.NON_EMPTY);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Autowired
	private PersonRepository personRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/v1/hello/person/lastname/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get(@PathVariable(value = "lastname") String lastname) throws JsonProcessingException {
		List<Person> personList = personRepository.findByLastName(lastname);
		String json = jacksonObjectMapper.writeValueAsString(personList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/hello/person/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody Person person) throws JsonProcessingException {
		//List<Person> persons = new ArrayList<Person>();
		//Person person ;	
		//persons.add(person);

		 person = personRepository.save(person);
		String json = jacksonObjectMapper.writeValueAsString(person);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/hello/person/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listAll() throws JsonProcessingException {
		List<Person> personList = personRepository.findAll();
		for (Person person : personList) {
			System.out.println(person);
		}
		String json = jacksonObjectMapper.writeValueAsString(personList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
}

}
