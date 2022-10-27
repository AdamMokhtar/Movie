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

    @GetMapping("BestPicNom")
    public ResponseEntity<List<CsvDto>> getBestPicNom(){

        return ResponseEntity.ok(csvService.getBestPictureNominees());
    }

    @GetMapping("BestPicWinners")
    public ResponseEntity<List<CsvDto>> getBestPicWin(){

        return ResponseEntity.ok(csvService.getBestPictureWinners());
    }


    @GetMapping("BestPicWon/{movieName}")
    public ResponseEntity<CsvDto> checkBestPicWin(@PathVariable("movieName") String movieName) {
        try {
            return ResponseEntity.ok(csvService.checkBestPictureByName(movieName));
        }catch(MovieNotFound me){
             return new ResponseEntity("Best Picture award is not won by "+movieName, HttpStatus.NOT_FOUND);
        }
    }
}
