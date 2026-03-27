package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CartModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CartCtl" })
public class CartCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(CartCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("CartCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("cartCode"))) {
            request.setAttribute("cartCode", PropertyReader.getValue("error.require", "Cart Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("totalItems"))) {
            request.setAttribute("totalItems", PropertyReader.getValue("error.require", "Total Items"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }
        log.debug("CartCtl Method validate Ended");

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        CartDTO dto = new CartDTO();

        dto.setCartId(DataUtility.getLong(request.getParameter("id")));
        dto.setCartCode(DataUtility.getString(request.getParameter("cartCode")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setTotalItems(DataUtility.getLong(request.getParameter("totalItems")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = request.getParameter("operation");

        long id = DataUtility.getLong(request.getParameter("id"));

        CartModelInt model = ModelFactory.getInstance().getCartModel();

        if (id > 0 || op != null) {

            CartDTO dto;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = request.getParameter("operation");

        long id = DataUtility.getLong(request.getParameter("id"));

        CartModelInt model = ModelFactory.getInstance().getCartModel();

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            CartDTO dto = (CartDTO) populateDTO(request);

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
                        ServletUtility.setErrorMessage("Cart already exists", request);
                    }
                }

                ServletUtility.setDto(dto, request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Cart already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            CartDTO dto = (CartDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.CART_LIST_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CART_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CART_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CartCtl Method doPost Ended");
    }

    @Override
    protected String getView() {
        return ORSView.CART_VIEW;
    }
}