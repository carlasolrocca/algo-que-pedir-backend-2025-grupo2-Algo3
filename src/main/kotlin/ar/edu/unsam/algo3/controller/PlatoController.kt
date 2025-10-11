package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.service.PlatoService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class PlatoController(
    val platoService: PlatoService
) {
    @GetMapping("/platos")
    fun listarPlatos(): List<Plato> = platoService.getAll()

    @GetMapping("/plato/{id}")
    fun platoPorId(@PathVariable id: Int): Plato = platoService.getById(id)

    @PostMapping("/plato")
    fun crearPlato(@RequestBody nuevoPlato: Plato): Plato = platoService.create(nuevoPlato)

    @PutMapping("plato/{id}")
    fun actualizarPlato(@PathVariable id: Int, @RequestBody platoActualizado: Plato) = platoService.update(id, platoActualizado)

    @DeleteMapping("plato/{id}")
    fun borrarPlato(@PathVariable id: Int) = platoService.delete(id)
}