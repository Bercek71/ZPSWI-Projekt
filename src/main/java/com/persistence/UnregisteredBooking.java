package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Transient;

public class UnregisteredBooking {
	@JsonbProperty("email")
	public String email;

	@JsonbProperty("booking")
	public Booking booking;
}
