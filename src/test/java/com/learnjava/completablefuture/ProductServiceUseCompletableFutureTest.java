package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUseCompletableFutureTest {
    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final InventoryService inventoryService = new InventoryService();
    ProductServiceUseCompletableFuture  productServiceUseCompletableFuture = new ProductServiceUseCompletableFuture(productInfoService,reviewService,inventoryService);
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

    @Test
    void retrieveProductDetailsApproach2() {
        // given
        String productId= "ABCD123";
        // when
        Product product = productServiceUseCompletableFuture.retrieveProductDetailsApproach2(productId).join();

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventory() {
        // given
        String productId= "ABCD123";
        // when
        Product product = productServiceUseCompletableFuture.retrieveProductDetailsWithInventory(productId);

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                        .forEach(productOption -> {
                            assertNotNull(productOption.getInventory());
                        });
        assertNotNull(product.getReview());
    }
}