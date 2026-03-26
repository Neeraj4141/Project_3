<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.WalletListCtl"%>
<%@page import="in.co.rays.project_3.dto.WalletDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Wallet List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}

.table-hover tbody tr:hover td {
	background-color: #0064ff36;
}
</style>
</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.WALLET_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.WalletDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			List list = ServletUtility.getList(request);
			Iterator<WalletDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-primary font-weight-bold pt-3">
				<font color="black">Wallet List</font>
			</h1>
		</center>

		<br>

		<!-- 🔥 SEARCH BAR (ADDED) -->
		<div class="row">

			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input type="text" name="walletCode" placeholder="Enter Wallet Code"
					class="form-control"
					value="<%=ServletUtility.getParameter("walletCode", request)%>">
			</div>

			&emsp;

			<div class="col-sm-2">
				<input type="text" name="userName" placeholder="Enter User Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("userName", request)%>">
			</div>

			&emsp;

			<div class="col-sm-2">
				<input type="submit" class="btn btn-primary btn-md" name="operation"
					value="<%=WalletListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark btn-md" name="operation"
					value="<%=WalletListCtl.OP_RESET%>">
			</div>

			<div class="col-sm-2"></div>

		</div>

		<br>

		<!-- Success Message -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Error Message -->
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger text-center">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- TABLE -->
		<div class="table-responsive">
			<table class="table table-bordered table-dark table-hover">

				<thead>
					<tr style="background-color: #8C8C8C;">
						<th><input type="checkbox" id="select_all"> Select
							All</th>
						<th class="text">S.NO</th>
						<th class="text">Wallet Code</th>
						<th class="text">User Name</th>
						<th class="text">Balance</th>
						<th class="text">Status</th>
						<th class="text">Edit</th>
					</tr>
				</thead>

				<tbody>

					<%
						while (it.hasNext()) {
							dto = it.next();
					%>

					<tr>
						<td align="center"><input type="checkbox" name="ids"
							value="<%=dto.getWalletId()%>"></td>

						<td align="center"><%=index++%></td>
						<td align="center"><%=dto.getWalletCode()%></td>
						<td align="center"><%=dto.getUserName()%></td>
						<td align="center"><%=dto.getBalance()%></td>
						<td align="center"><%=dto.getStatus()%></td>

						<td align="center"><a
							href="WalletCtl?walletId=<%=dto.getWalletId()%>">Edit</a></td>
					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

		<!-- BUTTONS -->
		<table width="100%">
			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-secondary" value="<%=WalletListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=WalletListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=WalletListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-secondary" value="<%=WalletListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>