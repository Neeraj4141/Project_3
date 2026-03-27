<%@page import="in.co.rays.project_3.controller.CartCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
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

	<form action="<%=ORSView.CART_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CartDTO"
			scope="request" />

		<div class="container">
			<div class="row pt-3">

				<div class="col-md-4"></div>

				<div class="col-md-4">
					<div class="card grad">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (dto.getCartCode() != null && dto.getCartId() > 0) {
							%>
							<h3 class="text-center text-primary">Update Cart</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Cart</h3>
							<%
								}
							%>

							<!-- Success Message -->
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

							<!-- Error Message -->
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

							<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getCartId()%>">

							<!-- Cart Code -->
							<span><b>Cart Code</b> <span style="color: red">*</span></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-code"></i>
									</div>
								</div>
								<input type="text" name="cartCode" class="form-control"
									value="<%=DataUtility.getStringData(dto.getCartCode())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("cartCode", request)%>
							</font><br>

							<!-- User Name -->
							<span><b>User Name</b> <span style="color: red">*</span></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user"></i>
									</div>
								</div>
								<input type="text" name="userName" class="form-control"
									value="<%=DataUtility.getStringData(dto.getUserName())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("userName", request)%>
							</font><br>

							<!-- Total Items -->
							<span><b>Total Items</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-list"></i>
									</div>
								</div>
								<input type="text" name=totalItems class="form-control"
									value="<%=DataUtility.getStringData(dto.getTotalItems())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("totalItems", request)%>
							</font><br>

							<!-- Status -->
							<span><b>Status</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-info-circle"></i>
									</div>
								</div>
								<input type="text" name="status" class="form-control"
									value="<%=DataUtility.getStringData(dto.getStatus())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
							</font><br>

							<!-- Buttons -->
							<div class="text-center">

								<%
									if (dto.getCartId() > 0) {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=CartCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=CartCtl.OP_CANCEL%>">
								<%
									} else {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=CartCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=CartCtl.OP_RESET%>">
								<%
									}
								%>

							</div>

						</div>
					</div>
				</div>

				<div class="col-md-4"></div>

			</div>
		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>