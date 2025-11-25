package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.EnumEstadosPedido
import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.UsuarioCombinadoStrategy
import ar.edu.unsam.algo3.UsuarioImpacienteStrategy
import ar.edu.unsam.algo3.UsuarioMarketingStrategy
import ar.edu.unsam.algo3.UsuarioVeganoStrategy
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.LocalRepositorio
import ar.edu.unsam.algo3.repositorios.PedidoRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.uqbar.geodds.Point
import java.time.LocalDate
import java.time.LocalTime
import ar.edu.unsam.algo3.utils.HashUtils

@Service
class AppBootstrap(
    val ingredienteRepositorio: IngredienteRepositorio,
    val platoRepositorio: PlatoRepositorio,
    val pedidoRepositorio: PedidoRepositorio,
    val usuarioRepositorio: UsuarioRepositorio,
    val localRepositorio: LocalRepositorio
    ): InitializingBean {

    private lateinit var tomate: Ingrediente
    private lateinit var pechugaDePollo: Ingrediente
    private lateinit var arroz: Ingrediente
    private lateinit var leche: Ingrediente
    private lateinit var palta: Ingrediente
    private lateinit var queso: Ingrediente
    private lateinit var medallonDeCarne: Ingrediente
    private lateinit var lechuga: Ingrediente
    private lateinit var chocolate: Ingrediente
    private lateinit var glaseado: Ingrediente
    private lateinit var bizcocho: Ingrediente
    private lateinit var manteca: Ingrediente
    private lateinit var aji: Ingrediente

    fun crearIngredientes() {
        ingredienteRepositorio.clearInit()

        tomate = Ingrediente(
            "Tomate",
            0.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        pechugaDePollo = Ingrediente(
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
        queso = Ingrediente(
            "Queso",
            2.00,
            EnumGrupoAlimenticio.LACTEOS,
            true
        )
        medallonDeCarne = Ingrediente(
            "Medallón de carne",
            3.50,
            EnumGrupoAlimenticio.PROTEINAS,
            true
        )
        lechuga = Ingrediente(
            "Lechuga",
            1.00,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        chocolate = Ingrediente(
            "Chocolate",
            3.00,
            EnumGrupoAlimenticio.AZUCARES_Y_DULCES,
            false
        )
        glaseado = Ingrediente(
            "Glaseado",
            1.25,
            EnumGrupoAlimenticio.AZUCARES_Y_DULCES,
            false
        )
        bizcocho = Ingrediente(
            "Bizcocho",
            4.00,
            EnumGrupoAlimenticio.GRASAS_Y_ACEITES,
            true
        )
        manteca = Ingrediente(
            "Manteca",
            1.00,
            EnumGrupoAlimenticio.GRASAS_Y_ACEITES,
            true
        )
        aji = Ingrediente(
            "Aji picante",
            3.5,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        ingredienteRepositorio.apply {
            create(tomate)
            create(pechugaDePollo)
            create(arroz)
            create(leche)
            create(palta)
            create(queso)
            create(medallonDeCarne)
            create(lechuga)
            create(chocolate)
            create(glaseado)
            create(bizcocho)
            create(manteca)
            create(aji)
        }
    }

    private lateinit var local1: Local
    private lateinit var local2: Local
    private lateinit var localMoe: Local

    fun crearLocales() {
        localRepositorio.clearInit()

        localMoe = Local(
            "Taberna de Moe",
            Direccion("Av. Siempre Viva", 742, Point(-34.58, -58.542)),
            "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
            10.0,
            5.0,
            usuario = "local1",
            password = HashUtils.hash53("local1")
        ).apply {
            agregarMedioDePago(MedioDePago.EFECTIVO)
            agregarMedioDePago(MedioDePago.TARJETA)
            agregarMedioDePago(MedioDePago.QR)
            agregarRecargo(MedioDePago.EFECTIVO, 0.0)
            agregarRecargo(MedioDePago.TARJETA, 0.05)
            agregarRecargo(MedioDePago.QR, 0.05)
        }
        local1 = Local(
            "Local Plato 1 y Plato 2",
            direccion2,
            "https://www.clarin.com/img/2018/01/30/BkD3hG0rG_1256x620__1.jpg",
            3.0,
            3.0,
            usuario = "local2",
            password = HashUtils.hash53("local2")
        ).apply {
            agregarMedioDePago(MedioDePago.EFECTIVO)
            agregarMedioDePago(MedioDePago.TARJETA)
            agregarRecargo(MedioDePago.EFECTIVO, 0.0)
            agregarRecargo(MedioDePago.TARJETA, 0.05)
        }
        local2 = Local(
            "Local Plato 3",
            Direccion("Calle Verdadera", 456, Point(-34.58, -58.58)),
            "https://www.clarin.com/img/2018/01/30/rySp2GArM_1256x620__1.jpg",
            1.5,
            1.0,
            usuario = "local3",
            password = HashUtils.hash53("local3")
        ).apply {
            agregarMedioDePago(MedioDePago.EFECTIVO)
            agregarMedioDePago(MedioDePago.QR)
            agregarRecargo(MedioDePago.EFECTIVO, 0.0)
            agregarRecargo(MedioDePago.QR, 0.05)
        }

        localRepositorio.apply{
            create(localMoe)
            create(local1)
            create(local2)
        }
    }

    private lateinit var alitasPicantes: Plato
    private lateinit var arrozConLeche: Plato
    private lateinit var veggieSalad: Plato
    private lateinit var pizzaVegetariana: Plato
    private lateinit var hamburguesaConQueso: Plato
    private lateinit var pastelDeChocolate: Plato
    private lateinit var deLaCasa: Plato

    fun crearPlatos() {
        platoRepositorio.clearInit()

         deLaCasa = Plato(
             nombre = "Especial de la Casa",
             descripcion = "Primer plato realizado por el local",
             imagenNombre = "pescado-papas-fritas.png",
             valorBase = 16.75,
             esdeAutor = true,
             local = local1,
             popular = true
         ).apply {
             agregarIngrediente(pechugaDePollo)
             fechaLanzamiento= LocalDate.of(2024,10,20)
         }

         alitasPicantes = Plato(
            nombre = "Alitas de pollo picantes",
            descripcion = "Picantes alitas de pollo estilo mexicano",
            imagenNombre = "alitas-picantes.png",
            valorBase = 10.0,
            esdeAutor = true,
            local = local1,
            popular = true

         ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(pechugaDePollo)
            agregarIngrediente(aji)
        }

         arrozConLeche = Plato(
            nombre = "Arroz con Leche",
            descripcion = "Riquisimo postre de nuestras abuelas",
            imagenNombre = "arroz-con-leche.png",
            valorBase = 7.5,
            esdeAutor = false,
            local = local1,
            popular = false
         ).apply {
            agregarIngrediente(leche)
            agregarIngrediente(arroz)
        }

         veggieSalad = Plato(
            nombre = "Veggie Salad",
            descripcion = "Una ensalada sin maltrato animal",
            imagenNombre = "ensalada-huerta.png",
            valorBase = 18.0,
            esdeAutor = true,
            local = local1,
            popular = false
         ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(palta)
            agregarIngrediente(lechuga)
             agregarIngrediente(queso)
        }

        pizzaVegetariana = Plato(
            nombre = "Pizza Vegetariana",
            descripcion = "Pizza vegetariana con ingredientes variados",
            imagenNombre = "pizza-vegetariana.png",
            valorBase = 14.25,
            esdeAutor = false, 
            local = local2,
            popular = true
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(queso)
        }

        hamburguesaConQueso = Plato(
            nombre = "Hamburguesa con Queso",
            descripcion = "Hamburguesa clásica con queso y papas fritas",
            imagenNombre = "hamburguesa-con-queso.png",
            valorBase = 10.50,
            esdeAutor = false,
            local = local2,
            popular = false
        ).apply {
            agregarIngrediente(medallonDeCarne)
            agregarIngrediente(queso)
            agregarIngrediente(tomate)
            agregarIngrediente(lechuga)
        }

        pastelDeChocolate = Plato(
            nombre = "Pastel de Chocolate",
            descripcion = "Pastel de chocolate rico con glaseado",
            imagenNombre = "pastel-chocolate.png",
            valorBase = 6.50,
            esdeAutor = true,
            local = localMoe,
            popular = true
        ).apply {
            agregarIngrediente(bizcocho)
            agregarIngrediente(chocolate)
            agregarIngrediente(glaseado)
        }

        platoRepositorio.apply {
            create(deLaCasa)
            create(alitasPicantes)
            create(arrozConLeche)
            create(veggieSalad)
            create(pizzaVegetariana)
            create(hamburguesaConQueso)
            create(pastelDeChocolate)
        }
    }

    private lateinit var sofia: Usuario
    private lateinit var micaela: Usuario
    private lateinit var jose: Usuario
    private lateinit var miguel: Usuario

    val direccion1 = Direccion("Av. Siempre Viva", 555, Point(-34.58, -58.53))
    val direccion2 = Direccion("Calle Falsa", 123, Point(-34.58, -58.53))
    val direccion3 = Direccion("Cucha Cucha", 45, Point(-34.58, -58.53))
    val direccion4 = Direccion("Av. Rigoleau", 333, Point(-34.58, -58.53))

    fun crearUsuarios() {
        usuarioRepositorio.clearInit()

        val criterioCombinado = UsuarioCombinadoStrategy().apply {
            agregarUsuarios(UsuarioVeganoStrategy())
            agregarUsuarios(UsuarioMarketingStrategy().apply {
                agregarTexto("vegano")
                agregarTexto("sin carne")
                agregarTexto("palta")
            })
        }

        sofia = Usuario(
            nombre = "Sofía",
            apellido = "Miller",
            usuario = "smiller2005",
            password = HashUtils.hash53("123"),
            direccion = direccion1,
            distanciaMaximaCercana = 6.0,
            tipoDeUsuario = criterioCombinado
        ).apply {
            agregarPreferido(tomate)
            agregarPreferido(palta)
            agregarProhibido(medallonDeCarne)
            agregarProhibido(leche)
        }

        micaela = Usuario(
            nombre = "Micaela",
            apellido = "Moreno",
            usuario = "mmoreno2005",
            password = HashUtils.hash53("123"),
            direccion = direccion2,
            distanciaMaximaCercana = 3.0,
            tipoDeUsuario = UsuarioImpacienteStrategy()
        )

        jose = Usuario(
            nombre = "Jose",
            apellido = "Gomez",
            usuario = "jgomez1998",
            password = HashUtils.hash53("123"),
            direccion = direccion3
        )

        miguel = Usuario(
            nombre = "Miguel",
            apellido = "Manso",
            usuario = "mmanso2002",
            password = HashUtils.hash53("123"),
            direccion = direccion4
        )

        usuarioRepositorio.apply{
            create(sofia)
            create(micaela)
            create(jose)
            create(miguel)
        }
    }

    private lateinit var pedido1: Pedido
    private lateinit var pedido2: Pedido
    private lateinit var pedido3: Pedido
    private lateinit var pedido4: Pedido
    private lateinit var pedido5: Pedido
    private lateinit var pedido6: Pedido
    private lateinit var pedido7: Pedido

    fun crearPedidos() {
         pedidoRepositorio.clearInit()

        pedido1 = Pedido(
            cliente = sofia,
            local = local1,
            estadoDelPedido = EnumEstadosPedido.PENDIENTE,
            medioDePago = MedioDePago.TARJETA,
            horarioPedido = LocalTime.of(12,30)
        ).apply {
            agregarPlatoAlPedido(alitasPicantes)
            agregarPlatoAlPedido(arrozConLeche)
        }
        pedido2 = Pedido(
            cliente = micaela,
            local = local2,
            estadoDelPedido = EnumEstadosPedido.ENTREGADO,
            medioDePago = MedioDePago.EFECTIVO,
            horarioPedido = LocalTime.of(15,45)
        ).apply {
            agregarPlatoAlPedido(hamburguesaConQueso)
        }

        pedido3 = Pedido(
            cliente = jose,
            local = local1,
            estadoDelPedido = EnumEstadosPedido.CANCELADO,
            medioDePago = MedioDePago.QR,
            horarioPedido = LocalTime.of(12,0)
        ).apply{
            agregarPlatoAlPedido(veggieSalad)
        }

        pedido4 = Pedido(
            cliente = miguel,
            local = local2,
            estadoDelPedido = EnumEstadosPedido.ENTREGADO,
            medioDePago = MedioDePago.TARJETA,
            horarioPedido = LocalTime.of(13,0)
        ).apply{
            agregarPlatoAlPedido(pizzaVegetariana)
            agregarPlatoAlPedido(hamburguesaConQueso)
        }

        pedido5 = Pedido(
            cliente = micaela,
            local = local1,
            estadoDelPedido = EnumEstadosPedido.PENDIENTE,
            medioDePago = MedioDePago.QR,
            horarioPedido = LocalTime.of(15,30)
        ).apply{
            agregarPlatoAlPedido(veggieSalad)
            agregarPlatoAlPedido(alitasPicantes)
            agregarPlatoAlPedido(arrozConLeche)
        }

        pedido6 = Pedido(
            cliente = sofia,
            local = local1,
            estadoDelPedido = EnumEstadosPedido.ENTREGADO,
            medioDePago = MedioDePago.QR,
            horarioPedido = LocalTime.of(14,43)
        ).apply{
            agregarPlatoAlPedido(veggieSalad)
            agregarPlatoAlPedido(deLaCasa)
            agregarPlatoAlPedido(arrozConLeche)
        }

        pedido7 = Pedido(
            cliente = jose,
            local = localMoe,
            estadoDelPedido = EnumEstadosPedido.PREPARADO,
            medioDePago = MedioDePago.QR,
            horarioPedido = LocalTime.of(14,43)
        ).apply{
            agregarPlatoAlPedido(pastelDeChocolate)
        }

        pedidoRepositorio.apply{
            create(pedido1)
            create(pedido2)
            create(pedido3)
            create(pedido4)
            create(pedido5)
            create(pedido6)
            create(pedido7)
        }
    }

    fun configurarLocalesAPuntuar() {
        val fechaReciente = LocalDate.now().minusDays(3)
        sofia.agregarLocalAPuntuar(local1, fechaReciente)
        sofia.agregarLocalAPuntuar(localMoe, fechaReciente)
        micaela.agregarLocalAPuntuar(local2, fechaReciente)
        miguel.agregarLocalAPuntuar(local2, LocalDate.now().minusDays(5))
    }

    override fun afterPropertiesSet() {
        this.crearIngredientes()
        this.crearLocales()
        this.crearPlatos()
        this.crearUsuarios()
        this.crearPedidos()
        this.configurarLocalesAPuntuar()
    }
}