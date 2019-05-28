package com.interview;

public class PaymentModule extends AbstractModule {
    @Override
    protected void configure configure() {
        bind(PaymentService.class).to(CashPaymentService.class);
        bind(Cart.class);
    }
}