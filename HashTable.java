/*
 * Ryan Flageolle
 * Computer Science
 * November 4, 2017
 * HW7 - Do It Yourself HashTables
 */

/** This is the Home made HashTable that uses chaining to handle collisions through
 * Linked lists of HashedNodes **/

import java.lang.ArrayIndexOutOfBoundsException;

/** HashTable.java is the class which sets up my home-made HashTable which uses
 *  chaining (of the HashedNode class) to catch collisions **/
public class HashTable {

    HashedNode[] index;
    int total; // number of buckets
    int occupied; // number of HashedNodes

    /** HashTable Constructor with integer parameter **/
    HashTable(int n){
        occupied = 0; //initialized Table is empty
        int possibleSize = nextPrime(n); //run through nextPrime
        System.out.println("Number of Buckets: " + possibleSize);
        index = new HashedNode[possibleSize];
        total = (index.length);
        emptyTable();
        System.out.println(occupied);
    }

    /** HashTable Constructor with String Array parameter **/
    HashTable(String[] str) {
        occupied = 0;
        int possibleSize = str.length;
        System.out.println("Number of Buckets: " + possibleSize);
        index = new HashedNode[nextPrime(possibleSize)];
        total = (index.length);
        emptyTable();
        toTable(str);
        System.out.println("Occupied: " + occupied);
    }

    /** Method to initialize a HashTable with x ammount of empty buckets **/
    public void emptyTable(){
        for (HashedNode hn : index){
            hn = null;
        }
    }

    /** Method to check if given int is a prime number **/
    public boolean isPrime(int x) {
        if ( x % 2 == 0){
            return false;
        }
        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0){
                return false;
            }
        }
        return true;
    }

    /** Method which uses isPrime to check if num is Prime and if not return the next
     * highest prime number **/
    public int nextPrime(int x) {
        if (isPrime(x)){
            return x;
        }
        else {
            for (int i = x; true; i++) {
                if (isPrime(i)){
                    return i;
                }
            }
        }
    }

    /** Method which returns predefined int representation of a given String **/
    public int hashWord(String str) {
        int hashed = 0;
        for (int i = 0; i < str.length(); i++){
            hashed += str.charAt(i);
        }
        return (hashed % total);
    }

    /** Method to prevent collisions, an improved HashFunction **/
    public int hashFunc(String str){
      int hashed = 3;
      for (int i = 0; i < str.length(); i++){
          hashed = hashed * (31) + str.charAt(i);
      }
      return Math.abs(hashed % total); // some of the hashed totals may be < 0
    }

    public long sfold(String s, int M) {
     int intLength = s.length() / 4;
     long sum = 0;
     for (int j = 0; j < intLength; j++) {
       char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
       long mult = 1;
       for (int k = 0; k < c.length; k++) {
	 sum += c[k] * mult;
	 mult *= 256;
       }
     }

     char c[] = s.substring(intLength * 4).toCharArray();
     long mult = 1;
     for (int k = 0; k < c.length; k++) {
       sum += c[k] * mult;
       mult *= 256;
     }

     return(Math.abs(sum) % M);
   }

    /** Method which adds a string into a hashTable **/
    public void add(String str) {
        Boolean added = false;
        int hv = hashFunc(str);
        HashedNode toAdd = new HashedNode(hv, str);

        if (index[hv] == null ){
           index[hv] = toAdd;
           //System.out.println("added as head: " + str);
           added = true;
           occupied++;
        }
        else {
            HashedNode current = index[hv];
            while (current.next != null) {
              current = current.next;
            }
            current.setNext(toAdd);
            added = true;
            //System.out.println("added after head: " + str);
        }
        if(!added){
            System.out.println("not added");
        }
    }

    /** Method to remove HashedNode associated with given string from the Hash table,
     *  if it exists in the table. **/
    public void remove(String str){
        boolean removed = false;
        int hv = hashWord(str);
        if (index[hv] != null) {
            HashedNode before = index[hv];
            HashedNode current = index[hv];
            while (current.next != null ){
                  if (current.equals(str)){
                      before.setNext(current.next);
                      System.out.println(str + " was removed from the table.");
                      removed = true;
                      occupied--;
                  }
                  before = current;
                  current = current.next;
            }
        }
        if (!removed){
            System.out.println("Sorry the word you entered does not exist in the table.");
        }
    }

    /** Method which loads an Array of Strings into an existing HashedTable **/
    public void toTable(String[] words){
        for(String str : words) {
            add(str);
        }
    }

    @Override
    public String toString(){
      String str = "";
      try{
        int currentBucket = 0;
        int length = index.length;
        for (int i = 0; i < length; i++){
            str += "Bucket " + i + ": ";
            HashedNode current = index[i];
            if (current != null){
                while (current.next != null){
                    str += current.toString() + ", ";
                    current = current.next;
                }
            }
            str += "\n";
            currentBucket++;
        }
      } catch(ArrayIndexOutOfBoundsException i){
          return str;
      }
      return str;
    }

    /** Method to print range of the HashTable **/
    public String toString(int start, int end){
      String str = "";
      try{
        for (int i = start; i < end; i++){
            str += "Bucket " + i + ": ";
            HashedNode current = index[i];
            if (current != null){
                while (current.next != null){
                    str += current.toString() + ", ";
                    current = current.next;
                }
            }
            str += "\n";
        }
      } catch(ArrayIndexOutOfBoundsException i){
          return str;
      }
      return str;
    }

    /** Method to print occupied buckets of the HashTable **/
    public String toStringOccupied(){
      String str = "";
      try{
        for (int i = 0; i < index.length; i++){
            HashedNode current = index[i];
            if (current != null){
                str += "Bucket " + i + ": ";
                while (current.next != null){
                    str += current.toString() + ", ";
                    current = current.next;
                }
            }
            str += "\n";
        }
      } catch(ArrayIndexOutOfBoundsException i){
          return str;
      }
      return str;
    }

}
