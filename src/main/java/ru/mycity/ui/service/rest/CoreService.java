package ru.mycity.ui.service.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mycity.ui.service.rest.dto.*;
import ru.mycity.ui.utils.MarshallerHelper;
import ru.mycity.ui.view.MainViewParameters;

import java.util.List;

@Service
public class CoreService {

    public List<ComplaintDto> getComplaints(MainViewParameters parameters) throws Exception {
        try {
            HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
            rf.setConnectTimeout(30 * 1000);
            rf.setConnectionRequestTimeout(30 * 1000);
            rf.setReadTimeout(60 * 1000);
            RestTemplate restTemplate = new RestTemplate(rf);

            ResponseEntity<List<ComplaintDto>> responseEntity =
                    restTemplate.exchange( "http://localhost:9190/complaint/" + buildUrl(parameters),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<ComplaintDto>>(){});
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public InsertComplaintResultDto addComplaint(AddComplaintDto complaintDto) {
        try {

            HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
            rf.setConnectTimeout(30 * 1000);
            rf.setConnectionRequestTimeout(30 * 1000);
            rf.setReadTimeout(60 * 1000);
            RestTemplate restTemplate = new RestTemplate(rf);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            JsonNode node = new MarshallerHelper<AddComplaintDto>().convertToJson(complaintDto);
            HttpEntity<String> entity = new HttpEntity<String>(node.toString(), headers);

            return restTemplate.postForEntity("http://localhost:9190/complaint/", entity, InsertComplaintResultDto.class).getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public AuthUserResponseDto authUser(AuthUserRequestDto requestDto) {
        try {

            HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
            rf.setConnectTimeout(30 * 1000);
            rf.setConnectionRequestTimeout(30 * 1000);
            rf.setReadTimeout(60 * 1000);
            RestTemplate restTemplate = new RestTemplate(rf);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            JsonNode node = new MarshallerHelper<AuthUserRequestDto>().convertToJson(requestDto);
            HttpEntity<String> entity = new HttpEntity<String>(node.toString(), headers);

            return restTemplate.postForEntity("http://localhost:9190/auth/", entity, AuthUserResponseDto.class).getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    private String buildUrl(MainViewParameters parameters){
        StringBuilder sb = new StringBuilder("?");
        if (parameters.getCategory() != null && parameters.getStatus() != null){
            sb.append("category=" + parameters.getCategory());
            sb.append("&status=" + parameters.getStatus());
            return sb.toString();
        } else if(parameters.getCategory() != null){
            sb.append("category=" + parameters.getCategory());
            return sb.toString();
        } else if(parameters.getStatus() != null){
            sb.append("status=" + parameters.getStatus());
            return sb.toString();
        } else {
            return "";
        }
    }
}
