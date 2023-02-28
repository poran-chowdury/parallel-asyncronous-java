package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MoviesClient {
    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        MovieInfo movieInfo = invokeMovieInfoServices(movieInfoId);
        List<Review> reviews = invokeReviewServices(movieInfoId);
        return new Movie(movieInfo, reviews);
    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {
        var movieInfoCompletableFuture = CompletableFuture.supplyAsync(() -> invokeMovieInfoServices(movieInfoId));
        var reviews = CompletableFuture.supplyAsync(() -> invokeReviewServices(movieInfoId));

        return movieInfoCompletableFuture
                .thenCombine(reviews, Movie::new);
    }
    public List<Movie> retrieveMovieList(List<Long> movieInfoIds){
          return movieInfoIds
                  .stream()
                  .map(this::retrieveMovie)
                  .collect(Collectors.toList());
    }
    public List<Movie> retrieveMovieList_CF(List<Long> movieInfoIds){
        List<CompletableFuture<Movie>> movieFutures = movieInfoIds
                .stream()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());

        return movieFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private MovieInfo invokeMovieInfoServices(Long movieInfoId) {

        var moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";
        return webClient
                .get()
                .uri(moviesInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
    }

    private List<Review> invokeReviewServices(Long movieInfoId) {

        var reviewBasePath = "/v1/reviews";
        String reviewUri = UriComponentsBuilder.fromUriString(reviewBasePath).queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toUriString();
        return webClient
                .get()
                .uri(reviewUri)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }
}
