<%@page import="in.co.rays.project_3.controller.PortfolioCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Portfolio View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PortfolioDTO"
		scope="request" />

	<form action="<%=ORSView.PORTFOLIO_CTL%>" method="post">

		<%
			long id = DataUtility.getLong(request.getParameter("id"));
		%>

		<center>
			<div class="card" style="width: 400px; padding: 20px;">

				<%
					if (dto.getId() != null) {
				%>
				<h3>Update Portfolio</h3>
				<%
					} else {
				%>
				<h3>Add Portfolio</h3>
				<%
					}
				%>

				<!-- Messages -->
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<%
					}
				%>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<%
					}
				%>

				<br> <br> <input type="hidden" name="id"
					value="<%=dto.getId()%>"> <input type="hidden"
					name="createdBy" value="<%=dto.getCreatedBy()%>"> <input
					type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
				<input type="hidden" name="createdDatetime"
					value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

				<!-- Portfolio No -->
				<b>Portfolio No *</b><br> <input type="text" name="portfolioNo"
					class="form-control"
					value="<%=DataUtility.getStringData(dto.getPortfolioNo())%>">
				<font color="red"> <%=ServletUtility.getErrorMessage("portfolioNo", request)%>
				</font> <br>

				<!-- Portfolio Name -->
				<b>Portfolio Name *</b><br> <input type="text"
					name="portfolioName" class="form-control"
					value="<%=DataUtility.getStringData(dto.getPortfolioName())%>">
				<font color="red"> <%=ServletUtility.getErrorMessage("portfolioName", request)%>
				</font> <br>

				<!-- Total Value -->
				<b>Total Value *</b><br> <input type="text" name="totalValue"
					class="form-control"
					value="<%=DataUtility.getStringData(dto.getTotalValue())%>">
				<font color="red"> <%=ServletUtility.getErrorMessage("totalValue", request)%>
				</font> <br> <br>

				<%
					if (id > 0) {
				%>
				<input type="submit" name="operation"
					value="<%=PortfolioCtl.OP_UPDATE%>"> <input type="submit"
					name="operation" value="<%=PortfolioCtl.OP_CANCEL%>">
				<%
					} else {
				%>
				<input type="submit" name="operation"
					value="<%=PortfolioCtl.OP_SAVE%>"> <input type="submit"
					name="operation" value="<%=PortfolioCtl.OP_RESET%>">
				<%
					}
				%>

			</div>
		</center>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>