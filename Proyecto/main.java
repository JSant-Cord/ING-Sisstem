
 // Importa la clase Scanner, que permite leer los datos ingresados por el usuario desde el teclado.
import java.util.Scanner;

// Declara la clase principal llamada main.
// Esta clase contiene el menú desde donde el usuario puede administrar las categorías.
public class main {

    // Método principal del programa.
    // Es el primer método que Java ejecuta cuando se inicia la aplicación.
    public static void main(String[] args) {

        // Crea un objeto Scanner llamado teclado para recibir información del usuario.
        // System.in indica que los datos se reciben desde el teclado.
        Scanner teclado = new Scanner(System.in);

        // Crea un objeto de GestionCategorias.
        // Este objeto permite utilizar los métodos para registrar, consultar,
        // actualizar, eliminar, listar y recuperar categorías.
        // Al crearlo, también se ejecuta su constructor y se cargan los datos del archivo.
        GestionCategorias gestion = new GestionCategorias();

        // Declara la variable opcion de tipo entero.
        // Almacena la opción que el usuario selecciona en el menú.
        // Inicialmente tiene el valor 0.
        int opcion = 0;

        // Inicia un ciclo do-while.
        // Este ciclo permite mostrar el menú repetidamente hasta que el usuario seleccione salir.
        // El bloque do se ejecuta al menos una vez.
        do {

            // Imprime una línea de separación para organizar visualmente el menú.
            System.out.println("\n==========================================");

            // Muestra el título principal del sistema.
            // \n genera un salto de línea antes del texto.
            System.out.println("  GESTIÓN DE CATEGORÍAS DE INCENDIO (IS1)");

            // Muestra el nombre del estudiante responsable de esta parte del proyecto.
            System.out.println("  Estudiante B: Juan Esteban Monsalve");

            // Imprime otra línea para cerrar el encabezado del menú.
            System.out.println("==========================================");

            // Muestra la opción 1 para registrar una categoría nueva.
            System.out.println("1. Registrar nueva categoría (HU-07)");

            // Muestra la opción 2 para consultar una categoría por su código.
            System.out.println("2. Consultar categoría por código (HU-08)");

            // Muestra la opción 3 para actualizar los datos de una categoría.
            System.out.println("3. Actualizar categoría (HU-09)");

            // Muestra la opción 4 para eliminar una categoría.
            System.out.println("4. Eliminar categoría (HU-10)");

            // Muestra la opción 5 para listar todas las categorías almacenadas.
            System.out.println("5. Listar todas las categorías (HU-11)");

            // Muestra la opción 6 para recuperar la última categoría eliminada.
            System.out.println("6. Deshacer última eliminación (HU-12)");

            // Muestra la opción 7 para salir del programa.
            System.out.println("7. Salir");

            // Solicita al usuario que seleccione una opción del menú.
            // print muestra el mensaje sin generar un salto de línea.
            System.out.print("Seleccione una opción: ");

            // Inicia un bloque try para controlar posibles errores al ingresar la opción.
            try {

                // Lee el texto ingresado por el usuario y lo convierte en un número entero.
                // nextLine() lee toda la línea ingresada.
                // Integer.parseInt() convierte ese texto en un int.
                opcion = Integer.parseInt(teclado.nextLine());

            // catch captura cualquier error que ocurra al convertir el texto en número.
            } catch (Exception e) {

                // Si el usuario escribe algo que no es un número válido,
                // se asigna 0 para que el programa muestre la opción no válida.
                opcion = 0;
            }

            // switch permite ejecutar diferentes instrucciones dependiendo de la opción seleccionada.
            // Compara el valor de opcion con cada uno de los case.
            switch (opcion) {

                // OPCIÓN 1: REGISTRAR UNA NUEVA CATEGORÍA.
                case 1:

                    // Solicita al usuario el código que identificará la categoría.
                    System.out.print("Ingrese código de categoría (ej: CAT-01): ");

                    // Lee el código ingresado y lo almacena en la variable cod.
                    String cod = teclado.nextLine();

                    // Solicita el nombre de la categoría.
                    System.out.print("Ingrese nombre (ej: Humo / Incendio): ");

                    // Lee el nombre y lo almacena en la variable nom.
                    String nom = teclado.nextLine();

                    // Solicita el nivel de gravedad de la categoría.
                    System.out.print("Ingrese nivel de gravedad (1 a 5): ");

                    // Declara la variable grav y le asigna inicialmente el valor 1.
                    int grav = 1;

                    // Intenta convertir el nivel de gravedad ingresado en un número entero.
                    try {

                        // Lee el dato ingresado y lo convierte en un entero.
                        grav = Integer.parseInt(teclado.nextLine());

                    // Captura errores si el usuario ingresa un dato que no es numérico.
                    } catch (Exception e) {

                        // Asigna el valor 1 como valor predeterminado si ocurre un error.
                        grav = 1;
                    }

                    // Solicita la descripción de la categoría.
                    System.out.print("Ingrese descripción: ");

                    // Lee la descripción y la almacena en la variable desc.
                    String desc = teclado.nextLine();

                    // Llama al método registrarCategoria de GestionCategorias.
                    // Envía el código, nombre, gravedad y descripción como parámetros.
                    // El resultado del método se almacena en resReg.
                    String resReg = gestion.registrarCategoria(cod, nom, grav, desc);

                    // Muestra el resultado que devuelve el método de registro.
                    // Puede ser un mensaje de éxito o un mensaje de error.
                    System.out.println("\n>>> RESULTADO: " + resReg);

                    // break finaliza la ejecución del case 1 y evita pasar a los siguientes casos.
                    break;

                // OPCIÓN 2: CONSULTAR UNA CATEGORÍA POR SU CÓDIGO.
                case 2:

                    // Solicita el código de la categoría que se desea consultar.
                    System.out.print("Ingrese el código de la categoría a consultar: ");

                    // Lee el código ingresado y lo almacena en codCons.
                    String codCons = teclado.nextLine();

                    // Llama al método consultarCategoria enviando el código ingresado.
                    // Muestra los datos encontrados o el mensaje correspondiente.
                    System.out.println("\n>>> RESULTADO:\n" + gestion.consultarCategoria(codCons));

                    // Finaliza la ejecución de la opción 2.
                    break;

                // OPCIÓN 3: ACTUALIZAR UNA CATEGORÍA.
                case 3:

                    // Solicita el código original de la categoría que se desea modificar.
                    System.out.print("Ingrese el código original de la categoría: ");

                    // Lee el código original y lo almacena en codAct.
                    String codAct = teclado.nextLine();

                    // Solicita nuevamente el código para comprobar que no se intente modificar.
                    // También permite probar la validación de inmutabilidad.
                    System.out.print("Reingrese el código para confirmar (o un código distinto para probar inmutabilidad): ");

                    // Lee el código ingresado para la confirmación y lo almacena en codIntento.
                    String codIntento = teclado.nextLine();

                    // Solicita el nuevo nombre de la categoría.
                    System.out.print("Ingrese nuevo nombre: ");

                    // Lee el nuevo nombre y lo almacena en nNom.
                    String nNom = teclado.nextLine();

                    // Solicita el nuevo nivel de gravedad.
                    System.out.print("Ingrese nuevo nivel de gravedad (1 a 5): ");

                    // Declara nGrav y le asigna inicialmente el valor 1.
                    int nGrav = 1;

                    // Intenta convertir el nuevo nivel de gravedad en un número entero.
                    try {

                        // Lee el dato ingresado y lo convierte en entero.
                        nGrav = Integer.parseInt(teclado.nextLine());

                    // Captura cualquier error durante la conversión.
                    } catch (Exception e) {

                        // Si ocurre un error, establece el nivel de gravedad en 1.
                        nGrav = 1;
                    }

                    // Solicita la nueva descripción de la categoría.
                    System.out.print("Ingrese nueva descripción: ");

                    // Lee la nueva descripción y la almacena en nDesc.
                    String nDesc = teclado.nextLine();

                    // Llama al método actualizarCategoria de GestionCategorias.
                    // Envía el código original, el código de intento, el nuevo nombre,
                    // la nueva gravedad y la nueva descripción.
                    // Muestra el mensaje que devuelve el método.
                    System.out.println("\n>>> RESULTADO: " + gestion.actualizarCategoria(codAct, codIntento, nNom, nGrav, nDesc));

                    // Finaliza la ejecución de la opción 3.
                    break;

                // OPCIÓN 4: ELIMINAR UNA CATEGORÍA.
                case 4:

                    // Solicita el código de la categoría que se desea eliminar.
                    System.out.print("Ingrese el código de la categoría a eliminar: ");

                    // Lee el código ingresado y lo almacena en codElim.
                    String codElim = teclado.nextLine();

                    // Simulación de Validación Cruzada (RN-05 / CA-10.2).
                    // Esta validación simula si la categoría tiene reportes activos asociados.

                    // Pregunta si se desea simular que existen reportes asociados en la Entidad A.
                    System.out.print("¿Simular que tiene reportes activos asociados en la Entidad A de Santiago? (S/N): ");

                    // Lee la respuesta ingresada por el usuario.
                    String respuesta = teclado.nextLine();

                    // Declara una variable booleana llamada tieneReportes.
                    // equalsIgnoreCase("S") comprueba si la respuesta es S sin importar mayúsculas o minúsculas.
                    // Si la respuesta es S, guarda true; de lo contrario, guarda false.
                    boolean tieneReportes = respuesta.equalsIgnoreCase("S");

                    // Llama al método eliminarCategoria.
                    // Envía el código y el valor booleano que indica si existen reportes asociados.
                    // Muestra el resultado de la operación.
                    System.out.println("\n>>> RESULTADO: " + gestion.eliminarCategoria(codElim, tieneReportes));

                    // Finaliza la ejecución de la opción 4.
                    break;

                // OPCIÓN 5: LISTAR TODAS LAS CATEGORÍAS.
                case 5:

                    // Llama al método listarCategorias de GestionCategorias.
                    // Este método muestra todas las categorías y calcula los totales.
                    gestion.listarCategorias();

                    // Finaliza la ejecución de la opción 5.
                    break;

                // OPCIÓN 6: DESHACER LA ÚLTIMA ELIMINACIÓN.
                case 6:

                    // Llama al método deshacerUltimaEliminacion.
                    // Recupera la última categoría eliminada utilizando la pila.
                    // Muestra el resultado de la recuperación.
                    System.out.println("\n>>> RESULTADO: " + gestion.deshacerUltimaEliminacion());

                    // Finaliza la ejecución de la opción 6.
                    break;

                // OPCIÓN 7: SALIR DEL PROGRAMA.
                case 7:

                    // Muestra un mensaje indicando que el programa terminó correctamente.
                    System.out.println("¡Programa finalizado correctamente!");

                    // Finaliza la ejecución de la opción 7.
                    break;

                // DEFAULT: SE EJECUTA CUANDO EL USUARIO INGRESA UNA OPCIÓN DIFERENTE DE 1 A 7.
                default:

                    // Muestra un mensaje indicando que la opción ingresada no es válida.
                    System.out.println("¡Opción no válida! Intente de nuevo.");
            }

        // Comprueba si la opción seleccionada es diferente de 7.
        // Si es diferente, el menú vuelve a mostrarse.
        // Si es igual a 7, el ciclo termina y el programa sale del menú.
        } while (opcion != 7);

        // Nota: el código original no incluye el cierre del Scanner.
        // Se conserva así para no modificar la estructura original.
    }
}
