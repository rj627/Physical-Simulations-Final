/*
 * Author: Rahul Jayaraman
 * Date Created: 9/22/2014
 * Date Modified: 9/25/2014
 * 
 * This class implements the Newton-Raphson method for approximating roots to a function. This
 * algorithm is specifically optimized for polynomials, but if we change the value() function and
 * the parameters in the other functions, we can diversify this function for e^x, sin(x), etc. 
 * We use four methods -- one that generates the value of a point given an array of coefficients
 * (polynomial) and a x-value, one that generates three points in the neighborhood (using the 
 * defined constant SPACING, we get x - SPACING and x + SPACING as well as x, these three points 
 * help us use the three-point derivative function), one that finds the next x-value to test, and
 * one that pulls it all together and implements the method with a given number of iterations. 
 */

public class NewtonRaphson 
{
	//constant used in the arrayOfVals() method to determine the surrounding point values
	public static final double SPACING = 0.001;
	
	/**
	 * This method returns a Point at the desired x-coordinate after taking in an array of
	 * coefficients to represent a polynomial. We use a for-loop construct to calculate the
	 * "exact" y-value and package up the two values neatly into a Point object.
	 * @param coeffs is an array with all the polynomial coefficients of our desired function. 
	 * We could also use a Taylor expansion of transcendental functions, if we know the coefficients,
	 * to approximate roots of functions such as sin(x) and log(x). 
	 * @param x is the x-value of the point that we would like to determine the value of
	 * @return a point with x-coordinate x and y-coordinate depending on the coefficients
	 */
	public static Point value(double[] coeffs, double x )
	{
		//initialize variables
		double a = 0.0;
		
		//determine the value using Math.pow() and the loop index (for the exponent)
		for (int i = 0; i < coeffs.length; i++)
		{
			a += coeffs[i]*Math.pow(x,coeffs.length-i-1);
		} //close for
		
		return new Point(x, a);
		
	} //close value()
	
	/**
	 * This method generates an array of three values surrounding the value which we start at. 
	 * If we do this, we can then use the three-point derivative function in order to calculate
	 * the derivative at this point to start off the Newton-Raphson method, which is implemented
	 * in the findRoot method. The three points are spaced based upon a constant defined at the
	 * start of this class, SPACING -- which is usually on the order of 10^-4. Of course, if the 
	 * constant is way too small, then the method fails and returns NaN. 
	 * @param coeffs is the list of coefficients for the polynomial function
	 * @param x is the starting location of our search for the root
	 * @return three Points in an array, spaced from each other by SPACING
	 */
	public static Point[] arrayOfVals(double[] coeffs, double x)
	{
		//set up the other points
		double before = x-SPACING;
		double after = x+SPACING;
		
		//generate array
		Point[] vals = {value(coeffs, before), value(coeffs, x), value(coeffs, after)};
		
		return vals;
	} //close arrayOfVals();
	
	/**
	 * This method is the meat of our algorithm; it is the one that determine the derivative
	 * at the point as well as the next x-value that we test. We generate the current y-value
	 * as well as an array of neighboring points. Then, we use the formula to find the next point
	 * to test (generate the derivative at the point, keep going). We do this for a number of
	 * iterations in our findRoot method. We have used the DerivativesFinal class, which has
	 * the method for the three-point derivative. As long as the two files (this and the 
	 * DerivativesFinal class) are in the same package, this algorithm will work. 
	 * @param coeffs is the array of coefficients for the polynomial
	 * @param x is the location where we start (we will find the closest root)
	 * @return the next x-value to start the process again
	 */
	public static double nextX(double[] coeffs, double x)
	{
		//defining relevant values
		double currY = value(coeffs, x).getY();
		Point[] arr = arrayOfVals(coeffs, x);
		
		//define the distance to next point (f'(x)/f(x))
		double valueOverDeriv= currY/DerivativesFinal.parabolicDeriv(arr[0], arr[1], arr[2], x);
		
		return x - valueOverDeriv;
	} //close nextX()
	
	/**
	 * In this method, we finally find the root of the function by constantly repeating the nextX
	 * function for a given number of iterations, stored as a constant 30. Experiments have shown 
	 * that usually 100 iterations are enough to cover almost all the cases. However, certain 
	 * special cases take a longer time to do -- for instance, if the point we pick is close
	 * to a relative maximum (where the tangent line slope is close to zero). If we put in a 
	 * function with complex roots, we will get a NaN error; we may also get a value that is way
	 * too large to be even a feasible root of the function (on the order of millions for a 
	 * function with coefficients that are less than 10). Otherwise, we may keep oscillating between
	 * a bunch of values that varies each time, due to the fact that we cannot get a firm "lock"
	 * on where currY from the nextX() method approximately equals 0.
	 * @param coeffs is the set of coefficients for the polynomial function we use
	 * @param x is the location where we want to start finding the root (initial "approximation"?)
	 * @param iterations is the amount of iterations that we want to keep doing the nextX() method
	 * 		  (user-defined), this allows us to vary how close the value is to the exact value 
	 * 		  (lower iterations means we allow error, higher iterations means error is minimized)
	 * @return the root of the function, approximated (it can be NaN or unreasonably large if the
	 * 		   function has only complex roots)
	 */
	public static double findRoot(double[] coeffs, double x, int iterations)
	{
		//define a variable to store the value in the loop
		double x1 = 0.0;
		
		for (int i = 0; i < iterations; i++)
		{
			//keep on feeding in the newly found x-value
			x1 = nextX(coeffs, x);
			x = x1;
		} //close for
		
		return x;
	} //close findRoot()
	
} //close NewtonRaphson class
