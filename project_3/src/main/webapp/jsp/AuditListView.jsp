<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.dto.AuditDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.AuditListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Audit List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

	<div>
		<%@include file="Header.jsp"%>
	</div>

	<div>
		<form action="<%=ORSView.AUDIT_LIST_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AuditDTO"
				scope="request"></jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<AuditDTO> it = list.iterator();
			%>

			<%
				if (list.size() != 0) {
			%>

			<center>
				<h1 class="text-light font-weight-bold pt-2">
					<font color="black">Audit List</font>
				</h1>
			</center>

			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>

				<div class="col-md-4"></div>
			</div>

			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>

				<div class="col-md-4"></div>
			</div>

			<!-- SEARCH FIELDS -->
			<div class="row">
				<div class="col-sm-3">
					<input type="text" name="auditCode" class="form-control"
						placeholder="Audit Code"
						value="<%=DataUtility.getStringData(dto.getAuditCode())%>">
				</div>

				<div class="col-sm-3">
					<input type="text" name="performedBy" class="form-control"
						placeholder="Performed By"
						value="<%=DataUtility.getStringData(dto.getPerformedBy())%>">
				</div>

				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						name="operation" value="<%=AuditListCtl.OP_SEARCH%>"> <input
						type="submit" class="btn btn-dark btn-md" name="operation"
						value="<%=AuditListCtl.OP_RESET%>">
				</div>
			</div>

			</br>

			<!-- TABLE -->
			<div class="table-responsive">
				<table class="table table-dark table-bordered">
					<thead>
						<tr style="background-color: #8C8C8C;">
							<th><input type="checkbox" id="select_all"> Select
								All</th>
							<th class="text">S.NO</th>
							<th class="text">Audit Code</th>
							<th class="text">Performed By</th>
							<th class="text">Action</th>
							<th class="text">Status</th>
							<th class="text">Edit</th>
						</tr>
					</thead>

					<%
						while (it.hasNext()) {
								dto = it.next();
					%>
					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>
							<td align="center"><%=index++%></td>
							<td align="center"><%=dto.getAuditCode()%></td>
							<td align="center"><%=dto.getPerformedBy()%></td>
							<td align="center"><%=dto.getAction()%></td>
							<td align="center"><%=dto.getStatus()%></td>
							<td align="center"><a href="AuditCtl?id=<%=dto.getId()%>">Edit</a>
							</td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>

			<!-- PAGINATION -->
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-secondary" value="<%=AuditListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary" value="<%=AuditListCtl.OP_NEW%>">
					</td>

					<td><input type="submit" name="operation"
						class="btn btn-danger" value="<%=AuditListCtl.OP_DELETE%>">
					</td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-secondary" value="<%=AuditListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				} else {
			%>

			<center>
				<h1 class="text-primary">Audit List</h1>
			</center>

			<br>

			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-danger">
					<%=ServletUtility.getErrorMessage(request)%>
				</div>
				<%
					}
				%>

				<div class="col-md-4"></div>
			</div>

			<br>

			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary"
					value="<%=AuditListCtl.OP_BACK%>">
			</div>

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