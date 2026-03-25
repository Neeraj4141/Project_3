<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.OTPVerificationListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OTP Verification List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
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

	<form action="<%=ORSView.OTPVERIFICATION_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.OTPVerificationDTO" scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<in.co.rays.project_3.dto.OTPVerificationDTO> it = list.iterator();
		%>

		<%
			if (list.size() != 0) {
		%>

		<center>
			<h1>
				<u>OTP Verification List</u>
			</h1>
		</center>

		<!-- Messages -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger text-center">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Search -->
		<div class="row">
			<div class="col-sm-3">
				<input type="text" name="otpCode" placeholder="Enter OTP Code"
					class="form-control"
					value="<%=ServletUtility.getParameter("otpCode", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="text" name="otpValue" placeholder="Enter OTP Value"
					class="form-control"
					value="<%=ServletUtility.getParameter("otpValue", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="text" name="otpStatus" placeholder="Enter OTP Status"
					class="form-control"
					value="<%=ServletUtility.getParameter("otpStatus", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=OTPVerificationListCtl.OP_SEARCH%>"> <input
					type="submit" class="btn btn-dark" name="operation"
					value="<%=OTPVerificationListCtl.OP_RESET%>">
			</div>
		</div>

		<br>

		<!-- Table -->
		<table class="table table-bordered table-hover">
			<tr style="background-color: #f79d65;">
				<th><input type="checkbox" id="select_all"> Select</th>
				<th>S.NO</th>
				<th>OTP ID</th>
				<th>OTP Code</th>
				<th>OTP Value</th>
				<th>OTP Status</th>
				<th>Expiry Time</th>
				<th>Edit</th>
			</tr>

			<%
				while (it.hasNext()) {
						dto = it.next();
			%>

			<tr>
				<td align="center"><input type="checkbox" name="ids"
					value="<%=dto.getId()%>"></td>

				<td><%=index++%></td>
				<td><%=dto.getOtpId()%></td>
				<td><%=dto.getOtpCode()%></td>
				<td><%=dto.getOtpValue()%></td>
				<td><%=dto.getOtpStatus()%></td>
				<td><%=DataUtility.getDateString(dto.getExpiryTime())%></td>

				<td><a href="OTPVerificationCtl?id=<%=dto.getId()%>">Edit</a></td>
			</tr>

			<%
				}
			%>

		</table>

		<!-- Buttons -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					value="<%=OTPVerificationListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					value="<%=OTPVerificationListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					value="<%=OTPVerificationListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" name="operation"
					value="<%=OTPVerificationListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h2>No Record Found</h2>
		</center>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@include file="FooterView.jsp"%>
</html>