package tutorial.injection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> productList = new ArrayList<>();
    private PaymentService paymentService;

    @Inject
    public Cart(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void addProductToCart(Product product) {
        productList.add(product);
    }

    public void buy() {
        productList.stream().forEach(paymentService::pay);
    }
}