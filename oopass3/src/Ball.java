import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The ball class defines a filled circle of a given color that knows the bounds of its movements.
 * The ball is able to handle the physics of hitting walls on its given boundaries,
 * and may print itself.
 *
 * @author Benjy Berkowicz & Atara Razin
 */
public class Ball implements Sprite {
   private Point center;
   private int radius;
   private Color color;
   // The velocity is initialized to 0,0 by default (the user doesn't need to set them)
   private Velocity ballVelocity = new Velocity(0, 0);
   private GameEnvironment game;
   
   // Constructors
   /**
    * The main constructor for the ball - takes all possible parameters including top/bottom boundary
    * points for the ball to bounce within. Creates a new ball with given characteristics.
    *
    * @param x the x starting position of the ball.
    * @param y the y starting position of the ball.
    * @param r the radius of the ball.
    * @param color the color that fills the ball.
    * @param topBound the top Point of the 'rectangle' within which the ball bounces.
    * @param bottomBound the bottom Point of the 'rectangle' within which the ball bounces.
    */
   public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment game) {
       this.radius = r;
       //Creates a new 'Point' for the center of the ball
       this.center = new Point(x, y);
       this.color = color;
       this.game = game;
   }

   /**
    * A more basic constructor than the one above, takes a point - but
    * takes no bounds for the ball - useful for constructing a static ball.
    *
    * @param center the Point where the ball will be created.
    * @param r the radius of the ball.
    * @param color the color of the ball.
    */
   public Ball(Point center, int r, java.awt.Color color) {
       this.radius = r;
       //Creates a new 'Point' for the center of the ball
       this.center = center;
       this.color = color;
   }

   // Basic Accessors

   /**
    * getX returns the x coordinate of the center of the ball.
    * @return the x coordinate of the center of the ball.
    */
   public int getX() {
       //Casting to int is done because Point's getX function returns a double.
       return (int) this.center.getX();
   }

   /**
    * getY returns the y coordinate of the center of the ball.
    * @return the y coordinate of the center of the ball.
    */
   public int getY() {
       //Casting to int is done because Point's getY function returns a double.
       return (int) this.center.getY();
   }

   /**
    * getSize returns the radius of the ball.
    * @return the radius of the ball
    */
   public int getSize() {
       return this.radius;
   }

   /**
    * getColor returns the color of the ball as a java color object.
    * @return the color of the ball.
    */
   public java.awt.Color getColor() {
       return this.color;
   }

   /**
    * getVelocity returns the current velocity of the ball.
    * @return the velocity of the ball as a Velocity object.
    */
   public Velocity getVelocity() {
       return this.ballVelocity;
   }

   // General Methods
   /**
    * Adds itself to the list of sprites in the game.
    * 
    * @param g (the Game we are working with).
    */
   public void addToGame(Game g) {
       g.addSprite(this);
   }

   /**
    * moveOneStep handles the movement of a ball for one 'step' according to its velocity and the bounds
    * of its movement.  When the ball is detected as hitting or passing a given bound, its x or y velocity
    * is reversed - and its x and y position are reset to be within the bounds of the field.
    */
   public void timePassed() {
       //changed the name from moveonestep to timepassed
       // A new center, the desired location of the ball, is generated.
       Point newCenter = this.getVelocity().applyToPoint(this.center);
       //we create a new line, which is the distance between the new and old centers.
       Line trajectory = new Line(this.center, newCenter);
       //gets the info of the next hit of the trajectory
       CollisionInfo nextHit = game.getClosestCollision(trajectory);
       
       //if the next hit is not the new center, set the new center as newCenter.
       if (nextHit == null) {
           this.center = newCenter;
       }
       else {
           /*
            * We first adjust the X/Y position of the ball as needed.  This is done by detecting which
            * side of the collision rectangle has been hit and moving the center of the ball to a point just 'touching'
            * the collision rectangle, but not inside it.
            */
           
           Point hitSpot = nextHit.collisionPoint();
           List<Line> collisionLines = nextHit.collisionObject().getCollisionRectangle().componentLines();
           
           // Case 1: We hit the 'horizontal top' line of the rectangle, therefore adjust the Y coordinate.
           Line oneLine = collisionLines.get(0);
           if (oneLine.isOnLine(hitSpot)) {
               this.center.setY(hitSpot.getY() - radius);
           }
           
           // Case 2: We hit the 'horizontal bottom' line of the rectangle, therefore adjust the Y coordinate.
           oneLine = collisionLines.get(1);
           if (oneLine.isOnLine(hitSpot)) {
               this.center.setY(hitSpot.getY() + radius);
           }
           
           // Case 3: We hit the 'vertical left' line of the rectangle, therefore adjust the Y coordinate.
           oneLine = collisionLines.get(2);
           if (oneLine.isOnLine(hitSpot)) {
               System.out.println("hit a left vert");
               this.center.setX(hitSpot.getX() - radius);
           }
           
           // Case 3: We hit the 'vertical right' line of the rectangle, therefore adjust the Y coordinate.
           oneLine = collisionLines.get(3);
           if (oneLine.isOnLine(hitSpot)) {
               System.out.println("hit a right vert");
               this.center.setX(hitSpot.getX() + radius);
           }
           
           this.ballVelocity = nextHit.collisionObject().hit(hitSpot, this.ballVelocity);
       }
   }

    /**
     * Draws the ball on the provided DrawSurface. Generally this method is called after moveOneStep.
     * @param surface the surface for the ball to be drawn on.
     */
   public void drawOn(DrawSurface surface) {
       surface.setColor(this.color);
       // We call our accessors to find the dimensions needed for printing.
       surface.fillCircle(this.getX(), this.getY(), this.radius);
   }

   /**
    * Used to set the velocity of the ball given an independantly created Velocity object.
    * @param v the new velocity of the ball.
    */
   public void setVelocity(Velocity v) {
       this.ballVelocity = v;
   }

   /**
    * Used to set the velocity of the ball using a delta X and delta Y parameters.
    * @param dx the change in x position of the ball per step movement
    * @param dy the change in y position of the ball per step movement
    */
   public void setVelocity(double dx, double dy) {
       this.ballVelocity.setDeltaX(dx);
       this.ballVelocity.setDeltaY(dy);
   }
}