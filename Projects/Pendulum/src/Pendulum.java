
public class Pendulum 
{
	public static final double EULER_STEP = 0.1;
	public static final double BIG_OMEGA = 1.0;
	public static final double SMALL_OMEGA = 0.5;
	public static final double INIT_THETA = Math.PI/4;
	public static final double C = 0.45;
	public static final double RADIUS = 0.5;
	public static final double DENSITY = 1.125;
	public static final double MASS = 1.25;
	public static final double FORCE = 5;
	public static final double DRIVING_FREQ = .5;
	
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
	public static Point[] euler(double endTime) 
	{ 
		if (endTime < 0.0) 
			throw new IllegalArgumentException("Time cannot be negative.");

		double currTheta = INIT_THETA;
		double currAngVel = SMALL_OMEGA;
		Point[] arr = new Point[(int) (endTime/EULER_STEP)+1];
		
		for (int i = 0; i < arr.length; i++)
		{
			double drag = (.5*C*Math.PI*Math.pow(RADIUS, 2.0)*DENSITY/MASS);
			
			double omegaDeriv = -Math.pow(BIG_OMEGA, 2.0)*Math.sin(currTheta)-
					Math.signum(currAngVel*9.81*Math.sin(currTheta))-drag*Math.pow(currAngVel,2.0);
					
			
			//currAngVel-=Math.pow(BIG_OMEGA, 2)*Math.sin(currTheta)*EULER_STEP;
			currAngVel+=omegaDeriv*EULER_STEP;
			currTheta+=currAngVel*EULER_STEP;
			arr[i] = new Point(currTheta, currAngVel);
		}
		
		return arr;
		
	} //close euler()
	
	public static Point[] forcingFunct(double endTime) 
	{ 
		if (endTime < 0.0) 
			throw new IllegalArgumentException("Time cannot be negative.");

		double currTheta = INIT_THETA;
		double currAngVel = SMALL_OMEGA;
		Point[] arr = new Point[(int) (endTime/EULER_STEP)+1];
		
		for (int i = 0; i < arr.length; i++)
		{
			double drag = (.5*C*Math.PI*Math.pow(RADIUS, 2.0)*DENSITY/MASS);
			
			double omegaDeriv = -Math.pow(BIG_OMEGA, 2.0)*Math.sin(currTheta)-
					drag*Math.signum(currAngVel)*currAngVel+FORCE*Math.sin(DRIVING_FREQ*i*EULER_STEP);
					
			
			//currAngVel-=Math.pow(BIG_OMEGA, 2)*Math.sin(currTheta)*EULER_STEP;
			currAngVel-=omegaDeriv*EULER_STEP;
			currTheta+=currAngVel*EULER_STEP;
			
			arr[i] = new Point(currTheta, currAngVel);
		}
		
		return arr;
		
	} //close euler()
}
