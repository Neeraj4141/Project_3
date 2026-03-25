package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PortfolioDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PortfolioModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PortfolioCtl" })
public class PortfolioCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PortfolioCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("PortfolioCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("portfolioNo"))) {
			request.setAttribute("portfolioNo",
					PropertyReader.getValue("error.require", "Portfolio No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("portfolioName"))) {
			request.setAttribute("portfolioName",
					PropertyReader.getValue("error.require", "Portfolio Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalValue"))) {
			request.setAttribute("totalValue",
					PropertyReader.getValue("error.require", "Total Value"));
			pass = false;
		}

		log.debug("PortfolioCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PortfolioDTO dto = new PortfolioDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setPortfolioNo(DataUtility.getString(request.getParameter("portfolioNo")));
		dto.setPortfolioName(DataUtility.getString(request.getParameter("portfolioName")));
		dto.setTotalValue(DataUtility.getString(request.getParameter("totalValue")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		PortfolioModelInt model = ModelFactory.getInstance().getPortfolioModel();

		if (id > 0 || op != null) {

			try {
				PortfolioDTO dto = model.findByPK(id);
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

		PortfolioModelInt model = ModelFactory.getInstance().getPortfolioModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PortfolioDTO dto = (PortfolioDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Portfolio already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException | DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PortfolioDTO dto = (PortfolioDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PORTFOLIO_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PORTFOLIO_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PORTFOLIO_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.PORTFOLIO_VIEW;
	}
}