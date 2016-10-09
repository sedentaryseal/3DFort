package code.objects;

import processing.core.PApplet;
import processing.core.PImage;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

public class Plane extends DisplayableObject implements Animation {

	PImage tex;
	float pi     = PApplet.PI;
    float angle = pi/2.f;
    float aS = 0.f, aT = 0.f;
    float animationTime = 6.f;
    float amount = 70.f;
    float aT2 = 0.f;

	public Plane(Scene parent, String filename) {
		super(parent);
		tex = parent.loadImage(filename);
	}

	public void update(float dT){
		// Update plane position
	    amount = amount + -0.3f;

	    // Reset plane position when it moves out of the sky box
	    if (amount < -20.f){
	    	amount = 70.f;
	    	amount = amount + -0.3f;
	    }

	    // Rotating the plane prop
	    aT = (aT + dT) % animationTime/50.f;
	    aS = 14.f*aT/animationTime;
	    if (aS < 1 || aS > 13){
	    	angle += (float)dT*Scene.radians(20.f) + 2.f;
	    }else if (aS < 2 || aS > 12){
	    	angle += (float)dT*Scene.radians(20.f) + 2.f;
	    }else if (aS < 3 || aS > 11){
	    	angle += (float)dT*Scene.radians(20.f);
	    }else if (aS < 4 || aS > 10){
	    	angle -= (float)dT*Scene.radians(20.f);
	    }else if (aS < 5 || aS > 9){
	    	angle -= (float)dT*Scene.radians(20.f);
	    }else if (aS < 6 || aS > 8){
	    	angle -= (float)dT*Scene.radians(20.f);
	    }else{
	    	angle -= (float)dT*Scene.radians(20.f);
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

			parent.translate(30.f, -5.f,-40.f);
			parent.rotateY(PApplet.radians(270.f));

			parent.pushMatrix();
				parent.translate(amount, 0.f,0.f); // Moving the plane
				parent.translate(0.f,0.f,5.f);
				parent.pushMatrix();
					parent.translate(-4.f, 0.f,0.f);
					drawPlaneBody();
					parent.translate(-3.5f, 0.f,0.f);
					parent.fill(0,0,0);
					parent.sphere(0.5f);
				parent.popMatrix();

				parent.pushMatrix();
					parent.rotateX(angle); // Rotating the prop
					parent.translate(-7.5f, 0.f,1.5f);
					drawProp();
					parent.translate(0.f, 0.f,-3.f);
					drawProp();
				parent.popMatrix();

				parent.pushMatrix();
					parent.translate(-4.f, 0.f,4.f);
					parent.rotateY(PApplet.radians(90.f));
					drawWing();
				parent.popMatrix();

				parent.pushMatrix();
					parent.translate(-4.f, 0.f,-4.f);
					parent.rotateY(PApplet.radians(-90.f));
					drawWing();
				parent.popMatrix();

				parent.pushMatrix();
					parent.translate(-2.f, -2.f,0.f);
					drawTail();
					parent.translate(0.f, 0.f,1.1f);
					parent.pushMatrix();
						parent.rotateX(PApplet.radians(-90.f));
						drawTail();
					parent.popMatrix();

					parent.pushMatrix();
						parent.translate(0.f, 0.f,-2.1f);
						parent.rotateX(PApplet.radians(-90.f));
						drawTail();
					parent.popMatrix();
				parent.popMatrix();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	private void drawPlaneBody() {
		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex);

			float x = 3.f;
			float y = 1.f;
			float z = 1.f;

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

	private void drawTail() {
		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex);
			float x = 1.f;
			float y = 1.f;
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
		parent.endShape();
		parent.texture(null);
	}

	private void drawProp() {
		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex);
			float x = 0.1f;
			float y = 0.1f;
			float z = 1.f;

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

	private void drawWing() {
		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex);
			float x = 3.f;
			float y = 0.2f;
			float z = 1.f;

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
}