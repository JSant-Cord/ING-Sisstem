import java.io.*;
import java.util.Scanner;

/**
 * Programado por Santiago Córdoba Camacho.
 */
public class GestionReportes {
    private Reporte[] reportesActivos;
    private int contador;
    private PilaReportes pilaDeshacer;
    private static int MAX_REPORTES = 100;
    private static String ARCHIVO = "reportes.txt";

    public GestionReportes() {
        this.reportesActivos = new Reporte[MAX_REPORTES];
        this.contador = 0;
        this.pilaDeshacer = new PilaReportes();
        cargarDesdeArchivo(); 
    }

    public String registrarReporte(String codigo, String codigoCat, String fecha, 
                                   double lat, double lon, String desc, boolean categoriaValida) {
        if (buscarIndice(codigo) != -1) {
            return "¡RECHAZO: El código de reporte " + codigo + " ya existe en el sistema! (CA-01.2)";
        }
        if (!categoriaValida) {
            return "¡RECHAZO: La categoría " + codigoCat + " no existe en el catálogo de la Entidad B! (CA-01.3)";
        }
        if (contador >= MAX_REPORTES) {
            return "¡IMPEDIMENTO: Capacidad máxima del arreglo alcanzada!";
        }

        Reporte nuevo = new Reporte(codigo, codigoCat, fecha, lat, lon, desc);
        reportesActivos[contador] = nuevo;
        contador++;
        guardarEnArchivo();
        return "¡ÉXITO: Reporte " + codigo + " guardado correctamente en el arreglo activo! (CA-01.1)";
    }

    public String consultarReporte(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "¡AVISO: Debe ingresar un código de reporte para realizar la búsqueda! (CA-02.3)";
        }
        int pos = buscarIndice(codigo);
        if (pos == -1) {
            return "¡AVISO: El reporte " + codigo + " no fue encontrado en la memoria activa! (CA-02.2)";
        }
        return "¡ÉXITO (CA-02.1)! Datos del reporte:\n" + reportesActivos[pos].toString();
    }

    public String actualizarReporte(String codigoOriginal, String codigoIntentoCambio, 
                                     String nuevaDesc, double nuevaLat, double nuevaLon) {
        if (!codigoOriginal.equals(codigoIntentoCambio)) {
            return "¡IMPEDIMENTO: El código identificador del reporte es inmutable! (CA-03.3)";
        }
        if (nuevaDesc == null || nuevaDesc.trim().isEmpty()) {
            return "¡RECHAZO: La descripción del incidente no puede estar vacía! (CA-03.2)";
        }
        int pos = buscarIndice(codigoOriginal);
        if (pos == -1) {
            return "¡AVISO: El reporte a actualizar no existe!";
        }

        reportesActivos[pos].setDescripcion(nuevaDesc);
        reportesActivos[pos].setLatitud(nuevaLat);
        reportesActivos[pos].setLongitud(nuevaLon);
        guardarEnArchivo();
        return "¡ÉXITO: Reporte " + codigoOriginal + " actualizado en memoria! (CA-03.1)";
    }

    public String eliminarReporte(String codigo) {
        if (contador == 0) {
            return "¡RECHAZO: El arreglo de reportes activos está totalmente vacío! (CA-04.3)";
        }
        int pos = buscarIndice(codigo);
        if (pos == -1) {
            return "¡IMPEDIMENTO: El reporte " + codigo + " no existe en los registros activos! (CA-04.2)";
        }

        // 1. Apilar Deshacer
        Reporte eliminado = reportesActivos[pos];
        pilaDeshacer.apilar(eliminado);

        // izquierda 
        for (int i = pos; i < contador - 1; i++) {
            reportesActivos[i] = reportesActivos[i + 1];
        }
        reportesActivos[contador - 1] = null;
        contador--;

        guardarEnArchivo();
        return "¡ÉXITO: Reporte " + codigo + " eliminado del arreglo (desplazado a la izquierda) y apilado en la Pila! (CA-04.1)";
    }

    public void listarReportes() {
        if (contador == 0) {
            System.out.println("¡AVISO: No hay reportes activos registrados para listar! (CA-05.2)");
            return;
        }

        System.out.println("\n=== LISTADO GENERAL DE REPORTES ACTIVOS (CA-05.1) ===");
        int totalHumo = 0, totalFoco = 0, totalQuema = 0, totalIncendio = 0;

        for (int i = 0; i < contador; i++) {
            Reporte r = reportesActivos[i];
            System.out.println("[" + (i + 1) + "] " + r.toString());

            // Conteo por categoría
            if (r.getCodigo_categoria().equalsIgnoreCase("CAT-01")) totalHumo++;
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-02")) totalFoco++;
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-03")) totalQuema++;
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-04")) totalIncendio++;
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("TOTALES CONSOLIDADOS:");
        System.out.println("Total Alertas: " + contador);
        System.out.println("Humo (CAT-01): " + totalHumo + " | Foco Fuego (CAT-02): " + totalFoco + 
                           " | Quema (CAT-03): " + totalQuema + " | Incendio (CAT-04): " + totalIncendio);
    }

    public String deshacerUltimaEliminacion() {
        if (pilaDeshacer.estaVacia()) {
            return "¡IMPEDIMENTO: La pila de eliminaciones está vacía. No hay nada que deshacer! (CA-06.2)";
        }
        if (contador >= MAX_REPORTES) {
            return "¡IMPEDIMENTO: No hay espacio en el arreglo activo para desapilar el reporte!";
        }

        // Desapilar 
        Reporte desapilado = pilaDeshacer.desapilar();
        reportesActivos[contador] = desapilado;
        contador++;

        guardarEnArchivo();
        return "¡ÉXITO: Reporte " + desapilado.getCodigo_reporte() + " recuperado de la Pila y restituido al arreglo activo! (CA-06.1)";
    }

    // búsqueda
    private int buscarIndice(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (reportesActivos[i].getCodigo_reporte().equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // reportes.txt
    private void guardarEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (int i = 0; i < contador; i++) {
                writer.println(reportesActivos[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo reportes.txt: " + e.getMessage());
        }
    }

    // reportes.txt
    private void cargarDesdeArchivo() {
        File f = new File(ARCHIVO);
        if (!f.exists()) return;

        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine() && contador < MAX_REPORTES) {
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty()) {
                    String[] partes = linea.split(";");
                    if (partes.length == 6) {
                        Reporte r = new Reporte(partes[0], partes[1], partes[2], 
                                                Double.parseDouble(partes[3]), 
                                                Double.parseDouble(partes[4]), partes[5]);
                        reportesActivos[contador] = r;
                        contador++;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo reportes.txt: " + e.getMessage());
        }
    }
}
