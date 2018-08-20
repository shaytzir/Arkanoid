package menu;

/**
 * a task interface.
 * @param <T> type of the task
 */
public interface Task<T> {
   /**
    * runs the task.
    * @return the return value of the task
    */
   T run();
}