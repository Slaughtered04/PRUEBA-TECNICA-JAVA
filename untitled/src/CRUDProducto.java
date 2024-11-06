import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDProducto {
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_online";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789#";

    public void agregarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, descripcion, precio, cantidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.executeUpdate();
        }
    }

    public List<Producto> obtenerProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                ));
            }
        }
        return productos;
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, cantidad = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarProducto(int id) throws SQLException {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Producto obtenerProductoConMenorConsonantes() throws SQLException {
        String query = "SELECT * FROM productos";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            Producto productoConMenorConsonantes = null;
            int menorConsonantes = Integer.MAX_VALUE;

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidadConsonantes = contarConsonantes(nombre);
                if (cantidadConsonantes < menorConsonantes) {
                    menorConsonantes = cantidadConsonantes;
                    productoConMenorConsonantes = new Producto(
                            rs.getInt("id"),
                            nombre,
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getInt("cantidad")
                    );
                }
            }
            return productoConMenorConsonantes;
        }
    }

    public int obtenerNumeroPrimoCercano(double precioMinimo) {
        int numero = (int) precioMinimo;
        while (!esPrimo(numero)) {
            numero++;
        }
        return numero;
    }

    private boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int contarConsonantes(String palabra) {
        int contador = 0;
        palabra = palabra.toLowerCase();
        for (char c : palabra.toCharArray()) {
            if ("bcdfghjklmnpqrstvwxyz".indexOf(c) >= 0) {
                contador++;
            }
        }
        return contador;
    }

    public int sumaRecursivaCantidad(List<Producto> productos, int index) {
        if (index < 0) return 0;
        return productos.get(index).getCantidad() + sumaRecursivaCantidad(productos, index - 1);
    }
}
