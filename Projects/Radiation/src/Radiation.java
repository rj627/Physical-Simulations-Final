/*
 * Author: Rahul Jayaraman
 * Date Created: 11/2/2014
 * Date Modified: 11/5/2014
 * 
 * This class simulates a radioactive decay model. We define four methods: One for the direct 
 * calculation of the amount of material remaining after a certain period of time (radiation()), 
 * one for the direct calculation of the closed-form derivative of the exponential radioactive 
 * decay model (radiationModelDerivValue()), one for the Euler's method calculation of the amount
 * remaining after a specific time (euler), and one for the RK4 (Runge-Kutta) calculation of the
 * amount remaining after a specific time (rungeKutta). We also have a global constant, EULER_STEP,
 * which defines how big of a step we take after looking for the next tangent line's slope. With
 * slight modifications to the parameters (and, of course, the method body), the RK4 method and 
 * the Euler method can be used in other programs as well. 
 */
public class Radiation 
{
	//global constant for the euler step size
	public static final double EULER_STEP = .00001;
	
	/**
	 * This method defines an exponential decay model for radiation. We use the model that states
	 * N[t] = No*e^(-t/tau), where tau is a constant defined as the half-life over ln(2), e is the
	 * natural logarithm base, and No is the starting amount. We calculate -t/tau and then e raised
	 * to that power and then multiply it by the initial amount for our final result.
	 * @param initAmt is the amount of material we start with, i.e. No
	 * @param time is the time at which we would like to determine the material remaining
	 * @param halfLife is the half-life of the desired material
	 * @return the amount remaining at the specified time (parameter)
	 * @throws IllegalArgumentException if the time is negative. It will give a valid result, but
	 * negative time is meaningless in the context of this program and therefore useless.
	 */
	public static double radiation(double initAmt, double time, double halfLife)
	{
		if (time < 0) 
			throw new IllegalArgumentException("Time cannot be negative.");
		
		double timeConstant = halfLife/Math.log(2.0); //calculate tau
		
		double decay = Math.exp(-time/timeConstant); //calculate the exponent part
		
		return initAmt*decay; 
		
	} //close radiation()
	
	/**
	 * This method represents the closed-form derivative of the radioactive decay model presented
	 * in the previous method. This derivative is with respect to time, and takes on the expression
	 * N'[t] = -No/tau * e^(-t/tau), where tau is the half-life over ln(2), e is the natural 
	 * logarithm base, and No is the starting amount. Here, we calculate the value of tau, then
	 * the constant at the front, then the exponent, and then multiply these values for the final 
	 * result, the rate at which the amount of material is changing at a desired time.
	 * @param initAmt is the amount of material we start with, i.e. No
	 * @param time is when we would like to determine the rate of change of material amount
	 * @param halfLife is the half-life of the material being investigated
	 * @return the amount remaining at the specified time (parameter)
	 * @throws IllegalArgumentException if the time is negative. It will give a valid result, but
	 * negative time is meaningless in the context of this program and therefore useless.
	 */
	public static double radiationModelDerivValue(double initAmt, double time, double halfLife)
	{
		if (time < 0) 
			throw new IllegalArgumentException("Time cannot be negative.");
		
		//calculate the right side of the expression (e to the power of -t/tau)
		double timeConstant = halfLife/Math.log(2.0);
		double decay = Math.exp(-time/timeConstant);
		
		//calculate the left side of the expression (-No/tau)
		double constant = -initAmt/timeConstant;
		
		return decay*constant;
		
	} //close radiationModelDerivValue()
	
	/**
	 * This method implements an Euler's method algorithm to determine the amount of radioactive
	 * material remaining after a set time. Euler's method uses successive tangent line 
	 * approximations to a function to get a final y-value (the points at which we take the 
	 * tangent lines are separated by an amount of EULER_STEP). We have two variables, currTime 
	 * and currAmt, which change throughout the while loop as we keep stepping forward on the 
	 * x-axis. In the loop, we increment the currTime by EULER_STEP and the currAmt by 
	 * EULER_STEP*the derivative (the slope at the point), which represents a change in the 
	 * y-value (EULER_STEP represents a change in the x-value). Once the value of currTime reaches
	 * endTime, we return the value stored in currAmt. 
	 * --------------------------------------------------------------------------------------
	 * A key point to note is that we need to use initAmt in the while loop as the argument of 
	 * the derivative function rather than currAmt, as the rate of change depends on the time 
	 * elapsed from the original amount, not the immediate decay rate of a "new" original amount. 
	 * We also observe that decreasing the value of EULER_STEP causes the program to have a longer 
	 * run-time, which may not be efficacious for programs and simulations that use repeated 
	 * iterations of Euler's method.
	 * --------------------------------------------------------------------------------------
	 * @param initAmt is the amount we start with; specifically, No in the decay formula
	 * @param halfLife is the half-life of the radioactive element that we are studying
	 * @param endTime is the time at which we wish to determine the amount of material remaining
	 * @return the amount of radioactive material remaining at endTime
	 * @throws IllegalArgumentException if the time is negative. It will give a valid result, but
	 * negative time is meaningless in the context of this program and therefore useless.
	 */
	public static double euler(double initAmt, double halfLife, double endTime) 
	{ 
		if (endTime < 0.0) 
			throw new IllegalArgumentException("Time cannot be negative.");
		
		//define variables changed in the while loop
		double currTime = 0.0;
		double currAmt = initAmt;
		
		while (currTime < endTime)
		{
			currTime+=EULER_STEP; //change x-val
			currAmt+= EULER_STEP*radiationModelDerivValue(initAmt, currTime, halfLife); //change y-val
		} //close while
		
		return currAmt;
		
	} //close euler()
	
	/**
	 * This method implements a fourth-order Runge-Kutta algorithm, which uses four points in each
	 * interval in order to construct a better approximation to the original function, as tangent
	 * line approximations are prone to much error. We start as we did the Euler's method, by 
	 * defining variables currTime and currAmt; however, in the while loop, we calculate four
	 * values. After calculating these values (formulas obtained from the Princeton Intro CS course
	 * website), we increment currTime as well as currAmt to reflect the updated values and 
	 * progress further to termination of the while loop so that we can return a value.
	 * -----------------------------------------------------------------------------------
	 * These intermediate values are weighted and step based upon the previously determined value (thus 
	 * making RK4 an adaptive algorithm). Using these weighted numbers, we then calculate a 
	 * weighted average that is the location of the next point on our approximation. This, far
	 * from being a simple tangent-line approximation, is a much more powerful tool than Euler's
	 * method for calculations that require a high degree of accuracy (precision, not so much -- as
	 * it depends on the machine). We also observe that this algorithm is slightly slower than 
	 * Euler's algorithm, as it takes up more space and needs to perform more calculations, but 
	 * the end result is much closer to the theoretical ones when compared to results from Euler's. 
	 * -----------------------------------------------------------------------------------
	 * @param initAmt is the amount of radioactive material that we start with
	 * @param halfLife is the half-life of the radioactive material that we investigate
	 * @param endTime is the time at which we would like to find how much material remains
	 * @return a (very good) approximation to the true value of the amount of material remaining
	 * @throws IllegalArgumentException if the time is negative. It will give a valid result, but
	 * negative time is meaningless in the context of this program and therefore useless.
	 */
	public static double rungeKutta(double initAmt, double halfLife, double endTime) 
	{ 
		if (endTime < 0.0) 
			throw new IllegalArgumentException("Time cannot be negative.");
		
		//define variables changed in the while loop
		double currTime = 0.0;
		double currAmt = initAmt;
		
		while (currTime < endTime)
		{
			//calculate all the necessary values to see where currAmt should go next
			double k1 = EULER_STEP*radiationModelDerivValue(initAmt, currTime, halfLife);
			double k2 = 2*EULER_STEP*radiationModelDerivValue(initAmt + k1*.5, currTime 
					+ EULER_STEP*.5, halfLife);
			double k3 = 2*EULER_STEP*radiationModelDerivValue(initAmt + k2*.5, currTime
					+ EULER_STEP*.5, halfLife);
			double k4 = EULER_STEP*radiationModelDerivValue(initAmt + k3, currTime
					+ EULER_STEP, halfLife);
			
			//update y- and x-values, respectively (avoiding mixed-mode arithmetic!)
			currAmt+=(k1+k2+k3+k4)/6.0;
			currTime+=EULER_STEP;
		} //close while loop
		
		return currAmt;
		
	} //close rungeKutta()
	
} //close Radiation class
