package javaschool.service.impl;

import java.util.HashMap;
import java.util.Map;
import javaschool.controller.dtoentity.ReCaptchaResponse;
import javaschool.service.api.ReCaptchaApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaApiClientImpl implements ReCaptchaApiClient {
    private static final String SECRET_PROP = "secret";
    private static final String RESPONSE_PROP = "response";
    @Value("${google.recaptcha.secretKey}")
    private String SECRET;
    @Value("${google.recaptcha.url}")
    private String VERIFY_URL;
    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public ReCaptchaResponse verify(String reCaptchaCode) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(SECRET_PROP, SECRET);
        form.add(RESPONSE_PROP, reCaptchaCode);
        return restTemplate.postForObject(VERIFY_URL, form, ReCaptchaResponse.class);
    }
}
