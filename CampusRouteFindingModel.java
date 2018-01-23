	package hw7;

	import hw4.graph;

	import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

	/**
	 * a class representing the campus
	 *
	 */
public class CampusRouteFindingModel {
	private graph<String, Float> campusPaths;
	private Map<String, XYLocation> locationsOfBuildings;
	private Map<String, String> idFirst; 
	private Map<String, String> nameFirst;  
	
	public CampusRouteFindingModel(String buildings, String paths) throws Exception {
		nameFirst = new HashMap<String, String>();
		idFirst = new HashMap<String, String>();
		locationsOfBuildings = new HashMap<String, XYLocation>();
		campusPaths = new graph<String, Float>();
		CampusParse.readDataNodes(buildings, locationsOfBuildings, idFirst, nameFirst);
		CampusParse.readDataEdges(paths, campusPaths, locationsOfBuildings);
		checkRep();
	}
	/**
	 * returns the graph with campusPaths
	 * @return campusPaths
	 */
	public graph<String, Float> getGraph() {
		checkRep();
		return campusPaths;
	}
	/**
	 * returns the map with namesfirst
	 * @return nameFirst
	 */
	public Map<String, String> getName() {
		checkRep();
		return new HashMap<String,String>(nameFirst);
	}
	/**
	 * returns the map with namesfirst wihtout intersections
	 * @return nameFirst - intersections
	 */
	public Map<String, String> getNameNoIntersection() {
		HashMap<String,String> temp = new HashMap<String,String>();
		Iterator<String> it = nameFirst.values().iterator();
		while(it.hasNext()) {
			String x =  it.next();
			if(!x.startsWith("Intersection")) {
				temp.put(x, x);
			}
		}
		checkRep();
		return temp;
	}
	/**
	 * returns the map with idfirst
	 * @return idFirst
	 */
	public Map<String, String> getID() {
		checkRep();
		return new HashMap<String,String>(idFirst);

	}
	/**
	 * returns the map with locations
	 * @return locationsOfBuildings
	 */
	public Map<String, XYLocation> getLocations() {
		checkRep();
		return new HashMap<String,XYLocation>(locationsOfBuildings);
	}
	private void checkRep() {
			if (campusPaths == null ||locationsOfBuildings == null || idFirst == null || nameFirst == null)
				throw new RuntimeException("null componenet");
			
	}
}
