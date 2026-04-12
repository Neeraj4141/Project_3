package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StockMovementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.StockMovementModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "StockMovementListCtl", urlPatterns = { "/ctl/StockMovementListCtl" })

public class StockMovementListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StockMovementListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("Stock list populate bean start");

		StockMovementDTO dto = new StockMovementDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setMovementCode(DataUtility.getString(request.getParameter("movementCode")));
		dto.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		dto.setProductName(DataUtility.getString(request.getParameter("productName")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		log.debug("Stock list populate bean end");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Stock list doGet start");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		StockMovementDTO dto = (StockMovementDTO) populateDTO(request);
		StockMovementModelInt model = ModelFactory.getInstance().getStockMovementModel();

		List list;
		List next;

		try {
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			System.out.println(list + "in doget method in stock");

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("Stock list doGet end");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Stock list doPost start");

		List list;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		StockMovementModelInt model = ModelFactory.getInstance().getStockMovementModel();
		StockMovementDTO dto = (StockMovementDTO) populateDTO(request);

		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STOCKMOVEMENT_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STOCKMOVEMENT_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STOCKMOVEMENT_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {
					StockMovementDTO deleteDto = new StockMovementDTO();

					for (String id : ids) {
						deleteDto.setId(DataUtility.getLong(id));
						model.delete(deleteDto);
					}

					ServletUtility.setSuccessMessage("Data Delete Successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			list = model.search(dto, pageNo, pageSize);
			List next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);

			if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("Stock list doPost end");
	}

	@Override
	protected String getView() {
		return ORSView.STOCKMOVEMENT_LIST_VIEW;
	}
}