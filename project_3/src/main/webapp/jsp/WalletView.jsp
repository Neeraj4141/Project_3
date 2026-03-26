<%@page import="in.co.rays.project_3.controller.WalletCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Wallet View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.log1 {
	padding-top: 3%;
}

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.p4 {
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
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.WalletDTO"
			scope="request"></jsp:useBean>

		<main>
		<form action="<%=ORSView.WALLET_CTL%>" method="post">

			<div class="row pt-3">
				<div class="col-md-4"></div>

				<div class="col-md-4 ">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("walletId"));

								if (dto.getWalletId() != null) {
							%>

							<h3 class="text-center text-primary font-weight-bold">Update
								Wallet</h3>

							<%
								} else {
							%>

							<h3 class="text-center text-primary font-weight-bold">Add
								Wallet</h3>

							<%
								}
							%>

							<!-- Messages -->
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

							<!-- Hidden Fields -->
							<input type="hidden" name="walletId"
								value="<%=dto.getWalletId()%>"> <input type="hidden"
								name="createdBy" value="<%=dto.getCreatedBy()%>"> <input
								type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
							<input type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Wallet Code -->
							<span class="pl-sm-5"><b>Wallet Code</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-id-card grey-text"></i>
										</div>
									</div>
									<input type="text" name="walletCode" class="form-control"
										placeholder="Enter Wallet Code"
										value="<%=DataUtility.getStringData(dto.getWalletCode())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("walletCode", request)%>
							</font>

							<!-- User Name -->
							<span class="pl-sm-5"><b>User Name</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user grey-text"></i>
										</div>
									</div>
									<input type="text" name="userName" class="form-control"
										placeholder="Enter User Name"
										value="<%=DataUtility.getStringData(dto.getUserName())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("userName", request)%>
							</font>

							<!-- Balance -->
							<span class="pl-sm-5"><b>Balance</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-money-bill grey-text"></i>
										</div>
									</div>
									<input type="text" name="balance" class="form-control"
										placeholder="Enter Balance"
										value="<%=DataUtility.getStringData(dto.getBalance())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("balance", request)%>
							</font>

							<!-- Status -->
							<span class="pl-sm-5"><b>Status</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-toggle-on grey-text"></i>
										</div>
									</div>
									<input type="text" name="status" class="form-control"
										placeholder="Enter Status"
										value="<%=DataUtility.getStringData(dto.getStatus())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
							</font> <br> <br>

							<%
								if (id > 0) {
							%>

							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=WalletCtl.OP_UPDATE%>">
								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=WalletCtl.OP_CANCEL%>">
							</div>

							<%
								} else {
							%>

							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=WalletCtl.OP_SAVE%>">
								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=WalletCtl.OP_RESET%>">
							</div>

							<%
								}
							%>

						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
		</main>
	</div>

</body>

<%@include file="FooterView.jsp"%>
</html>