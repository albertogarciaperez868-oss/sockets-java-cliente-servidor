package ExamenHiloServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private void run() {
		try (Socket socket = new Socket("localhost", 5000);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				Scanner teclado = new Scanner(System.in)) {
			
			String linealeida;
			
			do {
				
				menu();
				System.out.println("ELIGE UNA OPCION DEL MENU :");
				linealeida = teclado.nextLine();
				
				String comando = prepararComando(linealeida, teclado);
				if (comando== null) {
					System.out.println("EL COMANDO ES INVALIDO,INTENTALO DE NUEVO");
					continue;
				}
				
				System.out.println("ENVIANDO COMANDO '" + comando +"'");
				pw.println(comando);
				
				String respuesta = br.readLine();
				System.out.println("RECIBIENDO RESPUESTA DEL SERVIDOR " + respuesta);
				
			} while (true);

		} catch (IOException e) {
			System.err.println("ERROR DE CONEXION" +e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	private void menu() {
		System.out.println("--MENU--");
		System.out.println("1.Agregar");
		System.out.println("2.Obtener");
		System.out.println("3.Eliminar");
		System.out.println("Actualizar");
	}
	
	private String prepararComando (String opcion, Scanner teclado) {
		switch (opcion) {
		case "1":
			return "ADD " + solicitarDatos(teclado);
		case "2":
			return "GET " + solicitarID(teclado);
		case "3":
			return "REMOVE " + solicitarID(teclado);
		case "4":
			return "UPDATE " + solicitarDatos(teclado);

		default:
			return null;
		}
	}
	
	private String solicitarID (Scanner teclado) {
		System.out.println("INTRODUCE EL ID :");
		return teclado.nextLine();
	}
	
	private String solicitarDatos(Scanner teclado) {
		System.out.println("INTRODUCE EL ID :");
		String id = teclado.nextLine();
		
		System.out.println("INTRODUCE EL NOMBRE :");
		String nombre = teclado.nextLine();
		
		System.out.println("INTRODUCE EL PRECIO :");
		String precio = teclado.nextLine();
		
		return id + "#" + nombre + "#" + precio;
		
		
		
	}

	public static void main(String[] args) {
		
		new Cliente().run();
	}

}
