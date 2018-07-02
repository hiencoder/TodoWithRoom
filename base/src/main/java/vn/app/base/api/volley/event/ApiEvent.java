package vn.app.base.api.volley.event;


public class ApiEvent {

    public ApiEventType apiEventType;
    public String title;
    public String message;
    public Exception exception;

    public ApiEvent(ApiEventType apiEventType) {
        this.apiEventType = apiEventType;
    }

    public ApiEvent(ApiEventType apiEventType, String message) {
        this.apiEventType = apiEventType;
        this.message = message;
    }

    public ApiEvent(ApiEventType apiEventType, String title, String message) {
        this.apiEventType = apiEventType;
        this.title = title;
        this.message = message;
    }

    public ApiEventType getApiEventType() {
        return apiEventType;
    }

    public void setApiEventType(ApiEventType apiEventType) {
        this.apiEventType = apiEventType;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
