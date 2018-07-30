package javaschool.controller.dtoentity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ReCaptchaResponse {
    private boolean success;
    @JsonProperty("challenge_ts")
    private String timestamp;
    private String hostname;
    @JsonProperty("error-codes")
    private List<String> errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
