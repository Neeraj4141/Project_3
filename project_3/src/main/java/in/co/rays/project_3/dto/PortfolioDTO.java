package in.co.rays.project_3.dto;

public class PortfolioDTO extends BaseDTO {

	private String portfolioNo;
	private String portfolioName;
	private String totalValue;

	public String getPortfolioNo() {
		return portfolioNo;
	}

	public void setPortfolioNo(String portfolioNo) {
		this.portfolioNo = portfolioNo;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
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
