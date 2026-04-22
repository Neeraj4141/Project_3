<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.InvestorListCtl"%>
<%@page import="in.co.rays.project_3.dto.InvestorDTO"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Investor List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

<body class="hm">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.INVESTOR_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.InvestorDTO"
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
			Iterator<InvestorDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Investor List</u>
			</h1>
		</center>

		<!-- Success -->
		<div class="row">
			<div class="col-md-4"></div>
			<%
				if (!ServletUtility.getSuccessMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-success">
				<%=ServletUtility.getSuccessMessage(request)%>
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
			<div class="col-md-4 alert alert-danger">
				<%=ServletUtility.getErrorMessage(request)%>
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
				<input type="text" name="investorName" class="form-control"
					placeholder="Investor Name"
					value="<%=DataUtility.getStringData(dto.getInvestorName())%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="investorCode" class="form-control"
					placeholder="Investor Code"
					value="<%=DataUtility.getStringData(dto.getInvestorcode())%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="investmentType" class="form-control"
					placeholder="Investment Type"
					value="<%=DataUtility.getStringData(dto.getInvestmentType())%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=InvestorListCtl.OP_SEARCH%>"> <input
					type="submit" class="btn btn-dark" name="operation"
					value="<%=InvestorListCtl.OP_RESET%>">
			</div>

		</div>

		<br>

		<!-- TABLE -->
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">

				<thead>
					<tr style="background-color: #f79d65; font-size: 18px;">

						<th width="10%"><input type="checkbox" id="select_all">
							Select All</th>

						<th class="text">S.NO</th>
						<th class="text">Investor Name</th>
						<th class="text">Investor Code</th>
						<th class="text">Investment Amount</th>
						<th class="text">Investment Type</th>
						<th class="text">Edit</th>

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
						<td class="text"><%=dto.getInvestorName()%></td>
						<td class="text"><%=dto.getInvestorcode()%></td>
						<td class="text"><%=dto.getInvestmentAmount()%></td>
						<td class="text"><%=dto.getInvestmentType()%></td>

						<td class="text"><a href="InvestorCtl?id=<%=dto.getId()%>">Edit</a>
						</td>

					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

		<!-- Buttons -->
		<table width="100%">
			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-warning" value="<%=InvestorListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=InvestorListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=InvestorListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=InvestorListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>