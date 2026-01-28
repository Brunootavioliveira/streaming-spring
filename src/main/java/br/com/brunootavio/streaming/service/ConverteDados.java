package br.com.brunootavio.streaming.service;

import br.com.brunootavio.streaming.model.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{ //  Criar um novo serviço para converter dados e obter flexibilidade - Ctrl + /

    private ObjectMapper mapper = new ObjectMapper(); // cria um objeto responsável por converter JSON em objetos Java (e vice-versa) usando a biblioteca Jackson.

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe); // readValue(json, classe): Lê o JSON (json) e Converte para o tipo informado (classe)
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
