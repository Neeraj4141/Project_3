package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LockerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.LockerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/LockerCtl" })
public class LockerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(LockerCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("lockerNumber"))) {
			request.setAttribute("lockerNumber", PropertyReader.getValue("error.require", "Locker Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lockerType"))) {
			request.setAttribute("lockerType", PropertyReader.getValue("error.require", "Locker Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("annualFee"))) {
			request.setAttribute("annualFee", PropertyReader.getValue("error.require", "Annual Fee"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		LockerDTO dto = new LockerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("LockerId")));
		dto.setLockerNumber(DataUtility.getString(request.getParameter("lockerNumber")));
		dto.setLockerType(DataUtility.getString(request.getParameter("lockerType")));
		dto.setAnnualFee(DataUtility.getString(request.getParameter("annualFee")));

		return populateBean(dto, request);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("lockerId"));
		LockerModelInt model = ModelFactory.getInstance().getLockerModel();
		System.out.println(id);

		if (id > 0) {
			try {
				LockerDTO dto = model.findByPK(id);
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

		long id = DataUtility.getLong(request.getParameter("lockerId"));

		LockerModelInt model = ModelFactory.getInstance().getLockerModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			LockerDTO dto = (LockerDTO) populateDTO(request);

			try {
				long pk = model.add(dto);
				ServletUtility.setSuccessMessage("Locker Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Locker Number Already Exists", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

			ServletUtility.forward(getView(), request, response);

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			LockerDTO dto = (LockerDTO) populateDTO(request);

			try {
				model.update(dto);
				ServletUtility.setSuccessMessage("Locker Updated Successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Locker Number Already Exists", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

			ServletUtility.forward(getView(), request, response);

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			LockerDTO dto = (LockerDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.LOCKER_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOCKER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOCKER_LIST_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.LOCKER_VIEW;
	}

}
