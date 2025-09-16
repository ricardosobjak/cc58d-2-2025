package br.edu.utfpr.lembretes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaDTO(
    @NotBlank
    @Size(min = 2, max = 200)
    String nome,

    @NotBlank
    @Email
    @Size(min = 2, max = 200)
    String email
) {}