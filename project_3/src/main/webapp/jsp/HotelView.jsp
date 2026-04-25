<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.HotelCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel view</title>

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

	<div>
		<main>

		<form action="<%=ORSView.HOTEL_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HotelDTO"
				scope="request"></jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">
					<div class="card input-group-addon grad">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getFirstName() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Hotel</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Hotel</h3>
							<%
								}
							%>

							<!-- SUCCESS MESSAGE -->
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

							<!-- ERROR MESSAGE -->
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
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<div class="md-form">

								<!-- First Name -->
								<span class="pl-sm-5"><b>First Name</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="firstName"
											placeholder="First Name"
											value="<%=DataUtility.getStringData(dto.getFirstName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></br>

								<!-- Last Name -->
								<span class="pl-sm-5"><b>Last Name</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-circle grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="lastName"
											placeholder="Last Name"
											value="<%=DataUtility.getStringData(dto.getLastName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></br>

								<!-- Mobile -->
								<span class="pl-sm-5"><b>Mobile No</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-phone-square grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="mobileNo"
											placeholder="Mobile No"
											value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></br>

								<!-- Gender -->
								<span class="pl-sm-5"><b>Gender</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-venus-mars grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<%
											HashMap map = new HashMap();
											map.put("Male", "Male");
											map.put("Female", "Female");
											String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
										%>
										<%=htmlList%>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("gender", request)%></font></br>

								<!-- Room No -->
								<span class="pl-sm-5"><b>Room No</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-bed grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="roomNo"
											placeholder="Room No"
											value="<%=(dto.getRoomNo() != null && dto.getRoomNo() > 0) ? dto.getRoomNo() : ""%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("roomNo", request)%></font></br>

								<!-- Check In -->
								<span class="pl-sm-5"><b>Check In</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker" name="cheackIn"
											class="form-control" placeholder="Check In"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getCheackIn())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cheackIn", request)%></font></br>

								<!-- Check Out -->
								<span class="pl-sm-5"><b>Check Out</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker2" name="cheackOut"
											class="form-control" placeholder="Check Out"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getCheackOut())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cheackOut", request)%></font></br>

								<br>

								<div class="text-center">
									<%
										if (dto.getFirstName() != null && dto.getId() > 0) {
									%>
									<input type="submit" name="operation"
										class="btn btn-success btn-md" value="<%=HotelCtl.OP_UPDATE%>">
									<input type="submit" name="operation"
										class="btn btn-warning btn-md" value="<%=HotelCtl.OP_CANCEL%>">
									<%
										} else {
									%>
									<input type="submit" name="operation"
										class="btn btn-success btn-md" value="<%=HotelCtl.OP_SAVE%>">
									<input type="submit" name="operation"
										class="btn btn-warning btn-md" value="<%=HotelCtl.OP_RESET%>">
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
		</main>
	</div>

	<%@include file="FooterView.jsp"%>

</body>
</html>