package servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Scanner;

import dominio.Categoria;
import dominio.Evento;
import dominio.Organizador;
import dominio.Usuario;
import persistencia.CategoriaDao;
import persistencia.EventoDao;

public class EventoServicio implements IEventoServicio{ //Implemento la interfaz 

	private final Scanner sc;
	private EventoDao eventoDao;
	private CategoriaDao categoriaDao;

	public EventoServicio(Scanner sc) {
		this.sc = sc;
		this.eventoDao = new EventoDao();
		this.categoriaDao = new CategoriaDao();
	}

	public void mostrarEventos() {

		//Instancio una nueva coleccion HashMap para poder almacenar el resultado de la busqueda de
		// metodo del DAO .obtenerEventos()
		HashMap<String, Evento> evento = eventoDao.obtenerEventos();
		
		//Para recorrer la coleccion, utilizo un for/each mediante el cual en cada iteracion imprimiremos
		// el nombre, descripcion, fecha, hora y ubicacion, llamanod a sus correspondiente getters de Evento
		for(Evento e: evento.values()) {
			System.out.println("Nombre: " + e.getNombre());
			System.out.println("Descripción: " + e.getDescripcion());
			System.out.println("Fecha: " + e.getFecha());
			System.out.println("Hora: " + e.getHora());
			System.out.println("Ubicación: " + e.getUbicacion());
			System.out.println();
		}
	}

	//Mostrar eventos de un unico usuario donde el metodo recibira un usuario.
	public void mostrarEventosUsuario(Usuario usuario) {

		//En primer lugar instanciaremos una nueva collecion para albergar el resultado del metodo
		//.obtnerEvento() del DAO 
		HashMap<String, Evento> eventos = eventoDao.obtenerEventos();

		//a continuacion mediante un bucle for/each iremos revisando cada evento y encada iteracion
		// revisaremos si el evento en  cuestion contiene el asistente que buscamos mediante un condicional
		// if el cual si devuelve true, imprimiremos el nombre del evento.
		for (Evento evento : eventos.values()) {
			if (evento.getAsistentes().contains(usuario)) {
				System.out.println(evento.getNombre());
			}
		}	
	}

	// Inscribir usuario en un evento
	public void inscribirUsuario(Usuario usuario) {

		//mostraremos un indice informativo con los eventos actuales antes de darle a elegir uno, para evitar
		// errores en los inputs, lo haremos mediante el metodo creado previamente .mostrarEventos()
		mostrarEventos();

		//Solicito el nombre del evento y lo guardo en la variable nombreEvento
		System.out.println("Introduce el nombre del evento:");
		String nombreEvento = sc.nextLine();

		//Instancio un nuevo Evento donde almacenare el evento  con el nombre que buscamos
		Evento evento = eventoDao.obtenerEventos().get(nombreEvento);

		//si el resultado es null: imprimo 'evento no existe'
		if (evento == null) {
			System.out.println("El evento no existe");
			return;//devuelvo vacio
		}
		//si el usuario ya coincide con otro inscrito previamente: imprimo 'usuario ya existe'
		if (evento.getAsistentes().contains(usuario)) {
			System.out.println("Ya estás inscrito");
			return;//devuelvo vacio
		}
		//sino cumple con las condiciones previas entonces procedo a añadir el usuario a la lista de asistente
		// del evento e imprimo un mensaje por panatalla indicando 'inscripcion realizada con existo'
		evento.getAsistentes().add(usuario);
		System.out.println("Inscripción realizada correctamente");
	}

	// Cancelar inscripción
	public void cancelarInscripcion(Usuario usuario) {

		//mostraremos un indice informativo con los eventos actuales del usuario que recibe el el metodo
		// antes de darle a elegir uno, para evitar errores en los inputs, lo haremos mediante el metodo 
		// creado previamente .mostrarEventos()
		mostrarEventosUsuario(usuario);

		//Solicito el nombre del evento y lo guardo en la variable nombreEvento
		System.out.println("Introduce el nombre del evento:");
		String nombreEvento = sc.nextLine();

		//Instancio un nuevo Evento donde almacenare el evento  con el nombre que buscamos
		Evento evento = eventoDao.obtenerEventos().get(nombreEvento);

		//si el evento es null: imprimo 'evento no existe'
		if (evento == null) {
			System.out.println("El evento no existe");
			return;//no devuelvo nada
		}
		//mediante un condicional 'if' compruebo si el usuario se encuentra en la lista de asistentes
		// si es true llama al metodo .remove() de la coleccion Set para eleminar el usuario en cuestion.
		
		//si devuelve false: imprimo 'No estabas inscrito'
		if (evento.getAsistentes().remove(usuario)) {
			System.out.println("Inscripción cancelada");
		} else {
			System.out.println("No estabas inscrito");
		}
	}

	// Mostrar eventos creados por un organizador
	public void mostrarEventosOrganizador(Organizador organizador) {
		//En primer lugar instanciaremos una nueva collecion para albergar el resultado del metodo
		//.obtnerEvento() del DAO 
		HashMap<String, Evento> eventos = eventoDao.obtenerEventos();

		//Mediante un bucle for/aech recorro la coleccion eventos creada previamente y compruebo con
		//un condicional 'if' si el nombre del orgqanizador coincide con alguno de los nombre de los 
		//organizadores de los eventos registrados. Si es asi imprimo el nombre del evento en cada iteracion
		for (Evento evento : eventos.values()) {
			if (evento.getOrganizador().getNombre()
					.equals(organizador.getNombre())) {
				System.out.println(evento.getNombre());
			}
		}
	}

	// Crear un nuevo evento
	public void crearEvento(Organizador organizador) {

		System.out.println("Nombre del evento:");
		String nombre = sc.nextLine();

		System.out.println("Descripción:");
		String descripcion = sc.nextLine();

		System.out.println("Ubicación:");
		String ubicacion = sc.nextLine();

		System.out.println("Duración en minutos:");
		int duracion = Integer.parseInt(sc.nextLine());

		// Fecha y hora 
		LocalDate fecha = LocalDate.now();
		LocalTime hora = LocalTime.of(20, 0);

		// Usamos una categoría existente
		Categoria categoria = categoriaDao.obtenerCategoria("Concierto");

		Evento evento = new Evento(nombre,descripcion,fecha,hora,duracion,ubicacion,categoria,organizador);

		if (eventoDao.insertarEvento(evento)) {
			System.out.println("Evento creado correctamente");
		} else {
			System.out.println("Ya existe un evento con ese nombre");
		}
	}


}

