package com.movie.movieapi.controller;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/csv")
public class CsvController implements CsvControllerInterface {

    @Autowired
    private CsvService csvService;

    @GetMapping("BestPicNom")
    public List<CsvDto> getBestPicNom(){
        return csvService.getBestPictureNominees();
    }

    @GetMapping("BestPicWinners")
    public List<CsvDto> getBestPicWin(){
        return csvService.getBestPictureWinners();
    }


    @GetMapping("BestPicWon/{movieName}")
    public CsvDto checkBestPicWin(@PathVariable("movieName") String movieName) {
        return csvService.checkBestPictureByName(movieName);
    }
}
