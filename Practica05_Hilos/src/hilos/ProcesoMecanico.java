package hilos;

import modelo.Mecanico;
import modelo.Vehiculo;
import java.util.concurrent.ThreadLocalRandom;

public class ProcesoMecanico implements Runnable {

    private Mecanico mecanico;

    public ProcesoMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(
                ThreadLocalRandom.current().nextInt(0, 3001)
            );

            synchronized (System.out) {
                System.out.println("\n==============================================");
                System.out.println("MECANICO: " + mecanico.getNombre());
                System.out.println("==============================================");

                int fallasLambda = 0;
                int fallasO2 = 0;
                int fallasN2O = 0;
                int fallasCO2 = 0;

                System.out.printf("%-12s %-12s %8s %8s %8s %8s%n",
                    "MARCA", "PLACA", "LAMBDA", "O2", "N2O", "CO2");

                for (Vehiculo v : mecanico.getVehiculos()) {
                    System.out.printf("%-12s %-12s %8.2f %8.2f %8.2f %8.2f%n",
                        v.getMarca(), v.getPlaca(),
                        v.getLambda(), v.getO2(),
                        v.getN2O(), v.getCo2());

                    if (v.getLambda() > 1) fallasLambda++;
                    if (v.getO2() >= 2) fallasO2++;
                    if (v.getN2O() >= 40) fallasN2O++;
                    if (v.getCo2() >= 15) fallasCO2++;
                }

                int total = mecanico.getVehiculos().size();

                double pLambda = porcentaje(fallasLambda, total);
                double pO2 = porcentaje(fallasO2, total);
                double pN2O = porcentaje(fallasN2O, total);
                double pCO2 = porcentaje(fallasCO2, total);

                System.out.println("----------------------------------------------");
                System.out.printf("Error Lambda: %.2f%% (Tolerancia: 70%%)%n", pLambda);
                System.out.printf("Error O2: %.2f%% (Tolerancia: 30%%)%n", pO2);
                System.out.printf("Error N2O: %.2f%% (Tolerancia: 80%%)%n", pN2O);
                System.out.printf("Error CO2: %.2f%% (Tolerancia: 50%%)%n", pCO2);

                boolean aprobado = pLambda <= 70
                    && pO2 <= 30
                    && pN2O <= 80
                    && pCO2 <= 50;

                System.out.println("----------------------------------------------");
                System.out.println("RESULTADO DEL LOTE: " +
                    (aprobado
                    ? "PASA EN PRIMERA REVISION"
                    : "SEGUNDA REVISION ASIGNADA"));
                System.out.println("==============================================");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Proceso interrumpido: " + mecanico.getNombre());
        }
    }

    private double porcentaje(int fallas, int total) {
        if (total == 0) return 0;
        return (fallas * 100.0) / total;
    }
}