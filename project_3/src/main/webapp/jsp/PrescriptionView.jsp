<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.PrescriptionCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Prescription view</title>
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

	<form action="<%=ORSView.PRESCRIPTION_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PrescriptionDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));

							if (dto.getPatientName() != null && dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Prescription</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Prescription</h3>
						<%
							}
						%>

						<!-- Messages -->
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

						<!-- PRESCRIPTION NO -->
						<span class="pl-sm-5"><b>Prescription No</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="prescriptionNo"
									value="<%=DataUtility.getStringData(dto.getPrescriptionNo())%>">
							</div>
						</div>

						<!-- PATIENT NAME -->
						<span class="pl-sm-5"><b>Patient Name *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="patientName"
									value="<%=DataUtility.getStringData(dto.getPatientName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("patientName", request)%>
						</font></br>

						<!-- DOCTOR NAME -->
						<span class="pl-sm-5"><b>Doctor Name *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control" name="doctorName"
									value="<%=DataUtility.getStringData(dto.getDoctorName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("doctorName", request)%>
						</font></br>

						<!-- PRESCRIBED DATE -->
						<span class="pl-sm-5"><b>Prescribed Date *</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-calendar grey-text"></i>
									</div>
								</div>

								<input type="text" id="datepicker2" name="prescribedDate"
									class="form-control" readonly="readonly"
									style="background-color: white;"
									value="<%=DataUtility.getDateString(dto.getPrescribedDate())%>">

							</div>
						</div>

						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("prescribedDate", request)%>
						</font></br>

						<!-- BUTTON -->
						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=PrescriptionCtl.OP_UPDATE%>">

							<input type="submit" name="operation"
								class="btn btn-warning btn-md" value="<%=PrescriptionCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=PrescriptionCtl.OP_SAVE%>">

							<input type="submit" name="operation"
								class="btn btn-warning btn-md" value="<%=PrescriptionCtl.OP_RESET%>">
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