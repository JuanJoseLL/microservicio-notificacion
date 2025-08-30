package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificar")
@Tag(name = "Notificaciones", description = "API para el envío de notificaciones a usuarios del sistema de biblioteca")
public class NotificacionController {
    
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_LIBRARIAN')")
    @Operation(
        summary = "Enviar notificación",
        description = "Envía una notificación a un usuario específico del sistema de biblioteca. " +
                     "Solo usuarios autenticados con rol USER o LIBRARIAN pueden enviar notificaciones.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación enviada exitosamente",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(example = "Error: El campo usuarioId es requerido"))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "No autorizado - Token de autenticación requerido",
            content = @Content(schema = @Schema(example = "Error: Token de acceso requerido"))
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Acceso denegado - Permisos insuficientes",
            content = @Content(schema = @Schema(example = "Error: Rol USER o LIBRARIAN requerido"))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(example = "Error interno al procesar la notificación"))
        )
    })
    public ResponseEntity<String> enviarNotificacion(
        @Parameter(
            description = "Datos de la notificación a enviar", 
            required = true,
            schema = @Schema(implementation = NotificacionDTO.class)
        )
        @Valid @RequestBody NotificacionDTO notificacion
    ) {
        try {
            notificacionService.enviarNotificacion(notificacion);
            return ResponseEntity.ok("Notificación enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar la notificación: " + e.getMessage());
        }
    }
}