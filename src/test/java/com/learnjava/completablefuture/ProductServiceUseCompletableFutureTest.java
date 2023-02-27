package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUseCompletableFutureTest {
    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();

    ProductServiceUseCompletableFuture  productServiceUseCompletableFuture = new ProductServiceUseCompletableFuture(productInfoService,reviewService);
    @BeforeEach
    void setUp() {
    }

    @Test
    void retrieveProductDetails() {
        // given
        String productId= "ABCD123";

        // when
        Product product = productServiceUseCompletableFuture.retrieveProductDetails(productId);

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}