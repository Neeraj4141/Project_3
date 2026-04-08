package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Neeraj Mewada
 *
 */
public interface ORSView {

	public String APP_CONTEXT = "/project_3";

	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";

	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";

	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";

	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";

	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";

	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";

	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";

	public String HOSTEL_VIEW = "/jsp/HostelView.jsp";

	public String HOSTEL_LIST_VIEW = "/jsp/HostelListView.jsp";

	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";

	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

	public String HOSTEL_CTL = APP_CONTEXT + "/ctl/HostelCtl";

	public String HOSTEL_LIST_CTL = APP_CONTEXT + "/ctl/HostelListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String RESTURENT_CTL = APP_CONTEXT + "/ctl/ResturentCtl";

	public String RESTURENT_LIST_CTL = APP_CONTEXT + "/ctl/ResturentListCtl";

	public String RESTURENT_VIEW = PAGE_FOLDER + "/ResturentView.jsp";

	public String RESTURENT_LIST_VIEW = PAGE_FOLDER + "/ResturentListView.jsp";

	public String INVESTOR_CTL = APP_CONTEXT + "/ctl/InvestorCtl";

	public String INVESTOR_LIST_CTL = APP_CONTEXT + "/ctl/InvestorListCtl";

	public String INVESTOR_VIEW = PAGE_FOLDER + "/InvestorView.jsp";

	public String INVESTOR_LIST_VIEW = PAGE_FOLDER + "/InvestorListView.jsp";

	public String LOCKER_CTL = APP_CONTEXT + "/ctl/LockerCtl";

	public String LOCKER_LIST_CTL = APP_CONTEXT + "/ctl/LockerListCtl";

	public String LOCKER_VIEW = PAGE_FOLDER + "/LockerView.jsp";

	public String LOCKER_LIST_VIEW = PAGE_FOLDER + "/LockerListView.jsp";

	public String PORTFOLIO_CTL = APP_CONTEXT + "/ctl/PortfolioCtl";

	public String PORTFOLIO_LIST_CTL = APP_CONTEXT + "/ctl/PortfolioListCtl";

	public String PORTFOLIO_VIEW = PAGE_FOLDER + "/PortfolioView.jsp";

	public String PORTFOLIO_LIST_VIEW = PAGE_FOLDER + "/PortfolioListView.jsp";

	public String OTPVERIFICATION_CTL = APP_CONTEXT + "/ctl/OTPVerificationCtl";

	public String OTPVERIFICATION_LIST_CTL = APP_CONTEXT + "/ctl/OTPVerificationListCtl";

	public String OTPVERIFICATION_VIEW = PAGE_FOLDER + "/OTPVerificationView.jsp";

	public String OTPVERIFICATION_LIST_VIEW = PAGE_FOLDER + "/OTPVerificationListView.jsp";

	public String WALLET_CTL = APP_CONTEXT + "/ctl/WalletCtl";

	public String WALLET_LIST_CTL = APP_CONTEXT + "/ctl/WalletListCtl";

	public String WALLET_VIEW = PAGE_FOLDER + "/WalletView.jsp";

	public String WALLET_LIST_VIEW = PAGE_FOLDER + "/WalletListView.jsp";

	public String CART_CTL = APP_CONTEXT + "/ctl/CartCtl";

	public String CART_LIST_CTL = APP_CONTEXT + "/ctl/CartListCtl";

	public String CART_VIEW = PAGE_FOLDER + "/CartView.jsp";

	public String CART_LIST_VIEW = PAGE_FOLDER + "/CartListView.jsp";

	public String BANKACCOUNT_CTL = APP_CONTEXT + "/ctl/BankAccountCtl";

	public String BANKACCOUNT_LIST_CTL = APP_CONTEXT + "/ctl/BankAccountListCtl";

	public String BANKACCOUNT_VIEW = PAGE_FOLDER + "/BankAccountView.jsp";

	public String BANKACCOUNT_LIST_VIEW = PAGE_FOLDER + "/BankAccountListView.jsp";

	public String POLICY_CTL = APP_CONTEXT + "/ctl/PolicyCtl";

	public String POLICY_LIST_CTL = APP_CONTEXT + "/ctl/PolicyListCtl";

	public String POLICY_VIEW = PAGE_FOLDER + "/PolicyView.jsp";

	public String POLICY_LIST_VIEW = PAGE_FOLDER + "/PolicyListView.jsp";

	public String CONTRACT_CTL = APP_CONTEXT + "/ctl/ContractCtl";

	public String CONTRACT_LIST_CTL = APP_CONTEXT + "/ctl/ContractListCtl";

	public String CONTRACT_VIEW = PAGE_FOLDER + "/ContractView.jsp";

	public String CONTRACT_LIST_VIEW = PAGE_FOLDER + "/ContractListView.jsp";

	public String PRESCRIPTION_CTL = APP_CONTEXT + "/ctl/PrescriptionCtl";

	public String PRESCRIPTION_LIST_CTL = APP_CONTEXT + "/ctl/PrescriptionListCtl";

	public String PRESCRIPTION_VIEW = PAGE_FOLDER + "/PrescriptionView.jsp";

	public String PRESCRIPTION_LIST_VIEW = PAGE_FOLDER + "/PrescriptionListView.jsp";

	public String REPORT_CTL = APP_CONTEXT + "/ctl/ReportCtl";

	public String REPORT_LIST_CTL = APP_CONTEXT + "/ctl/ReportListCtl";

	public String REPORT_VIEW = PAGE_FOLDER + "/ReportView.jsp";

	public String REPORT_LIST_VIEW = PAGE_FOLDER + "/ReportListView.jsp";

	public String INSURANCE_CTL = APP_CONTEXT + "/ctl/InsuranceCtl";

	public String INSURANCE_LIST_CTL = APP_CONTEXT + "/ctl/InsuranceListCtl";

	public String INSURANCE_VIEW = PAGE_FOLDER + "/InsuranceView.jsp";

	public String INSURANCE_LIST_VIEW = PAGE_FOLDER + "/InsuranceListView.jsp";

}
