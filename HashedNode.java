/*
 * Ryan Flageolle
 * Computer Science
 * November 4, 2017
 * HW7 - Do It Yourself HashTables
 */

/** The class builds the base unit of our HashTable, where the refrences to memory locations of our
 data is stored **/

public class HashedNode {

   private int hashValue; //value
   private String word; //key
   private int frequency;
   HashedNode next; //link to the next HashedNode if any

   HashedNode(int hv, String word){
      this.hashValue = hv;
      this.word = word;
      this.next = null;
   }

   // Getters for private data and setter for next as these are the only changes that should be made to
   // these ojects.
   public int getHashValue(){
     return hashValue;
   }

   public String getWord(){
      return word;
   }

   public void setNext(HashedNode next){
     this.next = next;
   }

   public void setHashValue(int i){
     this.hashValue = i;
   }

   public void setFrequency(){
     frequency++;
   }

   /** Method to check if HashedNode is last in chain **/
   public boolean isLast(HashedNode hn){
      if (hn != null){
        return (hn.next == null);
      }
      return true;
   }

   /** Method which returns whether a HashedNode contains the provided String **/
   public boolean equals(String str){
      return ((this.getWord()).equals(str));
   }

   // toString method that I always build for objects because printing is better
   @Override
   public String toString(){
      return word;
   }

}
