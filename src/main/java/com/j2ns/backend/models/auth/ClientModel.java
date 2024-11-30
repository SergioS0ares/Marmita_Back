package com.j2ns.backend.models.auth;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Define que esta classe é uma entidade JPA, mapeada para uma tabela no banco de dados.
public class ClientModel {

    @Id // Define o campo "id" como a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.AUTO) // O ID será gerado automaticamente (com a estratégia definida no banco de dados).
    private UUID id; // ID único do cliente, gerado como UUID.

    @Column(nullable = false) // Define que o campo "nome" não pode ser nulo no banco de dados.
    private String nome; // Nome do cliente.

    @Column(nullable = false) // Define que o campo "descricaoEndereco" não pode ser nulo no banco de dados.
    private String descricaoEndereco; // Endereço do cliente.

    @Column // Define que o campo "quantPedido" pode ser nulo.
    private int quantPedido; // Quantidade de pedidos do cliente.

    @Column // Define que o campo "telefone" pode ser nulo.
    private String telefone; // Telefone de contato do cliente.

    @Column // Define que o campo "latitude" pode ser nulo.
    private String latitude; // Latitude do cliente para localização.

    @Column // Define que o campo "longitude" pode ser nulo.
    private String longitude; // Longitude do cliente para localização.

    @Column // Define que o campo "sujestH" pode ser nulo.
    private String sujestH; // Sugestões ou observações adicionais sobre o cliente.

    // Métodos getters e setters (acessores e mutadores) para manipular os atributos do cliente.
    
    public UUID getId() {
        return id; // Retorna o ID do cliente.
    }

    public String getNome() {
        return nome; // Retorna o nome do cliente.
    }

    public String getDescricaoEndereco() {
        return descricaoEndereco; // Retorna a descrição do endereço do cliente.
    }

    public int getQuantPedido() {
        return quantPedido; // Retorna a quantidade de pedidos do cliente.
    }

    public String getTelefone() {
        return telefone; // Retorna o telefone do cliente.
    }

    public String getLatitude() {
        return latitude; // Retorna a latitude do cliente.
    }

    public String getLongitude() {
        return longitude; // Retorna a longitude do cliente.
    }

    public String getSujestH() {
        return sujestH; // Retorna as sugestões ou observações adicionais sobre o cliente.
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o nome do cliente.
    }

    public void setDescricaoEndereco(String descricaoEndereco) {
        this.descricaoEndereco = descricaoEndereco; // Define a descrição do endereço do cliente.
    }

    public void setQuantPedido(int x) {
        this.quantPedido = x; // Define a quantidade de pedidos do cliente.
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone; // Define o telefone do cliente.
    }

    public void setLatitude(String lat) {
        this.latitude = lat; // Define a latitude do cliente.
    }

    public void setLongitude(String lon) {
        this.longitude = lon; // Define a longitude do cliente.
    }

    public void setSujestH(String sujestH) {
        this.sujestH = sujestH; // Define as sugestões ou observações sobre o cliente.
    }

    public void setId(UUID id) {
        this.id = id; // Define o ID do cliente.
    }
}
