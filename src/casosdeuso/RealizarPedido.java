/*
 Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package casosdeuso;

import entidades.Pedido;
import entidades.Plato;
import entidades.Cliente;
import adaptadores.RepositorioPedidoArchivo;

import java.util.List;
import java.util.ArrayList;

public class RealizarPedido {
    private RepositorioPedidoArchivo repositorio;

    public RealizarPedido(RepositorioPedidoArchivo repositorio) {
        this.repositorio = repositorio;
    }

    public Pedido ejecutar(int clienteId, List<Integer> platosIds) throws Exception {
        Cliente cliente = repositorio.obtenerClientePorId(clienteId);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }

        List<Plato> platos = new ArrayList<>();
        for (int platoId : platosIds) {
            Plato plato = repositorio.obtenerPlatoPorId(platoId);
            if (plato != null) {
                platos.add(plato);
            }
        }

        double total = 0;
        for (Plato plato : platos) {
            total += plato.getPrecio();
        }

        Pedido nuevoPedido = new Pedido(
            repositorio.obtenerSiguienteIdPedido(),
            clienteId,
            platos,
            total,
            "pendiente"
        );

        return repositorio.guardarPedido(nuevoPedido);
    }
}
