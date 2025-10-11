package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.service.PlatoService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class PlatoController(val platoService: PlatoService) {
    @GetMapping("/plato")
    fun listarPlatos(): List<Plato> = platoService.getAll()

    @GetMapping("/plato/{id}")
    fun platoPorId(@PathVariable id: Int): Plato = platoService.getById(id)

    @PostMapping("/plato")
    fun crearPlato(@RequestBody platoBody: Plato): Plato =
        platoService.create(platoBody)

    @PutMapping("/plato/{id}")
    fun actualizarPlato(@PathVariable id: Int, @RequestBody platoBody: Plato): Plato =
        platoService.update(id, platoBody)

    @DeleteMapping("/plato/{id}")
    fun borrarPlato(@PathVariable id: Int): List<Plato> =
        platoService.delete(id)
}