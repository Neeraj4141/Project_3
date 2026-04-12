package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;

import in.co.rays.project_3.dto.StockMovementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.StockMovementModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/StockMovementCtl" })
public class StockMovementCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StockMovementCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("StockMovementCtl validate start");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("movementCode"))) {
			request.setAttribute("movementCode", PropertyReader.getValue("error.require", "Movement Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("productName"))) {
			request.setAttribute("productName", PropertyReader.getValue("error.require", "Product Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		log.debug("StockMovementCtl validate end");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("StockMovementCtl populateDTO start");

		StockMovementDTO dto = new StockMovementDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setMovementCode(DataUtility.getString(request.getParameter("movementCode")));
		dto.setProductName(DataUtility.getString(request.getParameter("productName")));
		dto.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		log.debug("StockMovementCtl populateDTO end");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("StockMovementCtl doGet start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		StockMovementModelInt model = ModelFactory.getInstance().getStockMovementModel();

		if (id > 0 || op != null) {
			try {
				StockMovementDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("StockMovementCtl doGet end");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("StockMovementCtl doPost start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		StockMovementModelInt model = ModelFactory.getInstance().getStockMovementModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			StockMovementDTO dto = (StockMovementDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					dto.setId(id);
					ServletUtility.setSuccessMessage("Data Successfully Update", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data Successfully Saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Movement Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException | DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			StockMovementDTO dto = (StockMovementDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STOCKMOVEMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCKMOVEMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCKMOVEMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("StockMovementCtl doPost end");
	}

	@Override
	protected String getView() {
		return ORSView.STOCKMOVEMENT_VIEW;
	}
}