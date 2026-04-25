package in.co.rays.project_3.dto;

import java.util.Date;

public class HotelDTO extends BaseDTO {

	private String firstName;
	private String lastName;
	private String gender;
	private String mobileNo;
	private Long roomNo;
	private Date cheackIn;
	private Date cheackOut;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Long roomNo) {
		this.roomNo = roomNo;
	}

	public Date getCheackIn() {
		return cheackIn;
	}

	public void setCheackIn(Date cheackIn) {
		this.cheackIn = cheackIn;
	}

	public Date getCheackOut() {
		return cheackOut;
	}

	public void setCheackOut(Date cheackOut) {
		this.cheackOut = cheackOut;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}

}
