package com.cobistopaz.prueba_tecnica.domain.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespuestaHttp {
    private String mensaje;
    private int codigoStatus;
    private long tiempo;
    private Object contenido;
}
