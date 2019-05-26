package ru.mycity.ui.service.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.mycity.ui.service.rest.dto.ComplaintDto;

import java.util.List;

public class CoreService {

    public List<ComplaintDto> getComplaints() throws Exception {
        try {
            HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
            rf.setConnectTimeout(30 * 1000);
            rf.setConnectionRequestTimeout(30 * 1000);
            rf.setReadTimeout(60 * 1000);
            RestTemplate restTemplate = new RestTemplate(rf);

            ResponseEntity<List<ComplaintDto>> responseEntity =
                    restTemplate.exchange( "http://localhost:9190/complaint/?category=Сантехника",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<ComplaintDto>>(){});
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    private String buildUrl(){
        return null;
    }
}
