package servicio;

import java.util.Scanner;

import dominio.Categoria;
import persistencia.CategoriaDao;

public class CategoriaServicio implements ICategoriaServicio{ //Implemento la interfaz 
	private final Scanner sc;
	private CategoriaDao categoriaDao;
	
	
	public CategoriaServicio(Scanner sc) {
		this.sc = sc;
		this.categoriaDao = new CategoriaDao();
	}


	@Override
	public Categoria buscarCategoria() {

		//Mediante un ucle for/each muestro las categoria de la coleccion HashMap extraidas mediante el metodo .obtenercategoria()
		// del DAO y reduciendo la busqueda solo a la palabra clave, en este caso el nombre, mediante el metodo .keySet()
		System.out.println("Categorías disponibles:");
        for (String nombre : categoriaDao.obtenerCategorias().keySet()) {
            System.out.println("- " + nombre);
        }

        // Pido categoría
        System.out.println("Introduce el nombre de la categoría:");
        String nombreCategoria = sc.nextLine();

        // Busco en el DAO a traves de su metodo .obtenerCategoria() que recibe un nombre
        Categoria categoria = categoriaDao.obtenerCategoria(nombreCategoria);

        // Compruebo si la categoria existe
        if (categoria == null) {
            System.out.println("La categoría no existe.");
        }
        //Devuelvo categoria
        return categoria;
	}
}
