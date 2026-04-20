package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WebHookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.WebHookModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/WebHookCtl" })
public class WebHookCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(WebHookCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("logCode"))) {
			request.setAttribute("logCode", PropertyReader.getValue("error.require", "Log Code"));
			pass = false;
		}
		// WEBHOOK ID
		if (DataValidator.isNull(request.getParameter("webhookId"))) {
			request.setAttribute("webhookId", PropertyReader.getValue("error.require", "Webhook Id"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("webhookId"))) {
			request.setAttribute("webhookId", "Webhook Id must be a number");
			pass = false;
		}

		// RESPONSE
		if (DataValidator.isNull(request.getParameter("response"))) {
			request.setAttribute("response", PropertyReader.getValue("error.require", "Response"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("response"))) {
			request.setAttribute("response", "Please enter valid Response");
			pass = false;
		}

		// STATUS
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("status"))) {
			request.setAttribute("status", "Please enter correct Status");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		WebHookDTO dto = new WebHookDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLogCode(DataUtility.getString(request.getParameter("logCode")));
		dto.setWebhookId(DataUtility.getLong(request.getParameter("webhookId")));
		dto.setResponse(DataUtility.getString(request.getParameter("response")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("WebHookCtl doGet method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		WebHookModelInt model = ModelFactory.getInstance().getWebHookModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			WebHookDTO dto;
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

		WebHookModelInt model = ModelFactory.getInstance().getWebHookModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			WebHookDTO dto = (WebHookDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("LogCode already exists", request);
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

			WebHookDTO dto = (WebHookDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.WEBHOOK_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WEBHOOK_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WEBHOOK_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.WEBHOOK_VIEW;
	}
}