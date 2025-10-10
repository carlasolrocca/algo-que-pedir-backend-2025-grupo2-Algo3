package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.service.PlatoService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class PlatoController(

) {
    @GetMapping("/platos")
    fun getPlatos(): String {
        return "Funciona el GET de platos!"
    }

   /* @GetMapping("/plato/{id}")
    fun platoPorId(@PathVariable id: Int) = platoService.platoPorId(id)

    @PostMapping
    fun crearPlato(@RequestBody plato: Plato): Plato = platoService.crear(plato) */
}