package com.fazekas.service;

import java.util.List;

import com.fazekas.model.Offer;

public interface MerchantOfferService {
	
	public Offer findById(long id);
	
	public List<Offer> getListOfOffers();
	
	public void saveOffer(Offer offer);
	
	public void updateOffer(Offer offer);
	
	public void deleteOfferById(long id);
	
	public void deleteAllOffers();
	
	public void cancelOfferById(long id);

	public boolean isOfferExist(Offer offer);

}
