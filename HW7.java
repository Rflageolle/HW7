/*
 * Ryan Flageolle
 * Computer Science
 * November 4, 2017
 * HW7 - Do It Yourself HashTables
 */

 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.util.*;
 import java.io.File;
 import java.util.regex.Pattern;
 import java.util.regex.Matcher;
 import java.util.Scanner;
 import java.io.ObjectOutputStream;
 import java.io.FileOutputStream;
 import java.io.FileWriter;

/** HW7.java is the main class which brings in words from a txtfile removes special
 *  characters, duplicate words and then adds each word into a hashtable,
 *  it then uses the same array of words to fill java's hashtable. This program
 *  returns 3 files, HW7.ser - a visual representation of my home-made HashTable,
 *  HW7.out - what is contained in the first 5 buckets of my home-made HashTable,
 *  HW7.hash - what is contained in the first 5 buckets of java's HashTable. **/
 
public class HW7 implements java.io.Serializable{

    File text; // users file
    Scanner in = new Scanner(System.in); // to get input from users
    Scanner fromFile; // to scan file

    ArrayList<String> inFromFile = new ArrayList<String>();
    String[] toTable;
    String[] noDuplicates;
    HashTable table;

    int tempWords = 0;

    HW7(){
      System.out.println("Please enter the name of the file"); // prompt the user to begin
      loadFile(in.next()); // create the file
      loadArray(); // create the initial array
      removeDuplicates();
      System.out.println(noDuplicates.length);
      table = new HashTable(noDuplicates.length);
      table.toTable(noDuplicates);
      printRange(0,6);
      createJavaHT(this);
    }

    public static void main(String[] args){
        HW7 current = new HW7();
        current.serialize();
    }

    /** Method to load user selected file and attatch a Scanner to it **/
    public void loadFile(String fileName) {
        try {
            text = new File(fileName);
            fromFile = new Scanner(text); // create a scanner for the file
        } catch (FileNotFoundException fnfe) {
            System.out.println("Please enter a valid file name!");
            loadFile(in.next());
        }
    }

    /** Method for creating the initial Array **/
    public void loadArray() {
            while (fromFile.hasNext()) { // while there are still words in the file
                inFromFile.add(fromFile.next().toLowerCase());
                tempWords++;
            } // end for

            toTable = new String[tempWords];

            for(int i = 0; i < tempWords; i++) {
                String add = removeSpecialChar(inFromFile.get(i));
                toTable[i] = add;
            }
    }

    /** Method to remove special characters from strings **/
    public String removeSpecialChar(String str){
        Pattern pattern = Pattern.compile("[^a-zA-Z']");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

    public void removeDuplicates() {

        inFromFile = new ArrayList<String>();
        tempWords = 0;
        Arrays.sort(toTable);

        for (int i = 1; i < toTable.length; i++) {
            if (!(toTable[i].equals(toTable[i-1]))) {
                inFromFile.add(toTable[i]);
                tempWords++;
            }
        }

        inFromFile.removeAll(Collections.singleton(""));

        noDuplicates = new String[tempWords];
        for (int i = 0; i < inFromFile.size(); i++) {
            noDuplicates[i] = inFromFile.get(i);
        }
    }

    /** Method to remove duplicate Strings **/
    public void createTable(){
          table = new HashTable(noDuplicates);
    }

    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream("HW7.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.toString());
            out.close();
            fileOut.close();
            System.out.println("Serialized HashTable saved in /HW7.ser");
        } catch (IOException i){
            i.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return table.toString();
    }

    public String toStringOccupied(){
        return table.toStringOccupied();
    }

    public void printRange(int a, int b){
        try{
            File out = new File("HW7.out");
            FileWriter toOut = new FileWriter(out);
            String str = table.toString(a, b);
            toOut.write(str);
            toOut.close();
        }catch (IOException i){
          i.printStackTrace();
        }
    }

    public void createJavaHT(HW7 h){
        int size = promptUser();
        Hashtable<Integer, String> ht = new Hashtable<Integer, String>(size,(float)(0.78));
        for (String str : h.noDuplicates){
            ht.put(h.table.hashWord(str), str);
        }
        try{
            File out = new File("HW7.hash");
            FileWriter toOut = new FileWriter(out);
            String str = "";
            for (int i = 0; i < 6; i++){
              str += "Bucket " + i + ": " + ht.get(i) + "\n";
            }
            toOut.write(str);
            toOut.close();
        }catch (IOException i){
          i.printStackTrace();
        }
    }

    public int promptUser(){
      int rtn = 0;
      int size = 0;
      Boolean cont = false;
      do{
        System.out.println("With the # of unique words being " + noDuplicates.length + "\n"
        + "What should the size of the Hashtable be (think prime #'s): ");
        cont = in.hasNextInt();
        size = in.nextInt();
      } while(!cont);
      if (table.isPrime(size)){
          System.out.println("Sure we will use " + size + " as our table's size.");
          rtn = size;
      }
      else {
          System.out.println("Sorry your number is not prime, So we're going to use " + table.nextPrime(size) + " instead.");
          rtn = table.nextPrime(size);
      }
      return rtn;
    }
}
