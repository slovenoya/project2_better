import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public final class Octo_not_full extends Octo{
    private static final String OCTO_KEY = "octo";
    private static final int OCTO_NUM_PROPERTIES = 7;
    private static final int OCTO_ID = 1;
    private static final int OCTO_COL = 2;
    private static final int OCTO_ROW = 3;
    private static final int OCTO_LIMIT = 4;
    private static final int OCTO_ACTION_PERIOD = 5;
    private static final int OCTO_ANIMATION_PERIOD = 6;

    public Octo_not_full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<ActionableEntity> notFullTarget = findNearest(world, this.getPosition(), Fish.class);
        if (!notFullTarget.isPresent() ||
                !moveTo(this, world, notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore)) {
            scheduleEvent(scheduler, createActivityAction(this, world, imageStore), getActionPeriod());
        }
    }
    @Override
    public boolean moveTo(Entity entity, WorldModel world, Entity target, EventScheduler scheduler) {
        if (entity.getPosition().adjacent(target.getPosition())){
            Octo o = (Octo)entity;
            o.setResourceCount(getResourceCount() + 1);
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

    private boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getResourceCount() >= getResourceLimit()) {
            Octo_full octo = new Octo_full(getId(), getPosition(), getImages(),  getResourceLimit(),
                    getResourceCount(), getActionPeriod(), getAnimationPeriod());
            removeEntity(this, world);
            unscheduleAllEvents(scheduler, this);
            octo.addEntity(world);
            octo.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }


    public static boolean parse(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == OCTO_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                    Integer.parseInt(properties[OCTO_ROW]));
            Octo_not_full octo_not_full = new Octo_not_full(properties[OCTO_ID],
                    pt, imageStore.getImageList(OCTO_KEY),
                    Integer.parseInt(properties[OCTO_LIMIT]),0,
                    Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                    Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]));
            octo_not_full.tryAddEntity(world);
        }

        return properties.length == OCTO_NUM_PROPERTIES;
    }
}
