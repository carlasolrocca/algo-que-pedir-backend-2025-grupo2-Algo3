package ar.edu.unsam.algo3

import ar.edu.unsam.algo3.repositorios.TipoRepositorio
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Plato(
    var local: Local = Local(),
    var esdeAutor: Boolean = false,
    var nombre: String = "Nombre plato de autor",
    var descripcion: String = "Descripcion plato de autor",
    var valorBase: Double = 0.0,
): TipoRepositorio() {
    var fechaLanzamiento: LocalDate = LocalDate.now()
    var porcentajeDescuento: Double = 0.0
    var listaDeIngredientes: MutableSet<Ingrediente> = mutableSetOf()

    fun agregarIngrediente(nuevoIngrediente: Ingrediente) = listaDeIngredientes.add(nuevoIngrediente)

    fun removerIngrediente(sacoIngrediente: Ingrediente) = listaDeIngredientes.remove(sacoIngrediente)

    fun tieneIngrediente(ingrediente: Ingrediente) = listaDeIngredientes.contains(ingrediente)

    fun costoDeProduccion(): Double = listaDeIngredientes.sumOf { it.costoMercado }

    fun esVegano(): Boolean = listaDeIngredientes.none { it.origenAnimal }

    fun esNuevo(): Boolean = ChronoUnit.DAYS.between(fechaLanzamiento, LocalDate.now()) <= 30

    fun porcentajeIncremento(): Double {
        return 1 + local.porcentajeSobreCadaPlato + if(esdeAutor) local.porcentajeRegaliasDeAutor else 0.0
    }

   fun valorTotal(): Double = valorBase + (costoDeProduccion() * porcentajeIncremento())

    fun diasDesdeLanzamiento(): Int = ChronoUnit.DAYS.between(fechaLanzamiento, LocalDate.now()).toInt()

    fun descuentoPorPlatoNuevo(): Double {
        return if (diasDesdeLanzamiento() >= 21) {
            0.1
        } else {
            0.3 - (diasDesdeLanzamiento() * 0.01)
        }
    }

    fun valorDeVenta(): Double {
        val descuento = if (esNuevo()) descuentoPorPlatoNuevo() else porcentajeDescuento
        return valorTotal() * (1 - descuento)
    }

    fun contieneIngredientesProhibidos(usuario: Usuario): Boolean {
        return listaDeIngredientes.any { usuario.esIngredienteProhibido(it) }
    }

}