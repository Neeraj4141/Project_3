package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.EmployeeRoleModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * @author NeerajMeawda
 *
 */
@WebServlet(urlPatterns = { "/ctl/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(EmployeeCtl.class);
	
	protected void preload(HttpServletRequest request) {

		EmployeeRoleModelInt model = ModelFactory.getInstance().getEmployeeRoleModel();

		List list;
		try {
			list = model.list();

			request.setAttribute("roleList", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= VALIDATION =================
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		// Employee Name
		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", "Please enter valid name");
			pass = false;
		}

		// Address
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		// Role
		if (DataValidator.isNull(request.getParameter("role"))) {
			request.setAttribute("role", PropertyReader.getValue("error.require", "Role"));
			pass = false;
		}

		// Gender
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		// Joining Date
		if (DataValidator.isNull(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.require", "Joining Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.date", "Joining Date"));
			pass = false;
		}

		// Email
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email"));
			pass = false;
		}

		// Employee Code
		if (DataValidator.isNull(request.getParameter("employeeCode"))) {
			request.setAttribute("employeeCode", PropertyReader.getValue("error.require", "Employee Code"));
			pass = false;
		} else if (!request.getParameter("employeeCode").matches("^[a-zA-Z0-9]+$")) {
			request.setAttribute("employeeCode", "Employee Code must be alphanumeric");
			pass = false;
		}

		return pass;
	}

	// ================= DTO POPULATION =================
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EmployeeDTO dto = new EmployeeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setRole(DataUtility.getLong(request.getParameter("role")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setEmployeeCode(DataUtility.getString(request.getParameter("employeeCode")));

		populateBean(dto, request);

		return dto;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EmployeeCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			try {
				EmployeeDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ================= DO POST =================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EmployeeCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		// SAVE / UPDATE
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Employee updated successfully", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Employee added successfully", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Employee Code already exists", request);
					}
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException | DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		// DELETE
		else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		// CANCEL
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
			return;
		}

		// RESET
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ================= VIEW =================
	@Override
	protected String getView() {
		return ORSView.EMPLOYEE_VIEW;
	}
}