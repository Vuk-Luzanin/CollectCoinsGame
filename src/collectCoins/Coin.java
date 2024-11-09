package collectCoins;

import java.awt.Color;
import java.awt.Graphics;

public class Coin extends Figure {	

	public Coin(int x, int y, int width) {
		super(x, y, width);
	}

	@Override
	public void draw(Graphics g) {
		Color prevColor = g.getColor();				//zapamtimo koja je boja bila, da bi je vratili posle
		g.setColor(Color.YELLOW);
		g.fillOval(x - width/2, y - width/2, width, width);
		g.setColor(prevColor);
	}

}

