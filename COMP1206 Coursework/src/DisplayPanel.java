import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

//special displaypanel class to enable drag drawing 
//and start off with lines
class DisplayPanel extends JPanel {

	// variables to be used by this class
	// or that need to passed into and
	// out of this class
	JLabel savedImage;
	JPanel rightHandSidePanel;

	DrawnPoint pointPassed;
	Point startPoint, endPoint;

	ArrayList<DrawnPoint> pointsDrawn = new ArrayList<DrawnPoint>();
	ArrayList<Integer> collectionsOfDrawnPoints = new ArrayList<Integer>();

	Color pointColour;
	int thickness = 10;
	int numberOfSectors = 2;
	boolean showSectorLines = false;
	boolean isReflected;
	boolean timeToGo = false;
	int numberOfPictures = 0;

	// add listeners in constructor
	// (upon creation)
	public DisplayPanel(JPanel panel) {
		// both mouse listener and mouse motion listener are required
		DisplayPanelListeners listener = new DisplayPanelListeners();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		this.rightHandSidePanel = panel;

	}

	// gets whether or not it's time for
	// the pictures to go
	public boolean getTimeToGo() {
		return timeToGo;
	}

	// sets whether or not it's time for
	// the pictures to go
	public void setTimeToGo(boolean endOfTime) {
		this.timeToGo = endOfTime;
	}

	// sets number of sectors
	public void setNumberOfSectors(int secNumber) {
		this.numberOfSectors = secNumber;
		repaint();
	}

	// sets stroke of pen
	public void setStroke(int strokeDough) {
		this.thickness = strokeDough;
		repaint();
	}

	// adds a point selected into arraylist of drawn points
	public void addPoint(Point startPoint, Point endPoint) {
		pointsDrawn.add(new DrawnPoint(startPoint, endPoint, pointColour, thickness, isReflected));
		repaint();
	}

	// clears arraylist of drawn points
	// therefore clearing rotated points
	// and reflected points at once
	public void clearPoints() {
		pointsDrawn.clear();
		repaint();
	}

	// counter for naming pictures
	int counter = 0;
	String filename;

	// using a bufffered image to capture the display panel
	// and saving it as a file
	// passing this buffered image into the save doily method
	// along with the panel's width and height
	// (for more information, see saveDoily)
	public void save(int width, int height) {
		filename = "pic" + counter + ".png";
		BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		this.paint(g);
		saveDoily(bi, width, height);
		g.dispose();
		try {
			ImageIO.write(bi, "png", new File(filename));
			counter++;
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// gets the last integer put into the arraylist
	// passing that into the for loop
	// to loop that many times in removing points
	// from points drawn arraylist
	public void undo() {
		if (collectionsOfDrawnPoints.size() != 0) {
			int pointsToRemove = collectionsOfDrawnPoints.get(collectionsOfDrawnPoints.size() - 1);

			for (int i = 0; i < pointsToRemove; i++) {
				pointsDrawn.remove(pointsDrawn.size() - 1);
			}

			collectionsOfDrawnPoints.remove(collectionsOfDrawnPoints.size() - 1);
		}

		repaint();
	}

	// scales image captured of display panel
	// sends image to addImage method
	public void saveDoily(BufferedImage image, int width, int height) {
		Image scaledImage = image.getScaledInstance(width / 2, height / 6, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(scaledImage);

		addImage(imageIcon);
	}

	// adds image to gallery panel
	// which is on the right hand side panel
	// increments counter to endure images
	// don't exceed 12
	public void addImage(ImageIcon imageIcon) {

		if (imageIcon != null) {
			this.savedImage = new JLabel(imageIcon);
			if (numberOfPictures < 12) {
				rightHandSidePanel.add(new GalleryPanel(savedImage, this));
				numberOfPictures++;
			}
			rightHandSidePanel.revalidate();
			rightHandSidePanel.repaint();

		}

	}

	// removes image from gallerypanel
	// decrements counter so that there is
	// space for up to 12 images
	public void removeImage(GalleryPanel pan) {
		if (timeToGo == true) {
			rightHandSidePanel.remove(pan);
			numberOfPictures--;
		}
		rightHandSidePanel.repaint();
		rightHandSidePanel.revalidate();

	}

	// inner class of mouse listeners and mouse motion listeners;
	class DisplayPanelListeners implements MouseListener, MouseMotionListener {
		// DrawnPoint pointPassed;

		int pointsPassedCounter;

		// when the mouse is dragged, each point passed
		// is added to the arraylist of drawn points
		public void mouseDragged(MouseEvent e) {

			// gets endpoint
			endPoint = (e.getPoint());

			// add start and end point to array list
			addPoint(startPoint, endPoint);
			pointsPassedCounter++;

			// set startpoint to the end point
			startPoint = endPoint;

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			startPoint = e.getPoint();

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// sets startpoint to null
			// so that line isn't drawn from
			// previous endpoint
			startPoint = null;
			// adds counter pounts to arraylist
			collectionsOfDrawnPoints.add(pointsPassedCounter);
			// resets counted points
			pointsPassedCounter = 0;

		}

	}

	// establishes points to draw lines
	protected Point2D pointAt(double radians, double radius) {
		double x = radius * Math.cos(radians);
		double y = radius * Math.sin(radians);

		return new Point2D.Double(x, y);
	}

	// translates points
	protected Point2D translate(Point2D point, Point2D to) {
		Point2D newPoint = new Point2D.Double(point.getX(), point.getY());
		newPoint.setLocation(point.getX() + to.getX(), point.getY() + to.getY());
		return newPoint;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// draws lines in black
		g2d.setColor(Color.BLACK);

		double startAngle = 0;
		double divisions = numberOfSectors;
		double delta = 360.0 / divisions;

		// gets center point
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		// calculates radius
		int radius = Math.min(centerX, centerY) * 2;
		Point2D centerPoint = new Point2D.Double(centerX, centerY);
		double angle = startAngle;
		// draws lines in for loop
		if (showSectorLines == true) {
			for (int index = 0; index < divisions; index++) {
				Point2D point = pointAt(Math.toRadians(angle), radius);
				point = translate(point, centerPoint);
				g2d.draw(new Line2D.Double(centerPoint, point));
				angle += delta;
			}

		}

		for (DrawnPoint p : pointsDrawn) {
			// sets new colour
			// sets new thickness
			g2d.setColor(p.getColour());
			g2d.setStroke(new BasicStroke(p.getThickness()));

			if (p.getStartPoint() != null) {
				g2d.draw(new Line2D.Double(p.getStartPoint().getX(), p.getStartPoint().getY(), p.getEndPoint().getX(),
						p.getEndPoint().getY()));

				double alpha = 2 * Math.PI / numberOfSectors;

				for (int i = 0; i < numberOfSectors; i++) {

					
					// radius of both points (sqrt - distance)
					double squareStart = Math.pow((p.startPoint.getX() - centerX), 2)
							+ Math.pow((p.startPoint.getY() - centerY), 2);
					double radStart = Math.sqrt(squareStart);
					double squareEnd = Math.pow((p.endPoint.getX() - centerX), 2)
							+ Math.pow((p.endPoint.getY() - centerY), 2);
					double radEnd = Math.sqrt(squareEnd);

					// angle from centre of screen to point
					// draw line between points

					double angleStart = Math.atan2(p.startPoint.getY() - centerY, p.startPoint.getX() - centerX);
					double angleEnd = Math.atan2(p.endPoint.getY() - centerY, p.endPoint.getX() - centerX);

					double newAngleStart = (i * alpha) + angleStart;
					double startX = radStart * Math.cos(newAngleStart);
					double startY = radStart * Math.sin(newAngleStart);
					Point newStart = new Point((int) startX + centerX, (int) startY + centerY);

					double newAngleEnd = (i * alpha) + angleEnd;
					double endX = radEnd * Math.cos(newAngleEnd);
					double endY = radEnd * Math.sin(newAngleEnd);
					Point newEnd = new Point((int) endX + centerX, (int) endY + centerY);

					g2d.draw(new Line2D.Double(newStart, newEnd));

					// reflect checked
					if (p.reflectedDough == true) {
						g2d.draw(new Line2D.Double((int) startX + centerX, -(int) startY + centerY,
								(int) endX + centerX, -(int) endY + centerY));
					}

				}

			}

		}

	}

}