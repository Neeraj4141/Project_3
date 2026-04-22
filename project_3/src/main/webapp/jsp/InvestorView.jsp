<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.InvestorCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Investor View</title>

<style>
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
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}
</style>

</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.InvestorDTO"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.INVESTOR_CTL%>" method="post">

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Investor</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Investor</h3>
						<%
							}
						%>

						<!-- Success -->
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

						<!-- Error -->
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

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Investor Name -->
						<span class="pl-sm-5"><b>Investor Name</b><span
							style="color: red">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user"></i>
									</div>
								</div>
								<input type="text" name="investorName" class="form-control"
									value="<%=DataUtility.getStringData(dto.getInvestorName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("investorName", request)%></font><br>

						<!-- Investor Code -->
						<span class="pl-sm-5"><b>Investor Code</b><span
							style="color: red">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-key"></i>
									</div>
								</div>
								<input type="text" name="investorcode" class="form-control"
									value="<%=DataUtility.getStringData(dto.getInvestorcode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("investorcode", request)%></font><br>

						<!-- Investment Amount -->
						<span class="pl-sm-5"><b>Investment Amount</b><span
							style="color: red">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-money-bill"></i>
									</div>
								</div>
								<input type="text" name="investmentAmount" class="form-control"
									value="<%=DataUtility.getStringData(dto.getInvestmentAmount())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("investmentAmount", request)%></font><br>

						<!-- Investment Type -->
						<span class="pl-sm-5"><b>Investment Type</b><span
							style="color: red">*</span></span>

						<div class="col-sm-12">
							<div class="input-group">

								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-chart-line"></i>
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

						<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("investmentType", request)%></font><br>

						<!-- Buttons -->
						<div class="text-center">
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
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=InvestorCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=InvestorCtl.OP_RESET%>">
							<%
								}
							%>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>