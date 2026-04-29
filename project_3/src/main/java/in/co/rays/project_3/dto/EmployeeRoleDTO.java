package in.co.rays.project_3.dto;

/**
 * @author NeerajMewada
 *
 */
public class EmployeeRoleDTO extends BaseDTO {

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getKey() {

		return id + "";
	}

	public String getValue() {

		return roleName + "";
	}

}
