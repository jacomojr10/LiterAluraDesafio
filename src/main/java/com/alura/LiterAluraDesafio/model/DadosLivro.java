package com.alura.LiterAluraDesafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("authors") List<DadosAutor> autores,
        @JsonAlias("download_count") int numDownload
)  {
}

