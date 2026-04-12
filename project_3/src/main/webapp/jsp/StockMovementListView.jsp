<%@page import="in.co.rays.project_3.dto.StockMovementDTO"%>
<%@page import="java.util.Iterator"%>

<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.StockMovementListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Stock Movement List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.text {
	text-align: center;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.STOCKMOVEMENT_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.StockMovementDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			List list = ServletUtility.getList(request);
			Iterator<StockMovementDTO> it = list.iterator();
		%>

		<center>
			<h1>Stock Movement List</h1>
		</center>

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

		<!-- Search Fields -->
		<div class="row text-center">

			<div class="col-sm-3">
				<input type="text" name="movementCode" class="form-control"
					placeholder="Movement Code"
					value="<%=DataUtility.getStringData(dto.getMovementCode())%>">
			</div>

			<div class="col-sm-3">
				<input type="text" name="productName" class="form-control"
					placeholder="Product Name"
					value="<%=DataUtility.getStringData(dto.getProductName())%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="status" class="form-control"
					placeholder="Status"
					value="<%=DataUtility.getStringData(dto.getStatus())%>">
			</div>

			<div class="col-sm-2">
				<input type="submit" name="operation" class="btn btn-primary"
					value="<%=StockMovementListCtl.OP_SEARCH%>"> <input
					type="submit" name="operation" class="btn btn-dark"
					value="<%=StockMovementListCtl.OP_RESET%>">
			</div>
		</div>

		<br>

		<!-- Table -->
		<table class="table table-dark table-bordered">

			<thead>
				<tr>
					<th><input type="checkbox" id="select_all"> Select All</th>
					<th>S.NO</th>
					<th>Movement Code</th>
					<th>Product Name</th>
					<th>Quantity</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>
			</thead>

			<tbody>
				<%
					while (it.hasNext()) {
						dto = it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids"
						value="<%=dto.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=dto.getMovementCode()%></td>
					<td align="center"><%=dto.getProductName()%></td>
					<td align="center"><%=dto.getQuantity()%></td>
					<td align="center"><%=dto.getStatus()%></td>
					<td align="center"><a
						href="StockMovementCtl?id=<%=dto.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</tbody>

		</table>

		<!-- Pagination Buttons -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					class="btn btn-secondary"
					value="<%=StockMovementListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=StockMovementListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=StockMovementListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-secondary" value="<%=StockMovementListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>