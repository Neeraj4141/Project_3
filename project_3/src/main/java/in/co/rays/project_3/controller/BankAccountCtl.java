package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BankAccountDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BankAccountModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BankAccountCtl" })
public class BankAccountCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BankAccountCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("BankAccountCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("accountCode"))) {
			request.setAttribute("accountCode", PropertyReader.getValue("error.require", "Account Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("accountHolderName"))) {
			request.setAttribute("accountHolderName", PropertyReader.getValue("error.require", "Account Holder Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("balance"))) {
			request.setAttribute("balance", PropertyReader.getValue("error.require", "Balance"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		log.debug("BankAccountCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BankAccountDTO dto = new BankAccountDTO();

		dto.setAccountId(DataUtility.getLong(request.getParameter("id")));
		dto.setAccountCode(DataUtility.getString(request.getParameter("accountCode")));
		dto.setAccountHolderName(DataUtility.getString(request.getParameter("accountHolderName")));
		dto.setBalance(DataUtility.getString(request.getParameter("balance")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		BankAccountModelInt model = ModelFactory.getInstance().getBankAccountModel();

		if (id > 0 || op != null) {

			BankAccountDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
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

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		BankAccountModelInt model = ModelFactory.getInstance().getBankAccountModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BankAccountDTO dto = (BankAccountDTO) populateDTO(request);

			try {
				if (id > 0) {

					model.update(dto);
					ServletUtility.setSuccessMessage("Successfully Updated", request);

				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Successfully Saved", request);

					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("BankAccount already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("BankAccount already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BankAccountDTO dto = (BankAccountDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BANKACCOUNT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANKACCOUNT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANKACCOUNT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("BankAccountCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.BANKACCOUNT_VIEW;
	}
}