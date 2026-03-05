<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.LockerCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Locker View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
	background-repeat: no-repeat;
	background-size: 100%;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}
</style>

</head>

<body class="p4">

	```
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.LockerDTO"
		scope="request"></jsp:useBean>

	<main>
	<form action="<%=ORSView.LOCKER_CTL%>" method="post">

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("lockerId"));
							if (dto.getLockerId() != null && dto.getLockerId() > 0) {
						%>
						<h3 class="text-center text-primary font-weight-bold">Update
							Locker</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary font-weight-bold">Add
							Locker</h3>
						<%
							}
						%>

						<!-- Success Message -->
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

						<!-- Error Message -->
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

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Locker Number -->
						<label><b>Locker Number</b><span style="color: red;">*</span></label>
						<input type="text" name="lockerNumber" class="form-control"
							placeholder="Enter Locker Number"
							value="<%=DataUtility.getStringData(dto.getLockerNumber())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("lockerNumber", request)%>
						</font> <br>

						<!-- Annual Fee -->
						<label><b>Annual Fee</b><span style="color: red;">*</span></label>
						<input type="text" name="annualFee" class="form-control"
							placeholder="Enter Annual Fee"
							value="<%=DataUtility.getStringData(dto.getAnnualFee())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("annualFee", request)%>
						</font> <br>

						<!-- Locker Type Dropdown -->
						<span class="pl-sm-5"><b>Locker Type</b><span
							style="color: red;">*</span></span> </br>

						<div class="col-sm-12">
							<div class="input-group">

								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-lock grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>

								<%
									HashMap map = new HashMap();

									map.put("small", "Small Locker");
									map.put("medium", "Medium Locker");
									map.put("large", "Large Locker");
									map.put("premium", "Premium Locker");

									String htmlList = HTMLUtility.getList("lockerType", dto.getLockerType(), map);
								%>

								<%=htmlList%>

							</div>
						</div>

						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("lockerType", request)%>
						</font> <br>

						<%
							if (id > 0) {
						%>
						<div align="center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=LockerCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=LockerCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>

						<div class="text-center">


							<input type="submit" name="operation"
								class="btn btn-success btn-md" style="font-size: 17px"
								value="<%=LockerCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning btn-md"
								style="font-size: 17px" value="<%=LockerCtl.OP_RESET%>">

						</div>

						<%
							}
						%>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>
	</main>
	```

</body>
<%@include file="FooterView.jsp"%>
</html>
