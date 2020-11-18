import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;

public abstract class ActionableEntity extends Entity{
    private int actionPeriod;

    public ActionableEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public void executeActivityAction(ActivityAction action, EventScheduler scheduler) {
        executeActivity(action.getWorld(), action.getImageStore(), scheduler);
    }

    public Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new ActivityAction(entity, world, imageStore);
    }

    public void scheduleEvent(EventScheduler scheduler, Action action, long afterPeriod) {
        long time = System.currentTimeMillis() +
                (long)(afterPeriod * scheduler.getTimeScale());
        Event event = new Event(action, time, this);

        scheduler.getEventQueue().add(event);

        // update list of pending events for the given entity
        List<Event> pending = scheduler.getPendingEvents().getOrDefault(this, new LinkedList<>());
        pending.add(event);
        scheduler.getPendingEvents().put(this, pending);
    }

    public void unscheduleAllEvents(EventScheduler scheduler, Entity entity) {
        List<Event> pending = scheduler.getPendingEvents().remove(entity);
        if (pending != null) {
            for (Event event : pending){
                scheduler.getEventQueue().remove(event);
            }
        }
    }

    public void removeEntity(Entity entity, WorldModel world)
    {
        world.removeEntityAt(entity.getPosition());
    }



    public int getActionPeriod() {
        return actionPeriod;
    }

    public void setActionPeriod(int actionPeriod) {
        this.actionPeriod = actionPeriod;
    }
}