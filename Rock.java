import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Rock {
    protected Image rockImage, destroyedRockImage, ladderImage, fruitImage;
    private int width, height;
    private int x, y;

    private int mapX, mapY;
    private GamePanel gPanel;

    protected int materialValue;
    protected int scoreValue;

    private Background bg;

    private SoundManager soundManager;

    private boolean destroyed;
    private boolean disappearCompleted;

    private DisappearFX disappearFX;

    private Boolean hasLadder;
    private Boolean hasFruit;
    private Boolean fruitEaten;

    public Rock(GamePanel gPanel, int mapX, int mapY, Background bg) {

        this.mapX = mapX;
        this.mapY = mapY;
        this.gPanel = gPanel;
        this.bg = bg;

        width = height = 30;

        materialValue=0;
        scoreValue=5;

        soundManager = SoundManager.getInstance();

        rockImage = ImageManager.loadImage("images/Rocks/basicRock.png");
        destroyedRockImage = ImageManager.loadImage("images/Rocks/destroyedRock.png");

        disappearFX = null;
        destroyed = false;
        disappearCompleted = false;
        hasLadder=false;
        hasFruit=false;
        fruitEaten=false;
        fruitImage=null;
    }

    public Rock(GamePanel gPanel, int mapX, int mapY, Background bg, Boolean hasLadder){
        this(gPanel, mapX, mapY, bg);

        this.hasLadder=hasLadder;
        if(hasLadder)
            setLadderImage();
    }

    public Rock(GamePanel gPanel, int mapX, int mapY, Background bg, Boolean hasLadder, Boolean hasFruit){
        this(gPanel, mapX, mapY, bg, hasLadder);

        this.hasFruit=hasFruit;
        if(hasFruit)
            setFruitImage();
    }

    public void draw(Graphics2D g2) {

        updateScreenCoordinates();

        if(destroyed && hasFruit && !fruitEaten){
            g2.drawImage(fruitImage, x, y, width, height, null);
            return;
        }

        if(destroyed && hasLadder){
            g2.drawImage(ladderImage, x, y, width, height, null);
            return;
        }

        if (destroyed) { // draw disappear effect if rock is destroyed
            if (disappearFX != null) {
                disappearFX.draw(g2);
                if (disappearFX.isCompleted())
                    disappearCompleted = true;
            }
        } else
            g2.drawImage(rockImage, x, y, width, height, null);

    }

    // this method is needed to ensure the entity stays where you want it to on the
    // background map coordinates
    // the x and y that is updated are the coordinates to be drawn to the screen
    public void updateScreenCoordinates() { // meant to be called right before the entity is drawn to the screen

        if(bg==null)
            return;

        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        // if bgX or bgY are negative then make them positive
        if (bgX < 0)
            bgX *= -1;
        if (bgY < 0)
            bgY *= -1;

        // positioning the entity on the screen relative to the background
        // this calculation makes it so that the entity stays on the map where it needs
        // to be
        x = mapX - bgX;
        y = mapY - bgY;
    }

    public void destroy() {
        soundManager.playClip("break_rock", false);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
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

    public void setFX(DisappearFX fx) {
        disappearFX = fx;
    }

    public Boolean hasLadder(){
        return hasLadder;
    }

    public void setHasLadder(Boolean hasLadder){
        this.hasLadder=hasLadder;
    }

    public void setLadderImage(){
        String level = gPanel.getCurrentLevel();
        if(level=="1")
            ladderImage=ImageManager.loadImage("images/Rocks/Level1_ladder.png");
        
        if(level=="2")
            ladderImage=ImageManager.loadImage("images/Rocks/Level2_ladder.png");
        
        if(level=="3")
            ladderImage=ImageManager.loadImage("images/Rocks/Level3_ladder.png");
    }

    public String getRockImageString() { // method to return the current image of the rock(destroyed and alive)
        if (!destroyed)
            return "images/Rocks/basicRock.png";
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

    public Boolean hasFruit(){
        return hasFruit;
    }

    public void setHasFruit(Boolean f){
        
        if(f && !hasFruit)
            setFruitImage();

        this.hasFruit=f;
    }

    public void setFruitEaten(Boolean fruitEaten){
        this.fruitEaten=fruitEaten;
    }

    public void setFruitImage(){
        String level = gPanel.getCurrentLevel();
        if(level=="1")
            fruitImage=ImageManager.loadImage("images/Player/Hearts/starfruit.png");
        
        if(level=="2")
            fruitImage=ImageManager.loadImage("images/Potions/Level2/level2Potion.png");
        
        if(level=="3")
            fruitImage=ImageManager.loadImage("images/Player/Hearts/Prismatic_Shard.png");
    }

    public void updateFX() {
        if (disappearFX != null) {
            disappearFX.update();
            if (disappearFX.isCompleted()) {
                disappearFX = null;
            }
        }
    }

    public int getMaterialValue(){
        return materialValue;
    }

    public int getScoreValue(){
        return scoreValue;
    }

}
