package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run ---
 * 
 * @author Neeraj Mewada
 * 
 * 
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	/**
	 * @return
	 */
	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	/**
	 * @return
	 */
	public HostelModelInt getHostelModel() {

		HostelModelInt hostelModel = (HostelModelInt) modelCache.get("hostelModel");

		if (hostelModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				hostelModel = new HostelModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				hostelModel = new HostelModelJDBCImpl();
			}

			modelCache.put("hostelModel", hostelModel);
		}

		return hostelModel;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

	public ResturentModelInt getResturentModel() {
		ResturentModelInt resturentmodel = (ResturentModelInt) modelCache.get("resturentmodel");
		if (resturentmodel == null) {
			if ("Hibernate".equals(DATABASE)) {
				resturentmodel = new ResturentModelHibImpl();
			}
		}
		return resturentmodel;
	}

	public InvestorModelInt getInvestorModel() {

		InvestorModelInt investormodel = (InvestorModelInt) modelCache.get("investormodel");

		if (investormodel == null) {

			if ("Hibernate".equals(DATABASE)) {
				investormodel = new InvestorModelHibImpl();
			}
		}

		return investormodel;
	}

	public LockerModelInt getLockerModel() {

		LockerModelInt lockermodel = (LockerModelInt) modelCache.get("lockermodel");

		if (lockermodel == null) {

			if ("Hibernate".equals(DATABASE)) {
				lockermodel = new LockerModelHibImpl();
				{
				}
			}
		}
		return lockermodel;
	}

	public PortfolioModelInt getPortfolioModel() {

		PortfolioModelInt portfoliomodel = (PortfolioModelInt) modelCache.get("portfoliomodel");

		if (portfoliomodel == null) {

			if ("Hibernate".equals(DATABASE)) {
				portfoliomodel = new PortfolioModelHibImp();
				{
				}
			}
		}
		return portfoliomodel;
	}

	public OTPVerificationModelInt getOTPVerificationModel() {

		OTPVerificationModelInt otpverificationmodel = (OTPVerificationModelInt) modelCache.get("otpverificationmodel");

		if (otpverificationmodel == null) {

			if ("Hibernate".equals(DATABASE)) {
				otpverificationmodel = new OTPVerificationModelHibImpl();
			}
		}

		return otpverificationmodel;
	}

	public WalletModelInt getWalletModel() {

		WalletModelInt walletModel = (WalletModelInt) modelCache.get("walletModel");

		if (walletModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				walletModel = new WalletModelHibImpl();
			}

			modelCache.put("walletModel", walletModel);
		}

		return walletModel;
	}

	public CartModelInt getCartModel() {

		CartModelInt cartModel = (CartModelInt) modelCache.get("cartModel");

		if (cartModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				cartModel = new CartModelHibImpl();
			}
		}

		return cartModel;
	}

	public BankAccountModelInt getBankAccountModel() {

		BankAccountModelInt bankAccountModel = (BankAccountModelInt) modelCache.get("bankAccountModel");

		if (bankAccountModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				bankAccountModel = new BankAccountModelHibImpl();
			}
		}

		return bankAccountModel;
	}

	public PolicyModelInt getPolicyModel() {

		PolicyModelInt policyModel = (PolicyModelInt) modelCache.get("policyModel");

		if (policyModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				policyModel = new PolicyModelHibImpl();
			}
		}

		return policyModel;
	}

	public PrescriptionModelInt getPrescriptionModel() {

		PrescriptionModelInt prescriptionModel = (PrescriptionModelInt) modelCache.get("prescriptionModel");

		if (prescriptionModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				prescriptionModel = new PrescriptionModelHibImpl();
			}
		}

		return prescriptionModel;
	}

	public ReportModelInt getReportModel() {

		ReportModelInt reportModel = (ReportModelInt) modelCache.get("reportModel");

		if (reportModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				reportModel = new ReportModelHibImpl();
			}
		}

		return reportModel;
	}

	public InsuranceModelInt getInsuranceModel() {

		InsuranceModelInt insuranceModel = (InsuranceModelInt) modelCache.get("insuranceModel");

		if (insuranceModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				insuranceModel = new InsuranceModelHibImpl();
			}
		}

		return insuranceModel;
	}

	public SpaceMissionModelInt getSpaceMissionModel() {

		SpaceMissionModelInt spaceMissionModel = (SpaceMissionModelInt) modelCache.get("spaceMissionModel");

		if (spaceMissionModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				spaceMissionModel = new SpaceMissionModelHibImpl();
			}
		}

		return spaceMissionModel;
	}

	public ContractModelInt getContractModel() {

		ContractModelInt contractModel = (ContractModelInt) modelCache.get("contractModel");

		if (contractModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				contractModel = new ContractModelHibImpl();
			}
		}

		return contractModel;
	}

	public StockMovementModelInt getStockMovementModel() {

		StockMovementModelInt stockMovementModel = (StockMovementModelInt) modelCache.get("stockMovementModel");

		if (stockMovementModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				stockMovementModel = new StockMovementModelHibImpl();
			}
		}

		return stockMovementModel;
	}

	public AdvertisementModelInt getAdvertisementModel() {

		AdvertisementModelInt advertisementModel = (AdvertisementModelInt) modelCache.get("advertisementModel");

		if (advertisementModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				advertisementModel = new AdvertisementModelHibImpl();
			}
		}

		return advertisementModel;
	}

	public CaseModelInt getCaseModel() {

		CaseModelInt caseModel = (CaseModelInt) modelCache.get("caseModel");

		if (caseModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				caseModel = new CaseModelHibImpl();
			}
		}

		return caseModel;
	}

	public EnvironmentModelInt getEnvironmentModel() {

		EnvironmentModelInt environmentModel = (EnvironmentModelInt) modelCache.get("environmentModel");

		if (environmentModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				environmentModel = new EnvironmentModelHibImpl();
			}

			modelCache.put("environmentModel", environmentModel);
		}

		return environmentModel;
	}

	public DataRetentionModelInt getDataRetentionModel() {

		DataRetentionModelInt dataRetentionModel = (DataRetentionModelInt) modelCache.get("dataRetentionModel");

		if (dataRetentionModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				dataRetentionModel = new DataRetentionModelHibImpl();
			}

			modelCache.put("dataRetentionModel", dataRetentionModel);
		}

		return dataRetentionModel;
	}

	public WebHookModelInt getWebHookModel() {

		WebHookModelInt webHookModel = (WebHookModelInt) modelCache.get("webHookModel");

		if (webHookModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				webHookModel = new WebHookModelHibImpl();
			}

			modelCache.put("webHookModel", webHookModel);
		}

		return webHookModel;
	}

	public NotificationModelInt getNotificationModel() {

		NotificationModelInt notificationModel = (NotificationModelInt) modelCache.get("notificationModel");

		if (notificationModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				notificationModel = new NotificationModelHibImpl();
			}
		}

		return notificationModel;
	}

	public RuleModelInt getRuleModel() {

		RuleModelInt ruleModel = (RuleModelInt) modelCache.get("ruleModel");

		if (ruleModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				ruleModel = new RuleModelHibImpl();
			}
		}

		return ruleModel;
	}

	public AuditModelInt getAuditModel() {

		AuditModelInt auditModel = (AuditModelInt) modelCache.get("auditModel");

		if (auditModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				auditModel = new AuditModelHibImpl();
			}
		}

		return auditModel;
	}
}
