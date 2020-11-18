import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public final class Crab extends MovableEntity{
    private static final String QUAKE_KEY = "quake";
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    public Crab( String id, Point position, List<PImage> images,  int actionPeriod, int animationPeriod) {
        super( id, position, images, actionPeriod, animationPeriod);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<ActionableEntity> crabTarget = findNearest(world, this.getPosition(), SGrass.class);
        long nextPeriod = this.getActionPeriod();
        if (crabTarget.isPresent()) {
            Point tgtPos = crabTarget.get().getPosition();
            if (moveTo(this, world, crabTarget.get(), scheduler)) {
                Quake quake = new Quake(  QUAKE_ID, tgtPos, imageStore.getImageList(QUAKE_KEY), QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
                quake.addEntity(world);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    @Override
    public boolean moveTo(Entity entity, WorldModel world, Entity target, EventScheduler scheduler){
        if (entity.getPosition().adjacent(target.getPosition())){
            removeEntity(target, world);
            unscheduleAllEvents(scheduler, target);
            return true;
        }
        else{
            Point nextPos = nextPosition(world, target.getPosition());
            if (!entity.getPosition().equals(nextPos)){
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()){
                    unscheduleAllEvents(scheduler, occupant.get());
                }
                moveEntity(world, nextPos);
            }
            return false;
        }
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());
        Optional<Entity> occupant = world.getOccupant(newPos);
        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().getClass() == Fish.class))) {
            int vert = Integer.signum(destPos.getY() - getPosition().getY());
            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);
            occupant = world.getOccupant(newPos);
            if (vert == 0 || (occupant.isPresent() && !(occupant.get().getClass() == Fish.class))) {
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
//                                || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Crab.class)
//                                || world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Octo_not_full.class)
//                                ||world.getOccupant(new Point(p.x, p.y)).get().getClass().equals(Octo_full.class))
//                            return false;
//                    }
//                    return true;
//                },
//                (p1, p2) -> true,
//                PathingStrategy.CARDINAL_NEIGHBORS);
//        if(path.size() == 0)
//            return new Point(currentLocation.x, currentLocation.y);
//        return new Point(path.get(0).x, path.get(0).y);

    }
}
