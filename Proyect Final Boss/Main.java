import java.util.Scanner;

/**
 * Clase principal para la sustentación.
 * Estudiante: Santiago Córdoba Camacho
 */
public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GestionReportes gestion = new GestionReportes();
        int opcion = 0;

        do {
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA DE REPORTES DE INCENDIO (IS1)");
            System.out.println("==========================================");
            System.out.println("1. Registrar nuevo reporte (HU-01)");
            System.out.println("2. Consultar reporte por código (HU-02)");
            System.out.println("3. Actualizar reporte (HU-03)");
            System.out.println("4. Eliminar reporte (HU-04)");
            System.out.println("5. Listar todos los reportes (HU-05)");
            System.out.println("6. Deshacer última eliminación (HU-06)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código del reporte (ej: REP-001): ");
                    String cod = teclado.nextLine();
                    System.out.print("Ingrese código de categoría (ej: CAT-01): ");
                    String cat = teclado.nextLine();
                    System.out.print("Ingrese fecha (ej: 2026-11-06): ");
                    String fec = teclado.nextLine();
                    System.out.print("Ingrese latitud (ej: 6.2442): ");
                    double lat = Double.parseDouble(teclado.nextLine());
                    System.out.print("Ingrese longitud (ej: -75.5812): ");
                    double lon = Double.parseDouble(teclado.nextLine());
                    System.out.print("Ingrese descripción: ");
                    String desc = teclado.nextLine();

                    // Simulación de validación cruzada (CAT-99 no existe)
                    boolean catExiste = !cat.equalsIgnoreCase("CAT-99");
                    
                    String resReg = gestion.registrarReporte(cod, cat, fec, lat, lon, desc, catExiste);
                    System.out.println("\n>>> RESULTADO: " + resReg);
                    break;

                case 2:
                    System.out.print("Ingrese el código a consultar: ");
                    String codCons = teclado.nextLine();
                    System.out.println("\n>>> RESULTADO:\n" + gestion.consultarReporte(codCons));
                    break;

                case 3:
                    System.out.print("Ingrese el código del reporte a actualizar: ");
                    String codAct = teclado.nextLine();
                    System.out.print("Reingrese el código para confirmar (o diferente para probar inmutabilidad): ");
                    String codIntento = teclado.nextLine();
                    System.out.print("Ingrese nueva latitud: ");
                    double nLat = Double.parseDouble(teclado.nextLine());
                    System.out.print("Ingrese nueva longitud: ");
                    double nLon = Double.parseDouble(teclado.nextLine());
                    System.out.print("Ingrese nueva descripción: ");
                    String nDesc = teclado.nextLine();

                    System.out.println("\n>>> RESULTADO: " + gestion.actualizarReporte(codAct, codIntento, nDesc, nLat, nLon));
                    break;

                case 4:
                    System.out.print("Ingrese el código del reporte a eliminar: ");
                    String codElim = teclado.nextLine();
                    System.out.println("\n>>> RESULTADO: " + gestion.eliminarReporte(codElim));
                    break;

                case 5:
                    gestion.listarReportes();
                    break;

                case 6:
                    System.out.println("\n>>> RESULTADO: " + gestion.deshacerUltimaEliminacion());
                    break;

                case 7:
                    System.out.println("¡Programa finalizado correctamente!");
                    break;

                default:
                    System.out.println("¡Opción no válida! Intente de nuevo.");
            }

        } while (opcion != 7);
    }
}