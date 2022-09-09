import java.lang.Math;

public class StringFixedArrayList implements StringList {

  String [] list;
  int length = 0;

  public StringFixedArrayList(int size) {
    list = new String[size];
  }
    
  public void add(String x) {
    if ( length == list.length ) {
      String[] temp = list;
      list = new String[list.length * 2];
      for (int i = 0; i < temp.length; i++) {
        list[i] = temp[i];
      }
    }
    list[length] = x;
    length++;
  }

  public boolean contains(String x) {
    for ( int i = 0 ; i < length ; i++ ) {
      if ( x.compareTo(list[i]) == 0) {
        return true;
      }
    }
    return false;
  }

  public String get(int i) {
    if ( i < length  && i >= 0) {
        return list[i];
    }
    System.out.println("Error in StringFixedArrayList at line 39! Was sent \"" + i + "\", length is: " + length);
    throw new IndexOutOfBoundsException("" + i);
  }

  public void remove(String x) {
    for ( int i = 0 ; i < length ; i++ ) {
      if ( x.compareTo(list[i]) == 0) {
        for ( int j = i ; j < length-1 ; j++ ) {
          list[j] = list[j+1];
        } 
        length = length - 1;
        return;
      }
    }
      System.out.println("Failure in remove");
  }

  public void clear() {
    length = 0;
  }

  public int length() {
    return length;
  }

  public void sort() {
    for ( int i = 0 ; i < length-1 ; i++ ) {
      for ( int j = 0 ; j < length-(i+1) ; j++ ) {
        if ( list[j].compareTo(list[j+1]) > 0 ) {
          // swap
          String temp = list[j];
          list[j] = list[j+1];
          list[j+1] = temp;
        }
      }
    }
  }

  public void mergeSort() {
    String[] data = new String[length];
    for ( int i = 0 ; i < length ; i++ ) {
      data[i] = list[i];
    }

    data = mergeSort(data);

    for ( int i = 0 ; i < length ; i++ ) {
      list[i] = data[i];
    }
  }

  private String[] mergeSort(String[] arr) {

    // Split arr into ar1 & ar2

    int n = arr.length;
    int n1 = n/2;
    int n2 = n - n1;
    String[] ar1 = new String[n1];
    String[] ar2 = new String[n2];

    for ( int i = 0 ; i < n1 ; i++ ) {
        ar1[i] = arr[i];
    }
    for ( int i = 0; i < n2 ; i++ ) {
      ar2[i] = arr[i+n1];
    }

    ar1 = mergeSort(ar1);
    ar2 = mergeSort(ar2);

    // now merge ar1 & ar2 into arr
    int i1 = 0, i2 = 0;
    for ( int i = 0 ; i < n ; i++ ) {
      if ( i2 < n2 && (i1 >= n1 || ar1[i1].compareTo(ar2[i2]) > 0) ) {
        arr[i] = ar2[i2];
        i2 = i2 + 1;
      } else {
        arr[i] = ar1[i1];
        i1 = i1 + 1;
      }
    }
    // Return the result
    return arr;
  }

  //returns a random string from the list
  public String random() {
    return list[randomInt(length)];
  }
  // Helper for random, 
  // Returns a random integer
  private int randomInt(int n) {
    double rand = Math.random();
    int randInt = (int) (rand = (rand * n) % n);
    return randInt;
  } 

  // Heart of the game
  // Filters out all impossible words
  public void filter(String x) {
      
    for (int i = 0; i < x.length(); i+=3) { // Runs through each symbol
      if (x.substring(i, i + 1).compareTo(" ") == 0) { // If gray
        gray(x.substring(i + 1, i + 2));
      } else if (x.substring(i, i + 1).compareTo("*") == 0) { // If yellow
        yellow((i / 3), x.substring(i + 1, i + 2));
      } else if (x.substring(i, i + 1).compareTo("|") == 0) { // if green
        green((i / 3), x.substring(i + 1, i + 2));
      } 
    }
  }

  // Helper for filter
  // Removes each word in the list which contains the given letter
  private void gray(String letter) {
    for (int i = 0; i < length; i++) {
      if (list[i].contains(letter)) {
        remove(list[i]);
        i--;
      }
    }
  }

  // Helper for filter
  // Removes each word in the list that either has the given letter in the same index or does not contain that letter at all
  private void yellow(int index, String letter) {
    for (int i = 0; i < length; i++) {
      if ((list[i].substring(index, index + 1).compareTo(letter) == 0) || 
        (!(list[i].contains(letter)))) {
        remove(list[i]);
        i--;
      }
    }
  }
  
  // Helper for filter
  // Removes each word that does not have the given letter in the given index
  private void green(int index, String letter) {
    for (int i = 0; i < length; i++) {
      if (!(list[i].substring(index, index + 1).compareTo(letter) == 0)) {
        remove(list[i]);
        i--;
      }
    }
  }
}
