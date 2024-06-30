package com.cobistopaz.prueba_tecnica.domain.web.factorias;

import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;

public class RespuestaHttpFactory {
    public static RespuestaHttp respuestaError(String mensaje, int codigo) {
        return respuestaError(mensaje, codigo, null);
    }

    public static RespuestaHttp respuestaError(String mensaje, int codigo, Object contenido) {
        return crear(mensaje, codigo, contenido != null ? contenido : "");
    }

    public static RespuestaHttp respuestaExitosa(String mensaje, int codigo, Object contenido) {
        return crear(mensaje, codigo, contenido);
    }

    public static RespuestaHttp crear(String mensaje, int codigo, Object contenido) {
        return RespuestaHttp.builder()
                .mensaje(mensaje)
                .codigoStatus(codigo)
                .contenido(contenido)
                .tiempo(System.currentTimeMillis())
                .build();

    }
}
