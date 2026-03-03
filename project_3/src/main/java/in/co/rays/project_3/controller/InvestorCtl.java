package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.InvestorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.InvestorModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/InvestorCtl" })
public class InvestorCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(InvestorCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("investorName"))) {
			request.setAttribute("investorName", PropertyReader.getValue("error.require", "Investor Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("investorcode"))) {
			request.setAttribute("investorcode", PropertyReader.getValue("error.require", "Investor Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("investmentAmount"))) {
			request.setAttribute("investmentAmount", PropertyReader.getValue("error.require", "Investment Amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("investmentType"))) {
			request.setAttribute("investmentType", PropertyReader.getValue("error.require", "Investment Type"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		InvestorDTO dto = new InvestorDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setInvestorName(DataUtility.getString(request.getParameter("investorName")));
		dto.setInvestorcode(DataUtility.getString(request.getParameter("investorcode")));
		dto.setInvestmentAmount(DataUtility.getString(request.getParameter("investmentAmount")));
		dto.setInvestmentType(DataUtility.getString(request.getParameter("investmentType")));

		return populateBean(dto, request);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		InvestorModelInt model = ModelFactory.getInstance().getInvestorModel();

		if (id > 0) {
			try {
				InvestorDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		InvestorModelInt model = ModelFactory.getInstance().getInvestorModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InvestorDTO dto = (InvestorDTO) populateDTO(request);

			try {
				long pk = model.add(dto);
				ServletUtility.setSuccessMessage("Investor Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Investor Code Already Exists", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

			ServletUtility.forward(getView(), request, response);

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InvestorDTO dto = (InvestorDTO) populateDTO(request);

			try {
				model.update(dto);
				ServletUtility.setSuccessMessage("Investor Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Investor Code Already Exists", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

			ServletUtility.forward(getView(), request, response);

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			InvestorDTO dto = (InvestorDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.INVESTOR_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVESTOR_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVESTOR_LIST_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.INVESTOR_VIEW;
	}
}