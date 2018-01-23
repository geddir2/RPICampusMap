package hw9;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import hw7.CampusRouteFindingModel;

public class RPICampusPathsGUI {
	private static final String WINDOW_TITLE = "RPI Campus GPS";
	private CampusRouteFindingModel model;  
	private JFrame contentFrame;  
	
	/**
	 * Makes a GUI view
	 * 
	 * @param m the model of campus 
	 * @requires m != null
	 */
	public RPICampusPathsGUI(CampusRouteFindingModel m) {
		if (m == null) {
			throw new IllegalArgumentException("Model cannot be null.");
		}
			
		model = m;
		contentFrame = new JFrame(WINDOW_TITLE);
		contentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentFrame.setPreferredSize(new Dimension(1024, 768));
		contentFrame.setLayout(new BoxLayout(contentFrame.getContentPane(), BoxLayout.Y_AXIS));
		

		RPICampusPathsMap view = new RPICampusPathsMap(model);
		RPICampusPathsGUIControler control = new RPICampusPathsGUIControler(model, view);
		control.setPreferredSize(new Dimension(1024, 70));
		
		contentFrame.add(view);
		contentFrame.add(control);
		contentFrame.pack();
		contentFrame.setVisible(true);
	}
}
