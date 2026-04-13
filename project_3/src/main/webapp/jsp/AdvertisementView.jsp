<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.AdvertisementCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Advertisement view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
	background-repeat: no-repeat;
	background-size: 100%;
}
</style>
</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<form action="<%=ORSView.ADVERTISEMENT_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.AdvertisementDTO" scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							if (dto.getAdName() != null && dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Advertisement</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Advertisement</h3>
						<%
							}
						%>

						<H4 align="center">
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
						</H4>

						<H4 align="center">
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
						</H4>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- AD CODE -->
						<span class="pl-sm-5"><b>Ad Code</b> <span
							style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-key"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="adCode"
									placeholder="Ad Code"
									value="<%=DataUtility.getStringData(dto.getAdCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("adCode", request)%>
						</font><br>

						<!-- AD NAME -->
						<span class="pl-sm-5"><b>Ad Name</b> <span
							style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="adName"
									placeholder="Ad Name"
									value="<%=DataUtility.getStringData(dto.getAdName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("adName", request)%>
						</font><br>

						<!-- BUDGET -->
						<span class="pl-sm-5"><b>Budget</b> <span
							style="color: red;">*</span></span>

						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-wallet"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="budget"
									placeholder="Budget"
									value="<%=dto.getBudget() > 0 ? dto.getBudget() : ""%>">

							</div>
						</div>

						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("budget", request)%>
						</font><br>

						<!-- STATUS -->
						<span class="pl-sm-5"><b>Status</b> <span
							style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-info-circle"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="status"
									placeholder="Status"
									value="<%=DataUtility.getStringData(dto.getStatus())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
						</font><br>

						<!-- BUTTON -->
						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=AdvertisementCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning"
								value="<%=AdvertisementCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=AdvertisementCtl.OP_SAVE%>"> <input
								type="submit" name="operation" class="btn btn-warning"
								value="<%=AdvertisementCtl.OP_RESET%>">
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

	<%@include file="FooterView.jsp"%>

</body>
</html>