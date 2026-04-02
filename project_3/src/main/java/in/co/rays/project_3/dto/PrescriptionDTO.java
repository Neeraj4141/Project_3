package in.co.rays.project_3.dto;

import java.util.Date;

public class PrescriptionDTO extends BaseDTO {

	private Long prescriptionNo;
	private String patientName;
	private String doctorName;
	private Date prescribedDate;

	public Long getPrescriptionNo() {
		return prescriptionNo;
	}

	public void setPrescriptionNo(Long prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
