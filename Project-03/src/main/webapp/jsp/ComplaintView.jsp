<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.controller.ComplaintCtl"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Complaint View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

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
	<form action="<%=ORSView.COMPLAINT_CTL%>" method="post">

		<div class="row pt-3 pb-4">

			<jsp:useBean id="dto" class="com.rays.dto.ComplaintDTO"
				scope="request" />

			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (id > 0) {
						%>
						<h3 class="text-center text-primary">Update Complaint</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Complaint</h3>
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

						<!-- Complaint Code -->
						<span class="pl-sm-5"><b>Complaint Code</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-id-card grey-text"></i>
									</div>
								</div>
								<input type="text" name="complaintCode" class="form-control"
									placeholder="Enter Complaint Code"
									value="<%=DataUtility.getStringData(dto.getComplaintCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("complaintCode", request)%>
						</font><br>

						<!-- Complaint Title -->
						<span class="pl-sm-5"><b>Complaint Title</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-file text grey-text"></i>
									</div>
								</div>
								<input type="text" name="complaintTitle" class="form-control"
									placeholder="Enter Complaint Title"
									value="<%=DataUtility.getStringData(dto.getComplaintTitle())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("complaintTitle", request)%>
						</font><br>

						<!-- Raised By -->
						<span class="pl-sm-5"><b>Raised By</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user grey-text"></i>
									</div>
								</div>
								<input type="text" name="raisedBy" class="form-control"
									placeholder="Enter Raised By"
									value="<%=DataUtility.getStringData(dto.getRaisedBy())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("raisedBy", request)%>
						</font><br>
						
						<% HashMap map =(HashMap) request.getAttribute("map"); %>

						<!-- Complaint Status -->
						<span class="pl-sm-5"><b>Complaint Status</b><span
							style="color: red">*</span></span><br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-info-circle grey-text"></i>
									</div>
								</div>
							<%= HTMLUtility.getList("complaintStatus", dto.getComplaintStatus(), map) %>
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("complaintStatus", request)%>
						</font><br> <br>

						<!-- Buttons -->
						<div class="text-center">
							<%
								if (id > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=ComplaintCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=ComplaintCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=ComplaintCtl.OP_SAVE%>">
							<input type="submit" name="operation"
								class="btn btn-warning btn-md"
								value="<%=ComplaintCtl.OP_RESET%>">
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