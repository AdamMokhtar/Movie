package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.exception.MovieNotFound;
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
import java.util.Optional;

import static com.movie.movieapi.utils.Navigation.CSVFILE;

@Service
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

//    @Override
//    public String checkBestPictureByName(String movieName) throws URISyntaxException, IOException {
//        String absolutePath = initializeCSV();
//        String won = "FALSE";
//        BufferedReader reader = null;
//        String line = "";
//
//        try {
//            reader = new BufferedReader(new FileReader(absolutePath));
//            while ((line = reader.readLine()) != null){
//
//                String[] values = line.split(",");
//                if(values[2].equalsIgnoreCase(movieName)) {
//                    if (values[1].equalsIgnoreCase("Best Picture")) {
//                        if (values[4].equalsIgnoreCase("Yes")) {
//                            won = "TRUE";
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            reader.close();
//            return won.toString();
//        }
//    }

    //TODO:: check space for the category name
    @Override
    public CsvDto checkBestPictureByName(String movieName) {
        List<CsvDto> CscList = getListedCsv();
//        return Optional.ofNullable(CscList.stream()
//                .filter(mo -> mo.getNominee().equalsIgnoreCase(movieName))
//                .filter(mo -> mo.getCategory().equalsIgnoreCase("Best Picture"))
//                .filter(mo_ -> mo_.getWon().equalsIgnoreCase("YES"))
//                .findFirst().orElseThrow(() -> new MovieNotFound("Movie was not found")));
        Optional<CsvDto> csv = CscList.stream().
                filter(mo->(mo.getNominee().equalsIgnoreCase(movieName)
                        && mo.getCategory().equalsIgnoreCase("Best Picture")
                        && mo.getWon().equalsIgnoreCase("YES"))).findFirst();
            return csv.orElseThrow(() -> new MovieNotFound("Movie was not found"));

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
