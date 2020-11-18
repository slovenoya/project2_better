import processing.core.PImage;

import java.util.List;

public final class Quake extends AnimatedEntity{
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images,  actionPeriod, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduleEvent(scheduler, createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        unscheduleAllEvents(scheduler, this);
        removeEntity(this, world);
    }

}
