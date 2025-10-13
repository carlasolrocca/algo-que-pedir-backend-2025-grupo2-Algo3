package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Pedido

data class PedidoDTO (
    var id: Int ? = null,
    var clienteNombre: String = "",
    var clienteUsername : String = "",
    var hora : String = "",
    var items : Int = 0,
    var precioTotal : Double = 0.0,
    var estado : String = "",
    var direccion : String = "",
    var medioDePago : String = ""
)

fun Pedido.toListadoDTO() : PedidoDTO =
    PedidoDTO().also { dto ->
        dto.id = this.id
        dto.clienteNombre = "${this.cliente.nombre} ${this.cliente.apellido}".trim()
        dto.clienteUsername = this.cliente.username
        dto.hora = this.horarioPedido.toString()
        dto.items = this.platosDelPedido.size
        dto.precioTotal = this.costoTotalPedido()
        dto.estado = this.estadoDelPedido.name
        dto.direccion = "${this.cliente.direccion.calle} ${this.cliente.direccion.altura}"
        dto.medioDePago = this.medioDePago.name
    }