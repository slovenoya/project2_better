import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }
   public void updateOnTime(long time) {
      while (!this.eventQueue.isEmpty() && this.eventQueue.peek().getTime() < time) {
         Event next = this.eventQueue.poll();
         removePendingEvent(next);
         next.getAction().executeAction(this);
      }
   }

   private void removePendingEvent(Event event) {
      List<Event> pending = this.pendingEvents.get(event.getEntity());
      if (pending != null) {
         pending.remove(event);
      }
   }

   public PriorityQueue<Event> getEventQueue() {
      return eventQueue;
   }
   public Map<Entity, List<Event>> getPendingEvents() {
      return pendingEvents;
   }
   public double getTimeScale() {
      return timeScale;
   }
}
