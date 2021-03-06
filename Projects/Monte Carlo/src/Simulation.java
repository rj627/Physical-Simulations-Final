/*
 * Author: Rahul Jayaraman
 * Date Created: September 16, 2014
 * Date Modified: September 20, 2014
 * 
 * This class represents a simulation using a Monte Carlo (random) method to find Pi. The main
 * method prints out a characterization of the random number generator, the approximated value of 
 * pi, as well as the deviation from the Java constant Math.PI. Other methods generate a random
 * point and test to see if it's in the circle, generate arrays of random numbers, and print
 * a characterization of generated random numbers to the console.
 */
public class Simulation
{
	
	/**
	 * This method generates a random point (x, y) using Math.random() and then tests to see if
	 * it is in the quarter circle that we are using to generate the value of pi. We use double
	 * multiplication to do the test; if x*x + y*y -- the distance from the origin -- is less than
	 * 1, we say that it is <i>in</i> the circle. We observe lesser accuracy in the value of pi
	 * if we treat points on the circle as being outside the circle; therefore, for a better
	 * simulation, we treat points on the circle as being inside the circle. Furthermore, another
	 * observation we can make is that if the side length is longer, then the guesses are less
	 * accurate.
	 * @param sideLength is the side length of the square we use in the simulation
	 * @return true if the point is in the circle, false otherwise
	 */
	public static boolean generateRandomPointAndTest(int sideLength)
	{
		//generate random point
		double x = sideLength*Math.random();
		double y = sideLength*Math.random();
		
		//test if a point is on or within the circle, return result of this test
		if (x*x + y*y <= Math.pow(sideLength,2)) 
			return true;
		else 
			return false;
		
	} //close generateRandomPointAndTest()
	
	/**
	 * This method generates a lengthy array of random numbers; it is used in the method 
	 * characterizeRandomNums(). We define an array of length Integer.MAX_VALUE/1000 (we could
	 * preferably use MAX_VALUE itself, but space on the heap and VM are constraints). We populate
	 * the generated array with random numbers, and then return it to be analyzed in the next method.
	 * @return an array of random numbers <i>generated by Math.random()</i>
	 */
	private static double[] genArrayOfRandomNums()
	{
		double[] nums = new double[Integer.MAX_VALUE/1000];
		
		for (int i = 0; i < nums.length; i++)
		{
			nums[i] = Math.random();
		} //close for
		
		return nums;
		
	} //close genArrayOfRandomNums()
	
	/**
	 * This method uses the genArrayOfRandomNums() method to generate a long array and then
	 * tabulates how many of the numbers fall within a certain range (0.0 to 0.1, 0.1 to 0.2, etc.).
	 * We discovered that the numbers were roughly uniformly distributed between 0.0 and 1.0, showing
	 * that Math.random() is random enough for our purposes. 
	 * 
	 * In the method, we define 10 variables to hold the amount of numbers in each range, and then 
	 * loop through the array to figure out the desired values of these variables; we then print it 
	 * to the console.
	 * @post Number of random numbers within a certain range is printed to console
	 */
	public static void characterizeRandomNums()
	{
		//define, initialize all necessary variables
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0;
		double[] randoms = genArrayOfRandomNums();
		
		//loop through the array, testing
		for (int k = 0; k < randoms.length; k++)
		{
			if (randoms[k] >= 0.0 && randoms[k] < 0.1) a++;
			else if (randoms[k] >= 0.1 && randoms[k] < 0.2) b++;
			else if (randoms[k] >= 0.2 && randoms[k] < 0.3) c++;
			else if (randoms[k] >= 0.3 && randoms[k] < 0.4) d++;
			else if (randoms[k] >= 0.4 && randoms[k] < 0.5) e++;
			else if (randoms[k] >= 0.5 && randoms[k] < 0.6) f++;
			else if (randoms[k] >= 0.6 && randoms[k] < 0.7) g++;
			else if (randoms[k] >= 0.7 && randoms[k] < 0.8) h++;
			else if (randoms[k] >= 0.8 && randoms[k] < 0.9) i++;
			else if (randoms[k] >= 0.9 && randoms[k] < 1.0) j++;
		} //close for
		
		//print to console
		System.out.println("Amounts of random numbers: " + "\n" +
		"Between 0 and 0.1: " + a + "\n" + 
		"Between 0.1 and 0.2: " + b + "\n" + 
		"Between 0.2 and 0.3: " + c + "\n" +
		"Between 0.3 and 0.4: " + d + "\n" +
		"Between 0.4 and 0.5: " + e + "\n" +
		"Between 0.5 and 0.6: " + f + "\n" +
		"Between 0.6 and 0.7: " + g + "\n" +
		"Between 0.7 and 0.8: " + h + "\n" +
		"Between 0.8 and 0.9: " + i + "\n" +
		"Between 0.9 and 1.0: " + j);
	} //close characterizeRandomNums() method
	
	/**
	 * The main method, firstly, characterizes the random number generator using the 
	 * characterizeRandomNums() method; it then uses the generateRandomPointAndTest() method
	 * to generate a bunch of random points while in a while and for loop to determine whether
	 * the points generated are in the quarter-circle or outside the quarter-circle. There are two 
	 * counters: one for the number of hits inside the circle, and one for the total number of points 
	 * generated. The one for the number of hits inside the circle as well as the total are 
	 * incremented when a successful hit is made; only the total is incremented when a general 
	 * hit is made (not inside the circle). The ratio between the number of hits inside the circle 
	 * and the total number of hits is pi/4. We print this value times 4 to the console and then the 
	 * deviation from the value stored in Math.PI. 
	 * @param args - legacy command-line
	 */
	public static void main(String[] args)
	{
		characterizeRandomNums();
		
		//initialize counters to 0
		double in = 0;
		int throwCount = 0;
		
		//an arbitrarily large number so that the while loop will run for a long time, generating
		//numerous approximations
		int counter = 100000000;
		
		//keeps the simulation running
		while (counter > 0)
		{
			//keeps generating random points and testing, we can see that using a lot of points
			//gives a better approximation rather than using, say, 1000 points.
			for (int i = 0; i < 30000000; i++)
			{
				if (generateRandomPointAndTest(5))
				{
					in++;
					throwCount++;
				} //close if
				
				else throwCount++;
				
			} //close for
			
			//calculate pi (this is a quarter circle) and the deviation
			double pi = 4*in/throwCount;
			double deviation = pi - Math.PI;
			
			System.out.print("pi equals " + pi + "\tDeviation from \"Actual\" Value: " +
					deviation + "\n" );
			
			//reset counters
			in = 0;
			throwCount = 0;
			
			//decrement toward finishing the while loop
			counter-=1;
			
		} //close while
		
	} //close main()
	
} //close Simulation class
