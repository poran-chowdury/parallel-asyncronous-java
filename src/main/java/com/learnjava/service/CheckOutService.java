package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CheckOutService {
    private final  PriceValidatorService priceValidatorService;

    public CheckOutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){
        startTimer();
        List<CartItem> pricevalidationList = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> {
                    boolean cartItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(cartItemInvalid);
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        if (pricevalidationList.size() >0){
            return new CheckoutResponse(CheckoutStatus.FAILURE,pricevalidationList);
        }
        timeTaken();
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}
