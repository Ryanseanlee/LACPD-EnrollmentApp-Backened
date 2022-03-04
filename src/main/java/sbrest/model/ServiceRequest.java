package sbrest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	private boolean newRegistration;
	private boolean deletePriorRegistration;
	private boolean updatePriorRegistration;
	private boolean replaceLostToken;
	private boolean addLogonId;
	private boolean changeLogonId;
	private boolean deleteLogonId;
	
	
	@Column(length = 5000) 
	private ArrayList<String> eventHistory;
	
	@Column(length = 50)
	private String lastName;
	@Column(length = 50)
	private String firstName;
	@Column(length = 5)
	private String middleInitial;
	@Column(length = 50)
	private String employeeNumber;
	@Column(length = 100)
	private String departmentName;
	@Column(length = 50)
	private String departmentNumber;
	@Column(length = 100)
	private String ContractorCompanyName;
	@Column(length = 100)
	private String companyEmailAddress;
	@Column(length = 100)
	private String countyEmailAddress;
	@Column(length = 100)
	private String employeeEmailAddress;
	@Column(length = 100)
	private String businessStreetAddress;
	@Column(length = 50)
	private String businessCity;
	@Column(length = 50)
	private String businessState;
	@Column(length = 20)
	private String businessZip;
	@Column(length = 20)
	private String businessPhoneNumber;
	@Column(length = 150)
	private String workMailingAddress;
	@Column(length = 100)
	private String companyStreetAddress;
	@Column(length = 50)
	private String companyCity;
	@Column(length = 50)
	private String companyState;
	@Column(length = 20)
	private String companyZip;
	@Column(length = 20)
	private String companyPhoneNumber;
	@Column(length = 20)
	private String countyPhoneNumber;
	@Column(length = 50)
	private String ContractorWorkOrder;
	@Column(length = 20)
	private String ContractorExperationDate;
	@Column(length = 50)
	private String ibmLogOnId;
	@Column(length = 150)
	private String majorGroupCode;
	@Column(length = 150)
	private String lsoGroupCode;
	@Column(length = 250)
	private String securityAuthorization;
//	private boolean tsoAccess;
	@Column(length = 150)
	private String tsoGroupCode;
	@Column(length = 50)
	private String binNumber;
	@Column(length = 150)
	private String subGroup1;
	@Column(length = 150)
	private String subGroup2;
	@Column(length = 150)
	private String subGroup3;
//	private boolean onlineAccess;
	@Column(length = 250)
	private String systemApplication;
	@Column(length = 150)
	private String groupName;
	@Column(length = 150)
	private String oldGroup;
	private boolean unixAddLogonId;
	private boolean unixChangeLogonId;
	private boolean unixDeleteLogonId;
	@Column(length = 150)
	private String unixLogOnId;
	@Column(length = 150)
	private String unixApplication;
	@Column(length = 150)
	private String unixAccessGroup;
	@Column(length = 150)
	private String unixAccountNumber;
	@Column(length = 150)
	private String BillingAccount;
	private boolean securIdVpn;
	private boolean adaptiveAuthenticationVpn;
	private boolean internetApplication;
	private boolean exchangeEmail;
	private boolean emailEncryption;
	private boolean laCountyGovAccess;
	private boolean tokenlessAuthentication;
	private boolean lacMobileWifiAccess;
	private boolean cherwellSms;
	private boolean windowsRightsMgmt;
	private boolean gmailAccess;
	private boolean yahooMailAccess;
	private boolean otherEmailAccess;
	@Column(length = 100)
	private String otherEmailDomain;
	@Column(length = 250)
	private String BusinessJustification1;
	private boolean defaultCountyWidePolicy;
	private boolean departmentPolicyRule0;
	private boolean departmentPolicyRule1;
	private boolean departmentPolicyRule2;
	private boolean departmentPolicyRule3;
	private boolean departmentPolicyRule4;
	private boolean socialNetworkingFacebook;
	private boolean socialNetworkingTwitter;
	private boolean socialNetworkingLinkedIn;
	
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
	@Column(length = 150)
	private String managerTitle;
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
	
	//Newly added
	private boolean AllWebmail;
	private boolean Android;
	private boolean Computer;
	private boolean ContractorWorkforce1;
	private boolean CountywidePolicyA;
	private boolean CountywidePolicyB;
	private boolean DeleteRequest;
	private boolean EmployeeContractor;
	private boolean EmployeeWorkforce;
	private boolean HardTokenVPN;
	private boolean IOS;
	private boolean LACMobile;
	private boolean LACounty;
	private boolean NewRequest;
	private boolean NewToken;
	private boolean O365Email;
	private boolean OnlineAccess;
	private boolean RenewToken;
	private boolean ReplaceDefectiveToken;
	private boolean ReplaceLostStolenToken;
	private boolean SoftwareToken;
	private boolean StreamingMedia;
	private boolean TokenlessAuth;
	private boolean TsoAccess;
	private boolean UpdateRequest;
	private boolean addNewLoginId;
	private boolean deleteLoginId1;
	private boolean deleteLoginId2;
	private boolean newLoginId;
	private boolean updateLoginId1;
	private boolean updateLoginId2;
	
	@Column(length = 250)
	private String businessJustification2;
	@Column(length = 150)
	private String CountyDepartmentName;
	@Column(length = 50)
	private String CountyWorkforceId1;
	@Column(length = 150)
	private String CountyWorkforceName1;
	@Column(length = 150)
	private String CustomerName2;
	@Column(length = 150)
	private String DepartmentCoord1;
	@Column(length = 150)
	private String DepartmentInfo;
	@Column(length = 150)
	private String EmailAddress;
	@Column(length = 20)
	private String ExperationDate;
	@Column(length = 150)
	private String ManagerName1;
	@Column(length = 150)
	private String ManagerName2;
	@Column(length = 150)
	private String ManagerName3;
	@Column(length = 150)
	private String ManagerTitle1;
	@Column(length = 150)
	private String Name1;
	@Column(length = 20)
	private String Phone1;
	@Column(length = 20)
	private String Phone2;
	@Column(length = 20)
	private String Phone3;
	@Column(length = 20)
	private String Phone4;
	@Column(length = 20)
	private String Phone5;
	@Column(length = 20)
	private String Phone6;
	@Column(length = 150)
	private String accessGroup1;
	@Column(length = 150)
	private String application1;
	@Column(length = 50)
	private String countyDepartmentNumber;
	private String dateOfRequest;
	@Column(length = 150)
	private String digitLSOGroupCode;
	@Column(length = 150)
	private String digitMajorGroupCode;
	@Column(length = 150)
	private String digitTSOGrpCode;
	@Column(length = 50)
	private String employeeContractor;
	@Column(length = 150)
	private String grpName1;
	@Column(length = 150)
	private String grpName2;
	@Column(length = 150)
	private String grpName3;
	@Column(length = 150)
	private String lastNamefirstNameMi;
	@Column(length = 50)
	private String loginId1;
	@Column(length = 50)
	private String loginId2;
	@Column(length = 150)
	private String oldGrp1;
	@Column(length = 150)
	private String oldGrp2;
	@Column(length = 150)
	private String oldGrp3;
	@Column(length = 250)
	private String systemApplication1;
	@Column(length = 250)
	private String systemApplication2;
	@Column(length = 250)
	private String systemApplication3;
	@Column(length = 20)
	private String workPhoneNumber;
	
	//Unsure
	@Column(length = 100)
	private String jai;
	@Column(length = 100)
	private String dmv;
	@Column(length = 100)
	private String aps;
	
	
	//Unsure Field Type is Signature
//	@Column(length = 100)
//	private String CountWorkforceSig1;
//	@Column(length = 100)
//	private String CustomerSig;
//	@Column(length = 100)
//	private String CustomerSig2;
//	@Column(length = 100)
//	private String DepartmentCoordSig1;
//	@Column(length = 100)
//	private String DepartmentInfoSig;
//	@Column(length = 100)
//	private String DepartmentInfoSig2;
//	@Column(length = 100)
//	private String ManagerSig1;
//	@Column(length = 100)
//	private String ManagerSig2;
//	@Column(length = 100)
//	private String ManagerSig3;
//	@Column(length = 100)
//	private String ManagerSig4;
//	@Column(length = 100)
//	private String ManagerSig5;
	
	
	//Unsure Field Type Date
//	@Column(length = 100)
//	private String Date1;
//	@Column(length = 100)
//	private String Date2;
//	@Column(length = 100)
//	private String Date3;
//	@Column(length = 100)
//	private String Date4;
//	@Column(length = 100)
//	private String Date5;
//	@Column(length = 100)
//	private String Date6;
//	@Column(length = 100)
//	private String Date7;
//	@Column(length = 100)
//	private String Date8;
	

	



	
	
	
	
	public ServiceRequest() {
		String pattern = "yyyy-MM-dd hh:mm:ss";
		DateFormat d = new SimpleDateFormat(pattern);
		Date currentDate = Calendar.getInstance().getTime();
		this.createDate = d.format(currentDate);
		this.requestStatus = "Draft";
		this.eventHistory = new ArrayList<String>();
		this.eventHistory.add("(" + this.getCreateDate() + ") Request draft created");
		
		this.agreementId = "";
		this.lastName = "";
		this.firstName = "";
		this.middleInitial = "";
		this.employeeNumber = "";
		this.departmentName = "";
		this.departmentNumber = "";
		this.ContractorCompanyName = "";
		this.companyEmailAddress = "";
		this.countyEmailAddress = "";
		this.employeeEmailAddress = "";
		this.businessStreetAddress = "";
		this.businessCity = "";
		this.businessState = "";
		this.businessZip = "";
		this.businessPhoneNumber = "";
		this.workMailingAddress = "";
		this.companyStreetAddress = "";
		this.companyCity = "";
		this.companyState = "";
		this.companyZip = "";
		this.companyPhoneNumber = "";
		this.countyPhoneNumber = "";
		this.ContractorWorkOrder = "";
		this.ContractorExperationDate = "";
		this.ibmLogOnId = "";
		this.majorGroupCode = "";
		this.lsoGroupCode = "";
		this.securityAuthorization = "";
		this.tsoGroupCode = "";
		this.binNumber = "";
		this.subGroup1 = "";
		this.subGroup2 = "";
		this.subGroup3 = "";
		this.systemApplication = "";
		this.groupName = "";
		this.oldGroup = "";
		this.unixLogOnId = "";
		this.unixApplication = "";
		this.unixAccessGroup = "";
		this.unixAccountNumber = "";
		this.BillingAccount = "";
		this.otherEmailDomain = "";
		this.BusinessJustification1 = "";
		this.businessJustification2 = "";
		this.CountyDepartmentName = "";
		this.CountyWorkforceId1 = "";
		this.CountyWorkforceName1 = "";
		this.EmailAddress = "";
		this.ExperationDate = "";
		this.Phone1= "";
		this.Phone2= "";
		this.Phone3= "";
		this.Phone4= "";
		this.Phone5= "";
		this.Phone6= "";
		this.accessGroup1 = "";
		this.application1 = "";
		this.countyDepartmentNumber = "";
		this.grpName1 = "";
		this.grpName2 = "";
		this.grpName3 = "";
		this.digitLSOGroupCode = "";
		this.digitMajorGroupCode = "";
		this.digitTSOGrpCode = "";
		this.loginId1 = "";
		this.loginId2 = "";
		this.oldGrp1 = "";
		this.oldGrp2 = "";
		this.oldGrp3 = "";
		this.systemApplication1 = "";
		this.systemApplication2 = "";
		this.systemApplication3 = "";
		this.setWorkPhoneNumber("");
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

	public boolean isNewRegistration() {
		return newRegistration;
	}

	public void setNewRegistration(boolean newRegistration) {
		this.newRegistration = newRegistration;
	}

	public boolean isDeletePriorRegistration() {
		return deletePriorRegistration;
	}

	public void setDeletePriorRegistration(boolean deletePriorRegistration) {
		this.deletePriorRegistration = deletePriorRegistration;
	}

	public boolean isUpdatePriorRegistration() {
		return updatePriorRegistration;
	}

	public void setUpdatePriorRegistration(boolean updatePriorRegistration) {
		this.updatePriorRegistration = updatePriorRegistration;
	}

	public boolean isAllWebmail() {
		return AllWebmail;
	}

	public void setAllWebmail(boolean allWebmail) {
		this.AllWebmail = allWebmail;
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

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getContractorCompanyName() {
		return ContractorCompanyName;
	}

	public void setContractorCompanyName(String ContractorCompanyName) {
		this.ContractorCompanyName = ContractorCompanyName;
	}

	public String getCompanyEmailAddress() {
		return companyEmailAddress;
	}

	public void setCompanyEmailAddress(String companyEmailAddress) {
		this.companyEmailAddress = companyEmailAddress;
	}

	public String getCountyEmailAddress() {
		return countyEmailAddress;
	}

	public void setCountyEmailAddress(String countyEmailAddress) {
		this.countyEmailAddress = countyEmailAddress;
	}

	public String getEmployeeEmailAddress() {
		return employeeEmailAddress;
	}

	public void setEmployeeEmailAddress(String employeeEmailAddress) {
		this.employeeEmailAddress = employeeEmailAddress;
	}

	public String getBusinessStreetAddress() {
		return businessStreetAddress;
	}

	public void setBusinessStreetAddress(String businessStreetAddress) {
		this.businessStreetAddress = businessStreetAddress;
	}

	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}

	public String getBusinessState() {
		return businessState;
	}

	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}

	public String getBusinessZip() {
		return businessZip;
	}

	public void setBusinessZip(String businessZip) {
		this.businessZip = businessZip;
	}

	public String getBusinessPhoneNumber() {
		return businessPhoneNumber;
	}

	public void setBusinessPhoneNumber(String businessPhoneNumber) {
		this.businessPhoneNumber = businessPhoneNumber;
	}

	public String getWorkMailingAddress() {
		return workMailingAddress;
	}

	public void setWorkMailingAddress(String workMailingAddress) {
		this.workMailingAddress = workMailingAddress;
	}

	public String getCompanyStreetAddress() {
		return companyStreetAddress;
	}

	public void setCompanyStreetAddress(String companyStreetAddress) {
		this.companyStreetAddress = companyStreetAddress;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyState() {
		return companyState;
	}

	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}

	public String getCompanyZip() {
		return companyZip;
	}

	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public String getCountyPhoneNumber() {
		return countyPhoneNumber;
	}

	public void setCountyPhoneNumber(String countyPhoneNumber) {
		this.countyPhoneNumber = countyPhoneNumber;
	}

	public String getContractorWorkOrder() {
		return ContractorWorkOrder;
	}

	public void setContractorWorkOrder(String ContractorWorkOrder) {
		this.ContractorWorkOrder = ContractorWorkOrder;
	}

	public String getContractorExperationDate() {
		return ContractorExperationDate;
	}

	public void setContractorExperationDate(String ContractorExperationDate) {
		this.ContractorExperationDate = ContractorExperationDate;
	}

	public String getIbmLogOnId() {
		return ibmLogOnId;
	}

	public void setIbmLogOnId(String ibmLogOnId) {
		this.ibmLogOnId = ibmLogOnId;
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

//	public boolean isTsoAccess() {
//		return tsoAccess;
//	}
//
//	public void setTsoAccess(boolean tsoAccess) {
//		this.tsoAccess = tsoAccess;
//	}

	public String getTsoGroupCode() {
		return tsoGroupCode;
	}

	public void setTsoGroupCode(String tsoGroupCode) {
		this.tsoGroupCode = tsoGroupCode;
	}

	public String getBinNumber() {
		return binNumber;
	}

	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}

	public String getSubGroup1() {
		return subGroup1;
	}

	public void setSubGroup1(String subGroup1) {
		this.subGroup1 = subGroup1;
	}

	public String getSubGroup2() {
		return subGroup2;
	}

	public void setSubGroup2(String subGroup2) {
		this.subGroup2 = subGroup2;
	}

	public String getSubGroup3() {
		return subGroup3;
	}

	public void setSubGroup3(String subGroup3) {
		this.subGroup3 = subGroup3;
	}

//	public boolean isOnlineAccess() {
//		return onlineAccess;
//	}
//
//	public void setOnlineAccess(boolean onlineAccess) {
//		this.onlineAccess = onlineAccess;
//	}

	public String getSystemApplication() {
		return systemApplication;
	}

	public void setSystemApplication(String systemApplication) {
		this.systemApplication = systemApplication;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOldGroup() {
		return oldGroup;
	}

	public void setOldGroup(String oldGroup) {
		this.oldGroup = oldGroup;
	}

	public boolean isUnixAddLogonId() {
		return unixAddLogonId;
	}

	public void setUnixAddLogonId(boolean unixAddLogonId) {
		this.unixAddLogonId = unixAddLogonId;
	}

	public boolean isUnixChangeLogonId() {
		return unixChangeLogonId;
	}

	public void setUnixChangeLogonId(boolean unixChangeLogonId) {
		this.unixChangeLogonId = unixChangeLogonId;
	}

	public boolean isUnixDeleteLogonId() {
		return unixDeleteLogonId;
	}

	public void setUnixDeleteLogonId(boolean unixDeleteLogonId) {
		this.unixDeleteLogonId = unixDeleteLogonId;
	}

	public String getUnixLogOnId() {
		return unixLogOnId;
	}

	public void setUnixLogOnId(String unixLogOnId) {
		this.unixLogOnId = unixLogOnId;
	}

	public String getUnixApplication() {
		return unixApplication;
	}

	public void setUnixApplication(String unixApplication) {
		this.unixApplication = unixApplication;
	}

	public String getUnixAccessGroup() {
		return unixAccessGroup;
	}

	public void setUnixAccessGroup(String unixAccessGroup) {
		this.unixAccessGroup = unixAccessGroup;
	}

	public String getUnixAccountNumber() {
		return unixAccountNumber;
	}

	public void setUnixAccountNumber(String unixAccountNumber) {
		this.unixAccountNumber = unixAccountNumber;
	}

	public String getBillingAccount() {
		return BillingAccount;
	}

	public void setBillingAccount(String BillingAccount) {
		this.BillingAccount = BillingAccount;
	}

	public boolean isSecurIdVpn() {
		return securIdVpn;
	}

	public void setSecurIdVpn(boolean securIdVpn) {
		this.securIdVpn = securIdVpn;
	}

	public boolean isAdaptiveAuthenticationVpn() {
		return adaptiveAuthenticationVpn;
	}

	public void setAdaptiveAuthenticationVpn(boolean adaptiveAuthenticationVpn) {
		this.adaptiveAuthenticationVpn = adaptiveAuthenticationVpn;
	}

	public boolean isInternetApplication() {
		return internetApplication;
	}

	public void setInternetApplication(boolean internetApplication) {
		this.internetApplication = internetApplication;
	}

	public boolean isExchangeEmail() {
		return exchangeEmail;
	}

	public void setExchangeEmail(boolean exchangeEmail) {
		this.exchangeEmail = exchangeEmail;
	}

	public boolean isEmailEncryption() {
		return emailEncryption;
	}

	public void setEmailEncryption(boolean emailEncryption) {
		this.emailEncryption = emailEncryption;
	}

	public boolean isLaCountyGovAccess() {
		return laCountyGovAccess;
	}

	public void setLaCountyGovAccess(boolean laCountyGovAccess) {
		this.laCountyGovAccess = laCountyGovAccess;
	}

	public boolean isTokenlessAuthentication() {
		return tokenlessAuthentication;
	}

	public void setTokenlessAuthentication(boolean tokenlessAuthentication) {
		this.tokenlessAuthentication = tokenlessAuthentication;
	}

	public boolean isLacMobileWifiAccess() {
		return lacMobileWifiAccess;
	}

	public void setLacMobileWifiAccess(boolean lacMobileWifiAccess) {
		this.lacMobileWifiAccess = lacMobileWifiAccess;
	}

	public boolean isCherwellSms() {
		return cherwellSms;
	}

	public void setCherwellSms(boolean cherwellSms) {
		this.cherwellSms = cherwellSms;
	}

	public boolean isWindowsRightsMgmt() {
		return windowsRightsMgmt;
	}

	public void setWindowsRightsMgmt(boolean windowsRightsMgmt) {
		this.windowsRightsMgmt = windowsRightsMgmt;
	}

	public boolean isGmailAccess() {
		return gmailAccess;
	}

	public void setGmailAccess(boolean gmailAccess) {
		this.gmailAccess = gmailAccess;
	}

	public boolean isYahooMailAccess() {
		return yahooMailAccess;
	}

	public void setYahooMailAccess(boolean yahooMailAccess) {
		this.yahooMailAccess = yahooMailAccess;
	}

	public boolean isOtherEmailAccess() {
		return otherEmailAccess;
	}

	public void setOtherEmailAccess(boolean otherEmailAccess) {
		this.otherEmailAccess = otherEmailAccess;
	}

	public String getOtherEmailDomain() {
		return otherEmailDomain;
	}

	public void setOtherEmailDomain(String otherEmailDomain) {
		this.otherEmailDomain = otherEmailDomain;
	}

	public String getBusinessJustification1() {
		return BusinessJustification1;
	}

	public void setBusinessJustification1(String BusinessJustification1) {
		this.BusinessJustification1 = BusinessJustification1;
	}

	public boolean isDefaultCountyWidePolicy() {
		return defaultCountyWidePolicy;
	}

	public void setDefaultCountyWidePolicy(boolean defaultCountyWidePolicy) {
		this.defaultCountyWidePolicy = defaultCountyWidePolicy;
	}

	public boolean isDepartmentPolicyRule0() {
		return departmentPolicyRule0;
	}

	public void setDepartmentPolicyRule0(boolean departmentPolicyRule0) {
		this.departmentPolicyRule0 = departmentPolicyRule0;
	}

	public boolean isDepartmentPolicyRule1() {
		return departmentPolicyRule1;
	}

	public void setDepartmentPolicyRule1(boolean departmentPolicyRule1) {
		this.departmentPolicyRule1 = departmentPolicyRule1;
	}

	public boolean isDepartmentPolicyRule2() {
		return departmentPolicyRule2;
	}

	public void setDepartmentPolicyRule2(boolean departmentPolicyRule2) {
		this.departmentPolicyRule2 = departmentPolicyRule2;
	}

	public boolean isDepartmentPolicyRule3() {
		return departmentPolicyRule3;
	}

	public void setDepartmentPolicyRule3(boolean departmentPolicyRule3) {
		this.departmentPolicyRule3 = departmentPolicyRule3;
	}

	public boolean isDepartmentPolicyRule4() {
		return departmentPolicyRule4;
	}

	public void setDepartmentPolicyRule4(boolean departmentPolicyRule4) {
		this.departmentPolicyRule4 = departmentPolicyRule4;
	}

	public boolean isSocialNetworkingFacebook() {
		return socialNetworkingFacebook;
	}

	public void setSocialNetworkingFacebook(boolean socialNetworkingFacebook) {
		this.socialNetworkingFacebook = socialNetworkingFacebook;
	}

	public boolean isSocialNetworkingTwitter() {
		return socialNetworkingTwitter;
	}

	public void setSocialNetworkingTwitter(boolean socialNetworkingTwitter) {
		this.socialNetworkingTwitter = socialNetworkingTwitter;
	}

	public boolean isSocialNetworkingLinkedIn() {
		return socialNetworkingLinkedIn;
	}

	public void setSocialNetworkingLinkedIn(boolean socialNetworkingLinkedIn) {
		this.socialNetworkingLinkedIn = socialNetworkingLinkedIn;
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

	public String getManagerTitle() {
		return managerTitle;
	}

	public void setManagerTitle(String managerTitle) {
		this.managerTitle = managerTitle;
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

	public boolean isAndroid() {
		return Android;
	}

	public void setAndroid(boolean android) {
		this.Android = android;
	}

	public boolean isComputer() {
		return Computer;
	}

	public void setComputer(boolean computer) {
		this.Computer = computer;
	}

	public String getBusinessJustification2() {
		return businessJustification2;
	}

	public void setBusinessJustification2(String businessJustification2) {
		this.businessJustification2 = businessJustification2;
	}

	public boolean isContractorWorkforce1() {
		return ContractorWorkforce1;
	}

	public void setContractorWorkforce1(boolean contractorWorkforce1) {
		this.ContractorWorkforce1 = contractorWorkforce1;
	}

	public String getCountyDepartmentName() {
		return CountyDepartmentName;
	}

	public void setCountyDepartmentName(String countyDepartmentName) {
		this.CountyDepartmentName = countyDepartmentName;
	}

	public String getCountyWorkforceId1() {
		return CountyWorkforceId1;
	}

	public void setCountyWorkforceId1(String countyWorkforceId1) {
		this.CountyWorkforceId1 = countyWorkforceId1;
	}

	public String getCountyWorkforceName1() {
		return CountyWorkforceName1;
	}

	public void setCountyWorkforceName1(String countyWorkforceName1) {
		this.CountyWorkforceName1 = countyWorkforceName1;
	}

	public boolean isCountywidePolicyA() {
		return CountywidePolicyA;
	}

	public void setCountywidePolicyA(boolean countywidePolicyA) {
		this.CountywidePolicyA = countywidePolicyA;
	}

	public boolean isCountywidePolicyB() {
		return CountywidePolicyB;
	}

	public void setCountywidePolicyB(boolean countywidePolicyB) {
		this.CountywidePolicyB = countywidePolicyB;
	}

	public String getCustomerName2() {
		return CustomerName2;
	}

	public void setCustomerName2(String customerName2) {
		this.CustomerName2 = customerName2;
	}

	public boolean isDeleteRequest() {
		return DeleteRequest;
	}

	public void setDeleteRequest(boolean deleteRequest) {
		this.DeleteRequest = deleteRequest;
	}

	public String getDepartmentCoord1() {
		return DepartmentCoord1;
	}

	public void setDepartmentCoord1(String departmentCoord1) {
		this.DepartmentCoord1 = departmentCoord1;
	}

	public String getDepartmentInfo() {
		return DepartmentInfo;
	}

	public void setDepartmentInfo(String departmentInfo) {
		this.DepartmentInfo = departmentInfo;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.EmailAddress = emailAddress;
	}

	public boolean isEmployeeContractor() {
		return EmployeeContractor;
	}

	public void setEmployeeContractor(boolean employeeContractor) {
		this.EmployeeContractor = employeeContractor;
	}

	public boolean isEmployeeWorkforce() {
		return EmployeeWorkforce;
	}

	public void setEmployeeWorkforce(boolean employeeWorkforce) {
		this.EmployeeWorkforce = employeeWorkforce;
	}

	public String getExperationDate() {
		return ExperationDate;
	}

	public void setExperationDate(String experationDate) {
		this.ExperationDate = experationDate;
	}

	public boolean isHardTokenVPN() {
		return HardTokenVPN;
	}

	public void setHardTokenVPN(boolean hardTokenVPN) {
		this.HardTokenVPN = hardTokenVPN;
	}

	public boolean isIOS() {
		return IOS;
	}

	public void setIOS(boolean iOS) {
		this.IOS = iOS;
	}

	public boolean isLACMobile() {
		return LACMobile;
	}

	public void setLACMobile(boolean lACMobile) {
		this.LACMobile = lACMobile;
	}

	public boolean isLACounty() {
		return LACounty;
	}

	public void setLACounty(boolean lACounty) {
		this.LACounty = lACounty;
	}

	public String getManagerName1() {
		return ManagerName1;
	}

	public void setManagerName1(String managerName1) {
		this.ManagerName1 = managerName1;
	}

	public String getManagerName2() {
		return ManagerName2;
	}

	public void setManagerName2(String managerName2) {
		this.ManagerName2 = managerName2;
	}

	public String getManagerName3() {
		return ManagerName3;
	}

	public void setManagerName3(String managerName3) {
		this.ManagerName3 = managerName3;
	}

	public String getManagerTitle1() {
		return ManagerTitle1;
	}

	public void setManagerTitle1(String managerTitle1) {
		this.ManagerTitle1 = managerTitle1;
	}

	public String getName1() {
		return Name1;
	}

	public void setName1(String name1) {
		this.Name1 = name1;
	}

	public boolean isNewRequest() {
		return NewRequest;
	}

	public void setNewRequest(boolean newRequest) {
		this.NewRequest = newRequest;
	}

	public boolean isNewToken() {
		return NewToken;
	}

	public void setNewToken(boolean newToken) {
		this.NewToken = newToken;
	}

	public boolean isO365Email() {
		return O365Email;
	}

	public void setO365Email(boolean o365Email) {
		this.O365Email = o365Email;
	}

	public boolean isOnlineAccess() {
		return OnlineAccess;
	}

	public void setOnlineAccess(boolean onlineAccess) {
		this.OnlineAccess = onlineAccess;
	}

	public String getPhone1() {
		return Phone1;
	}

	public void setPhone1(String phone1) {
		this.Phone1 = phone1;
	}

	public String getPhone2() {
		return Phone2;
	}

	public void setPhone2(String phone2) {
		this.Phone2 = phone2;
	}

	public String getPhone3() {
		return Phone3;
	}

	public void setPhone3(String phone3) {
		this.Phone3 = phone3;
	}

	public String getPhone4() {
		return Phone4;
	}

	public void setPhone4(String phone4) {
		this.Phone4 = phone4;
	}

	public String getPhone5() {
		return Phone5;
	}

	public void setPhone5(String phone5) {
		this.Phone5 = phone5;
	}

	public String getPhone6() {
		return Phone6;
	}

	public void setPhone6(String phone6) {
		this.Phone6 = phone6;
	}

	public boolean isRenewToken() {
		return RenewToken;
	}

	public void setRenewToken(boolean renewToken) {
		this.RenewToken = renewToken;
	}

	public boolean isReplaceDefectiveToken() {
		return ReplaceDefectiveToken;
	}

	public void setReplaceDefectiveToken(boolean replaceDefectiveToken) {
		this.ReplaceDefectiveToken = replaceDefectiveToken;
	}

	public boolean isReplaceLostStolenToken() {
		return ReplaceLostStolenToken;
	}

	public void setReplaceLostStolenToken(boolean replaceLostStolenToken) {
		this.ReplaceLostStolenToken = replaceLostStolenToken;
	}

	public boolean isSoftwareToken() {
		return SoftwareToken;
	}

	public void setSoftwareToken(boolean softwareToken) {
		this.SoftwareToken = softwareToken;
	}

	public boolean isStreamingMedia() {
		return StreamingMedia;
	}

	public void setStreamingMedia(boolean streamingMedia) {
		this.StreamingMedia = streamingMedia;
	}

	public boolean isTokenlessAuth() {
		return TokenlessAuth;
	}

	public void setTokenlessAuth(boolean tokenlessAuth) {
		this.TokenlessAuth = tokenlessAuth;
	}

	public boolean isTsoAccess() {
		return TsoAccess;
	}

	public void setTsoAccess(boolean tsoAccess) {
		this.TsoAccess = tsoAccess;
	}

	public boolean isUpdateRequest() {
		return UpdateRequest;
	}

	public void setUpdateRequest(boolean updateRequest) {
		this.UpdateRequest = updateRequest;
	}

	public String getAccessGroup1() {
		return accessGroup1;
	}

	public void setAccessGroup1(String accessGroup1) {
		this.accessGroup1 = accessGroup1;
	}

	public boolean isAddNewLoginId() {
		return addNewLoginId;
	}

	public void setAddNewLoginId(boolean addNewLoginId) {
		this.addNewLoginId = addNewLoginId;
	}

	public String getApplication1() {
		return application1;
	}

	public void setApplication1(String application1) {
		this.application1 = application1;
	}

	public String getAps() {
		return aps;
	}

	public void setAps(String aps) {
		this.aps = aps;
	}

	public String getCountyDepartmentNumber() {
		return countyDepartmentNumber;
	}

	public void setCountyDepartmentNumber(String countyDepartmentNumber) {
		this.countyDepartmentNumber = countyDepartmentNumber;
	}

	public String getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(String dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public boolean isDeleteLoginId1() {
		return deleteLoginId1;
	}

	public void setDeleteLoginId1(boolean deleteLoginId1) {
		this.deleteLoginId1 = deleteLoginId1;
	}

	public boolean isDeleteLoginId2() {
		return deleteLoginId2;
	}

	public void setDeleteLoginId2(boolean deleteLoginId2) {
		this.deleteLoginId2 = deleteLoginId2;
	}

	public String getDigitLSOGroupCode() {
		return digitLSOGroupCode;
	}

	public void setDigitLSOGroupCode(String digitLSOGroupCode) {
		this.digitLSOGroupCode = digitLSOGroupCode;
	}

	public String getDigitMajorGroupCode() {
		return digitMajorGroupCode;
	}

	public void setDigitMajorGroupCode(String digitMajorGroupCode) {
		this.digitMajorGroupCode = digitMajorGroupCode;
	}

	public String getDigitTSOGrpCode() {
		return digitTSOGrpCode;
	}

	public void setDigitTSOGrpCode(String digitTSOGrpCode) {
		this.digitTSOGrpCode = digitTSOGrpCode;
	}

	public String getDmv() {
		return dmv;
	}

	public void setDmv(String dmv) {
		this.dmv = dmv;
	}

	public String getEmployeeContractor() {
		return employeeContractor;
	}

	public void setEmployeeContractor(String employeeContractor) {
		this.employeeContractor = employeeContractor;
	}

	public String getGrpName1() {
		return grpName1;
	}

	public void setGrpName1(String grpName1) {
		this.grpName1 = grpName1;
	}

	public String getGrpName2() {
		return grpName2;
	}

	public void setGrpName2(String grpName2) {
		this.grpName2 = grpName2;
	}

	public String getGrpName3() {
		return grpName3;
	}

	public void setGrpName3(String grpName3) {
		this.grpName3 = grpName3;
	}

	public String getJai() {
		return jai;
	}

	public void setJai(String jai) {
		this.jai = jai;
	}

	public String getLastNamefirstNameMi() {
		return lastNamefirstNameMi;
	}

	public void setLastNamefirstNameMi(String lastNamefirstNameMi) {
		this.lastNamefirstNameMi = lastNamefirstNameMi;
	}

	public String getLoginId1() {
		return loginId1;
	}

	public void setLoginId1(String loginId1) {
		this.loginId1 = loginId1;
	}

	public String getLoginId2() {
		return loginId2;
	}

	public void setLoginId2(String loginId2) {
		this.loginId2 = loginId2;
	}

	public boolean isNewLoginId() {
		return newLoginId;
	}

	public void setNewLoginId(boolean newLoginId) {
		this.newLoginId = newLoginId;
	}

	public String getOldGrp1() {
		return oldGrp1;
	}

	public void setOldGrp1(String oldGrp1) {
		this.oldGrp1 = oldGrp1;
	}

	public String getOldGrp2() {
		return oldGrp2;
	}

	public void setOldGrp2(String oldGrp2) {
		this.oldGrp2 = oldGrp2;
	}

	public String getOldGrp3() {
		return oldGrp3;
	}

	public void setOldGrp3(String oldGrp3) {
		this.oldGrp3 = oldGrp3;
	}

	public String getSystemApplication1() {
		return systemApplication1;
	}

	public void setSystemApplication1(String systemApplication1) {
		this.systemApplication1 = systemApplication1;
	}

	public String getSystemApplication2() {
		return systemApplication2;
	}

	public void setSystemApplication2(String systemApplication2) {
		this.systemApplication2 = systemApplication2;
	}

	public String getSystemApplication3() {
		return systemApplication3;
	}

	public void setSystemApplication3(String systemApplication3) {
		this.systemApplication3 = systemApplication3;
	}

	public boolean isUpdateLoginId1() {
		return updateLoginId1;
	}

	public void setUpdateLoginId1(boolean updateLoginId1) {
		this.updateLoginId1 = updateLoginId1;
	}

	public boolean isUpdateLoginId2() {
		return updateLoginId2;
	}

	public void setUpdateLoginId2(boolean updateLoginId2) {
		this.updateLoginId2 = updateLoginId2;
	}

	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}




	
	
	//Dates

//	public String getDate1() {
//		return Date1;
//	}
//
//	public void setDate1(String date1) {
//		this.Date1 = date1;
//	}
//
//	public String getDate2() {
//		return Date2;
//	}
//
//	public void setDate2(String date2) {
//		this.Date2 = date2;
//	}
//
//	public String getDate3() {
//		return Date3;
//	}
//
//	public void setDate3(String date3) {
//		this.Date3 = date3;
//	}
//
//	public String getDate4() {
//		return Date4;
//	}
//
//	public void setDate4(String date4) {
//		this.Date4 = date4;
//	}
//
//	public String getDate5() {
//		return Date5;
//	}
//
//	public void setDate5(String date5) {
//		this.Date5 = date5;
//	}
//
//	public String getDate6() {
//		return Date6;
//	}
//
//	public void setDate6(String date6) {
//		this.Date6 = date6;
//	}
//
//	public String getDate7() {
//		return Date7;
//	}
//
//	public void setDate7(String date7) {
//		this.Date7 = date7;
//	}
//
//	public String getDate8() {
//		return Date8;
//	}
//
//	public void setDate8(String date8) {
//		this.Date8 = date8;
//	}

	//Signatures
//	public String getCustomerSig() {
//		return CustomerSig;
//	}
//
//	public void setCustomerSig(String customerSig) {
//		this.CustomerSig = customerSig;
//	}
//
//	public String getCustomerSig2() {
//		return CustomerSig2;
//	}
//
//	public void setCustomerSig2(String customerSig2) {
//		this.CustomerSig2 = customerSig2;
//	}

//	public String getCountWorkforceSig1() {
//		return CountWorkforceSig1;
//	}
//
//	public void setCountWorkforceSig1(String countWorkforceSig1) {
//		this.CountWorkforceSig1 = countWorkforceSig1;
//	}
//	public String getDepartmentCoordSig1() {
//		return DepartmentCoordSig1;
//	}
//
//	public void setDepartmentCoordSig1(String departmentCoordSig1) {
//		this.DepartmentCoordSig1 = departmentCoordSig1;
//	}
//	public String getDepartmentInfoSig() {
//		return DepartmentInfoSig;
//	}
//
//	public void setDepartmentInfoSig(String departmentInfoSig) {
//		DepartmentInfoSig = departmentInfoSig;
//	}
//
//	public String getDepartmentInfoSig2() {
//		return DepartmentInfoSig2;
//	}
//
//	public void setDepartmentInfoSig2(String departmentInfoSig2) {
//		DepartmentInfoSig2 = departmentInfoSig2;
//	}
//
//	public String getManagerSig1() {
//		return ManagerSig1;
//	}
//
//	public void setManagerSig1(String managerSig1) {
//		ManagerSig1 = managerSig1;
//	}
//
//	public String getManagerSig2() {
//		return ManagerSig2;
//	}
//
//	public void setManagerSig2(String managerSig2) {
//		ManagerSig2 = managerSig2;
//	}
//
//	public String getManagerSig3() {
//		return ManagerSig3;
//	}
//
//	public void setManagerSig3(String managerSig3) {
//		ManagerSig3 = managerSig3;
//	}
//
//	public String getManagerSig4() {
//		return ManagerSig4;
//	}
//
//	public void setManagerSig4(String managerSig4) {
//		ManagerSig4 = managerSig4;
//	}
//
//	public String getManagerSig5() {
//		return ManagerSig5;
//	}
//
//	public void setManagerSig5(String managerSig5) {
//		ManagerSig5 = managerSig5;
//	}



}
