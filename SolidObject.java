import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SolidObject {

	private int x, y;
	private int mapX, mapY;
	private int width, height;

	private Color colour;
	private boolean visible;

    private Background bg;

	public SolidObject (int mapX, int mapY, int width, int height, Color c, boolean isVisible, Background bg) { 
		this.mapX = mapX;
		this.mapY = mapY;
		this.width = width;
		this.height = height;
        this.bg = bg;

		colour = c;
		visible = isVisible;
	}


	public void draw (Graphics2D g2) {

        updateScreenCoordinates();

		if (!visible) //don't draw if object is invisible
			return;
		
		Rectangle2D.Double solidObject = new Rectangle2D.Double(x, y, width, height);
		g2.setColor(colour);
		g2.fill(solidObject);
	}


	public Rectangle2D.Double getBoundingRectangle() {
        updateScreenCoordinates();
		return new Rectangle2D.Double (x, y, width, height);
	}

    public void updateScreenCoordinates(){ 
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();


        if(bgX <0)
            bgX *= -1;
        if(bgY <0)
            bgY *= -1;


        x = mapX - bgX;
        y = mapY - bgY;
    }

    public void setVisible(Boolean v){
        visible = v;
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


	public int getHeight() {
		return height;
	}
}