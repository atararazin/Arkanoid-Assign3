import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class Paddle implements Sprite, Collidable {
   private KeyboardSensor keyboard;
   private java.awt.Color color;
   private int maxWidth;
   private int width;
   private int height;
   private int x;
   private int y;
   
   public Paddle(int x, int y, int width, int height, java.awt.Color color, int maxWidth, KeyboardSensor keyboard) {
       this.x = x;
       this.y = y;
       this.height = height;
       this.width = width;
       this.maxWidth = maxWidth;
       this.color = color;
       this.keyboard = keyboard;
   }
   
   public void moveLeft(int newX) {
        this.x = Math.max(this.x - newX, 10);
   }
   
   public void moveRight(int newX) {
       this.x = Math.min(this.x + newX, maxWidth - this.width - 10);
   }

   // Sprite
   public void timePassed() {
       if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
           this.moveLeft(3);
       }
       if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
           this.moveRight(3);
       }
   }
   
   public void drawOn(DrawSurface surface) {
       surface.setColor(java.awt.Color.BLACK);
       surface.fillRectangle(this.x, this.y, this.width, this.height);
       
       surface.setColor(this.color);
       surface.fillRectangle(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
   }

   // Collidable
   public Rectangle getCollisionRectangle() {
       Point topLeft = new Point(this.x, this.y);
       return new Rectangle(topLeft, this.width, 1);
   }
   
   public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
       Line oneLine;
       
       // Case 1: We hit the 'horizontal top' line of the rectangle, therefore we reverse Y velocity.
       oneLine = this.getCollisionRectangle().componentLines().get(0);
       if (oneLine.isOnLine(collisionPoint)) {
           double baseHitPos = collisionPoint.getX() - oneLine.start().getX();
           long multiplier = Math.round(baseHitPos / (this.width / 4));
           double currentDx = currentVelocity.getDeltaX();
           double currentDy = currentVelocity.getDeltaY();
           double currentSpeed = Math.sqrt(Math.pow(currentDx, 2) + Math.pow(currentDy, 2));
           
           Velocity newVeloc = Velocity.fromAngleAndSpeed(300 + (multiplier * 30), currentSpeed);
           currentVelocity = newVeloc;
           System.out.println("New speed = " + currentSpeed + " Multiplier = " + multiplier);
       }
       
       
       // Case 2: We hit the 'horizontal bottom' line of the rectangle, therefore we reverse Y velocity.
       oneLine = this.getCollisionRectangle().componentLines().get(1);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaY(Math.abs(currentVelocity.getDeltaY()));
           System.out.println("hit a horz bot");
       }
       /*
       // Case 3: We hit the 'left verticle' line of the rectangle, therefore we reverse X velocity.
       oneLine = this.getCollisionRectangle().componentLines().get(2);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaX(-1 * Math.abs(currentVelocity.getDeltaX()));
           System.out.println("hit a left verticle");
       }
       
       // Case 4: We hit the 'right verticle' line of the rectangle, therefore we reverse X velocity.
       oneLine = this.getCollisionRectangle().componentLines().get(3);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaX(Math.abs(currentVelocity.getDeltaX()));
           System.out.println("hit a right verticle");
       }
       */
       return currentVelocity;
   }

   // Add this paddle to the game.
   public void addToGame(Game g) {
       g.addSprite(this);
       g.addCollidable(this);
   }
}