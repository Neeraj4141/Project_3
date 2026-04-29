package in.co.rays.project_3.test;

import in.co.rays.project_3.dto.EmployeeRoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeeRoleModelHibImpl;
import in.co.rays.project_3.model.EmployeeRoleModelInt;

public class EmployeeRoleModelTest {
	public static EmployeeRoleModelInt model = new EmployeeRoleModelHibImpl();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		testAdd();
	}

	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		EmployeeRoleDTO dto = new EmployeeRoleDTO();
		dto.setRoleName("HR");
		long pk = model.add(dto);

	}
}
