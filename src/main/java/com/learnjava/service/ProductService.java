package com.learnjava.service;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductService {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        ProductInfoRunnable productInfoRunable = new ProductInfoRunnable(productId);
        Thread productThread = new Thread(productInfoRunable);
        ReviewInfoRunable reviewInfoRunable = new ReviewInfoRunable(productId);
        Thread reviewThread = new Thread(reviewInfoRunable);
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call
        productThread.start();
        reviewThread.start();
        productThread.join();
        reviewThread.join();
        ProductInfo productInfo = productInfoRunable.getProductInfo();
        Review review = reviewInfoRunable.getReview();
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    public class ReviewInfoRunable implements Runnable {
        private final String productId;
        private Review review;

        public ReviewInfoRunable(String productId) {
            this.productId = productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(this.productId);
        }
    }

    public class ProductInfoRunnable implements Runnable {
        private ProductInfo productInfo;
        private final String productId;

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

    }
}
