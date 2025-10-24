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

    @GetMapping("/pedidos", params = ["estado"])
    fun listarPedidosPorEstado(@RequestParam("estado") estado : String): List<PedidoDTO> = pedidoService.getByEstado(estado)

    @PatchMapping("/pedidos")
    fun actualizarEstado() = pedidoService.actualizarEstado()   //No recibe nada porque siempre pasa al siguiente estado
}
