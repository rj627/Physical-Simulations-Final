import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException
	{
		/*Point[] swag = new Point[25000];
		
		for (int i = 0; i < swag.length; i++)
		{
			swag[i] = new Point(i, 2423.12312*i*i +5.234211412*i + 1512.123511125);
		}*/
		
		int[] swagaf = LeastSquares.loadData("GeigerData.txt");
		Point[] swagafpt = new Point[swagaf.length];
		
		for (int i = 0; i < swagaf.length; i++)
		{
			swagafpt[i] = new Point(i, swagaf[i]);
		}
		System.out.println(Arrays.toString(swagafpt));
		double[] guess = {50000,20,7,50};
		
		System.out.print(Arrays.toString(LeastSquares.genReg(swagafpt, guess)));
		
		//System.out.print("\n" + Arrays.toString(swagaf) + "\n");
		/*
		for (int i = 0; i < swagaf.length; i++)
		{
			System.out.println(swagaf[i]);
		}*/
		
		/*Point[] lol = new Point[250];
		
		double[] coeffs = {2, 3, 4, 5};
		for (int i = 0; i < lol.length; i++)
		{
			
			lol[i] = LeastSquares.myGeneralFunction(i,coeffs );
		}
		
		System.out.print(Arrays.toString(LeastSquares.genReg(lol, guess)));*/
	}
	
}


