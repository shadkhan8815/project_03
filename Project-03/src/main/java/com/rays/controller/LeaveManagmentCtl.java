package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.LeaveManagmentDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.LeaveManagmentInt;
import com.rays.model.ModelFactory;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/LeaveManagmentCtl" })
public class LeaveManagmentCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LeaveManagmentCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("leaveCode"))) {
			request.setAttribute("leaveCode", PropertyReader.getValue("error.require", "leaveCode"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "employeeName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", "Employee Name must contain alphabets only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("leaveStartDate"))) {
			request.setAttribute("leaveStartDate", PropertyReader.getValue("error.require", "leaveStartDate"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("leaveEndDate"))) {
			request.setAttribute("leaveEndDate", PropertyReader.getValue("error.require", "leaveEndDate"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("leaveStatus"))) {
			request.setAttribute("leaveStatus", PropertyReader.getValue("error.require", "leaveStatus"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		LeaveManagmentDTO dto = new LeaveManagmentDTO();

		dto.setLeaveCode(DataUtility.getString(request.getParameter("leaveCode")));
		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
		dto.setLeaveStartDate(DataUtility.getDate(request.getParameter("leaveStartDate")));
		dto.setLeaveEndDate(DataUtility.getDate(request.getParameter("leaveEndDate")));
		dto.setLeaveStatus(DataUtility.getString(request.getParameter("leaveStatus")));

		populateBean(dto, request);
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		long id = DataUtility.getLong(request.getParameter("id"));

		LeaveManagmentInt model = ModelFactory.getInstance().getLeaveManagmentModel();

		if (id > 0) {
			try {
				LeaveManagmentDTO dto = model.findByPK(id);
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

		LeaveManagmentInt model = ModelFactory.getInstance().getLeaveManagmentModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			LeaveManagmentDTO dto = (LeaveManagmentDTO) populateDTO(request);

			try {

				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setSuccessMessage("Leave Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Leave Added Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error("Database Error", e);
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Database Server is down. Please try after some time..!", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Leave Code Already Exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LEAVE_MANAGMENT_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LEAVE_MANAGMENT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.LEAVE_MANAGMENT_VIEW;
	}
}