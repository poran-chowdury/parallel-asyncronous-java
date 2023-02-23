package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckOutServiceTest {
    private CheckOutService checkOutService;

    @BeforeEach
    void setUp() {
        PriceValidatorService priceValidatorService = new PriceValidatorService();
        this.checkOutService = new CheckOutService(priceValidatorService);
    }

    @Test
    void checkout_6Items() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkoutResponse = this.checkOutService.checkout(cart);
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }
}