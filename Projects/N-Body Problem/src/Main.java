import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		GraphViewer g = new GraphViewer(200,200);
		//PointGraph p = new PointGraph(5, 4);
		
		g.mode[0] = "DISCRETE";
		
		PointGraph[] myData = new PointGraph[(int) (Math.random()*100)];
		
		for (int i = 0; i < myData.length; i++)
		{
			myData[i] = new PointGraph(Math.random()*100, Math.random()*200);
		}
		
		g.dataSet[0] = myData;
		g.repaint();
		
		//g.paint(p);
		
		//Mass m1 = new Mass(new Vector(0,10,0), 100);
		//Mass m2 = new Mass(new Vector(0,0,0), 100);

		/*double massEarth = 5.972E24;
		//double massMoon = 7.34767309E22;
		double massSun = 1.989E30;
		double massMerc = 3.3022E23;
		double massVenus = 4.8685E24;
		double massMars = 6.4185E23;
		double massJupiter = 1.8986E27;
		double massSaturn = 5.6846E26;
		double massUrAnus = 8.6810E25;
		double massNeptune = 10.243E25;
		
		
		
		//correct velocity, positions, etc 
		Mass sun = new Mass(new Vector(0, 0, 0), new Vector(0, 0, 0), massSun);
		
		Mass mercury = new Mass(new Vector(57000000000., 0, 0), new Vector (0, 47900., 0), massMerc);
		
		Mass venus = new Mass(new Vector(-1.082E11, 0, 0), new Vector (0, 35000., 0), massVenus);
		
		Mass earth = new Mass(new Vector(-152600000000.,0,0), new Vector(0,30000.,0), massEarth);
		
		Mass moon = new Mass(new Vector(-406536000-152600000000.,0,0), 
				new Vector(0, 31023, 0), massMoon);
		
		Mass mars = new Mass(new Vector(-2.279E11, 0, 0), new Vector(0, 24100., 0), massMars);
		
		Mass jupiter = new Mass(new Vector(-7.783E11, 0, 0), new Vector(0, 13100., 0), massJupiter);
		
		Mass saturn = new Mass(new Vector(-1.426E12,0, 0), new Vector(0, 9600., 0), massSaturn);
		
		Mass uranus = new Mass(new Vector(-2.871E12, 0, 0), new Vector(0, 6800., 0), massUrAnus);
		
		Mass neptune = new Mass(new Vector(-4.497E12, 0, 0), new Vector(0, 5400., 0), massNeptune);
		
		Mass[] sweg = {sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune};
		
		Simulation.simulateMultipleBodies(sweg);*/
		
		//Simulation.simulateThreeBodies(sun, earth, moon);
		
		//System.out.print(s + " seconds" + "\n" + (s/10)/86400 + " days" );
		
		//System.out.print(Simulation.simulate(m1v,m2v));
		
		//System.out.print(Arrays.toString(Simulation.simulate(sweg)));
	}

}
