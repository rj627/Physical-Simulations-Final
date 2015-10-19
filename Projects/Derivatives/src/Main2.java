import java.util.Arrays;

/*
 * Author: Rahul Jayaraman
 * Date Created: 9/10/2014
 * Date Last Modified: 9/11/2014
 * 
 * This class is the driver class for the program to find an approximate derivative
 * at an array of points. We print out all the points, the estimate, and the actual
 * derivative -- on separate lines, in that order. 
 */
public class Main2 
{
	/**
	 * This method is the tester method for the approximation. We print out the array of
	 * points, the array of the estimates, the array of the actual values, and an array of
	 * how much the two values vary. We can see that the %-error gets larger as we go 
	 * further from zero due to the difficulty in constructing a parabola there.
	 * @param args - legacy command-line
	 */
	public static void main(String[] args)
	{
		//get the arrays with all the values
		Point[] points = ThreePointDeriv.arrFunction();
		double[] estimates = ThreePointDeriv.derivOfAllPoints(points);
		double[] actual = ThreePointDeriv.actualDerivGaussian(points);
		
		//ensures that they are same length
		if (estimates.length != actual.length) 
			System.out.println("The two arrays are not of the same length.");
		
		//prints all three arrays to console
		//System.out.print(Arrays.toString(points) + "\n");
		System.out.print(Arrays.toString(estimates) + "\n");
		System.out.print(Arrays.toString(actual) + "\n");
		
		//creates an array with all the error values and fills it
		double[] error = new double[estimates.length];
		
		for (int i = 0; i < estimates.length; i++)
		{
			error[i] = estimates[i] - actual[i];
		}
		
		//creates an array with percent error and fills it
		double[] percentError = new double[error.length];
		
		for (int i = 0; i < error.length; i++)
		{
			percentError[i] = 100*error[i]/actual[i];
		}
		
		//prints the error arrays to console
		System.out.print(Arrays.toString(error) + "\n");
		System.out.print(Arrays.toString(percentError));
	}
	
}

//Math.pow(Math.E, -Math.pow(low, 2))
//-2*arr[i].getX()*Math.pow(Math.E, -Math.pow(low, 2))
