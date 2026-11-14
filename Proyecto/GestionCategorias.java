
 // Importa la clase File para crear, buscar y verificar archivos.
import java.io.File;

// Importa FileWriter para escribir información dentro de un archivo.
import java.io.FileWriter;

// Importa IOException para controlar errores relacionados con la lectura y escritura de archivos.
import java.io.IOException;

// Importa PrintWriter para escribir texto dentro de un archivo de forma sencilla.
import java.io.PrintWriter;

// Importa Scanner para leer información desde archivos.
import java.util.Scanner;

/**
 * Gestor principal de la entidad Categoria.
 * Esta clase administra el registro, consulta, actualización y eliminación de categorías.
 * También maneja el arreglo, la pila de deshacer y el archivo categorias.txt.
 */
public class GestionCategorias {

    // Declara un arreglo de objetos Categoria donde se almacenan las categorías activas.
    private Categoria[] categoriasActivas;

    // Guarda la cantidad actual de categorías almacenadas en el arreglo.
    private int contador;

    // Declara una pila que almacena las categorías eliminadas para poder recuperarlas.
    private PilaCategorias pilaDeshacer;

    // Declara una constante que establece la cantidad máxima de categorías permitidas.
    private static final int MAX_CATEGORIAS = 100;

    // Declara una constante con el nombre del archivo donde se guardan las categorías.
    private static final String ARCHIVO = "categorias.txt";

    // Constructor de la clase GestionCategorias.
    // Se ejecuta automáticamente cuando se crea un objeto de esta clase.
    public GestionCategorias() {

        // Crea el arreglo con capacidad para almacenar 100 categorías.
        this.categoriasActivas = new Categoria[MAX_CATEGORIAS];

        // Inicializa el contador en cero porque todavía no se han contado las categorías.
        this.contador = 0;

        // Crea una nueva pila para almacenar las categorías eliminadas.
        this.pilaDeshacer = new PilaCategorias();

        // Llama al método que recupera las categorías guardadas anteriormente en el archivo.
        cargarDesdeArchivo();
    }

    // 1. CREAR / REGISTRAR (HU-07)
    // Método que permite registrar una nueva categoría.
    // Recibe el código, nombre, nivel de gravedad y descripción.
    public String registrarCategoria(String codigo, String nombre, int gravedad, String desc) {

        // Verifica si el código que se quiere registrar ya existe.
        // Si buscarIndice devuelve -1, significa que el código no está registrado.
        if (buscarIndice(codigo) != -1) {

            // Devuelve un mensaje de error si el código ya está registrado.
            return "¡Error! El código de categoría " + codigo + " ya existe (CA-07.2).";
        }

        // Verifica que el nivel de gravedad esté dentro del rango permitido.
        if (gravedad < 1 || gravedad > 5) {

            // Devuelve un mensaje si la gravedad es menor que 1 o mayor que 5.
            return "¡Error! El nivel de gravedad debe estar entre 1 y 5 (CA-07.3).";
        }

        // Verifica si el arreglo ya alcanzó su capacidad máxima.
        if (contador >= MAX_CATEGORIAS) {

            // Devuelve un mensaje indicando que no hay espacio para más categorías.
            return "¡Error! Capacidad máxima de almacenamiento alcanzada.";
        }

        // Crea un nuevo objeto Categoria con los datos recibidos.
        // El último parámetro true indica que la categoría se registra como activa.
        Categoria nueva = new Categoria(codigo, nombre, gravedad, desc, true);

        // Almacena la nueva categoría en la posición indicada por el contador.
        categoriasActivas[contador] = nueva;

        // Aumenta el contador en uno porque se agregó una nueva categoría.
        contador++;

        // Guarda los cambios realizados en el archivo categorias.txt.
        guardarEnArchivo();

        // Devuelve un mensaje confirmando que la categoría fue registrada correctamente.
        return "¡Éxito! Categoría " + codigo + " registrada correctamente.";
    }

    // 2. CONSULTAR POR CÓDIGO (HU-08)
    // Método que busca una categoría por su código y devuelve el objeto encontrado.
    public Categoria buscarCategoria(String codigo) {

        // Busca la posición de la categoría dentro del arreglo.
        int pos = buscarIndice(codigo);

        // Comprueba que la categoría exista y que su estado sea activo.
        if (pos != -1 && categoriasActivas[pos].isEstado_activo()) {

            // Devuelve el objeto Categoria encontrado.
            return categoriasActivas[pos];
        }

        // Devuelve null cuando la categoría no existe o no está activa.
        return null;
    }

    // Método que consulta una categoría y devuelve sus datos en forma de texto.
    public String consultarCategoria(String codigo) {

        // Comprueba que el código no sea null, que no esté vacío ni contenga solamente espacios.
        if (codigo == null || codigo.trim().isEmpty()) {

            // Devuelve un aviso indicando que se debe ingresar un código.
            return "¡Aviso! Debe escribir un código para consultar (CA-08.3).";
        }

        // Busca la posición de la categoría utilizando el código recibido.
        int pos = buscarIndice(codigo);

        // Comprueba si la búsqueda no encontró ninguna categoría.
        if (pos == -1) {

            // Devuelve un mensaje indicando que la categoría no fue encontrada.
            return "¡Aviso! La categoría " + codigo + " no fue encontrada (CA-08.2).";
        }

        // Devuelve los datos de la categoría utilizando el método toString().
        return "Datos de la Categoría:\n" + categoriasActivas[pos].toString();
    }

    // 3. ACTUALIZAR (HU-09)
    // Método que permite modificar el nombre, gravedad y descripción de una categoría.
    public String actualizarCategoria(String codigoOriginal, String codigoIntento,
                                       String nuevoNombre, int nuevaGravedad, String nuevaDesc) {

        // Compara el código original con el código que se intenta utilizar.
        // equalsIgnoreCase compara textos sin diferenciar mayúsculas y minúsculas.
        if (!codigoOriginal.equalsIgnoreCase(codigoIntento)) {

            // Devuelve un error porque el código de una categoría no se puede modificar.
            return "¡Error! El código de la categoría no se puede modificar (Inmutable / CA-09.3).";
        }

        // Verifica que el nuevo nivel de gravedad esté entre 1 y 5.
        if (nuevaGravedad < 1 || nuevaGravedad > 5) {

            // Devuelve un mensaje de error si el nivel de gravedad no es válido.
            return "¡Error! El nivel de gravedad debe estar entre 1 y 5.";
        }

        // Busca la posición de la categoría que se desea actualizar.
        int pos = buscarIndice(codigoOriginal);

        // Comprueba si la categoría no existe dentro del arreglo.
        if (pos == -1) {

            // Devuelve un aviso indicando que no se encontró la categoría.
            return "¡Aviso! La categoría a actualizar no existe.";
        }

        // Modifica el nombre de la categoría utilizando su método Setter.
        categoriasActivas[pos].setNombre_categoria(nuevoNombre);

        // Modifica el nivel de gravedad de la categoría.
        categoriasActivas[pos].setNivel_gravedad(nuevaGravedad);

        // Modifica la descripción de la categoría.
        categoriasActivas[pos].setDescripcion(nuevaDesc);

        // Guarda los cambios realizados en el archivo de texto.
        guardarEnArchivo();

        // Devuelve un mensaje confirmando que la actualización fue exitosa.
        return "¡Éxito! Categoría " + codigoOriginal + " actualizada correctamente.";
    }

    // 4. ELIMINAR CON DESPLAZAMIENTO A LA IZQUIERDA Y VALIDACIÓN CRUZADA (HU-10)
    // Método que elimina una categoría del arreglo.
    // Recibe el código de la categoría y un boolean que indica si tiene reportes asociados.
    public String eliminarCategoria(String codigo, boolean tieneReportesAsociados) {

        // Verifica si el arreglo está vacío.
        if (contador == 0) {

            // Devuelve un mensaje indicando que no hay categorías para eliminar.
            return "¡Error! No hay categorías en la lista para eliminar.";
        }

        // Busca la posición de la categoría que se desea eliminar.
        int pos = buscarIndice(codigo);

        // Comprueba si la categoría no existe.
        if (pos == -1) {

            // Devuelve un aviso indicando que no se encontró la categoría.
            return "¡Aviso! La categoría " + codigo + " no existe.";
        }

        // Regla de Negocio RN-05 / CA-10.2.
        // Comprueba si la categoría tiene reportes activos asociados.
        if (tieneReportesAsociados) {

            // Rechaza la eliminación para evitar borrar una categoría que tiene reportes relacionados.
            return "¡Rechazado! No se puede eliminar la categoría " + codigo +
                   " porque tiene reportes activos vinculados en la Entidad A (RN-05 / CA-10.2).";
        }

        // Guarda la categoría que se va a eliminar dentro de la pila.
        // Esto permite recuperarla posteriormente utilizando la opción deshacer.
        pilaDeshacer.apilar(categoriasActivas[pos]);

        // Desplazamiento manual a la izquierda.
        // El ciclo comienza en la posición que se va a eliminar.
        // Continúa mientras existan elementos a la derecha.
        for (int i = pos; i < contador - 1; i++) {

            // Copia el siguiente elemento en la posición actual.
            // De esta manera se llena el espacio que dejó la categoría eliminada.
            categoriasActivas[i] = categoriasActivas[i + 1];
        }

        // Coloca null en la última posición ocupada porque el elemento fue desplazado.
        categoriasActivas[contador - 1] = null;

        // Disminuye el contador porque ahora existe una categoría menos.
        contador--;

        // Guarda el arreglo actualizado en el archivo categorias.txt.
        guardarEnArchivo();

        // Devuelve un mensaje confirmando la eliminación y el almacenamiento en la pila.
        return "¡Éxito! Categoría " + codigo + " eliminada del arreglo y guardada en la pila de deshacer.";
    }

    // 5. LISTAR Y TOTALIZAR (HU-11)
    // Método que muestra todas las categorías almacenadas y cuenta las de gravedad alta.
    public void listarCategorias() {

        // Comprueba si no hay categorías registradas.
        if (contador == 0) {

            // Muestra un mensaje indicando que la lista está vacía.
            System.out.println("¡Aviso! La lista de categorías está vacía.");

            // Finaliza el método para evitar continuar con el listado.
            return;
        }

        // Imprime el título del listado de categorías activas.
        System.out.println("\n=== LISTADO DE CATEGORÍAS ACTIVAS ===");

        // Declara una variable que contará las categorías con gravedad igual o superior a 4.
        int totalGraves = 0;

        // Recorre todas las posiciones ocupadas del arreglo.
        // Comienza en cero y termina cuando llega al valor del contador.
        for (int i = 0; i < contador; i++) {

            // Obtiene la categoría almacenada en la posición actual.
            Categoria c = categoriasActivas[i];

            // Muestra el número de la categoría y sus datos utilizando toString().
            // Se suma 1 a i para que el listado comience desde el número 1.
            System.out.println((i + 1) + ". " + c.toString());

            // Comprueba si el nivel de gravedad de la categoría es mayor o igual a 4.
            if (c.getNivel_gravedad() >= 4) {

                // Aumenta en uno el contador de categorías graves.
                totalGraves++;
            }
        }

        // Imprime una línea para separar la información del listado y los totales.
        System.out.println("-------------------------------------");

        // Muestra la cantidad total de categorías y cuántas tienen gravedad alta.
        System.out.println("TOTALES: " + contador + " categorías activas | " +
                           totalGraves + " clasificadas de alta severidad (Gravedad >= 4)");
    }

    // 6. DESHACER ÚLTIMA ELIMINACIÓN (HU-12)
    // Método que recupera la última categoría eliminada utilizando la pila.
    public String deshacerUltimaEliminacion() {

        // Comprueba si la pila de categorías eliminadas está vacía.
        if (pilaDeshacer.estaVacia()) {

            // Devuelve un aviso porque no existe ninguna categoría para recuperar.
            return "¡Aviso! La pila está vacía. No hay categorías para deshacer (CA-12.2).";
        }

        // Verifica que el arreglo tenga espacio disponible para recuperar una categoría.
        if (contador >= MAX_CATEGORIAS) {

            // Devuelve un error si el arreglo alcanzó su capacidad máxima.
            return "¡Error! No hay espacio en el arreglo para restaurar la categoría.";
        }

        // Extrae la última categoría guardada en la pila.
        // El método desapilar elimina el elemento de la pila y lo devuelve.
        Categoria recuperada = pilaDeshacer.desapilar();

        // Coloca la categoría recuperada en la siguiente posición disponible del arreglo.
        categoriasActivas[contador] = recuperada;

        // Aumenta el contador porque se agregó nuevamente una categoría.
        contador++;

        // Guarda el arreglo actualizado en el archivo.
        guardarEnArchivo();

        // Devuelve un mensaje confirmando la recuperación de la categoría.
        return "¡Éxito! Categoría " + recuperada.getCodigo_categoria() + " restaurada desde la pila de deshacer.";
    }

    // MÉTODO DE BÚSQUEDA SECUENCIAL.
    // Busca una categoría dentro del arreglo y devuelve su posición.
    // private significa que solamente se puede utilizar dentro de esta clase.
    private int buscarIndice(String codigo) {

        // Recorre las posiciones ocupadas del arreglo desde cero hasta contador - 1.
        for (int i = 0; i < contador; i++) {

            // Compara el código recibido con el código de la categoría actual.
            // equalsIgnoreCase permite comparar sin importar mayúsculas o minúsculas.
            if (categoriasActivas[i].getCodigo_categoria().equalsIgnoreCase(codigo)) {

                // Devuelve la posición donde se encontró la categoría.
                return i;
            }
        }

        // Devuelve -1 si termina el recorrido y no encuentra el código.
        return -1;
    }

    // PERSISTENCIA: GUARDAR EN categorias.txt.
    // Método encargado de guardar todas las categorías del arreglo en el archivo.
    private void guardarEnArchivo() {

        // try permite ejecutar instrucciones que podrían generar errores.
        // PrintWriter crea el objeto que permitirá escribir en el archivo.
        // FileWriter abre el archivo indicado por ARCHIVO.
        // Al utilizarlo sin true, reemplaza el contenido anterior del archivo.
        // try-with-resources cierra automáticamente el archivo al terminar.
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {

            // Recorre todas las categorías que están almacenadas en el arreglo.
            for (int i = 0; i < contador; i++) {

                // Convierte la categoría actual en texto separado por punto y coma.
                // Luego escribe esa información en una nueva línea del archivo.
                pw.println(categoriasActivas[i].aFormatoArchivo());
            }

        // catch captura los errores que puedan ocurrir durante la escritura del archivo.
        } catch (IOException e) {

            // Muestra un mensaje indicando que ocurrió un error al guardar.
            // e.getMessage() devuelve la descripción del error ocurrido.
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // PERSISTENCIA: LEER DESDE categorias.txt.
    // Método encargado de recuperar las categorías guardadas en el archivo.
    private void cargarDesdeArchivo() {

        // Crea un objeto File que representa el archivo categorias.txt.
        File f = new File(ARCHIVO);

        // Comprueba si el archivo no existe.
        if (!f.exists()) return; // Finaliza el método si no existe el archivo.

        // try-with-resources crea un Scanner para leer el archivo.
        // El archivo se cierra automáticamente al finalizar el bloque.
        try (Scanner sc = new Scanner(f)) {

            // Recorre el archivo mientras existan líneas por leer.
            // También comprueba que el arreglo no haya alcanzado su capacidad máxima.
            while (sc.hasNextLine() && contador < MAX_CATEGORIAS) {

                // Lee una línea del archivo y elimina los espacios al principio y al final.
                String linea = sc.nextLine().trim();

                // Comprueba que la línea no esté vacía.
                if (!linea.isEmpty()) {

                    // Divide la línea en diferentes partes utilizando el punto y coma como separador.
                    String[] p = linea.split(";");

                    // Comprueba que la línea tenga exactamente cinco datos.
                    // Código, nombre, gravedad, descripción y estado.
                    if (p.length == 5) {

                        // Crea un objeto Categoria utilizando los datos recuperados del archivo.
                        Categoria c = new Categoria(p[0], p[1],

                                                    // Convierte el texto de la posición 2 en un número entero.
                                                    Integer.parseInt(p[2]),

                                                    // Recupera la descripción almacenada en la posición 3.
                                                    p[3],

                                                    // Convierte el texto de la posición 4 en un valor boolean.
                                                    Boolean.parseBoolean(p[4]));

                        // Almacena la categoría recuperada en la siguiente posición disponible del arreglo.
                        categoriasActivas[contador] = c;

                        // Aumenta el contador porque se agregó una categoría al arreglo.
                        contador++;
                    }
                }
            }

        // Captura cualquier excepción que pueda ocurrir durante la lectura o conversión de los datos.
        } catch (Exception e) {

            // Muestra un mensaje indicando que ocurrió un error al cargar las categorías.
            // e.getMessage() permite conocer la descripción del error.
            System.out.println("Error al cargar el archivo de categorías: " + e.getMessage());
        }
    }
}
