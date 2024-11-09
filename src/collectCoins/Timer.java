package collectCoins;

import java.awt.Label;

public class Timer extends Thread {
	
	private Label label;
	private int s, m;
	private boolean work;

	public Timer(Label label) {
		super();
		this.label = label;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {		//dok nas neko ne zaustavi
				
				synchronized (this) {		//da krenemo tek kad nam neko kaze da krenemo
					while (!work) {
						wait();
					}		
				}
				label.setText(toString());
				label.revalidate();
				sleep(1000);
				s++;
				if (s%60 == 0) {
					m++;
					s = 0;
				}
			}
		} catch (InterruptedException e) {}
	} 
	
	public synchronized void go() {		//pocni
		work = true;					
		notifyAll();					//signal niti da izadje iz wait
	}
	
	public synchronized void pause() {		//da se pauziramo na wait-u
		work = false;						
	}
	
	public synchronized void reset() {	
		m = s = 0;					
	}
	
	
	@Override
	public String toString() {
		return String.format("%02d:%02d", m, s);
	}

	
}
