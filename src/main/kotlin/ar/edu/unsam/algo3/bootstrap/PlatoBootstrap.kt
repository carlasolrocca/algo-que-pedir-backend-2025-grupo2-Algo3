package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class PlatoBootstrap(
    val platoRepositorio: PlatoRepositorio,
    val ingredienteRepositorio: IngredienteRepositorio
): InitializingBean {

    private lateinit var tomate: Ingrediente
    private lateinit var leche: Ingrediente
    private lateinit var pollo: Ingrediente
    private lateinit var arroz: Ingrediente
    private lateinit var palta: Ingrediente

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

    fun crearIngredientes(){
        tomate = Ingrediente(
            "Tomate",
            0.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        pollo = Ingrediente(
            "Pechuga de pollo",
            3.00,
            EnumGrupoAlimenticio.PROTEINAS,
            true
        )
        arroz = Ingrediente(
            "Arroz",
            1.00,
            EnumGrupoAlimenticio.CEREALES_Y_TUBERCULOS,
            false
        )
        leche = Ingrediente(
            "Leche",
            2.00,
            EnumGrupoAlimenticio.LACTEOS,
            true
        )
        palta = Ingrediente(
            "Palta",
            1.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        ingredienteRepositorio.apply {
            create(tomate)
            create(pollo)
            create(arroz)
            create(leche)
            create(palta)
        }
    }

    override fun afterPropertiesSet() {
        this.crearIngredientes() // DEBERIA LLAMAR A LOS PLATOS CREADOS, HACER CADENA, INCLUSO PEDIDO LLAMAR A ESTA FUN
        this.crearPlatos()
    }
}