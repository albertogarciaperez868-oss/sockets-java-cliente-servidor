package ExamenHiloServidor;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor {
	
	public void Ejecutar() {
		try (ServerSocket ss = new ServerSocket(5000)){
			System.out.println("INICIANDO SERVIDOR EN EL PUERTO 5000...");
			
			while (true) {
				
				new HiloServidor(ss.accept()).start();
				
			}
			
		} catch (IOException e) {
			System.err.println("ERROR AL INICIAR EL SERVIDOR" +e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Servidor().Ejecutar();
		
	}

}
