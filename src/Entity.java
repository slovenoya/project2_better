import java.util.List;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */
public class Entity
{
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;

   public Entity(String id, Point position, List<PImage> images) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }

   public void nextImage(){
      imageIndex = (imageIndex + 1) % images.size();
   }

   public PImage getCurrentImage(){
      return images.get(imageIndex);
   }

   public void tryAddEntity(WorldModel world) {
      if (world.isOccupied(this.position)) {
         throw new IllegalArgumentException("position occupied");
      }
      addEntity(world);
   }

   public void addEntity(WorldModel world) {
      if (world.withinBounds(this.position)) {
         world.setOccupancyCell(this.position, this);
         world.getEntities().add(this);
      }
   }


   public String getId() {
      return id;
   }

   public Point getPosition() {
      return position;
   }

   public void setPosition(Point position) {
      this.position = position;
   }

   public List<PImage> getImages() {
      return images;
   }

}
