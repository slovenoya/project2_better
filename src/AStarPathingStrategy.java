import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
    public List<Node> computePath(Node start, Node end,
                                  Predicate<Node> canPassThrough,
                                  BiPredicate<Node, Node> withinReach,
                                  Function<Node, Stream<Node>> potentialNeighbors) {
        List<Node> path = new LinkedList<>();
        List<Node> openList = new ArrayList<>();
        List<Node> closeList = new ArrayList<>();
        List<Node> qualifiedNeighbors;
        Node current = start;
        openList.add(start);
        Comparator<Node> FComparator = (Node p1, Node p2) -> (p1.getFValue(end, p1.getGValue()) - p2.getFValue(end, p2.getGValue()));
        FComparator = FComparator.thenComparing((Node n1, Node n2) -> n1.getGValue() - n2.getGValue());
        while(!openList.isEmpty() && !withinReach.test(current, end)){
            qualifiedNeighbors = potentialNeighbors.apply(current).
                    filter(canPassThrough).
                    filter(p -> !p.equals(start)).
                    filter(neighbor -> !closeList.contains(neighbor)).
                    collect(Collectors.toList());
            if(!qualifiedNeighbors.isEmpty()) {
                for (Node p : qualifiedNeighbors) {
                    p.setPrior(current);
                    if(current.getPrior() != null) {
                        if(p.getGValue() == 0)
                            p.setGValue(current.getGValue() + 1);
                        else
                            p.setGValue(min(current.getGValue(), p.getGValue()));
                    }
                    else
                        p.setGValue(1);
                    if(!openList.contains(p)){
                        openList.add(p);
                    }
                }
            }
            openList.remove(current);
            closeList.add(current);
            if(!openList.isEmpty()) {
                openList.sort(FComparator);
                current = openList.get(0);
            }
        }
        Node p = null;
        if(openList.size() != 0)
             p = openList.get(0);
        while(p != null){
            path.add(0, p);
            p = p.getPrior();
        }
//        if(path.size() > 0)
            path.remove(0);
//        if(path.size() > 0)
//            path.remove(path.get(path.size() - 1));
        return path;
    }
    private int min(int a, int b){
        if (a < b)
            return a;
        else
            return b;
    }
}
