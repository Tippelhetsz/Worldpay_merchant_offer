package com.fazekas.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fazekas.model.Offer;
import com.fazekas.service.MerchantOfferService;

@RestController
@RequestMapping("/api")
public class MerchantOfferRestController {

	private static Logger LOG = Logger.getGlobal();

	@Autowired
	private MerchantOfferService offerService;

	@RequestMapping(value = "/offer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Offer> getOffer(@PathVariable("id") long id) {
		LOG.info("Fetching Offer with id " + id);
		Offer offer = offerService.findById(id);
		if (offer == null) {
			LOG.info("Offer with id " + id + " not found");
			return new ResponseEntity<Offer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Offer>(offer, HttpStatus.OK);
	}

	@RequestMapping(value = "/offer", method = RequestMethod.GET)
	public ResponseEntity<List<Offer>> listAllOffers() {
		LOG.info("Listing offers");
		List<Offer> offers = offerService.getListOfOffers();

		if (offers.isEmpty()) {
			return new ResponseEntity<List<Offer>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Offer>>(offers, HttpStatus.OK);
	}

	@RequestMapping(value = "/offer", method = RequestMethod.POST)
	public ResponseEntity<Void> createOffer(@RequestBody Offer offer, UriComponentsBuilder ucBuilder) {
		LOG.info("Creating Offer");

		if (offerService.isOfferExist(offer)) {
			LOG.info("An Offer with these parameters already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		offerService.saveOffer(offer);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/offer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Offer> updateOffer(@PathVariable("id") long id, @RequestBody Offer offer) {
		LOG.info("Updating offer " + id);

		Offer currentOffer = offerService.findById(id);

		if (currentOffer == null) {
			LOG.info("Offer with id " + id + " not found");
			return new ResponseEntity<Offer>(HttpStatus.NOT_FOUND);
		}

		currentOffer.setDescription(offer.getDescription());
		currentOffer.setPrice(offer.getPrice());
		currentOffer.setCurrency(offer.getCurrency());
		currentOffer.setValidityPeriod(offer.getValidityPeriod());
		currentOffer.setExpired(offer.isExpired());
		currentOffer.setCancelled(offer.isCancelled());
		
		offerService.updateOffer(currentOffer);

		return new ResponseEntity<Offer>(HttpStatus.OK);
	}

	@RequestMapping(value = "/offer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Offer> deleteOffer(@PathVariable("id") long id) {
		LOG.info("Fetching & Deleting Offer with id " + id);

		Offer offer = offerService.findById(id);
		if (offer == null) {
			LOG.info("Unable to delete. Offer with id " + id + " not found");
			return new ResponseEntity<Offer>(HttpStatus.NOT_FOUND);
		}

		offerService.deleteOfferById(id);
		return new ResponseEntity<Offer>(HttpStatus.OK);
	}

	@RequestMapping(value = "/offer", method = RequestMethod.DELETE)
	public ResponseEntity<Offer> deleteAllOffers() {
		LOG.info("Deleting All Offers");

		offerService.deleteAllOffers();
		return new ResponseEntity<Offer>(HttpStatus.OK);
	}

}
