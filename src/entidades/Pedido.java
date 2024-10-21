/*
 Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package entidades;

import java.util.List;

public class Pedido {
    private int id;
    private int clienteId;
    private List<Plato> platos;
    private double total;
    private String estado;

    public Pedido(int id, int clienteId, List<Plato> platos, double total, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.platos = platos;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(clienteId).append(",");
        for (Plato plato : platos) {
            sb.append(plato.getId()).append(";");
        }
        sb.append(",").append(total).append(",").append(estado);
        return sb.toString();
    }
}
