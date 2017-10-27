package hack.exception;

public class AppError {
	private AppErrorSeverity severity;
	private String code;
	private String message;
	private String detail;

	public AppError() {
	}

	public AppError(AppErrorSeverity severity, String code, String message, String detail) {
		this.severity = severity;
		this.code = code;
		this.message = message;
		this.detail = detail;
	}

	public AppErrorSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(AppErrorSeverity severity) {
		this.severity = severity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
