package com.interview.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Demo {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new PaymentModule());
		Cart cart = injector.getInstance(Cart.class);
		System.out.println(cart.toString());
	}
}