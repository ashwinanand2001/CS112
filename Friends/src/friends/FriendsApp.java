package friends;

import java.io.*;
import java.util.*;

// Testing client for Friends
public class FriendsApp {

    public static void main (String[] args) {

	if ( args.length < 1 ) {
	    System.out.println("Expecting graph text file as input");
	    return;
	}

	String filename = args[0];
	try {
	    Graph g = new Graph(new Scanner(new File(filename)));

	    // Update p1 and p2 to refer to people on Graph g
	    // sam and sergei are from sample graph
	    String p1 = "nick";
	    String p2 = "aparna";
	    ArrayList<String> shortestChain = Friends.shortestChain(g, p1, p2);

	    // Testing Friends.shortestChain
	    System.out.println("Shortest chain from " + p1 + " to " + p2);
	    for ( String s : shortestChain ) {
		System.out.println(s);
	    }
        System.out.println("shortest result is "+shortestChain);

	    // ADD test for Friends.cliques() here
		String school = "rutgers";
	    ArrayList<ArrayList<String>> alist = Friends.cliques(g, school);
		System.out.println("Cliques " + school + " result is "+alist);
//	    }

	    
	    // ADD test for Friends.connectors() here

		System.out.println("connector out---" + Friends.connectors(g));
	} catch (FileNotFoundException e) {

	    System.out.println(filename + " not found");
	}
    }
}
