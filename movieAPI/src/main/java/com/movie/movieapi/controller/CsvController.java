package com.movie.movieapi.controller;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private CsvService csvService;

    @GetMapping("BestPicNom")
    public String getBestPicNom() throws URISyntaxException, IOException {
        return csvService.getBestPictureNominees();
    }

    @GetMapping("BestPicWinners")
    public String getBestPicWin() throws URISyntaxException, IOException {
        return csvService.getBestPictureWinners();
    }


    @GetMapping("BestPicWon/{movieName}")
    public CsvDto checkBestPicWin(@PathVariable("movieName") String movieName) throws URISyntaxException, IOException {
        return csvService.checkBestPictureByName(movieName);
    }
}
