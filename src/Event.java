final class Event {
   private Action action;
   private long time;
   private Entity entity;

   public Event(Action action, long time, Entity entity) {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }

   public Action getAction() {
      return action;
   }

   public void setAction(Action action) {
      this.action = action;
   }

   public long getTime() {
      return time;
   }

   public Entity getEntity() {
      return entity;
   }

   public void setEntity(Entity entity) {
      this.entity = entity;
   }
}
