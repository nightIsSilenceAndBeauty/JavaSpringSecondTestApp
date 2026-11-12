package ru.trints.MySecondTestAppSpringBoot.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.trints.MySecondTestAppSpringBoot.model.Request;

@Service
public class ModifySourceRequestService implements ModifyRequestService {
    @Override
    public void modify(Request request, long requestReceivedAtMillis) {
        request.setSource("Service 1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Request-Received-At", Long.toString(requestReceivedAtMillis));
        HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);

        new RestTemplate().exchange("http://localhost:8084/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
