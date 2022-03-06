package logic.general;
/**
 *  Counter.
 *
 *  This is a class which describes a counter and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Counter {
    private int count;

    /**
     * Constructor.
     */
    public Counter() {
        count = 0;
    }

    /**
     * Increases the counter by number.
     * @param number the number
     */
    public void increase(int number) {
        count += number;
    }
    /**
     * Decreases the counter by number.
     * @param number the number
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * Returns the value of the counter.
     * @return the value of the counter.
     */
    public int getValue() {
        return count;
    }
}
