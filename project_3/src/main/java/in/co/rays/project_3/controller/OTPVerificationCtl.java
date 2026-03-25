package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OTPVerificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.OTPVerificationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/OTPVerificationCtl" })
public class OTPVerificationCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(OTPVerificationCtl.class);

	// ================= VALIDATE =================
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("otpid"))) {
			request.setAttribute("otpid", PropertyReader.getValue("error.require", "OTP Id"));
			pass = false;
		} 
		
		// OTP Code
		if (DataValidator.isNull(request.getParameter("otpCode"))) {
			request.setAttribute("otpCode", PropertyReader.getValue("error.require", "OTP Code"));
			pass = false;
		} else if (request.getParameter("otpCode").length() != 6) {
			request.setAttribute("otpCode", "OTP must be 6 digits");
			pass = false;
		}

		// OTP Value
		if (DataValidator.isNull(request.getParameter("otpValue"))) {
			request.setAttribute("otpValue", PropertyReader.getValue("error.require", "OTP Value"));
			pass = false;
		}

		// Expiry Time
		if (DataValidator.isNull(request.getParameter("expiryTime"))) {
			request.setAttribute("expiryTime", PropertyReader.getValue("error.require", "Expiry Time"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("expiryTime"))) {
			request.setAttribute("expiryTime", PropertyReader.getValue("error.date", "Expiry Time"));
			pass = false;
		}

		// OTP Status
		if (DataValidator.isNull(request.getParameter("otpStatus"))) {
			request.setAttribute("otpStatus", PropertyReader.getValue("error.require", "OTP Status"));
			pass = false;
		}

		return pass;
	}

	// ================= POPULATE DTO =================
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		OTPVerificationDTO dto = new OTPVerificationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id"))); // BaseDTO
		dto.setOtpId(DataUtility.getLong(request.getParameter("otpId")));
		dto.setOtpCode(DataUtility.getString(request.getParameter("otpCode")));
		dto.setOtpValue(DataUtility.getString(request.getParameter("otpValue")));
		dto.setOtpStatus(DataUtility.getString(request.getParameter("otpStatus")));
		dto.setExpiryTime(DataUtility.getDate(request.getParameter("expiryTime")));

		populateBean(dto, request);

		return dto;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("OTPVerificationCtl doGet Start");

		OTPVerificationModelInt model = ModelFactory.getInstance().getOTPVerificationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				OTPVerificationDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ================= DO POST =================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		OTPVerificationModelInt model = ModelFactory.getInstance().getOTPVerificationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		// SAVE / UPDATE
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			OTPVerificationDTO dto = (OTPVerificationDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("OTP Updated Successfully", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("OTP Added Successfully", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("OTP Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("OTP Code already exists", request);
			}

		}

		// DELETE
		else if (OP_DELETE.equalsIgnoreCase(op)) {

			OTPVerificationDTO dto = (OTPVerificationDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.OTPVERIFICATION_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}

		// CANCEL
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.OTPVERIFICATION_LIST_CTL, request, response);
			return;
		}

		// RESET
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.OTPVERIFICATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("OTPVerificationCtl doPost End");
	}

	// ================= VIEW =================
	@Override
	protected String getView() {
		return ORSView.OTPVERIFICATION_VIEW;
	}
}