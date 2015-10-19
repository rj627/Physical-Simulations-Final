import java.util.Arrays;


public class Main 
{

	public static void main(String[] args) 
	{
		double asdf = 0.0;
		double[] values = Derivatives.populateArray(asdf, 1000);
		System.out.print(Arrays.toString(values));
		
		for (int i = 1; i < 999; i++)
		{
			System.out.print(Derivatives.leftDeriv(values,i) + " ");
			System.out.print(Derivatives.rightDeriv(values, i) + " ");
			System.out.print(Derivatives.midDeriv(values,i) + " ");
			double a = Derivatives.returnA(asdf-.01, asdf, asdf+.01, values[i-1], values[i], values[i+1]);
			double h = Derivatives.returnH(asdf-.01, asdf, asdf + 0.01, values[i-1],values[i],values[i+1]);
			System.out.print(Derivatives.parabolicDeriv(a, h, asdf) + " ");
			System.out.print(Derivatives.actualDeriv(asdf) + "\n");
			//n+=Derivatives.DELTA_X;
			asdf+=Derivatives.DELTA_X;
		}
		
		

	}

}
