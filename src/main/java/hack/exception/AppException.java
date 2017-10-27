package hack.exception;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

public class AppException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;		
	private HttpStatus httpResponseCode;
	private List<AppError> errors;
	private String errorUid = UUID.randomUUID().toString();

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message, Throwable cause, HttpStatus httpResponseCode, List<AppError> errors) {
        super(message, cause);
        this.httpResponseCode = httpResponseCode;
        this.errors = errors;
    }

    public AppException(String message, HttpStatus httpResponseCode, List<AppError> errors) {
        super(message);
        this.httpResponseCode = httpResponseCode;
        this.errors = errors;
    }

	public HttpStatus getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(HttpStatus httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public List<AppError> getErrors() {
		return errors;
	}

	public void setErrors(List<AppError> errors) {
		this.errors = errors;
	}

	public String getErrorUid() {
		return errorUid;
	}
    
}
