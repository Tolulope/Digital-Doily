import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class Window extends JFrame {

	// creating the buttons needed for the control panel
	JButton save = new JButton("Save");
	JButton penColour = new JButton("Pen Colour");

	// thicknesses are put into the array
	// for the combobox as strings
	// I would've used a method if I had more time
	String[] thicknesses = { "Choose thickness of pen:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	JComboBox thicknessesList = new JComboBox(thicknesses);
	// the same applies here for the array
	String[] sectorsNumber = { "Choose number of sectors:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	JComboBox sectorsNumberList = new JComboBox(sectorsNumber);
	JRadioButton showSectorLines = new JRadioButton("Show Sector Lines");
	JRadioButton noSectorLines = new JRadioButton("Don't show sector lines");
	JRadioButton doReflectPoints = new JRadioButton("Reflect Points");
	JRadioButton dontReflectPoints = new JRadioButton("Don't reflect points");

	JButton undo = new JButton("Undo");
	JButton clear = new JButton("Clear");

	// creates button groups for the options
	// the user has
	ButtonGroup sectorLines = new ButtonGroup();
	ButtonGroup reflectPoints = new ButtonGroup();

	// creates delete on click checkBox
	// which will delete images when clicked
	// when the checkbox is checked
	JCheckBox deleteOnClick = new JCheckBox("Delete Images On Click");

	// colour of the pen that will
	// eventually be passed into the drawnpoint class
	Color colour;

	public Window() {
		// layout of gui initialised in startup
		setTitle("Digital Doilies by Tolulope Ogunremi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 700);

		// JPanel containing gallery images
		JPanel galleryImages = new JPanel();
		galleryImages.setLayout(new GridLayout(6, 2));
		galleryImages.setPreferredSize(new Dimension(200, 500));

		// JPanel for right hand side with gallery images
		// on it
		JPanel rightHandSide = new JPanel();
		rightHandSide.setLayout(new FlowLayout());
		rightHandSide.add(galleryImages);

		// display panel created
		// taking gallery images as a parameter
		DisplayPanel displayPanel = new DisplayPanel(galleryImages);
		displayPanel.setLayout(new FlowLayout());

		// adding buttons to button group
		sectorLines.add(showSectorLines);
		sectorLines.add(noSectorLines);

		// sector options JPanel
		// so that they don't lose each other
		JPanel sectorOptions = new JPanel();
		sectorOptions.setLayout(new BoxLayout(sectorOptions, BoxLayout.Y_AXIS));
		sectorOptions.add(showSectorLines);
		sectorOptions.add(noSectorLines);
		sectorOptions.add(Box.createVerticalGlue());
		// paints the JPanel with sector lines
		// (calls drawSectorLines in paintCompoenet method)
		showSectorLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showSectorLines.isSelected())
					displayPanel.showSectorLines = true;
				displayPanel.repaint();
			}
		});

		// paints the JPanel without sector lines
		// (doesn't call drawSectorLines in paintCompoenet method)
		noSectorLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (noSectorLines.isSelected()) {
					displayPanel.showSectorLines = false;
					displayPanel.repaint();
				}
			}
		});

		// adding buttons to button group
		reflectPoints.add(doReflectPoints);
		reflectPoints.add(dontReflectPoints);

		// reflect options JPanel
		// so that they don't lose each other
		JPanel reflectOptions = new JPanel();
		reflectOptions.setLayout(new BoxLayout(reflectOptions, BoxLayout.Y_AXIS));
		reflectOptions.add(doReflectPoints);
		reflectOptions.add(dontReflectPoints);
		sectorOptions.add(Box.createVerticalGlue());

		// adding all of the functionality required
		// to the control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(save);
		controlPanel.add(penColour);
		controlPanel.add(thicknessesList);
		controlPanel.add(sectorsNumberList);
		controlPanel.add(sectorOptions);
		controlPanel.add(reflectOptions);
		controlPanel.add(undo);
		controlPanel.add(clear);
		controlPanel.add(deleteOnClick);

		// if deleted on click is checked
		// panel will be set to allow
		// deletion on click
		deleteOnClick.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (deleteOnClick.isSelected()) {
					displayPanel.setTimeToGo(true);
				} else {
					displayPanel.setTimeToGo(false);
				}
			}

		});

		// save button will call save on the displayPanel
		// capturing the screen and scaling it
		// depending on its width and height
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel.save(galleryImages.getWidth(), galleryImages.getHeight());

			}
		});

		// pen colour will call a colour chooser
		// and set the drawn point pen colour to the colour chosen
		penColour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colour = JColorChooser.showDialog(Window.this, "Pen Tools", colour);
				if (colour == null) {
					colour = Color.BLACK;
				} else {
					displayPanel.pointColour = colour;
				}
			}
		});

		// parses string and converts it to an int
		// sends this to set the number of sectors in the
		// display panel
		// if nothing is selected, default number of sectors
		// is 2
		sectorsNumberList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectorsNumberList.getSelectedItem() != "Choose number of sectors:") {
					displayPanel.setNumberOfSectors(Integer.parseInt((String) sectorsNumberList.getSelectedItem()));
				} else {
					displayPanel.setNumberOfSectors(2);
				}
			}

		});

		// parses string and converts it to an int
		// sends this to set the thickness of the pen
		// in the display panel
		// if nothing is selected, default thickness
		// is 2
		thicknessesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (thicknessesList.getSelectedItem() != "Choose thickness of pen:") {
					displayPanel.setStroke(Integer.parseInt((String) thicknessesList.getSelectedItem()));
				} else {
					displayPanel.setStroke(2);
				}
			}

		});

		// marks the relfectPoints boolean as true in the
		// display panel as true
		doReflectPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (doReflectPoints.isSelected() == true) {
					displayPanel.isReflected = true;
				}
			}
		});

		// marks the relfectPoints boolean as false in the
		// display panel as true
		dontReflectPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dontReflectPoints.isSelected() == true) {
					displayPanel.isReflected = false;
				} else {
					displayPanel.isReflected = true;
				}
			}
		});

		// calls undo method in displayPanel
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel.undo();
			}
		});

		// clear button will call the clear method on the displayPanel
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel.clearPoints();
			}
		});

		// JPanel for top of the doily
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(displayPanel, BorderLayout.CENTER);
		top.add(rightHandSide, BorderLayout.EAST);

		// adding the top and bottom parts of the
		// window together
		Container window = this.getContentPane();
		window.setLayout(new BorderLayout());
		window.add(top, BorderLayout.CENTER);
		window.add(controlPanel, BorderLayout.SOUTH);

	}

}