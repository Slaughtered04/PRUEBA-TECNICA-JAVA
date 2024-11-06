import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            CRUDProducto crudProducto = new CRUDProducto();

            Producto nuevoProducto = new Producto(0, "Ordenador Portatil", "Lenovo Thinkpad", 800.000, 10);
            crudProducto.agregarProducto(nuevoProducto);

            Producto otroProducto = new Producto(0, "Telefono", "Samsung A31", 329.000, 20);
            crudProducto.agregarProducto(otroProducto);

            List<Producto> productos = crudProducto.obtenerProductos();
            productos.forEach(producto -> System.out.println(producto.getNombre()));

            Producto productoConMenorConsonantes = crudProducto.obtenerProductoConMenorConsonantes();
            System.out.println("Producto con menor cantidad de consonantes: " + productoConMenorConsonantes.getNombre());

            double precioMinimo = productos.stream().min((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio())).get().getPrecio();
            int numeroPrimoCercano = crudProducto.obtenerNumeroPrimoCercano(precioMinimo);
            System.out.println("Número primo más cercano al precio mínimo: " + numeroPrimoCercano);

            int sumaTotal = crudProducto.sumaRecursivaCantidad(productos, productos.size() - 1);
            System.out.println("Suma total de la cantidad de todos los productos: " + sumaTotal);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
