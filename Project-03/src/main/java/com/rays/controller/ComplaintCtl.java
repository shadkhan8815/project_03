package com.rays.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.ComplaintDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.ComplaintModelInt;
import com.rays.model.ModelFactory;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ComplaintCtl" })
public class ComplaintCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ComplaintCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		
		map.put("Open", "Open");
		map.put("In Progress", "In Progress");
		map.put("On Hold", "On Hold");
		map.put("Resolved", "Resolved");
		map.put("Closed", "Closed");
		map.put("Rejected", "Rejected");
		
		request.setAttribute("map", map);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("complaintCode"))) {
			request.setAttribute("complaintCode", PropertyReader.getValue("error.require", "complaintCode"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("complaintTitle"))) {
			request.setAttribute("complaintTitle", PropertyReader.getValue("error.require", "complaintTitle"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("raisedBy"))) {
			request.setAttribute("raisedBy", PropertyReader.getValue("error.require", "raisedBy"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("complaintStatus"))) {
			request.setAttribute("complaintStatus", PropertyReader.getValue("error.require", "complaintStatus"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ComplaintDTO dto = new ComplaintDTO();

		dto.setComplaintCode(DataUtility.getString(request.getParameter("complaintCode")));
		dto.setComplaintTitle(DataUtility.getString(request.getParameter("complaintTitle")));
		dto.setRaisedBy(DataUtility.getString(request.getParameter("raisedBy")));
		dto.setComplaintStatus(DataUtility.getString(request.getParameter("complaintStatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		long id = DataUtility.getLong(request.getParameter("id"));

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		if (id > 0) {
			try {
				ComplaintDTO dto = model.findByPK(id);
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

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ComplaintDTO dto = (ComplaintDTO) populateDTO(request);

			try {

				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setSuccessMessage("Complaint Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Complaint Added Successfully", request);
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
				ServletUtility.setErrorMessage("Complaint Code Already Exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.COMPLAINT_VIEW;
	}
}