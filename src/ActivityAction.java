public class ActivityAction extends Action{
    private WorldModel world;
    private ImageStore imageStore;
    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
            ActionableEntity actionable = (ActionableEntity) this.getEntity();
            actionable.executeActivityAction(this, scheduler);
    }

    public WorldModel getWorld() {
        return world;
    }

    public void setWorld(WorldModel world) {
        this.world = world;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }

    public void setImageStore(ImageStore imageStore) {
        this.imageStore = imageStore;
    }
}
