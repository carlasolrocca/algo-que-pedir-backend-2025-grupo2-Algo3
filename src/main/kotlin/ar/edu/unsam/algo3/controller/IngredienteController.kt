package ar.edu.unsam.algo3.ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.ar.edu.unsam.algo3.service.IngredienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class IngredienteController(val ingredienteService: IngredienteService) {
    @GetMapping("/ingrediente")
    fun listarIngredientes(): List<Ingrediente> = ingredienteService.getAll()

    @GetMapping("/ingrediente/{id}")
    fun ingredientePorId(@PathVariable id: Int): Ingrediente = ingredienteService.getById(id)

    @PostMapping("/ingrediente")
    fun crearIngrediente(@RequestBody ingredienteBody: Ingrediente): Ingrediente =
        ingredienteService.create(ingredienteBody)

}