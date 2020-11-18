import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.List;

interface PathingStrategy
{
   /*
    * Returns a prefix of a path from the start point to a point within reach
    * of the end point.  This path is only valid ("clear") when returned, but
    * may be invalidated by movement of other entities.
    *
    * The prefix includes neither the start point nor the end point.
    */
   List<Node> computePath(Node start, Node end,
      Predicate<Node> canPassThrough,
      BiPredicate<Node, Node> withinReach,
      Function<Node, Stream<Node>> potentialNeighbors);

   static final Function<Node, Stream<Node>> CARDINAL_NEIGHBORS =
      point ->
         Stream.<Node>builder()
            .add(new Node(point.x, point.y - 1))
            .add(new Node(point.x, point.y + 1))
            .add(new Node(point.x - 1, point.y))
            .add(new Node(point.x + 1, point.y))
            .build();
}