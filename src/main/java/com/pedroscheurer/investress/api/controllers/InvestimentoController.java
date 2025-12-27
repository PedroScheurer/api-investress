package com.pedroscheurer.investress.api.controllers;

import com.pedroscheurer.investress.api.dtos.InvestimentoDTO;
import com.pedroscheurer.investress.api.dtos.InvestimentoResponseDTO;
import com.pedroscheurer.investress.api.dtos.UserResponseDTO;
import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import com.pedroscheurer.investress.api.services.InvestimentoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ws/investimento")
public class InvestimentoController {

    private final InvestimentoService service;

    public InvestimentoController(InvestimentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InvestimentoResponseDTO> save(@RequestBody InvestimentoDTO dto) {
        InvestimentoEntity novoInvestimento = new InvestimentoEntity();
        BeanUtils.copyProperties(dto, novoInvestimento);

        service.save(novoInvestimento"");

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        String message = e.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.status(400).body(message);
    }


    private InvestimentoResponseDTO retornoBody (InvestimentoEntity investimento){
        return new InvestimentoResponseDTO(investimento.getId(), investimento.getNome(),investimento.getRetornoInvestimento(),
                investimento.getType(), investimento.getValorAtual(), investimento.getValorInvestido(),
                new UserResponseDTO(investimento.getUser().getId(), investimento.getUser().getNome(),
                        investimento.getUser().getEmail(), investimento.getUser().getType()));
    }
}
