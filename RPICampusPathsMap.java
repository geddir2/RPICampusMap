package hw9;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import hw4.edges;
import hw4.graph;
import hw7.CampusRouteFindingModel;
import hw7.XYLocation;
import hw7.findPaths;
public class RPICampusPathsMap extends JComponent {
	private static final long serialVersionUID = 1L;
	private static final String mapLocation = "hw9/data/RPI_campus_map_2010_extra_nodes_edges.png";
	private int dWidth;
	private int dHeight;
	private double wRatio;
	private double hRatio;
	private static CampusRouteFindingModel model;
	private static List<XYLocation> coords;
	private BufferedImage img;
	
	/**
	 * Constructs a GUI of campus
	 * 
	 * @param mod the model of campus
	 * @requires mod doesn't equal null
	 */
	public RPICampusPathsMap(CampusRouteFindingModel mod) {
		if (mod == null)
			throw new IllegalArgumentException("Model cannot be null.");
		
		model = mod;
		dWidth = 1024;
		dHeight = 768;
		coords = null;
		this.setPreferredSize(new Dimension(dWidth, dHeight));
		try {
			img = ImageIO.read(new File(mapLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		wRatio = (double)dWidth / img.getWidth();
		hRatio = (double)dHeight / img.getHeight();
	}
	/**
	 * resets the gps to being a blank screen
	 */
	public void reset() {
		coords = null;
		repaint();
	}
	/**
	 * function to find the distance between chat 1 and char 2 to display for the total distance part of gps
	 * @param CHAR1 starting location
	 * @param CHAR2 ending location
	 * @param campusModel the model of the campus
	 * @return returns a double of the length between char1 and char2
	 */
	public  double findDistnace(String CHAR1, String CHAR2,CampusRouteFindingModel campusModel) {
		 coords = findPaths.findPath2(CHAR1,CHAR2,model);
		 repaint();
		 graph<String, Float> g = campusModel.getGraph();
		 Map<String,String> nameFirst = campusModel.getID();
		if (nameFirst.containsKey(CHAR1)) {
			CHAR1 = nameFirst.get(CHAR1);
		}
		if (nameFirst.containsKey(CHAR2)) {
			CHAR2 = nameFirst.get(CHAR2);
		}
		double fin = 0;
		if (!(CHAR1.equals(CHAR2))) {
			ArrayList<edges<String, Double>> tmp = findPaths.Dijkstra(CHAR1,CHAR2,g);
			if (tmp == null) {
				return -1;
			}
			Iterator<edges<String, Double>> print = tmp.iterator();
			print.next();
			while (print.hasNext()) {
				edges<String, Double> temp = print.next();
				fin = temp.getLabel();
			}
		}
		return fin;
	}
	
	/**
	 * a function to paint on the directions
	 * 
	 * @param g the graphics to use when painting
	 * @modifies the gui for the user
	 * @effect either displays the route if blank, or repaints new route while removing old route if theres alredy a route present 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

		dWidth = getWidth();
		dHeight = getHeight();
		wRatio = (double)dWidth / img.getWidth();
		hRatio = (double)dHeight/ img.getHeight();
		
		g2d.drawImage(img, 0, 0, dWidth, dHeight,0, 0, img.getWidth(), img.getHeight(), null);
		if (coords!= null) {	
			int sx = (int) Math.round(coords.get(0).getX() * wRatio);
			int sy = (int) Math.round(coords.get(0).getY() * hRatio);
			
			int cx = sx;
			int cy = sy;
			
			g2d.setColor(Color.CYAN);
		
			for (XYLocation c : coords) {
				int dx = (int) Math.round(c.getX() * wRatio);
				int dy = (int) Math.round(c.getY() * hRatio);
				
				g2d.drawLine(cx, cy, dx, dy);
				
				cx = dx;
				cy = dy;
			}
			
			g2d.setColor(Color.RED);
			g2d.fillOval(sx - 2, sy - 2, 10, 10);
			g2d.setColor(Color.MAGENTA);
			g2d.fillOval(cx - 2, cy - 2, 10, 10);
		}
	}
}
