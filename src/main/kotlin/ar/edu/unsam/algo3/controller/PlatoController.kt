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

    @PostMapping
    fun crearPlato(@RequestBody plato: Plato) = platoService.create(plato)

    @PutMapping
    fun actualizarPlato(@RequestBody plato: Plato) = platoService.update(plato)

    @DeleteMapping
    fun borrarPlato(@RequestBody plato: Plato) = platoService.delete(plato)
}