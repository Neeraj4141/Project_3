<%@page import="in.co.rays.project_3.dto.EmployeeRoleDTO"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.model.EmployeeRoleModelInt"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.EmployeeListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.dto.EmployeeDTO"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Employee List</title>

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

	<form class="pb-5" action="<%=ORSView.EMPLOYEE_LIST_CTL%>"
		method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EmployeeDTO"
			scope="request" />

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			EmployeeRoleModelInt emodel = ModelFactory.getInstance().getEmployeeRoleModel();
			List list = ServletUtility.getList(request);
			Iterator<EmployeeDTO> it = list.iterator();

			if (list.size() != 0) {
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Employee List</u>
			</h1>
		</center>

		<!-- SUCCESS -->
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

		<!-- ERROR -->
		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getErrorMessage(request).equals("")) {
			%>

			<div class="col-md-4 alert alert-danger alert-dismissible">
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

		<!-- SEARCH BAR SAME -->
		<div class="row">

			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input type="text" name="employeeName"
					placeholder="Enter Employee Name" class="form-control"
					value="<%=ServletUtility.getParameter("employeeName", request)%>">
			</div>

			&emsp;

			<div class="col-sm-2">
				<input type="text" name="email" placeholder="Enter Email"
					class="form-control"
					value="<%=ServletUtility.getParameter("email", request)%>">
			</div>

			&emsp;

			<div class="col-sm-2">
				<input type="text" name="employeeCode"
					placeholder="Enter Employee Code" class="form-control"
					value="<%=ServletUtility.getParameter("employeeCode", request)%>">
			</div>

			&emsp;

			<div class="col-sm-2">
				<input type="submit" class="btn btn-primary btn-md"
					style="font-size: 15px" name="operation"
					value="<%=EmployeeListCtl.OP_SEARCH%>"> &emsp; <input
					type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
					name="operation" value="<%=EmployeeListCtl.OP_RESET%>">
			</div>

			<div class="col-sm-1"></div>

		</div>

		<br>

		<!-- TABLE SAME -->
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">

				<thead>
					<tr style="background-color: #f79d65; font-size: 18px;">

						<th width="10%"><input type="checkbox" id="select_all">
							Select All</th>
						<th width="5%" class="text">S.NO</th>
						<th width="15%" class="text">Name</th>
						<th width="15%" class="text">Email</th>
						<th width="15%" class="text">Address</th>
						<th width="10%" class="text">Code</th>
						<th width="10%" class="text">Role</th>
						<th width="10%" class="text">Gender</th>
						<th width="10%" class="text">Joining Date</th>
						<th width="5%" class="text">Edit</th>

					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
								dto = it.next();
								EmployeeRoleDTO rdto = emodel.findByPK(dto.getRole());
					%>

					<tr style="font-weight: bold;">

						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>

						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getEmployeeName()%></td>
						<td class="text"><%=dto.getEmail()%></td>
						<td class="text"><%=dto.getAddress()%></td>
						<td class="text"><%=dto.getEmployeeCode()%></td>
						<td class="text"><%=rdto.getRoleName()%></td>
						<td class="text"><%=dto.getGender()%></td>
						<td class="text"><%=DataUtility.getDateString(dto.getJoiningDate())%></td>

						<td class="text"><a href="EmployeeCtl?id=<%=dto.getId()%>">Edit</a>
						</td>

					</tr>

					<%
						}
					%>
				</tbody>

			</table>
		</div>

		<!-- PAGINATION SAME -->
		<table width="100%">
			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-warning btn-md"
					value="<%=EmployeeListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary btn-md" value="<%=EmployeeListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger btn-md"
					value="<%=EmployeeListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning btn-md" value="<%=EmployeeListCtl.OP_NEXT%>"
					<%=nextPageSize != 0 ? "" : "disabled"%>></td>

			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h1>Employee List</h1>
		</center>

		<div class="row">
			<div class="col-md-4"></div>
			<div class=" col-md-4 alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				</h4>
			</div>

			<div class="col-md-4"></div>
		</div>

		<div style="padding-left: 48%;">
			<input type="submit" name="operation" class="btn btn-primary btn-md"
				value="<%=EmployeeListCtl.OP_BACK%>">
		</div>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>