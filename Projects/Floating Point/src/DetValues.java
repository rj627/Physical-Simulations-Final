/*
 * Author: Rahul Jayaraman
 * Date of Creation: August 26, 2014
 * Date Last Modified: August 28, 2014
 * 
 * This class determines the minimum representable values for the 
 * types float and double. We ascertain the maximum value representable
 * and the minimum value as well as epsilon -- the minimum value for
 * which 1 + eps does not equal 1. Six methods are in this class -- 
 * one for each value of each type -- to achieve this end.
 */

public class DetValues 
{
	/**
	 * This method determines the minimum nonnegative nonzero value 
	 * that the type float can hold. We assign a random float value 
	 * to the variable 'min' and then keep dividing that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * lastMin. While min does not equal zero, we keep dividing by two
	 * until it does and then return lastMin.
	 * @return the minimum nonzero nonnegative value representable
	 * 		   by type float
	 */
	public static Float detMin()
	{
		float min = 0.1f;
		float lastMin = 2362.2342f;
		
		while (min != 0f)
		{
			lastMin = min;
			min/=2;
		} //close while loop
		
		return lastMin;
		
	}     //close detMin()
	
	/**
	 * This method determines the minimum nonnegative nonzero value 
	 * that the type double can hold. We assign a random double value 
	 * to the variable 'min' and then keep dividing that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * lastMin. While min does not equal zero, we keep dividing by two
	 * until it does and then return lastMin.
	 * @return the minimum nonzero nonnegative value representable
	 * 		   by type double
	 */
	public static Double detDoubMin()
	{
		double min = 0.1;
		double lastMin = 0.0;
		
		while (min != 0.0)
		{
			lastMin = min;
			min/=2;
		} //close while loop
		
		return lastMin;
		
	}     //close detDoubMin()
	
	/**
	 * This method determines the value of epsilon that the type 
	 * float uses. We assign a random float value to the variable 
	 * 'eps' and then keep dividing that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * minEps. While 1 + eps does not evaluate to zero, we keep 
	 * dividing by two until it does and then return minEps.
	 * @return the value of epsilon used by type float
	 */
	public static Float detEps()
	{
		float eps = 0.1f;
		float minEps = 23452.2342f;
		
		while (1 + eps != 1)
		{
			minEps = eps;
			eps/=2;
		} //close while loop
		
		return minEps;
		
	}     //close detEps()
	
	/**
	 * This method determines the value of epsilon that the type 
	 * double uses. We assign a random double value to the variable 
	 * 'eps' and then keep dividing that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * minEps. While 1 + eps does not evaluate to zero, we keep 
	 * dividing by two until it does and then return minEps.
	 * @return the value of epsilon used by type double
	 */
	public static Double detDoubEps()
	{
		double eps = 0.1;
		double minEps = 0.0;
		
		while (1.0 + eps != 1.0)
		{
			minEps = eps;
			eps/=2;
		} //close while loop
		
		return minEps;
		
	}     //close detDoubEps()
	
	/**
	 * This method determines the maximum nonnegative value 
	 * that the type float can hold. We assign a random float value 
	 * to the variable 'min' and then keep multiplying that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * lastMax. While max does not "equal" the value of infinity 
	 * stored as a constant in type Float, we keep multiplying by two
	 * until it does and then return lastMin.
	 * @return the max nonnegative value representable by type float
	 */
	public static Float detMax()
	{
		float max = 0.1f;
		float lastMax = 2342.2342f;
		
		while (max != Float.POSITIVE_INFINITY)
		{
			lastMax = max;
			max*=2;
		} //close while loop
		
		return lastMax;
		
	}     //close detMax()
	
	/**
	 * This method determines the maximum nonnegative value 
	 * that the type double can hold. We assign a random double value 
	 * to the variable 'min' and then keep multiplying that by 2 (because
	 * it is represented in binary) and store the previous value in
	 * lastMax. While max does not "equal" the value of infinity 
	 * stored as a constant in type Double, we keep multiplying by two
	 * until it does and then return lastMin.
	 * @return the max nonnegative value representable by type double
	 */
	public static Double detDoubMax()
	{
		double max = 0.1;
		double lastMax = 232.2342;
		
		while (max != Double.POSITIVE_INFINITY)
		{
			lastMax = max;
			max*=2;
		} //close while loop
		
		return lastMax;
		
	}     //close detDoubMax()
	
}         //close DetValues class
