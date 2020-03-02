package com.tutorial.injection;

public class CashPaymentService implements PaymentService {
	@Override
	public void pay(Product product) {
		System.out.println("Payed with cash");
	}
}