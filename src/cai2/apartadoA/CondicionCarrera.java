package cai2.apartadoA;

import java.util.Scanner;
/* En esta clase estudiaremos las condiciones de carrera.
 * Para ello haremos uso de los hilos.
 * 
 *  Primero usaremos la clase Hilo para crear dos hilos y ejecutarlos. Con esta clase sufriremos condiciones de carrera
 *  y podremos ver que en distintas ejecuciones dan diferentes resultados par el mismo c�digo.
 *  
 *  Para evitar la condicion de carrera usando hilos, usaremos a clase HiloSync, la cual usa la opcion synchronized
 *  que esta implementada en java para evitar las condiciones de carrera entre metodos. Esta soluci�n no solo es valida
 *  para los hilos, sino para cualquier m�todo de cualquier clase
 * */
public class CondicionCarrera {

	public static void main(String[] args) {
		System.out.println("Pruebas sobre condici�n de carrera: \n	1� Operaciones sin sincronizaci�n"
				+ "\n	2� Operaciones con sincronizaci�n"
				+ "\n	3� Concatenaci�n sin sincronizaci�n"
				+ "\n	4� Concatenaci�n con sincronizaci�n"
				+ "\nIntroduzca el n�mero de la prueba:");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		if(t==1) {
			Hilo h = new Hilo();
			Thread t1 = new Thread(h);
			Thread t2 = new Thread(h);
			t1.start();
			t2.start();
			System.out.println("Resultado esperado: 4093");
		}else if(t==2) {
			HiloSync h = new HiloSync();
			Thread t1 = new Thread(h);
			Thread t2 = new Thread(h);
			t1.start();
			t2.start();
			System.out.println("Resultado esperado: 4093");
		}else if(t==3) {
			HiloString h = new HiloString();
			Thread t1 = new Thread(h);
			Thread t2 = new Thread(h);
			t1.start();
			t2.start();
			System.out.println("Resultado esperado: Esto es una prueba sobre las condiciones de carrera Esto es una prueba sobre las condiciones de carrera");
		}else if(t==4) {
			HiloStringSync h = new HiloStringSync();
			Thread t1 = new Thread(h);
			Thread t2 = new Thread(h);
			t1.start();
			t2.start();
			System.out.println("Resultado esperado: Esto es una prueba sobre las condiciones de carrera Esto es una prueba sobre las condiciones de carrera");
		}else {
			System.out.println("Error al elegir el test");
		}
		
		

		

	

		
	
}
}
