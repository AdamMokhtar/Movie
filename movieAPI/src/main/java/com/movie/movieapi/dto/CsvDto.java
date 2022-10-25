package com.movie.movieapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CsvDto {
    String year;
    String category;
    String Nominee;
    String additionalInfo;
    boolean won;
}
