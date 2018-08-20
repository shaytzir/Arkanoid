package coregame;
/** * @author shaytzir
 *
 */
public class Counter {
     private int count;
     /**
      * constructor.
      * @param counter a number
      */
     public Counter(int counter) {
          this.count = counter;
     }

     /**
      * add number to current count.
      * @param number an int number
      */
    public void increase(int number) {
         this.count = this.count + number;
    }

    /**
     * subtract number from current count.
     * @param number an int number
     */
    public void decrease(int number) {
         this.count = this.count - number;
    }

    /**
     * getter.
     * @return current count.
     */
    public int getValue() {
         return this.count;
    }
}