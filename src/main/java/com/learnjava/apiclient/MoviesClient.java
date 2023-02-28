package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MoviesClient {
    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        MovieInfo movieInfo = invokeMovieInfoServices(movieInfoId);
        List<Review> reviews = invokeReviewServices(movieInfoId);
        return new Movie(movieInfo,reviews);
    }

    private MovieInfo invokeMovieInfoServices(Long movieInfoId) {

        var moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";
        return webClient
                .get()
                .uri(moviesInfoUrlPath,movieInfoId)
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
