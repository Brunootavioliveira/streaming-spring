package br.com.brunootavio.streaming.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe); //VAI RETORNAR ALGUMA COISA, MAS NAO SABEMOS OQ <T>

//    - <T> → tipo genérico (pode ser qualquer classe)
//    - String json → texto JSON
//    - Class<T> classe → qual classe você quer converter
//    - Retorno: um objeto do tipo que você pediu
}
