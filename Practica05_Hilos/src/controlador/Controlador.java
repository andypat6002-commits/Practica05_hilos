package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import modelo.Mecanico;
import modelo.Vehiculo;
import hilos.ProcesoMecanico;
import vista.Vista;

public class Controlador {

    private ArrayList<Mecanico> mecanicos = new ArrayList<>();
    private Vista vista;

    public Controlador(Vista vista) {
        this.vista = vista;
    }

    public void ejecutar() {
        try {
            leerMecanicos();
            leerVehiculos();

            if (mecanicos.size() != 10) {
                vista.mostrarMensaje("Se necesitan exactamente 10 mecanicos.");
                return;
            }

            for (Mecanico m : mecanicos) {
                if (m.getVehiculos().size() != 5) {
                    vista.mostrarMensaje(
                        "Error: cada mecanico debe tener 5 vehiculos."
                    );
                    return;
                }
            }

            vista.mostrarMensaje("Datos cargados correctamente.");
            vista.mostrarMensaje("Iniciando los 10 hilos...\n");

            ArrayList<Thread> hilos = new ArrayList<>();

            for (Mecanico m : mecanicos) {
                Thread hilo = new Thread(new ProcesoMecanico(m));
                hilos.add(hilo);
                hilo.start();
            }

            for (Thread hilo : hilos) {
                hilo.join();
            }

            vista.mostrarMensaje("\nTodos los procesos finalizaron.");

        } catch (IOException e) {
            vista.mostrarMensaje("Error al leer archivos: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            vista.mostrarMensaje("Ejecucion interrumpida.");
        }
    }

    private void leerMecanicos() throws IOException {
        try (BufferedReader br =
                new BufferedReader(new FileReader("mecanicos.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    mecanicos.add(new Mecanico(linea.trim()));
                }
            }
        }
    }

    private void leerVehiculos() throws IOException {
        try (BufferedReader br =
                new BufferedReader(new FileReader("vehiculos.txt"))) {

            String linea;
            int indice = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(";");

                if (datos.length != 6) {
                    throw new IOException("Registro de vehiculo incorrecto: " + linea);
                }

                if (indice >= mecanicos.size() * 5) {
                    throw new IOException("Hay mas de 50 vehiculos.");
                }

                Vehiculo v = new Vehiculo(
                    datos[0].trim(),
                    datos[1].trim(),
                    Double.parseDouble(datos[2].trim()),
                    Double.parseDouble(datos[3].trim()),
                    Double.parseDouble(datos[4].trim()),
                    Double.parseDouble(datos[5].trim())
                );

                mecanicos.get(indice / 5).agregarVehiculo(v);
                indice++;
            }

            if (indice != 50) {
                throw new IOException("Se necesitan exactamente 50 vehiculos.");
            }
        }
    }
}