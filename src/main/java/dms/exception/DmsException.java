package dms.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DmsException extends Exception implements Serializable {
    private List<String> errors;
    private String message;

    public DmsException() {
        this.errors = new ArrayList<>();
    }

    public DmsException(String message, List<String> errors) {
        this.errors = errors;
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
