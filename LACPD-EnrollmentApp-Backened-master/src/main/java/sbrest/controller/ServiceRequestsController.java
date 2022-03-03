package sbrest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import sbrest.model.ServiceRequest;
import sbrest.model.dao.AdminDao;
import sbrest.model.dao.ServiceRequestDao;
import sbrest.service.SendEmail;
import sbrest.signapi.AgreementEvents;
//import sbrest.signapi.Agreements;

@RestController
@RequestMapping("/service_requests")
public class ServiceRequestsController {

	@Autowired
    private AdminDao adminDao;
	
	@Autowired
	private SendEmail sendEmail;	
	
	@Autowired
	private ServiceRequestDao serviceRequestDao;

	@GetMapping
	public List<ServiceRequest> serviceRequests(ModelMap models) {
		return serviceRequestDao.getServiceRequests();
	}

	@GetMapping("/{requestNumber}")
	public ServiceRequest get(@PathVariable Integer requestNumber) throws Exception {
		ServiceRequest s = serviceRequestDao.getServiceRequest(requestNumber);
		
		s = AgreementEvents.updateRequestStatus(s);
		serviceRequestDao.saveServiceRequest(s);
		
		if (s == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Request not found");
		return s;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceRequest add(@RequestBody ServiceRequest s) throws Exception {
		// Generate a six digit code
		Random rnd = new Random();
		int randomRequestNumber = 100000 + rnd.nextInt(900000);

		// Check if it exists in database
		// If it does exists, regenerate the number (Do this until six digit code is
		// unique)
		while (existsByRequestNumber(randomRequestNumber)) {
			randomRequestNumber = 100000 + rnd.nextInt(900000);
		}

		// Set requestNumber
		s.setRequestNumber(randomRequestNumber);
		
		checkSubmitted(s);
		return serviceRequestDao.saveServiceRequest(s);
	}

	@PutMapping("/{requestNumber}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ServiceRequest update(@PathVariable Integer requestNumber, @RequestBody ServiceRequest s) throws Exception {

		ServiceRequest originalServiceRequest = serviceRequestDao.getServiceRequest(requestNumber);

		originalServiceRequest.setCreateDate(s.getCreateDate());
		originalServiceRequest.setSubmitDate(s.getSubmitDate());
		originalServiceRequest.setEmployee(s.isEmployee());
		originalServiceRequest.setRequestStatus(s.getRequestStatus());
		originalServiceRequest.setNewRegistration(s.isNewRegistration());
		originalServiceRequest.setDeletePriorRegistration(s.isDeletePriorRegistration());
		originalServiceRequest.setUpdatePriorRegistration(s.isUpdatePriorRegistration());
		originalServiceRequest.setReplaceLostToken(s.isReplaceLostToken());
		originalServiceRequest.setAddLogonId(s.isAddLogonId());
		originalServiceRequest.setChangeLogonId(s.isChangeLogonId());
		originalServiceRequest.setDeleteLogonId(s.isDeleteLogonId());
		originalServiceRequest.setLastName(s.getLastName());
		originalServiceRequest.setFirstName(s.getFirstName());
		originalServiceRequest.setMiddleInitial(s.getMiddleInitial());
		originalServiceRequest.setEmployeeNumber(s.getEmployeeNumber());
		originalServiceRequest.setDepartmentName(s.getDepartmentName());
		originalServiceRequest.setDepartmentNumber(s.getDepartmentNumber());
		originalServiceRequest.setContractorCompanyName(s.getContractorCompanyName());
		originalServiceRequest.setCompanyEmailAddress(s.getCompanyEmailAddress());
		originalServiceRequest.setCountyEmailAddress(s.getCountyEmailAddress());
		originalServiceRequest.setEmployeeEmailAddress(s.getEmployeeEmailAddress());
		originalServiceRequest.setBusinessStreetAddress(s.getBusinessStreetAddress());
		originalServiceRequest.setBusinessCity(s.getBusinessCity());
		originalServiceRequest.setBusinessState(s.getBusinessState());
		originalServiceRequest.setBusinessZip(s.getBusinessZip());
		originalServiceRequest.setBusinessPhoneNumber(s.getBusinessPhoneNumber());
		originalServiceRequest.setWorkMailingAddress(s.getWorkMailingAddress());
		originalServiceRequest.setCompanyStreetAddress(s.getCompanyStreetAddress());
		originalServiceRequest.setCompanyCity(s.getCompanyCity());
		originalServiceRequest.setCompanyState(s.getCompanyState());
		originalServiceRequest.setCompanyZip(s.getCompanyZip());
		originalServiceRequest.setCompanyPhoneNumber(s.getCompanyPhoneNumber());
		originalServiceRequest.setCountyPhoneNumber(s.getCountyPhoneNumber());
		originalServiceRequest.setContractorWorkOrder(s.getContractorWorkOrder());
		originalServiceRequest.setContractorExperationDate(s.getContractorExperationDate());
		originalServiceRequest.setIbmLogOnId(s.getIbmLogOnId());
		originalServiceRequest.setMajorGroupCode(s.getMajorGroupCode());
		originalServiceRequest.setLsoGroupCode(s.getLsoGroupCode());
		originalServiceRequest.setSecurityAuthorization(s.getSecurityAuthorization());
		originalServiceRequest.setTsoAccess(s.isTsoAccess());
		originalServiceRequest.setTsoGroupCode(s.getTsoGroupCode());
		originalServiceRequest.setBinNumber(s.getBinNumber());
		originalServiceRequest.setSubGroup1(s.getSubGroup1());
		originalServiceRequest.setSubGroup2(s.getSubGroup2());
		originalServiceRequest.setSubGroup3(s.getSubGroup3());
		originalServiceRequest.setOnlineAccess(s.isOnlineAccess());
		originalServiceRequest.setSystemApplication(s.getSystemApplication());
		originalServiceRequest.setGroupName(s.getGroupName());
		originalServiceRequest.setOldGroup(s.getOldGroup());
		originalServiceRequest.setUnixAddLogonId(s.isUnixAddLogonId());
		originalServiceRequest.setUnixChangeLogonId(s.isUnixChangeLogonId());
		originalServiceRequest.setUnixDeleteLogonId(s.isUnixDeleteLogonId());
		originalServiceRequest.setUnixLogOnId(s.getUnixLogOnId());
		originalServiceRequest.setUnixApplication(s.getUnixApplication());
		originalServiceRequest.setUnixAccessGroup(s.getUnixAccessGroup());
		originalServiceRequest.setUnixAccountNumber(s.getUnixAccountNumber());
		originalServiceRequest.setBillingAccount(s.getBillingAccount());
		originalServiceRequest.setSecurIdVpn(s.isSecurIdVpn());
		originalServiceRequest.setAdaptiveAuthenticationVpn(s.isAdaptiveAuthenticationVpn());
		originalServiceRequest.setInternetApplication(s.isInternetApplication());
		originalServiceRequest.setExchangeEmail(s.isExchangeEmail());
		originalServiceRequest.setEmailEncryption(s.isEmailEncryption());
		originalServiceRequest.setLaCountyGovAccess(s.isLaCountyGovAccess());
		originalServiceRequest.setTokenlessAuthentication(s.isTokenlessAuthentication());
		originalServiceRequest.setLacMobileWifiAccess(s.isLacMobileWifiAccess());
		originalServiceRequest.setCherwellSms(s.isCherwellSms());
		originalServiceRequest.setWindowsRightsMgmt(s.isWindowsRightsMgmt());
		originalServiceRequest.setGmailAccess(s.isGmailAccess());
		originalServiceRequest.setYahooMailAccess(s.isYahooMailAccess());
		originalServiceRequest.setOtherEmailDomain(s.getOtherEmailDomain());
		originalServiceRequest.setBusinessJustification1(s.getBusinessJustification1());
		originalServiceRequest.setDefaultCountyWidePolicy(s.isDefaultCountyWidePolicy());
		originalServiceRequest.setDepartmentPolicyRule0(s.isDepartmentPolicyRule0());
		originalServiceRequest.setDepartmentPolicyRule1(s.isDepartmentPolicyRule1());
		originalServiceRequest.setDepartmentPolicyRule2(s.isDepartmentPolicyRule2());
		originalServiceRequest.setDepartmentPolicyRule3(s.isDepartmentPolicyRule3());
		originalServiceRequest.setDepartmentPolicyRule4(s.isDepartmentPolicyRule4());
		originalServiceRequest.setSocialNetworkingFacebook(s.isSocialNetworkingFacebook());
		originalServiceRequest.setSocialNetworkingTwitter(s.isSocialNetworkingTwitter());
		originalServiceRequest.setSocialNetworkingLinkedIn(s.isSocialNetworkingLinkedIn());
		originalServiceRequest.setSubmitted(s.isSubmitted());
		originalServiceRequest.setManagerFirstName(s.getManagerFirstName());
		originalServiceRequest.setManagerLastName(s.getManagerLastName());
		originalServiceRequest.setManagerEmail(s.getManagerEmail());
		originalServiceRequest.setManagerPhone(s.getManagerPhone());
		originalServiceRequest.setManagerTitle(s.getManagerTitle());
		originalServiceRequest.setAllWebmail(s.isAllWebmail());
		originalServiceRequest.setAndroid(s.isAndroid());
		originalServiceRequest.setBusinessJustification2(s.getBusinessJustification2());
		originalServiceRequest.setContractorWorkforce1(s.isContractorWorkforce1());
		originalServiceRequest.setCountyDepartmentName(s.getCountyDepartmentName());
		originalServiceRequest.setCountyWorkforceId1(s.getCountyWorkforceId1());
		originalServiceRequest.setCountyWorkforceName1(s.getCountyWorkforceName1());
		originalServiceRequest.setCountywidePolicyA(s.isCountywidePolicyA());
		originalServiceRequest.setCountywidePolicyB(s.isCountywidePolicyB());
		originalServiceRequest.setDepartmentCoord1(s.getDepartmentCoord1());
		originalServiceRequest.setDepartmentInfo(s.getDepartmentInfo());
		originalServiceRequest.setEmailAddress(s.getEmailAddress());
		originalServiceRequest.setEmployeeContractor(s.getEmployeeContractor());
		originalServiceRequest.setEmployeeWorkforce(s.isEmployeeWorkforce());
		originalServiceRequest.setHardTokenVPN(s.isHardTokenVPN());
		originalServiceRequest.setIOS(s.isIOS());
		originalServiceRequest.setLACMobile(s.isLACMobile());
		originalServiceRequest.setLACounty(s.isLACounty());
		originalServiceRequest.setManagerName1(s.getManagerName1());
		originalServiceRequest.setManagerName2(s.getManagerName2());
		originalServiceRequest.setManagerName3(s.getManagerName3());
		originalServiceRequest.setManagerTitle1(s.getManagerTitle1());
		originalServiceRequest.setName1(s.getName1());
		originalServiceRequest.setNewRequest(s.isNewRequest());
		originalServiceRequest.setNewToken(s.isNewToken());
		originalServiceRequest.setO365Email(s.isO365Email());
		originalServiceRequest.setOnlineAccess(s.isOnlineAccess());
		originalServiceRequest.setPhone1(s.getPhone1());
		originalServiceRequest.setPhone2(s.getPhone2());
		originalServiceRequest.setPhone3(s.getPhone3());
		originalServiceRequest.setPhone4(s.getPhone4());
		originalServiceRequest.setPhone5(s.getPhone5());
		originalServiceRequest.setPhone6(s.getPhone6());
		originalServiceRequest.setRenewToken(s.isRenewToken());
		originalServiceRequest.setReplaceDefectiveToken(s.isReplaceDefectiveToken());
		originalServiceRequest.setReplaceLostStolenToken(s.isReplaceLostStolenToken());
		originalServiceRequest.setSoftwareToken(s.isSoftwareToken());
		originalServiceRequest.setStreamingMedia(s.isStreamingMedia());
		originalServiceRequest.setTokenlessAuth(s.isTokenlessAuth());
		originalServiceRequest.setTsoAccess(s.isTsoAccess());
		originalServiceRequest.setUpdateRequest(s.isUpdateRequest());
		originalServiceRequest.setAccessGroup1(s.getAccessGroup1());
		originalServiceRequest.setAddNewLoginId(s.isAddNewLoginId());
		originalServiceRequest.setApplication1(s.getApplication1());
		originalServiceRequest.setAps(s.getAps());
		originalServiceRequest.setBinNumber(s.getBinNumber());
		//originalServiceRequest.setCountyDepartmetnNumber(s.getCountyDepartmetnNumber());
		originalServiceRequest.setDateOfRequest(s.getDateOfRequest());
		originalServiceRequest.setDeleteLoginId1(s.isDeleteLoginId1());
		originalServiceRequest.setDeleteLoginId2(s.isDeleteLoginId2());
		originalServiceRequest.setDigitLSOGroupCode(s.getDigitLSOGroupCode());
		originalServiceRequest.setDigitMajorGroupCode(s.getDigitMajorGroupCode());
		originalServiceRequest.setDigitTSOGrpCode(s.getDigitTSOGrpCode());
		originalServiceRequest.setDmv(s.getDmv());
		originalServiceRequest.setEmployeeContractor(s.getEmployeeContractor());
		originalServiceRequest.setGrpName1(s.getGrpName1());
		originalServiceRequest.setGrpName2(s.getGrpName2());
		originalServiceRequest.setGrpName3(s.getGrpName3());
		originalServiceRequest.setJai(s.getJai());
		originalServiceRequest.setLastNamefirstNameMi(s.getLastNamefirstNameMi());
		originalServiceRequest.setLoginId1(s.getLoginId1());
		originalServiceRequest.setLoginId2(s.getLoginId2());
		originalServiceRequest.setNewLoginId(s.isNewLoginId());
		originalServiceRequest.setOldGrp1(s.getOldGrp1());
		originalServiceRequest.setOldGrp2(s.getOldGrp2());
		originalServiceRequest.setOldGrp3(s.getOldGrp3());
		originalServiceRequest.setSystemApplication1(s.getSystemApplication1());
		originalServiceRequest.setSystemApplication2(s.getSystemApplication2());
		originalServiceRequest.setSystemApplication3(s.getSystemApplication3());
		originalServiceRequest.setUpdateLoginId1(s.isUpdateLoginId1());
		originalServiceRequest.setUpdateLoginId2(s.isUpdateLoginId2());
		originalServiceRequest.setWorkPhoneNumber(s.getWorkPhoneNumber());

		checkSubmitted(originalServiceRequest);
		
		originalServiceRequest = serviceRequestDao.saveServiceRequest(originalServiceRequest);
		return originalServiceRequest;

	}

	@PatchMapping("/{requestNumber}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ServiceRequest update(@PathVariable Integer requestNumber, @RequestBody Map<String, Object> patch) throws Exception {

		ServiceRequest s = serviceRequestDao.getServiceRequest(requestNumber);

		for (String key : patch.keySet()) {

			switch (key) {
			case "createDate":
				s.setCreateDate((String) patch.get(key));
				break;
			case "submitDate":
				s.setSubmitDate((String) patch.get(key));
				break;
			case "newRegistration":
				s.setNewRegistration((boolean) patch.get(key));
				break;
			case "deletePriorRegistration":
				s.setDeletePriorRegistration((boolean) patch.get(key));
				break;
			case "updatePriorRegistration":
				s.setUpdatePriorRegistration((boolean) patch.get(key));
				break;
			case "isEmployee":
				s.setEmployee((boolean) patch.get(key));
				break;
				// A.V.
			case "replaceLostToken":
				s.setReplaceLostToken((boolean) patch.get(key));
				break;
			case "addLogonId":
				s.setAddLogonId((boolean) patch.get(key));
				break;
			case "changeLogonId":
				s.setChangeLogonId((boolean) patch.get(key));
				break;
			case "deleteLogonId":
				s.setDeleteLogonId((boolean) patch.get(key));
				break;
			case "requestStatus":
				s.setRequestStatus((String) patch.get(key));
				break;
			case "lastName":
				s.setLastName((String) patch.get(key));
				break;
			case "firstName":
				s.setFirstName((String) patch.get(key));
				break;
			case "middleInitial":
				s.setMiddleInitial((String) patch.get(key));
				break;
			case "employeeNumber":
				s.setEmployeeNumber((String) patch.get(key));
				break;
			case "departmentName":
				s.setDepartmentName((String) patch.get(key));
				break;
			case "departmentNumber":
				s.setDepartmentNumber((String) patch.get(key));
				break;
			case "ContractorCompanyName":
				s.setContractorCompanyName((String) patch.get(key));
				break;
			case "companyEmailAddress":
				s.setCompanyEmailAddress((String) patch.get(key));
				break;
			case "countyEmailAddress":
				s.setCountyEmailAddress((String) patch.get(key));
				break;
			case "employeeEmailAddress":
				s.setEmployeeEmailAddress((String) patch.get(key));
				break;
			case "businessStreetAddress":
				s.setBusinessStreetAddress((String) patch.get(key));
				break;
			case "businessCity":
				s.setBusinessCity((String) patch.get(key));
				break;
			case "businessState":
				s.setBusinessState((String) patch.get(key));
				break;
			case "businessZip":
				s.setBusinessZip((String) patch.get(key));
				break;
			case "businessPhoneNumber":
				s.setBusinessPhoneNumber((String) patch.get(key));
				break;
			case "workMailingAddress":
				s.setWorkMailingAddress((String) patch.get(key));
				break;
			case "companyStreetAddress":
				s.setCompanyStreetAddress((String) patch.get(key));
				break;
			case "companyCity":
				s.setCompanyCity((String) patch.get(key));
				break;
			case "companyState":
				s.setCompanyState((String) patch.get(key));
				break;
			case "companyZip":
				s.setCompanyZip((String) patch.get(key));
				break;
			case "companyPhoneNumber":
				s.setCompanyPhoneNumber((String) patch.get(key));
				break;
			case "countyPhoneNumber":
				s.setCountyPhoneNumber((String) patch.get(key));
				break;
			case "ContractorWorkOrder":
				s.setContractorWorkOrder((String) patch.get(key));
				break;
			case "ContractorExperationDate":
				s.setContractorExperationDate((String) patch.get(key));
				break;
			case "ibmLogOnId":
				s.setIbmLogOnId((String) patch.get(key));
				break;
			case "majorGroupCode":
				s.setMajorGroupCode((String) patch.get(key));
				break;
			case "lsoGroupCode":
				s.setLsoGroupCode((String) patch.get(key));
				break;
			case "securityAuthorization":
				s.setSecurityAuthorization((String) patch.get(key));
				break;
			case "tsoAccess":
				s.setTsoAccess((boolean) patch.get(key));
				break;
			case "tsoGroupCode":
				s.setTsoGroupCode((String) patch.get(key));
				break;
			case "binNumber":
				s.setBinNumber((String) patch.get(key));
				break;
			case "subGroup1":
				s.setSubGroup1((String) patch.get(key));
				break;
			case "subGroup2":
				s.setSubGroup2((String) patch.get(key));
				break;
			case "subGroup3":
				s.setSubGroup3((String) patch.get(key));
				break;
			case "onlineAccess":
				s.setOnlineAccess((boolean) patch.get(key));
				break;
			case "systemApplication":
				s.setSystemApplication((String) patch.get(key));
				break;
			case "groupName":
				s.setGroupName((String) patch.get(key));
				break;
			case "oldGroup":
				s.setOldGroup((String) patch.get(key));
				break;
			case "unixAddLogonId":
				s.setUnixAddLogonId((boolean) patch.get(key));
				break;
			case "unixChangeLogonId":
				s.setUnixChangeLogonId((boolean) patch.get(key));
				break;
			case "unixDeleteLogonId":
				s.setUnixDeleteLogonId((boolean) patch.get(key));
				break;
			case "unixLogOnId":
				s.setUnixLogOnId((String) patch.get(key));
				break;
			case "unixApplication":
				s.setUnixApplication((String) patch.get(key));
				break;
			case "unixAccessGroup":
				s.setUnixAccessGroup((String) patch.get(key));
				break;
			case "unixAccountNumber":
				s.setUnixAccountNumber((String) patch.get(key));
				break;
			case "BillingAccount":
				s.setBillingAccount((String) patch.get(key));
				break;
			case "securIdVpn":
				s.setSecurIdVpn((boolean) patch.get(key));
				break;
			case "adaptiveAuthenticationVpn":
				s.setAdaptiveAuthenticationVpn((boolean) patch.get(key));
				break;
			case "internetApplication":
				s.setInternetApplication((boolean) patch.get(key));
				break;
			case "exchangeEmail":
				s.setExchangeEmail((boolean) patch.get(key));
				break;
			case "emailEncryption":
				s.setEmailEncryption((boolean) patch.get(key));
				break;
			case "laCountyGovAccess":
				s.setLaCountyGovAccess((boolean) patch.get(key));
				break;
			case "tokenlessAuthentication":
				s.setTokenlessAuthentication((boolean) patch.get(key));
				break;
			case "lacMobileWifiAccess":
				s.setLacMobileWifiAccess((boolean) patch.get(key));
				break;
			case "cherwellSms":
				s.setCherwellSms((boolean) patch.get(key));
				break;
			case "windowsRightsMgmt":
				s.setWindowsRightsMgmt((boolean) patch.get(key));
				break;
			case "gmailAccess":
				s.setGmailAccess((boolean) patch.get(key));
				break;
			case "yahooMailAccess":
				s.setYahooMailAccess((boolean) patch.get(key));
				break;
			case "otherEmailDomain":
				s.setOtherEmailDomain((String) patch.get(key));
				break;
			case "BusinessJustification1":
				s.setBusinessJustification1((String) patch.get(key));
				break;
			case "defaultCountyWidePolicy":
				s.setDefaultCountyWidePolicy((boolean) patch.get(key));
				break;
			case "departmentPolicyRule0":
				s.setDepartmentPolicyRule0((boolean) patch.get(key));
				break;
			case "departmentPolicyRule1":
				s.setDepartmentPolicyRule1((boolean) patch.get(key));
				break;
			case "departmentPolicyRule2":
				s.setDepartmentPolicyRule2((boolean) patch.get(key));
				break;
			case "departmentPolicyRule3":
				s.setDepartmentPolicyRule3((boolean) patch.get(key));
				break;
			case "departmentPolicyRule4":
				s.setDepartmentPolicyRule4((boolean) patch.get(key));
				break;
			case "socialNetworkingFacebook":
				s.setSocialNetworkingFacebook((boolean) patch.get(key));
				break;
			case "socialNetworkingTwitter":
				s.setSocialNetworkingTwitter((boolean) patch.get(key));
				break;
			case "socialNetworkingLinkedIn":
				s.setSocialNetworkingLinkedIn((boolean) patch.get(key));
				break;
			case "isSubmitted":
				s.setSubmitted((boolean) patch.get(key));
				break;
			case "managerFirstName":
				s.setManagerFirstName((String) patch.get(key));
				break;
			case "managerLastName":
				s.setManagerLastName((String) patch.get(key));
				break;
			case "managerPhone":
				s.setManagerPhone((String) patch.get(key));
				break;
			case "managerTitle":
				s.setManagerTitle((String) patch.get(key));
				break;
			case "managerEmail":
				s.setManagerEmail((String) patch.get(key));
				break;
            case "AllWebmail":
					s.setAllWebmail((boolean) patch.get(key));
					break;
 			case "Android":
					s.setAndroid((boolean) patch.get(key));
					break;
            case "BusinessJustification2":
					s.setBusinessJustification2((String) patch.get(key));
					break;
		    case "ContractorWorkforce1":
					s.setContractorWorkforce1((boolean) patch.get(key));
					break;
            case "CountyDepartmentName":
					s.setCountyDepartmentName((String) patch.get(key));
					break;
			case "CountyWorkforceId1":
					s.setCountyWorkforceId1((String) patch.get(key));
					break;
	        case "CountyWorkforceName1":
					s.setCountyWorkforceName1((String) patch.get(key));
					break;
	        case "CountywidePolicyA":
					s.setCountywidePolicyA((boolean) patch.get(key));
					break;
		     case "CountywidePolicyB":
					s.setCountywidePolicyB((boolean) patch.get(key));
					break;
		      case "DepartmentCoord1":
					s.setDepartmentCoord1((String) patch.get(key));
					break;
			   case "DepartmentInfo":
					s.setDepartmentInfo((String) patch.get(key));
					break;
				case "EmailAddress":
					s.setEmailAddress((String) patch.get(key));
					break;
				case "EmployeeContractor":
					s.setEmployeeContractor((String) patch.get(key));
					break;
				case "EmployeeWorkforce":
					s.setEmployeeWorkforce((boolean) patch.get(key));
					break;
				case "HardTokenVPN":
					s.setHardTokenVPN((boolean) patch.get(key));
					break;
				case "IOS":
					s.setIOS((boolean) patch.get(key));
					break;
				case "LACMobile":
					s.setLACMobile((boolean) patch.get(key));
					break;
				case "LACounty":
					s.setLACounty((boolean) patch.get(key));
					break;
				case "ManagerName1":
					s.setManagerName1((String) patch.get(key));
					break;
				case "ManagerName2":
					s.setManagerName2((String) patch.get(key));
					break;
				case "ManagerName3":
					s.setManagerName3((String) patch.get(key));
					break;
				case "ManagerTitle1":
					s.setManagerTitle1((String) patch.get(key));
					break;
				case "Name1":
					s.setName1((String) patch.get(key));
					break;
				case "NewRequest":
					s.setNewRequest((boolean) patch.get(key));
					break;
				case "NewToken":
					s.setNewToken((boolean) patch.get(key));
					break;
				case "O365Email":
					s.setO365Email((boolean) patch.get(key));
					break;
				case "OnlineAccess":
					s.setOnlineAccess((boolean) patch.get(key));
					break;
				case "Phone1":
					s.setPhone1((String) patch.get(key));
					break;
				case "Phone2":
					s.setPhone2((String) patch.get(key));
					break;
				case "Phone3":
					s.setPhone3((String) patch.get(key));
					break;
				case "Phone4":
					s.setPhone4((String) patch.get(key));
					break;
				case "Phone5":
					s.setPhone5((String) patch.get(key));
					break;
				case "Phone6":
					s.setPhone6((String) patch.get(key));
					break;
				case "RenewToken":
					s.setRenewToken((boolean) patch.get(key));
					break;
				case "ReplaceDefectiveToken":
					s.setReplaceDefectiveToken((boolean) patch.get(key));
					break;
				case "ReplaceLostStolenToken":
					s.setReplaceLostStolenToken((boolean) patch.get(key));
					break;
				case "SoftwareToken":
					s.setSoftwareToken((boolean) patch.get(key));
					break;
				case "StreamingMedia":
					s.setStreamingMedia((boolean) patch.get(key));
					break;
				case "TokenlessAuth":
					s.setTokenlessAuth((boolean) patch.get(key));
					break;
				case "TsoAccess":
					s.setTsoAccess((boolean) patch.get(key));
					break;
				case "UpdateRequest":
					s.setUpdateRequest((boolean) patch.get(key));
					break;
				case "accessGroup1":
					s.setAccessGroup1((String) patch.get(key));
					break;
				case "addNewLoginId":
					s.setAddNewLoginId((boolean) patch.get(key));
					break;
				case "application1":
					s.setApplication1((String) patch.get(key));
					break;
				case "aps":
					s.setAps((String) patch.get(key));
					break;
				//case "countyDepartmetnNumber":
					//.s.setCountyDepartmetnNumber((String) patch.get(key));
					//.key;
				case "dateOfRequest":
					s.setDateOfRequest((String) patch.get(key));
					break;
				case "deleteLoginId1":
					s.setDeleteLoginId1((boolean) patch.get(key));
					break;
				case "deleteLoginId2":
					s.setDeleteLoginId2((boolean) patch.get(key));
					break;
				case "digitLSOGroupCode":
					s.setDigitLSOGroupCode((String) patch.get(key));
					break;
				case "digitMajorGroupCode":
					s.setDigitMajorGroupCode((String) patch.get(key));
					break;
				case "digitTSOGrpCode":
					s.setDigitTSOGrpCode((String) patch.get(key));
					break;
				case "dmv":
					s.setDmv((String) patch.get(key));
					break;
				case "employeeContractor":
					s.setEmployeeContractor((String) patch.get(key));
					break;
				case "grpName1":
					s.setGrpName1((String) patch.get(key));
					break;
				case "grpName2":
					s.setGrpName2((String) patch.get(key));
					break;
				case "grpName3":
					s.setGrpName3((String) patch.get(key));
					break;

				case "jai":
					s.setJai((String) patch.get(key));
					break;
				case "lastNamefirstNameMi":
					s.setLastNamefirstNameMi((String) patch.get(key));
					break;
				case "loginIdw":
					s.setLoginId1((String) patch.get(key));
					break;
				case "loginId2":
					s.setLoginId2((String) patch.get(key));
					break;
				case "newLoginId":
					s.setNewLoginId((boolean) patch.get(key));
					break;
				case "oldGrp1":
					s.setOldGrp1((String) patch.get(key));
					break;
				case "oldGrp2":
					s.setOldGrp2((String) patch.get(key));
					break;
				case "oldGrp3":
					s.setOldGrp3((String) patch.get(key));
					break;
				case "systemApplication1":
					s.setSystemApplication1((String) patch.get(key));
					break;
				case "systemApplication2":
					s.setSystemApplication2((String) patch.get(key));
					break;
				case "systemApplication3":
					s.setSystemApplication3((String) patch.get(key));
					break;

				case "updateLoginId1":
					s.setUpdateLoginId1((boolean) patch.get(key));
					break;
				case "updateLoginId2":
					s.setUpdateLoginId2((boolean) patch.get(key));
					break;
				case "workPhoneNumber":
					s.setWorkPhoneNumber((String) patch.get(key));
					break;
			default:
				break;

			}

		}
		
		if (patch.keySet().contains("isSubmitted")) {
			checkSubmitted(s);
		}

		s = serviceRequestDao.saveServiceRequest(s);
		return s;
	}

	@DeleteMapping("/{requestNumber}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer requestNumber) {
		serviceRequestDao.deleteServiceRequest(requestNumber);
	}
	
	public void checkSubmitted(ServiceRequest s) {
		if (s.isSubmitted()) {
			s.setRequestStatus("Submitted for Admin Review");
			String pattern = "yyyy-MM-dd hh:mm:ss";
			DateFormat d = new SimpleDateFormat(pattern);
			Date currentDate = Calendar.getInstance().getTime();
			
			s.setSubmitDate(d.format(currentDate));
			s.getEventHistory().add("(" + s.getSubmitDate() + ") Request submitted for Admin review");
			
			String adminEmail = adminDao.getAdmin().getEmail();
			String subject = "New Request Submitted (#" + s.getRequestNumber() + ")";
			String text = "A new service request was submitted by " + s.getFirstName() + " " + s.getLastName()
					+ ". The request number is "+ s.getRequestNumber() +".\nLog in to review the request at " + "http://localhost:4200/admin"
					+ "\n\n" + "[THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL]";
 
			sendEmail.sendSimpleMessage(adminEmail, subject, text);

			
			String userEmail = "";
			if (s.isEmployee()) {
				userEmail = s.getEmployeeEmailAddress();
			}
			else {
				userEmail = s.getCompanyEmailAddress();
			}

			String userEmailSubject = "New Request Submitted (#" + s.getRequestNumber() + ")";
			String userEmailText = "Hello " + s.getFirstName() + ",\n\n"
					+ "Thank you for submitting your request. Here is your request number: "+ s.getRequestNumber() +"\nPlease store this request number for your records."
					+ "\n\n" + "[THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL]";

			sendEmail.sendSimpleMessage(userEmail, userEmailSubject, userEmailText);
			
		}
		else {
			s.setRequestStatus("Draft");
			s.setSubmitDate("");
		}
	}
	
	/**
	 * Will fetch a service request by request number. If it exists, then that means
	 * that the request number has already been used and is not unique.
	 * 
	 * @param requestNumber This is the 6 digit code
	 * @return Will return true if the request number is already used. A Service
	 *         Request using this request number already exists.
	 */
	public boolean existsByRequestNumber(Integer requestNumber) {
		ServiceRequest serviceRequest = serviceRequestDao.getServiceRequest(requestNumber);
		if (serviceRequest != null) {
			return true;
		}
		return false;
	}
	
	@Async
	@Scheduled(cron = "0 1 1 * * ?")
	public void updateRequestStatuses() throws Exception {
		List<ServiceRequest> serviceRequests = serviceRequestDao.getServiceRequests();
		for (ServiceRequest s : serviceRequests) {
			s = AgreementEvents.updateRequestStatus(s);
			serviceRequestDao.saveServiceRequest(s);
		}
	}
	
	
}
