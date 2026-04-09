package in.co.rays.project_3.dto;

public class SpaceMissionDTO extends BaseDTO {

	private String missionName;
	private String launchVehical;
	private String destination;
	private String missionStatus;

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public String getLaunchVehical() {
		return launchVehical;
	}

	public void setLaunchVehical(String launchVehical) {
		this.launchVehical = launchVehical;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMissionStatus() {
		return missionStatus;
	}

	public void setMissionStatus(String missionStatus) {
		this.missionStatus = missionStatus;
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
