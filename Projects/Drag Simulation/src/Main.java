import java.util.Arrays;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) 
	{
		double[] coeffs = {2., 8.43, 2.3262};
		double swag = Simulation.euler(coeffs, 1.6, .4, 5.3);
		//System.out.print(swag);
		
		/*Scanner s = new Scanner(System.in);
		String str = s.next();
			
		if (str.equals("no drag"))
			System.out.print(Simulation.noDrag());
		else if (str.equals("constant")) 				
			System.out.print(Simulation.constant());
		else if (str.equals("adiabatic"))
			System.out.print(Simulation.adiabatic());
		else if (str.equals("isothermal"))
			System.out.print(Simulation.isothermal());
			
		s.close();*/
		
		for (int i = 0; i < 51; i++)
		{
			Point j = new Point(i/Math.sqrt(2), i/Math.sqrt(2));
			double swiggity = - Simulation.constantDrag(j)[1].getY();
			Point s = new Point(i, swiggity);
			
			double lelasdf = Simulation.noDragVel(i)[0];
			Point ws = new Point(i, lelasdf);
			
			double asdfasdf = Simulation.adiabatic(j)[1].getY();
			double swaggin = -Simulation.isothermal(j)[1].getY();
			
			//System.out.print(i + "\t" + /*s.getY() /*+*/ "\t" + -ws.getY() + "\n");
			
			//System.out.print(i + "\t" + -asdfasdf + "\n");
			
			System.out.print(i + "\t" + swaggin + "\n");
		}

	}

}
