package br.edu.utfpr.lembretes.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LembreteDTO(UUID id, String mensagem, LocalDateTime dataHora) {
}
