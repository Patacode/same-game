package g56080.same.model;

/**
 * A 2D point represented by an x and y position. 
 */
public class Point{
    
    private int x, y;

    /**
     * Constructs a new point initialized as the origin (x and y are 0)
     */
    public Point(){} // origin

    /**
     * Constructs a new point using the given x and y coordinates.
     *
     * @param x the x dimension
     * @param y the y dimension
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x dimension of this point.
     *
     * @return the x dimension of this point.
     */
    public int getX(){
        return x;
    }

    /**
     * Sets the x dimension of this point.
     *
     * @param x the new x dimension
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Gets the y dimension of this point.
     *
     * @return the y dimension of this point.
     */
    public int getY(){
        return y;
    }

    /**
     * Sets the y dimension of this point.
     *
     * @param y the new y dimension
     */
    public void setY(int y){
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Point){
            Point p = (Point) obj;
            return p.x == x && p.y == y;
        }

        return false;
    }

    @Override
    public String toString(){
        return String.format("(%d;%d)", x, y);
    }
}

