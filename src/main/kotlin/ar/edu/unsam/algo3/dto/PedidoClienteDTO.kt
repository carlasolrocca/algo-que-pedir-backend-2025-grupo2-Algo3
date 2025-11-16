package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.dto.LocalClienteDTO
import ar.edu.unsam.algo3.dto.PlatoClienteDTO
import ar.edu.unsam.algo3.dto.toClienteDTO
import java.time.format.DateTimeFormatter
import java.util.Locale


// En principio voy a armar este nuevo DTO para la aplicación de React

data class PedidoClienteDTO(
    var id: Int,
    var local: LocalClienteDTO,
    var fechaPedido: String,
    var platosDelPedido: List<PlatoClienteDTO>,
    var cantidadDePlatos: Int,
    var medioDePago: MedioDePago,
    var costoSubtotalPedido: Double,
    var recargoMedioDePago: Double,
    var tarifaEntrega: Double,
    var costoTotalPedido: Double
)

private val formateoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "AR"))

fun Pedido.toClienteDTO(): PedidoClienteDTO {
    return PedidoClienteDTO(
        id = id!!,
        local = this.local.toClienteDTO(),
        fechaPedido = this.fechaPedido.format(formateoFecha),
        platosDelPedido = this.platosDelPedido.map { it.toClienteDTO() },
        cantidadDePlatos = this.cantidadDePlatos(),
        medioDePago = this.medioDePago,
        costoSubtotalPedido = this.valorVentaPlatos(),
        recargoMedioDePago = this.costoMedioDePago(),
        tarifaEntrega = this.costoDeEntrega(),
        costoTotalPedido = this.costoTotalPedido()
    )
}