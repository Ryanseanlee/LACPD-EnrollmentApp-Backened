package sbrest.signapi;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

import sbrest.model.ServiceRequest;

public class Agreements {

	@SuppressWarnings("unchecked")
	public static String sendAgreement(ServiceRequest serviceRequest) throws Exception {
		// URL to invoke the agreements end point.
		String accessToken = OAuthTokens.getOauthAccessToken();
		String url = "https://api.na3.adobesign.com:443/api/rest/v6/agreements";
		
		String email = "";
		String agreementName = "";
		String documentId = "";
		String workflowId = "";
		String fileLabel = "";
		email = serviceRequest.getEmailAddress();
		agreementName = "[NEW FORMS] Employee/Contractor Agreement";
		documentId = "CBJCHBCAABAAVz2rFlj7cDFElyaLVB6-vuf14BML9WTs";
		workflowId = "CBJCHBCAABAAs39_RUgLpEU8rQ7Tcwn1J-hKbTGPxqml";
		fileLabel = "[NEW FORMS] Employee/Contractor";
		
		

		// Create HTTP header list
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		headers.put("Authorization", accessToken);

		// Invoke API and get JSON response. The ID name determines what the input
		// (request) JSON structure should contain.
		JSONObject responseJson = null;

		// Based on the document type retrieved from above, set the corresponding item
		// in the request JSON structure.

		JSONObject requestJson = new JSONObject();
		ArrayList<JSONObject> fileInfos = new ArrayList<JSONObject>();
		
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("libraryDocumentId", documentId);
		
		fileInfo.put("label", fileLabel);
		
		
		fileInfos.add(fileInfo);
		requestJson.put("fileInfos", fileInfos);
		requestJson.put("name", agreementName);
		requestJson.put("state", "IN_PROCESS");
		requestJson.put("signatureType", "ESIGN");
		
		requestJson.put("workflowId", workflowId);
		
		ArrayList<JSONObject> participantSetsInfo = new ArrayList<JSONObject>();

		// Add 1st participant (Requester)
		JSONObject participantSets1Info = new JSONObject();
		ArrayList<JSONObject> memberInfos1 = new ArrayList<JSONObject>();
		JSONObject memberInfo1Json = new JSONObject();
		memberInfo1Json.put("email", email);
		memberInfos1.add(memberInfo1Json);
		participantSets1Info.put("order", 1);
		participantSets1Info.put("role", "SIGNER");
		participantSets1Info.put("memberInfos", memberInfos1);
		participantSets1Info.put("label", "Signer");
		participantSetsInfo.add(participantSets1Info);

		// Add 2nd participant (Manager)
		if (serviceRequest.getManagerEmail() != null) {
			JSONObject participantSets2Info = new JSONObject();
			ArrayList<JSONObject> memberInfos2 = new ArrayList<JSONObject>();
			JSONObject memberInfo2Json = new JSONObject();
			memberInfo2Json.put("email", serviceRequest.getManagerEmail());
			memberInfos2.add(memberInfo2Json);
			participantSets2Info.put("order", 2);
			participantSets2Info.put("role", "SIGNER");
			participantSets2Info.put("memberInfos", memberInfos2);
			participantSets2Info.put("label", "Manager");
			participantSetsInfo.add(participantSets2Info);
		}

		// Add 3rd participant (Div Chief / Manager)
		

		// Add 4th participant (Department Head)
		if (serviceRequest.getDepartmentHeadEmail() != null) {
			JSONObject participantSets4Info = new JSONObject();
			ArrayList<JSONObject> memberInfos4 = new ArrayList<JSONObject>();
			JSONObject memberInfo4Json = new JSONObject();
			memberInfo4Json.put("email", serviceRequest.getDepartmentHeadEmail());
			memberInfos4.add(memberInfo4Json);
			participantSets4Info.put("order", 4);
			participantSets4Info.put("role", "SIGNER");
			participantSets4Info.put("memberInfos", memberInfos4);
			participantSets4Info.put("label", "Department Coordinator");
			participantSetsInfo.add(participantSets4Info);
		}

		// Add 5th participant (Department Info Security Officer)
		if (serviceRequest.getDeptInfoSecurityOfficerEmail() != null) {
			JSONObject participantSets5Info = new JSONObject();
			ArrayList<JSONObject> memberInfos5 = new ArrayList<JSONObject>();
			JSONObject memberInfo5Json = new JSONObject();
			memberInfo5Json.put("email", serviceRequest.getDeptInfoSecurityOfficerEmail());
			memberInfos5.add(memberInfo5Json);
			participantSets5Info.put("order", 5);
			participantSets5Info.put("role", "SIGNER");
			participantSets5Info.put("memberInfos", memberInfos5);
			participantSets5Info.put("label", "Dept Info Security Officer");
			participantSetsInfo.add(participantSets5Info);
		}

		// Add 6th participant (ISD / Application Coordinator)
	

		requestJson.put("participantSetsInfo", participantSetsInfo);

		ArrayList<JSONObject> mergeFieldInfo = populateTemplate(serviceRequest);

		requestJson.put("mergeFieldInfo", mergeFieldInfo);
		
		System.out.println("Request" + requestJson.toJSONString());

		responseJson = (JSONObject) OAuthTokens.makeApiCall(url, "POST", headers, requestJson.toJSONString());

		String agreementId = responseJson.get("id").toString();
		return agreementId;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<JSONObject> populateTemplate(ServiceRequest serviceRequest) {
		ArrayList<JSONObject> fieldJsonArray = new ArrayList<JSONObject>();

		JSONObject lastName = new JSONObject();
		lastName.put("fieldName", "lastName");
		lastName.put("defaultValue", serviceRequest.getLastName());
		fieldJsonArray.add(lastName);

		JSONObject firstName = new JSONObject();
		firstName.put("fieldName", "firstName");
		firstName.put("defaultValue", serviceRequest.getFirstName());
		fieldJsonArray.add(firstName);

		JSONObject middleInitial = new JSONObject();
		middleInitial.put("fieldName", "middleInitial");
		middleInitial.put("defaultValue", serviceRequest.getMiddleInitial());
		fieldJsonArray.add(middleInitial);

		// Old Versions below
		// output += "{ \"fieldName\": \"lastName\", \"defaultValue\": \"" + serviceRequest.getLastName() + "\"}, ";
		// output += "{ \"fieldName\": \"firstName\", \"defaultValue\": \"" + serviceRequest.getFirstName() + "\"}, ";
		// output += "{ \"fieldName\": \"middleInitial\", \"defaultValue\": \"" + serviceRequest.getMiddleInitial() + "\"}, ";

		String fullName = serviceRequest.getFirstName() + " ";
		if (serviceRequest.getMiddleInitial() != null) {
			if (!serviceRequest.getMiddleInitial().isEmpty()) {
				fullName += serviceRequest.getMiddleInitial() + " ";
			}
		}
		fullName += serviceRequest.getLastName();

//		JSONObject fullName1 = new JSONObject();
//		fullName1.put("fieldName", "fullName1");
//		fullName1.put("defaultValue", fullName);
//		fieldJsonArray.add(fullName1);
//
		JSONObject fullName2 = new JSONObject();
		fullName2.put("fieldName", "fullName2");
		fullName2.put("defaultValue", fullName);
		fieldJsonArray.add(fullName2);

		JSONObject fullName3 = new JSONObject();
		fullName3.put("fieldName", "fullName3");
		fullName3.put("defaultValue", fullName);
		fieldJsonArray.add(fullName3);
		

		String v2FullName = serviceRequest.getLastName() + ", " + serviceRequest.getFirstName();
		if (serviceRequest.getMiddleInitial() != null) {
			if (!serviceRequest.getMiddleInitial().isEmpty()) {
				v2FullName += " " + serviceRequest.getMiddleInitial();
			}
		}
		JSONObject v2FullNameJson = new JSONObject();
		v2FullNameJson.put("fieldName", "v2FullName");
		v2FullNameJson.put("defaultValue", v2FullName);
		fieldJsonArray.add(v2FullNameJson);

		JSONObject submitDate = new JSONObject();
		submitDate.put("fieldName", "submitDate");
		submitDate.put("defaultValue", serviceRequest.getSubmitDate());
		fieldJsonArray.add(submitDate);
		//output += "{ \"fieldName\": \"submitDate\", \"defaultValue\": \"" + serviceRequest.getSubmitDate() + "\"}, ";

		
		if (serviceRequest.isAddLogonId()) {
			JSONObject addLogonId = new JSONObject();
			addLogonId.put("fieldName", "addLogonId");
			addLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(addLogonId);
			//output += "{ \"fieldName\": \"addLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isChangeLogonId()) {
			JSONObject changeLogonId = new JSONObject();
			changeLogonId.put("fieldName", "changeLogonId");
			changeLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(changeLogonId);
			//output += "{ \"fieldName\": \"changeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isDeleteLogonId()) {
			JSONObject deleteLogonId = new JSONObject();
			deleteLogonId.put("fieldName", "deleteLogonId");
			deleteLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(deleteLogonId);
			//output += "{ \"fieldName\": \"deleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject companyEmailAddress3 = new JSONObject();
		companyEmailAddress3.put("fieldName", "companyEmailAddress3");
		companyEmailAddress3.put("defaultValue", serviceRequest.getEmailAddress());
		fieldJsonArray.add(companyEmailAddress3);
		//output += "{ \"fieldName\": \"companyEmailAddress3\", \"defaultValue\": \"" + serviceRequest.getCompanyEmailAddress() + "\"}, ";

		JSONObject companyName2 = new JSONObject();
		companyName2.put("fieldName", "companyName2");
		companyName2.put("defaultValue", serviceRequest.getContractorName());
		fieldJsonArray.add(companyName2);
		//output += "{ \"fieldName\": \"companyName2\", \"defaultValue\": \"" + serviceRequest.getCompanyName() + "\"}, ";

		JSONObject companyPhoneNumber3 = new JSONObject();
		companyPhoneNumber3.put("fieldName", "companyPhoneNumber3");
		companyPhoneNumber3.put("defaultValue", serviceRequest.getPhoneNumber());
		fieldJsonArray.add(companyPhoneNumber3);
		
		JSONObject Phone2 = new JSONObject();
		Phone2.put("fieldName", "Phone2");
		Phone2.put("defaultValue", serviceRequest.getPhoneNumber());
		fieldJsonArray.add(Phone2);
		
		JSONObject contractWorkOrderNumber2 = new JSONObject();
		contractWorkOrderNumber2.put("fieldName", "contractWorkOrderNumber2");
		contractWorkOrderNumber2.put("defaultValue", serviceRequest.getEmployeeNumber());
		fieldJsonArray.add(contractWorkOrderNumber2);
		
		JSONObject contractWorkOrderNumber4 = new JSONObject();
		contractWorkOrderNumber4.put("fieldName", "contractWorkOrderNumber4");
		contractWorkOrderNumber4.put("defaultValue", serviceRequest.getEmployeeNumber());
		fieldJsonArray.add(contractWorkOrderNumber4);
		
		JSONObject ContractorWorkOrder = new JSONObject();
		ContractorWorkOrder.put("fieldName", "ContractorWorkOrder");
		ContractorWorkOrder.put("defaultValue", serviceRequest.getWorkOrderNumberInput());
		fieldJsonArray.add(ContractorWorkOrder);
		
		JSONObject departmentName3 = new JSONObject();
		departmentName3.put("fieldName", "departmentName3");
		departmentName3.put("defaultValue", serviceRequest.getCountyDepartmentNumber());
		fieldJsonArray.add(departmentName3);
		//output += "{ \"fieldName\": \"departmentNumber3\", \"defaultValue\": \"" + serviceRequest.getDepartmentNumber() + "\"}, ";

		JSONObject workPhoneNumber = new JSONObject();
		workPhoneNumber.put("fieldName", "workPhoneNumber");
		workPhoneNumber.put("defaultValue", serviceRequest.getWorkPhoneNumber());
		fieldJsonArray.add(workPhoneNumber);
		//output += "{ \"fieldName\": \"companyPhoneNumber5\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";

		String fullWorkAddress = serviceRequest.getAddress() + ", " +serviceRequest.getCity() + " " + serviceRequest.getState() + " " + serviceRequest.getZipCode();
		JSONObject workAddress = new JSONObject();
		workAddress.put("fieldName", "companyPhoneNumber5");
		workAddress.put("defaultValue", fullWorkAddress);
		fieldJsonArray.add(workAddress);
		
		String fullWorkAddressandname = serviceRequest.getContractorName() + " " + fullWorkAddress;
		JSONObject ContractorCompanyName = new JSONObject();
		ContractorCompanyName.put("fieldName", "ContractorCompanyName");
		ContractorCompanyName.put("defaultValue", fullWorkAddressandname);
		fieldJsonArray.add(ContractorCompanyName);
		
		JSONObject ibmLogOnId = new JSONObject();
		ibmLogOnId.put("fieldName", "ibmLogOnId");
		ibmLogOnId.put("defaultValue", serviceRequest.getIbmLogonId());
		fieldJsonArray.add(ibmLogOnId);
		//output += "{ \"fieldName\": \"ibmLogOnId\", \"defaultValue\": \"" + serviceRequest.getIbmLogOnId() + "\"}, ";

		JSONObject majorGroupCode = new JSONObject();
		majorGroupCode.put("fieldName", "majorGroupCode");
		majorGroupCode.put("defaultValue", serviceRequest.getMajorGroupCode());
		fieldJsonArray.add(majorGroupCode);
		//output += "{ \"fieldName\": \"majorGroupCode\", \"defaultValue\": \"" + serviceRequest.getMajorGroupCode() + "\"}, ";

		JSONObject lsoGroupCode = new JSONObject();
		lsoGroupCode.put("fieldName", "lsoGroupCode");
		lsoGroupCode.put("defaultValue", serviceRequest.getLsoGroupCode());
		fieldJsonArray.add(lsoGroupCode);
		//output += "{ \"fieldName\": \"lsoGroupCode\", \"defaultValue\": \"" + serviceRequest.getLsoGroupCode() + "\"}, ";
		
		JSONObject securityAuthorization = new JSONObject();
		securityAuthorization.put("fieldName", "securityAuthorization");
		securityAuthorization.put("defaultValue", serviceRequest.getSecurityAuthorization());
		fieldJsonArray.add(securityAuthorization);
		//output += "{ \"fieldName\": \"lsoGroupCode\", \"defaultValue\": \"" + serviceRequest.getLsoGroupCode() + "\"}, ";

		
		JSONObject unixLogOnId = new JSONObject();
		unixLogOnId.put("fieldName", "unixLogOnId");
		unixLogOnId.put("defaultValue", serviceRequest.getUnixLogonId());
		fieldJsonArray.add(unixLogOnId);
		//output += "{ \"fieldName\": \"unixLogOnId\", \"defaultValue\": \"" + serviceRequest.getUnixLogOnId() + "\"}, ";

		JSONObject unixApplication = new JSONObject();
		unixApplication.put("fieldName", "unixApplication");
		unixApplication.put("defaultValue", serviceRequest.getApplication());
		fieldJsonArray.add(unixApplication);
		//output += "{ \"fieldName\": \"unixApplication\", \"defaultValue\": \"" + serviceRequest.getUnixApplication() + "\"}, ";

		JSONObject unixAccessGroup = new JSONObject();
		unixAccessGroup.put("fieldName", "unixAccessGroup");
		unixAccessGroup.put("defaultValue", serviceRequest.getAccessGroup());
		fieldJsonArray.add(unixAccessGroup);
		//output += "{ \"fieldName\": \"unixAccessGroup\", \"defaultValue\": \"" + serviceRequest.getUnixAccessGroup() + "\"}, ";
		
	
		if (serviceRequest.isReplaceLostToken()) {
			JSONObject ReplaceLostStolenToken = new JSONObject();
			ReplaceLostStolenToken.put("fieldName", "ReplaceLostStolenToken");
			ReplaceLostStolenToken.put("defaultValue", "Checked");
			fieldJsonArray.add(ReplaceLostStolenToken);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject ExperationDate = new JSONObject();
		ExperationDate.put("fieldName", "ExperationDate");
		ExperationDate.put("defaultValue", serviceRequest.getExpirationDate());
		fieldJsonArray.add(ExperationDate);
		//output += "{ \"fieldName\": \"billingAccountNumber\", \"defaultValue\": \"" + serviceRequest.getBillingAccountNumber() + "\"}, ";
		
		JSONObject billingAccountNumber = new JSONObject();
		billingAccountNumber.put("fieldName", "billingAccountNumber");
		billingAccountNumber.put("defaultValue", serviceRequest.getBillingAccountNumber());
		fieldJsonArray.add(billingAccountNumber);
		//output += "{ \"fieldName\": \"billingAccountNumber\", \"defaultValue\": \"" + serviceRequest.getBillingAccountNumber() + "\"}, ";

		JSONObject CustomerName2 = new JSONObject();
		CustomerName2.put("fieldName", "CustomerName2");
		CustomerName2.put("defaultValue", fullName);
		fieldJsonArray.add(CustomerName2);
		
		if (serviceRequest.isO360Email()) {
			JSONObject O365Email = new JSONObject();
			O365Email.put("fieldName", "O365Email");
			O365Email.put("defaultValue", "Checked");
			fieldJsonArray.add(O365Email);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		if (serviceRequest.isCountyWidePolicyB()) {
			JSONObject CountywidePolicyB = new JSONObject();
			CountywidePolicyB.put("fieldName", "CountywidePolicyB");
			CountywidePolicyB.put("defaultValue", "Checked");
			fieldJsonArray.add(CountywidePolicyB);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isCountyWidePolicyA()) {
			JSONObject CountywidePolicyA = new JSONObject();
			CountywidePolicyA.put("fieldName", "CountywidePolicyA");
			CountywidePolicyA.put("defaultValue", "Checked");
			fieldJsonArray.add(CountywidePolicyA);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isAllWebmail()) {
			JSONObject allWebmail = new JSONObject();
			allWebmail.put("fieldName", "AllWebmail");
			allWebmail.put("defaultValue", "Checked");
			fieldJsonArray.add(allWebmail);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isStreamMedia()) {
			JSONObject StreamingMedia = new JSONObject();
			StreamingMedia.put("fieldName", "StreamingMedia");
			StreamingMedia.put("defaultValue", "Checked");
			fieldJsonArray.add(StreamingMedia);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject businessJustification = new JSONObject();
		businessJustification.put("fieldName", "businessJustification");
		businessJustification.put("defaultValue", serviceRequest.getJustification());
		fieldJsonArray.add(businessJustification);
		//output += "{ \"fieldName\": \"businessJustification\", \"defaultValue\": \"" + serviceRequest.getBusinessJustification() + "\"}, ";
		


		
		if (serviceRequest.getManagerEmail() != null) {
			JSONObject managerEmail = new JSONObject();
			managerEmail.put("fieldName", "managerEmail");
			managerEmail.put("defaultValue", serviceRequest.getManagerEmail());
			fieldJsonArray.add(managerEmail);
		}
		
		String managerFullName = serviceRequest.getManagerFirstName() + " " + serviceRequest.getManagerLastName();
		JSONObject managerName = new JSONObject();
		managerName.put("fieldName", "managerName");
		managerName.put("defaultValue", managerFullName);
		fieldJsonArray.add(managerName);
		
		JSONObject managerName1 = new JSONObject();
		managerName1.put("fieldName", "managerName1");
		managerName1.put("defaultValue", managerFullName);
		fieldJsonArray.add(managerName1);
		
		JSONObject managerName2 = new JSONObject();
		managerName2.put("fieldName", "managerName2");
		managerName2.put("defaultValue", managerFullName);
		fieldJsonArray.add(managerName2);
		
		JSONObject managerName3 = new JSONObject();
		managerName3.put("fieldName", "managerName3");
		managerName3.put("defaultValue", managerFullName);
		fieldJsonArray.add(managerName3);
		
	
		
		JSONObject justification = new JSONObject();
		justification.put("fieldName", "businessJustification2");
		justification.put("defaultValue", serviceRequest.getJustification());
		fieldJsonArray.add(justification);
		
		JSONObject manname = new JSONObject();
		manname.put("fieldName", "Custom Field 1");
		manname.put("defaultValue", managerFullName);
		fieldJsonArray.add(manname);
		
		if (serviceRequest.getManagerPhone() != null) {
			JSONObject managerPhone = new JSONObject();
			managerPhone.put("fieldName", "managerPhone");
			managerPhone.put("defaultValue", serviceRequest.getManagerPhone());
			fieldJsonArray.add(managerPhone);
		}
	
		
		if (serviceRequest.getDepartmentHeadEmail() != null) {
			JSONObject departmentHeadEmail = new JSONObject();
			departmentHeadEmail.put("fieldName", "departmentHeadEmail");
			departmentHeadEmail.put("defaultValue", serviceRequest.getDepartmentHeadEmail());
			fieldJsonArray.add(departmentHeadEmail);
		}
		
		if (serviceRequest.getDepartmentHeadName() != null) {
			JSONObject departmentHeadName = new JSONObject();
			departmentHeadName.put("fieldName", "applicationCoordinatorName");
			departmentHeadName.put("defaultValue", serviceRequest.getDepartmentHeadName());
			fieldJsonArray.add(departmentHeadName);
		}
		
		if (serviceRequest.getDepartmentHeadPhone() != null) {
			JSONObject departmentHeadPhone = new JSONObject();
			departmentHeadPhone.put("fieldName", "applicationCoordinatorPhone");
			departmentHeadPhone.put("defaultValue", serviceRequest.getDepartmentHeadPhone());
			fieldJsonArray.add(departmentHeadPhone);
		}
		
		if (serviceRequest.getDeptInfoSecurityOfficerEmail() != null) {
			JSONObject deptInfoSecurityOfficerEmail = new JSONObject();
			deptInfoSecurityOfficerEmail.put("fieldName", "deptInfoSecurityOfficerEmail");
			deptInfoSecurityOfficerEmail.put("defaultValue", serviceRequest.getDeptInfoSecurityOfficerEmail());
			fieldJsonArray.add(deptInfoSecurityOfficerEmail);
		}
		
		if (serviceRequest.getDeptInfoSecurityOfficerName() != null) {
			JSONObject deptInfoSecurityOfficerName = new JSONObject();
			deptInfoSecurityOfficerName.put("fieldName", "DepartmentInfo");
			deptInfoSecurityOfficerName.put("defaultValue", serviceRequest.getDeptInfoSecurityOfficerName());
			fieldJsonArray.add(deptInfoSecurityOfficerName);
		}
		
		if (serviceRequest.getDeptInfoSecurityOfficerPhone() != null) {
			JSONObject deptInfoSecurityOfficerPhone = new JSONObject();
			deptInfoSecurityOfficerPhone.put("fieldName", "Phone3");
			deptInfoSecurityOfficerPhone.put("defaultValue", serviceRequest.getDeptInfoSecurityOfficerPhone());
			fieldJsonArray.add(deptInfoSecurityOfficerPhone);
		}
		
		
		

		return fieldJsonArray;
	}

}
