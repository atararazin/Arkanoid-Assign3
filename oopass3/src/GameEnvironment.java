import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
   //members
   public List<Collidable> collidableList;

   /**
    * the constructor.
    */
   public GameEnvironment() {
       this.collidableList = new ArrayList<Collidable>();
   }

   /**
    * Adds the given collidable to the environment. Saves it in the List.
    *
    *@param c (a new collidable)
    */
   public void addCollidable(Collidable c) {
       this.collidableList.add(c);
   }
   
   // Assume an object moving from line.start() to line.end().
   // If this object will not collide with any of the collidables
   // in this collection, return null. Else, return the information
   // about the closest collision that is going to occur.
   public CollisionInfo getClosestCollision(Line trajectory) {
       if (this.collidableList.isEmpty()) {
           return null;
       }

       //gets the closest intersection point with the trajectory of that first rectangle.
       Point closestCollid = null;
       //assigns that same rectangle as the closest collidable
       Collidable closestObject = null;

       //goes through the elements in the collidable List
       for (Collidable nextCollid : this.collidableList) {
           
           //assigns this as the current rectangle that is being evaluated
           Rectangle nextCollisBox = nextCollid.getCollisionRectangle();
           
           //gets the current rectangle's closest intersection point.
           Point nextCollisPoint = trajectory.closestIntersectionToStartOfLine(nextCollisBox);
           
           if (null != nextCollisPoint) {
               //System.out.println("found possible collision");
               if (null == closestCollid) {
                   closestCollid = nextCollisPoint;
                   closestObject = nextCollid;
                   //System.out.println("picked collision");
               } else if (nextCollisPoint.distance(trajectory.start()) < closestCollid.distance(trajectory.start())) {
                   closestCollid = nextCollisPoint;
                   closestObject = nextCollid;
                   //System.out.println("found closer collision");
               }
           }
       }
       //returns the collision info.
       if (null != closestCollid) {
           //System.out.println("Collision found at: " + closestObject.getCollisionRectangle().getUpperLeft().getX());
           return new CollisionInfo(closestCollid, closestObject);
           
       } else {
           return null;
       }
   }
   
}