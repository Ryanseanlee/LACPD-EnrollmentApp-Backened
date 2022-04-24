package sbrest.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
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

import com.box.sdk.BoxCCGAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;

import sbrest.model.ServiceRequest;
import sbrest.model.dao.AdminDao;
import sbrest.model.dao.ServiceRequestDao;
import sbrest.service.SendEmail;
import sbrest.signapi.AgreementEvents;
import sbrest.signapi.Agreements;
import sbrest.signapi.OAuthTokens;

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
		
		originalServiceRequest.setAgreementId(s.getAgreementId());
		originalServiceRequest.setReplaceLostToken(s.isReplaceLostToken());
		originalServiceRequest.setAddLogonId(s.isAddLogonId());
		originalServiceRequest.setChangeLogonId(s.isChangeLogonId());
		originalServiceRequest.setDeleteLogonId(s.isDeleteLogonId());
		//personal information
		originalServiceRequest.setLastName(s.getLastName());
		originalServiceRequest.setFirstName(s.getFirstName());
		originalServiceRequest.setMiddleInitial(s.getMiddleInitial());
		originalServiceRequest.setEmailAddress(s.getEmailAddress());
		originalServiceRequest.setCountyDepartmentName(s.getCountyDepartmentName());
		originalServiceRequest.setCountyDepartmentNumber(s.getCountyDepartmentNumber());
		originalServiceRequest.setPhoneNumber(s.getPhoneNumber());
		originalServiceRequest.setWorkPhoneNumber(s.getWorkPhoneNumber());
		originalServiceRequest.setEmployeeNumber(s.getEmployeeNumber());
		originalServiceRequest.setContractorName(s.getContractorName());
		originalServiceRequest.setWorkOrderNumberInput(s.getWorkOrderNumberInput());
		originalServiceRequest.setExpirationDate(s.getExpirationDate());
		
		//address information
		originalServiceRequest.setAddress(s.getAddress());
		originalServiceRequest.setCity(s.getCity());
		originalServiceRequest.setState(s.getState());
		originalServiceRequest.setZipCode(s.getZipCode());
		
		//Internet access
		originalServiceRequest.setCountyWidePolicyA(s.isCountyWidePolicyA());
		originalServiceRequest.setCountyWidePolicyB(s.isCountyWidePolicyB());
		originalServiceRequest.setAllWebmail(s.isAllWebmail());
		originalServiceRequest.setStreamMedia(s.isStreamMedia());
		originalServiceRequest.setJustification(s.getJustification());
		
		//access information
		originalServiceRequest.setIbmLogonId(s.getIbmLogonId());
		originalServiceRequest.setMajorGroupCode(s.getMajorGroupCode());
		originalServiceRequest.setLsoGroupCode(s.getLsoGroupCode());
		originalServiceRequest.setSecurityAuthorization(s.getSecurityAuthorization());
		originalServiceRequest.setUnixLogonId(s.getUnixLogonId());
		originalServiceRequest.setApplication(s.getApplication());
		originalServiceRequest.setAccessGroup(s.getAccessGroup());
		originalServiceRequest.setBillingAccountNumber(s.getBillingAccountNumber());
		
		//additional information
		originalServiceRequest.setLaCountyGovAccess(s.isLaCountyGovAccess());
		originalServiceRequest.setLacMobileWifiAccess(s.isLacMobileWifiAccess());
		originalServiceRequest.setO360Email(s.isO360Email());
		
		
		originalServiceRequest.setSubmitted(s.isSubmitted());
		originalServiceRequest.setManagerFirstName(s.getManagerFirstName());
		originalServiceRequest.setManagerLastName(s.getManagerLastName());
		originalServiceRequest.setManagerEmail(s.getManagerEmail());
		originalServiceRequest.setManagerPhone(s.getManagerPhone());

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
				//personal information
			case "lastName":
				s.setLastName((String) patch.get(key));
				break;
			case "firstName":
				s.setFirstName((String) patch.get(key));
				break;
			case "middleInitial":
				s.setMiddleInitial((String) patch.get(key));
				break;
			case "emailAddress":
				s.setEmailAddress((String) patch.get(key));
				break;
			case "countyDepartmentName":
				s.setCountyDepartmentName((String) patch.get(key));
				break;
			case "countyDepartmentNumber":
				s.setCountyDepartmentNumber((String) patch.get(key));
				break;
			case "phoneNumber":
				s.setPhoneNumber((String) patch.get(key));
				break;
			case "workPhoneNumber":
				s.setWorkPhoneNumber((String) patch.get(key));
				break;
			case "employeeNumber":
				s.setEmployeeNumber((String) patch.get(key));
				break;
			case "contractorName":
				s.setContractorName((String) patch.get(key));
				break;
			case "workOrderNumberInput":
				s.setWorkOrderNumberInput((String) patch.get(key));
				break;
			case "expirationDate":
				s.setExpirationDate((String) patch.get(key));
				break;
				
			//address information
			case "address":
				s.setAddress((String) patch.get(key));
				break;
			case "city":
				s.setCity((String) patch.get(key));
				break;
			case "state":
				s.setState((String) patch.get(key));
				break;
			case "zipCode":
				s.setZipCode((String) patch.get(key));
				break;
				
			//Internet access
			case "countyWidePolicyA":
				s.setCountyWidePolicyA((Boolean) patch.get(key));
				break;
			case "countyWidePolicyB":
				s.setCountyWidePolicyB((Boolean) patch.get(key));
				break;
			case "allWebmail":
				s.setAllWebmail((Boolean) patch.get(key));
				break;
			case "streamMedia":
				s.setStreamMedia((Boolean) patch.get(key));
				break;
			case "justification":
				s.setJustification((String) patch.get(key));
				break;
				
			//access information
				
			case "ibmLogonId":
				s.setIbmLogonId((String) patch.get(key));
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
			case "unixLogonId":
				s.setUnixLogonId((String) patch.get(key));
				break;
			case "application":
				s.setApplication((String) patch.get(key));
				break;
			case "accessGroup":
				s.setAccessGroup((String) patch.get(key));
				break;
			case "billingAccountNumber":
				s.setBillingAccountNumber((String) patch.get(key));
				break;
				
			//additional information
			case "laCountyGovAccess":
				s.setLaCountyGovAccess((Boolean) patch.get(key));
				break;
			case "lacMobileWifiAccess":
				s.setLacMobileWifiAccess((Boolean) patch.get(key));
				break;
			case "o360Email":
				s.setO360Email((Boolean) patch.get(key));
				break;
				
			//other info
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
			case "managerEmail":
				s.setManagerEmail((String) patch.get(key));
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
			
			// IRV need email
			String adminEmail = adminDao.getAdmin("ivalen13@calstatela.edu").getEmail();
			String subject = "New Request Submitted (#" + s.getRequestNumber() + ")";
			String text = "A new service request was submitted by " + s.getFirstName() + " " + s.getLastName()
					+ ". The request number is "+ s.getRequestNumber() +".\nLog in to review the request at " + "http://localhost:4200/admin"
					+ "\n\n" + "[THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL]";
 
			sendEmail.sendSimpleMessage(adminEmail, subject, text);

			
			String userEmail = "";
			if (s.isEmployee()) {
				userEmail = s.getEmailAddress();
			}
			else {
				userEmail = s.getEmailAddress();
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
	
	//cron = "0 1 1 * * ?"
	@Async
	@Scheduled(cron = "0 0/1 * * ?")
	public void updateRequestStatuses() throws Exception {
		List<ServiceRequest> serviceRequests = serviceRequestDao.getServiceRequests();
		for (ServiceRequest s : serviceRequests) {
			s = AgreementEvents.updateRequestStatus(s);
			
			
			if(s.getRequestStatus().startsWith("Signed")) {
				String url = getUrl(s.getAgreementId()).toString();
				boxUpload(parseUrl(url), s.getRequestNumber());
				s.setRequestStatus("Uploaded to Box");
			}
			
			
			
			serviceRequestDao.saveServiceRequest(s);
		}
	}
	
	public JSONObject getUrl (String agreementId) throws Exception {
		String accessToken = OAuthTokens.getOauthAccessToken();
		String url = "https://api.na3.adobesign.com:443/api/rest/v6/agreements/" + agreementId + "/combinedDocument/url";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		headers.put("Authorization", accessToken);

		JSONObject responseJson = (JSONObject) OAuthTokens.makeApiCall(url, "GET", headers, null);
		
		return responseJson;
		
	}
	
	public String parseUrl(String responseJson) {
		String substring = responseJson.substring(8, responseJson.length() - 2);
		String url = "";
		
		
		
		for(int i = 0; i < substring.length(); i++) {
			if(substring.charAt(i) != '\\') {
				url += substring.charAt(i);
			}
		}
		
		return url;
	}
	
	public void boxUpload(String url, int requestNumber) throws IOException {    
	        
		   BoxCCGAPIConnection api = BoxCCGAPIConnection.applicationServiceAccountConnection(
	        	    "ffc823n3uuf15g5vooj45xg3eri29ukn",
	        	    "I9r7ZIFUpEUiNpTlDpfE9WVz4Wro4qrt",
	        	    "873161760"
	        	);
	        
	        api.asUser("18429209010");
	        
	        BoxFolder folder = new BoxFolder(api, "155054681560");
	        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
	        
	        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
	        
	        
	        for (BoxItem.Info itemInfo : rootFolder) {
	            System.out.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName());
	        }
	        
//	        FileInputStream stream = new FileInputStream("./input/test.txt");
//	        BoxFile.Info newFileInfo = folder.uploadFile(stream, "test.txt");
	        
	        FileInputStream stream = new FileInputStream("SignedAgreement.pdf");
	        BoxFile.Info newFileInfo = folder.uploadFile(in, "SignedAgreement" + requestNumber + ".pdf");
	        
	        stream.close();
	
	}
	
	
}