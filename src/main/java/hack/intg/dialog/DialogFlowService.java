package hack.intg.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import hack.dialog.model.ResponseModel;
import hack.exception.AppException;
import hack.intg.IntgReply;
import hack.intg.IntgRequest;
import hack.intg.IntgService;

@Component
public class DialogFlowService implements IntgService {
  private static Logger logger = LoggerFactory.getLogger(DialogFlowService.class);

  public IntgReply invoke(IntgRequest intgRequest) throws AppException {

    DialogFlowRequest request = (DialogFlowRequest) intgRequest;
   // logger.info("URL " + request.getURL());
    logger.info("Body " + request.getBody());
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", request.getToken());
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(10000);
    factory.setConnectTimeout(10000);
    restTemplate.setRequestFactory(factory);

    HttpEntity<?> httpEntity = new HttpEntity<Object>(request.getBody(), headers);

    ResponseModel responseModel =
        restTemplate.postForObject(request.getURL(), httpEntity, ResponseModel.class);
    logger.info("response Model from Dialog Flow[-{}-] {} ",request.getRequestModel().getConverseType(),
        responseModel);
    DialogFlowReply reply = new DialogFlowReply();
    reply.setResponseModel(responseModel);
    return reply;

  }



}
