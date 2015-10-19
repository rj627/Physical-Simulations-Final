
public class Derivatives 
{
	public static final double DELTA_X = 0.01;
	
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
	
	public static double midDeriv(double[] arr, int index)
	{
		return (rightDeriv(arr,index)+leftDeriv(arr,index))/2;
	}
	
	public static double actualDeriv(double x)
	{
		return -2*x*Math.pow(Math.E,-Math.pow(x, 2));
	}
	
	public static double returnA(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		double addend1 = x3*(y2-y1);
		double addend2 = x2*(y1-y3);
		double addend3 = x1*(y3-y2);
		double denom = (x1-x2)*(x1-x3)*(x2-x3);
		
		return (addend1+addend2+addend3)/denom;
	}
	
	public static double returnH(double x1, double x2, double x3, double y1, double y2, double y3)
	{
		double addend1 = Math.pow(x3,2.0)*(y1-y2);
		double addend2 = Math.pow(x1, 2)*(y2-y3);
		double addend3 = Math.pow(x2, 2)*(y3-y1);
		double denominator = 2*(x3*(y1-y2) + x1*(y2-y3) + x2*(y3-y1));
		
		return (addend1 + addend2 + addend3)/denominator;
	}
	
	public static double parabolicDeriv(double a, double h, double x)
	{
		return 2.0*a*(x-h);
	}
}
