<%@page import="in.co.rays.project_3.controller.BankAccountCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>BankAccount View</title>

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

	<form action="<%=ORSView.BANKACCOUNT_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankAccountDTO"
			scope="request" />

		<div class="container">
			<div class="row pt-3">

				<div class="col-md-4"></div>

				<div class="col-md-4">
					<div class="card grad">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (dto.getAccountCode() != null && dto.getAccountId() > 0) {
							%>
							<h3 class="text-center text-primary">Update BankAccount</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add BankAccount</h3>
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

							<!-- Hidden Field -->
							<input type="hidden" name="id" value="<%=dto.getAccountId()%>">

							<!-- Account Code -->
							<span><b>Account Code</b> <span style="color: red">*</span></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-code"></i>
									</div>
								</div>
								<input type="text" name="accountCode" class="form-control"
									value="<%=DataUtility.getStringData(dto.getAccountCode())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("accountCode", request)%>
							</font><br>

							<!-- Account Holder Name -->
							<span><b>Account Holder Name</b> <span style="color: red">*</span></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user"></i>
									</div>
								</div>
								<input type="text" name="accountHolderName" class="form-control"
									value="<%=DataUtility.getStringData(dto.getAccountHolderName())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("accountHolderName", request)%>
							</font><br>

							<!-- Balance -->
							<span><b>Balance</b></span>
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-money"></i>
									</div>
								</div>
								<input type="text" name="balance" class="form-control"
									value="<%=DataUtility.getStringData(dto.getBalance())%>">
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("balance", request)%>
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
									if (dto.getAccountId() > 0) {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=BankAccountCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=BankAccountCtl.OP_CANCEL%>">
								<%
									} else {
								%>
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=BankAccountCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=BankAccountCtl.OP_RESET%>">
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