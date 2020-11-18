import processing.core.PImage;
import java.util.Random;
import java.util.List;
import java.util.Optional;

public class SGrass extends ActionableEntity{

    private static final String SGRASS_KEY = "seaGrass";
    private static final int SGRASS_NUM_PROPERTIES = 5;
    private static final int SGRASS_ID = 1;
    private static final int SGRASS_COL = 2;
    private static final int SGRASS_ROW = 3;
    private static final int SGRASS_ACTION_PERIOD = 4;
    
    private static final String FISH_ID_PREFIX = "fish -- ";
    private static final int FISH_CORRUPT_MIN = 20000;
    private static final int FISH_CORRUPT_MAX = 30000;
    private static final int FISH_REACH = 1;
    private static final String FISH_KEY = "fish";
    private static final Random rand = new Random();

    public SGrass(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
    }



    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
    }



    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = findOpenAround(world, this.getPosition());

        if (openPt.isPresent()) {
            Fish fish = new Fish(FISH_ID_PREFIX + this.getId(),
                    openPt.get(), imageStore.getImageList(FISH_KEY), FISH_CORRUPT_MIN +
                    rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN));
            fish.addEntity(world);
            fish.scheduleActions(scheduler, world, imageStore);
        }
        scheduleEvent(scheduler, createActivityAction(this, world, imageStore), this.getActionPeriod());
    }




    public Optional<Point> findOpenAround(WorldModel world, Point pos) {
        for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++) {
            for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++) {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (world.withinBounds(newPt) &&
                        !world.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }





    public static boolean parse(String [] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == SGRASS_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                    Integer.parseInt(properties[SGRASS_ROW]));
            SGrass seaGrass = new SGrass(properties[SGRASS_ID],
                    pt,imageStore.getImageList(SGRASS_KEY), 0, 0,
                    Integer.parseInt(properties[SGRASS_ACTION_PERIOD]), 0
            );
            seaGrass.tryAddEntity(world);
        }
        return properties.length == SGRASS_NUM_PROPERTIES;
    }
}
