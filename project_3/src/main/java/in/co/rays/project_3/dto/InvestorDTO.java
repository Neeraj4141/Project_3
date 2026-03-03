package in.co.rays.project_3.dto;

public class InvestorDTO extends BaseDTO {

	private String investorName;
	private String investorcode;
	private String investmentAmount;
	private String investmentType;

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getInvestorcode() {
		return investorcode;
	}

	public void setInvestorcode(String investorcode) {
		this.investorcode = investorcode;
	}

	public String getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
