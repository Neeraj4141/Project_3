package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AuditDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AuditModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/AuditCtl" })
public class AuditCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AuditCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("auditCode"))) {
			request.setAttribute("auditCode", PropertyReader.getValue("error.require", "Audit Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("performedBy"))) {
			request.setAttribute("performedBy", PropertyReader.getValue("error.require", "Performed By"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("performedBy"))) {
			request.setAttribute("performedBy", "Please enter correct name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("action"))) {
			request.setAttribute("action", PropertyReader.getValue("error.require", "Action"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		AuditDTO dto = new AuditDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setAuditCode(DataUtility.getString(request.getParameter("auditCode")));
		dto.setPerformedBy(DataUtility.getString(request.getParameter("performedBy")));
		dto.setAction(DataUtility.getString(request.getParameter("action")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("AuditCtl doGet method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		AuditModelInt model = ModelFactory.getInstance().getAuditModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			AuditDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		AuditModelInt model = ModelFactory.getInstance().getAuditModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			AuditDTO dto = (AuditDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Audit Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException | DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			AuditDTO dto = (AuditDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.AUDIT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.AUDIT_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.AUDIT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.AUDIT_VIEW;
	}
}