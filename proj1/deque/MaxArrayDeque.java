package deque;

import java.util.Comparator;

//Since a MaxArrayDeque has all of the methods that an ArrayDeque has
//it inherit them from ArayDeque, so it is the subclass of ArrayDeque
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        this.comparator = c;
    }

    public T max(Comparator<T> c){
        if (this.isEmpty()) return null;
        T result = this.get(0);
        for (int i = 1 ; i < this.size() ; i++) {
            if (c.compare(this.get(i),result) > 0){
                result = this.get(i);
            }
        }
        return result;
    }



    public T max(){
        return max(comparator);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return ((MaxArrayDeque<?>) o).max() == max();
    }
}
