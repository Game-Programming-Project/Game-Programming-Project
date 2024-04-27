import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;

public class SolidObjectManager {

   private ArrayList<SolidObject> solidObjects;
   private Background bg;
  
   public SolidObjectManager () {
      solidObjects = new ArrayList<SolidObject>();
 }

   public SolidObjectManager (Background bg) {
        this();
        solidObjects = new ArrayList<SolidObject>();
   }

   public void draw (Graphics2D g2) {
   
      for (int i=0; i<solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         solidObject.draw (g2);
      }

   }

   public SolidObject collidesWith(Rectangle2D.Double boundingRectangle) {

      for (int i=0; i<solidObjects.size(); i++) {

	      SolidObject solidObject = solidObjects.get(i);
	      Rectangle2D.Double rect = solidObject.getBoundingRectangle();

	      if (rect.intersects (boundingRectangle)) {
		      return solidObjects.get(i);
	      }
      }

      return null;

   }

   public boolean collidesWithSolid(Rectangle2D.Double boundingRectangle) {

    for (int i=0; i<solidObjects.size(); i++) {

        SolidObject solidObject = solidObjects.get(i);
        Rectangle2D.Double rect = solidObject.getBoundingRectangle();

        if (rect.intersects(boundingRectangle)) {
            return true;
        }
    }

    return false;

   }

   //given coordinates, tells if the object is on a solid object
   public boolean onSolidObject(int mapX, int mapY, int width, int height) {
      for (int i=0; i<solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);

         int solidLeft = solidObject.getMapX();
         int solidRight = solidObject.getMapX() + solidObject.getWidth() - 1;

         int solidTop = solidObject.getMapY();
         int solidBottom = solidObject.getMapY() + solidObject.getHeight() - 1;

         if(mapX + width > solidLeft && mapX <= solidRight && mapY + height > solidTop && mapY <= solidBottom)
            return true;
      }

      return false;
   }

   public void setAllObjectsVisible(boolean v) {
      for (int i=0; i<solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         solidObject.setVisible(v);
      }
   }

   //checks if any rocks associated with solid objects are destroyed and deletes the solid if the rock was destroyed
   public void removeDestroyedRocks(){
      for (int i=0; i<solidObjects.size(); i++) {

         SolidObject solidObject = solidObjects.get(i);

         Rock r = solidObject.getRock();

         if(r != null && r.isDestroyed()){
            solidObjects.remove(i);
            i--;
         }

      }
   }

   public void addSolidObject(SolidObject solidObject) {
        solidObjects.add(solidObject);
   }

   public void addSolidObject(int mapX, int mapY, int width, int height, Color colour, boolean visible) {
      solidObjects.add(new SolidObject(mapX, mapY, width, height, colour, visible, bg));
   }

   public void setBg(Background bg) {
      this.bg = bg;
   }

   public void initLevelOne(){
        solidObjects.clear();

        solidObjects.add(new SolidObject (865, 692, 208, 156, Color.YELLOW,true, bg));
        solidObjects.add(new SolidObject (814, 647, 50, 148, Color.BLUE,true, bg));
        solidObjects.add(new SolidObject (624,602,189,116, Color.RED,true, bg));
        solidObjects.add(new SolidObject (601, 716, 32, 428, Color.GREEN,true, bg));
        solidObjects.add(new SolidObject (633, 1123, 156, 51, Color.PINK,true, bg));
   }

   public void initLevelTwo(){
      solidObjects.clear();

      solidObjects.add(new SolidObject (0, 0, 307, 611, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (0, 608, 486, 344, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (0, 944, 421, 305, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (413, 1132, 612, 108, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1049, 866, 333, 383, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1314, 1115, 551, 134, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1789, 927, 487, 186, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1708, 578, 194, 327, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1911, 617, 216, 132, Color.GRAY,false, bg));
      solidObjects.add(new SolidObject (2057, 709, 177, 261, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2118, 981, 149, 268, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2241, 1130, 608, 119, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2685, 965, 327, 160, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (2746, 267, 81, 741, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (2135, 136, 667, 174, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1877, 170, 291, 238, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1179, 53, 704, 182, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (832, 0, 408, 468, Color.RED,false, bg));
      solidObjects.add(new SolidObject (956, 410, 160, 157, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (763, 536, 276, 249, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (439, 0, 498, 173, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (218, 0, 266, 334, Color.MAGENTA,false, bg));
   }

   public void initLevelThree(){
      solidObjects.clear();

      solidObjects.add(new SolidObject (1359, 1094, 353, 136, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1380, 1071, 313, 27, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1416, 1032, 242, 43, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1448, 998, 172, 34, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1469, 982, 133, 17, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (296, 233, 2414,107, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (98,227,139,1406, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (2625,340,123,371, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (2716,707,42,135, Color.GRAY,false, bg));
      solidObjects.add(new SolidObject (2461,813,257,30, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1850,338,626,74, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2041,404,317,179, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2354,408,92,81, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1913,407,133,99, Color.GREEN,false, bg));

 }

}