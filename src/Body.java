
public class Body {
	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;
	
	/**
	 * Create a Body from parameters
	 * @param xp is initial x position (Horizontal axis)
	 * @param yp is initial y position (Vertical axis)
	 * @param xv is initial velocity along x axis 
	 * @param yv is initial velocity along y axis
	 * @param mass is mass of the object
	 * @param filename is the image name, which is used later to create animation
	 */
	public Body(double xp, double yp, double xv, double yv, double mass, String filename) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}
	
	
	/**
	 * Create a copy of a Body b
	 * @param b is the pre-existing body
	 */
	public Body(Body b) {
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	
	
	/**
	 * Getter method for obtaining myXPos instance variable
	 * @return myXPos of the current Body
	 */
	public double getX() {
		return myXPos;
	}
	
	
	/**
	 * Getter method for obtaining myYPos instance variable
	 * @return myYPos of the current Body
	 */
	public double getY() {
		return myYPos;
	}
	
	
	/**
	 * Getter method for obtaining myXVel instance variable
	 * @return myXVel of the current Body
	 */	
	public double getXVel() {
			return myXVel;
	}
	
	
	/**
	 * Getter method for obtaining myYVel instance variable
	 * @return myYVel of the current Body
	 */
	public double getYVel() {
		return myYVel;
	}
	
	
	/**
	 * Getter method for obtaining myMass instance variable
	 * @return myMass of the current Body
	 */
	public double getMass() {
		return myMass;
	}
	
	
	/**
	 * Getter method for obtaining myFileName instance variable
	 * @return myFileName of the current Body
	 */
	public String getName() {
		return myFileName;
	}
	
	
	/**
	 * Return the distance between this body and another
	 * @param b is the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(Body b) {
		double xPosOfb = b.getX();
		double yPosOfb = b.getY();
		double dx = xPosOfb - getX();
		double dy = yPosOfb - getY();
		double r = Math.sqrt((dx*dx)+(dy*dy)); //The function to calc r is sqrt(dx^2+dy^2)
		return r;		
	}
	
	
	/**
	 * Return exerted on this body by the another body b
	 * @param b is the other body that exert force toward this body
	 * @return force exerted on this body by the other body, b 
	 */
	public double calcForceExertedBy(Body p) {
		double g = 6.67 * 1e-11; //a gravitational force
		double massOfp = p.getMass();
		double f = ((g * getMass() * massOfp) / (calcDistance(p) * calcDistance(p))); //The function to calc f is (g*m*m_b)/(r^2)
		return f;
	}
	
	
	/**
	 * Return the force exerted on this body by body b along x axis
	 * where positive mean b pulling this body toward the right, and vice versa
	 * @param b is the other body that exert force toward this body
	 * @return force exerted on this body by the other body, b, along x axis 
	 */
	public double calcForceExertedByX(Body p) {
		double xPosOfp = p.getX();
		double dx = xPosOfp - getX();
		double fX = ((calcForceExertedBy(p) * dx) / (calcDistance(p))); // The function to calc fX is (f*dx)/r
		return fX;
	}
	
	
	/**
	 * Return the force exerted on this body by body b along y axis
	 * where positive mean b pulling this body upward, and vice versa
	 * @param b is the other body that exert force toward this body
	 * @return force exerted on this body by the other body, b, along y axis 
	 */
	public double calcForceExertedByY(Body p) {
		double yPosOfp = p.getY();
		double dy = yPosOfp - getY();
		double fY = ((calcForceExertedBy(p) * dy) / (calcDistance(p))); // The function to calc fY is (f*dy)/r
		return fY;
	}
	
	
	/**
	 * Return net force along x axis exerted on this body from all other bodies
	 * @param bodies is an array of other bodies
	 * @return net force along x axis exerted on this body from all other bodies
	 */
	public double calcNetForceExertedByX(Body[] bodies) {
		double xNetForce = 0;
		for (Body b : bodies) {
			if (! b.equals(this)) {
				xNetForce = xNetForce + calcForceExertedByX(b);
			}
		}
		return xNetForce;
	}
	
	
	/**
	 * Return net force along y axis exerted on this body from all other bodies
	 * @param bodies is an array of other bodies
	 * @return net force along y axis exerted on this body from all other bodies
	 */
	public double calcNetForceExertedByY(Body[] bodies) {
		double yNetForce = 0;
		for (Body b : bodies) {
			if (! b.equals(this)) {
				yNetForce = yNetForce + calcForceExertedByY(b);
			}
		}
		return yNetForce;
	}
	
	
	/**
	 * This void method is updating the position of this body for the simulation process
	 * @param deltaT is time interval for each simulation step
	 * @param xforce is net force exerted to this body along x axis which is obtained by calcNetForceExertedByX
	 * @param yforce is net force exerted to this body along y axis which is obtained by calcNetForceExertedByY
	 */
	public void update(double deltaT, double xforce, double yforce) {
		double ax = xforce / getMass();
		double ay = yforce / getMass();
		double nvx = myXVel + (deltaT * ax);
		double nvy = myYVel + (deltaT * ay);
		double nx = myXPos + (deltaT * nvx); 
		double ny = myYPos + (deltaT * nvy);
		myXPos = nx;
		myXVel = nvx;
		myYPos = ny;
		myYVel = nvy;
	}
	
	
	/**
	 * The method call StdDraw.java to draw a picture during simulation
	 */
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
	
}

