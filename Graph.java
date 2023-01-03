import java.util.*;

 public class Graph
 {
   private int V;
   private Edge[] vertices; //parellel arrays one holds city names, one holds
   private String[] cities; //          pointers to edge list
   private int numEdges;
   private ArrayList<Path> paths;
   private boolean[] marked;

   public Graph(int size)
   {
      V = size;
      vertices = new Edge[size];
      cities = new String[size];
   }

   public int getSize()
   {
     return V;
   }

   public ArrayList<Path> findPaths(int start, int dest, int maxC, int maxH)
   {
     paths = new ArrayList<Path>();
     String path = "";
     int tHops = 0, tDist = 0;
     double tCost = 0;
     marked = new boolean[V];
     for(int i = 0; i < V; i++) marked[i] = false;

     dfs(start, vertices[start], dest, (double)maxC, maxH, tCost, tHops, tDist, path);
     return paths;
   }

   public void dfs(int currFrom, Edge curr, int dest, double maxCost, int maxHops, double tCost, int tHops, int tDist, String path)
   {
     marked[currFrom] = true;
     if(tCost > maxCost || tHops > maxHops) marked[currFrom] = false; //are we over a constraint, reduntant but just there to skip over other two cases
     else if(currFrom == dest) paths.add(new Path(tCost,tDist,tHops,path));
     else while(curr != null)
     {
       int to = curr.toIndex;

       String add = "";
       if(tHops != 0 && tHops % 3 == 0) add += "\n\t"; //just for formatting
       add += "[(" + cities[currFrom] + "," + cities[curr.toIndex] + ") c:" + curr.cost + "0 d:" + (double)curr.distance + "]";

       if(!marked[to]) dfs(to, vertices[to], dest, maxCost, maxHops, tCost + curr.cost, tHops+1, tDist + curr.distance, path + add);

       curr = curr.next;
     }
     marked[currFrom] = false;
   }

   public void printCities()
   {
     System.out.println("Cities served: ");
     for(int i = 0; i < V; i++) System.out.println("\t" + cities[i]);
     System.out.println();
   }

   public String[] getCities()
   {
     return cities;
   }

   public void setCity(int i, String name)
   {
     cities[i] = name;
   }

   public void newEdge(int from, int to, int dist, double cost)
   {
     setEdge(from, to, dist, cost);
     setEdge(to,from,dist,cost);        //undirected graph needs edges two ways
   }

   public void setEdge(int from, int to, int dist, double cost)
   {
     if(vertices[from] == null) vertices[from] = new Edge(to,dist,cost,null);
     else
     {
       Edge edge = new Edge(to,dist,cost,null);
       Edge curr = vertices[from];

       while(curr.next != null) curr = curr.next;

       curr.next = edge;
     }
     numEdges++;
   }

   public void printGraph()
   {
     printCities();

     System.out.println("Direct Routes:");
     System.out.println("From             To               Cost      Distance");
     System.out.println("----             --               ----      --------  ");

     for(int i = 0; i < V; i++)
     {
       Edge curr = vertices[i];
       while(curr != null)
       {
         System.out.printf("%-17s", cities[i]); //from
         System.out.printf("%-17s", cities[curr.toIndex]); //to
         System.out.printf("%-10.2f", curr.cost); //cost
         System.out.printf("%-8.1f%n", (double) curr.distance); //distance
         if(curr.next != null) curr = curr.next;
         else break;
       }
     }
     System.out.println();
   }

   public ArrayList<Path> sort(ArrayList<Path> path, int choice)
   {
     if(choice == 1) path.sort(new ByHops());
     else if(choice == 2) path.sort(new ByCost());
     else if(choice == 3) path.sort(new ByDist());
     else return null;

     return path;
   }

   private class Edge
   {
     protected int toIndex;
     protected int distance;
     protected double cost;
     protected Edge next;

     public Edge(int to, int distance, double cost, Edge next)
     {
       this.toIndex = to;
       this.distance = distance;
       this.cost = cost;
       this.next = next;
     }
   }
   public class ByCost implements Comparator<Path>
   {
     public int compare(Path a, Path b)
     {
         if (a.cost > b.cost) return 1;
         else if (a.cost < b.cost) return -1;
         else return 0;
     }
   }
   public class ByDist implements Comparator<Path>
   {
     public int compare(Path a, Path b)
     {
         if (a.distance > b.distance) return 1;
         else if (a.distance < b.distance) return -1;
         else return 0;
     }
   }
   public class ByHops implements Comparator<Path>
   {
     public int compare(Path a, Path b)
     {
         if (a.hops > b.hops) return 1;
         else if (a.hops < b.hops) return -1;
         else return 0;
     }
   }
 }
