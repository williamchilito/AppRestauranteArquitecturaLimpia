/*
 Aplicacion Restaurante -- ARQUITECTURA LIMPIA
 */

package aplicacionrestaurante;

import adaptadores.ControladorPedido;
import adaptadores.RepositorioPedidoArchivo;
import casosdeuso.RealizarPedido;
import entidades.Pedido;
import entidades.Plato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AplicacionRestaurante extends JFrame {
    private ControladorPedido controlador;
    private JTextField clienteIdField;
    private JTextArea platosIdArea;
    private JTextArea resultadoArea;
    private JTextArea menuArea;

    public AplicacionRestaurante() {
        // Configuración de la ventana
        setTitle("SISTEMA DE PEDIDOS - Restaurante");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicialización de componentes
        RepositorioPedidoArchivo repositorio = new RepositorioPedidoArchivo();
        RealizarPedido realizarPedido = new RealizarPedido(repositorio);
        controlador = new ControladorPedido(realizarPedido);

        // Panel principal (dividido en dos)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        // Panel izquierdo (menú)
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        menuArea = new JTextArea(20, 20);
        menuArea.setEditable(false);
        panelIzquierdo.add(new JLabel("  MENU DEL RESTAURANTE"), BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(menuArea), BorderLayout.CENTER);
        splitPane.setLeftComponent(panelIzquierdo);

        // Panel derecho (formulario y resultados)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        splitPane.setRightComponent(panelDerecho);
        
        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2));
        panelEntrada.add(new JLabel("   ID del Cliente:"));
        clienteIdField = new JTextField();
        panelEntrada.add(clienteIdField);
        panelEntrada.add(new JLabel("   IDs de Platos (separados por coma):"));
        platosIdArea = new JTextArea(3, 20);
        panelEntrada.add(new JScrollPane(platosIdArea));

        // Botón de realizar pedido
        JButton realizarPedidoButton = new JButton("REALIZAR PEDIDO");
        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPedido();
            }
        });
        panelEntrada.add(realizarPedidoButton);

        panelDerecho.add(panelEntrada, BorderLayout.NORTH);

        // Área de resultado
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        panelDerecho.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        // Mostrar menú
        mostrarMenu();
    }

    private void realizarPedido() {
        try {
            int clienteId = Integer.parseInt(clienteIdField.getText());
            String[] platosIdStr = platosIdArea.getText().split(",");
            List<Integer> platosIds = new ArrayList<>();
            for (String idStr : platosIdStr) {
                platosIds.add(Integer.parseInt(idStr.trim()));
            }

            Pedido pedidoRealizado = controlador.manejarPedido(clienteId, platosIds);

            if (pedidoRealizado != null) {
                StringBuilder resultado = new StringBuilder();
                resultado.append("\nPEDIDO REALIZADO CON ÉXITO\n");
                resultado.append("ID del pedido: ").append(pedidoRealizado.getId()).append("\n");
                resultado.append("Cliente ID: ").append(pedidoRealizado.getClienteId()).append("\n");
                resultado.append("Platos:\n");
                for (Plato plato : pedidoRealizado.getPlatos()) {
                    resultado.append("  - ").append(plato.getNombre()).append(" ($").append(plato.getPrecio()).append(")\n");
                }
                resultado.append("Total: $").append(pedidoRealizado.getTotal()).append("\n");
                resultado.append("Estado: ").append(pedidoRealizado.getEstado());

                resultadoArea.setText(resultado.toString());
            } else {
                resultadoArea.setText("No se pudo realizar el pedido.");
            }
        } catch (NumberFormatException e) {
            resultadoArea.setText("Error: Asegúrese de ingresar números válidos.");
        } catch (Exception e) {
            resultadoArea.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarMenu() {
        RepositorioPedidoArchivo repositorio = new RepositorioPedidoArchivo();
        StringBuilder menuStr = new StringBuilder();
        for (int i = 1; i <= 5; i++) {  // Asumimos que hay al menos 5 platos
            Plato plato = repositorio.obtenerPlatoPorId(i);
            if (plato != null) {
                menuStr.append("\n  " + plato.getId()).append(". ").append(plato.getNombre())
                       .append(" - $").append(plato.getPrecio()).append("\n");
            }
        }
        menuArea.setText(menuStr.toString()); // Muestra el menú en el panel izquierdo
    }

    
    public static void main(String[] args) {        
        new AplicacionRestaurante().setVisible(true);        
    }
}
