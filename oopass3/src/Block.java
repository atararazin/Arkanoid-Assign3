
import biuoop.DrawSurface;

public class Block implements Collidable, Sprite {
    
    private Rectangle rect;
    private int hitPoints;
    private java.awt.Color color;
    
    //constructor
    public Block(Rectangle rect, int hitPoints, java.awt.Color color) {
        this.rect = rect;
        this.color = color;
        this.hitPoints = hitPoints;
    }
    
   // Return the "collision shape" of the object. 
   public Rectangle getCollisionRectangle() {
       return this.rect;
   }

   // Notify the object that we collided with it at collisionPoint with
   // a given velocity.
   // The return is the new velocity expected after the hit (based on
   // the force the object inflicted on us). 
   public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
       this.hitPoints = Math.max(this.hitPoints - 1, 0);
       Line oneLine;
       
       // Case 1: We hit the 'horizontal top' line of the rectangle, therefore we reverse Y velocity.
       oneLine = this.rect.componentLines().get(0);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaY(-1 * Math.abs(currentVelocity.getDeltaY()));
           System.out.println("hit a horz top");
       }
       
       // Case 2: We hit the 'horizontal bottom' line of the rectangle, therefore we reverse Y velocity.
       oneLine = this.rect.componentLines().get(1);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaY(Math.abs(currentVelocity.getDeltaY()));
           System.out.println("hit a horz bot");
       }
       
       // Case 3: We hit the 'left verticle' line of the rectangle, therefore we reverse X velocity.
       oneLine = this.rect.componentLines().get(2);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaX(-1 * Math.abs(currentVelocity.getDeltaX()));
           System.out.println("hit a left verticle");
       }
       
       // Case 4: We hit the 'right verticle' line of the rectangle, therefore we reverse X velocity.
       oneLine = this.rect.componentLines().get(3);
       if (oneLine.isOnLine(collisionPoint)) {
           currentVelocity.setDeltaX(Math.abs(currentVelocity.getDeltaX()));
           System.out.println("hit a right verticle");
       }
       return currentVelocity;
   }
   
   public void timePassed() {

   }
   
   public void drawOn(DrawSurface surface) {
       Point corner = this.rect.getUpperLeft();
       int rectHeight = (int) this.rect.getHeight();
       int rectWidth = (int) this.rect.getWidth();
       int x = (int) corner.getX();
       int y = (int) corner.getY();
       String text;

       if (this.hitPoints > 0) {
           text = Integer.toString(this.hitPoints);
       }
       else {
           text = "X";
       }
       surface.setColor(java.awt.Color.BLACK);
       surface.fillRectangle(x, y, rectWidth, rectHeight);
       
       surface.setColor(this.color);
       // We call our accessors to find the dimensions needed for printing.
       surface.fillRectangle(x + 2, y + 2, rectWidth - 4, rectHeight - 4);
       surface.setColor(java.awt.Color.WHITE);
       surface.drawText(x + (rectWidth / 2), y + (rectHeight/ 2), text, 14);
   }
   
   //implements the addtogame method in the interface sprite
   public void addToGame(Game g) {
       g.addSprite(this);
       g.addCollidable(this);
   }
}