package ExamenHiloServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

public class HiloServidor extends Thread {

	private final Socket socket;
	private final Hashtable<String, Producto> producto;

	public HiloServidor(Socket socket) {
		this.socket = socket;
		this.producto = new Hashtable<>();
	}
	
	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {

			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println("LINEA RECIBIDA" + linea + "");

				String[] partes = linea.split(" ", 2);
				String comando = partes[0];
				String datos = (partes.length > 1) ? partes[1] : "";

				String respuesta = procesarComando(comando, datos);
				pw.println(respuesta);

			}

		} catch (IOException e) {
			System.err.println("ERROR EN EL HILO SERVIDOR" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String procesarComando (String comando, String datos) {
		try {
			switch (comando) {
			case "ADD":
				return agregar(datos);
			case "UPDATE":
				return actualizar(datos);
			case "GET":
				return obtener(datos);
			case "REMOVE":
				return eliminar(datos);
			default:
				return "ERROR:COMANDO INVALIDO";
			}
			
		} catch (Exception e) {
			return "ERROR AL PROCESAR EL COMANDO" + e.getMessage();
			
		}
	}
	
	private String agregar(String datos) {
		String [] partes = datos.split("#");
		if (partes.length !=3) return "ERROR:DATOS INVALIDOS PARA EL ADD"; 
		
		producto.put(partes[0], new Producto(partes[0], partes[1], partes[2]));
		return "OK AÑADIDO";
			
		}
	private String actualizar(String datos) {
		return agregar(datos);
	}
	
	private String obtener(String datos) {
		Producto p = producto.get(datos);
		return (p !=null) ? "OK e" + p : "ERROR: NO ENCONTRADO";
	}
	
	private String eliminar(String datos) {
		
		return (producto.remove(datos) != null)? "OKEY ELIMINADO" : "ERROR NO ENCONTRADO";
		
	}
	}


