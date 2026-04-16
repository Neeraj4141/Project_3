package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EnvironmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EnvironmentModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Environment Controller
 * 
 * @author Neeraj Mewada
 *
 */
@WebServlet(urlPatterns = { "/ctl/EnvironmentCtl" })
public class EnvironmentCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(EnvironmentCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("environmentName"))) {
			request.setAttribute("environmentName",
					PropertyReader.getValue("error.require", "Environment Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("environmentName"))) {
			request.setAttribute("environmentName", "Please enter correct name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("environmentCode"))) {
			request.setAttribute("environmentCode",
					PropertyReader.getValue("error.require", "Environment Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("url"))) {
			request.setAttribute("url",
					PropertyReader.getValue("error.require", "URL"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EnvironmentDTO dto = new EnvironmentDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setEnvironmentCode(DataUtility.getString(request.getParameter("environmentCode")));
		dto.setEnvironmentName(DataUtility.getString(request.getParameter("environmentName")));
		dto.setUrl(DataUtility.getString(request.getParameter("url")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EnvironmentCtl doGet method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		EnvironmentModelInt model = ModelFactory.getInstance().getEnvironmentModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			EnvironmentDTO dto;
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

		EnvironmentModelInt model = ModelFactory.getInstance().getEnvironmentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EnvironmentDTO dto = (EnvironmentDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Environment Name already exists", request);
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

			EnvironmentDTO dto = (EnvironmentDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ENVIRONMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ENVIRONMENT_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ENVIRONMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ENVIRONMENT_VIEW;
	}
}