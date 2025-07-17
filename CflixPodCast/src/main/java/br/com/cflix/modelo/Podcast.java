package br.com.cflix.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "podcasts")
/**
 * A classe Podcast representa a entidade de um podcast no sistema.
 * 
 * Cada podcast possui informações como produtor, nome do episódio,
 * número do episódio, duração e URL do repositório.
 * Essa classe é utilizada para mapear a tabela "podcasts" no banco de dados,
 * facilitando o uso do JPA para persistência e manipulação dos dados.
 * 
 * Principais atributos:
 * - {@code id}: Identificador único do podcast.
 * - {@code produtor}: Nome do produtor do podcast.
 * - {@code nomeEpisodio}: Nome do episódio do podcast.
 * - {@code numEpisodio}: Número do episódio do podcast.
 * - {@code duracao}: Duração do episódio do podcast.
 * - {@code url}: URL do repositório do podcast.
 */
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String produtor;
    private String nomeEpisodio;
    private int numEpisodio;
    private String duracao;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdutor() {
        return produtor;
    }

    public void setProdutor(String produtor) {
        this.produtor = produtor;
    }

    public String getNomeEpisodio() {
        return nomeEpisodio;
    }

    public void setNomeEpisodio(String nomeEpisodio) {
        this.nomeEpisodio = nomeEpisodio;
    }

    public int getNumEpisodio() {
        return numEpisodio;
    }

    public void setNumEpisodio(int numEpisodio) {
        this.numEpisodio = numEpisodio;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
