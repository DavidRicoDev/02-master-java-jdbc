package es.davidrico.jdbc.singleton;

import es.davidrico.jdbc.singleton.util.ConexionBaseDatos;
import es.davidrico.jdbc.singleton.modelos.Categoria;
import es.davidrico.jdbc.singleton.modelos.Producto;
import es.davidrico.jdbc.singleton.repositorios.ProductoRepositorioImpl;
import es.davidrico.jdbc.singleton.repositorios.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcTrx {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getInstance()) {
            if(conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
                System.out.println("============= listar =============");
                repositorio.listar().forEach(System.out::println);

                System.out.println("============= obtener por id =============");
                System.out.println(repositorio.porId(1L));

                System.out.println("============= insertar nuevo producto =============");
                Producto producto = new Producto();
                producto.setNombre("Teclado IBM mecánico");
                producto.setPrecio(1550);
                producto.setFechaRegistro(new Date());
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                producto.setSku("abcde12345");
                repositorio.guardar(producto);
                System.out.println("Producto guardado con éxito");

                System.out.println("============= editar producto =============");
                producto = new Producto();
                producto.setId(5L);
                producto.setNombre("Teclado Cosair k95 mecánico");
                producto.setPrecio(1000);
                producto.setSku("abcdef1234");
                categoria = new Categoria();
                categoria.setId(2L);
                producto.setCategoria(categoria);
                repositorio.guardar(producto);
                System.out.println("Producto editado con éxito");

                repositorio.listar().forEach(System.out::println);
                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                exception.printStackTrace();
            }
        }
    }
}
