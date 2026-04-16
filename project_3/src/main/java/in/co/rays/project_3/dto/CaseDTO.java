package in.co.rays.project_3.dto;

public class CaseDTO extends BaseDTO {

	private String testCaseCode;
	private String title;
	private String expectedResult;
	private String status;
	
	public String getTestCaseCode() {
		return testCaseCode;
	}
	public void setTestCaseCode(String testCaseCode) {
		this.testCaseCode = testCaseCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
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
