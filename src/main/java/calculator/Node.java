package calculator;

/**
 * An abstract node class which is extended by different node classes.
 */
public abstract class Node {
    public Node() {
    }

    /**
     * An abstract method that returns an integer. Implemented by different node classes.
     * @return None
     */
    public abstract int getValue();
}