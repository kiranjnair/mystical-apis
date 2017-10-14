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

import hack.dal.DrugRepository;
import hack.model.Drug;
import io.swagger.annotations.Api;

@Api(value = "Drug  REST API", description = "Drug REST API project")
@RestController
public class DrugController {

	private static Logger logger = LoggerFactory.getLogger(DrugController.class);
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

	{
		jacksonObjectMapper.setSerializationInclusion(Include.NON_EMPTY);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Autowired
	private DrugRepository drugRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/drugs/name/{drugname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get(@PathVariable(value = "drugname") String drugname) throws JsonProcessingException {
		List<Drug> drugList = drugRepository.findBydrugname(drugname);
		String json = jacksonObjectMapper.writeValueAsString(drugList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/d3/drugs/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody Drug drug) throws JsonProcessingException {

		 drug = drugRepository.save(drug);
		String json = jacksonObjectMapper.writeValueAsString(drug);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/d3/drugs/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listAll() throws JsonProcessingException {
		List<Drug> drugList = drugRepository.findAll();
		for (Drug drug : drugList) {
			System.out.println(drug);
		}
		String json = jacksonObjectMapper.writeValueAsString(drugList);
		logger.info(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
}

}
