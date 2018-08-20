package geometry;
import java.util.ArrayList;

import coregame.Velocity;
/**
 * @author shaytzir
 *
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

   /**
    * constructor.
    * @param upperLeft point which is the upperLeft of the rectangle.
    * @param width rectagle width.
    * @param height rectangle height.
    */
   public Rectangle(Point upperLeft, double width, double height) {
       this.upperLeft = upperLeft;
       this.width = width;
       this.height = height;
   }

   /**
    *
    * @param line a line to find intersection with this rectangle.
    * @return a (possibly empty) List of intersection points
    * with the specified line.
    */
   public ArrayList<Point> intersectionPoints(Line line) {
      ArrayList<Point> pointList = new ArrayList<Point>();
      //if the line is not crossing the rectangle at all
      // return empty list
      if (!this.inRectangleRange(line)) {
          return pointList;
      }
      //creating a line for the upper border of the rectangle
      Line upperHorizon = this.upperHorizon();
      //finding intersection with given line
      Point upperInter = line.intersectionWith(upperHorizon);
      //if there is a intersection point add it to the list
      if (upperInter != null) {
          pointList.add(upperInter);
      }
      //creating a line for the lower border of the rectangle
      Line lowerHorizon = this.lowerHorizon();
      //finding intersection with the given line
      Point lowerInter = line.intersectionWith(lowerHorizon);
      //if theres an intersection point add it to the list
      if (lowerInter != null) {
          pointList.add(lowerInter);
      }
      //creating a line for the left border of the rectangle
      Line leftVertical = this.leftVertical();
      // finding intersection with the given line
      Point leftInter = line.intersectionWith(leftVertical);
      //if theres an intersection (which is different from the previous, add it
      if ((leftInter != null) && (!leftInter.equals(upperInter))
              && (leftInter != lowerInter)) {
          pointList.add(leftInter);
      }
      //creating a line for the right border of the rectangle
      Line rightVertical = this.rightVertical();
      //find intersection with given line
      Point rightInter = line.intersectionWith(rightVertical);
      //if theres an intersection (which is different from the previous, add it
      if ((rightInter != null) && (!rightInter.equals(upperInter))
              && (!rightInter.equals(lowerInter))) {
          pointList.add(rightInter);
      }
      return pointList;
   }

    /**
     *  the method checks if a given line can't intersect with rectangle.
     * @param line a line to find intersection with this rectangle
     * @return false if the line is cant intersect (out of borders),
     * true otherwise
     */
   public boolean inRectangleRange(Line line) {
    //if both start and end of the line X's are bigger the the upper right
    //corner, it can't touch the rectangle
    if ((line.start().getX() > this.getUpperLeft().getX() + this.getWidth())
    && (line.end().getX() > this.getUpperLeft().getX()
            + this.getWidth())) {
        return false;
    }
    //if both start and end of the line X's are smaller the the upper left
    //corner, it can't touch the rectangle
    if ((line.end().getX() < this.getUpperLeft().getX())
            && (line.start().getX() < this.getUpperLeft().getX())) {
        return false;
    }
    //if both start and end of the line Y's are smaller the the upper (which
    //has smaller Y value) it can't touch the rectangle
    if ((line.start().getY() < this.getUpperLeft().getY())
            && (line.end().getY() < this.getUpperLeft().getY())) {
        return false;
    }
    //if both start and end of the line Y's are bigger the the lower (which
    //has higher Y value) it can't touch the rectangle
    if ((line.start().getY() > this.getUpperLeft().getY() + this.getHeight())
            && (line.end().getY() > this.getUpperLeft().getY()
                    + this.getHeight())) {
        return false;
    }
    //else
    return true;
   }
   /**
    * creates a line, representing the upper border of the rectangle.
    * @return the upper horizontal line
    */
   public Line upperHorizon() {
     double x1 = this.getUpperLeft().getX();
     double y = this.getUpperLeft().getY();
     double x2 = this.getUpperLeft().getX() + this.getWidth();
     return new Line(x1, y, x2, y);
   }
   /**
    * creates a line, representing the lower border of the rectangle.
    * @return the lower horizontal line
    */
   public Line lowerHorizon() {
     double x1 = this.getUpperLeft().getX();
     double y = this.getUpperLeft().getY() + this.getHeight();
     double x2 = this.getUpperLeft().getX() + this.getWidth();
     return new Line(x1, y, x2, y);
   }
   /**
    * creates a line, representing the left border of the rectangle.
    * @return the left vertical line created
    */
   public Line leftVertical() {
       double x = this.getUpperLeft().getX();
       double y2 = this.getUpperLeft().getY() + this.getHeight();
       double y1 = this.getUpperLeft().getY();
       return new Line(x, y1, x, y2);
   }
   /**
    * creates a line, representing the right border of the rectangle.
    * @return the right vertical line created
    */
   public Line rightVertical() {
       double x = this.getUpperLeft().getX() + this.getWidth();
       double y2 = this.getUpperLeft().getY() + this.getHeight();
       double y1 = this.getUpperLeft().getY();
       return new Line(x, y1, x, y2);
   }

   /**
    *
    * @return Return the width of the rectangle
    */
   public double getWidth() {
       return this.width;
   }
   /**
    *
    * @return Return the height of the rectangle
    */
   public double getHeight() {
       return this.height;
   }
   /**
    *
    * @return the upper-left point of the rectangle.
    */
   public Point getUpperLeft() {
       return this.upperLeft;
   }
   /**
    * the method checks on which side of the rectangle.
    * the point is, accordingly creates a new point which
    * will represent the change to apply on the previous location
    * @param p a point
    * @param v a velocity
    * @return a new point represents change in location
    */
   public Point checkHitChangeLocation(Point p, Velocity v) {
       double blockLeftX = this.getUpperLeft().getX();
       double blockRightX = blockLeftX + this.getWidth();
       double blockUpY = this.getUpperLeft().getY();
       double blockDownY = blockUpY + this.getHeight();
       double changeX = 0;
       double changeY = 0;
       //if it hits the left border
       if (p.getX() == blockLeftX) {
           //if it comes from the left
           if (v.getDx() > 0) {
               //take it a small step to the left
               changeX = -0.0001;
             //if it comes from the right
           } else if (v.getDx() < 0) {
               //take it a small step to the right
               changeX = 0.0001;
           }
       }
       //if it hits the right border
       if (p.getX() == blockRightX) {
           //if comes from left
           if (v.getDx() > 0) {
               //take a small step left
               changeX = -0.0001;
               //if comes from right
           } else if (v.getDx() < 0) {
               //take a small step right
               changeX = 0.0001;
           }
       }
       //if hits the upper border
       if (p.getY() == blockUpY) {
           //if come from above
           if (v.getDy() > 0) {
               //take a small step up
               changeY = -0.0001;
               //if comes from below
           } else if (v.getDy() < 0) {
               //take a small step down
               changeY = 0.0001;
           }
       }
       //if hits the lower border
       if (p.getY() == blockDownY) {
           //if comes from above
           if (v.getDy() > 0) {
               //take a small step up
               changeY = -0.0001;
               //if comes from below
           } else if (v.getDy() < 0) {
               //take small step down
               changeY =  0.0001;
           }
       }
       //create the changes needed (representing point)
       Point change = new Point(changeX, changeY);
       return change;
   }

   /**
    * update this rectangle location by upperLeft point.
    * @param p a point
    */
   public void changeRecLocation(Point p) {
       this.upperLeft = p;
   }

}