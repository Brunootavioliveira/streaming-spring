package br.com.brunootavio.streaming.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //ignora oq vc nao encontrar
public record DadosSerie(@JsonAlias({"Title", "Titulo"}) String titulo,
                         @JsonAlias("Year") String ano,
                         @JsonAlias("totalSeasons") Integer temporadasTotais,
                         @JsonAlias("imdbRating") String avaliacao) {
    @Override
    public String toString() {
        return "Titulo: " + titulo +
                "\nAno: " + ano +
                "\nTemporados: " + temporadasTotais +
                "\nAvaliação: " + avaliacao;
    }
}
