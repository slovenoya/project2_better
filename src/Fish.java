import processing.core.PImage;
import java.util.Random;
import java.util.List;

public final class Fish extends ActionableEntity{
    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    private static final String FISH_KEY = "fish";
    private static final int FISH_NUM_PROPERTIES = 5;
    private static final int FISH_ID = 1;
    private static final int FISH_COL = 2;
    private static final int FISH_ROW = 3;
    private static final int FISH_ACTION_PERIOD = 4;

    private static final Random rand = new Random();


    public Fish(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();  // store current position before removing

        removeEntity(this, world);
        unscheduleAllEvents(scheduler, this);

        Crab crab = new Crab(this.getId() + CRAB_ID_SUFFIX,
                pos, imageStore.getImageList(CRAB_KEY),
                this.getActionPeriod() / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN + rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN)
        );

        crab.addEntity(world);
        crab.scheduleActions(scheduler, world, imageStore);
    }


    public static boolean parse(String [] properties, WorldModel world,
                                    ImageStore imageStore) {
        if (properties.length == FISH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                    Integer.parseInt(properties[FISH_ROW]));
            Fish fish = new Fish(properties[FISH_ID], pt, imageStore.getImageList(FISH_KEY),
                    Integer.parseInt(properties[FISH_ACTION_PERIOD]));
            fish.tryAddEntity(world);
        }

        return properties.length == FISH_NUM_PROPERTIES;
    }
}
