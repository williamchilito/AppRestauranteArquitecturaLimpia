/*
 Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package adaptadores;

import casosdeuso.RealizarPedido;
import entidades.Pedido;

import java.util.List;

public class ControladorPedido {
    private RealizarPedido realizarPedido;

    public ControladorPedido(RealizarPedido realizarPedido) {
        this.realizarPedido = realizarPedido;
    }

    public Pedido manejarPedido(int clienteId, List<Integer> platosIds) {
        try {
            return realizarPedido.ejecutar(clienteId, platosIds);
        } catch (Exception e) {
            System.out.println("Error al realizar el pedido: " + e.getMessage());
            return null;
        }
    }
}
