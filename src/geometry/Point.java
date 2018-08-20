package geometry;
/**
 *
 * @author shaytzir
 *
 */
public class Point {
    private double x;
    private double y;
     /**
      * Construct a point givne x and y coordinates.
      *
      * @param x the x coordinate
      * @param y the y coordinate
      */
    public Point(double x,  double y) {
        this.x = x;
        this.y = y;
    }
    /**
     *
     * @param other another point
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        double xDistance = (this.x - other.getX());
        double yDistance = (this.y - other.getY());
        double beforeRoot = (xDistance * xDistance) + (yDistance * yDistance);
        double finalDistance = Math.sqrt(beforeRoot);
        return finalDistance;
    }
    /**
     *
     * @param other another point
     * @return true is the points are equal,  false otherwise
     */
    public boolean equals(Point other) {
        if ((this == null) || (other == null)) {
            return false;
        }
        if ((this.getX() == other.getX()) && (this.getY() == other.getY())) {
                return true;
        }
        return false;
    }
    /**
     *
     * @param other another point
     * @return if this x is smaller so this point is
     * smaller, if x' are equal the smaler point is
     * the one with the lower y
     */
    public int compare(Point other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.getX() < other.getX()) {
            return -1;
        } else if (this.getX() > other.getX()) {
            return 1;
        } else {
            if (this.getY() < other.getY()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     *
     * @return the x value of this point
     */
    public double getX() {
        return this.x;
    }
    /**
     *
     * @return the y value of this point
     */
    public double getY() {
        return this.y;
    }
}