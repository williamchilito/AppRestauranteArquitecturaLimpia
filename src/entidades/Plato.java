/*
Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package entidades;

public class Plato {
    private int id;
    private String nombre;
    private double precio;

    public Plato(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return id + "," + nombre + "," + precio;
    }
}
