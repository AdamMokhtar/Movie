package com.movie.movieapi.service;

import com.movie.movieapi.dto.CsvDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public interface CsvService {

    public List<CsvDto> getListedCsv() throws URISyntaxException, IOException;
    public String initializeCSV() throws URISyntaxException;
    public CsvDto checkBestPictureByName(String movieName);

    public List<CsvDto> getBestPictureNominees();

    public List<CsvDto> getBestPictureWinners();

}
