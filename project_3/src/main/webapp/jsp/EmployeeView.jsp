<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.EmployeeCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
	background-repeat: no-repeat;
	background-size: cover;
	padding-top: 75px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
}
</style>
</head>

<body class="hm">

	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>

	<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EmployeeDTO"
			scope="request" />

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card grad">
					<div class="card-body">
						<%
							List list = (List) request.getAttribute("roleList");
						%>


						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Employee</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Employee</h3>
						<%
							}
						%>

						<!-- Success -->
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

						<!-- Error -->
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

						<!-- Employee Name -->
						<span class="pl-sm-5"><b>Employee Name</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="employeeName"
									placeholder="Employee Name"
									value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("employeeName", request)%></font></br>

						<!-- Address -->
						<span class="pl-sm-5"><b>Address</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-home grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="address"
									placeholder="Address Name"
									value="<%=DataUtility.getStringData(dto.getAddress())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("address", request)%></font></br>

						<!-- Email -->
						<span class="pl-sm-5"><b>Email Id</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" class="form-control" id="defaultForm-email"
									name="email" placeholder="Email Id"
									value="<%=DataUtility.getStringData(dto.getEmail())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("email", request)%></font></br>

						<!-- Employee Code -->
						<span class="pl-sm-5"><b>Employee Code</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" class="form-control" id="defaultForm-email"
									name="employeeCode" placeholder="Employee code"
									value="<%=DataUtility.getStringData(dto.getEmployeeCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("employeeCode", request)%></font></br>


						<!-- Role -->
						<span class="pl-sm-5"><b>Role</b><span style="color: red;">*</span></span></br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<%=HTMLUtility.getList("role", String.valueOf(dto.getRole()), list)%>
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("role", request)%></font></br>

						<!-- Gender -->
						<span class="pl-sm-5"><b>Gender</b><span
							style="color: red;">*</span></span> </br>

						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-venus-mars grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>

								<%
									HashMap map = new HashMap();
									map.put("Male", "Male");
									map.put("Female", "Female");

									String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
								%>
								<%=htmlList%></div>

						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("gender", request)%></font></br>

						<!-- Joining Date -->
						<span class="pl-sm-5"><b>Joining Date</b> <span
							style="color: red;">*</span></span></br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" id="datepicker2" name="joiningDate"
									class="form-control" placeholder="Joining Date"
									readonly="readonly" style="background-color: white;"<%=DataUtility.getDateString(dto.getJoiningDate())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("joiningDate", request)%></font></br>

						<br>

						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" class="btn btn-success" name="operation"
								value="<%=EmployeeCtl.OP_UPDATE%>"> <input type="submit"
								class="btn btn-warning" name="operation"
								value="<%=EmployeeCtl.OP_CANCEL%>">
							<%
								} else {
							%>

							<input type="submit" class="btn btn-success" name="operation"
								value="<%=EmployeeCtl.OP_SAVE%>"> <input type="submit"
								class="btn btn-warning" name="operation"
								value="<%=EmployeeCtl.OP_RESET%>">
							<%
								}
							%>

						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>