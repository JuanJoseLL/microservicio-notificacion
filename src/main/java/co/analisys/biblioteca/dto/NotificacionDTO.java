package co.analisys.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el envío de notificaciones a usuarios")
public class NotificacionDTO {
    
    @Schema(
        description = "Identificador único del usuario destinatario",
        example = "user123",
        required = true
    )
    @NotBlank(message = "El ID del usuario es obligatorio")
    @Size(min = 1, max = 50, message = "El ID del usuario debe tener entre 1 y 50 caracteres")
    private String usuarioId;
    
    @Schema(
        description = "Contenido del mensaje de notificación",
        example = "Su préstamo del libro 'Cien años de soledad' vence mañana. Por favor, renueve o devuelva el libro.",
        required = true
    )
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 500, message = "El mensaje debe tener entre 10 y 500 caracteres")
    private String mensaje;
}
