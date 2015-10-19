import java.util.*;

public class Simulation 
{	
	public static double TIME_STEP = .0001;
	public static double RADIUS_OF_BALL = .105; //kg
	public static double MASS_OF_BALL = 5.5; //m
	public static double SPHERE_DRAG = 0.45; //dimensionless
	public static double VALUE_OF_G = 9.81; //m per s^2
	
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
	
	public static double euler(double[] coeffs, double startX, double startY, double endX) 
	{ 
		double currX = startX;
		double currY = startY;
		
		while (currX < endX-TIME_STEP)
		{
			currY+=TIME_STEP*value(coeffs, currX).getY();
			//System.out.println(currY);
			currX+=TIME_STEP;
		}
		
		return currY;
	}

//---------------------------------------------------------------------------------------------//
	
	//mass of cannonball is 5.5 kg, so we use this (WIRED), modify euler's
	public static double[] noDragVel(double startVel) //init pos 0,0; vel is varied
	{ 
		double[] coeffs = {-VALUE_OF_G};
		
		double swag = startVel*Math.sqrt(2)/2;
		double[] coeffs2 = {-4.9, swag, 0};
		
		double otherTime = NewtonRaphson.findRoot(coeffs2, 10, 10000);
		
		double lul = euler(coeffs, 0, swag, otherTime);
		//double[] swiggity = {otherTime, lul, swag*otherTime};
		double[] swiggity = {lul};
		return swiggity;
	}

//---------------------------------------------------------------------------------------------//
	
	public static Point[] constantDrag(Point startVel) 
	{ 
		Point acc = constantDragAcc(1.225, startVel);
		
		double accX = acc.getX();
		double accY = VALUE_OF_G + acc.getY();
		
		double velX = startVel.getX();
		double velY = startVel.getY();
		
		double posX = 0;
		double posY = 0.001;
		
		//double otherTime = NewtonRaphson.findRoot(coeffsY, 10, 10000);
		
		while (velY > 0)
		{
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = constantDragAcc(1.225, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G - acc.getY();
		}
		
		accY = accY - VALUE_OF_G;
		
		while (posY > 0.0003)
		{
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = constantDragAcc(1.225, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G + acc.getY();
		}
		
		Point s = new Point(velX, velY);
		Point m = new Point(posX, posY);
		Point ac = new Point(accX, accY);
		
		//double posX = euler(velX)
		
		Point[] lel = {ac, s, m} ;
		return lel;
	}
	
	private static Point constantDragAcc(double rho, Point vel)
	{
		double dragX = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*rho*Math.pow(vel.getX(), 2);
		double dragY = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*rho*Math.pow(vel.getY(), 2);
		
		double accX = dragX/MASS_OF_BALL;
		double accY = dragY/MASS_OF_BALL;
		
		return new Point(accX, accY);
	}
	
//---------------------------------------------------------------------------------------------//
	
	public static Point[] adiabatic(Point startVel) 
	{ 
		double temp = 288.15;
		double posY = 0.001;
		Point acc = adiabaticAcc(posY, temp, startVel);
		
		double accX = acc.getX();
		double accY = VALUE_OF_G + acc.getY();
		
		double velX = startVel.getX();
		double velY = startVel.getY();
		
		double posX = 0;
		
		//double otherTime = NewtonRaphson.findRoot(coeffsY, 10, 10000);
		
		while (velY > 0)
		{
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = adiabaticAcc(posY, temp, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G - acc.getY();
		}
		
		accY = accY - VALUE_OF_G;
		
		while (posY > 0.0003)
		{
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = adiabaticAcc(posY, temp, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G + acc.getY();
		}
		
		Point s = new Point(velX, velY);
		Point m = new Point(posX, posY);
		Point ac = new Point(accX, accY);
		
		//double posX = euler(velX)
		
		Point[] lel = {ac, s, m} ;
		return lel;
	}
	
	private static Point adiabaticAcc(double height, double initTemp, Point vel)
	{
		double dragX = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*
				adiabaticDensity(height, initTemp)*Math.pow(vel.getX(), 2.0);
		
		double dragY = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*
				adiabaticDensity(height, initTemp)*Math.pow(vel.getY(), 2.0);
		
		return new Point(dragX/MASS_OF_BALL, dragY/MASS_OF_BALL);
	}
	
	private static double adiabaticDensity(double height, double initTemp)
	{
		double factor = 1 - 6.5E-3*height/initTemp;
		double s = Math.pow(factor, 2.5);
		return 1.225*s;
	}

//---------------------------------------------------------------------------------------------//
	
	public static Point[] isothermal(Point startVel) 
	{ 
		double posYStart = 0.0001;
		double rhoCurr = 1.225;
		Point acc = isothermalAcc(rhoCurr, posYStart, posYStart, startVel);
		
		double accX = acc.getX();
		double accY = VALUE_OF_G + acc.getY();
		
		double velX = startVel.getX();
		double velY = startVel.getY();
		
		double posX = 0;
		double posY = 0.01;
		
		//double otherTime = NewtonRaphson.findRoot(coeffsY, 10, 10000);
		
		while (velY > 0)
		{
			double posYPrev = posY;
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = isothermalAcc(rhoCurr, posYPrev, posY, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G - acc.getY();
		}
		
		accY = accY - VALUE_OF_G;
		
		while (posY > 0.0003)
		{
			double posYPrev = posY;
			posX = posX + velX*TIME_STEP;
			posY = posY + velY*TIME_STEP;
			
			velX = velX - accX*TIME_STEP;
			velY = velY + accY*TIME_STEP;
			
			Point swag = new Point(velX, velY);
			acc = isothermalAcc(rhoCurr, posYPrev, posY, swag);
			
			accX = -acc.getX();
			accY = -VALUE_OF_G + acc.getY();
		}
		
		Point s = new Point(velX, velY);
		Point m = new Point(posX, posY);
		Point ac = new Point(accX, accY);
		
		//double posX = euler(velX)
		
		Point[] lel = {ac, s, m} ;
		return lel;
	}
	
	private static double isothermalDensity(double rhoCurr, double initHeight, double currHeight)
	{
		double s = Math.exp(-(currHeight/initHeight));
		return rhoCurr*s;
	}
	
	private static Point isothermalAcc(double rhoCurr, double initHeight, double currHeight, 
			Point vel)
	{
		double dragX = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*
				isothermalDensity(rhoCurr, initHeight, currHeight)*Math.pow(vel.getX(), 2.0);
		
		double dragY = .5*SPHERE_DRAG*4*Math.PI*Math.pow(RADIUS_OF_BALL, 2.0)*
				isothermalDensity(rhoCurr, initHeight, currHeight)*Math.pow(vel.getY(), 2.0);
		
		return new Point(dragX/MASS_OF_BALL, dragY/MASS_OF_BALL);
	}
}
