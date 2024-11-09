package collectCoins;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Scene extends Canvas {
	
	private int squareWidth;
	private CollectCoins owner;
	private int rows = 10;
	private int score = 0;
	private Player player; 
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	 
	public void tossCoins(int coins) {
		owner.score.setText("");
		owner.log.setText("");
		player = null;
		figures = new ArrayList<Figure>();
		score = 0;
		
		while (coins >= rows*rows) {
			coins /= 4;
		}
		
		int x, y, coinR = squareWidth / 2;
		for (int i=0; i<coins; i++) {
			x = ((int) (Math.random() * rows)) * squareWidth + coinR;	//x koordinata centra novcica
			y = ((int) (Math.random() * rows)) * squareWidth + coinR;	//y koordinata centra novcica
			Coin c = new Coin(x, y, coinR);
			if (figures.contains(c)) {
				i--;
				continue;
			}
			figures.add(c);
		}
		do {
			x = ((int) (Math.random() * rows)) * squareWidth + squareWidth/2;	
			y = ((int) (Math.random() * rows)) * squareWidth + squareWidth/2;
			player = new Player(x, y, squareWidth);
		} while(figures.contains(player));			//dok igrac ne bude na razlicitoj poziciji od novcica
		figures.add(player);
	}
	
	@Override
	public void paint(Graphics g) {
		squareWidth = getDim()/rows;
		adjustScore();
		drawLines();
		drawFigures();
	}

	private void drawFigures() {
		if (player == null) {
			return;
		}
		for (Figure f : figures) {
			f.draw(getGraphics());
		}
		player.draw(getGraphics());
	}

	private void drawLines() {
		int dim = getDim();			//visina(sirina) cele scene
		int step = dim / rows;		//velicina celije
		Graphics graphics = getGraphics();
		graphics.setColor(Color.BLUE);
		for (int i=0; i<dim; i+=step) {
			graphics.drawLine(0, i, dim-1, i);
			graphics.drawLine(i, 0, i, dim-1);
		}
	}

	private int getDim() {
		int width = owner.centerPanel.getWidth();
		int height = owner.centerPanel.getHeight();
		int w = width / rows * rows;
		int h = height / rows * rows;
		return Math.max(w, h);		//dohvati vecu od izracunate visine i sirine
	}

	private void adjustScore() {
		for (Figure f : figures) {
			if (player.equals(f) && player != f) {
				score++;
				owner.score.setText("" + score);
				owner.log.append("Coin collected at: " + owner.timer.toString() + "\n");
				figures.remove(f);
				if(figures.size() == 1) {
					owner.timer.interrupt();	//zavrsili smo - gasimo Timer
					owner.log.append("BRAVO!\n");
				}
				
				break;
			}
		}
		
	}

	public Scene(CollectCoins owner) {
		this.owner = owner;
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_W) {
					int y = player.getY() - squareWidth;
					player.setY(y < 0 ? player.getY() : y);
					
				} else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_S) {
					int y = player.getY() + squareWidth;
					player.setY(y > getHeight() ? player.getY() : y);
					
				} else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_A) {
					int x = player.getX() - squareWidth;
					player.setX(x < 0 ? player.getX() : x);
					
				} else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_D) {
					int x = player.getX() + squareWidth;
					player.setX(x > getWidth() ? player.getX() : x);
				}
				repaint();
			}
		});
	}

	public int getRows() {
		return rows;
	}

	public void packScene() {
		int dim = getDim();
		int oldSquareWidth = squareWidth;	//da sacuvamo proporciju
		squareWidth = dim / rows;
		int x, y;
		for (Figure f : figures) {
			x =  ((int)(f.getX() / 1.0 / oldSquareWidth) * squareWidth) + squareWidth/2; //+ squareWidth/2 da bi postavili figuru u centar
			y =  ((int)(f.getY() / 1.0 / oldSquareWidth) * squareWidth) + squareWidth/2; 
			f.setX(x);
			f.setY(y);
			f.setWidth(squareWidth / 2);
		}
		if (player != null) {
			player.setWidth(squareWidth);	//da bi pregazili prethodno postavljeno f.setWidth(squareWidth / 2);, jer je player u figures
		}
		setPreferredSize(new Dimension(dim, dim));
			
	}



}
