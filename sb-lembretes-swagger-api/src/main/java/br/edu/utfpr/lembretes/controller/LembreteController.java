package br.edu.utfpr.lembretes.controller;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa/{pessoaId}/lembretes")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Lembretes", description = "Endpoints para gerenciar lembretes de pessoas")
public class LembreteController {

    private final LembreteService service;

    public LembreteController(LembreteService service) {
        this.service = service;
    }

    /**
     * Cria um novo lembrete para a pessoa especificada.
     */
    @Operation(summary = "Criar lembrete", description = "Cria um novo lembrete para a pessoa especificada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LembreteDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public LembreteDTO criar(@PathVariable String pessoaId, @Valid @RequestBody LembreteDTO dto) {
        return service.salvar(pessoaId, dto);
    }

    /**
     * Lista os lembretes da pessoa especificada com paginação.
     * @param pessoaId
     * @param pagina
     * @param tamanho
     * @return
     */
    @Operation(summary = "Listar lembretes", description = "Lista os lembretes da pessoa especificada com paginação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LembreteDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping
    public Page<LembreteDTO> listar(
            @PathVariable String pessoaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        return service.listarPorPessoa(pessoaId, pagina, tamanho);
    }
}