package collectCoins;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CollectCoins extends Frame {
	
	private Scene scene = new Scene(this);
	private Panel bottomPanel = new Panel();
	private Label time = new Label();
	Panel centerPanel = new Panel();		//package jer im se pristupa i iz drugih klasa
	TextArea log = new TextArea();
	Label score = new Label(); 
	Timer timer;
 
	public CollectCoins() {
		setBounds(700, 200,  500, 300);
		setResizable(true);
		setTitle("Collect coins");
	
		populateWindow();
		showHelpDialog();
		pack(); 
		 
		addComponentListener(new ComponentAdapter() {	//kada se resize-uje prozor, da se sve ponovo preracuna
			@Override
			public void componentResized(ComponentEvent e) {
				scene.packScene();	//ponovo izracuna sve 
				repaint();
				pack();			// da se ostale komponente upakuju
			}
		});
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (timer != null) {		//prekidamo run metodu timera, da ne bi nastavio da zivi nakon kraja naseg programa
					timer.interrupt();
				}
				dispose();
			}
		});
	}

	private void showHelpDialog() {
		Dialog help = new Dialog(this, ModalityType.APPLICATION_MODAL);
		help.setTitle("Help");
		help.add(new Label("Use W - A - S - D to move.", Label.CENTER));
		help.setBounds(700, 200, 200, 100);
		help.setResizable(false);
		
		help.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				help.dispose();
			}
		});
		
		help.setVisible(true);
	}

	private void populateWindow() {
		log.setColumns(20);log.setRows(1);log.setEditable(false);	//korisnik ne sme da upisuje nista
		int dim = (getWidth()/2) / scene.getRows() * scene.getRows();	//dimenzija jenog kvadratica puta broj kvadratica - visak odbaci - tabela zauzme tacno koliko joj treba
		centerPanel.setBackground(Color.green);
		
		scene.setPreferredSize(new Dimension(dim, dim));	//bolje uvek postaviti setPrefferedSize, jer ako stavimo setSize - necemo moci da menjamo velicinu
		
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));		//center - kako se pakuju, 0,0 - rastojanje
		centerPanel.add(scene);
		add(centerPanel, BorderLayout.CENTER);
		add(log, BorderLayout.EAST);
		
		TextField coins = new TextField("10");
		Button toss = new Button("Toss");
		
		toss.addActionListener((ae)->{					//klikom na dugme se fokus prebacuje na dugme
			scene.tossCoins(Integer.parseInt(coins.getText()));
			scene.repaint();
			if (timer != null) {
				timer.interrupt();		//da ga prekinemo sigurno (da zavrsi run)
			}
			timer = new Timer(time);
			timer.start();
			timer.go();	
			scene.requestFocus();		//vracamo fokus sceni
		});
		
		bottomPanel.add(new Label("Time: "));
		bottomPanel.add(time);
		bottomPanel.add(new Label("Score: "));
		bottomPanel.add(score);
		bottomPanel.add(new Label("Coins"));
		bottomPanel.add(coins);
		bottomPanel.add(toss);
		add(bottomPanel, BorderLayout.SOUTH);		
	}
	
	public static void main(String[] args) {
		new CollectCoins();
	}
	
}

//prozor se zablokira kada AWT nit upadne u beskonacnu petlju





