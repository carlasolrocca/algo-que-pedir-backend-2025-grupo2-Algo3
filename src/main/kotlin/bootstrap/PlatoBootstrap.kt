package bootstrap;

import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.bootstrap.IngredienteBootstrap
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service

@Service
class PlatoBootstrap(
        val platoRepositorio: PlatoRepositorio,
        val ingredienteBootstrap: IngredienteBootstrap
): InitializingBean {

    private lateinit var ensalada: Plato
    private lateinit var arrozConLeche: Plato
    private lateinit var veggieSalad: Plato

    fun crearPlatos() {
        val tomate = ingredienteBootstrap.tomate
        val pollo = ingredienteBootstrap.pechugaDePollo
        val arroz = ingredienteBootstrap.arroz
        val leche = ingredienteBootstrap.leche
        val palta = ingredienteBootstrap.palta

        ensalada = Plato(
            nombre = "Ensalada Primavera",
            descripcion = "Nutritiva ensalada fresca",
            valorBase = 10.0,
            esdeAutor = true
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(pollo)
            agregarIngrediente(arroz)
        }

        arrozConLeche = Plato(
            nombre = "Arroz con Leche",
            descripcion = "Riquisimo postre de nuestras abuelas",
            valorBase = 7.5,
            esdeAutor = false
        ).apply {
            agregarIngrediente(leche)
            agregarIngrediente(arroz)
        }

        veggieSalad = Plato(
            nombre = "Veggie Salad",
            descripcion = "Una ensalada sin maltrato animal",
            valorBase = 18.0,
            esdeAutor = true
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(palta)
            agregarIngrediente(arroz)
        }

        platoRepositorio.apply {
            create(ensalada)
            create(arrozConLeche)
            create(veggieSalad)
        }
    }

    override fun afterPropertiesSet() {
        this.crearPlatos()
    }
}