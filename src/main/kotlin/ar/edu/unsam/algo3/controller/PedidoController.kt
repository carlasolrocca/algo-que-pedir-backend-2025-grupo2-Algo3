package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.dto.PedidoClienteDTO
import ar.edu.unsam.algo3.dto.PedidoUpdateDTO
import ar.edu.unsam.algo3.dto.toClienteDTO
import ar.edu.unsam.algo3.service.PedidoService
import ar.edu.unsam.algo3.dto.PedidoDTO
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class PedidoController(val pedidoService: PedidoService) {
    //Llamada redundandte porque el home ya trae los pedidos Pendientes
    //@GetMapping("/pedidos")
    //fun listarPedidos() : List<PedidoDTO> = pedidoService.getAll()

    @GetMapping("/pedidos", params = ["estado"])
    fun listarPedidosPorEstado(@RequestParam("estado") estado : String): List<PedidoDTO> = pedidoService.getByEstado(estado)

    @GetMapping("/checkout-pedido/{id}")
    fun pedidoPorId(@PathVariable id: Int): PedidoClienteDTO = pedidoService.getById(id).toClienteDTO()

    @PostMapping("/checkout-pedido")
    fun crearPedido(@RequestBody pedidoBody: PedidoClienteDTO) = pedidoService.crearPedido(pedidoBody)

        //Endpoint para la actualizacion de estado del Pedido
    @PatchMapping("/pedidos")
    fun actualizarEstado(@RequestBody updateDTO: PedidoUpdateDTO) {
        pedidoService.actualizarEstado(updateDTO.id, updateDTO.nuevoEstado)
    }
}
