package com.ecoterreiro.ecoterreiro_backend.controller;

import com.ecoterreiro.ecoterreiro_backend.entity.Terreiro;
import com.ecoterreiro.ecoterreiro_backend.exception.TerreiroNotFoundException;
import com.ecoterreiro.ecoterreiro_backend.repository.TerreiroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marca esta classe como um controlador REST
@RequestMapping("/api/terreiros")  // Define o caminho base para os endpoints deste controlador
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (útil para desenvolvimento, CUIDADO em produção)

public class TerreiroController {

    @Autowired  // Injeta automaticamente uma instância de TerreiroRepository
    private TerreiroRepository terreiroRepository;

    // 1. Métdo POST
    // URL: POST http://localhost:8080/api/terreiros
    @PostMapping   // Mapeia requisições POST para este métdo no caminho base (/api/terreiros)
    public ResponseEntity<Terreiro> createTerreiro(@Valid @RequestBody Terreiro terreiro) {
        Terreiro novoTerreiro = terreiroRepository.save(terreiro);
        return new ResponseEntity<>(novoTerreiro, HttpStatus.CREATED);
    }

    // 2. Métdo GET ALL
    // URL: GET http://localhost:8080/api/terreiros
    @GetMapping
    public ResponseEntity<List<Terreiro>> getAllTerreiros() {
        List<Terreiro> terreiros = terreiroRepository.findAll();
        return new ResponseEntity<>(terreiros, HttpStatus.OK);  // Retorna 200 OK
    }


    // 3. Métdo GET BY ID
    // URL: GET http://localhost:8080/api/terreiros/{id} (ex: http://localhost:8080/api/terreiros/1)
    @GetMapping("/{id}")
    public ResponseEntity<Terreiro> getTerreiroById(@PathVariable Long id) {
        Terreiro terreiro = terreiroRepository.findById(id)
        // .orElseThrow: Ele tenta obter o objeto dentro do Optional. Se o objeto estiver presente, ele o retorna. Se o Optional estiver vazio, ele lança a exceção que foi especificada
                .orElseThrow(() -> new TerreiroNotFoundException("Terreiro não encontrado com o ID: " + id));
        return ResponseEntity.ok(terreiro);
    }

    // 4. Métdo PUT (UPDATE)
    // URL: PUT http://localhost:8080/api/terreiros/{id} (ex: http://localhost:8080/api/terreiros/1)
    // Corpo da requisição deve ser um JSON completo do Terreiro com os dados atualizados
    @PutMapping("/{id}")
    public ResponseEntity<Terreiro> updateTerreiro(@PathVariable Long id, @Valid @RequestBody Terreiro terreiroDetails) {
        // Verifica se o terreiro com o ID existe
        Optional<Terreiro> existingTerreiroOptional = terreiroRepository.findById(id);

        if (existingTerreiroOptional.isPresent()) {
            Terreiro existingTerreiro = existingTerreiroOptional.get();

            // Atualiza os campos do terreiro existente com os detalhes fornecidos
            // É importante atualizar campo por campo para não sobrescrever com null se um campo não for enviado no JSON
            // Ou podemos usar BeanUtils.copyProperties(terreiroDetails, existingTerreiro, "id", "dataCadastro")
            // caso queira um update mais robusto, mas para começar, atualização manual é mais clara.
            existingTerreiro.setNomeTerreiro(terreiroDetails.getNomeTerreiro());
            existingTerreiro.setEndTerreiro(terreiroDetails.getEndTerreiro());
            existingTerreiro.setNomePaiMaeSantoTerreiro(terreiroDetails.getNomePaiMaeSantoTerreiro());
            existingTerreiro.setAnosTerreiro(terreiroDetails.getAnosTerreiro());
            existingTerreiro.setDificuldadeParaAplicarTerreiro(terreiroDetails.getDificuldadeParaAplicarTerreiro());
            existingTerreiro.setPraticasQuePossuiTerreiro(terreiroDetails.getPraticasQuePossuiTerreiro());
            existingTerreiro.setFezDiferencaSite(terreiroDetails.getFezDiferencaSite());
            existingTerreiro.setApresentouParaComunidadeSite(terreiroDetails.getApresentouParaComunidadeSite());
            existingTerreiro.setAplicouAlgumaPraticaSiteNoTerreiro(terreiroDetails.getAplicouAlgumaPraticaSiteNoTerreiro());
            // dataCadastro não deve ser atualizado manualmente aqui se for @CreationTimestamp

            Terreiro updatedTerreiro = terreiroRepository.save(existingTerreiro); // Salva as alterações
            return new ResponseEntity<>(updatedTerreiro, HttpStatus.OK); // Retorna 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found se o ID não existir
        }
    }

    // 5. Métdo DELETE
    // URL: DELETE http://localhost:8080/api/terreiros/{id} (ex: http://localhost:8080/api/terreiros/1)
    @DeleteMapping("/{id}")
    public ResponseEntity<Terreiro> deleteTerreiro(@PathVariable Long id) {
        try {
            terreiroRepository.deleteById(id); // Tenta deletar pelo ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content (sucesso sem conteúdo)
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 em caso de erro
        }
    }

    // Endpoint para buscar terreiros por nome
    @GetMapping("/byName")
    public ResponseEntity<List<Terreiro>> getTerreiroByNomeTerreiro(@RequestParam("nome") String nomeTerreiro) {
        List<Terreiro> terreiro = terreiroRepository.findByNomeTerreiro(nomeTerreiro);
        if (terreiro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(terreiro);
    }

    // Endpoint para buscar por nome de pai/mãe de santo
    @GetMapping("/byPaiMaeSanto")
    public  ResponseEntity<List<Terreiro>> getTerreiroByPaiMaeSanto(@RequestParam("nome") String nomePaiMaeSantoTerreiro) {
        List<Terreiro> terreiro = terreiroRepository.findByNomePaiMaeSantoTerreiro(nomePaiMaeSantoTerreiro);
        if (terreiro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(terreiro);
    }

    // Endpoint para buscar por um trecho do nome do terreiro
    // URL: GET http://localhost:8080/api/terreiros/byNamePartial?nome=terro
    @GetMapping("/byNameParcial")
    public ResponseEntity<List<Terreiro>> getTerreirosByNomeParcial(@RequestParam("nome") String nomeTerreiro) {
        List<Terreiro> terreiro = terreiroRepository.findByNomeTerreiroContaining(nomeTerreiro);
        if (terreiro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(terreiro);
    }
}
