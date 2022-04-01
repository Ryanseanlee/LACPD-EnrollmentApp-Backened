package sbrest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer requestNumber;

	private String createDate;
	private String submitDate;
	private boolean isEmployee;
	private String requestStatus;
	private boolean isComplete;

	private String agreementId;
	private boolean replaceLostToken;
	private boolean addLogonId;
	private boolean changeLogonId;
	private boolean deleteLogonId;
	
	@Column(length = 5000) 
	private ArrayList<String> eventHistory;
	
	//personal Information
	@Column(length = 50)
	private String lastName;
	@Column(length = 50)
	private String firstName;
	@Column(length = 5)
	private String middleInitial;
	@Column(length = 100)
	private String emailAddress;
	@Column(length = 100)
	private String countyDepartmentName;
	@Column(length = 50)
	private String countyDepartmentNumber;
	@Column(length = 20)
	private String phoneNumber;
	@Column(length = 20)
	private String workPhoneNumber;
	@Column(length = 50)
	private String employeeNumber;
	@Column(length = 100)
	private String contractorName;
	@Column(length = 50)
	private String workOrderNumberInput;
	@Column(length = 100)
	private String expirationDate;
	
	//address Information
	@Column(length = 100)
	private String address;
	@Column(length = 50)
	private String city;
	@Column(length = 50)
	private String state;
	@Column(length = 20)
	private String zipCode;
	
	//Internet access
	private boolean countyWidePolicyA;
	private boolean countyWidePolicyB;
	private boolean allWebmail;
	private boolean streamMedia;
	@Column(length = 250)
	private String justification;
	
	//access information
	@Column(length = 50)
	private String ibmLogonId;
	@Column(length = 150)
	private String majorGroupCode;
	@Column(length = 150)
	private String lsoGroupCode;
	@Column(length = 250)
	private String securityAuthorization;
	
	@Column(length = 150)
	private String unixLogonId;
	@Column(length = 150)
	private String application;
	@Column(length = 150)
	private String accessGroup;
	
	@Column(length = 150)
	private String billingAccountNumber;
	//type was not implemeneted last year
	
	//additional information
	private boolean laCountyGovAccess;
	private boolean lacMobileWifiAccess;
	private boolean o360Email;
	
	
	// Added boolean to determine if request is submitted. 
	// Note: This differs from isComplete. User first submits request, then
	// Admin will 'complete' request after review.
	private boolean isSubmitted;
	
	// Added fields to store participant info.
	@Column(length = 150)
	private String managerFirstName;
	@Column(length = 150)
	private String managerLastName;
	@Column(length = 150)
	private String managerEmail;
	@Column(length = 20)
	private String managerPhone;
	
	// Below fields are only PATCHable by admin
	@Column(length = 150)
	private String divChiefManagerName;
	@Column(length = 150)
	private String divChiefManagerEmail;
	@Column(length = 20)
	private String divChiefManagerPhone;
	
	@Column(length = 150)
	private String departmentHeadName;
	@Column(length = 150)
	private String departmentHeadEmail;
	@Column(length = 20)
	private String departmentHeadPhone;
	
	@Column(length = 150)
	private String deptInfoSecurityOfficerName;
	@Column(length = 150)
	private String deptInfoSecurityOfficerEmail;
	@Column(length = 20)
	private String deptInfoSecurityOfficerPhone;
	
	@Column(length = 150)
	private String applicationCoordinatorName;
	@Column(length = 150)
	private String applicationCoordinatorEmail;
	@Column(length = 20)
	private String applicationCoordinatorPhone;

	

	public ServiceRequest() {
		String pattern = "yyyy-MM-dd hh:mm:ss";
		DateFormat d = new SimpleDateFormat(pattern);
		Date currentDate = Calendar.getInstance().getTime();
		this.createDate = d.format(currentDate);
		this.requestStatus = "Draft";
		this.eventHistory = new ArrayList<String>();
		this.eventHistory.add("(" + this.getCreateDate() + ") Request draft created");
		
		this.agreementId = "";
		//personal 
		this.lastName = "";
		this.firstName = "";
		this.middleInitial = "";
		this.emailAddress = "";
		this.employeeNumber = "";
		this.countyDepartmentName = "";
		this.countyDepartmentNumber = "";
		this.phoneNumber = "";
		this.workPhoneNumber ="";
		this.contractorName = "";
		this.emailAddress = "";
		this.contractorName ="";
		this.workOrderNumberInput = "";
		this.expirationDate ="";
		
		//address
		this.address = "";
		this.city = "";
		this.state = "";
		this.zipCode = "";
		
		//InternetAddress access
		this.justification = "";
		
		//accessInformation
		this.ibmLogonId = "";
		this.majorGroupCode = "";
		this.lsoGroupCode = "";
		this.securityAuthorization = "";
		
		this.unixLogonId = "";
		this.application = "";
		this.accessGroup = "";
		
		this.billingAccountNumber = "";
		
		//additional information
		this.o360Email = false;
		this.laCountyGovAccess = false;
		this.lacMobileWifiAccess = false;
		
	}

	

	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getSubmitDate() {
		return submitDate;
	}



	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}



	public boolean isEmployee() {
		return isEmployee;
	}



	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}



	public String getRequestStatus() {
		return requestStatus;
	}



	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}



	public String getAgreementId() {
		return agreementId;
	}



	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}



	public boolean isReplaceLostToken() {
		return replaceLostToken;
	}



	public void setReplaceLostToken(boolean replaceLostToken) {
		this.replaceLostToken = replaceLostToken;
	}



	public boolean isAddLogonId() {
		return addLogonId;
	}



	public void setAddLogonId(boolean addLogonId) {
		this.addLogonId = addLogonId;
	}



	public boolean isChangeLogonId() {
		return changeLogonId;
	}



	public void setChangeLogonId(boolean changeLogonId) {
		this.changeLogonId = changeLogonId;
	}



	public boolean isDeleteLogonId() {
		return deleteLogonId;
	}



	public void setDeleteLogonId(boolean deleteLogonId) {
		this.deleteLogonId = deleteLogonId;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getMiddleInitial() {
		return middleInitial;
	}



	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}



	public String getEmailAddress() {
		return emailAddress;
	}



	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getCountyDepartmentName() {
		return countyDepartmentName;
	}



	public void setCountyDepartmentName(String countyDepartmentName) {
		this.countyDepartmentName = countyDepartmentName;
	}



	public String getCountyDepartmentNumber() {
		return countyDepartmentNumber;
	}



	public void setCountyDepartmentNumber(String countyDepartmentNumber) {
		this.countyDepartmentNumber = countyDepartmentNumber;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}



	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}



	public String getEmployeeNumber() {
		return employeeNumber;
	}



	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}



	public String getContractorName() {
		return contractorName;
	}



	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}



	public String getWorkOrderNumberInput() {
		return workOrderNumberInput;
	}



	public void setWorkOrderNumberInput(String workOrderNumberInput) {
		this.workOrderNumberInput = workOrderNumberInput;
	}



	public String getExpirationDate() {
		return expirationDate;
	}



	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getZipCode() {
		return zipCode;
	}



	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}



	public boolean isCountyWidePolicyA() {
		return countyWidePolicyA;
	}



	public void setCountyWidePolicyA(boolean countyWidePolicyA) {
		this.countyWidePolicyA = countyWidePolicyA;
	}



	public boolean isCountyWidePolicyB() {
		return countyWidePolicyB;
	}



	public void setCountyWidePolicyB(boolean countyWidePolicyB) {
		this.countyWidePolicyB = countyWidePolicyB;
	}



	public boolean isAllWebmail() {
		return allWebmail;
	}



	public void setAllWebmail(boolean allWebmail) {
		this.allWebmail = allWebmail;
	}



	public boolean isStreamMedia() {
		return streamMedia;
	}



	public void setStreamMedia(boolean streamMedia) {
		this.streamMedia = streamMedia;
	}



	public String getJustification() {
		return justification;
	}



	public void setJustification(String justification) {
		this.justification = justification;
	}



	public String getIbmLogonId() {
		return ibmLogonId;
	}



	public void setIbmLogonId(String ibmLogonId) {
		this.ibmLogonId = ibmLogonId;
	}



	public String getMajorGroupCode() {
		return majorGroupCode;
	}



	public void setMajorGroupCode(String majorGroupCode) {
		this.majorGroupCode = majorGroupCode;
	}



	public String getLsoGroupCode() {
		return lsoGroupCode;
	}



	public void setLsoGroupCode(String lsoGroupCode) {
		this.lsoGroupCode = lsoGroupCode;
	}



	public String getSecurityAuthorization() {
		return securityAuthorization;
	}



	public void setSecurityAuthorization(String securityAuthorization) {
		this.securityAuthorization = securityAuthorization;
	}



	public String getUnixLogonId() {
		return unixLogonId;
	}



	public void setUnixLogonId(String unixLogonId) {
		this.unixLogonId = unixLogonId;
	}



	public String getApplication() {
		return application;
	}



	public void setApplication(String application) {
		this.application = application;
	}



	public String getAccessGroup() {
		return accessGroup;
	}



	public void setAccessGroup(String accessGroup) {
		this.accessGroup = accessGroup;
	}



	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}



	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}



	public boolean isLaCountyGovAccess() {
		return laCountyGovAccess;
	}



	public void setLaCountyGovAccess(boolean laCountyGovAccess) {
		this.laCountyGovAccess = laCountyGovAccess;
	}



	public boolean isLacMobileWifiAccess() {
		return lacMobileWifiAccess;
	}



	public void setLacMobileWifiAccess(boolean lacMobileWifiAccess) {
		this.lacMobileWifiAccess = lacMobileWifiAccess;
	}



	public boolean isO360Email() {
		return o360Email;
	}



	public void setO360Email(boolean o360Email) {
		this.o360Email = o360Email;
	}



	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) throws Exception {
		this.isComplete = isComplete;
	}

	public Integer getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(Integer requestNumber) {
		this.requestNumber = requestNumber;
	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}
	
	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}


	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}



	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getDivChiefManagerName() {
		return divChiefManagerName;
	}

	public void setDivChiefManagerName(String divChiefManagerName) {
		this.divChiefManagerName = divChiefManagerName;
	}

	public String getDivChiefManagerEmail() {
		return divChiefManagerEmail;
	}

	public void setDivChiefManagerEmail(String divChiefManagerEmail) {
		this.divChiefManagerEmail = divChiefManagerEmail;
	}

	public String getDivChiefManagerPhone() {
		return divChiefManagerPhone;
	}

	public void setDivChiefManagerPhone(String divChiefManagerPhone) {
		this.divChiefManagerPhone = divChiefManagerPhone;
	}

	public String getDepartmentHeadName() {
		return departmentHeadName;
	}

	public void setDepartmentHeadName(String departmentHeadName) {
		this.departmentHeadName = departmentHeadName;
	}

	public String getDepartmentHeadEmail() {
		return departmentHeadEmail;
	}

	public void setDepartmentHeadEmail(String departmentHeadEmail) {
		this.departmentHeadEmail = departmentHeadEmail;
	}

	public String getDepartmentHeadPhone() {
		return departmentHeadPhone;
	}

	public void setDepartmentHeadPhone(String departmentHeadPhone) {
		this.departmentHeadPhone = departmentHeadPhone;
	}

	public String getDeptInfoSecurityOfficerName() {
		return deptInfoSecurityOfficerName;
	}

	public void setDeptInfoSecurityOfficerName(String deptInfoSecurityOfficerName) {
		this.deptInfoSecurityOfficerName = deptInfoSecurityOfficerName;
	}

	public String getDeptInfoSecurityOfficerEmail() {
		return deptInfoSecurityOfficerEmail;
	}

	public void setDeptInfoSecurityOfficerEmail(String deptInfoSecurityOfficerEmail) {
		this.deptInfoSecurityOfficerEmail = deptInfoSecurityOfficerEmail;
	}

	public String getDeptInfoSecurityOfficerPhone() {
		return deptInfoSecurityOfficerPhone;
	}

	public void setDeptInfoSecurityOfficerPhone(String deptInfoSecurityOfficerPhone) {
		this.deptInfoSecurityOfficerPhone = deptInfoSecurityOfficerPhone;
	}

	public String getApplicationCoordinatorName() {
		return applicationCoordinatorName;
	}

	public void setApplicationCoordinatorName(String applicationCoordinatorName) {
		this.applicationCoordinatorName = applicationCoordinatorName;
	}

	public String getApplicationCoordinatorEmail() {
		return applicationCoordinatorEmail;
	}

	public void setApplicationCoordinatorEmail(String applicationCoordinatorEmail) {
		this.applicationCoordinatorEmail = applicationCoordinatorEmail;
	}

	public String getApplicationCoordinatorPhone() {
		return applicationCoordinatorPhone;
	}

	public void setApplicationCoordinatorPhone(String applicationCoordinatorPhone) {
		this.applicationCoordinatorPhone = applicationCoordinatorPhone;
	}

	public ArrayList<String> getEventHistory() {
		return eventHistory;
	}

	public void setEventHistory(ArrayList<String> eventHistory) {
		this.eventHistory = eventHistory;
	}

}