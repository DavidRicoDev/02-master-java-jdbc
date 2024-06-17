package es.davidrico.jdbc.singleton;

import es.davidrico.jdbc.singleton.util.ConexionBaseDatos;
import es.davidrico.jdbc.singleton.modelos.Producto;
import es.davidrico.jdbc.singleton.repositorios.ProductoRepositorioImpl;
import es.davidrico.jdbc.singleton.repositorios.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbcDelete {
    public static void main(String[] args) {
        try (Connection conn = ConexionBaseDatos.getInstance()) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("============= listar =============");
            repositorio.listar().forEach(System.out::println);

            System.out.println("============= obtener por id =============");
            System.out.println(repositorio.porId(1L));

            System.out.println("============= Eliminar producto =============");
            repositorio.eliminar(3L);
            System.out.println("Producto eliminado con éxito");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
