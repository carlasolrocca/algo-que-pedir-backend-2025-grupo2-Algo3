package ar.edu.unsam.algo3.service

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.PedidoRepositorio


@Service
class PedidoService(
    private val pedidoRepo : PedidoRepositorio
) {
    fun getAll() = pedidoRepo.findAll()

    fun getByEstado(estado : String) = pedidoRepo.search(estado)

    fun getById(id: Int) = pedidoRepo.getById(id)

    fun actualizarEstado(){}
}