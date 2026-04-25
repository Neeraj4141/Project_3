<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.HotelListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.HotelDTO"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hotel List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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
	<form action="<%=ORSView.HOTEL_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HotelDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<HotelDTO> it = list.iterator();
			if(list.size() != 0){
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Hotel List</u>
			</h1>
		</center>

		<!-- Messages -->

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
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				</h4>
			</div>
			<%
				}
			%>
			<div class="col-md-4"></div>
		</div>

		<!-- Search -->
		<div class="row">
			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input type="text" name="firstName" placeholder="Enter FirstName"
					class="form-control"
					value="<%=ServletUtility.getParameter("firstName", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="roomNo" placeholder="Enter Room No"
					class="form-control"
					value="<%=ServletUtility.getParameter("roomNo", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="mobileNo" placeholder="Enter Mobile No"
					class="form-control"
					value="<%=ServletUtility.getParameter("mobileNo", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=HotelListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=HotelListCtl.OP_RESET%>">
			</div>

		</div>

		<br>

		<!-- Table -->
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr style="background-color: #f79d65; font-size: 18px;">

					<th width="10%"><input type="checkbox" id="select_all"
						name="Select" class="text"> Select All</th>
					<th width="5%" class="text">S.NO</th>
					<th width="15%" class="text">FirstName</th>
					<th width="15%" class="text">LastName</th>
					<th width="10%" class="text">Gender</th>
					<th width="10%" class="text">RoomNo</th>
					<th width="10%" class="text">MobileNo</th>
					<th width="10%" class="text">CheackIn</th>
					<th width="10%" class="text">CheackOut</th>
					<th width="5%" class="text">Edit</th>
				</tr>
			</thead>

			<tbody>
				<%
					while (it.hasNext()) {
						dto = it.next();
				%>

				<tr style="font-weight: bold;">
					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=dto.getId()%>"></td>
					<td class="text"><%=index++%></td>
					<td class="text"><%=dto.getFirstName()%></td>
					<td class="text"><%=dto.getLastName()%></td>
					<td class="text"><%=dto.getGender()%></td>
					<td class="text"><%=dto.getRoomNo()%></td>
					<td class="text"><%=dto.getMobileNo()%></td>
					<td class="text"><%=DataUtility.getDateString(dto.getCheackIn())%></td>
					<td class="text"><%=DataUtility.getDateString(dto.getCheackOut())%></td>

					<td><a href="HotelCtl?id=<%=dto.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>

		<!-- Buttons -->
		<table width="100%">
			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-warning" value="<%=HotelListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=HotelListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=HotelListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=HotelListCtl.OP_NEXT%>"
					<%=nextPageSize != 0 ? "" : "disabled"%>></td>

			</tr>
		</table>
		<%
				}
				if (list.size() == 0) {
			%>
			<center>
				<h1 style="font-size: 40px; color: #162390;">Hotel List</h1>
			</center>
			</br>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			</br>

			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=HotelListCtl.OP_BACK%>">
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