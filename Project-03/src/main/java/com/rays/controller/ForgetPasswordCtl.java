package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.UserDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.RecordNotFoundException;
import com.rays.model.ModelFactory;
import com.rays.model.UserModelInt;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * forget password ctl.To perform password send in email
 * 
 * @author Shad Khan
 *
 */
@WebServlet(urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {
	/**
	 * 
	 */

	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "login"));
			pass = false;
		}
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		UserDTO dto = new UserDTO();

		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		populateBean(dto, request);
		System.out.println("Hello");

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("do get method started");

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("do get method started");

		String op = request.getParameter("operation");

		UserModelInt userModel = ModelFactory.getInstance().getUserModel();

		UserDTO dto = (UserDTO) populateDTO(request);

		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				userModel.forgetPassword(dto.getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your registered email id.", request);

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.forward(getView(), request, response);

		}

	}

	@Override
	protected String getView() {

		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
