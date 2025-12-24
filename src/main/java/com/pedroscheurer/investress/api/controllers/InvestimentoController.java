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
        InvestimentoEntity investimento = new InvestimentoEntity();
        BeanUtils.copyProperties(dto, investimento);

        service.save(investimento);

        return ResponseEntity.status(200).body(new InvestimentoResponseDTO(
                investimento.getId(), investimento.getNome(), investimento.getRetornoInvestimento(),
                investimento.getType(), investimento.getValorAtual(), investimento.getValorInvestido(),
                new UserResponseDTO(investimento.getUser().getId(), investimento.getUser().getNome(),
                        investimento.getUser().getEmail(), investimento.getUser().getType())
        ));
    }

    @GetMapping
    public ResponseEntity<Page<InvestimentoEntity>> buscar(@RequestParam int page) {
        Page<InvestimentoEntity> foundPage = service.listarTodos(page);

        return ResponseEntity.status(200).body(foundPage);
    }

    @GetMapping(params = "id")
    public ResponseEntity<InvestimentoResponseDTO> buscarPorId(@RequestParam Long id) {
        InvestimentoEntity investimentoEncontrado = service.listarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.status(200).body(new InvestimentoResponseDTO(
                investimentoEncontrado.getId(), investimentoEncontrado.getNome(), investimentoEncontrado.getRetornoInvestimento(),
                investimentoEncontrado.getType(), investimentoEncontrado.getValorAtual(), investimentoEncontrado.getValorInvestido(),
                new UserResponseDTO(investimentoEncontrado.getUser().getId(), investimentoEncontrado.getUser().getNome(),
                        investimentoEncontrado.getUser().getEmail(), investimentoEncontrado.getUser().getType())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        String message = e.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.status(400).body(message);
    }

}
