package servicio;

import java.util.Scanner;

import dominio.Usuario;
import persistencia.UsuarioDao;

public class UsuarioServicio implements IUsuarioServicio{ //Implemento la interfaz 
	
	private final Scanner sc;
	private UsuarioDao usuarioDao;
	
	public UsuarioServicio(Scanner sc) {
		this.sc = sc;
		this.usuarioDao = new UsuarioDao();
	}
	
	//Metodo para inicar sesion un Usuario normal:
	public Usuario hacerLogin() {
		
		//Pedimos los datos del usuario: nombre y contraseña. Y los guardamos en dos variables
		//'nombre' y 'contrasenia' para posteriormente utilizarlas
		System.out.println("Introduce el nombre de usuario:");
		String nombre = sc.nextLine();
		System.out.println("Introduce la contraseña:");
		String contrasenia = sc.nextLine();
		
		//Intanciamos un nuevo usuario con los atributos previamente recogidos
		Usuario usuario = usuarioDao.login(nombre, contrasenia);
		
		//mediante un codicional 'if' comprobamos si el usario instanciado es null, entonces imprimos
		//usuario o contraseña incorrecta.
		
		//sino imprimos 'sesion iniciada correctamente'
		if(usuario == null) {
			System.out.println("ERROR! Usuario o cantraseña incorrecta");
		}else {
			System.out.println("Sesion iniciada correctamente");
		}
			
		return usuario;		
	}
	
	//Metodo para registrar un usuario nuevo:
	public void registrarUsuario() {

		//Pedimos los datos necesario para crear un usuario y almacenamos los resultados
		//en sus respectivas variable: 'nombre', 'contraseña' y 'correo'.
	    System.out.println("Nombre:");
	    String nombre = sc.nextLine();

	    System.out.println("Correo:");
	    String correo = sc.nextLine();

	    System.out.println("Contraseña:");
	    String contrasenia = sc.nextLine();

	    //Instanciamos un nuevo usuario con las variables creadas previamente
	    Usuario usuario = new Usuario(nombre, correo, contrasenia);

	    //Y comprobamos si el nuevo usuario instanciado existe mediante el metodo .registrar() del DAO
	    // si la condicion devuelve true, entonces imprimimos: 'Registro correcto'.
	    
	    //sino imprimos:'El usuario ya existe'
	    if (usuarioDao.registrar(usuario)) {
	        System.out.println("Registro correcto");
	    } else {
	        System.out.println("Ese usuario ya existe");
	    }
	}
}
