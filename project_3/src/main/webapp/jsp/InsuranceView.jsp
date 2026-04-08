<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.InsuranceCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Insurance view</title>
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

	```
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<form action="<%=ORSView.INSURANCE_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.InsuranceDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							if (dto.getInsuranceCode() != null && dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Insurance</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Insurance</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>
						<div class="alert alert-success alert-dismissible text-center">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>
						<%
							}
						%>

						<!-- Error Message -->
						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>
						<div class="alert alert-danger alert-dismissible text-center">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<%=ServletUtility.getErrorMessage(request)%>
						</div>
						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- INSURANCE CODE -->
						<span class="pl-sm-5"><b>Insurance Code *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="insuranceCode"
									placeholder="Enter Insurance Code"
									value="<%=DataUtility.getStringData(dto.getInsuranceCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("insuranceCode", request)%>
						</font><br>

						<!-- POLICY HOLDER -->
						<span class="pl-sm-5"><b>Policy Holder *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="policyHolder"
									placeholder="Enter Policy Holder Name"
									value="<%=DataUtility.getStringData(dto.getPolicyHolder())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("policyHolder", request)%>
						</font><br>

						<!-- PREMIUM AMOUNT -->
						<span class="pl-sm-5"><b>Premium Amount *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="premiumAmount"
									placeholder="Enter Premium Amount"
									value="<%=(dto.getPremiumAmount() == 0) ? "" : dto.getPremiumAmount()%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("premiumAmount", request)%>
						</font><br>

						<!-- STATUS -->
						<span class="pl-sm-5"><b>Status *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="status"
									placeholder="Enter Status"
									value="<%=DataUtility.getStringData(dto.getStatus())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
						</font><br>

						<!-- BUTTONS -->
						<div class="text-center">
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=InsuranceCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=InsuranceCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=InsuranceCtl.OP_SAVE%>">

							<input type="submit" name="operation"
								class="btn btn-warning btn-md"
								value="<%=InsuranceCtl.OP_RESET%>">
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
	```

</body>
</html>
