package in.co.rays.project_3.dto;

import java.util.Date;

public class ReportDTO extends BaseDTO {

	private Long repairNo;
	private String deviceName;
	private Date repairDate;
	private String cost;

	

	public Long getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
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
