public interface StringList {

  String [] list = new String[1];
    int length = 0;
    
  public void add(String x);

  public boolean contains(String x);

  public String get(int i);

  public void remove(String x);

  public void clear();

  public int length();

  public void sort();
    
  public void mergeSort();

  public String random();

  public void filter(String x);
}
