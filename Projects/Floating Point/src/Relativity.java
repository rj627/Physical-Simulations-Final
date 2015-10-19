
public class Relativity 
{
	public static final double SPEED_OF_LIGHT = 3.0E8;
	
	public double beta(int v)
	{
		return Math.pow(v, 2)/Math.pow(SPEED_OF_LIGHT, 2);
	}
	
	public double gamma(double beta)
	{
		return 1/Math.sqrt(1-Math.pow(beta,2));
	}
	
	public double gammaApprox(double beta)
	{
		return 1 + 0.5*Math.pow(beta,2);
	}
	
	public double approx()
	{
		return 0.0;
	}
	
	public static Float expressions()
	{
		float one = 0.1f;
		float lastOne = 2352.2342f;
		
		while (one - Math.sqrt(Math.pow(one, 2)-1) !=0 )
		{
			one*=2;
			lastOne = one;
		}
		
		return lastOne;
	}
	
	public static Float expressions2()
	{
		float x = 0.1f;
		float lastX = 235.2342f;
		
		while (x*x - Math.pow(x, 2) == 0)
		{
			lastX=x;
			x/=2;
		}
		
		return lastX;
	}
}
