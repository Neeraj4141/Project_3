<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.PolicyDTO"%>
<%@page import="in.co.rays.project_3.controller.PolicyListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Policy List</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
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

	<form class="pb-5" action="<%=ORSView.POLICY_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PolicyDTO"
			scope="request" />

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<PolicyDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Policy List</u>
			</h1>
		</center>

		<!-- Success -->
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

		<!-- Error -->
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

		<!-- 🔍 Search Fields (IMPORTANT - method ke hisaab se) -->
		<div class="row">

			<div class="col-sm-2"></div>

			

			<div class="col-sm-3">
				<input type="text" name="policyName" placeholder="Enter Policy Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("policyName", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=PolicyListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=PolicyListCtl.OP_RESET%>">
			</div>

		</div>

		<br>

		<!-- TABLE -->
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">

				<thead>
					<tr style="background-color: #f79d65; font-size: 18px;">

						<th><input type="checkbox" id="select_all"> Select
							All</th>
						<th class="text">S.NO</th>
						<th class="text">Policy No</th>
						<th class="text">Policy Name</th>
						<th class="text">Premium Amount</th>
						<th class="text">Start Date</th>
						<th class="text">Edit</th>

					</tr>
				</thead>

				<tbody>

					<%
						while (it.hasNext()) {
							dto = it.next();
					%>

					<tr style="font-weight: bold;">

						<td align="center"><input type="checkbox" name="ids"
							value="<%=dto.getId()%>"></td>

						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getPolicyNo()%></td>
						<td class="text"><%=dto.getPolicyName()%></td>
						<td class="text"><%=dto.getPremiumAmount()%></td>
						<td class="text"><%=DataUtility.getDateString(dto.getStartDate())%></td>

						<td class="text"><a href="PolicyCtl?id=<%=dto.getId()%>">Edit</a>
						</td>

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
					class="btn btn-warning" value="<%=PolicyListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=PolicyListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=PolicyListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=PolicyListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>