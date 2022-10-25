package com.movie.movieapi.service;

import com.movie.movieapi.dto.CsvDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Component
public interface CsvService {

    public List<CsvDto> getListedCsv() throws URISyntaxException, IOException;
    public String initializeCSV() throws URISyntaxException;
    public CsvDto checkBestPictureByName(String movieName);

    public String getBestPictureNominees() throws URISyntaxException, IOException;

    public String getBestPictureWinners() throws URISyntaxException, IOException;

}
