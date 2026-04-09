package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SpaceMissionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SpaceMissionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/SpaceMissionCtl" })
public class SpaceMissionCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(SpaceMissionCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("missionName"))) {
			request.setAttribute("missionName", PropertyReader.getValue("error.require", "Mission Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("missionName"))) {
			request.setAttribute("missionName", "MissionName is not Correct");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("launchVehical"))) {
			request.setAttribute("launchVehical", PropertyReader.getValue("error.require", "Launch Vehical"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("launchVehical"))) {
			request.setAttribute("launchVehical", "launchVehical is not Correct");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("destination"))) {
			request.setAttribute("destination", PropertyReader.getValue("error.require", "Destination"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("destination"))) {
			request.setAttribute("destination", "destination is not Correct");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("missionStatus"))) {
			request.setAttribute("missionStatus", PropertyReader.getValue("error.require", "Mission Status"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("missionStatus"))) {
			request.setAttribute("missionStatus", "missionStatus is not Correct");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SpaceMissionDTO dto = new SpaceMissionDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setMissionName(DataUtility.getString(request.getParameter("missionName")));
		dto.setLaunchVehical(DataUtility.getString(request.getParameter("launchVehical")));
		dto.setDestination(DataUtility.getString(request.getParameter("destination")));
		dto.setMissionStatus(DataUtility.getString(request.getParameter("missionStatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("SpaceMissionCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SpaceMissionModelInt model = ModelFactory.getInstance().getSpaceMissionModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			SpaceMissionDTO dto;
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

		SpaceMissionModelInt model = ModelFactory.getInstance().getSpaceMissionModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SpaceMissionDTO dto = (SpaceMissionDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Mission Name already exists", request);
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

			SpaceMissionDTO dto = (SpaceMissionDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.SPACEMISSION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SPACEMISSION_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SPACEMISSION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SPACEMISSION_VIEW;
	}
}