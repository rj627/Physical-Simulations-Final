import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
 * Author: Rahul Jayaraman
 * Date Created: October 3, 2014
 * Date Modified: October 7, 2014
 * 
 * This class implements a least squares regression for a given function. It includes applicability
 * for a user-defined function, a linear function, and a quadratic function. It also includes 
 * methods to load data from a text file into an array of Points as well as to define an arbitrary 
 * function. In this particular case, we use the Gaussian function for our general function, as it
 * is the most likely fit for many types of frequency data that we are trying to analyze. 
 */
public class LeastSquares 
{
	/**
	 * This method generates a Point with a given x-value and a calculated y-value, given an array
	 * of coefficients. By changing the body of this method, we can evaluate different methods. If 
	 * we would like to use a polynomial, we can use a for-loop and then find the y-value, else we 
	 * simply hard-code the function into the method, as we have done in this case. 
	 * IMPORTANT: When we code the Gaussian, we include the mean in our definition of the function,
	 * as that is the commonly accepted definition (and the one we use in Logger Pro to determine
	 * a fit to a 'bell' curve). Other methods are not dependent upon this little quirk. 
	 * @param x is the location where we want to find the function's value
	 * @param coeffs is the array of coefficients of the function; depends on the function
	 * @return a Point with the input x-value and a calculated y-value based upon desired function
	 */
	public static Point myGeneralFunction(double x, double[] coeffs)
	{
		double f = coeffs[0]*Math.exp(-1*(x-coeffs[1])*(x-coeffs[1])/coeffs[2]/coeffs[2])+coeffs[3];
		Point p = new Point(x,f);
		
		return p;

		
	} //close myGeneralFunction()
	
	/**
	 * This method analyzes a file with tab-delimited data and then returns the frequency of
	 * each y-value. We assume here that the file contains two columns, separated by tabs -- one
	 * of x-value (independent variable), and one of y-value (dependent variable). We then load 
	 * each line into an ArrayList by using a BufferedReader. After this, we traverse the ArrayList
	 * using an iterator and determine where the tab character is. We then find the y-value at that
	 * point by using Integer.parseInt() and then increment the corresponding value in the array.
	 * We use an ArrayList because we do not know how many lines are in the file that we input.  
	 * IMPORTANT: In this particular method, after numerous trial and error, the returned array
	 * has size 42, because that is the range of the y-values: from 0 to 41. However, we have thrown
	 * away the last and first data (symmetry), as the Geiger data that we use records 0 when it 
	 * malfunctions or needs to be reset; the corresponding values in the array are 0. This 
	 * constant needs to be changed for different implementations of this method -- a limitation.
	 * @param fileName is the name of the file that we wish to analyze
	 * @return an array of frequencies, with size the maximum value of the y-value
	 * @throws IOException if the file cannot be read or some other I/O problem occurs
	 */
	public static int[] loadData(String fileName) throws IOException
	{
		//define necessary temporary variables
		BufferedReader b = new BufferedReader(new FileReader(fileName));
		ArrayList<String> arr = new ArrayList<String>();
		
		//start reading each line into the ArrayList, stopping at the end of the file
		String s = b.readLine();
		while (s != null)
		{
			arr.add(s);
			s = b.readLine();
		} //close while loop
		
		//define array
		int[] freqs = new int[42];
		
		//traverse the ArrayList, isolating delimited y-values and incrementing values in the array
		Iterator it = arr.iterator();
		while (it.hasNext())
		{
			String str = (String) it.next();
			
			int tabLoc = str.indexOf("\t");
			int val = Integer.parseInt(str.substring(tabLoc+1));
			
			freqs[val]++;
		} //close while loop
		
		//prevent resource leak
		b.close();
		
		//throw out first and last data
		freqs[0] = 0;
		freqs[freqs.length-1] = 0;
		
		return freqs;
		
	} //close loadData()
	
	/**
	 * This method returns an array of linear coefficients for the linear approximation to the 
	 * data. We derive the formula for such coefficients from the formula for least-squares 
	 * regression. First, we define variables for the sum of all the x, y, xy, and x^2 terms. 
	 * We sum up all these terms across all the points in the array and then use the previously
	 * derived formulas to determine the slope. The y-intercept is determined by subtracting the 
	 * slope*average x from the average value of y. Then, we return an array with elements in 
	 * order of the coefficients (i.e. first coefficient maps to the first term, ax; the second
	 * coefficient maps to the second term, b). The user can do what they would like to with the 
	 * values returned in the array. We could also return a Point, but this is not extensible to
	 * functions of higher order, where we would need three or more coordinates. 
	 * @param arr is the array of data points that we want to find a fit to
	 * @return an array with a[0] the coefficient of x, a[1] the constant term (y-intercept)
	 */
	public static double[] linearReg(Point[] arr)
	{
		//define necessary quantities
		double sumXY = 0;
		double sumX = 0;
		double sumY = 0;
		double sumXX = 0;
		
		//sum up all the quantities over each value
		for (int i = 0; i < arr.length; i++)
		{
			double x = arr[i].getX();
			double y = arr[i].getY();
			
			sumXY+=(x*y);
			sumX+=x;
			sumY+=y;
			sumXX+=Math.pow(x,2);
		} //close for loop
		
		//calculate slope (a in ax + b)
		double slope = (sumXY - sumX*sumY/arr.length)/(sumXX - Math.pow(sumX, 2)/arr.length);
		
		//calculate y-intercept (b in ax + b)
		double xAvg = sumX/arr.length;
		double yAvg = sumY/arr.length;
		double yInt = yAvg - slope*xAvg;
		
		//define array and return
		double[] arr1 = {slope, yInt};
		return arr1;
	} //close linearCoeffs;
	
	/**
	 * This method determines a least-squares fit to a quadratic function. We use a formula derived
	 * from the error formula and partial derivatives to perform this task. First, we define the 
	 * necessary quantitites and then traverse the array of data to get what we want, i.e. the sums
	 * of x^2, x*y, x^3, x*x*y, y, x, and x^4. We then simplify calculations by calculating certain
	 * quantities that appear in the numerators and denominators of the derived expressions for a,
	 * b, and c. Finally, we do all the hard work of obtaining the true values of a, b, and c by 
	 * separately calculating the numerator and denominator and then dividing those. We return an
	 * array of coefficients in order, i.e. {a, b, c} (a is for x^2, b is for x, and c is constant).
	 * These formulas were verified by reading the Arizona Department of Health Services analysis of
	 * quadratic regression, as well as my own further Mathematica calculations.
	 * IMPORTANT: In the implementation that we use for the Geiger counter data, we must add some
	 * x-values to the frequencies that we obtain in order to match the parameters for this method.
	 * This changing of the array is done in the main() method instead of here for wider usability. 
	 * @param data is the data that we wish to fit to a parabola
	 * @return an array {a, b, c} that contains the coefficients of the parabolic fit
	 */
	public static double[] quadReg(Point[] data)
	{
		//define initial quantities, set to 0
		double sumXX = 0;
		double sumXY = 0;
		double sumXXX = 0;
		double sumXXY = 0;
		double sumY = 0;
		double sumX = 0;
		double sumXXXX = 0;
		
		//calculate final values of initial quantities
		for (Point p : data)
		{
			sumXX+=Math.pow(p.getX(), 2.);
			sumXY+=p.getX()*p.getY();
			sumXXX+=Math.pow(p.getX(), 3.);
			sumXXY+=Math.pow(p.getX(), 2.)*p.getY();
			sumY+=p.getY();
			sumX+=p.getX();
			sumXXXX+=Math.pow(p.getX(), 4.);
		} //close for loop
		
		//create simplifying variables to determine a, b, and c
		double SXX = sumXX - Math.pow(sumX, 2)/data.length;
		double SXY = sumXY - sumX*sumY/data.length;
		double SXXX = sumXXX - sumX*sumXX/data.length;
		double SXXY = sumXXY - sumXX*sumY/data.length;
		double SXXXX = sumXXXX - Math.pow(sumXX, 2)/data.length;
		
		//determine value of a
		double aNum = SXXY*SXX - SXY*SXXX;
		double aDenom = SXX*SXXXX - Math.pow(SXXX, 2.);
		double a = aNum/aDenom;
		
		//determine value of b
		double bNum = SXY*SXXXX - SXXY*SXXX;
		double bDenom = SXX*SXXXX - Math.pow(SXXX, 2.);
		double b = bNum/bDenom;
		
		//determine value of c using the already-determined a and b to simplify calculations
		double c = sumY/data.length - b*sumX/data.length - a*sumXX/data.length;
		
		//define an array and return
		double[] finalCoeffs = {a, b, c};
		return finalCoeffs;
		
	} //close quadReg()
	
	/**
	 * This method returns the error value for a set of points and the a curve that we think fits
	 * to the data. We establish a temporary error variable and then find the difference between
	 * the actual y-value of the data and the predicted y-value of the model. We then square this
	 * value (to eliminate negative signs) and accumulate these values along the array of data, 
	 * returning the final value of error. 
	 * @param data is the set of points that we would like to check against the actual values
	 * @param coeffs is the set of coefficients of the general function; we are trying to find
	 * 		  the difference between a value generated by using this and the data point
	 * @return a sum of the squares of the errors (to avoid negative sign issues).
	 */
	private static double calcErrFunct(Point[] data, double[] coeffs)
	{
		double err = 0.;
		
		//traverse the data array, accumulating error
		for (int i = 0; i < data.length; i++)
		{
			double actualValue = myGeneralFunction(data[i].getX(), coeffs).getY();
			double dataValue = data[i].getY();
			
			double diff = actualValue - dataValue;
			
			err+=Math.pow(diff, 2.);
		} //close for loop
		
		return err;
	} //close calcErrFunct()
	
	/**
	 * This method returns a general regression formula for a given function (i.e. the function in
	 * myGeneralFunction). We use the method of steepest descent (gradient descent) in order to
	 * find the minimum value of the error. Implementing this algorithm involves calculating the
	 * error and progressing down the gradient incline accordingly. We first define values for 
	 * the step and lambda (learning factor) as well as the number of coefficients, a counter for
	 * the while loop, and three arrays that are initially the same as the coefficients but that
	 * change in the while loop to further satisfy our conditions. After much experimentation, I
	 * decided that a step of 0.001 and lambda of .1 are the best bets, as they provide values
	 * quite close to the actual one. In the while loop, we iterate a million times (we could do 
	 * more, but due to big-O constraints we must settle), stepping down each element of the 'left'
	 * array and stepping up each element of the 'right' array. We then find the derivative using
	 * three points: the corresponding points in each array (right, initGuesses, and left). The
	 * nextGuess element is then decreased by lambda times the derivative. This procedure is rep-
	 * eated for each element in the array during each pass of the while loop. We calculate the
	 * error of the nextGuess array as well as the currentGuess array and find their difference; 
	 * if it is nonzero, i.e. nextError > currentError, then we shift in the opposite direction 
	 * (negative gradient). If the two are machine-precision equal, we call it quits. Otherwise,
	 * the loop repeats -- and the new initialGuesses array is now the nextGuess array, with the
	 * updated values of coefficients. The loop trudges on. Finally, we return initialGuesses.
	 * @param data is the set of points that we wish to fit to a given curve
	 * @return an array of points that map to the coefficients of the curve equation
	 */
	public static double[] genReg(Point[] data, double[] initialGuesses)
	{	
		//define factors for descent
		double step = 0.0001;
		double lambda = .1;
		
		//define arrays to hold updated and nearby values
		double[] nextGuess = initialGuesses;
		double[] left = initialGuesses;
		double[] right = initialGuesses;
		
		//simplifies coding
		int numOfCoeffs = initialGuesses.length;
		
		//loop invariants
		boolean terminate = false;
		int counter = 0;
		
		while (counter < 1000000 && terminate == false)
		{
			//updates array
			initialGuesses = Arrays.copyOf(nextGuess, numOfCoeffs);
			
			//calculates 3-point derivative, shifts the coefficient in the nextGuess array
			for (int i = 0; i < numOfCoeffs; i++)
			{
				left = Arrays.copyOf(initialGuesses, numOfCoeffs);
				left[i] -= step;
				
				right = Arrays.copyOf(initialGuesses, numOfCoeffs);
				right[i] += step;
				
				Point leftPt = new Point(left[i], calcErrFunct(data, left));
				Point currentPt = new Point(initialGuesses[i], calcErrFunct(data, initialGuesses));
				Point rightPt = new Point(right[i], calcErrFunct(data, right));
				
				nextGuess[i] -= lambda*DerivativesUpdated.parabolicDerivNew(leftPt, currentPt, 
						rightPt, currentPt.getX());
			} //close for loop
			
			//calculates error
			double currentError = calcErrFunct(data, initialGuesses);
			double nextError = calcErrFunct(data, nextGuess);
			
			//if new error is greater, we dial down lambda
			if (currentError < nextError)
			{
				nextGuess = Arrays.copyOf(initialGuesses, numOfCoeffs);
				lambda/=2;
			} //close if
			
			//if the errors are approximately the same, we're done
			if (currentError == nextError)
			{
				terminate = true;
			} //close if
			
			counter++;
		} //close while
		
		return initialGuesses;
		
	} //close genReg()
	
} //close LeastSquares class
