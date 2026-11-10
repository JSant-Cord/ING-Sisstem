import java.io.*; // Importa clases de entrada/salida: File, FileWriter, PrintWriter, IOException
import java.util.Scanner; // Importa Scanner, aquí usado para leer el archivo de texto
 
/**
 * Programado por Santiago Córdoba Camacho.
 */
public class GestionReportes { // Clase que administra toda la lógica de los reportes (CRUD, pila y archivo)
    private Reporte[] reportesActivos; // Arreglo que guarda los reportes activos en memoria
    private int contador; // Cantidad de reportes que hay actualmente en el arreglo
    private PilaReportes pilaDeshacer; // Pila (estructura LIFO) donde se guardan los reportes eliminados para poder deshacer
    private static int MAX_REPORTES = 100; // Capacidad máxima del arreglo (constante compartida por la clase)
    private static String ARCHIVO = "reportes.txt"; // Nombre del archivo donde se guardan los datos
 
    public GestionReportes() { // Constructor: se ejecuta al crear el objeto
        this.reportesActivos = new Reporte[MAX_REPORTES]; // Crea el arreglo con espacio para 100 reportes
        this.contador = 0; // Inicia sin reportes
        this.pilaDeshacer = new PilaReportes(); // Crea la pila vacía
        cargarDesdeArchivo(); // Carga los reportes guardados previamente en reportes.txt
    } // Fin del constructor
 
    public String registrarReporte(String codigo, String codigoCat, String fecha, // Método de HU-01: registra un reporte nuevo
                                   double lat, double lon, String desc, boolean categoriaValida) { // Recibe todos los datos y si la categoría es válida
        if (buscarIndice(codigo) != -1) { // Si buscarIndice encuentra el código (no devuelve -1), ya existe
            return "¡RECHAZO: El código de reporte " + codigo + " ya existe en el sistema! (CA-01.2)"; // Rechaza por código duplicado
        } // Fin del if
        if (!categoriaValida) { // Si la categoría no es válida
            return "¡RECHAZO: La categoría " + codigoCat + " no existe en el catálogo de la Entidad B! (CA-01.3)"; // Rechaza por categoría inexistente
        } // Fin del if
        if (contador >= MAX_REPORTES) { // Si el arreglo ya está lleno
            return "¡IMPEDIMENTO: Capacidad máxima del arreglo alcanzada!"; // No se puede agregar más
        } // Fin del if
 
        Reporte nuevo = new Reporte(codigo, codigoCat, fecha, lat, lon, desc); // Crea el objeto Reporte con los datos recibidos
        reportesActivos[contador] = nuevo; // Lo guarda en la primera posición libre del arreglo
        contador++; // Aumenta la cantidad de reportes
        guardarEnArchivo(); // Actualiza reportes.txt con el cambio
        return "¡ÉXITO: Reporte " + codigo + " guardado correctamente en el arreglo activo! (CA-01.1)"; // Mensaje de éxito
    } // Fin de registrarReporte
 
    public String consultarReporte(String codigo) { // Método de HU-02: busca un reporte por código
        if (codigo == null || codigo.trim().isEmpty()) { // Si el código es nulo o solo tiene espacios en blanco
            return "¡AVISO: Debe ingresar un código de reporte para realizar la búsqueda! (CA-02.3)"; // Pide ingresar un código
        } // Fin del if
        int pos = buscarIndice(codigo); // Busca la posición del reporte en el arreglo
        if (pos == -1) { // Si devuelve -1, no se encontró
            return "¡AVISO: El reporte " + codigo + " no fue encontrado en la memoria activa! (CA-02.2)"; // Avisa que no existe
        } // Fin del if
        return "¡ÉXITO (CA-02.1)! Datos del reporte:\n" + reportesActivos[pos].toString(); // Devuelve los datos del reporte encontrado
    } // Fin de consultarReporte
 
    public String actualizarReporte(String codigoOriginal, String codigoIntentoCambio, // Método de HU-03: actualiza un reporte
                                     String nuevaDesc, double nuevaLat, double nuevaLon) { // Recibe los nuevos valores
        if (!codigoOriginal.equals(codigoIntentoCambio)) { // Si el código de confirmación es distinto al original
            return "¡IMPEDIMENTO: El código identificador del reporte es inmutable! (CA-03.3)"; // El código no se puede cambiar
        } // Fin del if
        if (nuevaDesc == null || nuevaDesc.trim().isEmpty()) { // Si la nueva descripción está vacía
            return "¡RECHAZO: La descripción del incidente no puede estar vacía! (CA-03.2)"; // Rechaza la actualización
        } // Fin del if
        int pos = buscarIndice(codigoOriginal); // Busca la posición del reporte
        if (pos == -1) { // Si no existe
            return "¡AVISO: El reporte a actualizar no existe!"; // Avisa que no se encontró
        } // Fin del if
 
        reportesActivos[pos].setDescripcion(nuevaDesc); // Cambia la descripción del reporte
        reportesActivos[pos].setLatitud(nuevaLat); // Cambia la latitud
        reportesActivos[pos].setLongitud(nuevaLon); // Cambia la longitud
        guardarEnArchivo(); // Guarda los cambios en el archivo
        return "¡ÉXITO: Reporte " + codigoOriginal + " actualizado en memoria! (CA-03.1)"; // Mensaje de éxito
    } // Fin de actualizarReporte
 
    public String eliminarReporte(String codigo) { // Método de HU-04: elimina un reporte
        if (contador == 0) { // Si no hay ningún reporte
            return "¡RECHAZO: El arreglo de reportes activos está totalmente vacío! (CA-04.3)"; // No hay nada que eliminar
        } // Fin del if
        int pos = buscarIndice(codigo); // Busca la posición del reporte
        if (pos == -1) { // Si no existe
            return "¡IMPEDIMENTO: El reporte " + codigo + " no existe en los registros activos! (CA-04.2)"; // Avisa que no existe
        } // Fin del if
 
        // 1. Apilar Deshacer
        Reporte eliminado = reportesActivos[pos]; // Guarda una referencia al reporte que se va a eliminar
        pilaDeshacer.apilar(eliminado); // Lo mete en la pila para poder recuperarlo después
 
        // izquierda 
        for (int i = pos; i < contador - 1; i++) { // Recorre desde la posición eliminada hasta el penúltimo elemento
            reportesActivos[i] = reportesActivos[i + 1]; // Mueve cada elemento una posición a la izquierda para tapar el hueco
        } // Fin del for
        reportesActivos[contador - 1] = null; // La última posición queda duplicada, así que se limpia con null
        contador--; // Disminuye la cantidad de reportes
 
        guardarEnArchivo(); // Actualiza el archivo
        return "¡ÉXITO: Reporte " + codigo + " eliminado del arreglo (desplazado a la izquierda) y apilado en la Pila! (CA-04.1)"; // Mensaje de éxito
    } // Fin de eliminarReporte
 
    public void listarReportes() { // Método de HU-05: lista todos los reportes
        if (contador == 0) { // Si no hay reportes
            System.out.println("¡AVISO: No hay reportes activos registrados para listar! (CA-05.2)"); // Avisa que está vacío
            return; // Termina el método
        } // Fin del if
 
        System.out.println("\n=== LISTADO GENERAL DE REPORTES ACTIVOS (CA-05.1) ==="); // Título del listado
        int totalHumo = 0, totalFoco = 0, totalQuema = 0, totalIncendio = 0; // Contadores por categoría, todos en 0
 
        for (int i = 0; i < contador; i++) { // Recorre cada reporte activo
            Reporte r = reportesActivos[i]; // Toma el reporte de la posición i
            System.out.println("[" + (i + 1) + "] " + r.toString()); // Lo imprime con número de lista (i+1 para empezar en 1)
 
            if (r.getCodigo_categoria().equalsIgnoreCase("CAT-01")) totalHumo++; // Si es CAT-01 suma a humo
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-02")) totalFoco++; // Si es CAT-02 suma a foco de fuego
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-03")) totalQuema++; // Si es CAT-03 suma a quema
            else if (r.getCodigo_categoria().equalsIgnoreCase("CAT-04")) totalIncendio++; // Si es CAT-04 suma a incendio
        } // Fin del for
 
        System.out.println("-----------------------------------------------------"); // Línea separadora
        System.out.println("TOTALES CONSOLIDADOS:"); // Encabezado de totales
        System.out.println("Total Alertas: " + contador); // Muestra el total de reportes
        System.out.println("Humo (CAT-01): " + totalHumo + " | Foco Fuego (CAT-02): " + totalFoco + // Muestra el total por cada categoría
                           " | Quema (CAT-03): " + totalQuema + " | Incendio (CAT-04): " + totalIncendio); // Continuación de la línea anterior
    } // Fin de listarReportes
 
    public String deshacerUltimaEliminacion() { // Método de HU-06: recupera el último reporte eliminado
        if (pilaDeshacer.estaVacia()) { // Si la pila no tiene elementos
            return "¡IMPEDIMENTO: La pila de eliminaciones está vacía. No hay nada que deshacer! (CA-06.2)"; // No hay nada que recuperar
        } // Fin del if
        if (contador >= MAX_REPORTES) { // Si el arreglo está lleno
            return "¡IMPEDIMENTO: No hay espacio en el arreglo activo para desapilar el reporte!"; // No hay espacio para restituirlo
        } // Fin del if
 
        // Desapilar 
        Reporte desapilado = pilaDeshacer.desapilar(); // Saca el último reporte que entró a la pila (LIFO)
        reportesActivos[contador] = desapilado; // Lo coloca al final del arreglo
        contador++; // Aumenta la cantidad de reportes
 
        guardarEnArchivo(); // Actualiza el archivo
        return "¡ÉXITO: Reporte " + desapilado.getCodigo_reporte() + " recuperado de la Pila y restituido al arreglo activo! (CA-06.1)"; // Mensaje de éxito
    } // Fin de deshacerUltimaEliminacion
 
    // búsqueda
    private int buscarIndice(String codigo) { // Método auxiliar privado: devuelve la posición de un código en el arreglo
        for (int i = 0; i < contador; i++) { // Recorre los reportes activos (búsqueda lineal)
            if (reportesActivos[i].getCodigo_reporte().equalsIgnoreCase(codigo)) { // Compara el código ignorando mayúsculas/minúsculas
                return i; // Si coincide, devuelve la posición
            } // Fin del if
        } // Fin del for
        return -1; // Si no encontró nada, devuelve -1
    } // Fin de buscarIndice
 
    // reportes.txt
    private void guardarEnArchivo() { // Escribe todos los reportes activos en el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) { // Abre el archivo para escribir (lo sobrescribe); try-with-resources lo cierra solo
            for (int i = 0; i < contador; i++) { // Recorre los reportes activos
                writer.println(reportesActivos[i].aFormatoArchivo()); // Escribe cada reporte en una línea con su formato de archivo
            } // Fin del for
        } catch (IOException e) { // Si ocurre un error de lectura/escritura
            System.err.println("Error al guardar en el archivo reportes.txt: " + e.getMessage()); // Muestra el error en la salida de errores
        } // Fin del try-catch
    } // Fin de guardarEnArchivo
 
    // reportes.txt
    private void cargarDesdeArchivo() { // Lee reportes.txt y llena el arreglo al iniciar
        File f = new File(ARCHIVO); // Crea un objeto File que representa el archivo
        if (!f.exists()) return; // Si el archivo no existe (primera ejecución), no hace nada
 
        try (Scanner scanner = new Scanner(f)) { // Abre el archivo para leerlo con Scanner (se cierra automáticamente)
            while (scanner.hasNextLine() && contador < MAX_REPORTES) { // Mientras haya líneas y quepa en el arreglo
                String linea = scanner.nextLine().trim(); // Lee una línea y quita espacios al inicio y final
                if (!linea.isEmpty()) { // Ignora las líneas vacías
                    String[] partes = linea.split(";"); // Separa la línea en campos usando ";" como separador
                    if (partes.length == 6) { // Verifica que tenga los 6 campos esperados
                        Reporte r = new Reporte(partes[0], partes[1], partes[2], // Crea un Reporte con código, categoría y fecha
                                                Double.parseDouble(partes[3]), // Convierte la latitud de texto a double
                                                Double.parseDouble(partes[4]), partes[5]); // Convierte la longitud y toma la descripción
                        reportesActivos[contador] = r; // Lo guarda en el arreglo
                        contador++; // Aumenta la cantidad de reportes
                    } // Fin del if de 6 campos
                } // Fin del if de línea no vacía
            } // Fin del while
        } catch (Exception e) { // Si algo falla (archivo dañado, número mal escrito, etc.)
            System.err.println("Error al cargar el archivo reportes.txt: " + e.getMessage()); // Muestra el error
        } 
    } 
} 
