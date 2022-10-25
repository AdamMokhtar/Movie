package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.service.CsvService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.movie.movieapi.utils.Navigation.CSVFILE;

@Service
public class CsvServiceImpl implements CsvService {


    @Override
    public String initializeCSV() throws URISyntaxException {
        //find the folder
        URL res = getClass().getClassLoader().getResource(CSVFILE);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }


    @Override
    public List<CsvDto> getListedCsv() throws URISyntaxException, IOException {
        String absolutePath = initializeCSV();
        String[] HEADERS = { "Year", "Category","Nominee","Additional Info","Won"};
        List<CsvDto> ListedCsv = new ArrayList<>();

        Reader in = new FileReader(absolutePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records) {
//            CsvDto csvDto = new CsvDto();
//            String csvDto. = record.get("Year");
//            String title = record.get("title");

        }
        return ListedCsv;
    }

    @Override
    public String checkBestPictureByName(String movieName) throws URISyntaxException, IOException {
        String absolutePath = initializeCSV();
        String won = "FALSE";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(absolutePath));
            while ((line = reader.readLine()) != null){

                String[] values = line.split(",");
                if(values[2].equalsIgnoreCase(movieName)) {
                    if (values[1].equalsIgnoreCase("Best Picture")) {
                        if (values[4].equalsIgnoreCase("Yes")) {
                            won = "TRUE";
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            return won.toString();
        }
    }

    @Override
    public String getBestPictureNominees() throws URISyntaxException, IOException {
        String absolutePath = initializeCSV();
        List<String> bestPictureNominees = new ArrayList<>();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(absolutePath));
            while ((line = reader.readLine()) != null){

                String[] values = line.split(",");
                if(values[1].equalsIgnoreCase("Best Picture"))
                {
                    bestPictureNominees.add(values[2]);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            return bestPictureNominees.toString();
        }
    }

    @Override
    public String getBestPictureWinners() throws URISyntaxException, IOException {

        String absolutePath = initializeCSV();
        List<String> bestPictureWinners = new ArrayList<>();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(absolutePath));
            while ((line = reader.readLine()) != null){

                String[] values = line.split(",");
                if(values[1].equalsIgnoreCase("Best Picture"))
                {
                    if(values[4].equalsIgnoreCase("Yes")){
                        bestPictureWinners.add(values[2]);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            return bestPictureWinners.toString();
        }
    }


}
