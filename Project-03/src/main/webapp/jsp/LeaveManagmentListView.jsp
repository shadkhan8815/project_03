<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="com.rays.controller.LeaveManagementListCtl"%>
<%@page import="com.rays.dto.LeaveManagmentDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Leave Management List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}
</style>
</head>

<%@include file="Header.jsp"%>

<body class="hm">

	<div>
		<form class="pb-5" action="<%=ORSView.LEAVE_MANAGMENT_LIST_CTL%>"
			method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = 0;
				if (request.getAttribute("nextListSize") != null) {
					nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				}

				List list = ServletUtility.getList(request);
				if (list == null) {
					list = new java.util.ArrayList();
				}

				Iterator<LeaveManagmentDTO> it = list.iterator();
			%>

			<center>
				<h1 class="text-dark font-weight-bold pt-3">
					<u>Leave Management List</u>
				</h1>
			</center>

			<!-- Success Message -->
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
						</font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<!-- Error Message -->
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
						</font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<%
				if (list.size() != 0) {
			%>
			<br>
			<!-- Search Panel -->
			<div class="row">

				<div class="col-sm-3"></div>

				<!-- Employee Name Search -->
				<div class="col-sm-2">
					<input type="text" name="employeeName"
						placeholder="Enter Employee Name" class="form-control"
						value="<%=ServletUtility.getParameter("employeeName", request)%>">
				</div>
				
				<div class="col-sm-3">
					<input type="submit" class="btn btn-primary btn-md"
						name="operation" value="<%=LeaveManagementListCtl.OP_SEARCH%>"> <input
						type="submit" class="btn btn-dark btn-md" name="operation"
						value="<%=LeaveManagementListCtl.OP_RESET%>">
				</div>

				<div class="col-sm-2"></div>
				
				<br>
				<br>

				<!-- Table -->
				<div class="table-responsive">
					<table class="table table-bordered table-dark table-hover">
						<thead>
							<tr style="background-color: #8C8C8C;">
								<th width="10%"><input type="checkbox" id="select_all">
									Select All</th>
								<th class="text">S.No</th>
								<th class="text">Leave Code</th>
								<th class="text">Employee Name</th>
								<th class="text">Start Date</th>
								<th class="text">End Date</th>
								<th class="text">Status</th>
								<th class="text">Edit</th>
							</tr>
						</thead>

						<tbody>
							<%
								while (it.hasNext()) {
										LeaveManagmentDTO dto = it.next();
							%>
							<tr>
								<td align="center"><input type="checkbox" class="checkbox"
									name="ids" value="<%=dto.getId()%>"></td>

								<td class="text"><%=index++%></td>
								<td class="text"><%=dto.getLeaveCode()%></td>
								<td class="text"><%=dto.getEmployeeName()%></td>
								<td class="text"><%=DataUtility.getDateString(dto.getLeaveStartDate())%></td>
								<td class="text"><%=DataUtility.getDateString(dto.getLeaveEndDate())%></td>
								<td class="text"><%=dto.getLeaveStatus()%></td>
								<td class="text"><a
									href="LeaveManagementCtl?id=<%=dto.getId()%>">Edit</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>

				<!-- Buttons -->
				<table width="100%">
					<tr>
						<td><input type="submit" name="operation"
							class="btn btn-warning btn-md"
							value="<%=LeaveManagementListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>

						<td><input type="submit" name="operation"
							class="btn btn-primary btn-md"
							value="<%=LeaveManagementListCtl.OP_NEW%>"></td>

						<td><input type="submit" name="operation"
							class="btn btn-danger btn-md"
							value="<%=LeaveManagementListCtl.OP_DELETE%>"></td>

						<td align="right"><input type="submit" name="operation"
							class="btn btn-warning btn-md"
							value="<%=LeaveManagementListCtl.OP_NEXT%>"
							<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
					</tr>
				</table>

				<%
					}
				%>

				<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
					type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</div>

</body>
<%@include file="FooterView.jsp"%>
</html>