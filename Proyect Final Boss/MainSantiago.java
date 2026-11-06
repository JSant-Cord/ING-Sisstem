/**
 * Clase principal para probar la ejecución y sustentar ante el profesor.
 */
public class MainSantiago {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  PROTOTIPO IS1 - SANTIAGO CÓRDOBA (4.º SEMESTRE)");
        System.out.println("  Entidad: Reporte de Incendio (Arreglo + Pila)");
        System.out.println("=================================================\n");

        GestionReportes gestion = new GestionReportes();

        // 1. Registro de Reportes (HU-01 / CA-01.1)
        System.out.println("--- 1. Registro de Reportes ---");
        System.out.println(gestion.registrarReporte("REP-001", "CAT-01", "2026-11-05", 6.2442, -75.5812, "Humo en Cerro Pan de Azucar", true));
        System.out.println(gestion.registrarReporte("REP-002", "CAT-03", "2026-11-05", 6.2510, -75.5700, "Quema de pastizal", true));

        // 2. Criterios de Falla (CA-01.2 y CA-01.3)
        System.out.println("\n--- 2. Validaciones de Falla ---");
        System.out.println(gestion.registrarReporte("REP-001", "CAT-01", "2026-11-05", 6.2442, -75.5812, "Intento duplicado", true));
        System.out.println(gestion.registrarReporte("REP-003", "CAT-99", "2026-11-05", 6.2600, -75.5600, "Categoria inexistente", false));

        // 3. Listado de Reportes (HU-05)
        gestion.listarReportes();

        // 4. Eliminación de Reporte (HU-04)
        System.out.println("\n--- 3. Eliminación de REP-001 ---");
        System.out.println(gestion.eliminarReporte("REP-001"));
        gestion.listarReportes();

        // 5. Deshacer Eliminación mediante Pila (HU-06)
        System.out.println("\n--- 4. Deshacer Eliminación (Restaurar REP-001) ---");
        System.out.println(gestion.deshacerUltimaEliminacion());
        gestion.listarReportes();
    }
}