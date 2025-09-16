package br.edu.utfpr.lembretes.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.lembretes.dto.PessoaDTO;
import br.edu.utfpr.lembretes.model.Pessoa;
import br.edu.utfpr.lembretes.service.PessoaService;
import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas a pessoas.
 * Ele fornece endpoints para criar, atualizar, deletar e listar pessoas.
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    // Injeção do serviço de pessoa
    private final PessoaService service;

    /**
     * Construtor da classe PessoaController.
     * Injeta a dependência do serviço de pessoa.
     * 
     * @param service
     */
    public PessoaController(PessoaService service) {
        this.service = service;
    }

    /**
     * Endpoint para criar uma nova pessoa.
     * Recebe um objeto PessoaDTO no corpo da requisição e retorna a pessoa criada.
     */
    @PostMapping
    public Pessoa criar(@Valid @RequestBody PessoaDTO dto) {
        return service.salvar(dto);
    }

    /**
     * Endpoint para listar pessoas com paginação.
     * Recebe parâmetros de consulta para página e tamanho, retornando uma página de
     * pessoas.
     */
    @GetMapping
    public Page<Pessoa> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        return service.listarTodos(pagina, tamanho);
    }

    /**
     * Endpoint para obter uma pessoa por ID.
     * Recebe o ID da pessoa como parâmetro de caminho e retorna a pessoa
     * correspondente.
     */
    @GetMapping("/{id}")
    public Pessoa obterPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    /**
     * Endpoint para deletar uma pessoa por ID.
     * Recebe o ID da pessoa como parâmetro de consulta e deleta a pessoa
     * correspondente.
     */
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        service.deletar(id);
    }

    /**
     * Endpoint para atualizar uma pessoa existente.
     * Recebe o ID da pessoa como parâmetro de caminho e um objeto PessoaDTO no
     * corpo da requisição.
     * Retorna a pessoa atualizada.
     * 
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable String id, @Valid @RequestBody PessoaDTO dto) {
        return service.atualizar(id, dto);
    }
}