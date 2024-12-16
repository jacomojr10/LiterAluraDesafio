package com.alura.LiterAluraDesafio.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);


}
