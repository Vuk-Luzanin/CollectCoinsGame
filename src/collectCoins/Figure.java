package collectCoins;

import java.awt.Graphics;

public abstract class Figure {	//x i y cu koordinate centra celije

	protected int x, y, width;

	public Figure(int x, int y, int width) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Figure)) {
			return false;
		}
		Figure f = (Figure) obj;
		return this.x==f.x && this.y==f.y;
	}
	
	public abstract void draw(Graphics g);
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}




