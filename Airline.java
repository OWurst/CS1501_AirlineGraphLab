import java.util.*;
import java.io.*;

public class Airline
{
  static public Scanner kbd;
  static public Graph G;
  public static void main(String[] args) throws Exception
  {
    if(args.length < 1){System.out.println("Please run with the file name as a command line argument"); System.exit(0);}

    Scanner sc = new Scanner(new File(args[0]));

    G = new Graph(sc.nextInt());

    for(int i = 0; i < G.getSize(); i++)
    {
      G.setCity(i, sc.next());
    }

    while(sc.hasNext()) G.newEdge(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextDouble());

    System.out.println("Welcome to Limited Airlines, Limited");
    while(true)
    {
      System.out.println("Please choose from the options below:");
      System.out.println("1) List cities served and direct routes");
      System.out.println("2) Find a route");
      System.out.println("3) Quit program");

      kbd = new Scanner(System.in);
      int option = kbd.nextInt();
      if(option == 1) G.printGraph();
      else if(option == 2) routeFinder();
      else if(option == 3) break;
      else System.out.println("You have entered an unrecognized option. Starting over \n");
    }
  }

  static public void routeFinder()
  {
    int cost,hops,s=0,d = 0;
    String sCity = "", dCity = "";
    boolean found = false;
    String[] cities = G.getCities();

    while(!found)
    {
      System.out.print("Starting City: ");
      sCity = kbd.next();
      for(int i = 0; i < G.getSize(); i++)
      {
        if(sCity.equals(cities[i])){found = true; s = i; break;}
      }
      if(!found)
      {
        System.out.println("Sorry but your city is not found.  Check your spelling.");
        G.printCities();
      }
    }

    found = false;
    while(!found)
    {
      System.out.print("Destination City: ");
      dCity = kbd.next();
      for(int i = 0; i < G.getSize(); i++)
      {
        if(dCity.equals(cities[i])){found = true; d = i; break;}
      }
      if(!found)
      {
        System.out.println("Sorry but your city is not found.  Check your spelling.");
        G.printCities();
      }
    }

    while(true)
    {
      System.out.print("Maximum cost? (> 0): ");
      cost = kbd.nextInt();
      if(cost > 0) break;
    }

    while(true)
    {
      System.out.print("Maximum hops? (> 0) ");
      hops = kbd.nextInt();
      if(hops > 0) break;
    }

    ArrayList<Path> allPaths = G.findPaths(s,d,cost,hops);
    System.out.println("There are " + allPaths.size() + " paths from " + sCity + " to " + dCity + " with maximum cost " + (double)cost + " and at most " + hops + " hops");


    while(true)
    {
      System.out.println("\nHow would you like to view these paths?");
      System.out.println("1) Ordered by hops (fewest to most)");
      System.out.println("2) Ordered by cost (least expensive to most expensive)");
      System.out.println("3) Ordered by distance (shortest overall to longest overall)");
      System.out.println("or any other number to go back to the main menu");

      int choice = kbd.nextInt();

      if(choice < 1 || choice > 3) return;
      allPaths = G.sort(allPaths, choice);

      if(choice == 1)      System.out.println("Paths from " + sCity + " to " + dCity + " sorted by hops (fewest to most):");
      else if(choice == 2) System.out.println("Paths from " + sCity + " to " + dCity + " sorted by cost (cheapest to most expensive):");
      else if(choice == 3) System.out.println("Paths from " + sCity + " to " + dCity + " sorted by distance (shortest to longest):");

      for(Path p : allPaths)
      {
        System.out.println(p.toString());
      }
    }
  }
}
