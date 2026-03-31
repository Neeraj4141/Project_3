package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PolicyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PolicyModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PolicyCtl" })
public class PolicyCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PolicyCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("policyName"))) {
			request.setAttribute("policyName",
					PropertyReader.getValue("error.require", "Policy Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("premiumAmount"))) {
			request.setAttribute("premiumAmount",
					PropertyReader.getValue("error.require", "Premium Amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate",
					PropertyReader.getValue("error.require", "Start Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("startDate"))) {
			request.setAttribute("startDate",
					PropertyReader.getValue("error.date", "Start Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PolicyDTO dto = new PolicyDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setPolicyNo(DataUtility.getLong(request.getParameter("policyNo")));
		dto.setPolicyName(DataUtility.getString(request.getParameter("policyName")));
		dto.setPremiumAmount(DataUtility.getString(request.getParameter("premiumAmount")));
		dto.setStartDate(DataUtility.getDate(request.getParameter("startDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("PolicyCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PolicyModelInt model = ModelFactory.getInstance().getPolicyModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			PolicyDTO dto;
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

		PolicyModelInt model = ModelFactory.getInstance().getPolicyModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PolicyDTO dto = (PolicyDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Policy Name already exists", request);
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

			PolicyDTO dto = (PolicyDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.POLICY_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.POLICY_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.POLICY_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.POLICY_VIEW;
	}
}