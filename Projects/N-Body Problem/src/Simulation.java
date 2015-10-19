import java.io.*;
import java.util.Arrays;


public class Simulation 
{
	public static double VALUE_OF_G = 6.67429E-11;
	public static double TIME_STEP = 36000;
	public static GraphViewer g = new GraphViewer(100,100);
	
	
	public static Vector force(Mass m1, Mass m2)
	{
		double numerator = m1.getMass()*m2.getMass() * VALUE_OF_G;
		//Vector subtr = m1.getPos().subtract(m2.getPos());
		Vector subtr = m1.getPos().add(m2.getPos());
		double mag = subtr.magnitude();
		
		double intermediate = -numerator/Math.pow(mag, 3.0);
		
		return subtr.multiply(intermediate);
	}
	
	public static Vector acceleration(Vector force, Mass m)
	{
		return force.multiply(1.0/m.getMass());
	}
	
	public static Vector velocity(Vector acc, Vector initVel)
	{
		return initVel.add(acc.multiply(TIME_STEP));
	}
	
	public static Vector position(Vector vel, Vector initPos)
	{
		return initPos.add(vel.multiply(TIME_STEP));
	}
	
	public static void simulate(Mass m1, Mass m2)
	{
		while (true)
		{
			Vector force = force(m1, m2);
			
			Vector acc1 = acceleration(force, m1);
			Vector acc2 = acceleration(force, m2);
			
			m1.setVel(velocity(acc1, m1.getVel()));
			m2.setVel(velocity(acc2.multiply(-1), m2.getVel()));
			
			m1.setPos(position(m1.getVel(), m1.getPos()));
			m2.setPos(position(m2.getVel(), m2.getPos()));
			
			System.out.print(m1.getPos() + "\t" + m2.getPos() + "\n");
			//System.out.print(m1.getVel() + "\t" + m2.getVel() + "\n");
		}

	}
	
	public static void simulateThreeBodies(Mass m1, Mass m2, Mass m3) throws FileNotFoundException
	{
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
		
		while (true)
		{
			Vector force12 = force(m1, m2);
			Vector force23 = force(m2, m3);
			Vector force13 = force(m1, m3);
		
			Vector acc1 = acceleration(force12.add(force13), m1);
			Vector acc2 = acceleration(force23.add(force12), m2);
			Vector acc3 = acceleration(force13.add(force23), m3);
		
			m1.setVel(velocity(acc1, m1.getVel()));
			m2.setVel(velocity(acc2.multiply(-1), m2.getVel()));
			m3.setVel(velocity(acc3.multiply(-1), m3.getVel()));
		
			m1.setPos(position(m1.getVel(), m1.getPos()));
			m2.setPos(position(m2.getVel(), m2.getPos()));
			m3.setPos(position(m3.getVel(), m3.getPos()));
		
			System.out.print(m1.getPos() + "\t" + m2.getPos() + "\t" + m3.getPos() + "\n");
		} //output to a text file!
		
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		g.mode[0] = "DISCRETE";
		
		Mass[] arr = new Mass[2500];
		
		//WITHOUT A INIT VEL 
		for (int i = 0; i < arr.length; i++)
		{
			if (i % 4 == 0)
				arr[i] = new Mass(new Vector(Math.random()*100, Math.random()*-100, Math.random()*100 ), Math.random()*500);
			if (i%4 == 1)
				arr[i] = new Mass(new Vector(Math.random()*-100, Math.random()*-100, Math.random()*100), Math.random()*500);
			if (i%4 == 2)
				arr[i] = new Mass(new Vector(Math.random()*-100, Math.random()*100, Math.random()*100), Math.random()*500);
			if (i%4 == 3)
				arr[i] = new Mass(new Vector(Math.random()*100, Math.random()*100, Math.random()*100), Math.random()*500);
		}
		
		//with init vel
		/*for (int i = 0; i < arr.length; i++)
		{
			if (i % 4 == 0)
				arr[i] = new Mass(new Vector(Math.random()*100, Math.random()*-100, Math.random()*100 ), 
						new Vector(2*Math.random(), 2*Math.random(), 2*Math.random()), Math.random()*500);
			if (i%4 == 1)
				arr[i] = new Mass(new Vector(Math.random()*-100, Math.random()*-100, Math.random()*100), 
						new Vector(2*Math.random(), 2*Math.random(), 2*Math.random()), Math.random()*500);
			if (i%4 == 2)
				arr[i] = new Mass(new Vector(Math.random()*-100, Math.random()*100, Math.random()*100), 
						new Vector(2*Math.random(), 2*Math.random(), 2*Math.random()), Math.random()*500);
			if (i%4 == 3)
				arr[i] = new Mass(new Vector(Math.random()*100, Math.random()*100, Math.random()*100), 
						new Vector(2*Math.random(), 2*Math.random(), 2*Math.random()), Math.random()*500);
		}*/
		
		//System.out.print(Arrays.toString(arr));
		
		while (true)
		{
			PointGraph[] arr2 = new PointGraph[2500];
			
			for (int i = 0; i < arr.length; i++)
			{
				Vector totalForce = new Vector(0, 0, 0);
				Mass m1 = arr[i];
				
				for (int j = 0; j < arr.length; j++)
				{
					Mass m2 = arr[j];
					
					if (i != j)
					{
						totalForce = totalForce.add(force(m2, m1));
					}
				}
			
				Vector totalAcc = acceleration(totalForce, m1);
				m1.setVel(velocity(totalAcc, m1.getVel()));
				m1.setPos(position(m1.getVel(), m1.getPos()));
				
				
				arr2[i] = VecToPt(m1.getPos());
				
			}
			
			g.dataSet[0] = arr2;
			g.repaint();
			//System.out.print("\n" + Arrays.toString(arr) + "\n");
		}
	}
	
	public static PointGraph VecToPt(Vector v)
	{
		return new PointGraph(v.getX(), v.getY());
	}
}
