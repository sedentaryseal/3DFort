package code.objects;

import framework.interfaces.Input;
import processing.core.PApplet;
import processing.core.PImage;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

public class Cannon extends DisplayableObject implements Animation, Input {

	PImage tex;
	PImage tex1;
    float amount1 = 2.f;
    float amount2 = 2.f;
    static int fire = 0;
    static int reachedPos = 0; // The position at which the cannon ball will explode and the explosion will appear
    static int reachedPos2 = 0; // The position at which the explosion will disappear
    static int cannonFireReached = 0;
    static int onTarget = 0; // If the cannon ball will hit/miss the target

	public Cannon(Scene parent, String filename, String filename1) {
		super(parent);
		tex = parent.loadImage(filename);
		tex1 = parent.loadImage(filename1);
	}

	public void update(float dT){
		// Reset cannon ball position
	    if (fire == 0){
	    	amount1 = 0.f;
	    	amount2 = 0.f;
	    	reachedPos = 0;
	    }

	    // The distance the cannon ball moves each update along Y and Z:
	    amount1 = amount1 + -0.1f;
	    amount2 = amount2 + -0.2f;

	    // ReachedPos is the position at which the cannon ball is set to explode, i.e. in line with the target
	    if (amount2 <= -22){
	    	reachedPos = 1;
	    }

	    // ReachedPos2 is 0.2f after the explosion and will make the explosion disappear
	    if (amount2 <= -22.2f){
	    	reachedPos2 = 1;
	    }

	    // Once the cannon ball has left the barrel of the cannon, display a small explosion
	    if (amount2 <= -1){
	    	cannonFireReached = 1;
	    }

	    // Stop displaying the small explosion from the barrel after the cannon ball moves from the barrel
	    if (amount2 <= -2){
	    	cannonFireReached = 0;
	    }
	}

	@Override
	public void display() {
		parent.pushMatrix();
		parent.pushStyle();
		{
			parent.noStroke();
			parent.translate(pos.x, pos.y, pos.z);
			parent.scale(scale.x, scale.y, scale.z);
			parent.rotateY(rotation.y);
			parent.rotateX(rotation.x);
			parent.rotateZ(rotation.z);

			parent.pushMatrix();
				parent.pushMatrix();
					parent.translate(0.f, amount1,amount2); // Move the cannon ball
					parent.translate(1.2f,-5.f,0.f);

					// If user presses fire button, create cannon ball
					if (fire == 1){
						parent.fill(0,0,0);
						parent.sphere(0.2f);
					}

					// Once the cannon has fired, display an explosion effect at the end of the cannon
					if (cannonFireReached == 1){
						for (int i = 0; i < 5; i++){
								// Doing rotations and drawing the fire on the end of the stick
								parent.rotateZ(PApplet.radians(40.f));
								fire(1);
								parent.rotateZ(PApplet.radians(20.f));
								fire(2);
						}
					}

					// Creating the cannon ball explosion when the ball reaches specified point
					if (reachedPos == 1){
						for (int i = 0; i < 10; i++){
							// Doing rotations and drawing the fire on the end of the stick
							parent.rotateZ(PApplet.radians(40.f));
							parent.scale(1.3f);
							fire(1);
							parent.rotateZ(PApplet.radians(20.f));
							fire(2);
						}
					}

					// Remove the fire once the cannon ball reaches the second position
					if (reachedPos2 == 1){
						fire = 0;
						reachedPos2 = 0;
					}

				parent.popMatrix();

				parent.rotateY(PApplet.radians(-90.f));
				parent.translate(0.f,-4.f,-2.5f);

				// Draw one side of the cannon
				parent.pushMatrix();
					drawCube(1);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(2);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(3);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(4);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(5);
				parent.popMatrix();

				// Drawing the wooden planks that separate the two sides of the cannon
				parent.pushMatrix();
					parent.translate(-0.5f,0.f,1.25f);
					parent.rotateY(PApplet.radians(90.f));
					drawCube(2);
					parent.translate(0.f,0.5f,0.f);
					drawCube(2);
					parent.translate(0.f,0.5f,0.f);
					drawCube(2);
					parent.translate(0.f,0.5f,0.f);
					drawCube(2);
					parent.translate(0.f,0.5f,0.f);
					drawCube(2);
				parent.popMatrix();

				// Drawing some wooden planks along the bottom
				parent.pushMatrix();
					parent.rotateY(PApplet.radians(90.f));
					parent.translate(-1.25f,2.f,0.0f);
					drawCube(2);
					parent.translate(0.f,0.f,0.5f);
					drawCube(2);
					parent.translate(0.f,0.f,0.5f);
					drawCube(2);
					parent.translate(0.f,0.f,0.5f);
					drawCube(2);
					parent.translate(0.f,0.f,0.5f);
					drawCube(2);
					parent.translate(0.f,0.f,0.5f);
					drawCube(2);
				parent.popMatrix();

				parent.translate(0.f,0.f,2.5f);

				// Draw the other side of the cannon
				parent.pushMatrix();
					drawCube(1);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(2);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(3);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(4);
					parent.translate(0.25f,0.5f,0.f);
					drawCube(5);
				parent.popMatrix();

				parent.translate(1.f,-0.25f,-1.25f);

				// Drawing the cannon barrel using cylinders with tops
				parent.pushMatrix();
					parent.rotateZ(PApplet.radians(40.f));
					parent.rotateY(PApplet.radians(90.f));
					cannonBarrel();
					parent.rotateX(PApplet.radians(180.f));
					cannonBarrel();
				parent.popMatrix();
		parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();

	}

	// Method to create the fire effect
	private void fire(int colour){
		float w = 1.f;
		if (colour == 1){
			parent.fill(255,140,0);
			parent.ambient(0,0,0);
			parent.specular(255, 140, 0);
		} else if (colour == 2){
			parent.fill(255,0,0);
			parent.ambient(0,0,0);
			parent.specular(255, 0, 0);
		}
		parent.rotateX(PApplet.radians(60.f));
		parent.beginShape(PApplet.QUAD_STRIP);
		for(int i = 0; i <= 360; i += 10){
			parent.vertex(PApplet.cos(i)*(w-1),PApplet.sin(i)*(w-1));
			parent.vertex(PApplet.cos(i)*w,PApplet.sin(i)*w);
			parent.normal(0.f, 0.f, 1.f);
		}
		parent.endShape();
	}

	private void drawCube(int lengthOfPlank) {
		// This method takes in an int, lengthOfPlank which will create planks of varying lengths

		parent.ambient(100,100,100);
		parent.specular(100, 100, 100);

		float x = 0.f;
		float y = 0.f;
		float z = 0.f;

		if (lengthOfPlank == 1){
			x = 0.75f;
			y = 0.25f;
			z = 0.25f;
		} else if (lengthOfPlank == 2){
			x = 1.f;
			y = 0.25f;
			z = 0.25f;
		} else if (lengthOfPlank == 3){
			x = 1.25f;
			y = 0.25f;
			z = 0.25f;
		} else if (lengthOfPlank == 4){
			x = 1.5f;
			y = 0.25f;
			z = 0.25f;
		} else if (lengthOfPlank == 5){
			x = 1.75f;
			y = 0.25f;
			z = 0.25f;
		}

		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex);

			parent.vertex(-x, -y, z, 0.f, 1.f);
			parent.vertex(-x, y, z, 0.f, 0.f);
			parent.vertex(x, y, z, 1.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 1.f);

			parent.normal(0.0f, 0.0f, -1.0f);

			parent.vertex(x, -y, -z, 0.f, 1.f);
			parent.vertex(x, y, -z, 0.f, 0.f);
			parent.vertex(-x, y, -z, 1.f, 0.f);
			parent.vertex(-x, -y, -z, 1.f, 1.f);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0.f, 1.f);
			parent.vertex(-x, y, -z, 0.f, 0.f);
			parent.vertex(-x, y, z, 1.f, 0.f);
			parent.vertex(-x, -y, z, 1.f, 1.f);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0.f, 1.f);
			parent.vertex(x, y, z, 0.f, 0.f);
			parent.vertex(x, y, -z, 1.f, 0.f);
			parent.vertex(x, -y, -z, 1.f, 1.f);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0.f, 1.f);
			parent.vertex(-x, -y, z, 0.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 0.f);
			parent.vertex(x, -y, -z, 1.f, 1.f);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0.f, 1.f);
			parent.vertex(-x, y, -z, 0.f, 0.f);
			parent.vertex(x, y, -z, 1.f, 0.f);
			parent.vertex(x, y, z, 1.f, 1.f);
		parent.endShape();
		parent.texture(null);
	}

	// Method to create the cannon barrel using cylinders
	private void cannonBarrel(){

		parent.ambient(0,0,0);
		parent.specular(50, 50, 50);
		parent.shininess(0);
		float r = 0.75f;
		float a = 0;
		int detail = 120;
		float d = 2;
		parent.fill(0,0,0);

		// Create the cylinder
		parent.beginShape(PApplet.QUAD_STRIP);
		 for (int i = 0; i <= detail; i++){
		   float  x = PApplet.cos(PApplet.radians(a)) * r;
		   float  y = PApplet.sin(PApplet.radians(a)) * r;
		   parent.vertex(x, y, d);
		   parent.normal(x, 0.f, d);
		   parent.vertex(x, y, -d);
		   a += 360 / detail;
		 }
		 parent.endShape();

		 // Create the top for the cylinder
		 parent.beginShape(PApplet.POLYGON);
		 for (int i = 0; i <= detail; i++){
		   float  x = PApplet.cos(PApplet.radians(a)) * r;
		   float  y = PApplet.sin(PApplet.radians(a)) * r;
		   parent.vertex(x, y, d);
		   a += 360 / detail;
		 }
		 parent.endShape();

		 // Creating the bottom for the cylinder
		 parent.beginShape(PApplet.POLYGON);
		 for (int i = 0; i <= detail; i++){
		   float  x = PApplet.cos(PApplet.radians(a)) * r;
		   float  y = PApplet.sin(PApplet.radians(a)) * r;
		   parent.vertex(x, y, -d);
		   a += 360 / detail;
		 }
		 parent.endShape();
	}

	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if (state != 1) return;

		switch (key)
		{
			case 'f':
				System.out.println("Firing cannon...");
				fire = 1;
				// From here need to call something in target plane to see if on target
				PlaneTarget.cannonFired();
				break;
			case 'F':
				System.out.println("Firing cannon...");
				fire = 1;
				// From here need to call something in target plane to see if on target
				PlaneTarget.cannonFired();
				break;
		}
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Input#handleSpecialKey(int, int, int, int)
	 */
	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Input#handleMouse(int, int, int, int)
	 */
	@Override
	public void handleMouse(int button, int state, int mX, int mY) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Input#handleMouseDrag(int, int)
	 */
	@Override
	public void handleMouseDrag(int mX, int mY) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Input#handleMouseMove(int, int)
	 */
	@Override
	public void handleMouseMove(int mX, int mY) {
		// TODO Auto-generated method stub

	}
}