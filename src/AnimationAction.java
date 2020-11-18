public class AnimationAction extends Action{
    private int repeatCount;
    public AnimationAction(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
            AnimatedEntity animation = (AnimatedEntity) this.getEntity();
            animation.executeAnimationAction(this, scheduler);
    }

    public int getRepeatCount() {
        return repeatCount;
    }

}
