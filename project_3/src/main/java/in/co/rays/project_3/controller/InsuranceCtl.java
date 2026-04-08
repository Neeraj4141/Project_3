package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.InsuranceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.InsuranceModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/InsuranceCtl" })
public class InsuranceCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(InsuranceCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("insuranceCode"))) {
			request.setAttribute("insuranceCode", PropertyReader.getValue("error.require", "Insurance Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("policyHolder"))) {
			request.setAttribute("policyHolder", PropertyReader.getValue("error.require", "Policy Holder"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("premiumAmount"))) {
			request.setAttribute("premiumAmount", PropertyReader.getValue("error.require", "Premium Amount"));
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

		InsuranceDTO dto = new InsuranceDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setInsuranceCode(DataUtility.getString(request.getParameter("insuranceCode")));
		dto.setPolicyHolder(DataUtility.getString(request.getParameter("policyHolder")));
		dto.setPremiumAmount(DataUtility.getLong(request.getParameter("premiumAmount")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("InsuranceCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		InsuranceModelInt model = ModelFactory.getInstance().getInsuranceModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			InsuranceDTO dto;
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

		InsuranceModelInt model = ModelFactory.getInstance().getInsuranceModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			InsuranceDTO dto = (InsuranceDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Insurance Code already exists", request);
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

			InsuranceDTO dto = (InsuranceDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.INSURANCE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INSURANCE_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INSURANCE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.INSURANCE_VIEW;
	}
}