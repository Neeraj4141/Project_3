package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ReportModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ReportCtl" })
public class ReportCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ReportCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("repairNo"))) {
			request.setAttribute("repairNo", PropertyReader.getValue("error.require", "Repair No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("deviceName"))) {
			request.setAttribute("deviceName", PropertyReader.getValue("error.require", "Device Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "Cost Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("repairDate"))) {
			request.setAttribute("repairDate", PropertyReader.getValue("error.require", "Repair Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("repairDate"))) {
			request.setAttribute("repairDate", PropertyReader.getValue("error.date", "Repair Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ReportDTO dto = new ReportDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRepairNo(DataUtility.getLong(request.getParameter("repairNo")));
		dto.setCost(DataUtility.getString(request.getParameter("cost")));
		dto.setRepairDate(DataUtility.getDate(request.getParameter("repairDate")));
		dto.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("ReportCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ReportModelInt model = ModelFactory.getInstance().getReportModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			ReportDTO dto;
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

		ReportModelInt model = ModelFactory.getInstance().getReportModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ReportDTO dto = (ReportDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Repair No already exists", request);
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

			ReportDTO dto = (ReportDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.REPORT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.REPORT_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.REPORT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.REPORT_VIEW;
	}
}