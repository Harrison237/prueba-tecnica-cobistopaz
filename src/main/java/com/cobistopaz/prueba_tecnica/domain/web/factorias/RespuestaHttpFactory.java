package com.cobistopaz.prueba_tecnica.domain.web.factorias;

import com.cobistopaz.prueba_tecnica.domain.web.RespuestaHttp;

public class RespuestaHttpFactory {
    public static RespuestaHttp<String> respuestaError(String mensaje, int codigo) {
        return respuestaError(mensaje, codigo, "");
    }

    public static <T> RespuestaHttp<T> respuestaError(String mensaje, int codigo, T contenido) {
        return crear(mensaje, codigo, contenido);
    }

    public static <T> RespuestaHttp<T> respuestaExitosa(int codigo, T contenido) {
        return crear("Ok", codigo, contenido);
    }

    public static <T> RespuestaHttp<T> respuestaExitosa(String mensaje, int codigo, T contenido) {
        return crear(mensaje, codigo, contenido);
    }

    public static <T> RespuestaHttp<T> crear(String mensaje, int codigo, T contenido) {
        RespuestaHttp<T> respuesta = new RespuestaHttp<>();
        respuesta.setMensaje(mensaje);
        respuesta.setCodigoStatus(codigo);
        respuesta.setContenido(contenido == null? (T) "" : contenido);
        respuesta.setTiempo(System.currentTimeMillis());

        return respuesta;

    }
}
