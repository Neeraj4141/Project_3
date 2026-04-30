package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * @author NeerajMewada
 *
 */
public class EmployeeDTO extends BaseDTO {

	private String employeeName;
	private String employeeCode;
	private String address;
	private String email;
	private Long role;
	private String gender;
	private Date joiningDate;

	// setter getter methods
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public String getKey() {

		return role + "";
	}

	@Override
	public String getValue() {

		return employeeName + "";
	}

}
