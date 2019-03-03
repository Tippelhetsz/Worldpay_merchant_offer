package com.fazekas.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazekas.model.Offer;

public class MerchantOfferRestControllerTest {

	private final static String BASE_URL = "http://localhost:8080/MerchantOffer/api/offer";

	@Before
	public void deleteAllOffers() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpDelete(BASE_URL);
		HttpClientBuilder.create().build().execute(request);
	}

	@Test
	public void testGetOffer() throws ClientProtocolException, IOException {
		int randomOfferId = new Random().nextInt(500);
		HttpUriRequest request = new HttpGet(BASE_URL + "/" + randomOfferId);

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		assertEquals(HttpStatus.NOT_FOUND.value(), httpResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void testListAllOffers() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(BASE_URL);

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		assertEquals(HttpStatus.NO_CONTENT.value(), httpResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void testCreateOffer() throws ClientProtocolException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		Offer offer = new Offer("Description", 10, "US Dollar", "1");
		restTemplate.postForObject(BASE_URL, offer, HttpResponse.class);

		HttpUriRequest request = new HttpGet(BASE_URL);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void testUpdateOffer() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(BASE_URL);
		RestTemplate restTemplate = new RestTemplate();
		Offer offer = new Offer("Description_to_be_changed", 20, "US Dollar", "5");
		restTemplate.postForObject(BASE_URL, offer, HttpResponse.class);

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		String jsonString = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		List<Offer> response = mapper.readValue(jsonString, new TypeReference<List<Offer>>() {
		});
		Offer offerToUpdate = response.get(0);
		offerToUpdate.setDescription("Changed Description");

		restTemplate.put("http://localhost:8080/MerchantOffer/api/offer/" + response.get(0).getId(), offerToUpdate);

		httpResponse = HttpClientBuilder.create().build().execute(request);
		jsonString = EntityUtils.toString(httpResponse.getEntity());
		mapper = new ObjectMapper();
		response = mapper.readValue(jsonString, new TypeReference<List<Offer>>() {
		});
		Offer updatedOffer = response.get(0);

		assertEquals("Changed Description", updatedOffer.getDescription());
	}

	@Test
	public void testDeleteOffer() throws ClientProtocolException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		Offer offer = new Offer("Description", 20, "US Dollar", "5");
		restTemplate.postForObject(BASE_URL, offer, HttpResponse.class);
		
		HttpUriRequest request = new HttpGet(BASE_URL);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		String jsonString = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		List<Offer> response = mapper.readValue(jsonString, new TypeReference<List<Offer>>() {
		});
		
		assertEquals(1, response.size());
		
		Offer offerToDelete = response.get(0);
		restTemplate = new RestTemplate();
		restTemplate.delete(BASE_URL + "/" + offerToDelete.getId());

		httpResponse = HttpClientBuilder.create().build().execute(request);

		assertEquals(HttpStatus.NO_CONTENT.value(), httpResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void testDeleteAllOffers() throws ClientProtocolException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		Offer offer = new Offer("Description", 20, "US Dollar", "5");
		restTemplate.postForObject(BASE_URL, offer, HttpResponse.class);
		
		HttpUriRequest request = new HttpGet(BASE_URL);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		String jsonString = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		List<Offer> response = mapper.readValue(jsonString, new TypeReference<List<Offer>>() {
		});
		
		assertEquals(1, response.size());
		
		restTemplate = new RestTemplate();
		restTemplate.delete(BASE_URL);

		httpResponse = HttpClientBuilder.create().build().execute(request);

		assertEquals(HttpStatus.NO_CONTENT.value(), httpResponse.getStatusLine().getStatusCode());
	}

}
