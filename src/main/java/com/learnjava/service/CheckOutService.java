package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

public class CheckOutService {
    private final PriceValidatorService priceValidatorService;

    public CheckOutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {
        startTimer();
        List<CartItem> pricevalidationList = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> {
                    boolean cartItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(cartItemInvalid);
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        if (pricevalidationList.size() > 0) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, pricevalidationList);
        }
        timeTaken();
        double finalPrice = calculateFinalPriceReduce(cart);
        log("Checkout final price : " + finalPrice);
        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    private double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList()
                .parallelStream().map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue).sum();
    }
    private double calculateFinalPriceReduce(Cart cart) {
        return cart.getCartItemList()
                .parallelStream().map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, Double::sum);
    }
}
