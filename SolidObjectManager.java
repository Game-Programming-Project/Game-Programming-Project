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

      solidObjects.add(new SolidObject(815, 1091, 33, 48, Color.RED, false, bg));
      solidObjects.add(new SolidObject(780, 1122, 82, 49, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1079, 1140, 143, 159, Color.RED, false, bg));
      solidObjects.add(new SolidObject(893, 1012, 225, 827, Color.RED, false, bg));

      solidObjects.add(new SolidObject(1621, 1082, 191, 217, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1804, 1216, 335, 83, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1785, 1118, 45, 112, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2070, 1135, 69, 114, Color.RED, false, bg));
      // solidObjects.add(new SolidObject(,Color.RED,false,bg));

   }


   public void initLevelTwo() {
      solidObjects.clear();

      solidObjects.add(new SolidObject (1417,1645,364,138, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (1471,1468,231,185, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (1692,1558,52,95, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (569,1147,282,331, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (578,1469,225,307, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (780,1705,83,85, Color.MAGENTA,false, bg));
      solidObjects.add(new SolidObject (571,1764,3153,185, Color.ORANGE,false, bg));
      solidObjects.add(new SolidObject (2076,1687,610,89, Color.PINK,false, bg));
      solidObjects.add(new SolidObject (2299,1524,337,173, Color.RED,false, bg));
      solidObjects.add(new SolidObject (2352,1492,206,45, Color.WHITE,false, bg));
      solidObjects.add(new SolidObject (2160,1633,158,64, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (2163,1191,158,126, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (2187,1306, 80,103, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (2664,1734,585,85, Color.GREEN,false, bg));
      solidObjects.add(new SolidObject (3059,1637,221,112, Color.LIGHT_GRAY,false, bg));
      solidObjects.add(new SolidObject (3137, 1543,139,130, Color.MAGENTA,false, bg));
      // solidObjects.add(new SolidObject (, Color.ORANGE,false, bg));
      // solidObjects.add(new SolidObject (, Color.PINK,false, bg));
      // solidObjects.add(new SolidObject (, Color.RED,false, bg));
      // solidObjects.add(new SolidObject (, Color.WHITE,false, bg));

      solidObjects.add(new SolidObject (960,0,90,601, Color.YELLOW,false, bg));
      solidObjects.add(new SolidObject (893,0,76,642, Color.BLUE,false, bg));
      solidObjects.add(new SolidObject (0,0,892,777, Color.CYAN,false, bg));
      solidObjects.add(new SolidObject (0,772,806,81, Color.GREEN,false, bg));

      solidObjects.add(new SolidObject(1048, 523, 151, 99, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1189, 622, 106, 74, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1268, 696, 178, 197, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1414, 893, 34, 131, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1151, 1110, 200, 251, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1269, 2000, 174, 95, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1167, 1074, 169, 35, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1387, 1008, 31, 100, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1362, 1040, 27, 71, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1335, 1056, 28, 54, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1406, 1127, 39, 170, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1448, 1000, 33, 166, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1484, 954, 36, 136, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1515, 860, 37, 126, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1549, 847, 59, 49, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1591, 628, 39, 326, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1623, 623, 575, 71, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2197, 624, 101, 115, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2269, 741, 326, 175, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2591, 679, 72, 126, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2663, 678, 350, 90, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(3012, 717, 72, 82, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(3082, 789, 77, 84, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(3155, 821, 35, 728, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(3125, 1529, 33, 102, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(3094, 1556, 27, 110, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2804, 1729, 171, 26, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2698, 1701, 105, 53, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(3077, 1604, 17, 84, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(3055, 1640, 21, 71, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2976, 1701, 42, 60, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(3017, 1668, 35, 84, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2666, 1668, 32, 92, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2633, 1524, 36, 178, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2561, 1494, 72, 167, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2527, 1385, 40, 131, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2502, 1251, 93, 143, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2596, 1252, 38, 83, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2500, 1219, 52, 34, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2311, 1188, 196, 69, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2285, 1179, 26, 54, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2190, 1148, 98, 59, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2163, 1156, 26, 61, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2143, 1187, 50, 250, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2116, 1193, 34, 91, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2190, 1390, 51, 15, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2236, 1350, 22, 22, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2254, 1355, 16, 25, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2269, 1280, 28, 80, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2296, 1279, 31, 46, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2320, 1258, 48, 28, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2454, 1256, 72, 240, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2370, 1468, 85, 39, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2431, 1444, 26, 27, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2179, 1594, 122, 40, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2265, 1537, 38, 55, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2282, 1519, 59, 25, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2334, 1474, 34, 58, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2311, 1496, 23, 22, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1810, 1736, 240, 41, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1742, 1565, 38, 172, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1782, 1712, 28, 67, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2163, 1612, 17, 42, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2046, 1708, 39, 68, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2133, 1634, 30, 55, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2115, 1657, 18, 55, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2084, 1676, 30, 62, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1666, 1449, 39, 99, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1707, 1526, 35, 57, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1581, 1441, 98, 43, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1506, 1406, 79, 65, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1482, 1431, 21, 73, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1450, 1449, 32, 208, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(959, 1739, 377, 42, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1417, 1641, 34, 116, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1390, 1665, 25, 101, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1368, 1696, 22, 76, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1333, 1722, 37, 53, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(839, 1703, 117, 77, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(779, 1495, 36, 204, Color.RED, false, bg));
      solidObjects.add(new SolidObject(811, 1676, 28, 65, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(852, 1222, 33, 257, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(807, 1468, 44, 37, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(806, 1156, 42, 80, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(689, 1142, 138, 35, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(666, 820, 35, 326, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(700, 1111, 29, 35, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(695, 824, 77, 53, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(767, 804, 38, 108, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(795, 788, 52, 42, Color.RED, false, bg));
      solidObjects.add(new SolidObject(841, 750, 44, 128, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(855, 645, 31, 107, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(860, 575, 97, 81, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(953, 560, 92, 57, Color.CYAN, false, bg));

   }

   public void initLevelThree() {
      solidObjects.clear();

      solidObjects.add(new SolidObject(752, 748, 117, 286, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(456, 783, 299, 293, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(375, 783, 83, 289, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(0, 757, 377, 261, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(0, 1017, 244, 717, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(243, 1213, 25, 521, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(265, 1247, 111, 487, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(373, 1282, 72, 85, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(445, 1303, 14, 64, Color.RED, false, bg));
      solidObjects.add(new SolidObject(374, 1466, 17, 268, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(386, 1497, 31, 237, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(414, 1527, 973, 207, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(551, 1496, 610, 30, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(574, 1447, 60, 54, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(588, 1412, 36, 40, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(629, 1484, 522, 15, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(650, 1465, 480, 22, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(945, 1178, 146, 290, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(842, 1204, 109, 277, Color.RED, false, bg));
      solidObjects.add(new SolidObject(768, 1242, 74, 229, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(750, 1339, 22, 133, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(705, 1377, 51, 93, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(685, 1401, 25, 69, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1303, 1353, 64, 180, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1268, 1499, 40, 36, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1373, 1545, 41, 189, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1411, 1566, 1443, 168, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1620, 1364, 78, 203, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1586, 1535, 39, 38, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1691, 1532, 44, 40, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1728, 1555, 1126, 12, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1910, 1250, 139, 307, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1880, 1531, 32, 28, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2041, 1278, 113, 282, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2149, 1311, 74, 248, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2221, 1331, 24, 226, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2245, 1459, 53, 100, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2242, 1425, 27, 36, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2294, 1475, 22, 84, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2315, 1526, 33, 29, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2726, 0, 128, 1585, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2438, 1450, 302, 111, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2402, 1528, 42, 30, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2473, 1381, 267, 81, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2511, 1352, 218, 31, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2546, 1316, 182, 38, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2654, 1285, 78, 36, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2687, 816, 41, 473, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2511, 819, 180, 273, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2435, 813, 76, 291, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2232, 778, 246, 43, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(2225, 797, 215, 293, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2158, 856, 72, 233, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(2091, 855, 69, 199, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(2115, 812, 53, 45, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(2043, 883, 52, 135, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(2674, 0, 56, 690, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(2635, 0, 41, 665, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(2610, 0, 26, 645, Color.RED, false, bg));
      solidObjects.add(new SolidObject(2459, 0, 155, 371, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(2385, 0, 77, 417, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1965, 0, 426, 460, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(2061, 452, 316, 115, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1997, 453, 65, 44, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1894, 0, 76, 458, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1824, 0, 73, 386, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1760, 0, 66, 320, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1181, 0, 583, 284, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1094, 0, 86, 313, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1033, 0, 64, 358, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(965, 0, 29, 172, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(894, 0, 72, 505, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(683, 0, 212, 493, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(605, 0, 83, 346, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(0, 0, 610, 303, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(0, 301, 484, 218, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(0, 519, 343, 241, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(335, 512, 91, 97, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(554, 674, 177, 116, Color.RED, false, bg));
      solidObjects.add(new SolidObject(513, 751, 41, 33, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(728, 714, 25, 72, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(541, 690, 14, 64, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1357, 1090, 352, 76, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1367, 1073, 335, 21, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1389, 1053, 287, 22, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1413, 1025, 243, 30, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1436, 1001, 194, 27, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1454, 983, 155, 21, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1502, 970, 64, 15, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1044, 720, 194, 205, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1235, 646, 114, 202, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1347, 642, 71, 145, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1343, 782, 70, 39, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1236, 846, 63, 48, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1103, 663, 133, 59, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1084, 677, 19, 46, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(1061, 702, 23, 20, Color.ORANGE, false, bg));
      solidObjects.add(new SolidObject(1115, 643, 123, 24, Color.PINK, false, bg));
      solidObjects.add(new SolidObject(1156, 630, 176, 17, Color.RED, false, bg));
      solidObjects.add(new SolidObject(1194, 617, 126, 15, Color.WHITE, false, bg));
      solidObjects.add(new SolidObject(1581, 638, 334, 181, Color.YELLOW, false, bg));
      solidObjects.add(new SolidObject(1549, 606, 36, 152, Color.BLUE, false, bg));
      solidObjects.add(new SolidObject(1580, 606, 188, 34, Color.CYAN, false, bg));
      solidObjects.add(new SolidObject(1568, 585, 179, 21, Color.GREEN, false, bg));
      solidObjects.add(new SolidObject(1586, 564, 139, 23, Color.LIGHT_GRAY, false, bg));
      solidObjects.add(new SolidObject(1604, 553, 72, 11, Color.MAGENTA, false, bg));
      solidObjects.add(new SolidObject(966, 0, 67, 429, Color.MAGENTA, false, bg));

      // this one is the minecart
      solidObjects.add(new SolidObject(1070, 495, 72, 15, Color.ORANGE, false, bg));
   }
}