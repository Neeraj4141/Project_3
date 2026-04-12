<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.StockMovementCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Stock Movement view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

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

	<form action="<%=ORSView.STOCKMOVEMENT_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.StockMovementDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Stock</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Stock</h3>
						<%
							}
						%>

						<H4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success">
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>
						</H4>

						<H4 align="center">
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger">
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</H4>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Movement Code -->
						<span><b>Movement Code *</b></span> <input type="text"
							class="form-control" name="movementCode"
							placeholder="Movement Code"
							value="<%=DataUtility.getStringData(dto.getMovementCode())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("movementCode", request)%>
						</font><br>

						<!-- Product Name -->
						<span><b>Product Name *</b></span> <input type="text"
							class="form-control" name="productName"
							placeholder="Product Name"
							value="<%=DataUtility.getStringData(dto.getProductName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("productName", request)%>
						</font><br>

						<!-- Quantity -->
						<span><b>Quantity *</b></span> <input type="number"
							class="form-control" name="quantity" placeholder="Quantity"
							value="<%=DataUtility.getStringData(dto.getQuantity())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
						</font><br>

						<!-- Status -->
						<span><b>Status *</b></span> <input type="text"
							class="form-control" name="status" placeholder="Status"
							value="<%=DataUtility.getStringData(dto.getStatus())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
						</font><br>

						<!-- Buttons -->
						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=StockMovementCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning"
								value="<%=StockMovementCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=StockMovementCtl.OP_SAVE%>"> <input
								type="submit" name="operation" class="btn btn-warning"
								value="<%=StockMovementCtl.OP_RESET%>">

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

	<%@include file="FooterView.jsp"%>

</body>
</html>