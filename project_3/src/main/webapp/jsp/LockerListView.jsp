<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.LockerListCtl"%>
<%@page import="in.co.rays.project_3.dto.LockerDTO"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.PropertyReader"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Locker List View</title>

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

	```
	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.LOCKER_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.LockerDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = 0;

			if (request.getAttribute("nextListSize") != null) {
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			}

			List list = ServletUtility.getList(request);
		%>

		<center>
			<h1 class="text-primary font-weight-bold pt-3">
				<font color="black">Locker List</font>
			</h1>
		</center>

		<br>

		<!-- Search Panel -->
		<div class="container">
			<div class="row">

				<div class="col-md-3">
					<input type="text" name="lockerNumber" class="form-control"
						placeholder="Locker Number"
						value="<%=DataUtility.getStringData(dto.getLockerNumber())%>">
				</div>

				<div class="col-md-3">
					<input type="text" name="lockerType" class="form-control"
						placeholder="Locker Type"
						value="<%=DataUtility.getStringData(dto.getLockerType())%>">
				</div>

				<div class="col-md-3">
					<input type="text" name="annualFee" class="form-control"
						placeholder="Annual Fee"
						value="<%=DataUtility.getStringData(dto.getAnnualFee())%>">
				</div>

				<div class="col-md-3">
					<input type="submit" name="operation"
						class="btn btn-primary btn-md"
						value="<%=LockerListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" class="btn btn-secondary btn-md"
						value="<%=LockerListCtl.OP_RESET%>">
				</div>

			</div>
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

		<br>

		<%
			if (list != null && list.size() > 0) {
				Iterator<LockerDTO> it = list.iterator();
		%>

		<div class="table-responsive">
			<table class="table table-bordered table-dark table-hover">

				<thead>
					<tr style="background-color: #8C8C8C;">
						<th width="10%"><input type="checkbox" id="select_all">
							Select All</th>
						<th class="text">S.NO</th>
						<th class="text">Locker Number</th>
						<th class="text">Locker Type</th>
						<th class="text">Annual Fee</th>
						<th class="text">Edit</th>
					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tr>
						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>

						<td align="center"><%=index++%></td>
						<td align="center"><%=dto.getLockerNumber()%></td>
						<td align="center"><%=dto.getLockerType()%></td>
						<td align="center"><%=dto.getAnnualFee()%></td>

						<td align="center"><a
							href="LockerCtl?lockerId=<%=dto.getLockerId()%>">Edit</a></td>
					</tr>

					<%
						}
					%>
				</tbody>
			</table>
		</div>

		<!-- Pagination Buttons -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					class="btn btn-secondary btn-md"
					value="<%=LockerListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary btn-md" value="<%=LockerListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger btn-md" value="<%=LockerListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-secondary btn-md" value="<%=LockerListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h2>No Record Found</h2>
			<br> <input type="submit" name="operation"
				class="btn btn-primary btn-md" value="<%=LockerListCtl.OP_BACK%>">
		</center>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>
	```

</body>

<%@include file="FooterView.jsp"%>

</html>
