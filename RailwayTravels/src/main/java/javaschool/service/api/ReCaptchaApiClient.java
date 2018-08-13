package javaschool.service.api;

import javaschool.controller.dtoentity.ReCaptchaResponse;

public interface ReCaptchaApiClient {
    /**
     * Checks if specified reCaptchaCode is valid
     *
     * @param recCaptchaCode
     * @return  status of verifying
     */
    ReCaptchaResponse verify(String recCaptchaCode);
}
