package br.com.brunootavio.streaming.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public String obterDados(String endereco) {

        // Cria um cliente HTTP padrão para fazer requisições
        HttpClient client = HttpClient.newHttpClient();

        // Cria a requisição HTTP usando o endereço (URL) recebido
        HttpRequest request = HttpRequest.newBuilder()
                // Converte a String 'endereco' em uma URI válida
                .uri(URI.create(endereco))
                // Finaliza a construção da requisição
                .build();

        // Declara a variável que vai armazenar a resposta da API
        HttpResponse<String> response = null;

        try {
            // Envia a requisição para a API e espera a resposta
            // O BodyHandlers.ofString() indica que o corpo da resposta será uma String
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            // Trata erro de entrada/saída (ex: problema de rede)
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            // Trata erro caso a execução da thread seja interrompida
            throw new RuntimeException(e);
        }

        // Extrai apenas o corpo da resposta (JSON retornado pela API)
        String json = response.body();

        // Retorna o JSON como String
        return json;
    }


}
