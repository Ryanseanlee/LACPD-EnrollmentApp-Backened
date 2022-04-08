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
		if (serviceRequest.isEmployee()) {
<<<<<<< HEAD
			email = serviceRequest.getEmployeeEmailAddress();
			agreementName = "PD Employee Agreement";
			documentId = "CBJCHBCAABAA0pFVPoxF34OjYxufbH42-pwMBVNbkE4L";
			workflowId = "CBJCHBCAABAAEHNe0UYWSVEoluFqkY6tz3bw8XwWwoXl";
			fileLabel = "PD Employee Forms";
		}
		else {
			email = serviceRequest.getCompanyEmailAddress();
			agreementName = "PD Contractor Agreement";
			documentId = "CBJCHBCAABAA1fprefTPFkmJwDbNeEIYvRWebof8Eb8B";
			workflowId = "CBJCHBCAABAAs8I5FBkEK7YwAbFT3thhIR7cqfXJs39Q";
			fileLabel = "PD Contractor Forms";
=======
			email = serviceRequest.getEmailAddress();
			agreementName = "[NEW FORMS] Employee/Contractor Agreement";
			documentId = "CBJCHBCAABAAVz2rFlj7cDFElyaLVB6-vuf14BML9WTs";
			workflowId = "CBJCHBCAABAAEqCs3Cn9gtrYqxXmA4_VoyrmHd_TJyjp";
			fileLabel = "[NEW FORMS] Employee/Contractor";
		}
		else {
			email = serviceRequest.getEmailAddress();
			agreementName = "[NEW FORMS] Employee/Contractor Agreement";
			documentId = "CBJCHBCAABAAVz2rFlj7cDFElyaLVB6-vuf14BML9WTs";
			workflowId = "CBJCHBCAABAAEqCs3Cn9gtrYqxXmA4_VoyrmHd_TJyjp";
			fileLabel = "[NEW FORMS] Employee/Contractor";
>>>>>>> parent of 73895d7 (agreement and admincontroller fix with new names)
		}
		

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
		if (serviceRequest.getDivChiefManagerEmail() != null) {
			JSONObject participantSets3Info = new JSONObject();
			ArrayList<JSONObject> memberInfos3 = new ArrayList<JSONObject>();
			JSONObject memberInfo3Json = new JSONObject();
			memberInfo3Json.put("email", serviceRequest.getDivChiefManagerEmail());
			memberInfos3.add(memberInfo3Json);
			participantSets3Info.put("order", 3);
			participantSets3Info.put("role", "SIGNER");
			participantSets3Info.put("memberInfos", memberInfos3);
			participantSets3Info.put("label", "Div Chief / Manager");
			participantSetsInfo.add(participantSets3Info);
		}

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
			participantSets4Info.put("label", "Department Head");
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
		if (serviceRequest.getApplicationCoordinatorEmail() != null) {
			JSONObject participantSets6Info = new JSONObject();
			ArrayList<JSONObject> memberInfos6 = new ArrayList<JSONObject>();
			JSONObject memberInfo6Json = new JSONObject();
			memberInfo6Json.put("email", serviceRequest.getApplicationCoordinatorEmail());
			memberInfos6.add(memberInfo6Json);
			participantSets6Info.put("order", 6);
			participantSets6Info.put("role", "SIGNER");
			participantSets6Info.put("memberInfos", memberInfos6);
			participantSets6Info.put("label", "Application Coordinator");
			participantSetsInfo.add(participantSets6Info);
		}

		requestJson.put("participantSetsInfo", participantSetsInfo);

		ArrayList<JSONObject> mergeFieldInfo = populateTemplate(serviceRequest);

		requestJson.put("mergeFieldInfo", mergeFieldInfo);

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
		companyEmailAddress3.put("defaultValue", serviceRequest.getCompanyEmailAddress());
		fieldJsonArray.add(companyEmailAddress3);
		//output += "{ \"fieldName\": \"companyEmailAddress3\", \"defaultValue\": \"" + serviceRequest.getCompanyEmailAddress() + "\"}, ";

		JSONObject companyName2 = new JSONObject();
		companyName2.put("fieldName", "companyName2");
		companyName2.put("defaultValue", serviceRequest.getCompanyName());
		fieldJsonArray.add(companyName2);
		//output += "{ \"fieldName\": \"companyName2\", \"defaultValue\": \"" + serviceRequest.getCompanyName() + "\"}, ";

		JSONObject departmentName3 = new JSONObject();
		departmentName3.put("fieldName", "departmentNumber3");
		departmentName3.put("defaultValue", serviceRequest.getCountyDepartmentName());
		fieldJsonArray.add(departmentName3);
		//output += "{ \"fieldName\": \"departmentNumber3\", \"defaultValue\": \"" + serviceRequest.getDepartmentNumber() + "\"}, ";

		JSONObject companyPhoneNumber5 = new JSONObject();
		companyPhoneNumber5.put("fieldName", "companyPhoneNumber5");
		companyPhoneNumber5.put("defaultValue", serviceRequest.getWorkPhoneNumber());
		fieldJsonArray.add(companyPhoneNumber5);
		//output += "{ \"fieldName\": \"companyPhoneNumber5\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";

		JSONObject workMailingAddress = new JSONObject();
		workMailingAddress.put("fieldName", "workMailingAddress");
		workMailingAddress.put("defaultValue", serviceRequest.getWorkMailingAddress());
		fieldJsonArray.add(workMailingAddress);
		//output += "{ \"fieldName\": \"workMailingAddress\", \"defaultValue\": \"" + serviceRequest.getWorkMailingAddress() + "\"}, ";

		JSONObject ibmLogOnId = new JSONObject();
		ibmLogOnId.put("fieldName", "ibmLogOnId");
		ibmLogOnId.put("defaultValue", serviceRequest.getIbmLogOnId());
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
		securityAuthorization.put("defaultValue", serviceRequest.getLsoGroupCode());
		fieldJsonArray.add(securityAuthorization);
		//output += "{ \"fieldName\": \"lsoGroupCode\", \"defaultValue\": \"" + serviceRequest.getLsoGroupCode() + "\"}, ";

		if (serviceRequest.isTsoAccess()) {
			JSONObject tsoAccess = new JSONObject();
			tsoAccess.put("fieldName", "tsoAccess");
			tsoAccess.put("defaultValue", "Checked");
			fieldJsonArray.add(tsoAccess);
			//output += "{ \"fieldName\": \"tsoAccess\", \"defaultValue\": \"Checked\"}, ";
		}

		JSONObject tsoGroupCode = new JSONObject();
		tsoGroupCode.put("fieldName", "tsoGroupCode");
		tsoGroupCode.put("defaultValue", serviceRequest.getTsoGroupCode());
		fieldJsonArray.add(tsoGroupCode);
		//output += "{ \"fieldName\": \"tsoGroupCode\", \"defaultValue\": \"" + serviceRequest.getTsoGroupCode() + "\"}, ";
		
		JSONObject binNumber = new JSONObject();
		binNumber.put("fieldName", "binNumber");
		binNumber.put("defaultValue", serviceRequest.getBinNumber());
		fieldJsonArray.add(binNumber);
		//output += "{ \"fieldName\": \"binNumber\", \"defaultValue\": \"" + serviceRequest.getBinNumber() + "\"}, ";

		JSONObject subGroup1 = new JSONObject();
		subGroup1.put("fieldName", "subGroup1");
		subGroup1.put("defaultValue", serviceRequest.getSubGroup1());
		fieldJsonArray.add(subGroup1);
		//output += "{ \"fieldName\": \"subGroup1\", \"defaultValue\": \"" + serviceRequest.getSubGroup1() + "\"}, ";

		JSONObject subGroup2 = new JSONObject();
		subGroup2.put("fieldName", "subGroup2");
		subGroup2.put("defaultValue", serviceRequest.getSubGroup2());
		fieldJsonArray.add(subGroup2);
		//output += "{ \"fieldName\": \"subGroup2\", \"defaultValue\": \"" + serviceRequest.getSubGroup2() + "\"}, ";

		JSONObject subGroup3 = new JSONObject();
		subGroup3.put("fieldName", "subGroup3");
		subGroup3.put("defaultValue", serviceRequest.getSubGroup3());
		fieldJsonArray.add(subGroup3);
		//output += "{ \"fieldName\": \"subGroup3\", \"defaultValue\": \"" + serviceRequest.getSubGroup3() + "\"}, ";

		if (serviceRequest.isOnlineAccess()) {
			JSONObject onlineAccess = new JSONObject();
			onlineAccess.put("fieldName", "onlineAccess");
			onlineAccess.put("defaultValue", "Checked");
			fieldJsonArray.add(onlineAccess);
			//output += "{ \"fieldName\": \"onlineAccess\", \"defaultValue\": \"Checked\"}, ";
		}

		JSONObject systemApplication1 = new JSONObject();
		systemApplication1.put("fieldName", "systemApplication1");
		systemApplication1.put("defaultValue", serviceRequest.getSystemApplication());
		fieldJsonArray.add(systemApplication1);
		//output += "{ \"fieldName\": \"systemApplication1\", \"defaultValue\": \"" + serviceRequest.getSystemApplication() + "\"}, ";

		JSONObject groupName1 = new JSONObject();
		groupName1.put("fieldName", "groupName1");
		groupName1.put("defaultValue", serviceRequest.getGroupName());
		fieldJsonArray.add(groupName1);
		//output += "{ \"fieldName\": \"groupName1\", \"defaultValue\": \"" + serviceRequest.getGroupName() + "\"}, ";

		JSONObject oldGroup1 = new JSONObject();
		oldGroup1.put("fieldName", "oldGroup1");
		oldGroup1.put("defaultValue", serviceRequest.getOldGroup());
		fieldJsonArray.add(oldGroup1);
		//output += "{ \"fieldName\": \"oldGroup1\", \"defaultValue\": \"" + serviceRequest.getOldGroup() + "\"}, ";
		
		JSONObject APS_AO = new JSONObject();
		APS_AO.put("fieldName", "APS_AO");
		APS_AO.put("defaultValue", serviceRequest.getAPS_AO());
		fieldJsonArray.add(APS_AO);
		//output += "{ \"fieldName\": \"oldGroup1\", \"defaultValue\": \"" + serviceRequest.getOldGroup() + "\"}, ";

		JSONObject DMV_SYSTEM_CODE = new JSONObject();
		DMV_SYSTEM_CODE.put("fieldName", "DMV_SYSTEM_CODE");
		DMV_SYSTEM_CODE.put("defaultValue", serviceRequest.getDMV_SYSTEM_CODE());
		fieldJsonArray.add(DMV_SYSTEM_CODE);
		//output += "{ \"fieldName\": \"oldGroup1\", \"defaultValue\": \"" + serviceRequest.getOldGroup() + "\"}, ";

		JSONObject JAI_SYSTEM_LOCATION = new JSONObject();
		JAI_SYSTEM_LOCATION.put("fieldName", "JAI_SYSTEM_LOCATION");
		JAI_SYSTEM_LOCATION.put("defaultValue", serviceRequest.getJAI_SYSTEM_LOCATION());
		fieldJsonArray.add(JAI_SYSTEM_LOCATION);
		//output += "{ \"fieldName\": \"oldGroup1\", \"defaultValue\": \"" + serviceRequest.getOldGroup() + "\"}, ";


		if (serviceRequest.isUnixAddLogonId()) {
			JSONObject unixAddLogonId = new JSONObject();
			unixAddLogonId.put("fieldName", "unixAddLogonId");
			unixAddLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(unixAddLogonId);
			//output += "{ \"fieldName\": \"unixAddLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isUnixChangeLogonId()) {
			JSONObject unixChangeLogonId = new JSONObject();
			unixChangeLogonId.put("fieldName", "unixChangeLogonId");
			unixChangeLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(unixChangeLogonId);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isUnixDeleteLogonId()) {
			JSONObject unixDeleteLogonId = new JSONObject();
			unixDeleteLogonId.put("fieldName", "unixDeleteLogonId");
			unixDeleteLogonId.put("defaultValue", "Checked");
			fieldJsonArray.add(unixDeleteLogonId);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}

		JSONObject unixLogOnId = new JSONObject();
		unixLogOnId.put("fieldName", "unixLogOnId");
		unixLogOnId.put("defaultValue", serviceRequest.getUnixLogOnId());
		fieldJsonArray.add(unixLogOnId);
		//output += "{ \"fieldName\": \"unixLogOnId\", \"defaultValue\": \"" + serviceRequest.getUnixLogOnId() + "\"}, ";

		JSONObject unixApplication = new JSONObject();
		unixApplication.put("fieldName", "unixApplication");
		unixApplication.put("defaultValue", serviceRequest.getUnixApplication());
		fieldJsonArray.add(unixApplication);
		//output += "{ \"fieldName\": \"unixApplication\", \"defaultValue\": \"" + serviceRequest.getUnixApplication() + "\"}, ";

		JSONObject unixAccessGroup = new JSONObject();
		unixAccessGroup.put("fieldName", "unixAccessGroup");
		unixAccessGroup.put("defaultValue", serviceRequest.getUnixAccessGroup());
		fieldJsonArray.add(unixAccessGroup);
		//output += "{ \"fieldName\": \"unixAccessGroup\", \"defaultValue\": \"" + serviceRequest.getUnixAccessGroup() + "\"}, ";
		
		if (serviceRequest.isNewToken()) {
			JSONObject NewToken = new JSONObject();
			NewToken.put("fieldName", "NewToken");
			NewToken.put("defaultValue", "Checked");
			fieldJsonArray.add(NewToken);
			//output += "{ \"fieldName\": \"unixAddLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isReplaceLostStolenToken()) {
			JSONObject ReplaceLostStolenToken = new JSONObject();
			ReplaceLostStolenToken.put("fieldName", "ReplaceLostStolenToken");
			ReplaceLostStolenToken.put("defaultValue", "Checked");
			fieldJsonArray.add(ReplaceLostStolenToken);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isReplaceDefectiveToken()) {
			JSONObject ReplaceDefectiveToken = new JSONObject();
			ReplaceDefectiveToken.put("fieldName", "ReplaceDefectiveToken");
			ReplaceDefectiveToken.put("defaultValue", "Checked");
			fieldJsonArray.add(ReplaceDefectiveToken);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isRenewToken()) {
			JSONObject RenewToken = new JSONObject();
			RenewToken.put("fieldName", "RenewToken");
			RenewToken.put("defaultValue", "Checked");
			fieldJsonArray.add(RenewToken);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject ExperationDate = new JSONObject();
		ExperationDate.put("fieldName", "billingAccountNumber");
<<<<<<< HEAD
		ExperationDate.put("defaultValue", serviceRequest.getExperationDate());
=======
		ExperationDate.put("defaultValue", serviceRequest.getExpirationDate());
>>>>>>> parent of 73895d7 (agreement and admincontroller fix with new names)
		fieldJsonArray.add(ExperationDate);
		//output += "{ \"fieldName\": \"billingAccountNumber\", \"defaultValue\": \"" + serviceRequest.getBillingAccountNumber() + "\"}, ";
		
		if (serviceRequest.isTokenlessAuth()) {
			JSONObject TokenlessAuth = new JSONObject();
			TokenlessAuth.put("fieldName", "TokenlessAuth");
			TokenlessAuth.put("defaultValue", "Checked");
			fieldJsonArray.add(TokenlessAuth);
			//output += "{ \"fieldName\": \"unixAddLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isHardTokenVPN()) {
			JSONObject HardTokenVPN = new JSONObject();
			HardTokenVPN.put("fieldName", "HardTokenVPN");
			HardTokenVPN.put("defaultValue", "Checked");
			fieldJsonArray.add(HardTokenVPN);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isSoftwareToken()) {
			JSONObject SoftwareToken = new JSONObject();
			SoftwareToken.put("fieldName", "SoftwareToken");
			SoftwareToken.put("defaultValue", "Checked");
			fieldJsonArray.add(SoftwareToken);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject billingAccountNumber = new JSONObject();
		billingAccountNumber.put("fieldName", "billingAccountNumber");
		billingAccountNumber.put("defaultValue", serviceRequest.getBillingAccountNumber());
		fieldJsonArray.add(billingAccountNumber);
		//output += "{ \"fieldName\": \"billingAccountNumber\", \"defaultValue\": \"" + serviceRequest.getBillingAccountNumber() + "\"}, ";

		if (serviceRequest.isiOS()) {
			JSONObject IOS = new JSONObject();
			IOS.put("fieldName", "TokenlessAuth");
			IOS.put("defaultValue", "Checked");
			fieldJsonArray.add(IOS);
			//output += "{ \"fieldName\": \"unixAddLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isAndroid()) {
			JSONObject Android = new JSONObject();
			Android.put("fieldName", "HardTokenVPN");
			Android.put("defaultValue", "Checked");
			fieldJsonArray.add(Android);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isComputer()) {
			JSONObject Computer = new JSONObject();
			Computer.put("fieldName", "SoftwareToken");
			Computer.put("defaultValue", "Checked");
			fieldJsonArray.add(Computer);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		if (serviceRequest.isEmployeeWorkforce()) {
			JSONObject EmployeeWorkforce = new JSONObject();
			EmployeeWorkforce.put("fieldName", "EmployeeWorkforce");
			EmployeeWorkforce.put("defaultValue", "Checked");
			fieldJsonArray.add(EmployeeWorkforce);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isContractorWorkforce1()) {
			JSONObject ContractorWorkforce1 = new JSONObject();
			ContractorWorkforce1.put("fieldName", "ContractorWorkforce1");
			ContractorWorkforce1.put("defaultValue", "Checked");
			fieldJsonArray.add(ContractorWorkforce1);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject ContractorCompanyName = new JSONObject();
		ContractorCompanyName.put("fieldName", "ContractorCompanyName");
		ContractorCompanyName.put("defaultValue", serviceRequest.getContractorCompanyName());
		fieldJsonArray.add(ContractorCompanyName);
		
		JSONObject ContractorWorkOrder = new JSONObject();
		ContractorWorkOrder.put("fieldName", "ContractorWorkOrder");
		ContractorWorkOrder.put("defaultValue", serviceRequest.getContractorWorkOrder());
		fieldJsonArray.add(ContractorWorkOrder);
		
		JSONObject ContractorExperationDate = new JSONObject();
		ContractorExperationDate.put("fieldName", "ContractorExperationDate");
		ContractorExperationDate.put("defaultValue", serviceRequest.getContractorExperationDate());
		fieldJsonArray.add(ContractorExperationDate);
		
		if (serviceRequest.isO365Email()) {
			JSONObject O365Email = new JSONObject();
			O365Email.put("fieldName", "O365Email");
			O365Email.put("defaultValue", "Checked");
			fieldJsonArray.add(O365Email);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		if (serviceRequest.isLacMobile()) {
			JSONObject LACMobile = new JSONObject();
			LACMobile.put("fieldName", "LACMobile");
			LACMobile.put("defaultValue", "Checked");
			fieldJsonArray.add(LACMobile);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isLaCounty()) {
			JSONObject LACounty = new JSONObject();
			LACounty.put("fieldName", "LACounty");
			LACounty.put("defaultValue", "Checked");
			fieldJsonArray.add(LACounty);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isCountywidePolicyB()) {
			JSONObject CountywidePolicyB = new JSONObject();
			CountywidePolicyB.put("fieldName", "CountywidePolicyB");
			CountywidePolicyB.put("defaultValue", "Checked");
			fieldJsonArray.add(CountywidePolicyB);
			//output += "{ \"fieldName\": \"unixChangeLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		if (serviceRequest.isCountywidePolicyA()) {
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
		if (serviceRequest.isStreamingMedia()) {
			JSONObject StreamingMedia = new JSONObject();
			StreamingMedia.put("fieldName", "CountywidePolicyA");
			StreamingMedia.put("defaultValue", "Checked");
			fieldJsonArray.add(StreamingMedia);
			//output += "{ \"fieldName\": \"unixDeleteLogonId\", \"defaultValue\": \"Checked\"}, ";
		}
		
		JSONObject businessJustification = new JSONObject();
		businessJustification.put("fieldName", "businessJustification");
		businessJustification.put("defaultValue", serviceRequest.getBusinessJustification());
		fieldJsonArray.add(businessJustification);
		//output += "{ \"fieldName\": \"businessJustification\", \"defaultValue\": \"" + serviceRequest.getBusinessJustification() + "\"}, ";
		

//		if (serviceRequest.isEmployee()) {
//			JSONObject departmentNumber3 = new JSONObject();
//			departmentNumber3.put("fieldName", "departmentNumber3");
//			departmentNumber3.put("defaultValue", serviceRequest.getDepartmentNumber());
//			fieldJsonArray.add(departmentNumber3);
//			//output += "{ \"fieldName\": \"departmentNumber3\", \"defaultValue\": \"" + serviceRequest.getDepartmentNumber() + "\"}, ";
//
//			JSONObject businessPhoneNumber1 = new JSONObject();
//			businessPhoneNumber1.put("fieldName", "businessPhoneNumber1");
//			businessPhoneNumber1.put("defaultValue", serviceRequest.getBusinessPhoneNumber());
//			fieldJsonArray.add(businessPhoneNumber1);
//			//output += "{ \"fieldName\": \"businessPhoneNumber1\", \"defaultValue\": \"" + serviceRequest.getBusinessPhoneNumber() + "\"}, ";
//
//			JSONObject businessPhoneNumber2 = new JSONObject();
//			businessPhoneNumber2.put("fieldName", "businessPhoneNumber2");
//			businessPhoneNumber2.put("defaultValue", serviceRequest.getBusinessPhoneNumber());
//			fieldJsonArray.add(businessPhoneNumber2);
//			//output += "{ \"fieldName\": \"businessPhoneNumber2\", \"defaultValue\": \"" + serviceRequest.getBusinessPhoneNumber() + "\"}, ";
//
//			JSONObject businessPhoneNumber3 = new JSONObject();
//			businessPhoneNumber3.put("fieldName", "businessPhoneNumber3");
//			businessPhoneNumber3.put("defaultValue", serviceRequest.getBusinessPhoneNumber());
//			fieldJsonArray.add(businessPhoneNumber3);
//			//output += "{ \"fieldName\": \"businessPhoneNumber3\", \"defaultValue\": \"" + serviceRequest.getBusinessPhoneNumber() + "\"}, ";
//
//			JSONObject businessPhoneNumber4 = new JSONObject();
//			businessPhoneNumber4.put("fieldName", "businessPhoneNumber4");
//			businessPhoneNumber4.put("defaultValue", serviceRequest.getBusinessPhoneNumber());
//			fieldJsonArray.add(businessPhoneNumber4);
//			//output += "{ \"fieldName\": \"businessPhoneNumber4\", \"defaultValue\": \"" + serviceRequest.getBusinessPhoneNumber() + "\"}, ";
//
//			JSONObject businessPhoneNumber5 = new JSONObject();
//			businessPhoneNumber5.put("fieldName", "businessPhoneNumber5");
//			businessPhoneNumber5.put("defaultValue", serviceRequest.getBusinessPhoneNumber());
//			fieldJsonArray.add(businessPhoneNumber5);
//			//output += "{ \"fieldName\": \"businessPhoneNumber5\", \"defaultValue\": \"" + serviceRequest.getBusinessPhoneNumber() + "\"}, ";
//
//			String hostedId = "E" + serviceRequest.getEmployeeNumber();
//
//			JSONObject hostedId1 = new JSONObject();
//			hostedId1.put("fieldName", "hostedId1");
//			hostedId1.put("defaultValue", hostedId);
//			fieldJsonArray.add(hostedId1);
//			//output += "{ \"fieldName\": \"hostedId1\", \"defaultValue\": \"" + hostedId + "\"}, ";
//
//			JSONObject hostedId2 = new JSONObject();
//			hostedId2.put("fieldName", "hostedId2");
//			hostedId2.put("defaultValue", hostedId);
//			fieldJsonArray.add(hostedId2);
//			//output += "{ \"fieldName\": \"hostedId2\", \"defaultValue\": \"" + hostedId + "\"}, ";
//
//			if (serviceRequest.isLaCountyGovAccess()) {
//				JSONObject laCountyGovAccess = new JSONObject();
//				laCountyGovAccess.put("fieldName", "laCountyGovAccess");
//				laCountyGovAccess.put("defaultValue", "Checked");
//				fieldJsonArray.add(laCountyGovAccess);
//				//output += "{ \"fieldName\": \"laCountyGovAccess\", \"defaultValue\": \"Checked\"}, ";
//			}
//
//			JSONObject employeeNumber1 = new JSONObject();
//			employeeNumber1.put("fieldName", "employeeNumber1");
//			employeeNumber1.put("defaultValue", serviceRequest.getEmployeeNumber());
//			fieldJsonArray.add(employeeNumber1);
//			//output += "{ \"fieldName\": \"employeeNumber1\", \"defaultValue\": \"" + serviceRequest.getEmployeeNumber() + "\"}, ";
//
//			JSONObject employeeNumber2 = new JSONObject();
//			employeeNumber2.put("fieldName", "employeeNumber2");
//			employeeNumber2.put("defaultValue", serviceRequest.getEmployeeNumber());
//			fieldJsonArray.add(employeeNumber2);
//			//output += "{ \"fieldName\": \"employeeNumber2\", \"defaultValue\": \"" + serviceRequest.getEmployeeNumber() + "\"}, ";
//
//			JSONObject employeeNumber3 = new JSONObject();
//			employeeNumber3.put("fieldName", "employeeNumber3");
//			employeeNumber3.put("defaultValue", serviceRequest.getEmployeeNumber());
//			fieldJsonArray.add(employeeNumber3);
//			//output += "{ \"fieldName\": \"employeeNumber3\", \"defaultValue\": \"" + serviceRequest.getEmployeeNumber() + "\"}, ";
//
//			JSONObject employeeNumber4 = new JSONObject();
//			employeeNumber4.put("fieldName", "employeeNumber4");
//			employeeNumber4.put("defaultValue", serviceRequest.getEmployeeNumber());
//			fieldJsonArray.add(employeeNumber4);
//			//output += "{ \"fieldName\": \"employeeNumber4\", \"defaultValue\": \"" + serviceRequest.getEmployeeNumber() + "\"}, ";
//
//			JSONObject employeeEmailAddress1 = new JSONObject();
//			employeeEmailAddress1.put("fieldName", "employeeEmailAddress1");
//			employeeEmailAddress1.put("defaultValue", serviceRequest.getEmployeeEmailAddress());
//			fieldJsonArray.add(employeeEmailAddress1);
//			//output += "{ \"fieldName\": \"employeeEmailAddress1\", \"defaultValue\": \"" + serviceRequest.getEmployeeEmailAddress() + "\"}, ";
//
//			JSONObject employeeEmailAddress2 = new JSONObject();
//			employeeEmailAddress2.put("fieldName", "employeeEmailAddress2");
//			employeeEmailAddress2.put("defaultValue", serviceRequest.getEmployeeEmailAddress());
//			fieldJsonArray.add(employeeEmailAddress2);
//			//output += "{ \"fieldName\": \"employeeEmailAddress2\", \"defaultValue\": \"" + serviceRequest.getEmployeeEmailAddress() + "\"}, ";
//
//			JSONObject employeeEmailAddress3 = new JSONObject();
//			employeeEmailAddress3.put("fieldName", "employeeEmailAddress3");
//			employeeEmailAddress3.put("defaultValue", serviceRequest.getEmployeeEmailAddress());
//			fieldJsonArray.add(employeeEmailAddress3);
//			//output += "{ \"fieldName\": \"employeeEmailAddress3\", \"defaultValue\": \"" + serviceRequest.getEmployeeEmailAddress() + "\"}, ";
//
//			JSONObject securityAuthorization = new JSONObject();
//			securityAuthorization.put("fieldName", "securityAuthorization");
//			securityAuthorization.put("defaultValue", serviceRequest.getSecurityAuthorization());
//			fieldJsonArray.add(securityAuthorization);
//			//output += "{ \"fieldName\": \"securityAuthorization\", \"defaultValue\": \"" + serviceRequest.getSecurityAuthorization() + "\"}, ";
//		}
//		else {
//
//			JSONObject companyName1 = new JSONObject();
//			companyName1.put("fieldName", "companyName1");
//			companyName1.put("defaultValue", serviceRequest.getCompanyName());
//			fieldJsonArray.add(companyName1);
//			//output += "{ \"fieldName\": \"companyName1\", \"defaultValue\": \"" + serviceRequest.getCompanyName() + "\"}, ";
//
//			JSONObject companyName2 = new JSONObject();
//			companyName2.put("fieldName", "companyName2");
//			companyName2.put("defaultValue", serviceRequest.getCompanyName());
//			fieldJsonArray.add(companyName2);
//			//output += "{ \"fieldName\": \"companyName2\", \"defaultValue\": \"" + serviceRequest.getCompanyName() + "\"}, ";
//
//			JSONObject companyEmailAddress1 = new JSONObject();
//			companyEmailAddress1.put("fieldName", "companyEmailAddress1");
//			companyEmailAddress1.put("defaultValue", serviceRequest.getCompanyEmailAddress());
//			fieldJsonArray.add(companyEmailAddress1);
//			//output += "{ \"fieldName\": \"companyEmailAddress1\", \"defaultValue\": \"" + serviceRequest.getCompanyEmailAddress() + "\"}, ";
//
//			JSONObject companyEmailAddress2 = new JSONObject();
//			companyEmailAddress2.put("fieldName", "companyEmailAddress2");
//			companyEmailAddress2.put("defaultValue", serviceRequest.getCompanyEmailAddress());
//			fieldJsonArray.add(companyEmailAddress2);
//			//output += "{ \"fieldName\": \"companyEmailAddress2\", \"defaultValue\": \"" + serviceRequest.getCompanyEmailAddress() + "\"}, ";
//
//			JSONObject companyEmailAddress3 = new JSONObject();
//			companyEmailAddress3.put("fieldName", "companyEmailAddress3");
//			companyEmailAddress3.put("defaultValue", serviceRequest.getCompanyEmailAddress());
//			fieldJsonArray.add(companyEmailAddress3);
//			//output += "{ \"fieldName\": \"companyEmailAddress3\", \"defaultValue\": \"" + serviceRequest.getCompanyEmailAddress() + "\"}, ";
//
//			JSONObject companyStreetAddress1 = new JSONObject();
//			companyStreetAddress1.put("fieldName", "companyStreetAddress1");
//			companyStreetAddress1.put("defaultValue", serviceRequest.getCompanyStreetAddress());
//			fieldJsonArray.add(companyStreetAddress1);
//			//output += "{ \"fieldName\": \"companyStreetAddress1\", \"defaultValue\": \"" + serviceRequest.getCompanyStreetAddress() + "\"}, ";
//
//			JSONObject companyStreetAddress2 = new JSONObject();
//			companyStreetAddress2.put("fieldName", "companyStreetAddress2");
//			companyStreetAddress2.put("defaultValue", serviceRequest.getCompanyStreetAddress());
//			fieldJsonArray.add(companyStreetAddress2);
//			//output += "{ \"fieldName\": \"companyStreetAddress2\", \"defaultValue\": \"" + serviceRequest.getCompanyStreetAddress() + "\"}, ";
//
//			JSONObject companyCity1 = new JSONObject();
//			companyCity1.put("fieldName", "companyCity1");
//			companyCity1.put("defaultValue", serviceRequest.getCompanyCity());
//			fieldJsonArray.add(companyCity1);
//			//output += "{ \"fieldName\": \"companyCity1\", \"defaultValue\": \"" + serviceRequest.getCompanyCity() + "\"}, ";
//
//			JSONObject companyCity2 = new JSONObject();
//			companyCity2.put("fieldName", "companyCity2");
//			companyCity2.put("defaultValue", serviceRequest.getCompanyCity());
//			fieldJsonArray.add(companyCity2);
//			//output += "{ \"fieldName\": \"companyCity2\", \"defaultValue\": \"" + serviceRequest.getCompanyCity() + "\"}, ";
//
//			JSONObject companyState1 = new JSONObject();
//			companyState1.put("fieldName", "companyState1");
//			companyState1.put("defaultValue", serviceRequest.getCompanyState());
//			fieldJsonArray.add(companyState1);
//			//output += "{ \"fieldName\": \"companyState1\", \"defaultValue\": \"" + serviceRequest.getCompanyState() + "\"}, ";
//
//			JSONObject companyState2 = new JSONObject();
//			companyState2.put("fieldName", "companyState2");
//			companyState2.put("defaultValue", serviceRequest.getCompanyState());
//			fieldJsonArray.add(companyState2);
//			//output += "{ \"fieldName\": \"companyState2\", \"defaultValue\": \"" + serviceRequest.getCompanyState() + "\"}, ";
//
//			JSONObject companyZip1 = new JSONObject();
//			companyZip1.put("fieldName", "companyZip1");
//			companyZip1.put("defaultValue", serviceRequest.getCompanyZip());
//			fieldJsonArray.add(companyZip1);
//			//output += "{ \"fieldName\": \"companyZip1\", \"defaultValue\": \"" + serviceRequest.getCompanyZip() + "\"}, ";
//
//			JSONObject companyZip2 = new JSONObject();
//			companyZip2.put("fieldName", "companyZip2");
//			companyZip2.put("defaultValue", serviceRequest.getCompanyZip());
//			fieldJsonArray.add(companyZip2);
//			//output += "{ \"fieldName\": \"companyZip2\", \"defaultValue\": \"" + serviceRequest.getCompanyZip() + "\"}, ";
//
//			JSONObject companyPhoneNumber1 = new JSONObject();
//			companyPhoneNumber1.put("fieldName", "companyPhoneNumber1");
//			companyPhoneNumber1.put("defaultValue", serviceRequest.getCompanyPhoneNumber());
//			fieldJsonArray.add(companyPhoneNumber1);
//			//output += "{ \"fieldName\": \"companyPhoneNumber1\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";
//
//			JSONObject companyPhoneNumber2 = new JSONObject();
//			companyPhoneNumber2.put("fieldName", "companyPhoneNumber2");
//			companyPhoneNumber2.put("defaultValue", serviceRequest.getCompanyPhoneNumber());
//			fieldJsonArray.add(companyPhoneNumber2);
//			//output += "{ \"fieldName\": \"companyPhoneNumber2\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";
//
//			JSONObject companyPhoneNumber3 = new JSONObject();
//			companyPhoneNumber3.put("fieldName", "companyPhoneNumber3");
//			companyPhoneNumber3.put("defaultValue", serviceRequest.getCompanyPhoneNumber());
//			fieldJsonArray.add(companyPhoneNumber3);
//			//output += "{ \"fieldName\": \"companyPhoneNumber3\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";
//
//			JSONObject companyPhoneNumber4 = new JSONObject();
//			companyPhoneNumber4.put("fieldName", "companyPhoneNumber4");
//			companyPhoneNumber4.put("defaultValue", serviceRequest.getCompanyPhoneNumber());
//			fieldJsonArray.add(companyPhoneNumber4);
//			//output += "{ \"fieldName\": \"companyPhoneNumber4\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";
//
//			JSONObject companyPhoneNumber5 = new JSONObject();
//			companyPhoneNumber5.put("fieldName", "companyPhoneNumber5");
//			companyPhoneNumber5.put("defaultValue", serviceRequest.getCompanyPhoneNumber());
//			fieldJsonArray.add(companyPhoneNumber5);
//			//output += "{ \"fieldName\": \"companyPhoneNumber5\", \"defaultValue\": \"" + serviceRequest.getCompanyPhoneNumber() + "\"}, ";
//
//			JSONObject contractWorkOrderNumber1 = new JSONObject();
//			contractWorkOrderNumber1.put("fieldName", "contractWorkOrderNumber1");
//			contractWorkOrderNumber1.put("defaultValue", serviceRequest.getContractWorkOrderNumber());
//			fieldJsonArray.add(contractWorkOrderNumber1);
//			//output += "{ \"fieldName\": \"contractWorkOrderNumber1\", \"defaultValue\": \"" + serviceRequest.getContractWorkOrderNumber() + "\"}, ";
//
//			JSONObject contractWorkOrderNumber2 = new JSONObject();
//			contractWorkOrderNumber2.put("fieldName", "contractWorkOrderNumber2");
//			contractWorkOrderNumber2.put("defaultValue", serviceRequest.getContractWorkOrderNumber());
//			fieldJsonArray.add(contractWorkOrderNumber2);
//			//output += "{ \"fieldName\": \"contractWorkOrderNumber2\", \"defaultValue\": \"" + serviceRequest.getContractWorkOrderNumber() + "\"}, ";
//
//			JSONObject contractWorkOrderNumber3 = new JSONObject();
//			contractWorkOrderNumber3.put("fieldName", "contractWorkOrderNumber3");
//			contractWorkOrderNumber3.put("defaultValue", serviceRequest.getContractWorkOrderNumber());
//			fieldJsonArray.add(contractWorkOrderNumber3);
//			//output += "{ \"fieldName\": \"contractWorkOrderNumber3\", \"defaultValue\": \"" + serviceRequest.getContractWorkOrderNumber() + "\"}, ";
//
//			JSONObject contractWorkOrderNumber4 = new JSONObject();
//			contractWorkOrderNumber4.put("fieldName", "contractWorkOrderNumber4");
//			contractWorkOrderNumber4.put("defaultValue", serviceRequest.getContractWorkOrderNumber());
//			fieldJsonArray.add(contractWorkOrderNumber4);
//			//output += "{ \"fieldName\": \"contractWorkOrderNumber4\", \"defaultValue\": \"" + serviceRequest.getContractWorkOrderNumber() + "\"}, ";
//
//			JSONObject contractExpirationDate1 = new JSONObject();
//			contractExpirationDate1.put("fieldName", "contractExpirationDate1");
//			contractExpirationDate1.put("defaultValue", serviceRequest.getContractExpirationDate());
//			fieldJsonArray.add(contractExpirationDate1);
//			//output += "{ \"fieldName\": \"contractExpirationDate1\", \"defaultValue\": \"" + serviceRequest.getContractExpirationDate() + "\"}, ";
//
//			JSONObject contractExpirationDate2 = new JSONObject();
//			contractExpirationDate2.put("fieldName", "contractExpirationDate2");
//			contractExpirationDate2.put("defaultValue", serviceRequest.getContractExpirationDate());
//			fieldJsonArray.add(contractExpirationDate2);
//			//output += "{ \"fieldName\": \"contractExpirationDate2\", \"defaultValue\": \"" + serviceRequest.getContractExpirationDate() + "\"}, ";
//
//			JSONObject countyEmailAddress1 = new JSONObject();
//			countyEmailAddress1.put("fieldName", "countyEmailAddress1");
//			countyEmailAddress1.put("defaultValue", serviceRequest.getCountyEmailAddress());
//			fieldJsonArray.add(countyEmailAddress1);
//			//output += "{ \"fieldName\": \"countyEmailAddress1\", \"defaultValue\": \"" + serviceRequest.getCountyEmailAddress() + "\"}, ";
//
//			JSONObject countyEmailAddress2 = new JSONObject();
//			countyEmailAddress2.put("fieldName", "countyEmailAddress2");
//			countyEmailAddress2.put("defaultValue", serviceRequest.getCountyEmailAddress());
//			fieldJsonArray.add(countyEmailAddress2);
//			//output += "{ \"fieldName\": \"countyEmailAddress2\", \"defaultValue\": \"" + serviceRequest.getCountyEmailAddress() + "\"}, ";
//
//			JSONObject countyPhoneNumber1 = new JSONObject();
//			countyPhoneNumber1.put("fieldName", "countyPhoneNumber1");
//			countyPhoneNumber1.put("defaultValue", serviceRequest.getCountyPhoneNumber());
//			fieldJsonArray.add(countyPhoneNumber1);
//			//output += "{ \"fieldName\": \"countyPhoneNumber1\", \"defaultValue\": \"" + serviceRequest.getCountyPhoneNumber() + "\"}, ";
//
//			JSONObject countyPhoneNumber2 = new JSONObject();
//			countyPhoneNumber2.put("fieldName", "countyPhoneNumber2");
//			countyPhoneNumber2.put("defaultValue", serviceRequest.getCountyPhoneNumber());
//			fieldJsonArray.add(countyPhoneNumber2);
//			//output += "{ \"fieldName\": \"countyPhoneNumber2\", \"defaultValue\": \"" + serviceRequest.getCountyPhoneNumber() + "\"}, ";
//		}
		
		if (serviceRequest.getManagerEmail() != null) {
			JSONObject managerEmail = new JSONObject();
			managerEmail.put("fieldName", "managerEmail");
			managerEmail.put("defaultValue", serviceRequest.getManagerEmail());
			fieldJsonArray.add(managerEmail);
		}
		
		String managerName = serviceRequest.getManagerFirstName() + " " + serviceRequest.getManagerLastName();
		JSONObject managerNameJson = new JSONObject();
		managerNameJson.put("fieldName", "managerName");
		managerNameJson.put("defaultValue", managerName);
		fieldJsonArray.add(managerNameJson);
		
		if (serviceRequest.getManagerPhone() != null) {
			JSONObject managerPhone = new JSONObject();
			managerPhone.put("fieldName", "managerPhone");
			managerPhone.put("defaultValue", serviceRequest.getManagerPhone());
			fieldJsonArray.add(managerPhone);
		}
		
		if (serviceRequest.getManagerTitle() != null) {
			JSONObject managerTitle = new JSONObject();
			managerTitle.put("fieldName", "managerTitle");
			managerTitle.put("defaultValue", serviceRequest.getManagerTitle());
			fieldJsonArray.add(managerTitle);
		}
		
		if (serviceRequest.getDivChiefManagerEmail() != null) {
			JSONObject divChiefManagerEmail = new JSONObject();
			divChiefManagerEmail.put("fieldName", "divChiefManagerEmail");
			divChiefManagerEmail.put("defaultValue", serviceRequest.getDivChiefManagerEmail());
			fieldJsonArray.add(divChiefManagerEmail);
		}
		
		if (serviceRequest.getDivChiefManagerName() != null) {
			JSONObject divChiefManagerName = new JSONObject();
			divChiefManagerName.put("fieldName", "divChiefManagerName");
			divChiefManagerName.put("defaultValue", serviceRequest.getDivChiefManagerName());
			fieldJsonArray.add(divChiefManagerName);
		}
		
		if (serviceRequest.getDivChiefManagerPhone() != null) {
			JSONObject divChiefManagerPhone = new JSONObject();
			divChiefManagerPhone.put("fieldName", "divChiefManagerPhone");
			divChiefManagerPhone.put("defaultValue", serviceRequest.getDivChiefManagerPhone());
			fieldJsonArray.add(divChiefManagerPhone);
		}
		
		if (serviceRequest.getDepartmentHeadEmail() != null) {
			JSONObject departmentHeadEmail = new JSONObject();
			departmentHeadEmail.put("fieldName", "departmentHeadEmail");
			departmentHeadEmail.put("defaultValue", serviceRequest.getDepartmentHeadEmail());
			fieldJsonArray.add(departmentHeadEmail);
		}
		
		if (serviceRequest.getDepartmentHeadName() != null) {
			JSONObject departmentHeadName = new JSONObject();
			departmentHeadName.put("fieldName", "departmentHeadName");
			departmentHeadName.put("defaultValue", serviceRequest.getDepartmentHeadName());
			fieldJsonArray.add(departmentHeadName);
		}
		
		if (serviceRequest.getDepartmentHeadPhone() != null) {
			JSONObject departmentHeadPhone = new JSONObject();
			departmentHeadPhone.put("fieldName", "departmentHeadPhone");
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
			deptInfoSecurityOfficerName.put("fieldName", "deptInfoSecurityOfficerName");
			deptInfoSecurityOfficerName.put("defaultValue", serviceRequest.getDeptInfoSecurityOfficerName());
			fieldJsonArray.add(deptInfoSecurityOfficerName);
		}
		
		if (serviceRequest.getDeptInfoSecurityOfficerPhone() != null) {
			JSONObject deptInfoSecurityOfficerPhone = new JSONObject();
			deptInfoSecurityOfficerPhone.put("fieldName", "deptInfoSecurityOfficerPhone");
			deptInfoSecurityOfficerPhone.put("defaultValue", serviceRequest.getDeptInfoSecurityOfficerPhone());
			fieldJsonArray.add(deptInfoSecurityOfficerPhone);
		}
		
		if (serviceRequest.getApplicationCoordinatorEmail() != null) {
			JSONObject applicationCoordinatorEmail = new JSONObject();
			applicationCoordinatorEmail.put("fieldName", "applicationCoordinatorEmail");
			applicationCoordinatorEmail.put("defaultValue", serviceRequest.getApplicationCoordinatorEmail());
			fieldJsonArray.add(applicationCoordinatorEmail);
		}
		
		if (serviceRequest.getApplicationCoordinatorName() != null) {
			JSONObject applicationCoordinatorName = new JSONObject();
			applicationCoordinatorName.put("fieldName", "applicationCoordinatorName");
			applicationCoordinatorName.put("defaultValue", serviceRequest.getApplicationCoordinatorName());
			fieldJsonArray.add(applicationCoordinatorName);
		}
		
		if (serviceRequest.getApplicationCoordinatorPhone() != null) {
			JSONObject applicationCoordinatorPhone = new JSONObject();
			applicationCoordinatorPhone.put("fieldName", "applicationCoordinatorPhone");
			applicationCoordinatorPhone.put("defaultValue", serviceRequest.getApplicationCoordinatorPhone());
			fieldJsonArray.add(applicationCoordinatorPhone);
		}
		

		return fieldJsonArray;
	}

}