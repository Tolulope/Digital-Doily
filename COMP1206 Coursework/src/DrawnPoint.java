import javax.swing.*;
import java.awt.*;
import java.util.*;

class DrawnPoint {
	// attributes that belong to every line
	// drawn on the doily

	// each line has an start point, an end point
	// a colour, a thickness and whether or not it is reflected
	Point startPoint;
	Point endPoint;
	Color pointColour;
	int thickness;
	boolean reflectedDough;

	// these attributes has to be declared when
	// creating a point
	public DrawnPoint(Point startPoint, Point endPoint, Color pointColour, int thickness, boolean reflectedDough) {
		super();

		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.pointColour = pointColour;
		this.thickness = thickness;
		this.reflectedDough = reflectedDough;
	}

	// getters and setter for each
	// attribute of the drawn point
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point newStartPoint) {
		this.startPoint = newStartPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point newEndPoint) {
		this.endPoint = newEndPoint;
	}

	public Color getColour() {
		return pointColour;
	}

	public void setColour(Color newColour) {
		this.pointColour = newColour;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int newThickness) {
		this.thickness = newThickness;
	}

}