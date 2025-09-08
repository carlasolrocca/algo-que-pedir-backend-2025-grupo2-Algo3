package ar.edu.unsam.algo2

class GestorObserversPedido {
    val observers : MutableList<PedidoObserver> = mutableListOf()

    fun addObserver(observer: PedidoObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: PedidoObserver) {
        observers.remove(observer)
    }

    fun notificarPedidoRealizado(pedido: Pedido){
        observers.forEach { it.onPedidoRealizado(pedido) }
    }
}