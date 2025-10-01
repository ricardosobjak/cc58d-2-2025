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
import br.edu.utfpr.lembretes.exception.ErrorResponse;
import br.edu.utfpr.lembretes.model.Pessoa;
import br.edu.utfpr.lembretes.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas a pessoas.
 * Ele fornece endpoints para criar, atualizar, deletar e listar pessoas.
 */
@RestController
@RequestMapping("/pessoa")
@Tag(name = "Pessoa", description = "Endpoints para gerenciar pessoas")
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
    @Operation(summary = "Criar pessoa", description = "Cria uma nova pessoa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Pessoa.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public Pessoa criar(@Valid @RequestBody PessoaDTO dto) {
        return service.salvar(dto);
    }

    /**
     * Endpoint para listar pessoas com paginação.
     * Recebe parâmetros de consulta para página e tamanho, retornando uma página de
     * pessoas.
     */
    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas com paginação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Pessoa.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500")
    })
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
    @Operation(summary = "Obter pessoa por ID", description = "Obtém uma pessoa pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Pessoa.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping("/{id}")
    public Pessoa obterPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    /**
     * Endpoint para deletar uma pessoa por ID.
     * Recebe o ID da pessoa como parâmetro de consulta e deleta a pessoa
     * correspondente.
     */
    @Operation(summary = "Deletar pessoa", description = "Deleta uma pessoa pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500")
    })
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
    @Operation(summary = "Atualizar pessoa", description = "Atualiza uma pessoa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Pessoa.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500")
    })
    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable String id, @Valid @RequestBody PessoaDTO dto) {
        return service.atualizar(id, dto);
    }
}