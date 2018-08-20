package geometry;
/**
 *
 * @author shaytzir
 *
 */
import java.util.ArrayList;
/**
 * a line class.
 * @author shaytzir
 *
 */
public class Line {
    private Point start;
    private Point end;
    /**
     * calling the second constructor by turning the points
     * into x's and y's values.
     * @param start a point to add to the line
     * @param end a second point to add to the line
     */
    public Line(Point start, Point end) {
       this(start.getX(), start.getY(), end.getX(), end.getY());
    }
    /**
     * initializing the line with 2 points,
     * fisrt creating the points. the "start" point
     * will be the point with the lower x value. (if
     * theyre the same it will be determened by the lower y).
     * @param x1 x value of the first point
     * @param y1 y value of the first point
     * @param x2 x value of the second point
     * @param y2 y value of the second point
     */
    public Line(double x1, double y1, double x2, double y2) {
       //creating new points by the given values
       Point point1 = new Point(x1, y1);
       Point point2 = new Point(x2, y2);
       //the start point will be the first given point
       this.start = point1;
       this.end = point2;
    }
    /**
     * calculating the distance between the 2 points - length.
     * calculating by the equation: root(((x1-x2)^2)+((y1-y2)^2)).
     * @return the length of the line
     */
    public double length() {
        //x1-x2
        //should check if abs is necessary
        double xDist = Math.abs(this.start.getX() - this.end.getX());
        //y1-y2
        double yDist = Math.abs(this.start.getY() - this.end.getY());
        double beforeRoot = (xDist * xDist) + (yDist * yDist);
        return Math.sqrt(beforeRoot);
    }

    /**
     * finds the average x and y in the line,and creates new point.
     * @return the middle point of the line
     */
    public Point middle() {
        double xMiddle = (this.end.getX() + this.start.getX()) / 2;
        double yMiddle = (this.end.getY() + this.start.getY()) / 2;
        Point middle = new Point(xMiddle, yMiddle);
        return middle;
    }
    /**
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }
    /**
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }
    /**
     * calculate the slope by the equation (y1-y2)/(x1-x2).
     * @return the slope between 2 points
     */
    public double slope() {
        //by the equation.
        double slope = (this.start.getY() - this.end.getY())
                / (this.start.getX() - this.end.getX());
        //for the programmer comfort
        if (slope == -0.0) {
         return 0.0;
        }
        return slope;
    }
    /**
     * b stands for the linear equation: y=mx+b. (when m is the slope).
     * after moving between the members,for finding b the equation
     * is: b=y-mx
     * @param slope the slope of these particular points.
     * @return b
     */
    public double findB(double slope) {
        double b = this.start.getY() - ((this.start.getX()) * slope);
        return b;
    }
    /**
     * finding the slope and "b" for each line.
     * then comapring between those and finding an
     * intersection point (while ignoring the "limits"
     * of each line.
     * (can't get a "null",this case it taken care in another function
     * @param other another line to find intersection with
     * @return the intersection point
     */
    public Point findIntersection(Line other) {
        double slope1 = this.slope();
        double slope2 = other.slope();
        double b1 = this.findB(slope1);
        double b2 = other.findB(slope2);
        double interX = (b2 - b1) / (slope1 - slope2);
        interX = Math.round((interX * (Math.pow(10, 4)))) / (Math.pow(10, 4));
        double interY = (slope1 * interX) + b1;
        interY = Math.round((interY * (Math.pow(10, 4)))) / (Math.pow(10, 4));
        //in order to avoid lack of accuracy with double values
        //if one of the lines is horizontal we should have it's y
        if (slope1 == 0) {
            interY = this.start().getY();
        } else if (slope2 == 0) {
            interY = other.start().getY();
        }
        Point interPoint = new Point(interX, interY);
        return interPoint;
    }
    /**
     * after finding the intersection while ignoring
     * the limits,the function checks if the point is
     * indeed part of the given line (with limits).
     * @param interPoint the point to check if in line.
     * @return true if the point is in the given line,else-false.
     */
    public boolean checkIfInRange(Point interPoint) {
        //checking if X' value is in range
       if (((interPoint.getX() >= this.start.getX())
               && (interPoint.getX() <= this.end.getX()))
               || ((interPoint.getX() <= this.start.getX())
                       && (interPoint.getX() >= this.end.getX()))) {
           //checking if Y value in range of line
           if (((interPoint.getY() >= this.start().getY())
                   && (interPoint.getY() <= this.end().getY()))
                   || ((interPoint.getY() <= this.start().getY())
                           && (interPoint.getY() >= this.end().getY()))) {
               return true;
           }
       }
       return false;
    }
    /**
     * checks if a line is vertical.
     * @param other another line
     * @return intersection point if exist, null
     * otherwise.
     */
    public Point isVertical(Line other) {
        if (this.start.getX() == this.end.getX()) {
              Point intersection = this.interVertical(other);
              return intersection;
        } else {
            return null;
        }
    }
    /**
     *
     * @param other another line
     * @return  an intersection point with a vertical line,
     * null if the point doesnt exist
     */
    public Point interVertical(Line other) {
        double x = this.start.getX();
        if (((other.start.getX() <= x) && (other.end.getX() >= x))
                || ((other.start.getX() >= x) && (other.end.getX() <= x))) {
            double otherSlope = other.slope();
            double b = other.findB(otherSlope);
            double interY = (otherSlope * x) + b;
            //if the point isnt part of this line return null
            if (!this.checkIfInRange(new Point(x, interY))) {
                return null;
            }
            //if its part of both lines, create it and return it
            if (((interY >= other.start.getY())
                    && (interY <= other.end.getY()))
                || ((interY <= other.start.getY())
                        && (interY >= other.end.getY()))) {
                Point interVert = new Point(x, interY);
                return interVert;
            }
        }
        return null;
    }
    /**
     *
     * @param other line to check if intersect.
     * @return true if the lines intersect,false otherwise
     */
    public boolean isIntersecting(Line other) {
        boolean state = false;
        double slope1 = this.slope();
        double slope2 = other.slope();
        //to check theyre arent parallel
        if (slope1 == slope2) {
            //if just the ends are intersecting return true
            if (this.end().equals(other.end())) {
                if ((!this.checkIfInRange(other.start()))
                        && (other.checkIfInRange(this.start()))) {
                    return true;
                    }
            }
            if (this.end().equals(other.start())) {
                if ((!this.checkIfInRange(other.end()))
                        && (other.checkIfInRange(this.start()))) {
                    return true;
                    }
            }

            if (this.start().equals(other.end())) {
                if ((!this.checkIfInRange(other.start()))
                        && (other.checkIfInRange(this.end()))) {
                    return true;
                    }
            }
            if (this.start().equals(other.start())) {
                if ((!this.checkIfInRange(other.end()))
                        && (other.checkIfInRange(this.end()))) {
                    return true;
                    }
            }
            if ((this.end.equals(other.start()))
                    || (other.end.equals(this.start()))) {
                state = true;
            }
            //check if one of them is vertical and has intersection with other
        } else if ((this.isVertical(other) != null)
                || (other.isVertical(this) != null)) {
            state = true;
        }
        //else just find intersection normally
        Point interPoint = this.findIntersection(other);
        //check if the point is part of both "limited" by points lines
        if ((this.checkIfInRange(interPoint))
                && (other.checkIfInRange(interPoint))) {
            state = true;
        }
        return state;
    }
    /**
     *
     * @param other another line to find intersection with
     * @return intersection point if exists,else "null".
     */
    public Point intersectionWith(Line other) {
        double slope1 = this.slope();
        double slope2 = other.slope();
        //if arent intersecting the func returns false
        if (!this.isIntersecting(other)) {
         return null;
        } else {
           if (slope1 == slope2) {
               if ((this.end.equals(other.start))
                       || (this.end().equals(other.end()))) {
                   Point intersection = new Point(this.end.getX(),
                           this.end.getY());
                   return intersection;
               } else if ((this.start().equals(other.start()))
                       || (this.start().equals(other.end()))) {
                   Point intersection = new Point(this.start.getX(),
                           this.start.getY());
                   return intersection;
               }
           }
           Point verticalInter1 = this.isVertical(other);
           Point verticalInter2 = other.isVertical(this);
           if (verticalInter1 != null) {
               return verticalInter1;
           } else if (verticalInter2 != null) {
               return verticalInter2;
           }
         //calculates the intersection point and returns it
         Point intersection = this.findIntersection(other);
         return intersection;
        }
    }
    /**
     *
     * @param other line to check if equal
     * @return if lines are equal return true,else - false
     */
    public boolean equals(Line other) {
        if (((this.start().equals(other.start()))
                || (this.start().equals(other.end())))
                && ((this.end().equals(other.start()))
                || (this.end().equals(other.end())))) {
            return true;
            }
        return false;
        }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect a rectangle
     * @return null if theres no intersection, or closest intersection to the
     * start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //calls the rectangle method to find intersection with this line
        //and insert them into an array
        ArrayList<Point> interWithRec = rect.intersectionPoints(this);
        //if the array is empty it means theres no intersection
        if (interWithRec.size() == 0) {
            return null;
        } else {
            //if theres only one intersection return it
            if (interWithRec.size() == 1) {
                return interWithRec.get(0);
            } else {
                //there could be only two - find who's closer to the start
                double firstDis = interWithRec.get(0).distance(this.start());
                double secondDis = interWithRec.get(1).distance(this.start());
                if (firstDis < secondDis) {
                    return interWithRec.get(0);
                } else {
                    return interWithRec.get(1);
                }
            }
        }
    }
 }