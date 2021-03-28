package cai2.apartadoB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Pipes {
  public static void main(String[] args)throws IOException, InterruptedException {
		System.out.println("Pruebas sobre pipes: \n	1º Transmisión de integer"
				+ "\n	2º Tratamiento de ficheros"
				+ "\nIntroduzca el número de la prueba:");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		if(t==1) pipesInt();
		else if (t==2) pipesFiles();
		else System.out.println("Error al elegir el test");
	  

} 
  public static void pipesInt() {
	
	Queue<Integer> buffer = new ArrayBlockingQueue<Integer>(10); //TAMAÑO DEL BUFFER

	Thread threadProductor = new Thread("Productor") {
		public void run() {
			for (int i = 0; i < 5000; i++) {
				try {
					buffer.add(i);
				} catch (IllegalStateException e) {
					// Se ha capturado la excepcion
				}
			}
		};

	};

	Thread threadConsumidor = new Thread("Consumidor") {
		public void run() {
			// Se para la ejecucion cuando el hilo muera y la pila este vacia
			while (threadProductor.isAlive() || !buffer.isEmpty()) {
				if (!buffer.isEmpty()) {

					System.out.println(buffer.poll());
				}

			}
		};

	};

	threadProductor.start();
	threadConsumidor.start();
}
  

  
	public static void pipesFiles() throws IOException, InterruptedException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "\\files\\archivo-tests-apartado-b.txt");
		BufferedReader br = new BufferedReader(fr);

		FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\files\\archivo-salida-tests.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		List<String> lines = br.lines().collect(Collectors.toList());
		

		
		Integer size = 10; //TAMAÑO DEL BUFFER
		String buffer[] = new String[size];
		AtomicInteger pos = new AtomicInteger(0);
		
		Thread threadProductor = new Thread("Productor") {
			public void run() {
				for (String line : lines) {
					if (pos.get() < size) {
						buffer[pos.getAndIncrement()] = line;
					}	
				}
			};

		};

		Thread threadConsumidor = new Thread("Consumidor") {
			public void run() {
				while (threadProductor.isAlive() || pos.get() != 0) {
					try {
						System.out.println(buffer[0]);
						bw.write(buffer[0]);
						bw.newLine();
					} catch (IOException e) {
						//Se ha controlado la excepcion
					} catch (NullPointerException mp) {
						// Se ha controlado la excepcion
					}
					for (int i = 1; i < pos.get(); i++) {
						buffer[i-1] = buffer[i];
					}
					pos.decrementAndGet();

				}
			};

		};
		
		threadProductor.start();
		threadConsumidor.start();
		
		threadConsumidor.join();
		
		bw.flush();
		fw.flush();
		
		fr.close();
		br.close();
		fw.close();
		bw.close();

	}
}
