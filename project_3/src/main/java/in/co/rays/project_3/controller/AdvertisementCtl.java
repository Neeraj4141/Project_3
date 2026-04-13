package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AdvertisementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AdvertisementModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/AdvertisementCtl" })
public class AdvertisementCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AdvertisementCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("adName"))) {
			request.setAttribute("adName", PropertyReader.getValue("error.require", "Ad Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("adName"))) {
			request.setAttribute("adName", "Please enter correct name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("adCode"))) {
			request.setAttribute("adCode", PropertyReader.getValue("error.require", "Ad Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("budget"))) {
			request.setAttribute("budget", PropertyReader.getValue("error.require", "Budget"));
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

		AdvertisementDTO dto = new AdvertisementDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setAdCode(DataUtility.getString(request.getParameter("adCode")));
		dto.setAdName(DataUtility.getString(request.getParameter("adName")));
		dto.setBudget(DataUtility.getLong(request.getParameter("budget")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("AdvertisementCtl doGet method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		AdvertisementModelInt model = ModelFactory.getInstance().getAdvertisementModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			AdvertisementDTO dto;
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

		AdvertisementModelInt model = ModelFactory.getInstance().getAdvertisementModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			AdvertisementDTO dto = (AdvertisementDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Ad Name already exists", request);
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

			AdvertisementDTO dto = (AdvertisementDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ADVERTISEMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ADVERTISEMENT_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ADVERTISEMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ADVERTISEMENT_VIEW;
	}
}