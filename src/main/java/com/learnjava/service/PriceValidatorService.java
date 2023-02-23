package com.learnjava.service;


import com.learnjava.domain.checkout.CartItem;

import static com.learnjava.util.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        delay(500);
        return cartId == 7 || cartId == 9 || cartId == 11;
    }
}
