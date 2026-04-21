<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.RuleCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Rule view</title>
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

	<form action="<%=ORSView.RULE_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.RuleDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));

							if (dto.getRuleCode() != null && dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center default-text text-primary">Update Rule</h3>
						<%
							} else {
						%>
						<h3 class="text-center default-text text-primary">Add Rule</h3>
						<%
							}
						%>

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

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- RULE CODE -->
						<span class="pl-sm-5"><b>Rule Code</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-key grey-text"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="ruleCode"
									placeholder="Rule Code"
									value="<%=DataUtility.getStringData(dto.getRuleCode())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("ruleCode", request)%>
						</font></br>

						<!-- TRIGGER TYPE -->
						<span class="pl-sm-5"><b>Trigger Type</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-bolt grey-text"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="triggerType"
									placeholder="Trigger Type"
									value="<%=DataUtility.getStringData(dto.getTriggerType())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("triggerType", request)%>
						</font></br>

						<!-- EVENT -->
						<span class="pl-sm-5"><b>Event</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-cogs grey-text"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="event"
									placeholder="Event"
									value="<%=DataUtility.getStringData(dto.getEvent())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("event", request)%>
						</font></br>

						<!-- STATUS -->
						<span class="pl-sm-5"><b>Status</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-info-circle grey-text"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="status"
									placeholder="Status"
									value="<%=DataUtility.getStringData(dto.getStatus())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
						</font></br>

						<!-- BUTTON -->
						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=RuleCtl.OP_UPDATE%>">

							<input type="submit" name="operation"
								class="btn btn-warning btn-md" value="<%=RuleCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success btn-md" value="<%=RuleCtl.OP_SAVE%>">

							<input type="submit" name="operation"
								class="btn btn-warning btn-md" value="<%=RuleCtl.OP_RESET%>">
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