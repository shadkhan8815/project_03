package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.dto.BaseDTO;
import com.rays.dto.LeaveManagmentDTO;
import com.rays.exception.ApplicationException;
import com.rays.model.LeaveManagmentInt;
import com.rays.model.ModelFactory;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;
import com.rays.controller.ORSView;

@WebServlet(urlPatterns = { "/ctl/LeaveManagmentListCtl" })
public class LeaveManagementListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(LeaveManagementListCtl.class);

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request) {

        LeaveManagmentInt model = ModelFactory.getInstance().getLeaveManagmentModel();

        try {
            List leave = model.list();
            request.setAttribute("leave", leave);
        } catch (Exception e) {
            log.error(e);
        }
    }

    // ================= POPULATE DTO =================
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        LeaveManagmentDTO dto = new LeaveManagmentDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setLeaveCode(DataUtility.getString(request.getParameter("leaveCode")));
        dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
        dto.setLeaveStartDate(DataUtility.getDate(request.getParameter("leaveStartDate")));
        dto.setLeaveEndDate(DataUtility.getDate(request.getParameter("leaveEndDate")));
        dto.setLeaveStatus(DataUtility.getString(request.getParameter("leaveStatus")));

        populateBean(dto, request);

        return dto;
    }

    // ================= DO GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        LeaveManagmentDTO dto = (LeaveManagmentDTO) populateDTO(request);
        LeaveManagmentInt model = ModelFactory.getInstance().getLeaveManagmentModel();

        try {

            List list = model.search(dto, pageNo, pageSize);
            List nextList = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (nextList == null || nextList.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", nextList.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (Exception e) {
            log.error("Database Error", e);
            ServletUtility.handleListDBDown(getView(), dto, pageNo, pageSize, request, response);
        }
    }

    // ================= DO POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list;
        List nextList;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        LeaveManagmentDTO dto = (LeaveManagmentDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        LeaveManagmentInt model = ModelFactory.getInstance().getLeaveManagmentModel();

        try {

            // ===== SEARCH / NEXT / PREVIOUS =====
            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
            }

            // ===== NEW =====
            else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.LEAVE_MANAGMENT_CTL, request, response);
                return;
            }

            // ===== RESET =====
            else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.LEAVE_MANAGMENT_LIST_CTL, request, response);
                return;
            }

            // ===== DELETE =====
            else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    LeaveManagmentDTO deleteDto = new LeaveManagmentDTO();

                    for (String id : ids) {
                        deleteDto.setId(DataUtility.getLong(id));
                        model.delete(deleteDto);
                    }

                    ServletUtility.setSuccessMessage("Record Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            // ===== SEARCH RESULT LOAD =====
            list = model.search(dto, pageNo, pageSize);
            nextList = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                if (!OP_DELETE.equalsIgnoreCase(op)) {
                    ServletUtility.setErrorMessage("No record found", request);
                }
            }

            if (nextList == null || nextList.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", nextList.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setDto(dto, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    // ================= VIEW =================
    @Override
    protected String getView() {
        return ORSView.LEAVE_MANAGMENT_LIST_VIEW;
    }
}