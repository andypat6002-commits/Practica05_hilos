package modelo;

public class Vehiculo {
    private String marca;
    private String placa;
    private double lambda;
    private double o2;
    private double n2o;
    private double co2;

    public Vehiculo(String marca, String placa,
            double lambda, double o2, double n2o, double co2) {
        this.marca = marca;
        this.placa = placa;
        this.lambda = lambda;
        this.o2 = o2;
        this.n2o = n2o;
        this.co2 = co2;
    }

    public String getMarca() { return marca; }
    public String getPlaca() { return placa; }
    public double getLambda() { return lambda; }
    public double getO2() { return o2; }
    public double getN2O() { return n2o; }
    public double getCo2() { return co2; }
}