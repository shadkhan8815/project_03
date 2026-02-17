package com.rays.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.ModelFactory;
import com.rays.model.RoleModelInt;
import com.rays.model.UserModelInt;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * user functionality controller.to perform add,delete and update operation
 * 
 * @author Shad Khan
 *
 */
@WebServlet(urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	/**
	 * 
	 */

	private static Logger log = Logger.getLogger(UserCtl.class);

	protected void preload(HttpServletRequest request) {

		RoleModelInt model = ModelFactory.getInstance().getRoleModel();

		try {
			List list = model.list();
			Iterator it = list.iterator();

			while (it.hasNext()) {
				RoleDTO dto = (RoleDTO) it.next();
				System.out.println(dto.getId());
				System.out.println(dto.getName());
				System.out.println(dto.getDescription());

			}

			request.setAttribute("roleList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "first Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "please enter correct Name");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("lastName", "please enter correct Name");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Password Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "confirmPassword"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("role"))) {
			request.setAttribute("role", PropertyReader.getValue("error.require", "role"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.require", "email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.email", "Email Id "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isAge(request.getParameter("dob"))) {

			request.setAttribute("dob", "Age Must be greater then 18 year");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {

			request.setAttribute("confirmPassword", "Confirm  Password  should  be matched.");
			pass = false;
		}
		System.out.println(request.getParameter("dob"));
		System.out.println("validate end " + pass + "................" + request.getParameter("id"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("confirmPassword"));
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		UserDTO dto = new UserDTO();

		System.out.println(request.getParameter("dob"));
		System.out.println("Populate end " + "................" + request.getParameter("id"));
		System.out.println("-------------------------------------------" + request.getParameter("password"));
		System.out.println(request.getParameter("confirmPassword"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(DataUtility.getLong(request.getParameter("role")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("emailId")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateBean(dto, request);

		System.out.println(request.getParameter("dob") + "......." + dto.getDob());
		log.debug("UserRegistrationCtl Method populatedto Ended");

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("UserCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserDTO dto = null;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populateDTO(request);

			System.out.println(" in do post method jkjjkjk++++++++" + dto.getId());

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("college add" + dto + "id...." + id);
					// long pk
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error("Database Errore");
				ServletUtility.setErrorMessage(e.getMessage(), request);
				//ServletUtility.handleException(e, request, response);
				ServletUtility.forward(getView(), request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("User Already Exists", request);
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPostEnded");
	}

	@Override
	protected String getView() {

		return ORSView.USER_VIEW;
	}

}
