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
            // Ou posso usar BeanUtils.copyProperties(terreiroDetails, existingTerreiro, "id", "dataCadastro")
            // para um update mais robusto, mas para começar, atualização manual é mais clara.
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
            // Lançar a exceção personalizada, que o RestExceptionHandler irá capturar e formatar
            throw new TerreiroNotFoundException("Terreiro não encontrado com o ID: " + id);
        }
    }

    // 5. Métdo DELETE
    // URL: DELETE http://localhost:8080/api/terreiros/{id} (ex: http://localhost:8080/api/terreiros/1)
    @DeleteMapping("/{id}")
    public ResponseEntity<Terreiro> deleteTerreiro(@PathVariable Long id) {
        // Verifica se o Terreiro existe
        if (!terreiroRepository.existsById(id)) {
            // Se não existir, lança a exceção que será interceptada e retornará 404
            throw new TerreiroNotFoundException("Terreiro não encontrado com o ID: " + id);
        }
        // Se existir, deleta
        terreiroRepository.deleteById(id);
        // Retorna 204 No Content (sem corpo) indicando sucesso ao deletar
        return ResponseEntity.noContent().build();
    }


    // Endpoint para buscar terreiros por nome COMPLETO do terreiro
    // URL: GET http://localhost:8080/api/terreiros/byName?nome=Terreiro Pai Benedito
    @GetMapping("/byName")
    public ResponseEntity<List<Terreiro>> getTerreiroByNomeTerreiro(@RequestParam("nome") String nomeTerreiro) {
        List<Terreiro> terreiros = terreiroRepository.findByNomeTerreiro(nomeTerreiro);
        // Se a lista estiver vazia, lança a exceção
        if (terreiros.isEmpty()) {
            // Lançar a exceção personalizada, que o RestExceptionHandler irá formatar
            throw new TerreiroNotFoundException("Nenhum terreiro encontrado com o nome informado: " + nomeTerreiro);
        }
        return ResponseEntity.ok(terreiros);
    }

    // Endpoint para buscar por nome COMPLETO de pai/mãe de santo
    // URL: GET http://localhost:8080/api/terreiros/byPaiMaeSanto?nome=Mãe Jussara Martins
    @GetMapping("/byPaiMaeSanto")
    public  ResponseEntity<List<Terreiro>> getTerreiroByPaiMaeSanto(@RequestParam("nome") String nomePaiMaeSantoTerreiro) {
        List<Terreiro> terreiros = terreiroRepository.findByNomePaiMaeSantoTerreiro(nomePaiMaeSantoTerreiro);
        // Se a lista estiver vazia, lança a exceção
        if (terreiros.isEmpty()) {
            // Lançar a exceção personalizada, que o RestExceptionHandler irá formatar
            throw new TerreiroNotFoundException("Nenhum terreiro encontrado com o nome informado: " + nomePaiMaeSantoTerreiro);
        }
        return ResponseEntity.ok(terreiros);
    }

    // Endpoint para buscar por um trecho do nome do terreiro
    // URL: GET http://localhost:8080/api/terreiros/byNameParcial?nome=Aruanda (exemplo)
    @GetMapping("/byNameParcial")
    public ResponseEntity<List<Terreiro>> getTerreirosByNomeParcial(@RequestParam("nome") String nomeTerreiro) {
        List<Terreiro> terreiros = terreiroRepository.findByNomeTerreiroContaining(nomeTerreiro);
        // Se a lista estiver vazia, lança a exceção
        if (terreiros.isEmpty()) {
        // Lançar a exceção personalizada, que o RestExceptionHandler irá formatar
            throw new TerreiroNotFoundException("Nenhum terreiro encontrado com o nome ou trecho inserido: " + nomeTerreiro);
        }
        return ResponseEntity.ok(terreiros);
    }

    // Endpoint para buscar por um trecho do nome do pai/mãe de santo
    // URL: GET http://localhost:8080/api/terreiros/byPaiMaeSantoParcial?nome=Rosalina (exemplo)
    @GetMapping("/byPaiMaeSantoParcial")
    public ResponseEntity<List<Terreiro>> getTerreirosByPaiMaeSantoParcial(@RequestParam("nome") String nomePaiMaeSantoTerreiro) {
        List<Terreiro> terreiros = terreiroRepository.findByNomePaiMaeSantoTerreiroContaining(nomePaiMaeSantoTerreiro);
        if (terreiros.isEmpty()) {
            // Lançar a exceção personalizada, que o RestExceptionHandler irá formatar
            throw new TerreiroNotFoundException("Nenhum terreiro encontrado com o nome ou trecho inserido: " + nomePaiMaeSantoTerreiro);
        }
        return ResponseEntity.ok(terreiros);
    }
}
