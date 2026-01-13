package com.pedroscheurer.investress.api.controllers;

import com.pedroscheurer.investress.api.dtos.request.InvestimentoDTO;
import com.pedroscheurer.investress.api.dtos.response.InvestimentoQueryTipo;
import com.pedroscheurer.investress.api.dtos.response.InvestimentoResponseDTO;
import com.pedroscheurer.investress.api.dtos.response.UserResponseDTO;
import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import com.pedroscheurer.investress.api.services.InvestimentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ws/investimento")
public class InvestimentoController {

    private final InvestimentoService service;

    public InvestimentoController(InvestimentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InvestimentoResponseDTO> save(@Valid @RequestBody InvestimentoDTO dto) {
        InvestimentoEntity novoInvestimento = new InvestimentoEntity();
        BeanUtils.copyProperties(dto, novoInvestimento);
        novoInvestimento.setDataInvestimento(LocalDateTime.now());
        service.save(novoInvestimento);

        return ResponseEntity.status(200).body(retornoBody(novoInvestimento));
    }

    @GetMapping(params = "page")
    public ResponseEntity<Page<InvestimentoEntity>> buscar(@RequestParam int page) {
        Page<InvestimentoEntity> paginaEncontrada = service.listarTodos(page);

        return ResponseEntity.status(200).body(paginaEncontrada);
    }

    @GetMapping(params = "id")
    public ResponseEntity<InvestimentoResponseDTO> buscarPorId(@RequestParam Long id) {
        InvestimentoEntity investimentoEncontrado = service.listarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.status(200).body(retornoBody(investimentoEncontrado));
    }

    @GetMapping(params = {"nome", "page"})
    public ResponseEntity<Page<InvestimentoEntity>> buscarPorNome(@RequestParam String nome, int page){
        Page<InvestimentoEntity> paginaEncontrada = service.listarPorNome(page, nome);

        return ResponseEntity.status(200).body(paginaEncontrada);
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<InvestimentoQueryTipo>> buscarAgrupadosPorTipo(){
        List<InvestimentoQueryTipo> investimentosAgrupados = service.listarAgrupadoPorTipo();

        return ResponseEntity.status(200).body(investimentosAgrupados);
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<InvestimentoResponseDTO>> buscarPorTipo(@PathVariable TypeInvestimento tipo){
        List<InvestimentoEntity> investimentosEncontrados = service.listarPorTipo(tipo);
        List<InvestimentoResponseDTO> response =
                investimentosEncontrados.stream()
                        .map(this::retornoBody)
                        .toList();
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> buscarPorTipo(@PathVariable Long id){
        service.excluirInvestimento(id);
        return ResponseEntity.status(204).body("Investimento deletado com sucesso");
    }

    private InvestimentoResponseDTO retornoBody (InvestimentoEntity investimento){
        return new InvestimentoResponseDTO(investimento.getId(), investimento.getNome(),investimento.getRetornoInvestimento(),
                investimento.getType(), investimento.getValorAtual(), investimento.getValorInvestido(), investimento.getDataInvestimento(),
                new UserResponseDTO(investimento.getUser().getId(), investimento.getUser().getNome(),
                        investimento.getUser().getEmail(), investimento.getUser().getType()));
    }
}
