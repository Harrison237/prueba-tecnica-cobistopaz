package com.cobistopaz.prueba_tecnica.domain.web;

import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "RespuestaHttp", description = "Clase estandarizada para todas las respuestas HTTP que retornará el sistema.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespuestaHttp<T> {
    @Schema(name = "mensaje", description = "Mensaje de respuesta que retorna el sistema. Por lo general es personalizado, aunque puede ser un defecto \"Ok\".")
    private String mensaje;

    @Schema(description = "Código http del estado de la petición")
    private int codigoStatus;

    @Schema(description = "Tiempo representado en milisegundos del momento en que se respondió la petición.")
    private long tiempo;

    @Schema(description = "Contenido retornado por la petición. Si la respuesta de la petición es errónea, por lo general retorna un texto en blanco.")
    private T contenido;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
