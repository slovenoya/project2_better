import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public final class Octo_full extends Octo{
    public Octo_full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<ActionableEntity> fullTarget = findNearest(world, this.getPosition(), Atlantis.class);
        if (fullTarget.isPresent() && moveTo(this, world, fullTarget.get(), scheduler)){
            //at atlantis trigger animation
            fullTarget.get().scheduleActions(scheduler, world, imageStore);
            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else{
            scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    private void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Octo_not_full octo = new Octo_not_full(getId(), getPosition(), getImages(), getResourceLimit(), 0, getActionPeriod(), getAnimationPeriod());
        removeEntity(this, world);
        unscheduleAllEvents(scheduler, this);
        octo.addEntity(world);
        octo.scheduleActions(scheduler, world, imageStore);
    }

    @Override
    public boolean moveTo(Entity entity, WorldModel world, Entity target, EventScheduler scheduler){
        if (entity.getPosition().adjacent(target.getPosition())){
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
}

