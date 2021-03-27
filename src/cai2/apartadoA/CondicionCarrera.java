package cai2.apartadoA;
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
		Hilo h = new Hilo();
//		HiloSync h = new HiloSync();
		Thread t1 = new Thread(h);
		Thread t2 = new Thread(h);
		
		t1.setPriority(10);
		t2.setPriority(10);
		t1.start();
		t2.start();
	
	

		
	
}
}
