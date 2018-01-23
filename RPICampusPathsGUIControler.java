package hw9;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import hw7.CampusRouteFindingModel;
public class RPICampusPathsGUIControler extends JPanel {
private static final long serialVersionUID = 1L;
	private CampusRouteFindingModel model;
	private RPICampusPathsMap view;
	private Set<String> buildings;
	private JLabel startingBuildingSelection;
	private JComboBox<String> startingSelectionBox;
	private JLabel endingBuildingSelection;
	private JComboBox<String> endingSelectionBox;
	private JLabel distance;
	
	/**
	 * makes a gui for campus
	 * @param m model of campus route finding model
	 * @param v view of gui of campus 
	 * @requires m, v != null
	 */
	public RPICampusPathsGUIControler(CampusRouteFindingModel m, RPICampusPathsMap v) {
		if (m == null) {
			throw new IllegalArgumentException("Model cannot be null.");
		}
		if (v == null) {
			throw new IllegalArgumentException("View cannot be null.");
		}
		model = m;
		view = v;
		buildings = new TreeSet<String>(model.getNameNoIntersection().values());
		JPanel sp = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel bp = new JPanel(new GridLayout(2, 1));

		startingBuildingSelection = new JLabel("Start (red dot): ");
		startingSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		endingBuildingSelection = new JLabel("End (magenta dot): ");
		endingSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		
		JButton getRoute = new JButton("Get route!");
		getRoute.addActionListener(new UpdateActionListener());
		JButton reset = new JButton("Reset");
		reset.addActionListener(new UpdateActionListener());
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		sp.add(startingBuildingSelection, c);
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 5;
		sp.add(startingSelectionBox, c);  
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		sp.add(endingBuildingSelection, c);
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 5;
		sp.add(endingSelectionBox, c);
		
		bp.add(getRoute);
		bp.add(reset);
		bp.setBackground(Color.decode("#FDFDF4"));
		sp.setBackground(Color.decode("#FDFDF4"));
		
		this.add(sp);
		this.setBackground(Color.decode("#FDFDF4"));
		this.add(bp);
		
		JPanel distance_panel = new JPanel(new GridLayout(2, 1));
		JLabel distanceDiscription = new JLabel("  Total distance:");
		distance = new JLabel();
		distance_panel.add(distanceDiscription);
		distance_panel.add(distance);
		distance_panel.setBackground(Color.decode("#FDFDF4"));
		this.add(distance_panel);
	}
	/**
	 * either updates path or resetes depending on input
	 */
	private class UpdateActionListener implements ActionListener {
		/**
		 * Happens whenever an action is performed
		 * 
		 * @param e an event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();	
			if (action.equals("Get route!")) {
				String startingBuildingSelection = startingSelectionBox.getSelectedItem().toString();
				String endingBuildingSelection = endingSelectionBox.getSelectedItem().toString();
				double d =view.findDistnace(startingBuildingSelection, endingBuildingSelection, model)*3.2;
				repaint();
				if (d<0) {
					distance.setText("  No path found");
				}else {
					distance.setText(String.format("  %.0f feet", d));
				}
				
			} else {	
				view.reset();
				distance.setText("");
			}
		}
	}
}
