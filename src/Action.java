/*
Action: ideally what our various entities might do in our virutal world
 */

public abstract class Action
{
   private Entity entity;

   public Entity getEntity() {
      return entity;
   }

   public Action(Entity entity) {
      this.entity = entity;
   }

   public abstract void executeAction(EventScheduler scheduler);
}
