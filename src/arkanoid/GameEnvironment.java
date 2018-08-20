package arkanoid;
import java.util.ArrayList;
import java.util.List;

import coregame.Collidable;
import coregame.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
/**
 *
 * @author shaytzir
 *
 */
public class GameEnvironment {
    private ArrayList<Collidable> possibleCollide;

    /**
     * constructor.
     */
    public GameEnvironment() {
        this.possibleCollide = new ArrayList<Collidable>();
    }
   // add the given collidable to the environment.
   /**
    *  adds a collidable object to the game environment.
    * @param c a collidable object
    */
    public void addCollidable(Collidable c) {
       this.possibleCollide.add(c);
   }

    /**
     *
     * @return the array holding collidables
     */
    public ArrayList<Collidable> getarray() {
        return this.possibleCollide;
    }

   /**
    * find the closest intersection of this line with all of the collidables.
    * @param trajectory the line to find intersection with
    * @return the intersection point, null if it doesnt exist
    */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //creates new point array to keep all intrsections in
        ArrayList<Point> collisionPoints = new ArrayList<Point>();
        //setting high number as a minimum
        double minDistance = 300000000;
        double compareDistance = minDistance;
        Point minP = new Point(30000000, 30000000);
        Collidable collideWith = this.possibleCollide.get(0);
        List<Collidable> possibleCollisions = new ArrayList<Collidable>(this.possibleCollide);

        for (int i = 0; i < possibleCollisions.size(); i++) {
            //keeping the rectangle of current collidable
            Rectangle checkCol = possibleCollisions.
                    get(i).getCollisionRectangle();
            //find closest intersection of a specific block with trajectory
            Point closestPToStart = trajectory.
                    closestIntersectionToStartOfLine(checkCol);
            //if exists add to the intersection points array
            if (closestPToStart != null) {
                collisionPoints.add(closestPToStart);
                double currDis = closestPToStart.distance(trajectory.start());
                //each for makes sure th closest intersection is the minimum
                if (currDis < minDistance) {
                    minP = closestPToStart;
                    collideWith = possibleCollisions.get(i);
                    minDistance = currDis;
                }
           }
       }
        //check if any change was made from initiallze
        if (compareDistance == minDistance) {
            //if not it means there are no intersection points
            return null;
        } else {
            //exists, return rlativ information
            CollisionInfo infoReturn = new CollisionInfo(minP, collideWith);
            return infoReturn;
       }
   }
}