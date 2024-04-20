import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;

public class SolidObjectManager {

   private ArrayList<SolidObject> solidObjects;
   private Background bg;
  
   public SolidObjectManager (Background bg) {
        this.bg = bg;
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


   public boolean onSolidObject(int x, int width) {

      for (int i=0; i<solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         int solidRight = solidObject.getX() + solidObject.getWidth() - 1;

         if (x + width > solidObject.getX() && x <= solidRight) {
            return true;
         }
      }

      return false;

   }

   public void setAllObjectsVisible(boolean v) {
      for (int i=0; i<solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         solidObject.setVisible(v);
      }
   }

   public void addSolidObject(SolidObject solidObject) {
        solidObjects.add(solidObject);
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
   }

}