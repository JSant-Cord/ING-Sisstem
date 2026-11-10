import java.util.Scanner; // Importa la clase Scanner para leer datos escritos por el usuario en el teclado

/**
 * Clase principal para la sustentación.
 * Estudiante: Santiago Córdoba Camacho
 */
public class Main { // Clase pública que contiene el método main, el punto de entrada del programa
    public static void main(String[] args) { // Método principal: Java comienza a ejecutar el programa desde aquí
        Scanner teclado = new Scanner(System.in); // Crea un Scanner que lee desde la entrada estándar (teclado)
        GestionReportes gestion = new GestionReportes(); // Crea el objeto que administra los reportes (al crearse carga reportes.txt)
        int opcion = 0; // Variable que guarda la opción del menú elegida por el usuario; inicia en 0

        do { // Inicio del ciclo do-while: el menú se muestra al menos una vez y se repite
            System.out.println("\n=========================================="); // Imprime una línea decorativa (\n = salto de línea previo)
            System.out.println("   SISTEMA DE REPORTES DE INCENDIO (IS1)"); // Imprime el título del sistema
            System.out.println("=========================================="); // Imprime otra línea decorativa
            System.out.println("1. Registrar nuevo reporte (HU-01)"); // Opción 1: crear un reporte (historia de usuario 01)
            System.out.println("2. Consultar reporte por código (HU-02)"); // Opción 2: buscar un reporte por su código
            System.out.println("3. Actualizar reporte (HU-03)"); // Opción 3: modificar un reporte existente
            System.out.println("4. Eliminar reporte (HU-04)"); // Opción 4: borrar un reporte
            System.out.println("5. Listar todos los reportes (HU-05)"); // Opción 5: mostrar todos los reportes y totales
            System.out.println("6. Deshacer última eliminación (HU-06)"); // Opción 6: recuperar el último reporte eliminado
            System.out.println("7. Salir"); // Opción 7: terminar el programa
            System.out.print("Seleccione una opción: "); // Pide la opción sin salto de línea para que se escriba al lado

            try { // Bloque try: intenta ejecutar código que podría fallar
                opcion = Integer.parseInt(teclado.nextLine()); // Lee una línea de texto y la convierte a número entero
            } catch (Exception e) { // Si falla (ej: el usuario escribió letras), se captura el error
                opcion = 0; // Se asigna 0 para que caiga en el caso "default" (opción no válida)
            } // Fin del try-catch

            switch (opcion) { // Estructura que ejecuta un bloque según el valor de "opcion"
                case 1: // Si la opción es 1: registrar reporte
                    System.out.print("Ingrese código del reporte (ej: REP-001): "); // Pide el código del reporte
                    String cod = teclado.nextLine(); // Lee y guarda el código escrito
                    System.out.print("Ingrese código de categoría (ej: CAT-01): "); // Pide el código de la categoría
                    String cat = teclado.nextLine(); // Lee y guarda la categoría
                    System.out.print("Ingrese fecha (ej: 2026-11-06): "); // Pide la fecha del reporte
                    String fec = teclado.nextLine(); // Lee y guarda la fecha como texto
                    System.out.print("Ingrese latitud (ej: 6.2442): "); // Pide la latitud
                    double lat = Double.parseDouble(teclado.nextLine()); // Lee el texto y lo convierte a número decimal
                    System.out.print("Ingrese longitud (ej: -75.5812): "); // Pide la longitud
                    double lon = Double.parseDouble(teclado.nextLine()); // Lee el texto y lo convierte a número decimal
                    System.out.print("Ingrese descripción: "); // Pide la descripción del incidente
                    String desc = teclado.nextLine(); // Lee y guarda la descripción

                    // Simulación de validación cruzada (CAT-99 no existe)
                    boolean catExiste = !cat.equalsIgnoreCase("CAT-99"); // true si la categoría NO es "CAT-99" (ignora mayúsculas); simula el catálogo de otra entidad

                    String resReg = gestion.registrarReporte(cod, cat, fec, lat, lon, desc, catExiste); // Llama al método que registra y guarda el mensaje de resultado
                    System.out.println("\n>>> RESULTADO: " + resReg); // Muestra el resultado al usuario
                    break; // Sale del switch para no ejecutar los otros casos

                case 2: // Si la opción es 2: consultar reporte
                    System.out.print("Ingrese el código a consultar: "); // Pide el código a buscar
                    String codCons = teclado.nextLine(); // Lee el código
                    System.out.println("\n>>> RESULTADO:\n" + gestion.consultarReporte(codCons)); // Consulta y muestra los datos o el aviso de error
                    break; // Fin del caso 2

                case 3: // Si la opción es 3: actualizar reporte
                    System.out.print("Ingrese el código del reporte a actualizar: "); // Pide el código del reporte a modificar
                    String codAct = teclado.nextLine(); // Lee el código original
                    System.out.print("Reingrese el código para confirmar (o diferente para probar inmutabilidad): "); // Pide confirmar el código; si es distinto, prueba que el código no se puede cambiar
                    String codIntento = teclado.nextLine(); // Lee el código de confirmación
                    System.out.print("Ingrese nueva latitud: "); // Pide la nueva latitud
                    double nLat = Double.parseDouble(teclado.nextLine()); // Convierte el texto a decimal
                    System.out.print("Ingrese nueva longitud: "); // Pide la nueva longitud
                    double nLon = Double.parseDouble(teclado.nextLine()); // Convierte el texto a decimal
                    System.out.print("Ingrese nueva descripción: "); // Pide la nueva descripción
                    String nDesc = teclado.nextLine(); // Lee la nueva descripción

                    System.out.println("\n>>> RESULTADO: " + gestion.actualizarReporte(codAct, codIntento, nDesc, nLat, nLon)); // Llama a actualizar y muestra el resultado
                    break; // Fin del caso 3

                case 4: // Si la opción es 4: eliminar reporte
                    System.out.print("Ingrese el código del reporte a eliminar: "); // Pide el código a eliminar
                    String codElim = teclado.nextLine(); // Lee el código
                    System.out.println("\n>>> RESULTADO: " + gestion.eliminarReporte(codElim)); // Elimina y muestra el resultado
                    break; // Fin del caso 4

                case 5: // Si la opción es 5: listar reportes
                    gestion.listarReportes(); // Imprime todos los reportes y los totales (este método imprime por sí mismo)
                    break; // Fin del caso 5

                case 6: // Si la opción es 6: deshacer eliminación
                    System.out.println("\n>>> RESULTADO: " + gestion.deshacerUltimaEliminacion()); // Recupera el último eliminado y muestra el resultado
                    break; // Fin del caso 6

                case 7: // Si la opción es 7: salir
                    System.out.println("¡Programa finalizado correctamente!"); // Mensaje de despedida
                    break; // Fin del caso 7

                default: // Cualquier otro valor que no sea 1 a 7
                    System.out.println("¡Opción no válida! Intente de nuevo."); // Avisa que la opción es inválida
            } // Fin del switch

        } while (opcion != 7); // El ciclo se repite mientras la opción no sea 7 (salir)
    } 
} 
