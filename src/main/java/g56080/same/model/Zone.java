package g56080.same.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Iterable container of points. The points recorded into this zone are organized regarding to
 * their x coordinate and are sorted in descending order of their y coordinate using a priority 
 * queue.
 */
public class Zone implements Iterable<Point>{
    
    private final Map<Integer, Queue<Point>> points;
    private final Comparator<Point> comp = (a, b) -> -Integer.compare(a.getY(), b.getY());

    /**
     * Constructs a new empty zone.
     */
    public Zone(){
        points = new HashMap<>();
    }

    /**
     * Constructs a copy of the given zone.
     *
     * @param zone the zone to copy
     */
    public Zone(Zone zone){
        points = new HashMap<>();
        if(zone != null){
            for(Point p : zone){
                add(p);    
            }
        }
    }

    /**
     * Adds the given point to this zone if it doesn't already exist.
     *
     * @param p the point to add
     */
    public void add(Point p){
        points.compute(p.getX(), (k, v) -> {
            if(v == null){ 
                Queue<Point> queue = new PriorityQueue<>(comp);
                queue.add(p);
                return queue;
            }

            if(!v.contains(p))
                v.add(p);
            return v;
        });
    }

    /**
     * Adds all the given points to this zone.
     *
     * @param points the points to add
     */
    public void addAll(Point... points){
        for(Point p : points){
            add(p);
        } 
    }

    /**
     * Checks if the given point is present in this zone.
     *
     * @param p the point to be checked
     *
     * @return true if the given point is present in this zone, false otherwise.
     */
    public boolean contains(Point p){
        return getPoints().contains(p);
    }

    /**
     * Gets a list view of all the points contained in this zone.
     *
     * @return a list containing all the points of this zone.
     */
    public List<Point> getPoints(){
        List<Point> plist = new ArrayList<>();
        for(Map.Entry<Integer, Queue<Point>> entry : points.entrySet()){
            plist.addAll(entry.getValue());
        }

        return plist;
    }

    /**
     * Gets a list view of all the points present in this zone found at the given x coordinate.
     *
     * @param x the x coordinate of the points to obtain
     *
     * @return a list containing all the points of this zone found at the given x coordinate.
     */
    public List<Point> getPoints(int x){
        Queue<Point> points = this.points.get(x);
        return points == null ? Collections.emptyList() : points.stream().toList();
    }

    /**
     * Gets the max point of this zone found at the given x coordinate. The point
     * having the highest y coordinate for the given x coordinate is the considered to be
     * the max point.
     *
     * @param x the x coordinate of the max point to obtain
     *
     * @return the max point found at the given x coordinate
     */
    public Point getMaxPoint(int x){
        Queue<Point> points = this.points.get(x);
        return points == null ? null : points.peek();
    }

    /**
     * Gets a set view of the entries present in this zone.
     *
     * @return a set view of the entries present in this zone.
     */
    public Set<Map.Entry<Integer, Queue<Point>>> toSet(){
        return points.entrySet();
    }

    /**
     * Gets a map view of this zone.
     *
     * @return a map view of this zone.
     */
    public Map<Integer, Queue<Point>> toMap(){
        return new HashMap<>(points);
    }

    @Override
    public Iterator<Point> iterator(){
        return new ZoneIterator(getPoints());
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Zone){
            Zone zone = (Zone) obj;
            return getPoints().containsAll(zone.getPoints());
        }

        return false;
    }

    @Override
    public String toString(){
        return getPoints().toString();
    }

    private class ZoneIterator implements Iterator<Point>{
        
        private final List<Point> list;
        private int pointer;

        public ZoneIterator(List<Point> list){
            this.list = list;
        }

        @Override
        public boolean hasNext(){
            return pointer < list.size();
        }

        @Override
        public Point next(){
            if(pointer == list.size())
                throw new NoSuchElementException("No more elements");

            return list.get(pointer++);
        }
    }
}

