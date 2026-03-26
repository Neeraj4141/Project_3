package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.WalletModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Wallet functionality controller.to perform add,delete ,update operation
 * 
 * @author Neeraj Mewada
 *
 */
@WebServlet(urlPatterns = { "/ctl/WalletCtl" })
public class WalletCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(WalletCtl.class);

    protected boolean validate(HttpServletRequest request) {

        log.debug("WalletCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("walletCode"))) {
            request.setAttribute("walletCode",
                    PropertyReader.getValue("error.require", "Wallet Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName",
                    PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("balance"))) {
            request.setAttribute("balance",
                    PropertyReader.getValue("error.require", "Balance"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        log.debug("WalletCtl Method validate Ended");

        return pass;
    }

    protected BaseDTO populateDTO(HttpServletRequest request) {

        WalletDTO dto = new WalletDTO();

        // ✅ walletId as primary key
        dto.setWalletId(DataUtility.getLong(request.getParameter("walletId")));

        dto.setWalletCode(DataUtility.getString(request.getParameter("walletCode")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setBalance(DataUtility.getString(request.getParameter("balance")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = request.getParameter("operation");

        // ✅ walletId use
        long id = DataUtility.getLong(request.getParameter("walletId"));

        WalletModelInt model = ModelFactory.getInstance().getWalletModel();

        if (id > 0 || op != null) {

            WalletDTO dto;
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

        // ✅ walletId use
        long id = DataUtility.getLong(request.getParameter("walletId"));

        WalletModelInt model = ModelFactory.getInstance().getWalletModel();

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            WalletDTO dto = (WalletDTO) populateDTO(request);

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
                        ServletUtility.setErrorMessage("Wallet already exists", request);
                    }
                }

                ServletUtility.setDto(dto, request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Wallet already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            WalletDTO dto = (WalletDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.WALLET_LIST_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.WALLET_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.WALLET_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);

        log.debug("WalletCtl Method doPost Ended");
    }

    @Override
    protected String getView() {
        return ORSView.WALLET_VIEW;
    }
}