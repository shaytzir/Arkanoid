package coregame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaytzir
 *
 */
public class HighScoresTable implements Serializable {
    private static final int DEFAULTSIZE = 5;
    private int size;
    private List<ScoreInfo> scores;
    /**
     * Create an empty high-scores table with the specified size.
     * @param size table holds up to size top scores.
     */
   public HighScoresTable(int size) {
       this.size = size;
       this.scores = new ArrayList<ScoreInfo>();
   }

   /**
    * Add a high-score.
    * @param score new high score to add
    */
   public void add(ScoreInfo score) {
      // int tempIndex = this.size + 1;
       if (this.scores.isEmpty()) {
           this.scores.add(score);
       } else {
           int movingIndex = this.getRank(score.getScore()) - 1;
           if (movingIndex + 1 <= this.size) {
               this.scores.add(movingIndex, score);
               if (this.scores.size() > this.size) {
                   this.scores.remove(this.scores.size() - 1);
               }
           } else {
               return;
           }
       }
   }

   /**
    *
    * @return table size.
    */
   public int size() {
       return this.size;
   }

   /**
    *
    * @return current high scores, The list is sorted such that the highest scores come first.
    */
   public List<ScoreInfo> getHighScores() {
       return this.scores;
   }

   /**
    *
    * @param score new score to check on - where will be added
    * @return return the rank of the current score: where will it be on the list if added?
    * (rank 1 means highest, rank "size" means lowest, rank > size means too low to get into the table.
    */
   public int getRank(int score) {
       int rank = this.size + 1;
       if (this.scores.isEmpty()) {
           return 0;
       }
       for (int i = 0; i < this.scores.size(); i++) {
           if (score > this.scores.get(i).getScore()) {
               rank = i + 1;
               return rank;
           }
       }
       if (this.scores.size() <= this.size()) {
           rank = this.scores.size() + 1;
       }
       return rank;
   }

   /**
    * clears the table.
    */
   public void clear() {
       for (int i = 0; i < this.scores.size(); i++) {
           this.scores.remove(i);
       }
   }


   /**
    * load table data from file , Current table data is cleared.
    * @param filename a file
    * @throws IOException if something goes wrong with reading/creating high score table
    * @throws ClassNotFoundException goes wrong
    */
   public void load(File filename) throws IOException, ClassNotFoundException {
       this.clear();
       HighScoresTable table = null;
       ObjectInputStream objectInputStream = null;
       try {

           objectInputStream = new ObjectInputStream(
                                   new FileInputStream(filename));

           // unsafe down casting, we better be sure that the stream really contains a Person!
           table = (HighScoresTable) objectInputStream.readObject();
           this.size = table.size;
           this.scores = table.scores;
       } catch (FileNotFoundException e) { // Can't find file to open
           throw new FileNotFoundException();
       } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
           throw new ClassNotFoundException();
       } catch (IOException e) { // Some other problem
           throw new IOException();
       } finally {
           try {
               if (objectInputStream != null) {
                   objectInputStream.close();
               }
           } catch (IOException e) {
               System.err.println("Failed closing file: " + filename);
           }
       }
   }

   /**
    * saves table date to the specified file.
    * @param filename file
    * @throws IOException goes wrong with saving this file
    */
   public void save(File filename) throws IOException {
       ObjectOutputStream objectOutputStream = null;
       try {
           objectOutputStream = new ObjectOutputStream(
                                  new FileOutputStream(filename));
           objectOutputStream.writeObject(this);
       } catch (IOException e) {
           System.err.println("Failed saving object");
           e.printStackTrace(System.err);
           throw new IOException();
       } finally {
           try {
               if (objectOutputStream != null) {
                   objectOutputStream.close();
               }
           } catch (IOException e) {
               System.err.println("Failed closing file: " + filename);
               throw new IOException();
           }
       }
   }


   /**
    * read a table from file and returns it.
    * @param filename file to read from
    * @return the table, or if table doesnt exist/problem with reading returns empty table
    */
   public static HighScoresTable loadFromFile(File filename) {
       HighScoresTable table = new HighScoresTable(DEFAULTSIZE);
       ObjectInputStream objectInputStream = null;
       try {

           objectInputStream = new ObjectInputStream(
                                   new FileInputStream(filename));

           // unsafe down casting, we better be sure that the stream really contains a Person!
           table = (HighScoresTable) objectInputStream.readObject();
       } catch (FileNotFoundException e) { // Can't find file to open
           return table;
       } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
           return table;
       } catch (IOException e) { // Some other problem
           return table;
       } finally {
           try {
               if (objectInputStream != null) {
                   objectInputStream.close();
               }
           } catch (IOException e) {
               System.err.println("Failed closing file: " + filename);
           }
       }
       return table;
   }
}