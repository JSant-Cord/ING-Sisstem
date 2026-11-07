/**
 * Pila estática (LIFO) construida a mano para la Entidad Categoria.
 * Sin utilizar java.util.Stack ni colecciones de la biblioteca.
 */
public class PilaCategorias {
    private Categoria[] elementos;
    private int tope;
    private static final int CAPACIDAD = 50;

    public PilaCategorias() {
        this.elementos = new Categoria[CAPACIDAD];
        this.tope = -1; // -1 indica que la pila está vacía
    }

    public boolean estaVacia() {
        return tope == -1;
    }

    public boolean estaLlena() {
        return tope == CAPACIDAD - 1;
    }

    // Apila la categoría eliminada (HU-10)
    public boolean apilar(Categoria cat) {
        if (estaLlena()) {
            return false;
        }
        tope++;
        elementos[tope] = cat;
        return true;
    }

    // Desapila y retorna la última categoría agregada a la pila (HU-12)
    public Categoria desapilar() {
        if (estaVacia()) {
            return null;
        }
        Categoria recuperada = elementos[tope];
        elementos[tope] = null;
        tope--;
        return recuperada;
    }
}