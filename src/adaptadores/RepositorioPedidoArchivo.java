/*
 Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package adaptadores;

import entidades.Pedido;
import entidades.Plato;
import entidades.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPedidoArchivo {
    private static final String ARCHIVO_PLATOS = "platos.txt";
    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_PEDIDOS = "pedidos.txt";

    public Plato obtenerPlatoPorId(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PLATOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (Integer.parseInt(partes[0]) == id) {
                    return new Plato(Integer.parseInt(partes[0]), partes[1], Double.parseDouble(partes[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de platos: " + e.getMessage());
        }
        return null;
    }

    public Cliente obtenerClientePorId(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (Integer.parseInt(partes[0]) == id) {
                    return new Cliente(Integer.parseInt(partes[0]), partes[1], partes[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes: " + e.getMessage());
        }
        return null;
    }

    public Pedido guardarPedido(Pedido pedido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PEDIDOS, true))) {
            bw.write(pedido.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el pedido: " + e.getMessage());
            return null;
        }
        return pedido;
    }

    public int obtenerSiguienteIdPedido() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de pedidos: " + e.getMessage());
        }
        return maxId + 1;
    }
}
