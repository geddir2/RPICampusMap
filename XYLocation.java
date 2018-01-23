package hw7;
/**
 * object that stores the x and y location of a building or intersection
 *  X is x location on map of location
 *  Y is the y location on map of location
 */
public class XYLocation {
	private Float X;
	private Float Y;
	
	public XYLocation(Float x, Float y) {
		this.Y = y;
		this.X = x;
	}
	public Float getY() {
		return this.Y;
	}
	public Float  getX() {
		return this.X;
	}
}
