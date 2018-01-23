package hw7;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;

import hw4.graph;

public class CampusParse { 
/**
 * a parse function for the buildings and intersections
 * @param filename the filename of all the buildings and intersection 
 * @param locationsOfBuildings map to add the xyLocation and name of all nodes
 * @param idFirst map to be able to go between id and name of buildings 
 * @param nameFirst map to be able to go between id and name of buildings 
 * @throws Exception
 */
	  public static void readDataNodes(String filename, Map<String, XYLocation> locationsOfBuildings, Map<String, String> idFirst, Map<String, String> nameFirst) throws Exception {
		  BufferedReader reader = null;
		  try {
  			reader = new BufferedReader(new FileReader(filename));
  	        String line = null;
  	        while ((line = reader.readLine()) != null) { 	   
  	        		String[] tokens = line.split(",");
  	        			String name = tokens[0];
  	        			if (name.equals("")) {
  	        				name = "Intersection "+tokens[1];
  	        			}
  	    	    			String id = tokens[1];
  	    	    			float x = Float.valueOf(tokens[2]);
  	    	    			float y = Float.valueOf(tokens[3]);
  	    	    			idFirst.put(name, id);
  	    	    			nameFirst.put(id, name);
  	    	    			locationsOfBuildings.put(id, new XYLocation(x, y));  
 
  	        		}
  		}
  		catch (IOException e) {
  		    //  System.err.println(e.toString());
  		     // e.printStackTrace(System.err);
  		    }
  		
  }
	  /**
	   * a parse function that reads in all the edges and adds distance to campusPaths
	   * @param filename a file that represents all edges 
	   * @param campusPaths a graph representing all nodes and the distance of edges
	   * @param locationsOfBuildings a map that represents all edges and their xy location on graph
	   * @throws Exception
	   */
  public static void readDataEdges(String filename, graph<String, Float> campusPaths, Map<String, XYLocation> locationsOfBuildings) throws Exception {
	  BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        		String[] tokens = line.split(",");
	        		if (!(campusPaths.checkName(tokens[0]))){
	        			campusPaths.addNode(tokens[0]);
	        		}
	        		if (!(campusPaths.checkName(tokens[1]))){
	        			campusPaths.addNode(tokens[1]);
	        		}
	        		XYLocation xx = locationsOfBuildings.get(tokens[0]);
	        		XYLocation yy = locationsOfBuildings.get(tokens[1]);
	        		Float distance = (((yy.getY()-xx.getY())*(yy.getY()-xx.getY()))+((yy.getX()-xx.getX())*(yy.getX()-xx.getX())));
	        		distance = (float)Math.sqrt(distance);
	        		campusPaths.addEdge(tokens[0], tokens[1], distance);
	        		campusPaths.addEdge(tokens[1], tokens[0], distance);
	        }
		}
		catch (IOException e) {
		   //   System.err.println(e.toString());
		   //   e.printStackTrace(System.err);
		    }
  }

	
}
