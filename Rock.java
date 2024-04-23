import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Rock {
    private Image rockImage, destroyedRockImage;
    private int width, height;
    private int x, y;

    private int mapX, mapY;
    private GamePanel gPanel;

    private Background bg;

    private SoundManager soundManager;

    private boolean destroyed;
    private boolean disappearCompleted;

    private DisappearFX disappearFX;

    public Rock (GamePanel gPanel, int mapX, int mapY, Background bg) {

        this.mapX = mapX;
        this.mapY = mapY;
        this.gPanel = gPanel;
        this.bg = bg;

        width = height = 30;

        soundManager = SoundManager.getInstance();
        
        rockImage = ImageManager.loadImage("images/Rocks/Rock_1.png");
        destroyedRockImage = ImageManager.loadImage("images/Rocks/destroyedRock.png");

        disappearFX=null;
        destroyed = false;
        disappearCompleted = false;
    }

    public void draw(Graphics2D g2) {

        updateScreenCoordinates();
        
        if(destroyed){ // draw if rock is NOT destroyed
            //g2.drawImage(destroyedRockImage, x, y, width, height, null);
            if(disappearFX!=null){
                disappearFX.draw(g2);
                if(disappearFX.isCompleted())
                    disappearCompleted = true;
            }
        }
        else
            g2.drawImage(rockImage, x, y, width, height, null);

    }

    //this method is needed to ensure the entity stays where you want it to on the background map coordinates
    //the x and y that is updated are the coordinates to be drawn to the screen
    public void updateScreenCoordinates(){ //meant to be called right before the entity is drawn to the screen
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        //if bgX or bgY are negative then make them positive
        if(bgX <0)
            bgX *= -1;
        if(bgY <0)
            bgY *= -1;

        //positioning the entity on the screen relative to the background
        //this calculation makes it so that the entity stays on the map where it needs to be
        x = mapX - bgX;
        y = mapY - bgY;
    }

    public void destroy() {
        soundManager.playClip("break_rock", false);
    }

    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

    public boolean collidesWithPlayer(Player p) {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = p.getBoundingRectangle();
        
        return myRect.intersects(playerRect); 
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setFX(DisappearFX fx){
        disappearFX = fx;
     }
  
     public String getRockImageString(){ // method to return the current image of the rock(destroyed and alive)
        if(!destroyed)
            return "images/Rocks/Rock_1.png";
        else
            return "images/Rocks/destroyedRock.png";
     }

    public int getX() {
        updateScreenCoordinates();
        return x;
    }

    public int getY() {
        updateScreenCoordinates();
        return y;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDisappearCompleted() {
        return disappearCompleted;
    }

    public void updateFX(){
        if(disappearFX != null){
            disappearFX.update();
           if(disappearFX.isCompleted()){
                disappearFX = null;
           }
        }
    }

}
