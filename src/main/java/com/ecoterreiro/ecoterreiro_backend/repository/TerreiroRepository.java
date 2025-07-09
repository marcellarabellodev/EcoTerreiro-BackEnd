package com.ecoterreiro.ecoterreiro_backend.repository;

import com.ecoterreiro.ecoterreiro_backend.entity.Terreiro;     // importando classe Terreiro
import org.springframework.data.jpa.repository.JpaRepository;   // Esta é a interface central do Spring Data JPA.
import org.springframework.stereotype.Repository;

@Repository

public interface TerreiroRepository extends JpaRepository<Terreiro, Long> {

    // Spring Data JPA vai fornecer automaticamente métodos CRUD aqui
    // (ex: save(), findById(), findAll(), delete())

    // Podemos adicionar métodos personalizados aqui se precisar de consultas específicas, porém não é preciso, pois será criado no Controller
    // O Spring Data JPA pode implementá-los se seguir com a convenção de nomes.
    // Exemplo:  List<Terreiro> findByNomeTerreiroContainingIgnoreCase(String nome);
}
