package com.fazekas.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fazekas.model.Offer;
import com.fazekas.service.MerchantOfferService;

@Service
public class MerchantOfferServiceImpl implements MerchantOfferService {
	
	private List<Offer> listOfOffers = new ArrayList<>();

	@Override
	public Offer findById(long id) {
		for(Offer offer : listOfOffers){
			if(offer.getId() == id){
				checkValidity(offer);
				return offer;
			}
		}
		
		return null;
	}

	@Override
	public List<Offer> getListOfOffers() {
		for(Offer offer : listOfOffers){
			checkValidity(offer);
		}
		
		return listOfOffers;
	}

	@Override
	public void saveOffer(Offer offer) {
		Offer newOffer = new Offer(offer);
		listOfOffers.add(newOffer);

	}

	@Override
	public void updateOffer(Offer offer) {
		int index = listOfOffers.indexOf(offer);
		listOfOffers.set(index, offer);
	}

	@Override
	public void deleteOfferById(long id) {
		for(Iterator<Offer> iterator = listOfOffers.iterator(); iterator.hasNext(); ){
			Offer offer = iterator.next();
			if(offer.getId() == id){
				iterator.remove();
			}
		}
	}

	@Override
	public void deleteAllOffers() {
		listOfOffers.clear();
	}

	@Override
	public void cancelOfferById(long id) {
		for(Offer offer : listOfOffers){
			if(offer.getId() == id){
				int index = listOfOffers.indexOf(offer);
				offer.setCancelled(true);
				listOfOffers.set(index, offer);
			}
		}
	}

	@Override
	public boolean isOfferExist(Offer otherOffer) {
		for(Offer offer : listOfOffers){
			if(offer.getDescription().equalsIgnoreCase(otherOffer.getDescription()) && 
					offer.getPrice() == otherOffer.getPrice() &&
					offer.getValidityPeriod().equals(otherOffer.getValidityPeriod())){
				return true;
			}
		}
		
		return false;
	}
	
	private void checkValidity(Offer offer){
		Duration duration = Duration.between(offer.getTimeOfCreation(), LocalDateTime.now());
		
		if(!offer.isExpired() && Double.valueOf(offer.getValidityPeriod()) <= duration.toDays()){
			offer.setExpired(true);
		}
	}

}
