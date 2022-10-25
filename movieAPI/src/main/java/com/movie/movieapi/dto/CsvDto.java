package com.movie.movieapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CsvDto {
    private String year;
    private String category;
    private String Nominee;
    private String additionalInfo;
    private String won;
}
