package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ContractDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ContractModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ContractCtl" })
public class ContractCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ContractCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("contractName"))) {
			request.setAttribute("contractName", PropertyReader.getValue("error.require", "Contract Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contractCode"))) {
			request.setAttribute("contractCode", PropertyReader.getValue("error.require", "Contract Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.require", "Start Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.date", "Start Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("endDate"))) {
			request.setAttribute("endtDate", PropertyReader.getValue("error.require", "End Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("endDate"))) {
			request.setAttribute("endtDate", PropertyReader.getValue("error.date", "End Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ContractDTO dto = new ContractDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setContractCode(DataUtility.getString(request.getParameter("contractCode")));
		dto.setContractName(DataUtility.getString(request.getParameter("contractName")));
		dto.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		dto.setEndDate(DataUtility.getDate(request.getParameter("endDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("ContractCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ContractModelInt model = ModelFactory.getInstance().getContractModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			ContractDTO dto;
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

		ContractModelInt model = ModelFactory.getInstance().getContractModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ContractDTO dto = (ContractDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Contract Name already exists", request);
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

			ContractDTO dto = (ContractDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.CONTRACT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CONTRACT_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CONTRACT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.CONTRACT_VIEW;
	}
}