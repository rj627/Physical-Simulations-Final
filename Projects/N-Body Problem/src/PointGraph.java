/*
 * Author: Rahul Jayaraman
 * Created: 9/7/2014
 * Modified: 9/11/2014
 * 
 * This class represents a point construct with two values, an x and a y value.
 * It includes methods to get x, get y, and a method for a string representation (toString()). 
 */
import java.awt.Graphics2D;

public class PointGraph extends java.awt.geom.Point2D
{
	//coordinate values
	private double xVal;
	private double yVal;

	/**
	 * Default zero-param constructor for a Point with x and y both set to 0. 
	 */
	public PointGraph()
	{
		xVal = 0.0;
		yVal = 0.0;
	} // close constructor
	
	/**
	 * Two-param constructor for a Point.
	 * @param x is the desired x-value
	 * @param y is the desired y-value
	 */
	public PointGraph(double x, double y)
	{
		xVal = x;
		yVal = y;
	} //close constructor
	
	/**
	 * @return the point's x-value
	 */
	public double getX()
	{
		return xVal;
	} //close getX()
	
	/**
	 * @return the point's y-value
	 */
	public double getY()
	{
		return yVal;
	} //close getY()
	
	/**
	 * Returns a string representation of the point in the form (x, y). 
	 */
	public String toString()
	{
		return "(" + xVal + ", " + yVal + ")";
	} //close toString()

	@Override
	public void setLocation(double x, double y) 
	{
		xVal = x;
		yVal = y;
		
	}
}	  //close Point class
