public class Path
{
    public double cost;
    public int distance;
    public int hops;
    public String pathString;

    public Path(double c, int d, int h, String s)
    {
      this.cost = c;
      this.distance = d;
      this.hops = h;
      this.pathString = s;
    }

    public String toString()
    {
      String s = "Hops: " + hops + " Cost: " + cost +"0 Distance: " + (double)distance + " Edges:";
      s = s + "\n\t" + pathString;
      return s;
    }
}    
