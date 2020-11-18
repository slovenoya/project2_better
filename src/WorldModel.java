import processing.core.PImage;
import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private static final String OCTO_KEY = "octo";
   private static final String OBSTACLE_KEY = "obstacle";
   private static final String FISH_KEY = "fish";
   private static final String ATLANTIS_KEY = "atlantis";
   private static final String SGRASS_KEY = "seaGrass";
   private static final String BGND_KEY = "background";
   private static final int PROPERTY_KEY = 0;

   public WorldModel(int numRows, int numCols, Background defaultBackground) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();
      for (int row = 0; row < numRows; row++) {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public void load(Scanner in, WorldModel world, ImageStore imageStore) {
      int lineNumber = 0;
      while (in.hasNextLine()) {
         try {
            if (!processLine(in.nextLine(), world, imageStore)) {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e) {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e) {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private boolean processLine(String line, WorldModel world, ImageStore imageStore) {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY]) {
            case BGND_KEY:
               return Background.parse(properties, world, imageStore);
            case OCTO_KEY:
               return Octo_not_full.parse(properties, world, imageStore);
            case OBSTACLE_KEY:
               return Obstacle.parse(properties, world, imageStore);
            case FISH_KEY:
               return Fish.parse(properties, world, imageStore);
            case ATLANTIS_KEY:
               return Atlantis.parse(properties, world, imageStore);
            case SGRASS_KEY:
               return SGrass.parse(properties, world, imageStore);
         }
      }

      return false;
   }

   public boolean withinBounds(Point pos) {
      return pos.getY() >= 0 && pos.getY() < numRows &&
              pos.getX() >= 0 && pos.getX() <numCols;
   }

   public boolean isOccupied(Point pos) {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   public void removeEntityAt(Point pos) {
      if (withinBounds(pos) && getOccupancyCell(pos) != null) {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos) {
      if (withinBounds(pos)) {
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background) {
      if (withinBounds(pos)) {
         setBackgroundCell(pos, background);
      }
   }

   public Entity[][] getOccupancy(){
      return occupancy;
   }
   public Optional<Entity> getOccupant(Point pos) {
      if (isOccupied(pos)) {
         return Optional.of(getOccupancyCell(pos));
      }
      else {
         return Optional.empty();
      }
   }

   private Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.getY()][pos.getX()];
   }

   public void setOccupancyCell(Point pos, Entity entity)
   {
      occupancy[pos.getY()][pos.getX()] = entity;
   }

   private Background getBackgroundCell(Point pos)
   {
      return background[pos.getY()][pos.getX()];
   }

   private void setBackgroundCell(Point pos,Background background) {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public int getNumRows() {
      return numRows;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public int getNumCols() {
      return numCols;
   }

   public void setBackground(Background[][] background) {
      this.background = background;
   }

}
