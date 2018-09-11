	

/**
 * @author Promvarat Pradit
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		
		Scanner s = new Scanner(new File(fname));
		
		// TODO: read values at beginning of file to --> Done!!!
		// find the radius --> Done!!!
		int nbodies = s.nextInt();
		double universeRadius = s.nextDouble();
		
		s.close();
		
		// TODO: return radius read --> Done!!!
		return universeRadius;	
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static Body[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));
			
			// TODO: read # bodies, create array, ignore radius --> Done!!!
			int nb = 0; // # bodies to be read
			nb = s.nextInt();
			double universeRadius = s.nextDouble();
			
			Body[] bodies = new Body[nb];
			for(int k=0; k < nb; k++) {
				// TODO: read data for each body
				// construct new body object and add to array
				double xPos = s.nextDouble();
				double yPos = s.nextDouble();
				double xVel = s.nextDouble();
				double yVel = s.nextDouble();
				double mass = s.nextDouble();
				String filename = s.next();
				Body bodytoken = new Body(xPos, yPos, xVel, yVel, mass, filename);
				bodies[k] = bodytoken;
			}
			s.close();
			
			// TODO: return array of body objects read --> Done!!!
			return bodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 157788000.0;
		double dt = 25000.0;
		//double totalTime = 1000000000.0;
		//double dt = 1000000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		Body[] bodies = readBodies(fname);
		double radius = readRadius(fname);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces <-- Done!!!
			// to hold forces on each body <-- Done!!!
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			
			// TODO: loop over all bodies, calculate <-- Done!!!
			// net forces and store in xforces and yforces <-- Done!!!
			for (int k = 0; k < bodies.length ; k += 1) {
				xForces[k] = bodies[k].calcNetForceExertedByX(bodies);
				yForces[k] = bodies[k].calcNetForceExertedByY(bodies);
			}
			
			// TODO: loop over all bodies and call update <-- Done!!!
			// with dt and corresponding xforces, yforces values <-- Done!!!
			for (int k = 0; k < bodies.length ; k += 1) {
				bodies[k].update(dt, xForces[k], yForces[k]);
			}
			
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one <-- Done!!!
			for (int k = 0; k < bodies.length ; k += 1) {
				bodies[k].draw();
			}
			
			StdDraw.show(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
