package servicio;

import java.util.Scanner;

import dominio.Organizador;
import persistencia.OrganizadorDao;

public class OrganizadorServicio implements IOrganizadorServicio{ //Implemento la interfaz 
	
	private final Scanner sc;
	private OrganizadorDao organizadorDao;
	
	public OrganizadorServicio(Scanner sc) {
		this.sc = sc;
		this.organizadorDao = new OrganizadorDao();
	}
	
	//Metodo para hacer login de un organizador
	public Organizador hacerLogin() {

		//Pido el nombre del organizador y luego almaceno en la variable nombre
        System.out.println("Introduce el nombre:");
        String nombre = sc.nextLine();

      //Pido la contraseña del organizador y luego almaceno en la variable en contrasenia
        System.out.println("Introduce la contraseña:");
        String contrasenia = sc.nextLine();

        //Instancio un nuevo organizador y le añado los atributos previamente recogidos mediante
        //el metodo .login() del DAO que recibe dos datos un nombre y una contraseña y devbuleve
        //un organizador
        Organizador organizador = organizadorDao.login(nombre, contrasenia);

        //si el organizador esta vacio: imprimo usuario o contrasenia no existe
        if (organizador == null) {
            System.out.println("Usuario o contraseña incorrecta");
        //sino imprimo 'sesion iniciada correctamente'
        } else {
            System.out.println("Sesión iniciada correctamente");
        }
        return organizador;//devuelvo el organizador
    }

	//Metodo para registrar nuevo organizador
    public void registrarOrganizador() {

    	//Pido el nombre y lo guardo en una variable 'nombre'
        System.out.println("Nombre:");
        String nombre = sc.nextLine();

    	//Pido el correo y lo guardo en una variable 'correo'
        System.out.println("Correo:");
        String correo = sc.nextLine();
        
    	//Pido la contraseña y lo guardo en una variable 'contrasenia'
        System.out.println("Contraseña:");
        String contrasenia = sc.nextLine();

    	//Pido el telefono y lo guardo en una variable 'telefono'
        System.out.println("Teléfono:");
        String telefono = sc.nextLine();

        //con todos los datos recogidos, instancio un nuevo organizador y le añado todos los atributos
        Organizador organizador = new Organizador(nombre, correo, contrasenia, telefono);

        //Mediante un condicional 'if' comprobamos si podemos almacenar el nuevo organizar mediante el 
        //metodo .registrar() del DAO el cual comprueba si la collecion HashMap contiene el organizador en cuestion
        //si devuelve true , por que no existe en la coleccion por la palabra clave(no admite duplicados), entonces se
        //ejecuta el metodo regustrar correctamente devuelve true la condicion y imprimos en pantalla 'registrado correctamente'
        
        //sino imprimimos 'el organizador ya existe'
        if (organizadorDao.registrar(organizador)) {
            System.out.println("Organizador registrado correctamente");
        } else {
            System.out.println("Ese organizador ya existe");
        }
    }
}
