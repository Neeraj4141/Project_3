<%@page import="in.co.rays.project_3.controller.OTPVerificationCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OTP Verification</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
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
}
</style>
</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<form action="<%=ORSView.OTPVERIFICATION_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.OTPVerificationDTO" scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update OTP</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add OTP</h3>
						<%
							}
						%>

						<!-- Messages -->
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

						<!-- Hidden fields -->
						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<div class="md-form">

							<!-- OTP ID -->
							<span><b>OTP ID</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-id-card"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="otpId"
									value="<%=DataUtility.getStringData(dto.getOtpId())%>" placeholder="enter your id">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("otpid", request)%>
							</font><br>

							<!-- OTP Code -->
							<span><b>OTP Code *</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-key"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="otpCode"
									value="<%=DataUtility.getStringData(dto.getOtpCode())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("otpCode", request)%>
							</font><br>

							<!-- OTP Value -->
							<span><b>OTP Value *</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-lock"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="otpValue"
									value="<%=DataUtility.getStringData(dto.getOtpValue())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("otpValue", request)%>
							</font><br>

							<!-- Expiry Time -->
							<span><b>Expiry Time *</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
								<input type="text" id="datepicker2" class="form-control"
									name="expiryTime" readonly="readonly"
									value="<%=DataUtility.getDateString(dto.getExpiryTime())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("expiryTime", request)%>
							</font><br>

							<!-- OTP Status -->
							<span><b>OTP Status *</b></span>
							<div class="input-group">
								<input type="text" class="form-control" name="otpStatus"
									value="<%=DataUtility.getStringData(dto.getOtpStatus())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("otpStatus", request)%>
							</font><br>

							<!-- Buttons -->
							<div class="text-center">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=OTPVerificationCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=OTPVerificationCtl.OP_CANCEL%>">
								<%
									} else {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=OTPVerificationCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=OTPVerificationCtl.OP_RESET%>">
								<%
									}
								%>

							</div>

						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4"></div>
		</div>

	</form>

</body>

<%@include file="FooterView.jsp"%>

</html>