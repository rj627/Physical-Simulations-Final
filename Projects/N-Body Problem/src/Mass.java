
public class Mass 
{
	private Vector pos;
	private Vector vel;
	private double mass;
	
	public Mass() {pos = new Vector(0,0,0); vel = new Vector(0,0,0); mass = 0;}
	public Mass(Vector pos1, double mass1) {pos = pos1; mass = mass1; vel = new Vector(0,0,0);}
	public Mass(Vector pos1, Vector vel1, double mass1) {pos = pos1; vel = vel1; mass = mass1;}
	
	public Vector getPos() {return pos;}
	public double getMass() {return mass;}
	public Vector getVel() {return vel;}
	
	public void setVel(Vector newVel) {vel = newVel;}
	public void setPos(Vector newPos) {pos = newPos;}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Mass)) return false;
		
		Mass othMass = (Mass) other;
		
		if (othMass.getPos().equals(getPos()) && othMass.getVel().equals(getVel()) && 
				othMass.getMass() == getMass()); return true;
	
	}
	
	public String toString()
	{
		return mass + " " + vel.toString() + " " + pos.toString();
	}
}
