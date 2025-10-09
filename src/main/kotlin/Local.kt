package ar.edu.unsam.algo3

import ar.edu.unsam.algo3.repositorios.TipoRepositorio

class Local(
    val nombre: String = "",
    val direccion: Direccion = Direccion(),
    var porcentajeSobreCadaPlato: Double = 0.0,
    var porcentajeRegaliasDeAutor: Double = 0.0,
): TipoRepositorio() {
    val mediosDePago: MutableSet<MedioDePago> = mutableSetOf()
    val RANGO_PUNTUACION_LOCAL = 4.0..5.0                   //El local define su rango de puntuacion para ser confiable
    var inboxMensajes : InboxMensajes = InboxMensajes()
    val puntuacionUsuarios: MutableList<Double> = mutableListOf()

    fun puntuar (puntaje: Double) {
        if (puntaje in 1.0..5.0) {
            puntuacionUsuarios.add(puntaje)
        }
    }

    fun calcularPromedioPuntuacion(): Double {
        return if (puntuacionUsuarios.isNotEmpty()){
            puntuacionUsuarios.average()
        } else { 0.0 }
    }

    //** Metodo que retorna si el local es confiable o no
    fun esConfiable(): Boolean = calcularPromedioPuntuacion() in RANGO_PUNTUACION_LOCAL

    //** Manejo de la coleccion de medios de pago aceptados por el Local
    fun agregarMedioDePago(medio: MedioDePago) {
        if (!mediosDePago.contains(medio)) {
            mediosDePago.add(medio)
        }
    }
    fun eliminarMedioDePago(medio: MedioDePago) {
        if (mediosDePago.contains(medio)) {
            mediosDePago.remove(medio)
        }
    }
}