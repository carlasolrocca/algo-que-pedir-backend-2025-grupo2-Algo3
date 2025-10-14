package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service

@Service
@DependsOn("ingredienteBootstrap")
class PlatoBootstrap(
    val platoRepositorio: PlatoRepositorio,
    val ingredienteRepositorio: IngredienteRepositorio
): InitializingBean {

    private var tomate: Ingrediente = ingredienteRepositorio.getByNombre("Tomate")
    private var leche: Ingrediente = ingredienteRepositorio.getByNombre("Leche")
    private var pollo: Ingrediente = ingredienteRepositorio.getByNombre("Pechuga de pollo")
    private var arroz: Ingrediente = ingredienteRepositorio.getByNombre("Arroz")
    private var palta: Ingrediente = ingredienteRepositorio.getByNombre("Palta")

    fun crearPlatos() {
        platoRepositorio.clearInit()
        val ensalada = Plato(
            nombre = "Ensalada Primavera",
            descripcion = "Nutritiva ensalada fresca",
            valorBase = 10.0,
            esdeAutor = true
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(pollo)
            agregarIngrediente(arroz)
        }

        val arrozConLeche = Plato(
            nombre = "Arroz con Leche",
            descripcion = "Riquisimo postre de nuestras abuelas",
            valorBase = 7.5,
            esdeAutor = false
        ).apply {
            agregarIngrediente(leche)
            agregarIngrediente(arroz)
        }

        val veggieSalad = Plato(
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