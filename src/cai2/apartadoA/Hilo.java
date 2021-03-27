package cai2.apartadoA;



public class Hilo extends Thread{
	long x= 1;
	@Override
	public void run() {
		for(int i = 1 ; i <= 10 ; i++) {
			if (x%2==0) {
				try {
					double r = Math.random()*10;
					Thread.sleep((long) r);
					}catch (InterruptedException ie) { }
				x=x*2+1;
				System.out.println(x);
				
			}else {
				x++;
				System.out.println(x);
			}
		}
	}

}
