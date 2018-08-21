package javaschool.service.api;

import javaschool.controller.dtoentity.ReCaptchaResponse;

/**
 * The interface Re captcha api client.
 */
public interface ReCaptchaApiClient {
    /**
     * Checks if specified reCaptchaCode is valid
     *
     * @param recCaptchaCode the rec captcha code
     * @return status of verifying
     */
    ReCaptchaResponse verify(String recCaptchaCode);
}
