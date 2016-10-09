package code.objects;

import processing.core.PApplet;
import processing.core.PImage;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;
import framework.interfaces.Lighting;

public class Soldier extends DisplayableObject implements Animation, Lighting {
	PImage tex;
	PImage tex1;
	float pi = PApplet.PI;
    float angle1 = pi/4.f;
    float aS = 0.f, aT = 0.f;
    float animationTime = 6.f;
    static int directHit = 0;
    float angle = 0.f;
    int decrease = 0;
    float position0[], ambient0[], diffuse0[], specular0[];
    boolean positional;

	public Soldier(Scene parent, String filename, String filename1) {
		super(parent);
		tex = parent.loadImage(filename);
		tex1 = parent.loadImage(filename1);

		// Light settings for the fire stick
		position0 = new float[3];
		ambient0 = new float[3];
		diffuse0 = new float[3];
		specular0 = new float[3];

		ambient0[0] = 38.f;
		ambient0[1] = 0.f;
		ambient0[2] = 0.f;

		diffuse0[0] = 255.f;
		diffuse0[1] = 28.f;
		diffuse0[2] = 28.f;

		specular0[0] = 255.f;
		specular0[1] = 0.f;
		specular0[2] = 0.f;

		position0[0] = 1.f;
		position0[1] = -1.f;
		position0[2] = 1.f;

		positional = true;
	}

	@Override
	public void setupLighting() {
		parent.lightSpecular(0,0,0); // Reset specular light property!
	}

	@Override
	public void update(float dT) {
	    aT = (aT + dT) % animationTime;
	    aS = 14.f*aT/animationTime;

	    // Arm rotation for lighting the cannon
	    if (aS < 7){
	    	angle1 += (float)dT*Scene.radians(40.f);
	    }else if (aS > 7){
	    	angle1 -= (float)dT*Scene.radians(40.f);
	    }

	    // Check if the cannon ball hits the target, if so rotate arm in celebration
	    if (directHit == 1){
	    	if (decrease == 1){
	    		angle -= 0.1f;
	    	} else if (decrease == 0){
	    		angle += 0.1f;
	    	}
	    	if (angle <= 2.f){
	    		decrease = 0;
	    	}
	    	if (angle >= 4.f){
	    		decrease = 1;
	    	}
	    }
	}

	public static void directHit(){
		// The target will be hit so the soldier needs to celebrate
		directHit = 1;
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

			// Positioning the soldier, rotating to face direction of cannon
			parent.translate(5.f, 13.f,1.f);
			parent.rotateY(PApplet.radians(-90.f));

			// Creating the body as the parent
			drawBodyPart(1);
			parent.translate(-4.f,-2.f,0.f);

			// Drawing the legs
			parent.pushMatrix();
				parent.translate(0.5f, 4.f,0.f);
				drawBodyPart(2);
				parent.translate(-5.f, -2.f,0.f);
				drawBodyPart(2);
			parent.popMatrix();

			// Drawing the head
			parent.pushMatrix();
				parent.translate(0.f, -3.f,0.f);
				parent.rotateY(PApplet.radians(-60.f));
				parent.translate(-2.f, 0.f,-3.5f);
				drawBodyPart(3);
			parent.popMatrix();

			// Drawing the arms
			parent.pushMatrix();
				parent.pushMatrix();
					parent.translate(1.25f, 0.25f,0.f);
					parent.rotateX(angle); // Rotate the arm to light the cannon
					drawBodyPart(4);
				parent.popMatrix();

				parent.translate(-1.25f,0.25f,0.f);
				parent.rotateX(angle1-1.f);
				drawBodyPart(4);

				// Drawing the fire stick
				parent.pushMatrix();
					parent.translate(-4.f, 1.f,4.f);
					parent.rotateX(PApplet.radians(-80.f));
					drawTorch(); // Drawing the torch
					parent.translate(0.f, -2.f,0.f);
					parent.noLights();
					for (int i = 0; i < 5; i++){
						// Doing rotations and drawing the fire on the end of the stick
						parent.rotateZ(PApplet.radians(40.f));
						fire(1);
						parent.rotateZ(PApplet.radians(20.f));
						fire(2);
					}
					parent.lights();
				parent.popMatrix();
			parent.popMatrix();

			// Drawing the hat for the soldier, with a black fur texture
			parent.pushMatrix();
				parent.translate(0.f, -5.f,0.f);
				drawHat();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();

	}

	// Method for drawing a body part, parameter specifies which body part to draw
	private void drawBodyPart(int type) {
		float x = 0.f;
		float y = 0.f;
		float z = 0.f;

		parent.beginShape(Scene.QUADS);

		// Find out which body part to draw and set fill colour/texture:
		if (type == 1){
			// X/Y/Z for Body:
			parent.ambient(100,100,100);
			parent.specular(255, 0, 0);
			x = 1.0f;
			y = 2.f;
			z = 1.f;
			parent.fill(255, 0, 0); // Red
		} else if (type == 2){
			// X/Y/Z for Leg:
			parent.ambient(100,100,100);
			parent.specular(50, 50, 50);
			x = 0.5f;
			y = 2.f;
			z = 0.5f;
			parent.fill(0, 0, 0); // Black
		} else if (type == 3){
			// X/Y/Z for Head:
			parent.ambient(100,100,100);
			parent.specular(255, 220, 178);
			x = 0.5f;
			y = 1.f;
			z = 0.5f;
			if(tex != null) parent.texture(tex1); // Bind face texture
		} else if (type == 4){
			// X/Y/Z for Arm:
			parent.ambient(100,100,100);
			parent.specular(255, 50, 50);
			x = 0.25f;
			y = 2.f;
			z = 0.25f;
			parent.fill(255, 50, 50); // Different shade of red
		}

			parent.normal(0.f, 0.f, 1.f);

			parent.vertex(-x, -y, z, 0.f, 1.f);
			parent.vertex(-x, y, z, 0.f, 0.f);
			parent.vertex(x, y, z, 1.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 1.f);

			parent.normal(0.0f, 0.0f, -1.0f);

			parent.vertex(x, -y, -z, 0.f, 0.f);
			parent.vertex(x, y, -z, 0.f, 0.f);
			parent.vertex(-x, y, -z, 0.f, 0.f);
			parent.vertex(-x, -y, -z, 0.f, 0.f);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z,0.f, 0.f);
			parent.vertex(-x, y, -z,0.f, 0.f);
			parent.vertex(-x, y, z,0.f, 0.f);
			parent.vertex(-x, -y, z,0.f, 0.f);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z,0.f, 0.f);
			parent.vertex(x, y, z,0.f, 0.f);
			parent.vertex(x, y, -z,0.f, 0.f);
			parent.vertex(x, -y, -z,0.f, 0.f);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z,0.f, 0.f);
			parent.vertex(-x, -y, z,0.f, 0.f);
			parent.vertex(x, -y, z,0.f, 0.f);
			parent.vertex(x, -y, -z,0.f, 0.f);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z,0.f, 0.f);
			parent.vertex(-x, y, -z,0.f, 0.f);
			parent.vertex(x, y, -z,0.f, 0.f);
			parent.vertex(x, y, z,0.f, 0.f);

			parent.translate(4.f,2.f,0.f);
		parent.endShape();
		parent.texture(null);
	}

	// Method for drawing the hat for the soldier
	private void drawHat() {
		parent.ambient(100,100,100);
		parent.specular(50, 50, 50);

		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex);

			float x = 0.75f;
			float y = 1.5f;
			float z = 0.75f;

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

			parent.translate(4.f,2.f,0.f);

		parent.endShape();
		parent.texture(null);
	}

	private void drawTorch() {

		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex);

			float x = 0.1f;
			float y = 2.f;
			float z = 0.1f;

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

			parent.translate(4.f,2.f,0.f);

		parent.endShape();
		parent.texture(null);
	}

	private void fire(int colour){
		float w = 0.1f;
		if (colour == 1){
			parent.fill(255,140,0);
		} else if (colour == 2){
			parent.fill(255,0,0);
		}
		parent.rotateX(PApplet.radians(60.f));
		parent.beginShape(PApplet.QUAD_STRIP);
		for(int i = 0; i <= 360; i += 10){
			parent.vertex(PApplet.cos(i)*(w-1),PApplet.sin(i)*(w-1));
			parent.vertex(PApplet.cos(i)*w,PApplet.sin(i)*w);
		}
		parent.endShape();
	}
}
