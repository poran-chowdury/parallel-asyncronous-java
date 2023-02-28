package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUseCompletableFutureExceptionTest {
    private ProductInfoService productInfoService = mock(ProductInfoService.class);
    private ReviewService reviewService = mock(ReviewService.class);
    private InventoryService inventoryService = mock(InventoryService.class);
    @InjectMocks
    private ProductServiceUseCompletableFutureException productServiceUseCompletableFutureException;

    @Test
    void retrieveProductDetailsApproach2() {
        // given
        String productId = "ABCD1234";
        when(productInfoService.retrieveProductInfo(productId)).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("Exception occurred!"));
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
        // when
        Product product = productServiceUseCompletableFutureException.retrieveProductDetailsWithInventoryApproachCF2(productId);
        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
    }
    @Test
    void retrieveProductDetailsApproach2_Service_Error() {
        // given
        String productId = "ABCD1234";
        when(productInfoService.retrieveProductInfo(productId)).thenThrow(new RuntimeException("Exception occurred!"));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
        // then
        Assertions.assertThrows(RuntimeException.class,()-> productServiceUseCompletableFutureException.retrieveProductDetailsWithInventoryApproachCF2(productId));
    }

    @Test
    void retrieveProductDetailsWithInventoryApproachCF3WithInventoryException() {
        // given
        String productId = "ABCD1234";
        when(productInfoService.retrieveProductInfo(productId)).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenThrow(new RuntimeException("exception "));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();


        // then
        Product product = productServiceUseCompletableFutureException.retrieveProductDetailsWithInventoryApproachCF3WithInventoryException(productId);
        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
        assertEquals(200, product.getReview().getNoOfReviews());
    }
}