package com.ecoterreiro.ecoterreiro_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity  // Marca esta classe como uma entidade JPA, mapeada para uma tabela do BD
@Table(name = "terreiros") // Especifica o nome da tabela no BD se for diferente do nome da classe
@Data // Anotação do Lombok: gera getters, setters, toString, equals, hashCode automaticamente

public class Terreiro {

    @Id  // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura autoincremento para o ID
    @Column(name = "id_terreiro")
    private Long id;    //  Usamos Long para IDs em Java

    @Column(name = "nome_terreiro", nullable = false, length = 255)
    @NotBlank(message = "Favor inserir o nome do Terreiro.") // Validação no nível da API
    private String nomeTerreiro;

    @Column(name = "end_terreiro", nullable = false, length = 500)
    @NotBlank(message = "Favor inserir o endereço do Terreiro.")
    private String endTerreiro;

    @Column(name = "nome_paimae_santo_terreiro", nullable = false, length = 250)
    @NotBlank(message = "Favor inserir o nome do(a) Pai/Mãe de Santo.")
    private String nomePaiMaeSantoTerreiro;

    @Column(name = "anos_terreiro")
    private Integer anosTerreiro;   // Usamos Integer para permitir valores nulos se necessário

    @Column(name = "dificuldade_para_aplicar_terreiro", columnDefinition = "TEXT", nullable = false)    // Usado para mapear String para o tipo TEXT no MySQL para campos longos.
    @NotBlank(message = "Precisa informar a dificuldade do Terreiro.")
    private String dificuldadeParaAplicarTerreiro;

    @Column(name = "praticas_que_possui_terreiro", columnDefinition = "TEXT")
    private String praticasQuePossuiTerreiro;

    @Column(name = "fez_diferenca_site")
    private Boolean fezDiferencaSite;

    @Column(name = "apresentou_para_comunidade_site")
    private Boolean apresentouParaComunidadeSite;

    @Column(name = "aplicou_alguma_pratica_site_no_terreiro")
    private Boolean aplicouAlgumaPraticaSiteNoTerreiro;

    // Para TIMESTAMP com DEFAULT CURRENT_TIMESTAMP no MySQL, LocalDateTime é uma boa escolha
    @Column(name = "data_cadastro")
    @CreationTimestamp //Esta anotação do Hibernate instrui-o a deixar o banco de dados gerar o valor na criação da entidade, em vez de tentar enviar null ou um valor Java para o mysql
    private LocalDateTime dataCadastro;     // Não precisa de @CreationTimestamp se o DB gerencia o default

}
