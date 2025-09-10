package br.edu.utfpr.lembretes.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.lembretes.dto.LembreteDTO;
import br.edu.utfpr.lembretes.service.LembreteService;

@RestController
@RequestMapping("/pessoa/{pessoaId}/lembretes")
public class LembreteController {

    private final LembreteService service;

    public LembreteController(LembreteService service) {
        this.service = service;
    }

    @PostMapping
    public LembreteDTO criar(@PathVariable String pessoaId, @RequestBody LembreteDTO dto) {
        return service.salvar(pessoaId, dto);
    }

    @GetMapping
    public Page<LembreteDTO> listar(
            @PathVariable String pessoaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        return service.listarPorPessoa(pessoaId, pagina, tamanho);
    }
}