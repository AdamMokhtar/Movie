package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.entity.Movie;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.mapper.MovieMapper;
import com.movie.movieapi.repository.MovieRepository;
import com.movie.movieapi.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {
    private static MovieRepository movieRepository;
    private static MovieMapper movieMapper;
    private static Validator validator;
    private static Movie movie;
    private static MovieDto movieDto;

    private static final String  MOVIE_NAME = "Alpha Movie";
    private static final String  MOVIE_LANGUAGE = "English";
    private static final int  MOVIE_ReleaseYear = 2010;

    private MovieService sut;

    @BeforeEach
    public void setUp() {

        movieRepository = mock(MovieRepository.class);
        movieMapper = mock(MovieMapper.class);
        validator = mock(Validator.class);
        movie = mock(Movie.class);
        movieDto = mock(MovieDto.class);
        sut = new MovieServiceImpl(movieRepository, movieMapper, validator);
    }


    /**
     * @verifies callSaveFromMovieRepo
     * @see MovieServiceImpl#postMovie(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void postMovie_shouldCallSaveFromMovieRepo() throws Exception {
        // arrange
        Set<ConstraintViolation<MovieDto>> violations = new HashSet<>();
        when(validator.validate(movieDto)).thenReturn(violations);
        MovieDto passedDto = mock(MovieDto.class);

        when(passedDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(passedDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(passedDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        when(movieDto.getMovieName()).thenReturn("already added movie");
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDto = new ArrayList<>(){{
            add(movieDto);
        }};

        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(movies)).thenReturn(moviesDto);

        when(movieMapper.toEntity(passedDto)).thenReturn(movie);
        // act
        sut.postMovie(passedDto);
        // verify
        verify(movieRepository).save(movie);
    }

    /**
     * @verifies callFindAllMethodFromMovieRepo
     * @see MovieServiceImpl#getAllMovies()
     */
    @Test
    public void getAllMovies_shouldCallFindAllMethodFromMovieRepo() throws Exception {
        // arrange
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(movieRepository.findAll()).thenReturn(movies);
        // act
        sut.getAllMovies();
        // verify
        verify(movieRepository).findAll();
    }

    /**
     * @verifies throwMovieNotFoundExceptionWhenNoMovieWasFound
     * @see MovieServiceImpl#getAllMovies()
     */
    @Test
    public void getAllMovies_shouldThrowMovieNotFoundExceptionWhenNoMovieWasFound() throws Exception {
        //act
        Exception exception = assertThrows(MovieNotFound.class, () -> {
                    sut.getAllMovies();});
        String expectedMessage = "There were no movies found";
        String actualMessage = exception.getMessage();
        //assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies returnAListOfMovies
     * @see MovieServiceImpl#getAllMovies()
     */
    @Test
    public void getAllMovies_shouldReturnAListOfMovies() throws Exception {
        //arrange
        List<MovieDto> moviesDtos = new ArrayList<>(){{
            add(movieDto);
        }};
        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(movies)).thenReturn(moviesDtos);
        // act
        List<MovieDto> moviesDto = sut.getAllMovies();
        // assert
        assertThat(moviesDto.size()).isGreaterThan(0);
    }

    /**
     * @verifies throwIllegalArgumentExceptionWhenNameIsNullOrEmpty
     * @see MovieServiceImpl#getMoviesByName(String)
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""," "})
    public void getMoviesByName_shouldThrowIllegalArgumentExceptionWhenNameIsNullOrEmpty(String value) throws Exception {
        //act, assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sut.getMoviesByName(value);});
        String expectedMessage = "name cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies callFindByMovieName
     * @see MovieServiceImpl#getMoviesByName(String)
     */
    @Test
    public void getMoviesByName_shouldCallFindByMovieName() throws Exception {
        //arrange
        List<Movie> movieList = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findByMovieName(MOVIE_NAME)).thenReturn(movieList);
        //act
        sut.getMoviesByName(MOVIE_NAME);
        //verify
        verify(movieRepository).findByMovieName(MOVIE_NAME);
    }

    /**
     * @verifies throwMovieNotFoundExceptionWhenNoMovieWasFound
     * @see MovieServiceImpl#getMoviesByName(String)
     */
    @Test
    public void getMoviesByName_shouldThrowMovieNotFoundExceptionWhenNoMovieWasFound() throws Exception {
        //act, assert
        Exception exception = assertThrows(MovieNotFound.class, () -> {
            sut.getMoviesByName(MOVIE_NAME);});
        String expectedMessage = "No movie/s with the name "+MOVIE_NAME;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies returnAListOfMoviesFoundWithSimilarNames
     * @see MovieServiceImpl#getMoviesByName(String)
     */
    @Test
    public void getMoviesByName_shouldReturnAListOfMoviesFound() throws Exception {
        //arrange
        List<Movie> movieList = new ArrayList<>(){{
            add(movie);
        }};
        List<MovieDto> movieDtoList = new ArrayList<>(){{
            add(movieDto);
        }};
        when(movieRepository.findByMovieName(MOVIE_NAME)).thenReturn(movieList);
        when(movieMapper.toDto(movieList)).thenReturn(movieDtoList);
        //assert
        assertThat(sut.getMoviesByName(MOVIE_NAME).size()).isGreaterThan(0);

    }

    /**
     * @verifies throwMovieNotFoundExceptionWhenNoMovieWasFound
     * @see MovieServiceImpl#postMovie(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void postMovie_shouldThrowInstanceAlreadyExistsExceptionWhenThereIsAlreadySimilarMovie() throws Exception {
        // arrange
        Set<ConstraintViolation<MovieDto>> violations = new HashSet<>();
        when(validator.validate(movieDto)).thenReturn(violations);

        when(movieDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDto = new ArrayList<>(){{
            add(movieDto);
        }};

        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(movies)).thenReturn(moviesDto); //we here return the same list, so it will be equal

        //assert
        Exception exception = assertThrows(InstanceAlreadyExistsException.class, () -> {
            sut.postMovie(movieDto);});
        String expectedMessage = "There is a similar movie";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * @verifies throwConstraintViolationExceptionWhenNoMovieWasFound
     * @see MovieServiceImpl#postMovie(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void postMovie_shouldThrowConstraintViolationExceptionWhenThereIsAViolation() throws Exception {
        // arrange
        Set<ConstraintViolation<MovieDto>> violations = new HashSet<>();
        ConstraintViolation<MovieDto> aViolation = mock(ConstraintViolation.class);
        violations.add(aViolation);
        when(validator.validate(movieDto)).thenReturn(violations);

        //assert
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            sut.postMovie(movieDto);});
        String expectedMessage = "Validations were not passed";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies returnThePostedMovieAsMovieDtoObject
     * @see MovieServiceImpl#postMovie(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void postMovie_shouldReturnThePostedMovieAsMovieDtoObject() throws Exception {
        // arrange
        Set<ConstraintViolation<MovieDto>> violations = new HashSet<>();
        when(validator.validate(movieDto)).thenReturn(violations);
        MovieDto passedDto = mock(MovieDto.class);

        when(passedDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(passedDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(passedDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        when(movieDto.getMovieName()).thenReturn("already added movie");
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDto = new ArrayList<>(){{
            add(movieDto);
        }};

        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(movies)).thenReturn(moviesDto);
        when(movieMapper.toEntity(passedDto)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDto(movie)).thenReturn(passedDto);

        // assert, act
        assertThat(sut.postMovie(passedDto)).isEqualTo(passedDto);
    }

    /**
     * @verifies throwIllegalArgumentExceptionWhenMovieDtoIsNull
     * @see MovieServiceImpl#checkSimilarMovieExist(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void checkSimilarMovieExist_shouldThrowIllegalArgumentExceptionWhenMovieDtoIsNull() throws Exception {
        //assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sut.checkSimilarMovieExist(null);});
        String expectedMessage = "MovieDto cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies callGetAllMovies
     * @see MovieServiceImpl#checkSimilarMovieExist(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void checkSimilarMovieExist_shouldCallGetAllMovies() throws Exception {
        //arrange
        when(movieDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDtos = new ArrayList<>(){{
            add(movieDto);
        }};
        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};
        when(movieRepository.findAll()).thenReturn(movies);
        //when(movieMapper.toDto(movies)).thenReturn(moviesDtos);

        MovieService movieServiceSpy = Mockito.spy(sut);
        //act
        movieServiceSpy.checkSimilarMovieExist(movieDto);
        //verify
        verify(movieServiceSpy).getAllMovies();
    }

    /**
     * @verifies returnTrueIfMoreThenZeroFoundWithSimilarNameAndReleaseYearAndLanguage
     * @see MovieServiceImpl#checkSimilarMovieExist(com.movie.movieapi.dto.MovieDto)
     */
    @Test
    public void checkSimilarMovieExist_shouldReturnTrueIfMoreThenZeroFoundWithSimilarNameAndReleaseYearAndLanguage() throws Exception {
        //arrange
        MovieService movieServiceSpy = Mockito.spy(sut);

        when(movieDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDto = new ArrayList<>(){{
            add(movieDto);
        }};

        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieServiceSpy.getAllMovies()).thenReturn(moviesDto);
        //assert
        assertThat(movieServiceSpy.checkSimilarMovieExist(movieDto)).isTrue();
    }

    /**
     * @verifies returnFalseIfLessThenZeroFoundWithSimilarNameAndReleaseYearAndLanguage
     * @see MovieServiceImpl#checkSimilarMovieExist(com.movie.movieapi.dto.MovieDto)
     */
    @ParameterizedTest
    @CsvSource({"new movie Name, "+ MOVIE_LANGUAGE +", "+ MOVIE_ReleaseYear
            ,MOVIE_NAME +", Dutch, "+ MOVIE_ReleaseYear
            ,MOVIE_NAME +", "+ MOVIE_LANGUAGE +", 2011"
            ,"new movie Name,Dutch,2011"})
    public void checkSimilarMovieExist_shouldReturnFalseIfNotFoundWithSimilarNameAndReleaseYearAndLanguage
    (String name,String language,int year ) throws Exception {
        //arrange
        MovieDto passedDto = mock(MovieDto.class);
        when(passedDto.getMovieName()).thenReturn(name);
        when(passedDto.getReleaseYear()).thenReturn(year);
        when(passedDto.getLanguage()).thenReturn(language);

        when(movieDto.getMovieName()).thenReturn(MOVIE_NAME);
        when(movieDto.getReleaseYear()).thenReturn(MOVIE_ReleaseYear);
        when(movieDto.getLanguage()).thenReturn(MOVIE_LANGUAGE);

        List<MovieDto> moviesDto = new ArrayList<>(){{
            add(movieDto);
        }};

        List<Movie> movies = new ArrayList<>(){{
            add(movie);
        }};

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(movies)).thenReturn(moviesDto);

        //assert
        assertThat(sut.checkSimilarMovieExist(passedDto)).isFalse();
    }

    /**
     * @verifies throwIllegalArgumentExceptionWhenIdIsNull
     * @see MovieServiceImpl#deleteMovie(Long)
     */
    @Test
    public void deleteMovie_shouldThrowIllegalArgumentExceptionWhenIdIsNull() throws Exception {
        //act, assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sut.deleteMovie(null);});
        String expectedMessage = "Id cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * @verifies callExistsById
     * @see MovieServiceImpl#deleteMovie(Long)
     */
    @Test
    public void deleteMovie_shouldCallExistsById() throws Exception {
        //arrange
        long id = 1;
        when(movieRepository.existsById(id)).thenReturn(true);
        //act
        sut.deleteMovie(id);
        //verify
        verify(movieRepository).existsById(id);
    }

    /**
     * @verifies callDeleteByIdIfExistsByIdReturnTrue
     * @see MovieServiceImpl#deleteMovie(Long)
     */
    @Test
    public void deleteMovie_shouldCallDeleteByIdIfExistsByIdReturnTrue() throws Exception {
        //arrange
        long id = 1;
        when(movieRepository.existsById(id)).thenReturn(true);
        //act
        sut.deleteMovie(id);
        //verify
        verify(movieRepository).deleteById(id);
    }

    /**
     * @verifies MovieNotFoundExceptionWhenIfExistsByIdReturnFalse
     * @see MovieServiceImpl#deleteMovie(Long)
     */
    @Test
    public void deleteMovie_shouldThrowMovieNotFoundExceptionWhenIfExistsByIdReturnFalse() throws Exception {
        //arrange
        long id = 1;
        when(movieRepository.existsById(id)).thenReturn(false);
        //act, assert
        Exception exception = assertThrows(MovieNotFound.class, () -> {
            sut.deleteMovie(id);});
        String expectedMessage = "Movie with the id "+id+" was not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}