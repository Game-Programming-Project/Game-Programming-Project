import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class HealthDisplay {
    private Image fullHeart;
    private Image halfHeart;
    private Image emptyHeart;
    private int x, y;

    private int width, height;

    private int maxHealth; // adjust this value to change the maximum number of hearts

    public HealthDisplay(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            fullHeart = ImageManager.loadImage("images/Player/Hearts/fullHeart.png");
            halfHeart = ImageManager.loadImage("images/Player/Hearts/halfHeart.png");
            emptyHeart = ImageManager.loadImage("images/Player/Hearts/emptyHeart.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        width = 30;
        height = 28;

        maxHealth=10;
    }

    //code to show the hearts on the top left of the screen
    // public void draw(Graphics g, int health) {
    //     int fullHearts = health / 2;
    //     int halfHearts = health % 2;
    //     int emptyHearts = 5 - fullHearts - halfHearts; // assuming max health is 10
    //     int spacing = 10; // adjust this value to change the spacing between hearts
    
    //     for (int i = 0; i < fullHearts; i++) {
    //         g.drawImage(fullHeart, x + i * (width + spacing), y, width, height, null);
    //     }
    //     if (halfHearts > 0) {
    //         g.drawImage(halfHeart, x + fullHearts * (width + spacing), y, width, height, null);
    //     }
    //     for (int i = 0; i < emptyHearts; i++) {
    //         g.drawImage(emptyHeart, x + (fullHearts + halfHearts) * (width + spacing) + i * (width + spacing), y, width, height, null);
    //     }
    // }

    //code to show the hearts on the top right of the screen
    public void draw(Graphics g, int health, int screenWidth) {
        int fullHearts = health / 2;
        int halfHearts = health % 2;
        int emptyHearts = maxHealth / 2 - fullHearts - halfHearts;
        int spacing = 10; // adjust this value to change the spacing between hearts
    
        for (int i = 0; i < fullHearts; i++) {
            g.drawImage(fullHeart, screenWidth - (i + 1) * (width + spacing), y, width, height, null);
        }
        if (halfHearts > 0) {
            g.drawImage(halfHeart, screenWidth - (fullHearts + 1) * (width + spacing), y, width, height, null);
        }
        for (int i = 0; i < emptyHearts; i++) {
            g.drawImage(emptyHeart, screenWidth - (fullHearts + halfHearts + i + 1) * (width + spacing), y, width, height, null);
        }
    }

    public void setMaxHealth(int h){
        maxHealth=h;
    }

}