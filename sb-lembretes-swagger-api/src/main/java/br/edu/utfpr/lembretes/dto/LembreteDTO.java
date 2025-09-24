package br.edu.utfpr.lembretes.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LembreteDTO(UUID id, 
    @NotBlank
    @Size(min = 2, max = 255)
    String mensagem, 

    @FutureOrPresent
    LocalDateTime dataHora) {
}
