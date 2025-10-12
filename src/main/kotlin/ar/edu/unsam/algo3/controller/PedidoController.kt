package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.service.PedidoService
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class PedidoController(val pedidoService: PedidoService) {
    @GetMapping("/pedidos")
    fun listarPedidos() = pedidoService.getAll()

    @GetMapping("/pedidos/{estado}")
    fun listarPedidosPorEstado(@PathVariable estado : String) = pedidoService.getByEstado(estado)

    @GetMapping("/pedidos/{id}")
    fun listarPedidosPorEstado(@PathVariable id : Int) = pedidoService.getById(id)

    @PostMapping("/pedidos")
    fun actualizarEstado() = pedidoService.actualizarEstado()   //No recibe nada porque siempre pasa al siguiente estado
}