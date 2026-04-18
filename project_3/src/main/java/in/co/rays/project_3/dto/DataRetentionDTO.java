package in.co.rays.project_3.dto;

public class DataRetentionDTO extends BaseDTO {

	private String retentionCode;
	private String dataType;
	private String duration;
	private String status;
	
	public String getRetentionCode() {
		return retentionCode;
	}
	public void setRetentionCode(String retentionCode) {
		this.retentionCode = retentionCode;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
