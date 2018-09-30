package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.UsersUpdate;

public class PutAPITest extends TestBase{

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
		
		url = serviceUrl + "/api/users/345";
	}
	
	@Test
	public void putAPITest() throws JsonGenerationException, JsonMappingException, IOException{
		restClient = new RestClient();
		
		//1. Header preparation
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//2. Prepare JSON payload : jackson API - Core and DataBind APIs - Dependencies in Maven pom.xml
		ObjectMapper mapper = new ObjectMapper();
		UsersUpdate usersUpdate = new UsersUpdate("Kumar", "Manager"); //expected users obejct
		
		//object to json file:
		mapper.writeValue(new File("C:\\Ramesh Data\\Selenium\\Project Upload\\RestAPIHttpClientFW\\src\\main\\java\\com\\qa\\data\\usersUpdate.json"), usersUpdate);
		
		//3. Convert java object to json in String: Serialization --Marshelling
		String usersJsonString = mapper.writeValueAsString(usersUpdate);
		System.out.println(usersJsonString);
		
		closebaleHttpResponse = restClient.put(url, usersJsonString, headerMap); //call the API
		
		//validate response from API:
		//4. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
		//5. Get the Json response payload
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);
		
		//6. Convert json string to java object: Deserialization --Unmarshelling
		UsersUpdate usersUpdateResObj = mapper.readValue(responseString, UsersUpdate.class); //actual users object
		System.out.println(usersUpdateResObj);
		
		Assert.assertTrue(usersUpdate.getName().equals(usersUpdateResObj.getName()));
		
		Assert.assertTrue(usersUpdate.getJob().equals(usersUpdateResObj.getJob()));
		
		System.out.println(usersUpdateResObj.getId());
		System.out.println(usersUpdateResObj.getName());
		System.out.println(usersUpdateResObj.getJob());
		System.out.println(usersUpdateResObj.getUpdatedAt());
		
	}
}
