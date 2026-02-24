<%@page import="com.rays.controller.LeaveManagmentCtl"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Management View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.LEAVE_MANAGMENT_CTL%>" method="post">

		<div class="row pt-3 pb-4">

			<jsp:useBean id="dto" class="com.rays.dto.LeaveManagmentDTO"
				scope="request" />

			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (id > 0) {
						%>
						<h3 class="text-center text-primary">Update Leave</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Leave</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<h4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Error Message -->
						<h4 align="center">
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Hidden Fields -->
						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<!-- Leave Code -->
						<span class="pl-sm-5"><b>Leave Code</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" name="leaveCode" class="form-control"
									placeholder="Enter Leave Code"
									value="<%=DataUtility.getStringData(dto.getLeaveCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("leaveCode", request)%>
						</font><br>

						<!-- Employee Name -->
						<span class="pl-sm-5"><b>Employee Name</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" name="employeeName" class="form-control"
									placeholder="Enter Employee Name"
									value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("employeeName", request)%>
						</font><br>

						<!-- Leave Start Date -->
						<span class="pl-sm-5"><b>Leave Start Date</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" id="datepicker2" name="leaveStartDate" class="form-control" readonly="readonly"
									placeholder="Select Date"
									value="<%=DataUtility.getStringData(dto.getLeaveStartDate())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("leaveStartDate", request)%>
						</font><br>

						<!-- Leave End Date -->
						<span class="pl-sm-5"><b>Leave End Date</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" id="datepicker" name="leaveEndDate" class="form-control" readonly="readonly"
									placeholder="Select Date"
									value="<%=DataUtility.getStringData(dto.getLeaveEndDate())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("leaveEndDate", request)%>
						</font><br>

						<!-- Leave Status -->
						<span class="pl-sm-5"><b>Leave Status</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" name="leaveStatus" class="form-control"
									placeholder="Enter Leave Status"
									value="<%=DataUtility.getStringData(dto.getLeaveStatus())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("leaveStatus", request)%>
						</font><br> <br>

						<!-- Buttons -->
						<div class="text-center">
							<%
								if (id > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=LeaveManagmentCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=LeaveManagmentCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=LeaveManagmentCtl.OP_SAVE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=LeaveManagmentCtl.OP_RESET%>">
							<%
								}
							%>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4"></div>

		</div>
	</form>
	</main>

	<%@include file="FooterView.jsp"%>

</body>
</html>