package com.elkron.test;

import com.elkron.cordova.plugin.ElkronApiException;
import com.elkron.cordova.plugin.ElkronApiRequest;
import com.elkron.cordova.plugin.ElkronApiResponse;

public class UserProfileTest {

	public static void main(String[] args) {
		
		try {			
			ElkronApiRequest request = new ElkronApiRequest();
			ElkronApiResponse responseLogin = request.exec(
					"/index.php", 
					"POST", 
					"application/x-www-form-urlencoded", 
					false, 
					"httpd_username=testelkron&httpd_password=elkrontest");
			if (responseLogin.code != 200)  throw new Exception("login failure");
			ElkronApiResponse responseProfile = request.exec(
					"/multiapi/private/getuserprofile/", 
					"GET", 
					"application/json", 
					true);
			if (responseProfile.code != 200)  throw new Exception("getuserprofile failure");
			System.out.println(responseProfile.textResponse);
			System.out.println("Test ok.");
			System.out.println();
		} 
		catch (ElkronApiException e) {
			e.printStackTrace();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		
		

	}

}
