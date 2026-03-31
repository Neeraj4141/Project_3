package in.co.rays.project_3.dto;

import java.util.Date;

public class PolicyDTO extends BaseDTO {

	private Long policyNo;
	private String policyName;
	private String premiumAmount;
	private Date startDate;

	public Long getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(Long policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String getKey() {

		return null;
	}

	@Override
	public String getValue() {

		return null;
	}

}
