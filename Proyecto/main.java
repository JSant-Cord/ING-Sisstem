import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GestionCategorias gestion = new GestionCategorias();
        int opcion = 0;

        do {
            System.out.println("\n==========================================");
            System.out.println("  GESTIÓN DE CATEGORÍAS DE INCENDIO (IS1)");
            System.out.println("  Estudiante B: Juan Esteban Monsalve");
            System.out.println("==========================================");
            System.out.println("1. Registrar nueva categoría (HU-07)");
            System.out.println("2. Consultar categoría por código (HU-08)");
            System.out.println("3. Actualizar categoría (HU-09)");
            System.out.println("4. Eliminar categoría (HU-10)");
            System.out.println("5. Listar todas las categorías (HU-11)");
            System.out.println("6. Deshacer última eliminación (HU-12)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código de categoría (ej: CAT-01): ");
                    String cod = teclado.nextLine();
                    System.out.print("Ingrese nombre (ej: Humo / Incendio): ");
                    String nom = teclado.nextLine();
                    System.out.print("Ingrese nivel de gravedad (1 a 5): ");
                    int grav = 1;
                    try {
                        grav = Integer.parseInt(teclado.nextLine());
                    } catch (Exception e) {
                        grav = 1;
                    }
                    System.out.print("Ingrese descripción: ");
                    String desc = teclado.nextLine();

                    String resReg = gestion.registrarCategoria(cod, nom, grav, desc);
                    System.out.println("\n>>> RESULTADO: " + resReg);
                    break;

                case 2:
                    System.out.print("Ingrese el código de la categoría a consultar: ");
                    String codCons = teclado.nextLine();
                    System.out.println("\n>>> RESULTADO:\n" + gestion.consultarCategoria(codCons));
                    break;

                case 3:
                    System.out.print("Ingrese el código original de la categoría: ");
                    String codAct = teclado.nextLine();
                    System.out.print("Reingrese el código para confirmar (o un código distinto para probar inmutabilidad): ");
                    String codIntento = teclado.nextLine();
                    System.out.print("Ingrese nuevo nombre: ");
                    String nNom = teclado.nextLine();
                    System.out.print("Ingrese nuevo nivel de gravedad (1 a 5): ");
                    int nGrav = 1;
                    try {
                        nGrav = Integer.parseInt(teclado.nextLine());
                    } catch (Exception e) {
                        nGrav = 1;
                    }
                    System.out.print("Ingrese nueva descripción: ");
                    String nDesc = teclado.nextLine();

                    System.out.println("\n>>> RESULTADO: " + gestion.actualizarCategoria(codAct, codIntento, nNom, nGrav, nDesc));
                    break;

                case 4:
                    System.out.print("Ingrese el código de la categoría a eliminar: ");
                    String codElim = teclado.nextLine();
                    
                    // Simulación de Validación Cruzada (RN-05 / CA-10.2)
                    System.out.print("¿Simular que tiene reportes activos asociados en la Entidad A de Santiago? (S/N): ");
                    String respuesta = teclado.nextLine();
                    boolean tieneReportes = respuesta.equalsIgnoreCase("S");

                    System.out.println("\n>>> RESULTADO: " + gestion.eliminarCategoria(codElim, tieneReportes));
                    break;

                case 5:
                    gestion.listarCategorias();
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
