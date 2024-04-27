import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;

public class SolidObjectManager {

   private ArrayList<SolidObject> solidObjects;
   private Background bg;

   public SolidObjectManager() {
      solidObjects = new ArrayList<SolidObject>();
   }

   public SolidObjectManager(Background bg) {
      this();
      solidObjects = new ArrayList<SolidObject>();
   }

   public void draw(Graphics2D g2) {

      for (int i = 0; i < solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         solidObject.draw(g2);
      }

   }

   public SolidObject collidesWith(Rectangle2D.Double boundingRectangle) {

      for (int i = 0; i < solidObjects.size(); i++) {

         SolidObject solidObject = solidObjects.get(i);
         Rectangle2D.Double rect = solidObject.getBoundingRectangle();

         if (rect.intersects(boundingRectangle)) {
            return solidObjects.get(i);
         }
      }

      return null;

   }

   public boolean collidesWithSolid(Rectangle2D.Double boundingRectangle) {

      for (int i = 0; i < solidObjects.size(); i++) {

         SolidObject solidObject = solidObjects.get(i);
         Rectangle2D.Double rect = solidObject.getBoundingRectangle();

         if (rect.intersects(boundingRectangle)) {
            return true;
         }
      }

      return false;

   }

   // given coordinates, tells if the object is on a solid object
   public boolean onSolidObject(int mapX, int mapY, int width, int height) {
      for (int i = 0; i < solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);

         int solidLeft = solidObject.getMapX();
         int solidRight = solidObject.getMapX() + solidObject.getWidth() - 1;

         int solidTop = solidObject.getMapY();
         int solidBottom = solidObject.getMapY() + solidObject.getHeight() - 1;

         if (mapX + width > solidLeft && mapX <= solidRight && mapY + height > solidTop && mapY <= solidBottom)
            return true;
      }

      return false;
   }

   public void setAllObjectsVisible(boolean v) {
      for (int i = 0; i < solidObjects.size(); i++) {
         SolidObject solidObject = solidObjects.get(i);
         solidObject.setVisible(v);
      }
   }

   // checks if any rocks associated with solid objects are destroyed and deletes
   // the solid if the rock was destroyed
   public void removeDestroyedRocks() {
      for (int i = 0; i < solidObjects.size(); i++) {

         SolidObject solidObject = solidObjects.get(i);

         Rock r = solidObject.getRock();

         if (r != null && r.isDestroyed()) {
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

   public void initLevelOne() {
      solidObjects.clear();

      // adding solid objects to create boundaries on map

      solidObjects.add(new SolidObject(651, 584, 138, 64, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(631, 648, 20, 71, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(610, 725, 23, 399, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(650, 1140, 133, 31, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(611, 1122, 38, 45, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(881, 1001, 245, 135, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(867, 1024, 14, 113, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(841, 1039, 26, 98, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(803, 1090, 38, 58, Color.RED, false, bg));
      solidObjects.add(new SolidObject(791, 1102, 13, 56, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(775, 1118, 15, 26, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(790, 647, 27, 56, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(816, 673, 68, 96, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(883, 751, 187, 49, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(865, 768, 19, 32, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1049, 624, 21, 128, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1121, 582, 263, 69, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1071, 623, 49, 54, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1384, 570, 160, 54, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1537, 602, 44, 140, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1580, 652, 39, 116, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1618, 681, 182, 201, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1797, 686, 27, 119, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1820, 687, 156, 61, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1976, 687, 77, 43, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2053, 709, 58, 43, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2090, 752, 21, 383, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2070, 1124, 21, 85, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2048, 1141, 22, 80, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2034, 1158, 15, 67, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2014, 1176, 20, 58, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1994, 1197, 20, 54, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1980, 1211, 14, 50, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1934, 1216, 46, 50, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1908, 1209, 25, 58, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1876, 1175, 23, 104, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1898, 1213, 9, 52, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1833, 1148, 41, 110, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1816, 1098, 33, 68, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1653, 1056, 148, 30, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1802, 1071, 16, 50, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1635, 1065, 19, 54, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1617, 1080, 17, 57, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1592, 1097, 24, 72, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1574, 1152, 18, 35, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1515, 1168, 59, 62, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1450, 1170, 65, 65, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1425, 1193, 25, 46, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1244, 1214, 182, 32, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1210, 1192, 34, 56, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1184, 1145, 43, 48, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1127, 1127, 57, 55, Color.BLUE, false, bg));

   }

   public void initLevelTwo() {
      solidObjects.clear();
   }

   public void initLevelThree() {
      solidObjects.clear();

      solidObjects.add(new SolidObject(1359, 1094, 353, 136, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1380, 1071, 313, 27, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1416, 1032, 242, 43, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1448, 998, 172, 34, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1469, 982, 133, 17, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(296, 233, 2414, 107, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(98, 227, 139, 1406, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2625, 340, 123, 371, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2716, 707, 42, 135, Color.GRAY, false, bg));
      solidObjects.add(new SolidObject(2461, 813, 257, 30, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1850, 338, 626, 74, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2041, 404, 317, 179, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2354, 408, 92, 81, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1913, 407, 133, 99, Color.GREEN, false, bg));

   }

}