package com.fazekas.model;

import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Offer {
	
	private long id;
	
	private String description;
	
	private double price;
	
	private String currency = "US Dollar";
	
	private String validityPeriod;
	
	@JsonIgnore
	private LocalDateTime timeOfCreation;
	
	private boolean expired = false;
	
	private boolean cancelled = false;
	
	public Offer(){
		
	}

	public Offer(String description, double price, String currency, String validityPeriod) {
		super();
		this.id = new Random().nextInt(1000);
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.validityPeriod = validityPeriod;
		this.timeOfCreation = LocalDateTime.now();
	}
	
	public Offer(Offer offer){
		this(offer.getDescription(), offer.getPrice(), offer.getCurrency(), offer.getValidityPeriod());
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public LocalDateTime getTimeOfCreation() {
		return timeOfCreation;
	}
	
	public boolean isExpired(){
		return expired;
	}
	
	public boolean isCancelled(){
		return cancelled;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public void setTimeOfCreation(LocalDateTime timeOfCreation) {
		this.timeOfCreation = timeOfCreation;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
