package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class MoviesClientTest {
    private WebClient webClient = WebClient.builder().
            baseUrl("http://localhost:8080/movies")
            .build();

    private MoviesClient moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie() {
        /*
         * {
         *   "movieInfoId": 1,
         *   "name": "Batman Begins",
         *   "year": 2005,
         *   "cast": [
         *     "Christian Bale",
         *     "Michael Cane"
         *   ],
         *   "release_date": "2005-06-15"
         * }
         */
        // given
        var movieInfoId = 1L;
        startTimer();
        // when
        var movie = moviesClient.retrieveMovie(movieInfoId);
        System.out.println("movie: "+ movie);
        CommonUtil.timeTaken();
        // then
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

    }

    @RepeatedTest(10)
    void retrieveMovie_CF() {
        /*
         * {
         *   "movieInfoId": 1,
         *   "name": "Batman Begins",
         *   "year": 2005,
         *   "cast": [
         *     "Christian Bale",
         *     "Michael Cane"
         *   ],
         *   "release_date": "2005-06-15"
         * }
         */
        // given
        var movieInfoId = 1L;
        startTimer();
        // when
        var movie = moviesClient.retrieveMovie_CF(movieInfoId).join();
        System.out.println("movie: "+ movie);
        timeTaken();
        // then
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

    }
}