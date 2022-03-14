package sbrest.model;

//A representation of what the server will return (only some details of request)
public class RequestStatusResponse {
	private Integer requestNumber;
	private String requestStatus;
	private String firstName;
	private String lastName;
	private boolean isEmployee;
	private String submitDate;
	private boolean isComplete;

	public RequestStatusResponse(
			Integer requestNumber, 
			String requestStatus, 
			String firstName, 
			String lastName, 
			boolean isEmployee,
			String submitDate, 
			boolean isComplete) {
		this.requestNumber = requestNumber;
		this.requestStatus = requestStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setEmployee(isEmployee);
		this.setSubmitDate(submitDate);
		this.setComplete(isComplete);
	}

	public Integer getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(Integer requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

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

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	
}