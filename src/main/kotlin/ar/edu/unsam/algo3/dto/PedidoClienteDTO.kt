package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Delivery
import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.dto.ClienteInfoDTO
import ar.edu.unsam.algo3.dto.LocalClienteDTO
import ar.edu.unsam.algo3.dto.PlatoClienteDTO
import ar.edu.unsam.algo3.dto.toClienteDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.toDomain
import ar.edu.unsam.algo3.dto.toInfoDTO
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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
    var costoTotalPedido: Double,
    var usuario: UsuarioDTO
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
        costoTotalPedido = this.costoTotalPedido(),
        usuario = this.cliente.toDTO()
    )
}

fun PedidoClienteDTO.toDomain(): Pedido {
    if (costoTotalPedido != this.costoTotalPedido){
        throw ErrorException.BusinessException("El costo enviado no coincide con el costo real")
    }
    val instant = Instant.parse(this.fechaPedido)
    val zonaBuenosAires = ZoneId.of("America/Argentina/Buenos_Aires")
    val fechaLocal: LocalDate = instant.atZone(zonaBuenosAires).toLocalDate()
    return Pedido(
        local = this.local.toDomain(),
        medioDePago = this.medioDePago,
        platosDelPedido = this.platosDelPedido.map { it.toDomain() }.toMutableList(),
        fechaPedido = fechaLocal,
        cliente = this.usuario.toDomain()
    ).apply{
        this.id = this@toDomain.id
    }
}