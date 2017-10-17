package hack.intg.interaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class InteractionService implements IntgService{
	private static Logger logger = LoggerFactory.getLogger(InteractionService.class);

	public IntgReply invoke(IntgRequest request) throws Exception {
		
		InteractionRequest irequest = (InteractionRequest)request;
		RestTemplate restTemplate = new RestTemplate();
		logger.info("URL "+irequest.getUrl());
		ResponseEntity<String> response
		  = restTemplate.getForEntity(irequest.getUrl(), String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		InteractionReply reply = new InteractionReply();
		reply.setRawJson(response.getBody());
		return reply;
	
	}
	
 

}
