package utilitys;

import java.util.List;

public class Tuple<K, N> {

    private K firstValue;
    private N secondValue;

    public Tuple(K firstValue, N secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public K getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(K firstValue) {
        this.firstValue = firstValue;
    }

    public N getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(N secondValue) {
        this.secondValue = secondValue;
    }
}
