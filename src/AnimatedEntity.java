import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends ActionableEntity{
    private int animationPeriod;
    public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }
    public Action createAnimationAction(Entity entity, int repeatCount) {
        return new AnimationAction(entity,  repeatCount);
    }
    public void executeAnimationAction(AnimationAction action, EventScheduler scheduler) {
        action.getEntity().nextImage();
        if (action.getRepeatCount() != 1) {
            AnimatedEntity ae = (AnimatedEntity) action.getEntity();
            ae.scheduleEvent(scheduler, ae.createAnimationAction(action.getEntity(), Math.max(action.getRepeatCount() - 1, 0)), animationPeriod);
        }
    }
    public int getAnimationPeriod(){
        return animationPeriod;
    }



}
