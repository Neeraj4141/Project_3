package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ResturentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ResturentModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Resturent functionality controller to perform add, delete, update operation
 * 
 * @author Neeraj Mewada
 *
 */
@WebServlet(urlPatterns = { "/ctl/ResturentCtl" })
public class ResturentCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ResturentCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("ResturentCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("restaurantName"))) {
			request.setAttribute("restaurantName", PropertyReader.getValue("error.require", "Resturent Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cuisineType"))) {
			request.setAttribute("cuisineType", PropertyReader.getValue("error.require", "Cuisine Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("rate"))) {
			request.setAttribute("rate", PropertyReader.getValue("error.require", "Rate"));
			pass = false;
		}

		log.debug("ResturentCtl Method validate Ended");

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		ResturentDTO dto = new ResturentDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRestaurantName(DataUtility.getString(request.getParameter("restaurantName")));
		dto.setLocation(DataUtility.getString(request.getParameter("location")));
		dto.setCuisineType(DataUtility.getString(request.getParameter("cuisineType")));
		dto.setRate(DataUtility.getLong(request.getParameter("rate")));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ResturentModelInt model = ModelFactory.getInstance().getResturentModel();

		if (id > 0 || op != null) {

			ResturentDTO dto;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ResturentModelInt model = ModelFactory.getInstance().getResturentModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ResturentDTO dto = (ResturentDTO) populateDTO(request);

			try {
				if (id > 0) {

					model.update(dto);
					ServletUtility.setSuccessMessage("Successfully Updated", request);

				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Successfully Saved", request);

					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Resturent already exists", request);
					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Resturent already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ResturentDTO dto = (ResturentDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.RESTURENT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESTURENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESTURENT_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("ResturentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.RESTURENT_VIEW;
	}
}