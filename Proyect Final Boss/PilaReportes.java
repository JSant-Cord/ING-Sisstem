// Declara una clase pública llamada PilaReportes.
// Esta clase administra una pila de objetos de tipo Reporte.
public class PilaReportes {

    // Declara un arreglo que almacena objetos de tipo Reporte.
    private Reporte[] elementos;

    // Esta variable indica la posición del último elemento de la pila.
    private int tope;

    // Declara una constante que establece la capacidad máxima de la pila.
    // static significa que pertenece a la clase.
    // final significa que su valor no puede cambiar.
    private static final int CAPACIDAD = 50;


    // Constructor de la clase PilaReportes.
    public PilaReportes() {
        // Crea un arreglo de 50 posiciones para almacenar reportes.
        this.elementos = new Reporte[CAPACIDAD];
        this.tope = -1;
    }


    // Método que comprueba si la pila está vacía.
    public boolean estaVacia() {

        // Si el tope es -1, 
        return tope == -1;
    }


    // Método que comprueba si la pila está llena.
    public boolean estaLlena() {
        return tope == CAPACIDAD - 1;
    }


    // Método apilar: agrega un reporte en la parte superior de la pila.
    // Recibe un objeto de tipo Reporte como parámetro.
    public boolean apilar(Reporte reporte) {

        // Comprueba primero si la pila está llena.
        if (estaLlena()) {

            // Si está llena, devuelve false porque no puede agregarlo.
            return false;
        }

        // Aumenta el valor del tope en una posición.
        tope++;

        // Guarda el reporte recibido en la posición indicada por el tope.
        elementos[tope] = reporte;

        // Devuelve true para indicar que el reporte se agregó correctamente.
        return true;
    }


    // Método desapilar: elimina y devuelve el último reporte agregado.
    // Devuelve un objeto Reporte.
    public Reporte desapilar() {

        // Comprueba si la pila está vacía antes de eliminar.
        if (estaVacia()) {

            // Si no hay elementos, devuelve null.
            // null significa que no existe ningún objeto en esa posición.
            return null;
        }

        // Guarda temporalmente el reporte que está en la cima de la pila.
        Reporte eliminado = elementos[tope];

        // Limpia la posición que ocupaba el reporte.
        // Así se elimina la referencia al objeto dentro del arreglo.
        elementos[tope] = null;

        // Disminuye el tope en una posición.
        // Esto indica que el elemento superior ya fue retirado.
        tope--;

        // Devuelve el reporte que se había guardado temporalmente.
        return eliminado;
    }

// Esta llave cierra la clase PilaReportes.
}
