
import java.util.ArrayList;
import java.util.List;

public class Rectangle {

        private Point upperLeft;
        private double width;
        private double height;

        // Create a new rectangle with location and width/height.
        public Rectangle(Point upperLeft, double width, double height) {
            this.upperLeft = upperLeft;
            this.width = width;
            this.height = height;
        }

        public java.util.List<Line> componentLines() {
            double baseX = this.upperLeft.getX();
            double baseY = this.upperLeft.getY();
            double xPlusWidth = this.upperLeft.getX() + this.width;
            double yPlusHeight = this.upperLeft.getY() + this.height;
            List<Line> listOfLines = new ArrayList<Line>();

            // the horizontal top line
            listOfLines.add(new Line(baseX, baseY, xPlusWidth, baseY));
            // the horizontal bottom line
            listOfLines.add(new Line(baseX, yPlusHeight, xPlusWidth, yPlusHeight));
            // the left vertical line
            listOfLines.add(new Line(baseX, baseY, baseX, yPlusHeight));
            // the right vertical line
            listOfLines.add(new Line(xPlusWidth, baseY, xPlusWidth, yPlusHeight));

            return listOfLines;
        }

        // Return a (possibly empty) List of intersection points
        // with the specified line.
        public java.util.List<Point> intersectionPoints(Line line) {

            List<Line> lineArr = this.componentLines();

            //the list that will hold the intersection points
            List<Point> rectangleIntersectionPoints = new ArrayList<Point>();
            for (Line lineOfRectangle : lineArr) {
                //checks if the rectangle intersects with the line.
                if (lineOfRectangle.isIntersecting(line)) {
                    //if it does, it adds it to the list of rectangleIntersectionPoints.
                    rectangleIntersectionPoints.add(lineOfRectangle.intersectionWith(line));
                }
            }
            
            return rectangleIntersectionPoints;
        }

        /**
         * this method returns the width of the rectangle.
         *
         * @return the width of the rectangle.
         */
        public double getWidth() {
            return this.width;
        }

        /**
         * this method returns the height of the rectangle.
         *
         * @return the height of the rectangle.
         */
        public double getHeight() {
            return this.height;
        }

        /**
         * this method returns the upper-left point of the rectangle..
         *
         * @return the upper-left point of the rectangle.
         */
        public Point getUpperLeft() {
            return this.upperLeft;
        }
}