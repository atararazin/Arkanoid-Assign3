import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

public class Game {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment;
    private List<java.awt.Color> colorArray = new ArrayList<java.awt.Color>();
    private GUI gui;
    private Sleeper sleeper;
    private int maxWidth;
    private int maxHeight;
    

    public Game () {
        this.maxWidth = 1000;
        this.maxHeight = 800;
        this.environment = new GameEnvironment();
        this.colorArray.add(java.awt.Color.RED);
        this.colorArray.add(java.awt.Color.ORANGE);
        this.colorArray.add(java.awt.Color.YELLOW);
        this.colorArray.add(java.awt.Color.GREEN);
        this.colorArray.add(java.awt.Color.CYAN);
        this.colorArray.add(java.awt.Color.BLUE);
    }
    
    /**
     * this method adds a collidable to the list of Collidables.
     *
     * @param c (the new collidable that we want to add).
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    
    public int numElms() {
        return this.environment.collidableList.size();
    }

    /**
     * this method adds a sprite to the list of sprites.
     *
     * @param s (the new sprite that we want to add).
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle) 
    // and add them to the game.
    public void initialize() {
        final int width = 60;
        final int height = 30;
        
        this.sleeper = new Sleeper();
        this.gui = new GUI("Arkanoid - Batara Berkorazin", this.maxWidth, this.maxHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        
        Paddle paddle = new Paddle(this.maxWidth / 2, this.maxHeight - 50, 120, 15, java.awt.Color.GRAY, this.maxWidth, keyboard);
        paddle.addToGame(this);
        
        Ball ball = new Ball(this.maxWidth / 2, this.maxHeight - 60, 5, java.awt.Color.BLACK, this.environment);
        Velocity v = Velocity.fromAngleAndSpeed(35, 4.2);
        ball.setVelocity(v);
        ball.addToGame(this);
        
        Ball ball2 = new Ball(this.maxWidth / 5, this.maxHeight - 60, 5, java.awt.Color.BLACK, this.environment);
        Velocity v2 = Velocity.fromAngleAndSpeed(-50, 5.3);
        ball2.setVelocity(v2);
        ball2.addToGame(this);
        
        this.makeBorderWalls();


        for (int i = 0; i < 6; i++) {
           for (int j = 0; j < 7 + i; j++) {
               int hitPoints = 1 + (int) Math.floor(i / 5);
               int blockX = maxWidth - ((j+1) * width) - (10 * j) - 20;
               int blockY = (this.maxHeight / 3) - (10 * i) - (i * height);
               Point topCorner = new Point(blockX, blockY);
               Rectangle newBlock = new Rectangle(topCorner, width, height);
               
               Block block = new Block(newBlock, hitPoints, this.colorArray.get(i));
               block.addToGame(this);
           }
        }
    }

    // Run the game -- start the animation loop.
    public void run() {
            
            int framesPerSecond = 60;
            int millisecondsPerFrame = 1000 / framesPerSecond;
            while (true) {
               long startTime = System.currentTimeMillis(); // timing

               DrawSurface d = gui.getDrawSurface();
               this.sprites.drawAllOn(d);
               gui.show(d);
               this.sprites.notifyAllTimePassed();

               // timing
               long usedTime = System.currentTimeMillis() - startTime;
               long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
               if (milliSecondLeftToSleep > 0) {
                   sleeper.sleepFor(milliSecondLeftToSleep);
               }
         }
    }
    
    private void makeBorderWalls() {
        Point leftCorner = new Point(0,0);
        Rectangle newRect = new Rectangle(leftCorner, this.maxWidth, 10);
        Block thisBlock = new Block(newRect, 0, java.awt.Color.BLACK);
        thisBlock.addToGame(this);
        
        leftCorner = new Point(0,0);
        newRect = new Rectangle(leftCorner, 10, this.maxHeight);
        thisBlock = new Block(newRect, 0, java.awt.Color.BLACK);
        thisBlock.addToGame(this);
        
        leftCorner = new Point(0, this.maxHeight - 10);
        newRect = new Rectangle(leftCorner, this.maxWidth, 10);
        thisBlock = new Block(newRect, 0, java.awt.Color.BLACK);
        thisBlock.addToGame(this);
        
        leftCorner = new Point(this.maxWidth - 10, 0);
        newRect = new Rectangle(leftCorner, 10, this.maxHeight);
        thisBlock = new Block(newRect, 0, java.awt.Color.BLACK);
        thisBlock.addToGame(this);
    }
}
