package cai2.apartadoA;

public class HiloString extends Thread {
	String i = "";
	@Override
	public void run() {
		String s = "Esto es una prueba sobre las condiciones de carrera";
		for (int u = 0; u<s.length();u++) {
			double r = Math.random()*15;
			try {
				Thread.sleep((long) r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i = i+s.charAt(u);
		}
		System.out.println(i);
	}
}
