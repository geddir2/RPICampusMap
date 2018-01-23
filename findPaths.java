package hw7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import hw4.edges;
import hw4.graph;

public class findPaths {
	private static final double oneEighthPI = Math.PI / 8;
	private static final double threeEighthsPI = 3 * oneEighthPI;
	private static final double fiveEighthsPI = 5 * oneEighthPI;
	private static final double sevenEighthsPI = 7 * oneEighthPI;
	private static final double negOneEighthPI = -1 * oneEighthPI;
	private static final double negThreeEighthsPI = -1 * threeEighthsPI;
	private static final double negFiveEighthsPI = -1 * fiveEighthsPI;
	private static final double negSevenEighthsPI = -1 * sevenEighthsPI;
	/**
	 *  takes int he camous representation, and a start and end and returns the shortest path between the two 
	 * @param CHAR1 the start location 
	 * @param CHAR2 the end location
	 * @param campusModel is the CampusRouteFindingModel of the campus
	 * @return string with the path between CHAR1 and CHAR2
	 */
	public static  String findPath(String CHAR1, String CHAR2,CampusRouteFindingModel campusModel) {
		 graph<String, Float> g = campusModel.getGraph();
		 Map<String, String> idFirst = campusModel.getName();
		 Map<String,String> nameFirst = campusModel.getID();
		 Map<String, XYLocation> locations = campusModel.getLocations();
		 if(!(nameFirst.containsKey(CHAR1))&&(!(idFirst.containsKey(CHAR1)))) { 
			 if(!(nameFirst.containsKey(CHAR2))&&(!(idFirst.containsKey(CHAR2)))) {
				 if (CHAR1.equals(CHAR2)) {
					 return "Unknown building: ["+CHAR1+"]\n";
				 }
				 return "Unknown building: ["+CHAR1+"]\n"+"Unknown building: ["+CHAR2+"]\n";
			 }
				return "Unknown building: ["+CHAR1+"]\n";
			}
			if(!(nameFirst.containsKey(CHAR2))&&(!(idFirst.containsKey(CHAR2)))) {
				return "Unknown building: ["+CHAR2+"]\n";
			}
		if (nameFirst.containsKey(CHAR1)) {
			CHAR1 = nameFirst.get(CHAR1);
		}
		if (nameFirst.containsKey(CHAR2)) {
			CHAR2 = nameFirst.get(CHAR2);
		}
		String retrn = "Path from "+idFirst.get(CHAR1)+" to "+idFirst.get(CHAR2)+":\n";
		double fin = 0;
		String prev = CHAR1;
		if (Integer.valueOf(CHAR1) > 92) {
			if (Integer.valueOf(CHAR2) > 92) {
				if (CHAR1.equals(CHAR2)) {
					 return "Unknown building: ["+CHAR1+"]\n";
				 }
				return "Unknown building: ["+CHAR1+"]\n"+"Unknown building: ["+CHAR2+"]\n";
			}
			return "Unknown building: ["+CHAR1+"]\n";
		}
		if (Integer.valueOf(CHAR2) > 92) {
			return "Unknown building: ["+CHAR2+"]\n";
		}
		if (!(CHAR1.equals(CHAR2))) {
			ArrayList<edges<String, Double>> tmp = Dijkstra(CHAR1,CHAR2,g);
			if (tmp==null) {
				return "There is no path from "+idFirst.get(CHAR1)+" to "+idFirst.get(CHAR2)+".\n";
			}
			Iterator<edges<String, Double>> print = tmp.iterator();
			print.next();
			while (print.hasNext()) {
				edges<String, Double> temp = print.next();
				String direction = findDirection(locations.get(prev),locations.get(temp.getDest()));
				retrn = retrn+"\tWalk "+direction+" to ("+ idFirst.get(temp.getDest())+")\n";
				prev = temp.getDest();
				fin = temp.getLabel();
			}
		}
		retrn = retrn+String.format("Total distance: %.3f pixel units.\n", fin);
		return retrn;
	}
	/**
	 *  intakes 2 nodes and computes the shortest path between them using dijkstras algorith, and returns those nodes so they can be outputted
	 *  in the correct format in findpath
	 * @param CHAR1 initial char
	 * @param CHAR2 final char
	 * @return an arryalist with the values of edges that are in the shortest path
	 */
	public static  ArrayList<edges<String, Double>> Dijkstra(String CHAR1, String CHAR2, graph<String,Float> g) {
		PriorityQueue<ArrayList<edges<String, Double>>> active = 
			new PriorityQueue<ArrayList<edges<String, Double>>>(10,new Comparator<ArrayList<edges<String, Double>>>() {
				public int compare(ArrayList<edges<String, Double>> path1, ArrayList<edges<String, Double>> path2) {
					edges<String, Double> dest1 = path1.get(path1.size() - 1);
					edges<String, Double> dest2 = path2.get(path2.size() - 1);
					if (!(dest1.getLabel().equals(dest2.getLabel())))
						return dest1.getLabel().compareTo(dest2.getLabel());
					return path1.size() - path2.size();
				}
			});
			Set<String> known = new HashSet<String>();
			ArrayList<edges<String, Double>> begin = new ArrayList<edges<String, Double>>();
			begin.add(new edges<String, Double>(CHAR1, 0.0));
			active.add(begin);		
			while (!active.isEmpty()) {
				ArrayList<edges<String, Double>> minPath = active.poll();
				edges<String, Double> endOfMinPath = minPath.get(minPath.size() - 1);
				String minDest = endOfMinPath.getDest();
				double minCost = endOfMinPath.getLabel();
				if (minDest.equals(CHAR2)) {
					return minPath;
				}
				if (known.contains(minDest))
					continue;
				Set<edges<String,Float>> children = g.childrenOf(minDest);
				for (edges<String, Float> e : children) {
					if (!known.contains(e.getDest())) {
						double newCost = minCost + e.getLabel();
						ArrayList<edges<String, Double>> newPath = new ArrayList<edges<String, Double>>(minPath); 
						newPath.add(new edges<String, Double>(e.getDest(), newCost));
						active.add(newPath);
					}
				}
				
				known.add(minDest);
			}
			return null;
	}
	/**
	 * takes in 2 locations on map and returns the direction you need to walk between initial and goingto
	 * @param initial a XYLocation representing starting location
	 * @param goingTo a XYLocation representing ending locationa 
	 * @return a string representing direction
	 */
	public static String findDirection(XYLocation initial, XYLocation goingTo) {
		float goingToX = goingTo.getX();
		float goingToY = goingTo.getY();
		float initialX = initial.getX();
		float inititalY = initial.getY();
		float theta = (float) Math.atan2(goingToY - inititalY, goingToX - initialX);
		
		if (Math.abs(theta) <= oneEighthPI && Math.abs(theta) >= negOneEighthPI) {
				return "East";
			} else if (theta > oneEighthPI && theta < threeEighthsPI) {
				return "SouthEast";
			} else if (theta > negThreeEighthsPI && theta < negOneEighthPI) {
				return "NorthEast";
			} else if   (theta > threeEighthsPI && theta < fiveEighthsPI) {
				return "South";
			} else if (theta > negFiveEighthsPI && theta < negThreeEighthsPI){
				return "North";
			} else if (theta > fiveEighthsPI && theta < sevenEighthsPI) {
				return "SouthWest";
			} else if (theta > negSevenEighthsPI && theta < negFiveEighthsPI) {
				return "NorthWest";
			} else {
				return "West";
			}
	}
	//just like findpath but returns an array list of the path in XYLocations as opposed to a string format
	public static  ArrayList<XYLocation> findPath2(String CHAR1, String CHAR2,CampusRouteFindingModel campusModel) {
		 ArrayList<XYLocation> retrn = new ArrayList<XYLocation>();
		 graph<String, Float> g = campusModel.getGraph();
		 Map<String, String> idFirst = campusModel.getName();
		 Map<String,String> nameFirst = campusModel.getID();
		 Map<String, XYLocation> locations = campusModel.getLocations();
		 if(!(nameFirst.containsKey(CHAR1))&&(!(idFirst.containsKey(CHAR1)))) { 
			 if(!(nameFirst.containsKey(CHAR2))&&(!(idFirst.containsKey(CHAR2)))) {
				 if (CHAR1.equals(CHAR2)) {
					 retrn.add(locations.get(CHAR1));
					  return retrn;
				 }
				 retrn.add(locations.get(CHAR1));
				 retrn.add(locations.get(CHAR2));
				 return retrn;
			 }
			 retrn.add(locations.get(CHAR1));
			 return retrn;
			}
			if(!(nameFirst.containsKey(CHAR2))&&(!(idFirst.containsKey(CHAR2)))) {
				retrn.add(locations.get(CHAR2));
				return retrn;
			}
		if (nameFirst.containsKey(CHAR1)) {
			CHAR1 = nameFirst.get(CHAR1);
		}
		if (nameFirst.containsKey(CHAR2)) {
			CHAR2 = nameFirst.get(CHAR2);
		}
		retrn.add(locations.get(CHAR1));
		if (!(CHAR1.equals(CHAR2))) {
			ArrayList<edges<String, Double>> tmp = Dijkstra(CHAR1,CHAR2,g);
			if (tmp==null) {
				retrn.add(locations.get(CHAR1));
				retrn.add(locations.get(CHAR1));
				return retrn;
			}
			Iterator<edges<String, Double>> print = tmp.iterator();
			print.next();
			while (print.hasNext()) {
				edges<String, Double> temp = print.next();
				retrn.add(locations.get(temp.getDest()));
			}
		}
		return retrn;
	}

}
