package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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

    @RepeatedTest(10)
    void retrieveMovies() {
        // given
        List<Long> movieIds = List.of(1L, 2L, 3L, 4L,5L,6L,7L);
        startTimer();
        // when
        var moives = moviesClient.retrieveMovieList(movieIds);
        System.out.println("movie: "+ moives);
        timeTaken();
        // then
        assert moives != null;
        assert moives.size() == 7;
    }

    @RepeatedTest(10)
    void retrieveMovies_CF() {
        // given
        List<Long> movieIds = List.of(1L, 2L, 3L, 4L,5L,6L,7L);
        startTimer();
        // when
        var moives = moviesClient.retrieveMovieList_CF(movieIds);
        System.out.println("movie: "+ moives);
        timeTaken();
        // then
        assert moives != null;
        assert moives.size() == 7;
    }
    @RepeatedTest(10)
    void retrieveMovieList_CF_AllOf() {
        // given
        List<Long> movieIds = List.of(1L, 2L, 3L, 4L,5L,6L,7L);
        startTimer();
        // when
        var moives = moviesClient.retrieveMovieList_CF_AllOf(movieIds);
        System.out.println("movie: "+ moives);
        timeTaken();
        // then
        assert moives != null;
        assert moives.size() == 7;
    }
}