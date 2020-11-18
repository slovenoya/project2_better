import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class MovableEntity extends AnimatedEntity{
    private List<Node> path;

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public MovableEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public abstract boolean moveTo(Entity entity, WorldModel world, Entity target, EventScheduler scheduler);
    public abstract Point nextPosition(WorldModel world, Point destPos);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduleEvent(scheduler, createAnimationAction(this, 0), getAnimationPeriod());
    }


    public void moveEntity(WorldModel world, Point pos) {
        Point oldPos = this.getPosition();
        if (world.withinBounds(pos) && !pos.equals(oldPos)) {
            world.setOccupancyCell(oldPos, null);
            world.removeEntityAt(pos);
            world.setOccupancyCell(pos, this);
            this.setPosition(pos);
        }
    }


    public Optional<ActionableEntity> findNearest(WorldModel world, Point pos, Class c) {
        List<ActionableEntity> ofType = new LinkedList<>();
        for (Entity entity : world.getEntities()) {
            if (entity.getClass() == c) {
                ofType.add((ActionableEntity) entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    private Optional<ActionableEntity> nearestEntity(List<ActionableEntity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            ActionableEntity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);
            for (ActionableEntity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);
                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return Optional.of(nearest);
        }
    }
}
