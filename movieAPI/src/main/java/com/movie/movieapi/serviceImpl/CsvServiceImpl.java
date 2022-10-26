package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.service.CsvService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.movie.movieapi.utils.Navigation.CSVFILE;

@Service
@Slf4j
public class CsvServiceImpl implements CsvService {


    @Override
    public String initializeCSV()  {
        //find the folder
        URL res = getClass().getClassLoader().getResource(CSVFILE);
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    @Override
    public List<CsvDto> getListedCsv()  {
        String absolutePath = initializeCSV();
        String[] HEADERS = { "Year", "Category","Nominee","Additional Info","Won"};
        List<CsvDto> ListedCsv = new ArrayList<>();

        Iterable<CSVRecord> records = null;
        Reader in = null;
        try {
            in = new FileReader(absolutePath);
            records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (CSVRecord record : records) {
            CsvDto csvDto = new CsvDto();
            csvDto.setYear(record.get("Year"));
            csvDto.setCategory(record.get("Category"));
            csvDto.setNominee(record.get("Nominee"));
            csvDto.setAdditionalInfo(record.get("Additional Info"));
            csvDto.setWon(record.get("Won"));

            ListedCsv.add(csvDto);
        }
        return ListedCsv;
    }

    //TODO:: check space for the category name
    @Override
    public CsvDto checkBestPictureByName(String movieName) {
        List<CsvDto> csvList = getListedCsv();
        Optional<CsvDto> csv = csvList.stream().
                filter(mo->(mo.getNominee().equalsIgnoreCase(movieName)
                        && mo.getCategory().equalsIgnoreCase("Best Picture")
                        && mo.getWon().equalsIgnoreCase("YES"))).findFirst();
            return csv.orElseThrow(() -> new MovieNotFound("Movie was not found"));

    }

    @Override
    public List<CsvDto> getBestPictureNominees(){
        List<CsvDto> csvList = getListedCsv();
        return  csvList.stream()
                .filter(mo->mo.getCategory().equalsIgnoreCase("Best Picture"))
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if(result.isEmpty()) throw new MovieNotFound("Movie name entered was not nominated");
                    return result;
                }));
    }

    @Override
    public List<CsvDto> getBestPictureWinners() {
        List<CsvDto> csvList = getListedCsv();
        return  csvList.stream()
                .filter(mo->(mo.getCategory().equalsIgnoreCase("Best Picture")
                && mo.getWon().equalsIgnoreCase("YES")))
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if(result.isEmpty())
                        throw new MovieNotFound("Movie name entered was not a winner");
                                log.debug("print anything");
                    return result;
                }));
    }

}
