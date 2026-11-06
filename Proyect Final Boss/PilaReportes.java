/**
 * Pila estática (LIFO) construida a mano con un arreglo fijo.
 * No utiliza java.util.Stack ni ninguna colección de Java.
 */
public class PilaReportes {
    private Reporte[] elementos;
    private int tope;
    private static final int CAPACIDAD = 50;

    public PilaReportes() {
        this.elementos = new Reporte[CAPACIDAD];
        this.tope = -1; // Indica que la pila está vacía
    }

    public boolean estaVacia() {
        return tope == -1;
    }

    public boolean estaLlena() {
        return tope == CAPACIDAD - 1;
    }

    // Apila el reporte eliminado (HU-04)
    public boolean apilar(Reporte reporte) {
        if (estaLlena()) {
            return false;
        }
        tope++;
        elementos[tope] = reporte;
        return true;
    }

    // Saca y retorna el reporte de la cima de la pila (HU-06)
    public Reporte desapilar() {
        if (estaVacia()) {
            return null;
        }
        Reporte eliminado = elementos[tope];
        elementos[tope] = null; // Limpieza de la casilla
        tope--;
        return eliminado;
    }
}