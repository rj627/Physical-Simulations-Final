/*
 * Author: Rahul Jayaraman
 * Date Created: 9/10/2014
 * Date Modified: 9/11/2014
 * 
 * This class implements a three-point derivative using a parabolic approximation. This
 * can fit to curves' contours better than simple secant approximations can. The 
 * ThreePointDeriv class includes methods to return the a-value for a typical parabola of 
 * the form a*(x-h)^2, return the h-value for a parabola of that form, return the 
 * derivative at a particular point given three other points, and return the derivative at 
 * all points in a given array. 
 */

public class DerivativesUpdated
{	
public static final double DELTA_X = 0.01;
public static final double DELTA = 0.1;
public static final double LOWER_BOUND = 0.00;
public static final double UPPER_BOUND = 1.00;
	
	public static double[] populateArray(double xO, int n)
	{
		double[] yVals = new double[n];
		
		for (int i = 0; i < n; i++)
		{
			yVals[i] = Math.pow(Math.E, -Math.pow(xO, 2));
			xO+=DELTA_X;
		}
		
		return yVals;
	}
	
	public static double leftDeriv(double[] arr, int index)
	{
		if (index <= 0 || index >= arr.length-1) throw new IndexOutOfBoundsException();
		return (arr[index]-arr[index-1])/DELTA_X;
	}
	
	public static double rightDeriv(double[] arr, int index)
	{
		if (index < 0 || index >= arr.length-1) throw new IndexOutOfBoundsException();
		return (arr[index+1]-arr[index])/DELTA_X;
	}
	
	public static Point[] arrFunction()
	{
		//declare array and other variables
		Point[] values = new Point[11];
		
		//we need this because we can't change constants but we need to increment this
		double low = LOWER_BOUND;
		
		for (int i = 0; i < values.length; i++)
		{
			values[i] = new Point(low, Math.sin(low));
			low+=DELTA;
		} //close for
		
		return values;
		
	} 	  //close arrGaussian()
	
	public static double midDeriv(double[] arr, int index)
	{
		return (rightDeriv(arr,index)+leftDeriv(arr,index))/2;
	}
	
	public static double actualDeriv(double x)
	{
		return Math.cos(x);
	}
	
	/**
	 * This method returns the a-value in the generic parabola formula y = a*(x-h)^2. 
	 * We do this by putting in all the x- and y-values of the three points using an
	 * expression derived from first principles and simultaneous equations. 
	 * @param x1, x2, x3 are x-values of the respective points on the parabola
	 * @param y1, y2, y3 are y-values of the respective points on the parabola
	 * @return the value of a in the expression (for the exact expression that we
	 * 		   use to generate a value for a, see the code)
	 */
	public static double returnA(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		//simplifying the parts of the fractional expression to combine and return later
		double addend1 = x3*(y2-y1);
		double addend2 = x2*(y1-y3);
		double addend3 = x1*(y3-y2);
		double denom = (x1-x2)*(x1-x3)*(x2-x3);
		
		return (addend1+addend2+addend3)/denom;
		
	}	//close returnA()
	
	/**
	 * This method returns the h-value in the generic parabola formula y = a*(x-h)^2. 
	 * We do this by putting in all the x- and y-values of the three points using an
	 * expression derived from first principles and simultaneous equations. 
	 * @param x1, x2, x3 are x-values of the respective points on the parabola
	 * @param y1, y2, y3 are y-values of the respective points on the parabola
	 * @return the value of h in the expression (for the exact expression that we
	 * 		   use to generate a value for h, see the code)
	 */
	public static double returnH(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		//simplifying the parts of the fractional expression to combine later
		double addend1 = Math.pow(x3,2.0)*(y1-y2);
		double addend2 = Math.pow(x1, 2)*(y2-y3);
		double addend3 = Math.pow(x2, 2)*(y3-y1);
		double denominator = 2*(x3*(y1-y2) + x1*(y2-y3) + x2*(y3-y1));
		
		return (addend1 + addend2 + addend3)/denominator;
		
	}	//close returnH()
	
	/**
	 * This method is the 'meat' of the implementation. It combines the two above methods
	 * and returns the derivative at a point based upon the parabolic approximation to the
	 * curve. We get the values of a and h and then differentiate the expression 
	 * y = a*(x-h)^2 to get y' = 2*a*(x-h), which we return with specific values
	 * @param a, b, c are the points through which the parabola will pass
	 * @param x is where we want to take the derivative
	 * @return the value of the derivative to the parabolic approximation at x
	 */
	public static double parabolicDeriv(Point a, Point b, Point c, double x)
	{
		//get the values to use later
		double valueOfA = returnA(a.getX(), b.getX(), c.getX(), a.getY(), b.getY(), c.getY());
		double valueOfH = returnH(a.getX(), b.getX(), c.getX(), a.getY(), b.getY(), c.getY());
		
		return 2.0*valueOfA*(x-valueOfH);
		
	}	//close parabolicDeriv()
	
	/**
	 * This method returns the value of the derivative at all points in an array using
	 * the parabolic approximation. We use the point and the ones before and after it to
	 * 'construct a parabola' and get the derivative at the point. This has been shown to
	 * be more accurate than a simple secant-line approximation. Question -- how do we deal
	 * with the endpoints? We treat them as part of the parabola for the second and second
	 * to last points and find the derivative at that point using the other parabola. 
	 * @param arr is the array of points we want to find derivatives at
	 * @return an array of derivatives at all the points
	 * @precondition arr is sorted by increasing point's x-value
	 */
	public static double[] derivOfAllPoints(Point[] arr)
	{
		double[] derivs = new double[arr.length];
		
		//dealing with endpoint 1
		double first = parabolicDeriv(arr[0], arr[1], arr[2], arr[0].getX());
		derivs[0] = first;
		
		//filling the middle of the array
		for (int i = 1; i < arr.length-1; i++)
		{
			derivs[i] = parabolicDeriv(arr[i-1], arr[i], arr[i+1], arr[i].getX());
		} //close for loop
		
		//dealing with endpoint 2
		double last = parabolicDeriv(arr[arr.length-3], arr[arr.length-2], arr[arr.length-1], 
				arr[arr.length-1].getX());
		derivs[arr.length-1] = last;
		
		return derivs;
		
	}	//close derivOfAllPoints()
	
	public static double valueOfAbc(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		double num = -x2*y1 + x3*y1 + x1*y2 - x3*y2 - x1*y3 + x2*y3;
		double denom = (-x1 + x2)*(x2 - x3)*(-x1 + x3);
		
		return -num/denom;
	}
	
	public static double valueOfaBc(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		double num = Math.pow(x2, 2)*y1 - Math.pow(x3, 2)*y1 - Math.pow(x1, 2)*y2 + Math.pow(x3, 2)*y2
				+Math.pow(x1, 2)*y3 - Math.pow(x2, 2)*y3;
		
		double denom = (x1 - x2)*(x2-x3)*(x1-x3);
		
		return -num/denom;
	}
	
	public static double parabolicDerivNew(Point a, Point b, Point c, double x)
	{
		//get the values to use later
		double valueOfA = valueOfAbc(a.getX(), b.getX(), c.getX(), a.getY(), b.getY(), c.getY());
		double valueOfB = valueOfaBc(a.getX(), b.getX(), c.getX(), a.getY(), b.getY(), c.getY());
		
		return 2.0*valueOfA*x + valueOfB;
		
	}	//close parabolicDeriv()
	
	/**
	 * This method returns the value of the derivative at all points in an array using
	 * the parabolic approximation. We use the point and the ones before and after it to
	 * 'construct a parabola' and get the derivative at the point. This has been shown to
	 * be more accurate than a simple secant-line approximation. Question -- how do we deal
	 * with the endpoints? We treat them as part of the parabola for the second and second
	 * to last points and find the derivative at that point using the other parabola. 
	 * @param arr is the array of points we want to find derivatives at
	 * @return an array of derivatives at all the points
	 * @precondition arr is sorted by increasing point's x-value
	 */
	public static double[] derivOfAllPointsNew(Point[] arr)
	{
		double[] derivs = new double[arr.length];
		
		//dealing with endpoint 1
		double first = parabolicDerivNew(arr[0], arr[1], arr[2], arr[0].getX());
		derivs[0] = first;
		
		//filling the middle of the array
		for (int i = 1; i < arr.length-1; i++)
		{
			derivs[i] = parabolicDerivNew(arr[i-1], arr[i], arr[i+1], arr[i].getX());
		} //close for loop
		
		//dealing with endpoint 2
		double last = parabolicDerivNew(arr[arr.length-3], arr[arr.length-2], arr[arr.length-1], 
				arr[arr.length-1].getX());
		derivs[arr.length-1] = last;
		
		return derivs;
		
	}	//close derivOfAllPoints()
	
	
	//--------------------------------------------------------------------------------//
	
	public static double threePointDerivative(Point p1, Point p2, Point p3, double x)
	{
		double denominator = (p2.getX()-p1.getX())*(p3.getX()-p1.getX())*(p2.getX()-p3.getX());
		double numeratorA = (-p1.getX()*p2.getY() + p1.getX()*p3.getY() + p2.getX()*p1.getY() - p2.getX()*p3.getY() - p3.getX()*p1.getY() + p3.getX()*p2.getY());
		double numeratorB = (p1.getX()*p1.getX()*p2.getY() - p1.getX()*p1.getX()*p3.getY() - p2.getX()*p2.getX()*p1.getY() + p2.getX()*p2.getX()*p3.getY() + p3.getX()*p3.getX()*p1.getY() - p3.getX()*p3.getX()*p2.getY());

		double a = numeratorA/denominator;
		double b = numeratorB/denominator;
		
		return 2*a*x+b;
	}

	
	
}		//close DerivativesFinal class


