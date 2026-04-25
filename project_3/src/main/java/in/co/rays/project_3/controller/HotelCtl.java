package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HotelModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "HotelCtl", urlPatterns = { "/ctl/HotelCtl" })
public class HotelCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HotelCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "please enter correct Name");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("lastName", "please enter correct Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomNo"))) {
			request.setAttribute("roomNo", PropertyReader.getValue("error.require", "Room No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cheackIn"))) {
			request.setAttribute("cheackIn", PropertyReader.getValue("error.require", "Check In"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("cheackIn"))) {
			request.setAttribute("cheackIn", PropertyReader.getValue("error.date", "Date Of CheakIn"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cheackOut"))) {
			request.setAttribute("cheackOut", PropertyReader.getValue("error.require", "Check Out"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("cheackOut"))) {
			request.setAttribute("cheackOut", PropertyReader.getValue("error.date", "Date Of CheackOut"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		HotelDTO dto = new HotelDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setRoomNo(DataUtility.getLong(request.getParameter("roomNo")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setCheackIn(DataUtility.getDate(request.getParameter("cheackIn")));
		dto.setCheackOut(DataUtility.getDate(request.getParameter("cheackOut")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("HotelCtl doGet Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		HotelModelInt model = ModelFactory.getInstance().getHotelModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			HotelDTO dto = null;
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
			throws ServletException, IOException {

		log.debug("HotelCtl doPost Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		HotelModelInt model = ModelFactory.getInstance().getHotelModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HotelDTO dto = (HotelDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data updated successfully", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data saved successfully", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Room No already exists", request);
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

			HotelDTO dto = (HotelDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.HOTEL_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.HOTEL_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.HOTEL_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.HOTEL_VIEW;
	}
}