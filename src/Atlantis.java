import processing.core.PImage;

import java.util.List;

public final class Atlantis extends AnimatedEntity{
    private static final String ATLANTIS_KEY = "atlantis";
    private static final int ATLANTIS_NUM_PROPERTIES = 4;
    private static final int ATLANTIS_ID = 1;
    private static final int ATLANTIS_COL = 2;
    private static final int ATLANTIS_ROW = 3;
    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Atlantis(String id, Point position, List<PImage> images,int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduleEvent(scheduler, createAnimationAction(this, ATLANTIS_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        unscheduleAllEvents(scheduler, this);
        removeEntity(this, world);
    }

    public static boolean parse(String [] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == ATLANTIS_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                    Integer.parseInt(properties[ATLANTIS_ROW]));
            Atlantis atlantis = new Atlantis(properties[ATLANTIS_ID],
                    pt, imageStore.getImageList(ATLANTIS_KEY), 0, 0);
            atlantis.tryAddEntity(world);
        }

        return properties.length == ATLANTIS_NUM_PROPERTIES;
    }

}
