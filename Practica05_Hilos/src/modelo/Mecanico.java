package modelo;

import java.util.ArrayList;

public class Mecanico {
    private String nombre;
    private ArrayList<Vehiculo> vehiculos;

    public Mecanico(String nombre) {
        this.nombre = nombre;
        this.vehiculos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }
}