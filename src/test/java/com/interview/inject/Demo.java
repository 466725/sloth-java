package com.interview.inject;

public class Demo {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PaymentModule());
        // Now, whenever we want an instance we tell the injector to create it for us.
        Cart cart = injector.getInstance(Cart.class);
    }
}