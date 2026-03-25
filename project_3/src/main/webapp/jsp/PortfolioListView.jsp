<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.PortfolioListCtl"%>
<%@page import="in.co.rays.project_3.dto.PortfolioDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Portfolio List View</title>

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

<%@include file="Header.jsp"%>

<form action="<%=ORSView.PORTFOLIO_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PortfolioDTO" scope="request"/>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
	List list = ServletUtility.getList(request);
	Iterator<PortfolioDTO> it = list.iterator();
%>

<center>
	<h1><font color="black">Portfolio List</font></h1>
</center>

<br>

<!-- Search Fields -->
<div class="row">

	<div class="col-sm-3">
		<input type="text" name="portfolioName" class="form-control"
			placeholder="Search Portfolio Name"
			value="<%=DataUtility.getStringData(dto.getPortfolioName())%>">
	</div>

	<div class="col-sm-3">
		<input type="text" name="portfolioNo" class="form-control"
			placeholder="Search Portfolio No"
			value="<%=DataUtility.getStringData(dto.getPortfolioNo())%>">
	</div>

	<div class="col-sm-3">
		<input type="submit" class="btn btn-primary" name="operation"
			value="<%=PortfolioListCtl.OP_SEARCH%>">
		<input type="submit" class="btn btn-dark" name="operation"
			value="<%=PortfolioListCtl.OP_RESET%>">
	</div>

</div>

<br>

<!-- Table -->
<div class="table-responsive">
<table class="table table-bordered table-dark table-hover">

	<thead>
	<tr>
		<th>Select</th>
		<th>S.NO</th>
		<th>Portfolio No</th>
		<th>Portfolio Name</th>
		<th>Total Value</th>
		<th>Edit</th>
	</tr>
	</thead>

	<tbody>
	<%
		while (it.hasNext()) {
			dto = it.next();
	%>
	<tr>
		<td><input type="checkbox" name="ids" value="<%=dto.getId()%>"></td>
		<td><%=index++%></td>
		<td><%=dto.getPortfolioNo()%></td>
		<td><%=dto.getPortfolioName()%></td>
		<td><%=dto.getTotalValue()%></td>
		<td>
			<a href="PortfolioCtl?id=<%=dto.getId()%>">Edit</a>
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
	<td>
		<input type="submit" name="operation" class="btn btn-secondary"
			value="<%=PortfolioListCtl.OP_PREVIOUS%>"
			<%=pageNo > 1 ? "" : "disabled"%>>
	</td>

	<td>
		<input type="submit" name="operation" class="btn btn-primary"
			value="<%=PortfolioListCtl.OP_NEW%>">
	</td>

	<td>
		<input type="submit" name="operation" class="btn btn-danger"
			value="<%=PortfolioListCtl.OP_DELETE%>">
	</td>

	<td align="right">
		<input type="submit" name="operation" class="btn btn-secondary"
			value="<%=PortfolioListCtl.OP_NEXT%>"
			<%=(nextPageSize != 0) ? "" : "disabled"%>>
	</td>
</tr>
</table>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>S