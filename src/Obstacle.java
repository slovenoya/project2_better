import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity{
    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    public Obstacle(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images);
    }

    public static boolean parse(String [] properties, WorldModel world,
                                        ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Obstacle obstacle = new Obstacle( properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY),0,0,0,0);
            obstacle.tryAddEntity(world);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }
}
