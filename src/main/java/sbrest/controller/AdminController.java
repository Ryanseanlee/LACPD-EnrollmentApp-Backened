	package sbrest.controller;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	import java.util.concurrent.TimeUnit;

	import org.springframework.beans.factory.annotation.Autowired;
	// import org.springframework.beans.factory.annotation.Value;
	import org.springframework.http.HttpStatus;
	import org.springframework.ui.ModelMap;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PatchMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	// import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestHeader;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.server.ResponseStatusException;

	import sbrest.model.Admin;
	import sbrest.model.ApplicationCoordinator;
	import sbrest.model.DepartmentHead;
	import sbrest.model.DeptInfoSecurityOfficer;
	import sbrest.model.DivChiefManager;
	// import sbrest.model.Field;
	// import sbrest.model.Form;
	import sbrest.model.ServiceRequest;
	import sbrest.model.RequestStatusResponse;
	import sbrest.model.dao.AdminDao;
	import sbrest.model.dao.ApplicationCoordinatorDao;
	import sbrest.model.dao.DepartmentHeadDao;
	import sbrest.model.dao.DeptInfoSecurityOfficerDao;
	import sbrest.model.dao.DivChiefManagerDao;
	// import sbrest.model.dao.FormDao;
	import sbrest.model.dao.ServiceRequestDao;
	import sbrest.signapi.AgreementEvents;
	import sbrest.signapi.Agreements;

	@RestController
	@CrossOrigin(origins= "*")
	@RequestMapping("/admin")
	public class AdminController {
	//These functions are for the admin user
			
		@Autowired
	    private AdminDao adminDao;
		
		@Autowired
		private ServiceRequestDao serviceRequestDao;
		
		//Gets some details of all specified Service Request if user is admin
		@CrossOrigin(origins= "*")
		@GetMapping("/service_requests")
		public List<RequestStatusResponse> getRequestStatusByRequestNumber(
				@RequestHeader("email") String email,
				@RequestHeader("password") String password) {	// Retrieves email and password from header	
			// The Service Request
			List<ServiceRequest> serviceRequests = serviceRequestDao.getServiceRequests();
			
			// Request Status Response
			List<RequestStatusResponse> requestStatusResponses = new ArrayList<RequestStatusResponse>();

			//Retrieves email and password from database
			String dbPassword = adminDao.getAdmin(email).getPassword();
			String dbUsername = adminDao.getAdmin(email).getEmail();
			
			// IRV
			// Test the adminDao.createAdmin(String, String), works :)
			// adminDao.createAdmin("h", "h");
			
			// IRV
			// print dbpassword and dbusername in console
			// System.out.println(dbPassword + " " + dbUsername);
			
			// compares user inputed email and password 
			if (dbPassword.equals(password) && dbUsername.equals(email)) {
				if (serviceRequests != null) {				
					// pointer
					// This is where all of the content of Service Request is displayed
					for(ServiceRequest s : serviceRequests) {
						RequestStatusResponse r = new RequestStatusResponse(
								s.getRequestNumber(),
								s.getRequestStatus(), 
								s.getFirstName(), 
								s.getLastName(),
								s.isEmployee(),
								s.getSubmitDate(),
								s.isComplete()
						);
						requestStatusResponses.add(r);
					}
					return requestStatusResponses;
				} else
					////TODO: Null pointer exception if no service requests 
					return null;
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
		}	
		
		//Gets all details of a specified Service Request if user is an administrator
		@CrossOrigin(origins= "*")
		@GetMapping("/service_requests/{requestNumber}")
		public ServiceRequest get(
				// @RequestHeader("email") String email, // check if email passes IRV
				@RequestHeader("password") String password, 
				@PathVariable Integer requestNumber) throws Exception {
			ServiceRequest s = serviceRequestDao.getServiceRequest(requestNumber);

			// get password from user input and database, then compare
			//If password matches one in database return request status, else 403 error forbidden
			
			String dbPassword = adminDao.getAdmin("ivalen13@calstatela.edu").getPassword();

			if (dbPassword.equals(password)) {
				if (s == null)
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Request not found");
				
				s = AgreementEvents.updateRequestStatus(s);
				serviceRequestDao.saveServiceRequest(s);
				
				return s;
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			}
		}
		
		//Admin user can change the password
        @CrossOrigin(origins= "*")
        @PatchMapping("/reset_password")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void update
        (
        @RequestHeader("email") String email,
        @RequestHeader("password") String oldPassword, 
        @RequestHeader("new-password") String newPassword
        ) {

            // get password from database, then compare to user input
            //If password matches one in database return request status, else 403 error forbidden
            String dbPassword = adminDao.getAdmin(email).getPassword(); //database

            // testing only backend || no frontend
			// String oldPassword = "1";
			// String newPassword = "2";

            System.out.println(dbPassword);
            System.out.println(oldPassword);

            System.out.println(newPassword);

            if (dbPassword.equals(oldPassword)) {
                Admin originalAdmin = adminDao.getAdmin(email);
                originalAdmin.setPassword(newPassword);
                originalAdmin = adminDao.saveAdmin(originalAdmin);
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "User does not have authorization to view this page");
            }
        }
        
        // IRV
		// Admin user can create new admin
        @CrossOrigin(origins= "*")
        @PatchMapping("/Create_User")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void upgrade
        (
		@RequestHeader("email") String email,
		@RequestHeader("password") String password,
        @RequestHeader("newemail") String newEmail,
        @RequestHeader("newpassword") String newPassword,
        // IRV new headers for first Name, middle name, and last name.
        @RequestHeader("firstName") String firstName,
        @RequestHeader("middleName") String middleName,
        @RequestHeader("lastName") String lastName
        ) {

            // get password from database, then compare to user input
            //If password matches one in database return request status, else 403 error forbidden
            String dbPassword = adminDao.getAdmin(email).getPassword(); //database

            if (dbPassword.equals(password)) {
                adminDao.createAdmin(newEmail, newPassword, firstName, middleName, lastName);
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "User does not have authorization to view this page");
            }
        }  
        
		
		//Admin user can review a submitted request and edit field values
		@CrossOrigin(origins= "*")
		@PatchMapping("/service_requests/{requestNumber}")
		@ResponseStatus(HttpStatus.ACCEPTED)
		public ServiceRequest update(
				@RequestHeader("email") String email,
				@RequestHeader("password") String password, 
				@PathVariable Integer requestNumber, @RequestBody Map<String, Object> patch) throws Exception {
			
			ServiceRequest s = null;
			if (serviceRequestDao.getServiceRequest(requestNumber) == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Request not found");
			else
				s = serviceRequestDao.getServiceRequest(requestNumber);
			 
			
			// get password from user input and database, then compare
			// If password matches one in the database, the admin user
			//can edit field values, else 403 error forbidden
			String dbPassword = adminDao.getAdmin(email).getPassword();
		if (dbPassword.equals(password)) {			
			for (String key : patch.keySet()) {
				switch (key) {
					//TODO
				      // Form specific data
				 //     submitted: isSubmitted,
				case "isSubmitted":
					s.setSubmitted((boolean) patch.get(key));
					break;
				//      employee: true, // Since it is the employee form
				case "employee":
					s.setEmployee((boolean) patch.get(key));
					break;
				
				      // Personal Information\
//				      createDate: data.personalInformation.createDate,
				case "createDate":
					s.setCreateDate((String) patch.get(key));
					break;      
//				      lastName: data.personalInformation.lastName,
				case "lastName":
					s.setLastName((String) patch.get(key));
					break;
//				      firstName: data.personalInformation.firstName,
				case "firstName":
					s.setLastName((String) patch.get(key));
					break;
//				      middleInitial: data.personalInformation.middleInitial,
				case "middleInitial":
					s.setMiddleInitial((String) patch.get(key));
					break;
//				      employeeEmailAddress: data.personalInformation.emailAddress,
				case "employeeEmailAddress":
					s.setEmployeeEmailAddress((String) patch.get(key));
					break;
//				      phoneNumber: data.personalInformation.phoneNumber,
				case "phoneNumber":
					s.setPhoneNumber((String) patch.get(key));
					break;
//				      workPhoneNumber: data.personalInformation.workPhoneNumber,
				case "workPhoneNumber":
					s.setWorkPhoneNumber((String) patch.get(key));
					break;
//				      employeeNumber: data.personalInformation.employeeNumber,
				case "employeeNumber":
					s.setEmployeeNumber((String) patch.get(key));
					break;
//				      countyDepartmentName: data.personalInformation.countyDepartmentName,
				case "countyDepartmentName":
					s.setCountyDepartmentName((String) patch.get(key));
					break;
//				      countyDepartmentNumber: data.personalInformation.countyDepartmentNumber,
				case "countyDepartmentNumber":
					s.setCountyDepartmentNumber((String) patch.get(key));
					break;
//				      contractorName: data.personalInformation.contractorName,
				case "contractorName":
					s.setContractorName((String) patch.get(key));
					break;
//				      workOrderNumberInput: data.personalInformation.workOrderNumberInput,
				case "workOrderNumberInput":
					s.setWorkOrderNumberInput((String) patch.get(key));
					break;
//				      expirationDate: data.personalInformation.expirationDate,
				case "expirationDate":
					s.setExpirationDate((String) patch.get(key));
					break;
//
//				      // Address Information
//				      businessStreetAddress: data.addressInformation.address,
				case "businessStreetAddress":
					s.setBusinessStreetAddress((String) patch.get(key));
					break;
//				      businessCity: data.addressInformation.city,
				case "businessCity":
					s.setBusinessCity((String) patch.get(key));
					break;
//				      businessState: data.addressInformation.state,
				case "businessState":
					s.setBusinessState((String) patch.get(key));
					break;
//				      businessZip: data.addressInformation.zipCode,
				case "businessZip":
					s.setBusinessZip((String) patch.get(key));
					break;
//
//				      // Internet Access
//				      countyWidePolicyA: data.internetAccess.countyWidePolicyA,
				case "countyWidePolicyA":
					s.setCountywidePolicyA((boolean) patch.get(key));
					break;
//				      countyWidePolicyB: data.internetAccess.countyWidePolicyB,
				case "countyWidePolicyB":
					s.setCountywidePolicyB((boolean) patch.get(key));
					break;
//				      allWebmail: data.internetAccess.allWebmail,
				case "allWebmail":
					s.setAllWebmail((boolean) patch.get(key));
					break;
//				      streamMedia: data.internetAccess.streamMedia,
						// note: did not change this to setStreamMedia 
				case "streamMedia":
					s.setStreamingMedia((boolean) patch.get(key));
					break;
//				      justification: data.internetAccess.justification,
					// note: did not change this to justification
				case "justification":
					s.setBusinessJustification((String) patch.get(key));
					break;
					// boolean???
//				      // defaultCountyWidePolicy: data.internetAccess.applyDefaultCountyWidePolicy,
//				case "defaultCountyWidePolicy":
//					s.setDefaultCountyWidePolicy
//				      // departmentPolicyRule0: data.internetAccess.departmentPolicyRule0,
//				      // departmentPolicyRule1: data.internetAccess.departmentPolicyRule1,
//				      // departmentPolicyRule2: data.internetAccess.departmentPolicyRule2,
//				      // departmentPolicyRule3: data.internetAccess.departmentPolicyRule3,
//				      // departmentPolicyRule4: data.internetAccess.departmentPolicyRule4,
//				      // socialNetworkingFacebook: data.internetAccess.socialNetworkingFacebook,
//				      // socialNetworkingTwitter: data.internetAccess.socialNetworkingTwitter,
//				      // socialNetworkingLinkedIn: data.internetAccess.socialNetworkingLinkedIn,
//
//				      // Access Information
//				      ibmLogOnId: data.accessInformation.ibmLogonId,
				case "ibmLogOnId":
					s.setIbmLogOnId((String) patch.get(key));
					break;
//				      majorGroupCode: data.accessInformation.majorGroupCode,
				case "majorGroupCode":
					s.setMajorGroupCode((String) patch.get(key));
					break;
//				      lsoGroupCode: data.accessInformation.lsoGroupCode,
				case "lsoGroupCode":
					s.setLsoGroupCode((String) patch.get(key));
					break;
//				      securityAuthorization: data.accessInformation.securityAuthorization,
				case "securityAuthorization":
					s.setSecurityAuthorization((String) patch.get(key));
					break;
//				      unixLogOnId: data.accessInformation.unixLogonId,
				case "unixLogOnId":
					s.setUnixLogOnId((String) patch.get(key));
					break;
//				      unixApplication: data.accessInformation.application,
				case "unixApplication":
					s.setUnixApplication((String) patch.get(key));
					break;
//				      unixAccessGroup: data.accessInformation.accessGroup,
				case "unixAccessGroup":
					s.setUnixAccessGroup((String) patch.get(key));
					break;
//				      unixAccountNumber: data.accessInformation.accountNumber,
				case "unixAccountNumber":
					s.setUnixAccountNumber((String) patch.get(key));
					break;
//				      billingAccountNumber: data.accessInformation.billingAccountNumber,
				case "billingAccountNumber":
					s.setBillingAccountNumber((String) patch.get(key));
				break;
		
//				      // FIXME: On server side accessType(securid) might be misssing
//				      // Additional Information
				// IRV
				// did not fix these to a different name // Assuming these are Boolean
//				      laCountyGovAccess: data.additionalInformation.laCountyGovAccess,
				case "laCountGovAccess":
					s.setLaCounty((boolean) patch.get(key));
					break;
//				      lacMobileWifiAccess: data.additionalInformation.lacMobileWifiAccess,
				case "lacMobileWifiAccess":
					s.setLacMobile((boolean) patch.get(key));
					break;
//				      o365Email: data.additionalInformation.o365Email,
				case "o365Email":
					s.setO365Email((boolean) patch.get(key));
					break;
				
				
//				      // TODO: Add managerTitle
//				      // Mananger Information
					
//				      managerFirstName: data.managerInformation.managerFirstName,
				case "managerFirstName":
					s.setManagerFirstName((String) patch.get(key));
					break;
//				      managerLastName: data.managerInformation.managerLastName,
				case "managerLastName":
					s.setManagerLastName((String) patch.get(key));
					break;
//				      managerEmail: data.managerInformation.managerEmail,
				case "managerEmail":
					s.setManagerEmail((String) patch.get(key));
					break;
//				      managerPhone: data.managerInformation.managerPhoneNumber,
				case "managerPhone":
					s.setManagerPhone((String) patch.get(key));
					break;
//				    };
					
					
/*	Original form
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

					
					//not yet implemented in admin backend- checkCompletemness
				case "isComplete":
					s.setComplete((boolean) patch.get(key));
					//checkCompleteness(s);
					break;
				case "complete":
					s.setComplete((boolean) patch.get(key));
					//checkCompleteness(s);
					break;
					// A.V.
//				case "replaceLostToken":
//					s.setReplaceLostToken((boolean) patch.get(key));
//					break;
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
				case "billingAccountNumber":
					s.setBillingAccountNumber((String) patch.get(key));
					break;
				case "businessJustification":
					s.setBusinessJustification((String) patch.get(key));
					break;
				case "managerPhone":
					s.setManagerPhone((String) patch.get(key));
					break;
				case "managerTitle":
					s.setManagerTitle((String) patch.get(key));
					break;

				case "applicationCoordinatorName":
					s.setApplicationCoordinatorName((String) patch.get(key));
					break;
				case "applicationCoordinatorPhone":
					s.setApplicationCoordinatorPhone((String) patch.get(key));
					break;
					
					//new cases
				case "ContractorCompanyName":
					s.setContractorCompanyName((String) patch.get(key));
					break;
				case "ContractorWorkOrder":
					s.setContractorWorkOrder((String) patch.get(key));
					break;
				case "ContractorExperationDate":
					s.setContractorExperationDate((String) patch.get(key));
					break;
				case "ExperationDate":
					s.setExperationDate((String) patch.get(key));
					break;		
				case "AllWebmail":
					s.setAllWebmail((boolean) patch.get(key));
					break;
				case "Android":
					s.setAndroid((boolean) patch.get(key));
					break;
				case "Computer":
					s.setComputer((boolean) patch.get(key));
					break;
				case "ContractorWorkforce1":
					s.setContractorWorkforce1((boolean) patch.get(key));
					break;
				case "CountywidePolicyA":
					s.setCountywidePolicyA((boolean) patch.get(key));
					break;
				case "CountywidePolicyB":
					s.setCountywidePolicyB((boolean) patch.get(key));
					break;
				case "EmployeeWorkforce":
					s.setEmployeeWorkforce((boolean) patch.get(key));
					break;
				case "HardTokenVPN":
					s.setHardTokenVPN((boolean) patch.get(key));
					break;
				case "IOS":
					s.setiOS((boolean) patch.get(key));
					break;
				case "LACMobile":
					s.setLacMobile((boolean) patch.get(key));
					break;
				case "LACounty":
					s.setLaCounty((boolean) patch.get(key));
					break;
				case "NewToken":
					s.setNewToken((boolean) patch.get(key));
					break;
				case "O365Email":
					s.setO365Email((boolean) patch.get(key));
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
				case "isEmployee":
					s.setEmployee((boolean) patch.get(key));
					break;
				case "applicationCoordinatorEmail":
					s.setApplicationCoordinatorEmail((String) patch.get(key));
					break;
				case "managerEmail":
					s.setManagerEmail((String) patch.get(key));
					break;
				case "divChiefManagerName":
					s.setDivChiefManagerName((String) patch.get(key));
					break;
				case "divChiefManagerPhone":
					s.setDivChiefManagerPhone((String) patch.get(key));
					break;
				case "divChiefManagerEmail":
					s.setDivChiefManagerEmail((String) patch.get(key));
					break;
				case "departmentHeadName":
					s.setDepartmentHeadName((String) patch.get(key));
					break;
				case "departmentHeadPhone":
					s.setDepartmentHeadPhone((String) patch.get(key));
					break;
				case "departmentHeadEmail":
					s.setDepartmentHeadEmail((String) patch.get(key));
					break;
				case "deptInfoSecurityOfficerName":
					s.setDeptInfoSecurityOfficerName((String) patch.get(key));
					break;
				case "deptInfoSecurityOfficerPhone":
					s.setDeptInfoSecurityOfficerPhone((String) patch.get(key));
					break;
				case "deptInfoSecurityOfficerEmail":
					s.setDeptInfoSecurityOfficerEmail((String) patch.get(key));
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
				case "systemApplication":
					s.setSystemApplication((String) patch.get(key));
					break;
				case "groupName":
					s.setGroupName((String) patch.get(key));
					break;
				case "oldGroup":
					s.setOldGroup((String) patch.get(key));
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
				case "companyName":
					s.setCompanyName((String) patch.get(key));
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
				case "contractWorkOrderNumber":
					s.setContractWorkOrderNumber((String) patch.get(key));
					break;
				case "contractExpirationDate":
					s.setContractExpirationDate((String) patch.get(key));
					break;
					
					
					//not needed
//				case "companyEmailAddress3":
//					s.setCompanyEmailAddress3((String) patch.get(key));
//					break;
//				case "defaultCountyWidePolicy":
//					s.setDefaultCountyWidePolicy((boolean) patch.get(key));
//					break;
//				case "departmentPolicyRule0":
//					s.setDepartmentPolicyRule0((boolean) patch.get(key));
//					break;
//				case "departmentPolicyRule1":
//					s.setDepartmentPolicyRule1((boolean) patch.get(key));
//					break;
//				case "departmentPolicyRule2":
//					s.setDepartmentPolicyRule2((boolean) patch.get(key));
//					break;
//				case "departmentPolicyRule3":
//					s.setDepartmentPolicyRule3((boolean) patch.get(key));
//					break;
//				case "departmentPolicyRule4":
//					s.setDepartmentPolicyRule4((boolean) patch.get(key));
//					break;
//				case "socialNetworkingFacebook":
//					s.setSocialNetworkingFacebook((boolean) patch.get(key));
//					break;
//				case "socialNetworkingTwitter":
//					s.setSocialNetworkingTwitter((boolean) patch.get(key));
//					break;
//				case "socialNetworkingLinkedIn":
//					s.setSocialNetworkingLinkedIn((boolean) patch.get(key));
//					break;
//				case "securIdVpn":
//					s.setSecurIdVpn((boolean) patch.get(key));
//					break;
//				case "adaptiveAuthenticationVpn":
//					s.setAdaptiveAuthenticationVpn((boolean) patch.get(key));
//					break;
//				case "internetApplication":
//					s.setInternetApplication((boolean) patch.get(key));
//					break;
//				case "exchangeEmail":
//					s.setExchangeEmail((boolean) patch.get(key));
//					break;
//				case "emailEncryption":
//					s.setEmailEncryption((boolean) patch.get(key));
//					break;
//				case "laCountyGovAccess":
//					s.setLaCountyGovAccess((boolean) patch.get(key));
//					break;
//				case "tokenlessAuthentication":
//					s.setTokenlessAuthentication((boolean) patch.get(key));
//					break;
//				case "lacMobileWifiAccess":
//					s.setLacMobileWifiAccess((boolean) patch.get(key));
//					break;
//				case "cherwellSms":
//					s.setCherwellSms((boolean) patch.get(key));
//					break;
//				case "windowsRightsMgmt":
//					s.setWindowsRightsMgmt((boolean) patch.get(key));
//					break;
//				case "gmailAccess":
//					s.setGmailAccess((boolean) patch.get(key));
//					break;
//				case "yahooMailAccess":
//					s.setYahooMailAccess((boolean) patch.get(key));
//					break;
//				case "otherEmailDomain":
//					s.setOtherEmailDomain((String) patch.get(key));
//					break;
//				case "unixAccountNumber":
//					s.setUnixAccountNumber((String) patch.get(key));
//					break;
//				case "managerName":
//					s.setManagerName((String) patch.get(key));
//					break;
//				case "ManagerName3":
//					s.setManagerName3((String) patch.get(key));
//					break;
//				case "Phone2":
//					s.setPhone2((String) patch.get(key));
//					break;
//				case "Phone3":
//					s.setPhone3((String) patch.get(key));
//					break;
//				case "Phone4":
//					s.setPhone4((String) patch.get(key));
//					break;
//				case "grpName2":
//					s.setGrpName2((String) patch.get(key));
//					break;
//				case "grpName3":
//					s.setGrpName3((String) patch.get(key));
//					break;
//				case "v2FullName":
//					s.setV2FullName((String) patch.get(key));
//					break;
//				case "oldGrp1":
//					s.setOldGrp1((String) patch.get(key));
//					break;
//				case "oldGrp2":
//					s.setOldGrp2((String) patch.get(key));
//					break;
//				case "oldGrp3":
//					s.setOldGrp3((String) patch.get(key));
//					break;
//				case "systemApplication1":
//					s.setSystemApplication1((String) patch.get(key));
//					break;
//				case "systemApplication2":
//					s.setSystemApplication2((String) patch.get(key));
//					break;
//				case "systemApplication3":
//					s.setSystemApplication3((String) patch.get(key));
//					break;
//				case "workPhoneNumber":
//					s.setWorkPhoneNumber((String) patch.get(key));
//					break;
//				case "companyName2":
//					s.setCompanyName2((String) patch.get(key));
//					break;
//				case "companyPhoneNumber3":
//					s.setCompanyPhoneNumber3((String) patch.get(key));
//					break;
//				case "companyPhoneNumber5":
//					s.setCompanyPhoneNumber5((String) patch.get(key));
//					break;
//				case "contractWorkOrderNumber2":
//					s.setContractWorkOrderNumber2((String) patch.get(key));
//					break;
//				case "contractWorkOrderNumber4":
//					s.setContractWorkOrderNumber4((String) patch.get(key));
//					break;
//				case "departmentName3":
//					s.setDepartmentName3((String) patch.get(key));
//					break;
//				case "fullName2":
//					s.setFullName2((String) patch.get(key));
//					break;
//				case "fullName3":
//					s.setFullName3((String) patch.get(key));
//					break;
//				case "groupName1":
//					s.setGroupName1((String) patch.get(key));
//					break;
//				case "businessJustification2":
//					s.setBusinessJustification2((String) patch.get(key));
//					break;
//				case "CustomerName2":
//					s.setCustomerName2((String) patch.get(key));
//					break;
//				case "DepartmentInfo":
//					s.setDepartmentInfo((String) patch.get(key));
//					break;
					
				default:
					break;
					*/


					}
				}

				if (patch.keySet().contains("isComplete") || patch.keySet().contains("complete")) {
					checkCompleteness(s);	
				}
				
				s = serviceRequestDao.saveServiceRequest(s);
				return s;
				
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			}
		}
		
		
		
		public void checkCompleteness(ServiceRequest s) throws Exception {
			if (s.isComplete()) {
				s.setAgreementId(Agreements.sendAgreement(s));
				// 5 second timeout - account for time for Adobe to send first email
				TimeUnit.SECONDS.sleep(5);
				s = AgreementEvents.updateRequestStatus(s);
			}
		}

		// New Admin Endpoints for participant info.
		// Password-protected endpoints.
		@Autowired
	    private DivChiefManagerDao divChiefManagerDao;
		
		@CrossOrigin(origins= "*")
	    @GetMapping("/div_chief_managers")
	    public List<DivChiefManager> divChiefManagers(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		ModelMap models) throws Exception {
	    	
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return divChiefManagerDao.getDivChiefManagers();
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	    }
	    
		@CrossOrigin(origins= "*")
	    @GetMapping("/div_chief_managers/{id}")
	    public DivChiefManager divChiefManager(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				DivChiefManager d = divChiefManagerDao.getDivChiefManager(id);
		    	if (d == null) throw new ResponseStatusException( HttpStatus.NOT_FOUND, 
		    			"Division Chief / Manager not found" );
		    	return d;
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
		@CrossOrigin(origins= "*")
	    @PostMapping("/div_chief_managers")
	    @ResponseStatus(HttpStatus.CREATED)
	    public DivChiefManager addDivChiefManager(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@RequestBody DivChiefManager d) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return divChiefManagerDao.saveDivChiefManager(d);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    }
	    
		@CrossOrigin(origins= "*")
	    @PatchMapping("/div_chief_managers/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public DivChiefManager updateDivChiefManager(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id, 
	    		@RequestBody Map<String,Object> patch ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	

				DivChiefManager d = divChiefManagerDao.getDivChiefManager(id);
		    	
		    	for (String key : patch.keySet()) {
		    		
		    		switch(key) {
		    		
			    		case "name":
			    			d.setName( (String) patch.get(key));
			    			break;
			    		case "phone":
			    			d.setPhone( (String) patch.get(key));
			    			break;    
			    		case "email":
			    			d.setEmail( (String) patch.get(key));
			    		default:
			    			break;
		    		}
		    		
		    	}
		    	
		    	d = divChiefManagerDao.saveDivChiefManager(d);
		    	return d;

			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	   
	    }
	    
		@CrossOrigin(origins= "*")
	    @DeleteMapping("/div_chief_managers/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteDivChiefManager(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id) {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				divChiefManagerDao.deleteDivChiefManager(id);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @Autowired
	    private DepartmentHeadDao departmentHeadDao;

	    @CrossOrigin(origins= "*")
	    @GetMapping("/department_heads")
	    public List<DepartmentHead> departmentHeads(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		ModelMap models) throws Exception {
	    	
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return departmentHeadDao.getDepartmentHeads();
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	    }
	    
	    @CrossOrigin(origins= "*")
	    @GetMapping("/department_heads/{id}")
	    public DepartmentHead departmentHead(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				DepartmentHead d = departmentHeadDao.getDepartmentHead(id);
		    	if (d == null) throw new ResponseStatusException( HttpStatus.NOT_FOUND, 
		    			"Department Head not found" );
		    	return d;
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @CrossOrigin(origins= "*")
	    @PostMapping("/department_heads")
	    @ResponseStatus(HttpStatus.CREATED)
	    public DepartmentHead addDepartmentHead(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@RequestBody DepartmentHead d) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return departmentHeadDao.saveDepartmentHead(d);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    }
	    
	    @CrossOrigin(origins= "*")
	    @PatchMapping("/department_heads/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public DepartmentHead updateDepartmentHead(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id, 
	    		@RequestBody Map<String,Object> patch ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	

				DepartmentHead d = departmentHeadDao.getDepartmentHead(id);
		    	
		    	for (String key : patch.keySet()) {
		    		
		    		switch(key) {
		    		
			    		case "name":
			    			d.setName( (String) patch.get(key));
			    			break;
			    		case "phone":
			    			d.setPhone( (String) patch.get(key));
			    			break;    
			    		case "email":
			    			d.setEmail( (String) patch.get(key));
			    		default:
			    			break;
		    		}
		    		
		    	}
		    	
		    	d = departmentHeadDao.saveDepartmentHead(d);
		    	return d;

			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	   
	    }
	    
	    @CrossOrigin(origins= "*")
	    @DeleteMapping("/department_heads/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteDepartmentHead(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id) {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				departmentHeadDao.deleteDepartmentHead(id);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @Autowired
	    private ApplicationCoordinatorDao applicationCoordinatorDao;

	    @GetMapping("/application_coordinators")
	    public List<ApplicationCoordinator> applicationCoordinators(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		ModelMap models) throws Exception {
	    	
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return applicationCoordinatorDao.getApplicationCoordinators();
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	    }
	    
	    @GetMapping("/application_coordinators/{id}")
	    public ApplicationCoordinator applicationCoordinator(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				ApplicationCoordinator a = applicationCoordinatorDao.getApplicationCoordinator(id);
		    	if (a == null) throw new ResponseStatusException( HttpStatus.NOT_FOUND, 
		    			"Application Coordinator not found" );
		    	return a;
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @PostMapping("/application_coordinators")
	    @ResponseStatus(HttpStatus.CREATED)
	    public ApplicationCoordinator addApplicationCoordinator(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@RequestBody ApplicationCoordinator a) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return applicationCoordinatorDao.saveApplicationCoordinator(a);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    }
	    
	    @PatchMapping("/application_coordinators/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public ApplicationCoordinator updateApplicationCoordinator(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id, 
	    		@RequestBody Map<String,Object> patch ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	

				ApplicationCoordinator a = applicationCoordinatorDao.getApplicationCoordinator(id);
		    	
		    	for (String key : patch.keySet()) {
		    		
		    		switch(key) {
		    		
			    		case "name":
			    			a.setName( (String) patch.get(key));
			    			break;
			    		case "phone":
			    			a.setPhone( (String) patch.get(key));
			    			break;    
			    		case "email":
			    			a.setEmail( (String) patch.get(key));
			    		default:
			    			break;
		    		}
		    		
		    	}
		    	
		    	a = applicationCoordinatorDao.saveApplicationCoordinator(a);
		    	return a;

			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	   
	    }
	    
	    @DeleteMapping("/application_coordinators/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteApplicationCoordinator(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id) {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				applicationCoordinatorDao.deleteApplicationCoordinator(id);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @Autowired
	    private DeptInfoSecurityOfficerDao deptInfoSecurityOfficerDao;

	    @GetMapping("/dept_info_security_officers")
	    public List<DeptInfoSecurityOfficer> deptInfoSecurityOfficers(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		ModelMap models) throws Exception {
	    	
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return deptInfoSecurityOfficerDao.getDeptInfoSecurityOfficers();
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	    }
	    
	    @GetMapping("/dept_info_security_officers/{id}")
	    public DeptInfoSecurityOfficer deptInfoSecurityOfficer(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				DeptInfoSecurityOfficer d = deptInfoSecurityOfficerDao.getDeptInfoSecurityOfficer(id);
		    	if (d == null) throw new ResponseStatusException( HttpStatus.NOT_FOUND, 
		    			"Department Info Security Officer not found" );
		    	return d;
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
	    
	    @PostMapping("/dept_info_security_officers")
	    @ResponseStatus(HttpStatus.CREATED)
	    public DeptInfoSecurityOfficer addDeptInfoSecurityOfficer(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@RequestBody DeptInfoSecurityOfficer d) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				return deptInfoSecurityOfficerDao.saveDeptInfoSecurityOfficer(d);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    }
	    
	    @PatchMapping("/dept_info_security_officers/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public DeptInfoSecurityOfficer updateDeptInfoSecurityOfficer(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id, 
	    		@RequestBody Map<String,Object> patch ) throws Exception {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	

				DeptInfoSecurityOfficer d = deptInfoSecurityOfficerDao.getDeptInfoSecurityOfficer(id);
		    	
		    	for (String key : patch.keySet()) {
		    		
		    		switch(key) {
		    		
			    		case "name":
			    			d.setName( (String) patch.get(key));
			    			break;
			    		case "phone":
			    			d.setPhone( (String) patch.get(key));
			    			break;    
			    		case "email":
			    			d.setEmail( (String) patch.get(key));
			    		default:
			    			break;
		    		}
		    		
		    	}
		    	
		    	d = deptInfoSecurityOfficerDao.saveDeptInfoSecurityOfficer(d);
		    	return d;

			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
			
	   
	    }
	    
	    @DeleteMapping("/dept_info_security_officers/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteDeptInfoSecurityOfficer(
	    		@RequestHeader("email") String email,
	    		@RequestHeader("password") String password, 
	    		@PathVariable Integer id) {
	    	String dbPassword = adminDao.getAdmin(email).getPassword();
			if (dbPassword.equals(password)) {	
				deptInfoSecurityOfficerDao.deleteDeptInfoSecurityOfficer(id);
			} else
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"User does not have authorization to view this page");
	    
	    }
		
	}