package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class DeleteAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		//apiUrl = prop.getProperty("serviceURL");
		//https://reqres.in/api/users
		//url = serviceUrl + apiUrl;
		url = serviceUrl + "/api/users/345";
	}
	
	@Test(priority=1)
	public void deleteAPITestWithoutHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		closebaleHttpResponse = restClient.delete(url);
		
		//1. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_204, "Status code is not 204");
		
		//2. Get the Json response payload
		HttpEntity httpEntity = closebaleHttpResponse.getEntity();
		Assert.assertNull(httpEntity);
		
		//3. All Headers
		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();	
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		System.out.println("Headers Array-->"+allHeaders);
		
				
	}

}
