package com.ecoterreiro.ecoterreiro_backend.repository;

import com.ecoterreiro.ecoterreiro_backend.entity.Terreiro;     // importando classe Terreiro
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;   // Esta é a interface central do Spring Data JPA.
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TerreiroRepository extends JpaRepository<Terreiro, Long> {

    // Spring Data JPA vai fornecer automaticamente métodos CRUD aqui
    // (ex: save(), findById(), findAll(), delete())

    // Métdo para buscar um Terreiro por nome (Pode retornar uma lista se houver nomes duplicados)
    List<Terreiro> findByNomeTerreiro(String nomeTerreiro);

    // Métdo para buscar um Terreiro por nome de pai/mãe de santo
    List<Terreiro> findByNomePaiMaeSantoTerreiro(String nomePaiMaeSantoTerreiro);

    // Métdo para buscar por nome do terreiro OU nome do pai/mãe de santo
    List<Terreiro> findByNomeTerreiroOrNomePaiMaeSantoTerreiro(String nomeTerreiro, String nomePaiMaeSantoTerreiro);

    // Métdo para buscar por um trecho do nome do terreiro (não precisa ser o nome completo)
    List<Terreiro> findByNomeTerreiroContaining(String nomeTerreiro); //  O Containing faz com que o Spring crie uma query com LIKE %nomeTerreiro%, o que é ótimo para buscas parciais.

    List<Terreiro> findByNomePaiMaeSantoTerreiroContaining(String nomePaiMaeSantoTerreiro);
}
