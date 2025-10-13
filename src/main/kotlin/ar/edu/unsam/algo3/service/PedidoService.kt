package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.dto.toDTO
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.PedidoRepositorio


@Service
class PedidoService(
    private val pedidoRepo : PedidoRepositorio
) {
    fun getAll() : List<PedidoDTO> = pedidoRepo.findAll().map {it.toDTO()}

    fun getByEstado(estado : String) : List<Pedido> = pedidoRepo.search(estado)

    fun getById(id: Int) : Pedido = pedidoRepo.getById(id)

    fun actualizarEstado(){}
}