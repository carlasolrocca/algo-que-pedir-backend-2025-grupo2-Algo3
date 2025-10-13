package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.service.PedidoService
import ar.edu.unsam.algo3.dto.PedidoDTO
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class PedidoController(val pedidoService: PedidoService) {

    @GetMapping("/pedidos")
    fun listarPedidos() : List<PedidoDTO> = pedidoService.getAll()

    @GetMapping("/pedidos/{estado}")
    fun listarPedidosPorEstado(@PathVariable estado : String): List<Pedido> = pedidoService.getByEstado(estado)

//    @GetMapping("/detalle-pedido/{id}")
//    fun listarPedidosPorEstado(@PathVariable id : Int) : Pedido = pedidoService.getById(id)

    @PatchMapping("/pedidos")
    fun actualizarEstado() = pedidoService.actualizarEstado()   //No recibe nada porque siempre pasa al siguiente estado
}
