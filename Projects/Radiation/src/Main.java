
public class Main {

	public static void main(String[] args) 
	{
		double radTest = Radiation.radiation(25, 5.2, 2.6);
		System.out.print(radTest);
		
		/*double radTest2 = Radiation.radiationModelDerivValue(25, 5.62, 27.4);
		System.out.print("\n" + radTest2);*/
		
		double finalRad = Radiation.euler(25, 2.6, 5.2);
		System.out.print("\n" + finalRad);
		
		double rk4Test = Radiation.rungeKutta(25, 2.6, 5.2);
		System.out.print("\n" + rk4Test);
	}

}
