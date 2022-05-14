package sbrest.signapi;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.box.sdk.BoxCCGAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;

import sbrest.model.ServiceRequest;
import sbrest.model.dao.ServiceRequestDao;

public class AgreementEvents {
	
//	@Autowired
//	private ServiceRequestDao serviceRequestDao;
	public static void main(String[] args) throws Exception {
		System.out.println(getAgreementEvents("CBJCHBCAABAAUrECsZ7yKofBv0V-AFPzXLY8IXeCSfum"));
		System.out.println(getAgreementEvents("3AAABLblqZhAjhxOQn7ebkv371fpImoDy45mL9aTfS9GxmDAl-fUMHPnEKc1JlD_t2eaH3qGM6PrFnrdn6knNaxXxnitEEkzE"));
//		
//		System.out.println(getMostRecentAgreementEvent("CBJCHBCAABAAprK6W-ka1gwrz00FTa7AnZSwgJh9JrNG"));
		
		System.out.println(getMostRecentAgreementEventType("3AAABLblqZhAjhxOQn7ebkv371fpImoDy45mL9aTfS9GxmDAl-fUMHPnEKc1JlD_t2eaH3qGM6PrFnrdn6knNaxXxnitEEkzE"));
		
		//3AAABLblqZhA6wkPag_kYmUxp_tPvXdqtKWiQ5b7IQ0cPACLUE8Pbu5a_fi4W8WxJ2XJGWcJ6m7_SrO4Bn-i_8Syzq9bIdYk7
		System.out.println(getUrl("3AAABLblqZhAjhxOQn7ebkv371fpImoDy45mL9aTfS9GxmDAl-fUMHPnEKc1JlD_t2eaH3qGM6PrFnrdn6knNaxXxnitEEkzE"));
		
		String s = getUrl("CBJCHBCAABAAUrECsZ7yKofBv0V-AFPzXLY8IXeCSfum").toString();
		
		System.out.println(parseUrl(s));
		
		//downloadAgreements(parseUrl(s));
		
//		boxUpload(downloadAgreements(parseUrl(s)));
		
		//ServiceRequest service = serviceRequestDao.getServiceRequest(605522);
//		String l = "Signed by appenrollment@gmail.com";
//		System.out.println(l.contains("Signed"));

		
		//boxUpload(parseUrl(s), 213498);
		
		// iterate through ServiceRequest list and call updateRequestStatus on each ServiceRequest s.
		// add the box upload code and see if you can make the API call
		
		
		//1 on service request click and upload that specific form
		//2 automatic way, don't know how to check for duplicates because the backend can't change adobe sign JSON response
		// Is it possible to make a table of Agreements that are signed and uploaded to Box.com so theres a way to keep track of uploaded agreements 
		// 
	}
	
	public static ServiceRequest updateRequestStatus(ServiceRequest s) throws Exception {
		if (s.getAgreementId() != null) {
			if (!s.getAgreementId().isEmpty() && !s.getRequestStatus().contains("cancelled") && !s.getRequestStatus().startsWith("Signed")) {
				JSONObject eventHistory = getAgreementEvents(s.getAgreementId());
				JSONArray eventsList = ((JSONArray) eventHistory.get("events"));
				
				// Add non-Adobe Event History (index 0 & 1) from the service request to new eventHistory array.
				ArrayList<String> newEventHistory = new ArrayList<String>();
				newEventHistory.add(s.getEventHistory().get(0));
				newEventHistory.add(s.getEventHistory().get(1));
				
				JSONObject lastEvent = new JSONObject();
				
				for (int i = 0; i < eventsList.size(); i++) {
					JSONObject event = (JSONObject)eventsList.get(i);
					String date = (String)event.get("date");
					
					String datePortion = date.substring(0, 10);
					String timePortion = date.substring(11, 19);
					int hour = Integer.parseInt(timePortion.substring(0, 2));
					
					String consistentHour = ((hour + 5) % 24) + "";
					if (consistentHour.length() == 1) {
						consistentHour = "0" + consistentHour;
					}
					
					String newTimePortion = consistentHour + timePortion.substring(2);
					
					String formattedDate = datePortion + " " + newTimePortion;
					
					String description = (String)event.get("description");
					String type = (String)event.get("type");
					
					if (type.equals("CREATED")) {
						description = "The agreement has been created in Adobe Sign";
					}
					
					if (!type.equals("EMAIL_VIEWED")) {
						lastEvent = event;
						newEventHistory.add("(" + formattedDate + ") " + description);
					}
				}
				
				s.setEventHistory(newEventHistory);
				
				String participantEmail = (String)lastEvent.get("participantEmail");
				String lastEventType = (String)lastEvent.get("type");
				
				int matchesFound = 0;
				String participant = "";
				
				if (s.isEmployee()) {
					if (participantEmail.equalsIgnoreCase(s.getEmailAddress())) {
						matchesFound++;
						participant = "Employee";
					}
				}
				else {
					if (participantEmail.equalsIgnoreCase(s.getEmailAddress())) {
						matchesFound++;
						participant = "Contractor";
					}
				}
				
				if (participantEmail.equalsIgnoreCase(s.getManagerEmail())) {
					matchesFound++;
					participant = "Manager";
				}
				
				if (s.getDivChiefManagerEmail() != null) {
					if (participantEmail.equalsIgnoreCase(s.getDivChiefManagerEmail())) {
						matchesFound++;
						participant = "Division Chief";
					}
				}
				
				if (s.getDepartmentHeadEmail() != null) {
					if (participantEmail.equalsIgnoreCase(s.getDepartmentHeadEmail())) {
						matchesFound++;
						participant = "Department Head";
					}
				}
				
				if (s.getDeptInfoSecurityOfficerEmail() != null) {
					if (participantEmail.equalsIgnoreCase(s.getDeptInfoSecurityOfficerEmail())) {
						matchesFound++;
						participant = "Department Info Security Officer";
					}
				}
				
				if (s.getApplicationCoordinatorEmail() != null) {
					if (participantEmail.equalsIgnoreCase(s.getApplicationCoordinatorEmail())) {
						matchesFound++;
						participant = "Application Coordinator";
					}
				}
					
				if (matchesFound > 1) {
					participant = participantEmail;
				}
		
				switch (lastEventType) {
					case "CREATED":
						s.setRequestStatus("Agreement created in Adobe Sign");
						break;
					case "ACTION_REQUESTED":
						s.setRequestStatus("Out for signature (" + participant + ")");
						break;
					case "ACTION_COMPLETED":
						if(!s.getRequestStatus().equals("Uploaded to Box")) {
							s.setRequestStatus("Signed by " + participant);
						}
						
						//boxUpload(downloadAgreements(new URL(getUrl("CBJCHBCAABAAprK6W-ka1gwrz00FTa7AnZSwgJh9JrNG"))));

						break;
					case "RECALLED":
						s.setRequestStatus("Agreement cancelled in Adobe Sign");
						break;
					default:
						s.setRequestStatus("Adobe: " + lastEventType + " (" + participant + ")");
				}
				
			}
		}
		
		return s;
	}
	
	public static JSONObject getAgreementEvents(String agreementId) throws Exception {
		String accessToken = OAuthTokens.getOauthAccessToken();
		String url = "https://api.na3.adobesign.com:443/api/rest/v6/agreements/" + agreementId + "/events";
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		headers.put("Authorization", accessToken);

		JSONObject responseJson = (JSONObject) OAuthTokens.makeApiCall(url, "GET", headers, null);
		return responseJson;
	}
	
	public static String getMostRecentAgreementEvent(String agreementId) throws Exception {
		JSONObject response = getAgreementEvents(agreementId);
		JSONArray eventsList = ((JSONArray) response.get("events"));

		JSONObject mostRecentEvent = (JSONObject)eventsList.get(eventsList.size() - 1);
		String lastEventDescription = (String)mostRecentEvent.get("description");
		String lastEventDate = (String)mostRecentEvent.get("date");
		return "Current Status (" + lastEventDate +  "): " + lastEventDescription;
	}
	
	public static String getMostRecentAgreementEventType(String agreementId) throws Exception {
		JSONObject response = getAgreementEvents(agreementId);
		JSONArray eventsList = ((JSONArray) response.get("events"));

		JSONObject mostRecentEvent = (JSONObject)eventsList.get(eventsList.size() - 1);
		String lastEventType = (String)mostRecentEvent.get("type");
		return lastEventType;
	}
	
		
	public static JSONObject getUrl (String agreementId) throws Exception {
		String accessToken = OAuthTokens.getOauthAccessToken();
		String url = "https://api.na3.adobesign.com:443/api/rest/v6/agreements/" + agreementId + "/combinedDocument/url";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		headers.put("Authorization", accessToken);

		JSONObject responseJson = (JSONObject) OAuthTokens.makeApiCall(url, "GET", headers, null);
		
		return responseJson;
		
	}
	
	public static String parseUrl(String responseJson) {
		String substring = responseJson.substring(8, responseJson.length() - 2);
		String url = "";
		
		
		
		for(int i = 0; i < substring.length(); i++) {
			if(substring.charAt(i) != '\\') {
				url += substring.charAt(i);
			}
		}
		
		return url;
	}
	
	public static FileOutputStream downloadAgreements(String url) throws Exception {
//		BufferedInputStream in = new BufferedInputStream(new URL(getUrl("CBJCHBCAABAAprK6W-ka1gwrz00FTa7AnZSwgJh9JrNG")).openStream());
		
		
		
	    String tmpdir = System.getProperty("java.io.tmpdir");
        System.out.println("Temp file path: " + tmpdir);

//        try {
//
//            // Create an temporary file
//            Path temp = Files.createTempFile("tempfile", ".pdf");
//            System.out.println("Temp file : " + temp);
//            
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		
		
		
		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
				  FileOutputStream fileOutputStream = new FileOutputStream("SignedAgreement.pdf")) {
				    byte dataBuffer[] = new byte[1024];
				    int bytesRead;
				    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				        fileOutputStream.write(dataBuffer, 0, bytesRead);
				    }
				    System.out.println("File downloaded");
				    
//				    InputStream input = new URL(url).openStream();
//				    Files.copy(input, Paths.get(url), StandardCopyOption.REPLACE_EXISTING);
				    
				    return fileOutputStream;
				} catch (IOException e) {
				    // handle exception
					throw new IOException("File not found");
				}
	
		
	}
	
//	public static void boxUpload(FileOutputStream file) throws IOException {
//		    System.out.println( "Hello World!" );	
//	        BoxAPIConnection api = new BoxAPIConnection("gLUyjEyDwTZUvRyrSJUnqeSVTyepXtuA");
//	        
//	       // BoxCollaborator user = new BoxUser(api, "18429209010");
//	        BoxFolder folder = new BoxFolder(api, "155054681560");
//	        //folder.collaborate(user, BoxCollaboration.Role.EDITOR);
//
//	       
//	        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
//	        
//	        for (BoxItem.Info itemInfo : rootFolder) {
//	            System.out.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName());
//	        }
//	        
//	        
//	        //BoxFolder folder = new BoxFolder(api, "155054681560");
//	      
//	        FileInputStream stream = new FileInputStream("./input/SignedAgreement.pdf");
//	        BoxFile.Info newFileInfo = folder.uploadFile(stream, "SignedAgreement.pdf");
//	        stream.close();
//	
//	}
	
//	public static void boxUpload(FileOutputStream file) throws IOException {
//		 System.out.println( "Hello World!" );
//	        
//	        
//		   BoxCCGAPIConnection api = BoxCCGAPIConnection.applicationServiceAccountConnection(
//	        	    "ffc823n3uuf15g5vooj45xg3eri29ukn",
//	        	    "I9r7ZIFUpEUiNpTlDpfE9WVz4Wro4qrt",
//	        	    "873161760"
//	        	);
//	        
//	        api.asUser("18429209010");
//	        
//	        BoxFolder folder = new BoxFolder(api, "155054681560");
//	        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
//	        
//	        // change file output stream to file input stream
//	        //FileUtils.copyFile(inFile,outFile);
//
//	        
//	        
//	        for (BoxItem.Info itemInfo : rootFolder) {
//	            System.out.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName());
//	        }
//	        
////	        FileInputStream stream = new FileInputStream("./input/test.txt");
////	        BoxFile.Info newFileInfo = folder.uploadFile(stream, "test.txt");
//	        
//	        FileInputStream stream = new FileInputStream("SignedAgreement.pdf");
//	        BoxFile.Info newFileInfo = folder.uploadFile(stream, "SignedAgreement.pdf");
//	        
//	        stream.close();
//	
//	}
	
	public static void boxUpload(String url, int requestNumber) throws IOException {
		 System.out.println( "Hello World!" );
	        
	        
		   BoxCCGAPIConnection api = BoxCCGAPIConnection.applicationServiceAccountConnection(
	        	    "ffc823n3uuf15g5vooj45xg3eri29ukn",
	        	    "I9r7ZIFUpEUiNpTlDpfE9WVz4Wro4qrt",
	        	    "873161760"
	        	);
	        
	        api.asUser("18429209010");
	        
	        BoxFolder folder = new BoxFolder(api, "155054681560");
	        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
	        
	        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
	        // change file output stream to file input stream
	        //FileUtils.copyFile(inFile,outFile);

	        
	        
	        for (BoxItem.Info itemInfo : rootFolder) {
	            System.out.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName());
	        }
	        
//	        FileInputStream stream = new FileInputStream("./input/test.txt");
//	        BoxFile.Info newFileInfo = folder.uploadFile(stream, "test.txt");
	        
	        FileInputStream stream = new FileInputStream("SignedAgreement.pdf");
	        BoxFile.Info newFileInfo = folder.uploadFile(in, "SignedAgreement" + requestNumber +".pdf");
	        
	        stream.close();
	
	}
}
