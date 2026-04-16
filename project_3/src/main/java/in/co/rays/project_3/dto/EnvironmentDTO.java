package in.co.rays.project_3.dto;

public class EnvironmentDTO extends BaseDTO {

	private String environmentCode;
	private String environmentName;
	private String url;
	private String status;

	public String getEnvironmentCode() {
		return environmentCode;
	}

	public void setEnvironmentCode(String environmentCode) {
		this.environmentCode = environmentCode;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}