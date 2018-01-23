package hw9;
import hw7.CampusRouteFindingModel;
public class RPICampusPathsMain {
	public static void main(String[] args) throws Exception {
		CampusRouteFindingModel model = new CampusRouteFindingModel("hw7/data/RPI_map_data_Nodes.csv","hw7/data/RPI_map_data_Edges.csv"); 
		new RPICampusPathsGUI(model);
	}
}
