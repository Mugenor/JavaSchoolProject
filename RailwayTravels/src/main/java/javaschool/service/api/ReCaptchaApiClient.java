package javaschool.service.api;

import javaschool.controller.dtoentity.ReCaptchaResponse;

public interface ReCaptchaApiClient {
    ReCaptchaResponse verify(String recCaptchaCode);
}
