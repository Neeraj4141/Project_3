<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>User Registration</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" 
 href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- Font Awesome -->
<link rel="stylesheet" 
 href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- jQuery UI Datepicker -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
			yearRange: "1950:2025",
			dateFormat: "dd/mm/yy"
		});
	});
</script>

<style type="text/css">
.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
	background-repeat: no-repeat;
	background-size: 100%;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.p4 {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
	background-size: 100%;
	padding-top: 60px;
}
</style>
</head>

<body class="p4">

<div class="header">
	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>
</div>

<main>
<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

<div class="container pt-4">
    <div class="row">
        <div class="col-md-3"></div>

        <div class="col-md-6">
            <div class="card input-group-addon grad">
                <div class="card-body">

                    <h3 class="text-center text-primary pb-3">User Registration</h3>

                    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.UserDTO"
						scope="request"></jsp:useBean>

                    <!-- Success Message -->
                    <%
						if (!ServletUtility.getSuccessMessage(request).equals("")) {
					%>
					<div class="alert alert-success alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getSuccessMessage(request)%>
					</div>
					<% } %>

                    <!-- Error Message -->
                    <%
						if (!ServletUtility.getErrorMessage(request).equals("")) {
					%>
					<div class="alert alert-danger alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getErrorMessage(request)%>
					</div>
					<% } %>

					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

                    <!-- First Name -->
                    <label><b>First Name</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-user-alt"></i></div>
                        </div>
                        <input type="text" class="form-control" name="firstName"
							placeholder="First Name"
							value="<%=DataUtility.getStringData(dto.getFirstName())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("firstName", request)%></span>

                    <!-- Last Name -->
                    <label><b>Last Name</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-user-circle"></i></div>
                        </div>
                        <input type="text" class="form-control" name="lastName"
							placeholder="Last Name"
							value="<%=DataUtility.getStringData(dto.getLastName())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("lastName", request)%></span>

                    <!-- Password -->
                    <label><b>Password</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-key"></i></div>
                        </div>
                        <input type="password" class="form-control" name="password"
							placeholder="Password"
							value="<%=DataUtility.getStringData(dto.getPassword())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("password", request)%></span>

                    <!-- Confirm Password -->
                    <label><b>Confirm Password</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-key"></i></div>
                        </div>
                        <input type="password" class="form-control" name="confirmPassword"
							placeholder="Confirm Password"
							value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></span>

                    <!-- Email -->
                    <label><b>Email Id</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-envelope"></i></div>
                        </div>
                        <input type="text" class="form-control" name="emailId"
							placeholder="Email Id"
							value="<%=DataUtility.getStringData(dto.getLogin())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("emailId", request)%></span>

                    <!-- Mobile -->
                    <label><b>Mobile No</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-phone-square"></i></div>
                        </div>
                        <input type="text" maxlength="10" class="form-control" name="mobileNo"
							placeholder="Mobile No"
							value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("mobileNo", request)%></span>

                    

                    <!-- DOB -->
                    <label><b>DOB</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                        </div>
                        <input type="text" id="datepicker" name="dob"
							class="form-control" placeholder="Date Of Birth"
							readonly="readonly" style="background-color: white;"
							value="<%=DataUtility.getDateString(dto.getDob())%>">
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("dob", request)%></span>
                    
                    <!-- Gender -->
                    <label><b>Gender</b> <span style="color:red">*</span></label>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-venus-mars"></i></div>
                        </div>
                        <%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");
							String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
						%>
						<%=htmlList%>
                    </div>
                    <span style="color:red;"><%=ServletUtility.getErrorMessage("gender", request)%></span>

                    <!-- Buttons -->
                    <div class="text-center">
                        <input type="submit" name="operation"
							class="btn btn-success btn-md"
							value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
                        <input type="submit" name="operation"
							class="btn btn-secondary btn-md"
							value="<%=UserRegistrationCtl.OP_RESET%>">
                    </div>

                </div>
            </div>
        </div>

        <div class="col-md-3"></div>
    </div>
</div>

</form>
</main>

<div class="footer">
	<%@include file="FooterView.jsp"%>
</div>

<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>