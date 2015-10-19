import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException 
	{
		Point[] swag = Pendulum.forcingFunct(50);
		
		for (int i = 0; i < swag.length; i++)
		{
			System.out.print(swag[i].getX() + "\t" + swag[i].getY()+ "\n");
		}
		
		/*double s = 0.00;
		int t = 0;
		double[] swag = new double[100000];
		
		while (t < 100000)
		{
			swag[t] = s*Math.sin(s);
			s+=.01;
			System.out.print(s + "\t" + swag[t] + "\n");
		}*/
		
		//p.close();

	}

}
