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

   public void initLevelTwo(){
      solidObjects.clear();

      solidObjects.add(new SolidObject (0, 0, 307, 611, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (0, 608, 486, 344, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (0, 944, 421, 305, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (413, 1132, 612, 108, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1092, 883, 271, 296, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1314, 1115, 551, 134, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1789, 927, 487, 186, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1708, 578, 194, 327, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1911, 617, 216, 132, Color.GRAY,false, bg));
      solidObjects.add(new SolidObject (2057, 709, 160, 261, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2118, 981, 140, 268, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2236, 1180, 445, 58, Color.BLUE,false, bg));
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

   public void initLevelTwo() {
      solidObjects.clear();
   }

   public void initLevelThree() {
      solidObjects.clear();
     
      solidObjects.add(new SolidObject (752,748,117,286, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (456,783,299,293, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (375,783,83,289, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (0,757,377,261, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (0,1017,244,717, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (243,1213,25,521, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (265,1247,111,487, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (373,1282,72,85, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (445,1303,14,64, Color.RED,false, bg));
      solidObjects.add(new SolidObject (374,1466,17,268, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (386,1497,31,237, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (414,1527,973,207, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (551,1496,610,30, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (574,1447,60,54, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (588,1412,36,40, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (629,1484,522,15, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (650,1465,480,22, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (945,1178,146,290, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (842,1204,109,277, Color.RED,false, bg));
      solidObjects.add(new SolidObject (768,1242,74,229, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (750,1339,22,133, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (705,1377,51,93, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (685,1401,25,69, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (1303,1353,64,180, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1268,1499,40,36, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1373,1545,41,189, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1411,1566,1443,168, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1620,1364,78,203, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1586,1535,39,38, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1691,1532,44,40, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (1728,1555,1126,12, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1910,1250,139,307, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1880,1531,32,28, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (2041,1278,113,282, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (2149,1311,74,248, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (2221,1331,24,226, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (2245,1459,53,100, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2242,1425,27,36, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (2294,1475,22,84, Color.RED,false, bg));
      solidObjects.add(new SolidObject (2315,1526,33,29, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (2726,0,128,1585, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2438,1450,302,111, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2402,1528,42,30, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (2473,1381,267,81, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (2511,1352,218,31, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (2546,1316,182,38, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (2654,1285,78,36, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2687,816,41,473, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (2511,819,180,273, Color.RED,false, bg));
      solidObjects.add(new SolidObject (2435,813,76,291, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (2232,778,246,43, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2225,797,215,293, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2158,856,72,233, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (2091,855,69,199, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (2115,812,53,45, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (2043,883,52,135, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (2674,0,56,690, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2635,0,41,665, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (2610,0,26,645, Color.RED,false, bg));
      solidObjects.add(new SolidObject (2459,0,155,371, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (2385,0,77,417, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1965,0,426,460, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2061,452,316,115, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (1997,453,65,44, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1894,0,76,458, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1824,0,73,386, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1760,0,66,320, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1181,0,583,284, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1094,0,86,313, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1033,0,64,358, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (965,0,29,172, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (894,0,72,505, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (683,0,212,493, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (605,0,83,346, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (0,0,610,303, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (0,301,484,218, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (0,519,343,241, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (335,512,91,97, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (554,674,177,116, Color.RED,false, bg));
      solidObjects.add(new SolidObject (513,751,41,33, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (728,714,25,72, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (541,690,14,64, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1357,1090,352,76, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (1367,1073,335,21, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1389,1053,287,22, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1413,1025,243,30, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1436,1001,194,27, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1454,983,155,21, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1502,970,64,15, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1044,720,194,205, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (1235,646,114,202, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1347,642,71,145, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1343,782,70,39, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (1236,846,63,48, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1103,663,133,59, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1084,677,19,46, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (1061,702,23,20, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (1115,643,123,24, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (1156,630,176,17, Color.RED,false, bg));
      solidObjects.add(new SolidObject (1194,617,126,15, Color.WHITE,false, bg)); 
      solidObjects.add(new SolidObject (1581,638,334,181, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1549,606,36,152, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1580,606,188,34, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (1568,585,179,21, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (1586,564,139,23, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (1604,553,72,11, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (966,0,67,429, Color.MAGENTA,false, bg));

      //this one is the minecart
      solidObjects.add(new SolidObject (1070,495,72,15, Color.ORANGE,false, bg));
 }
}