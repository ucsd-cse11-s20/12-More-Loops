
import tester.*;
class Point {
  int x;
  int y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  boolean belowLeftOf(Point other) {
    return this.x <= other.x && this.y <= other.y;
  }
  boolean aboveRightOf(Point other) {
    return this.x >= other.x && this.y >= other.y;
  }
  double distance(Point other) {
    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }
}
interface Region { // Added an interface declaration with the shared method
  boolean contains(Point p);
}
class RectRegion implements Region { // Declared "implements Region" (the interface)
  Point lowerLeft;
  Point upperRight;
  RectRegion(Point lowerLeft, Point upperRight) {
    this.lowerLeft = lowerLeft;
    this.upperRight = upperRight;
  }
  public boolean contains(Point p) {
    return this.lowerLeft.belowLeftOf(p) && this.upperRight.aboveRightOf(p);
  }
}


class CircRegion implements Region {
  Point center;
  int radius;
  CircRegion(Point center, int radius) {
    this.center = center;
    this.radius = radius;
  }
  public boolean contains(Point p) {
    return this.center.distance(p) < this.radius;    
  }
}
abstract class ComboRegion implements Region{
  Region r1;
  Region r2;
  ComboRegion(Region r1, Region r2) {
    this.r1 = r1;
    this.r2 = r2;
  }
}
class UnionRegion extends ComboRegion {
  UnionRegion(Region r1, Region r2) {
    super(r1, r2);
  }
  public boolean contains(Point p) {
    return this.r1.contains(p) || this.r2.contains(p);
  }
}
class IntersectRegion extends ComboRegion {
  IntersectRegion(Region r1, Region r2) {
    super(r1, r2);
  }
  public boolean contains(Point p) {
    return this.r1.contains(p) && this.r2.contains(p);
  }
}
class SquareRegion implements Region {
  Point center;
  double sideLength;
  SquareRegion(Point center, double sideLength) {
    this.center = center;
    this.sideLength = sideLength;
  }
  public boolean contains(Point p) {
    int xDiff = Math.abs(p.x - this.center.x);
    int yDiff = Math.abs(p.y - this.center.y);
    boolean xInside = (xDiff < (this.sideLength / 2));
    boolean yInside = (yDiff < (this.sideLength / 2));
    return xInside && yInside;
  }
}
class LoopsAndRegions {

  String visualize(Region r, int xMin, int xMax, int yMin, int yMax) {
    String vis = "\n";
    for(int y = yMin; y < yMax; y += 1) {
      for(int x = xMin; x < xMax; x += 1) {
        if(r.contains(new Point(x, y))) {
          vis += "##";
        }
        else {
          vis += "__";
        }
      }
      vis += "\n";
    }
    return vis;
  }

  Region c1 = new CircRegion(new Point(10, 10), 9);
  Region c2 = new CircRegion(new Point(22, 10), 9);
  Region c1Andc2 = new IntersectRegion(c1, c2);
  String visualizeC1 = this.visualize(this.c1Andc2, 0, 40, 0, 20);
  // String visualizeC2 = this.visualize(this.c2, 0, 40, 0, 20);
}