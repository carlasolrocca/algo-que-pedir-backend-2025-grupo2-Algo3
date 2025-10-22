package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.EnumEstadosPedido
import ar.edu.unsam.algo3.Pedido
import java.time.format.DateTimeFormatter
import java.util.Locale

data class PedidoDTO (
    var id: Int,
    var cliente: ClienteInfoDTO,
    var direccion : DireccionDTO,
    var hora : String,
    var items : Int,
    var precioTotal : Double,
    var estado : String,
    var medioDePago : String,
    var estadoPedido : EnumEstadosPedido
)

private val formateoHora = DateTimeFormatter.ofPattern("HH:mm", Locale("es", "AR"))

//Convierto al objeto de dominio Pedido en un DTO con la info que le sirve a la vista
fun Pedido.toDTO() : PedidoDTO =
    PedidoDTO (
        id!!,
        cliente = this.cliente.toDTO(),
        direccion = this.cliente.direccion.toDTO(),
        hora = this.horarioPedido.format(formateoHora),
        items = this.platosDelPedido.size,
        precioTotal = this.costoTotalPedido(),
        estado = this.estadoDelPedido.name,
        medioDePago = this.medioDePago.name,
        estadoPedido = this.estadoDelPedido
    )

