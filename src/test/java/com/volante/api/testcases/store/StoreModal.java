package com.volante.api.testcases.store;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.github.javafaker.Faker;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a Store json object
 * 
 * @author Weipeng Zheng
 * 
 */
public class StoreModal {
	protected final static Logger logger = LogManager.getLogger(StoreModal.class.getName());
	protected static Faker faker = new Faker();

	@SerializedName("address")
	String address;
	@SerializedName("cityId")
	int cityId;
	@SerializedName("name")
	String name;
	@SerializedName("phoneNumber")
	String phoneNumber;
	@SerializedName("postalCode")
	String postalCode;
	@SerializedName("vertical")
	String vertical;

	public StoreModal(String address, int cityId, String name, String phoneNumber, String postalCode, String vertical) {
		this.address = address;
		this.cityId = cityId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.postalCode = postalCode;
		this.vertical = vertical;
	}

	public StoreModal() {
		this.address = faker.address().streetAddress();
		this.cityId = 4952206;
		this.name = faker.name().name();
		this.phoneNumber = faker.phoneNumber().cellPhone();
		this.postalCode = faker.address().zipCode();
		this.vertical = "ENTERTAINMENT";
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
