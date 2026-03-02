<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ResturentCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resturent View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.log1 { padding-top: 3%; }
.input-group-addon{ box-shadow: 9px 8px 7px #001a33; }
.p4{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat: no-repeat;
background-attachment: fixed; 
background-size: cover;
padding-top: 85px;
}
</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<div>
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ResturentDTO"
			scope="request"></jsp:useBean>

		<main>
		<form action="<%=ORSView.RESTURENT_CTL%>" method="post">

			<div class="row pt-3">
				<div class="col-md-4"></div>

				<div class="col-md-4 ">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (dto.getId() != null) {
							%>
							<h3 class="text-center text-primary font-weight-bold">Update Resturent</h3>
							<% } else { %>
							<h3 class="text-center text-primary font-weight-bold">Add Resturent</h3>
							<% } %>

							<div>
								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<% } %>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<% } %>
								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
								<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
								<input type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>

							<div class="md-form input-group-addon">

								<!-- Resturent Name -->
								<span class="pl-sm-5"><b>Resturent Name</b><span style="color:red">*</span></span></br>
								<div class="col-sm-12">
									<input type="text" name="restaurantName" class="form-control"
										placeholder="Enter Resturent Name"
										value="<%=DataUtility.getStringData(dto.getRestaurantName())%>">
								</div>
								<font color="red" class="pl-sm-5">
									<%=ServletUtility.getErrorMessage("restaurantName", request)%></font></br>

								<!-- Location -->
								<span class="pl-sm-5"><b>Location</b><span style="color:red">*</span></span></br>
								<div class="col-sm-12">
									<input type="text" name="location" class="form-control"
										placeholder="Enter Location"
										value="<%=DataUtility.getStringData(dto.getLocation())%>">
								</div>
								<font color="red" class="pl-sm-5">
									<%=ServletUtility.getErrorMessage("location", request)%></font></br>

								<!-- Cuisine Type Dropdown (Static Preload) -->
								<span class="pl-sm-5"><b>Cuisine Type</b><span style="color:red">*</span></span></br>
								<div class="col-sm-12">
									<%
										HashMap map = new HashMap();
										map.put("Indian", "Indian");
										map.put("Chinese", "Chinese");
										map.put("Italian", "Italian");
										map.put("South Indian", "South Indian");
										map.put("Fast Food", "Fast Food");

										String htmlList = HTMLUtility.getList("cuisineType", dto.getCuisineType(), map);
									%>
									<%=htmlList%>
								</div>
								<font color="red" class="pl-sm-5">
									<%=ServletUtility.getErrorMessage("cuisineType", request)%></font></br>

								<!-- Rate -->
								<span class="pl-sm-5"><b>Rate</b><span style="color:red">*</span></span></br>
								<div class="col-sm-12">
									<input type="number" name="rate" class="form-control"
										placeholder="Enter Rate"
										value="<%=DataUtility.getStringData(dto.getRate())%>">
								</div>
								<font color="red" class="pl-sm-5">
									<%=ServletUtility.getErrorMessage("rate", request)%></font></br>

							</div>
							</br></br>

							<%
								if (id > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=ResturentCtl.OP_UPDATE%>">
								<input type="submit" name="operation"
									class="btn btn-warning btn-md"
									value="<%=ResturentCtl.OP_CANCEL%>">
							</div>
							<% } else { %>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=ResturentCtl.OP_SAVE%>">
								<input type="submit" name="operation"
									class="btn btn-warning btn-md"
									value="<%=ResturentCtl.OP_RESET%>">
							</div>
							<% } %>

						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4"></div>
		</form>
		</main>
	</div>
</body>
<%@include file="FooterView.jsp"%>
</html>