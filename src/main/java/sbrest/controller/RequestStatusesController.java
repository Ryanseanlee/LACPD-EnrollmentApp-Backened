package sbrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbrest.model.ServiceRequest;
import sbrest.model.dao.ServiceRequestDao;

@RestController
@RequestMapping("/request_statuses")
public class RequestStatusesController {

	@Autowired
	private ServiceRequestDao serviceRequestDao;

	@GetMapping("/{requestNumber}")
	public RequestStatusResponse getRequestStatusByRequestNumber(@PathVariable Integer requestNumber) {
		// TODO: Handle null pointers (when getServiceReqeust returns null)
		ServiceRequest serviceRequest = serviceRequestDao.getServiceRequest(requestNumber);
		if (serviceRequest != null) {
			RequestStatusResponse res = new RequestStatusResponse(
					serviceRequest.getRequestNumber(),
					serviceRequest.getRequestStatus(), 
					serviceRequest.getFirstName(), 
					serviceRequest.getLastName(), 
					serviceRequest.isEmployee(), 
					serviceRequest.getSubmitDate(),
					serviceRequest.isComplete()); 
			return res;
		}
		else {
			return null;
		}
	}
}

// A representation of what the server will return
class RequestStatusResponse {
	private Integer requestNumber;
	private String requestStatus;
	private String firstName;
	private String lastName;
	private boolean isEmployee;
	private String submitDate;
	private boolean isComplete;

	public RequestStatusResponse(Integer requestNumber, 
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
		this.isEmployee = isEmployee;
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