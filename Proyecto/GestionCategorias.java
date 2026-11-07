import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Gestor principal de la Entidad Categoria (Juan Esteban Monsalve).
 * Maneja el Arreglo con Contador, la Pila de Deshacer y categorias.txt.
 */
public class GestionCategorias {
    private Categoria[] categoriasActivas;
    private int contador;
    private PilaCategorias pilaDeshacer;
    private static final int MAX_CATEGORIAS = 100;
    private static final String ARCHIVO = "categorias.txt";

    public GestionCategorias() {
        this.categoriasActivas = new Categoria[MAX_CATEGORIAS];
        this.contador = 0;
        this.pilaDeshacer = new PilaCategorias();
        cargarDesdeArchivo();
    }

    // 1. CREAR / REGISTRAR (HU-07)
    public String registrarCategoria(String codigo, String nombre, int gravedad, String desc) {
        if (buscarIndice(codigo) != -1) {
            return "¡Error! El código de categoría " + codigo + " ya existe (CA-07.2).";
        }
        if (gravedad < 1 || gravedad > 5) {
            return "¡Error! El nivel de gravedad debe estar entre 1 y 5 (CA-07.3).";
        }
        if (contador >= MAX_CATEGORIAS) {
            return "¡Error! Capacidad máxima de almacenamiento alcanzada.";
        }

        Categoria nueva = new Categoria(codigo, nombre, gravedad, desc, true);
        categoriasActivas[contador] = nueva;
        contador++;
        guardarEnArchivo();
        return "¡Éxito! Categoría " + codigo + " registrada correctamente.";
    }

    // 2. CONSULTAR POR CÓDIGO (HU-08 - Usado para validación cruzada)
    public Categoria buscarCategoria(String codigo) {
        int pos = buscarIndice(codigo);
        if (pos != -1 && categoriasActivas[pos].isEstado_activo()) {
            return categoriasActivas[pos];
        }
        return null;
    }

    public String consultarCategoria(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "¡Aviso! Debe escribir un código para consultar (CA-08.3).";
        }
        int pos = buscarIndice(codigo);
        if (pos == -1) {
            return "¡Aviso! La categoría " + codigo + " no fue encontrada (CA-08.2).";
        }
        return "Datos de la Categoría:\n" + categoriasActivas[pos].toString();
    }

    // 3. ACTUALIZAR (HU-09)
    public String actualizarCategoria(String codigoOriginal, String codigoIntento, 
                                       String nuevoNombre, int nuevaGravedad, String nuevaDesc) {
        if (!codigoOriginal.equalsIgnoreCase(codigoIntento)) {
            return "¡Error! El código de la categoría no se puede modificar (Inmutable / CA-09.3).";
        }
        if (nuevaGravedad < 1 || nuevaGravedad > 5) {
            return "¡Error! El nivel de gravedad debe estar entre 1 y 5.";
        }
        int pos = buscarIndice(codigoOriginal);
        if (pos == -1) {
            return "¡Aviso! La categoría a actualizar no existe.";
        }

        categoriasActivas[pos].setNombre_categoria(nuevoNombre);
        categoriasActivas[pos].setNivel_gravedad(nuevaGravedad);
        categoriasActivas[pos].setDescripcion(nuevaDesc);
        guardarEnArchivo();
        return "¡Éxito! Categoría " + codigoOriginal + " actualizada correctamente.";
    }

    // 4. ELIMINAR CON DESPLAZAMIENTO A LA IZQUIERDA Y VALIDACIÓN CRUZADA (HU-10)
    public String eliminarCategoria(String codigo, boolean tieneReportesAsociados) {
        if (contador == 0) {
            return "¡Error! No hay categorías en la lista para eliminar.";
        }
        int pos = buscarIndice(codigo);
        if (pos == -1) {
            return "¡Aviso! La categoría " + codigo + " no existe.";
        }

        // Regla de Negocio RN-05 / CA-10.2: Validación cruzada con la Entidad A (Santiago)
        if (tieneReportesAsociados) {
            return "¡Rechazado! No se puede eliminar la categoría " + codigo + 
                   " porque tiene reportes activos vinculados en la Entidad A (RN-05 / CA-10.2).";
        }

        // Apilamos en la Pila propia de Juan Esteban
        pilaDeshacer.apilar(categoriasActivas[pos]);

        // Desplazamiento manual a la izquierda
        for (int i = pos; i < contador - 1; i++) {
            categoriasActivas[i] = categoriasActivas[i + 1];
        }
        categoriasActivas[contador - 1] = null;
        contador--;

        guardarEnArchivo();
        return "¡Éxito! Categoría " + codigo + " eliminada del arreglo y guardada en la pila de deshacer.";
    }

    // 5. LISTAR Y TOTALIZAR (HU-11)
    public void listarCategorias() {
        if (contador == 0) {
            System.out.println("¡Aviso! La lista de categorías está vacía.");
            return;
        }

        System.out.println("\n=== LISTADO DE CATEGORÍAS ACTIVAS ===");
        int totalGraves = 0; // Categorías con gravedad >= 4

        for (int i = 0; i < contador; i++) {
            Categoria c = categoriasActivas[i];
            System.out.println((i + 1) + ". " + c.toString());
            if (c.getNivel_gravedad() >= 4) {
                totalGraves++;
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("TOTALES: " + contador + " categorías activas | " + 
                           totalGraves + " clasificadas de alta severidad (Gravedad >= 4)");
    }

    // 6. DESHACER ÚLTIMA ELIMINACIÓN (HU-12)
    public String deshacerUltimaEliminacion() {
        if (pilaDeshacer.estaVacia()) {
            return "¡Aviso! La pila está vacía. No hay categorías para deshacer (CA-12.2).";
        }
        if (contador >= MAX_CATEGORIAS) {
            return "¡Error! No hay espacio en el arreglo para restaurar la categoría.";
        }

        Categoria recuperada = pilaDeshacer.desapilar();
        categoriasActivas[contador] = recuperada;
        contador++;

        guardarEnArchivo();
        return "¡Éxito! Categoría " + recuperada.getCodigo_categoria() + " restaurada desde la pila de deshacer.";
    }

    // Búsqueda secuencial
    private int buscarIndice(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (categoriasActivas[i].getCodigo_categoria().equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // Persistencia: Guardar en categorias.txt
    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (int i = 0; i < contador; i++) {
                pw.println(categoriasActivas[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // Persistencia: Leer desde categorias.txt
    private void cargarDesdeArchivo() {
        File f = new File(ARCHIVO);
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine() && contador < MAX_CATEGORIAS) {
                String linea = sc.nextLine().trim();
                if (!linea.isEmpty()) {
                    String[] p = linea.split(";");
                    if (p.length == 5) {
                        Categoria c = new Categoria(p[0], p[1], 
                                                    Integer.parseInt(p[2]), 
                                                    p[3], 
                                                    Boolean.parseBoolean(p[4]));
                        categoriasActivas[contador] = c;
                        contador++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo de categorías: " + e.getMessage());
        }
    }
}