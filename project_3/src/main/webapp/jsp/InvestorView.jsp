<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.InvestorCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Investor View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
	background-repeat: no-repeat;
	background-size: 100%;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.InvestorDTO"
		scope="request"></jsp:useBean>

	<main>
	<form action="<%=ORSView.INVESTOR_CTL%>" method="post">

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null) {
						%>
						<h3 class="text-center text-primary font-weight-bold">Update
							Investor</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary font-weight-bold">Add
							Investor</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<h4 align="center">
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
						</h4>

						<!-- Error Message -->
						<h4 align="center">
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
						</h4>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Investor Name -->
						<label><b>Investor Name</b><span style="color: red;">*</span></label>
						<input type="text" name="investorName" class="form-control"
							placeholder="Enter Investor Name"
							value="<%=DataUtility.getStringData(dto.getInvestorName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("investorName", request)%>
						</font> <br>

						<!-- Investor Code -->
						<label><b>Investor Code</b><span style="color: red;">*</span></label>
						<input type="text" name="investorcode" class="form-control"
							placeholder="Enter Investor Code"
							value="<%=DataUtility.getStringData(dto.getInvestorcode())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("investorcode", request)%>
						</font> <br>

						<!-- Investment Amount -->
						<label><b>Investment Amount</b><span style="color: red;">*</span></label>
						<input type="text" name="investmentAmount" class="form-control"
							placeholder="Enter Investment Amount"
							value="<%=DataUtility.getStringData(dto.getInvestmentAmount())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("investmentAmount", request)%>
						</font> <br>

						<!-- Investment Type Dropdown -->
						<span class="pl-sm-5"><b>Investment Type</b><span
							style="color: red;">*</span></span> </br>

						<div class="col-sm-12">
							<div class="input-group">

								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-chart-line grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>

								<%
									HashMap map = new HashMap();

									map.put("realstate", "Real State");
									map.put("stockmarket", "Stock Market");
									map.put("gold", "Gold");
									map.put("silver", "Silver");
									map.put("bitcoin", "Bitcoin");

									String htmlList = HTMLUtility.getList("investmentType", dto.getInvestmentType(), map);
								%>

								<%=htmlList%>

							</div>
						</div>

						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("investmentType", request)%>
						</font> <br>

						<%
							if (id > 0) {
						%>

						<input type="submit" name="operation" class="btn btn-success"
							value="<%=InvestorCtl.OP_UPDATE%>"> <input type="submit"
							name="operation" class="btn btn-warning"
							value="<%=InvestorCtl.OP_CANCEL%>">

						<%
							} else {
						%>

						<div class="text-center">

							<input type="submit" name="operation"
								class="btn btn-success btn-md" style="font-size: 17px"
								value="<%=InvestorCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning btn-md"
								style="font-size: 17px" value="<%=InvestorCtl.OP_RESET%>">
						</div>

						<%
							}
						%>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>
	</main>

</body>
<%@include file="FooterView.jsp"%>
</html>