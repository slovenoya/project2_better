final class Node
{
   public final int x;
   public final int y;
   private int gValue = 0;
   public int fValue = 0;
   private Node prior;

   public Node getPrior() {
      return prior;
   }

   public void setPrior(Node prior) {
      this.prior = prior;
   }

   public Node(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + (x +1) + "," +( y + 1)+ ")" + ": gvalue:" + gValue;
   }

   public boolean equals(Object other) {
      return other instanceof Node &&
         ((Node)other).x == this.x &&
         ((Node)other).y == this.y;
   }

   public int hashCode(){
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   int getGValue(){
      return gValue;
   }

   void setGValue(int newG){
      gValue = newG;
   }

   private int getHValue(Node destination){
      return Math.abs(destination.x - x) + Math.abs(destination.y - y);
   }

   public boolean adjacent(Node p) {
      return (x == p.x && Math.abs(y - p.y) == 1) ||
              (y == p.y && Math.abs(x - p.x) == 1);
   }

   int getFValue(Node destination, int gValue) {
      return getHValue(destination) + gValue;

   }
}
