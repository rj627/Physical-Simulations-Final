
public class Vector 
{
	private double x, y, z;
	
	public Vector(double xVal, double yVal, double zVal) { x = xVal; y = yVal; z = zVal;}
	
	public double getX() {return x;}
	public double getY() {return y;}
	public double getZ() {return z;}
	
	public double magnitude()
	{
		return Math.sqrt(Math.pow(Math.abs(x),2.0) + Math.pow(Math.abs(y), 2.0) + Math.pow(Math.abs(z), 2.0));
	}
	
	public Vector multiply(double factor)
	{
		return new Vector(factor*x, factor*y, factor*z);
	}
	
	public Vector add(Vector v2)
	{
		return new Vector(x + v2.getX(), y + v2.getY(), z + v2.getZ());
	}
	
	public Vector subtract(Vector v2)
	{
		return new Vector(x - v2.getX(), y - v2.getY(), z - v2.getZ());
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Vector)) 
			return false;
		
		Vector v = (Vector) other;
		if (x - v.getX() <= 0.001 && y - v.getY() <= 0.001 && z - v.getZ() <= 0.001) 
			return true;
		
		return false;
	}
	
	public String toString()
	{
		return   x + ",  " + y ;
	}
}
