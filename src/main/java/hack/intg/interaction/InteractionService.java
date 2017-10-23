package hack.intg.interaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hack.intg.IntgReply;
import hack.intg.IntgRequest;
import hack.intg.IntgService;
@Component
public class InteractionService implements IntgService{
	private static Logger logger = LoggerFactory.getLogger(InteractionService.class);

	public IntgReply invoke(IntgRequest request) throws Exception {
		
		InteractionRequest irequest = (InteractionRequest)request;
		RestTemplate restTemplate = new RestTemplate();
		logger.info("URL "+irequest.getUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response
		  = restTemplate.getForEntity(irequest.getUrl(), String.class);
		logger.info("Response {} "+response.getBody());
		InteractionReply reply = new InteractionReply();
		reply.setRawJson(response.getBody());
		return reply;
	
	}
	
 

}
