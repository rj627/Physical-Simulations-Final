import java.util.Arrays;

public class Main 
{

	public static void main(String[] args) 
	{
		double[] a = {2.34, 1/2, 2.5};
		Point[] arr = NewtonRaphson.arrayOfVals(a, -5.0);
		System.out.print(Arrays.toString(arr) + "\n");
		
		for (int i = 0; i < 100; i++)
		{
			System.out.print("Iteration " + (i+1) + ": " + NewtonRaphson.findRoot(a, -234, i) + "\n");
		}
	}

}
