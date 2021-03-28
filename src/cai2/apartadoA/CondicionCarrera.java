package cai2.apartadoA;

import java.util.Scanner;
/* En esta clase estudiaremos las condiciones de carrera.
 * Para ello haremos uso de los hilos.
 * 
 *  Primero usaremos la clase Hilo para crear dos hilos y ejecutarlos. Con esta clase sufriremos condiciones de carrera
 *  y podremos ver que en distintas ejecuciones dan diferentes resultados par el mismo código.
 *  
 *  Para evitar la condicion de carrera usando hilos, usaremos a clase HiloSync, la cual usa la opcion synchronized
 *  que esta implementada en java para evitar las condiciones de carrera entre metodos. Esta solución no solo es valida
 *  para los hilos, sino para cualquier método de cualquier clase
 * */
public class CondicionCarrera {

	public static void main(String[] args) {
		System.out.println("Pruebas sobre condición de carrera: \n	1º Operaciones sin sincronización"
				+ "\n	2º Operaciones con sincronización"
				+ "\n	3º Concatenación sin sincronización"
				+ "\n	4º Concatenación con sincronización"
				+ "\nIntroduzca el número de la prueba:");
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
