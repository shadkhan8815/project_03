package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.ComplaintDTO;
import com.rays.exception.ApplicationException;
import com.rays.model.ComplaintModelInt;
import com.rays.model.ModelFactory;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

@WebServlet(name = "ComplaintListCtl", urlPatterns = { "/ctl/ComplaintListCtl" })
public class ComplaintListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ComplaintListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		try {
			List list = model.list();
			request.setAttribute("complaintList", list);
		} catch (Exception e) {
			log.error(e);
		}
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

	/**
	 * Display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("ComplaintListCtl doGet Start");

		List list;
		List next;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ComplaintDTO dto = (ComplaintDTO) populateDTO(request);

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		try {
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list != null && list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			log.error("Database Error", e);
			e.printStackTrace();
			ServletUtility.handleListDBDown(getView(), dto, pageNo, pageSize, request, response);
			return;
		}

		log.debug("ComplaintListCtl doGet End");
	}

	/**
	 * Submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("ComplaintListCtl doPost Start");

		List list;
		List next;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		ComplaintDTO dto = (ComplaintDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COMPLAINT_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {
					ComplaintDTO deleteDto = new ComplaintDTO();

					for (String id : ids) {
						deleteDto.setId(DataUtility.getLong(id));
						model.delete(deleteDto);
					}
					ServletUtility.setSuccessMessage("Complaint Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
				return;
			}

			dto = (ComplaintDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found", request);
				}
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("ComplaintListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.COMPLAINT_LIST_VIEW;
	}
}