package com.movie.movieapi.controller;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.service.CsvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/csv")
@Slf4j
public class CsvController implements CsvControllerInterface {

    @Autowired
    private CsvService csvService;

    /**
     *This method is used to display all movies that were nominated for best picture from the CSV file
     * All nominated movies for best picture will be returned as a list
     *
     * @return List<CsvDto>
     */
    @GetMapping("BestPicNom")
    public ResponseEntity<List<CsvDto>> getBestPicNom(){
        log.info("Starting getBestPicNom method");
        return ResponseEntity.ok(csvService.getBestPictureNominees());
    }

    /**
     *This method is used to display all movies that won best picture award in the CSV file
     * All movies that won the award will be returned
     *
     * @return List<CsvDto>
     */
    @GetMapping("BestPicWinners")
    public ResponseEntity<List<CsvDto>> getBestPicWin(){
        log.info("Starting getBestPicWin method");
        return ResponseEntity.ok(csvService.getBestPictureWinners());
    }


    /**
     *This method is used to check if a passed movie name has won Best picture award
     *The wining movie will be returned with all the associated values
     *
     * @param movieName
     * @return CsvDto
     * @exception MovieNotFound will be thrown if the best picture award was not won by the passed parameter movie name
     */
    @GetMapping("BestPicWon/{movieName}")
    public ResponseEntity<CsvDto> checkBestPicWin(@PathVariable("movieName") String movieName) {
        log.info("Starting checkBestPicWin method");
        try {
            return ResponseEntity.ok(csvService.checkBestPictureByName(movieName));
        }catch(MovieNotFound me){
             return new ResponseEntity("Best Picture award is not won by "+movieName, HttpStatus.NOT_FOUND);
        }
    }
}
