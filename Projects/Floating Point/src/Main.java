
/*
 * Author: Rahul Jayaraman
 * Date Created: 8/26/2014
 * Date Last Modified: 8/28/2014
 * 
 * This class has one method, the main method, which prints out the value of 
 * min/max/eps to System.out. 
 */
public class Main 
{
	/**
	 * We print out the values of min, max, and eps to System.out. We use the toString()
	 * capability (we return the non-generic types, but the Object types (Float, Double) 
	 * to allow for facile representation and no need to rely on auto-(un)boxing) in order
	 * to do this. Attached to each value is a label identifying it.
	 * @param args - legacy command line
	 */
	public static void main(String[] args)
	{
		System.out.print("Float minimum: " + DetValues.detMin().toString() + "\n");
		System.out.println("Double minimum: " + DetValues.detDoubMin().toString());
		System.out.print("Float Epsilon: " + DetValues.detEps().toString());
		System.out.print("\n" + "Double Epsilon: " + DetValues.detDoubEps().toString());
		System.out.print("\n" + "Float maximum: " + DetValues.detMax().toString());
		System.out.print("\n" + "Double maximum: " + DetValues.detDoubMax().toString());
		System.out.print("\n" + "x-sqrt(x^2-1): " + Relativity.expressions().toString());
		System.out.print("\n" + "Math power stuff: " + Relativity.expressions2().toString());
	} // close main()
	
} //close Main class
