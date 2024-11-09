package collectCoins;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Figure {

	public Player(int x, int y, int width) {
		super(x, y, width);
	}

	@Override
	public void draw(Graphics g) {
		Color prevColor = g.getColor();				//zapamtimo koja je boja bila, da bi je vratili posle
		g.setColor(Color.BLACK);
		g.drawLine(x, y - width/2, x, y + width/2 - 1);		//-1 da pixel ne bi obojio jedan pixel mreze
		g.drawLine(x - width/2, y, x + width/2 - 1, y);
		g.setColor(prevColor);
	}

}
