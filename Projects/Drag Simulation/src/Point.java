/*
 * Author: Rahul Jayaraman
 * Created: 9/7/2014
 * Modified: 9/11/2014
 * 
 * This class represents a point construct with two values, an x and a y value.
 * It includes methods to get x, get y, and a method for a string representation (toString()). 
 */
public class Point 
{
	//coordinate values
	private double xVal;
	private double yVal;

	/**
	 * Default zero-param constructor for a Point with x and y both set to 0. 
	 */
	public Point()
	{
		xVal = 0.0;
		yVal = 0.0;
	} // close constructor
	
	/**
	 * Two-param constructor for a Point.
	 * @param x is the desired x-value
	 * @param y is the desired y-value
	 */
	public Point(double x, double y)
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
	
	public double getMagnitude()
	{
		return Math.sqrt(Math.pow(xVal, 2.0) + Math.pow(yVal,  2.0));
	}
}	  //close Point class
