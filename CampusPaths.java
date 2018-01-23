package hw7;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
public class CampusPaths {
	/**
	 *  when b is called in main it prints out all buildings that arent an intersection
	 * @param campus model of campus
	 */
	public static void getAllBuildings(CampusRouteFindingModel campus) {
		Map<String, String> buildings = campus.getID();
		TreeSet<String> buildingsKeys = new TreeSet<String>(buildings.keySet());
		String bld = "";
		for (String name : buildingsKeys) {
			String id = buildings.get(name);
			if ((Integer.valueOf(id)<92)) {
				bld += name +","+ id +"\n";
			}
		}
		System.out.print(bld);
	}
	public static void main(String [] args)throws IOException {
		try {
			CampusRouteFindingModel model = new CampusRouteFindingModel("hw7/data/RPI_map_data_Nodes.csv","hw7/data/RPI_map_data_Edges.csv");
			Scanner reader = new Scanner(System.in);
			while (true) {
				String input = reader.nextLine();				
				if (input.equals("m")) {
					System.out.print("Menu:\n"+"   r to find a route\n" +"   b to see a list of all buildings\n" +"   q to quit\n");
				} else if (input.equals("b")) {
					getAllBuildings(model); 
				}
				else if (input.equals("r")) {
					System.out.print("First building id/name, followed by Enter: ");
					String start = reader.nextLine();
					System.out.print("Second building id/name, followed by Enter: ");
					String end = reader.nextLine();
					System.out.print(findPaths.findPath(start, end,model));
				} else if (input.equals("q")) {
					reader.close();
					return;
				} else {
					System.out.print("Unknown option\n");
				}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
	    	e.printStackTrace(System.err);
		}
	}

}
