package com.interview.inject;

public class Cart {
    private List < Product > productList = new ArrayList < > ();
    private PaymentService paymentService;
    @Inject
    public Cart(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public addProductToCart(Product product) {
        productList.add(product);
    }
    public void buy() {
        productList.stream().forEach(paymentService::pay);
    }
}