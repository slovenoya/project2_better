import processing.core.PImage;
import java.util.List;

public abstract class Octo extends MovableEntity{
    private int resourceLimit;
    private int resourceCount;
    public Octo(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    @Override
    public Point nextPosition(WorldModel world,Point destPos) {
        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - getPosition().getY());
            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);
            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = getPosition();
            }
        }
        return newPos;
//        Node currentLocation = new Node(getPosition().getX(), getPosition().getY());
//        Node destLocation = new Node(destPos.getX(), destPos.getY());
//        PathingStrategy pathingStrategy = new AStarPathingStrategy();
//        List<Node> path = pathingStrategy.computePath(currentLocation, destLocation,
//                p -> {
//                    if(world.getOccupant(new Point(p.x, p.y)).isPresent()){
//                        if(world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Obstacle.class)
//                        || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(SGrass.class)
//                        || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Crab.class)
//                        || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Octo_not_full.class)
//                        || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Octo_full.class))
//                            return false;
//                    }
//                    return true;
//                },
//                (p1, p2) -> true,
//                PathingStrategy.CARDINAL_NEIGHBORS);
//        if(path.size() == 0) {
//            System.out.println("no path");
//            return new Point(currentLocation.x, currentLocation.y);
//        }
//        return new Point(path.get(0).x, path.get(0).y);
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}
