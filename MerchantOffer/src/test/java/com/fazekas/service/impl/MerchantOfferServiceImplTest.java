package com.fazekas.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fazekas.model.Offer;
import com.fazekas.service.MerchantOfferService;


public class MerchantOfferServiceImplTest {
	

	private MerchantOfferService offerService = new MerchantOfferServiceImpl();;
	
	private static long offer1Id;
	private static long offer2Id;
	private static long offer3Id;
	private static long offer4Id;
	private static long offer5Id;
	
	@Before
	public void uploadOfferList(){
		Offer offer1  = new Offer("DescriptionAAA", 10, "UK Pound", "2");
		Offer offer2  = new Offer("DescriptionBBB", 20, "US Dollar" , "3");
		Offer offer3  = new Offer("DescriptionCCC", 30, "UK Pound", "4");
		Offer offer4  = new Offer("DescriptionDDD", 50, "US Dollar", "0");
		Offer offer5  = new Offer("DescriptionEEE", 80, "UK Pound", "5");
		
		offer1Id = offer1.getId();
		offer2Id = offer2.getId();
		offer3Id = offer3.getId();
		offer4Id = offer4.getId();
		offer5Id = offer5.getId();
		
		offerService.getListOfOffers().add(offer1);
		offerService.getListOfOffers().add(offer2);
		offerService.getListOfOffers().add(offer3);
		offerService.getListOfOffers().add(offer4);
		offerService.getListOfOffers().add(offer5);
	}

	@Test
	public void testFindById() {
		Offer testOffer = offerService.findById(offer1Id);
		
		assertEquals("DescriptionAAA", testOffer.getDescription());
		assertEquals("UK Pound", testOffer.getCurrency());
		assertEquals("2", "2");
	}

	@Test
	public void testGetListOfOffers() {
		List<Offer> listOfOffers = offerService.getListOfOffers();
		
		assertNotNull(listOfOffers);
		assertFalse(listOfOffers.isEmpty());
		assertEquals(5, listOfOffers.size());
	}

	@Test
	public void testSaveOffer() {
		Offer newOffer = new Offer("DescriptionFFF", 130, "US Dollar", "0");
		offerService.saveOffer(newOffer);
		
		assertEquals(6, offerService.getListOfOffers().size());
		assertTrue(offerService.isOfferExist(newOffer));
	}

	@Test
	public void testUpdateOffer() {
		Offer offerToUpdate = offerService.getListOfOffers().get(0);
		offerToUpdate.setDescription("DescriptionZZZ");
		
		offerService.updateOffer(offerToUpdate);
		
		assertEquals("DescriptionZZZ", offerToUpdate.getDescription());
		assertTrue(offerService.isOfferExist(offerToUpdate));
	}

	@Test
	public void testDeleteOfferById() {
		Offer offerToDelete = offerService.findById(offer2Id);
		assertTrue(offerService.isOfferExist(offerToDelete));
		
		offerService.deleteOfferById(offer2Id);
		assertFalse(offerService.isOfferExist(offerToDelete));
	}

	@Test
	public void testDeleteAllOffers() {
		assertFalse(offerService.getListOfOffers().isEmpty());
		
		offerService.deleteAllOffers();
		
		assertTrue(offerService.getListOfOffers().isEmpty());
	}

	@Test
	public void testCancelOfferById() {
		Offer offerToCancel = offerService.findById(offer3Id);
		assertFalse(offerToCancel.isCancelled());
		
		offerService.cancelOfferById(offer3Id);
		assertTrue(offerToCancel.isCancelled());
	}

	@Test
	public void testIsOfferExist() {
		Offer existingOffer = offerService.findById(offer4Id);
		assertTrue(offerService.isOfferExist(existingOffer));
		
		Offer newOffer = new Offer();
		assertFalse(offerService.isOfferExist(newOffer));
	}

}
