package hack.intg.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hack.exception.AppException;
import hack.intg.IntgReply;
import hack.intg.IntgRequest;
import hack.intg.IntgService;
import hack.model.converse.ResponseModel;
@Component
public class ConversationService implements IntgService{
	private static Logger logger = LoggerFactory.getLogger(ConversationService.class);

	public IntgReply invoke(IntgRequest request)  throws AppException {
		
		ConversationRequest crequest = (ConversationRequest)request;
		RestTemplate restTemplate = new RestTemplate();
		logger.info("URL "+crequest.getUrl());
		logger.info("Body "+crequest.getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 16096ae63c7a426abd25f1e4c23eb44f");
		headers.setContentType(MediaType.APPLICATION_JSON);
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(10000);
		restTemplate.setRequestFactory(factory);		

		HttpEntity<?> httpEntity = new HttpEntity<Object>(crequest.getBody(),headers);

		ResponseModel responseModel = restTemplate.postForObject(crequest.getUrl(), httpEntity, ResponseModel.class);
		logger.info("Raw response Model from Dialog Flow {} ",responseModel);
		ConversationReply reply = new ConversationReply();
		reply.setResponseModel(responseModel);
		return reply;
	
	}
	
 

}
