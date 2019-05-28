package com.interview.injection;

import com.google.inject.AbstractModule;

public class PaymentModule extends AbstractModule {
	@Override
	protected void configure() {
        bind(PaymentService.class).to(CashPaymentService.class);
        bind(Cart.class);
	}
}