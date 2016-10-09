package code.objects;

import processing.core.PApplet;
import processing.core.PImage;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

public class PlaneTarget extends DisplayableObject implements Animation {
	PImage tex;
	PImage tex1;
	PImage tex2;
	float pi     = PApplet.PI;
    float angle = pi/2.f;
    float aS = 0.f, aT = 0.f;
    float animationTime = 6.f;
    float amount = 70.f;
    float angle2 = 0.f;
    float aT2 = 0.f;
    static int inFiringLine = 0; // Used to check if the target is in the line of sight of the cannon

	public PlaneTarget(Scene parent, String filename, String filename1, String filename2) {
		super(parent);
		tex = parent.loadImage(filename);
		tex1 = parent.loadImage(filename1);
		tex2 = parent.loadImage(filename2);
	}

	public void update(float dT){
		aT = (aT + dT) % animationTime/50.f;

		// Moving the plane
	    amount = amount + -0.15f;

	    // Checking if the target is in the firing line
	    if (amount <= 42.f && amount >= 36.f){
	    	inFiringLine = 1;
	    } else {
	    	inFiringLine = 0;
	    }

	    // Reset the position of the plane if it moves out of the sky box
	    if (amount < -30.f){
	    	amount = 70.f;
	    	amount = amount + -0.15f;
	    }

	    // Used for propeller rotation
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

	    // Used for flag rotation
	    aT2 = (aT2 + dT) % animationTime;
	    aS = 14.f*aT2/animationTime;
	    if (aS < 1 || aS > 13){
	    	angle2 += (float)dT*Scene.radians(20.f);
	    }else if (aS < 2 || aS > 12){
	    	angle2 += (float)dT*Scene.radians(20.f);
	    }else if (aS < 3 || aS > 11){
	    	angle2 += (float)dT*Scene.radians(20.f);
	    }else if (aS < 4 || aS > 10){
	    	angle2 -= (float)dT*Scene.radians(20.f);
	    }else if (aS < 5 || aS > 9){
	    	angle2 -= (float)dT*Scene.radians(20.f);
	    }else if (aS < 6 || aS > 8){
	    	angle2 -= (float)dT*Scene.radians(20.f);
	    }else{
	    	angle2 -= (float)dT*Scene.radians(20.f);
	    }
	}

	public static void cannonFired(){
		if (inFiringLine == 1){
			Soldier.directHit(); // The target will be hit so need to tell the soldier to celebrate
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

			parent.translate(0.f, -20.f,0.f);
			parent.translate(0.f, 0.f,-10.f);

			parent.pushMatrix();
				parent.translate(amount, 0.f,0.f); // Move the plane
				parent.translate(0.f,0.f,5.f);

				parent.pushMatrix();
					parent.translate(-4.f, 0.f,0.f);
					drawPlaneBody();
					parent.translate(-3.5f, 0.f,0.f);
					parent.fill(0,0,0);
					parent.sphere(0.5f);
				parent.popMatrix();

				parent.pushMatrix();
					parent.rotateX(angle); // Rotate the prop
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
					parent.translate(-2.f, 0.f,0.f);
					drawFlagPole();
				parent.popMatrix();

				parent.pushMatrix();
					parent.translate(-2.f,-3.95f,0.f);
					parent.rotateY(PApplet.radians(180.f));
					parent.rotateY(angle2); // Rotate the flag
					parent.translate(-1.25f,0.f,0.f);
					drawFlag();
				parent.popMatrix();

				parent.pushMatrix();
					parent.translate(4.f,0.f,0.f);
					drawString();
					parent.translate(0.f,4.f,0.f);
					drawTargetFlag();
				parent.popMatrix();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	public void drawFlagPole(){
        float res = (float)Math.PI*0.1f;
        float r = 0.1f;
        float x = r, z = 0.f;
        float t = 0.f;
        do{
            parent.beginShape(PApplet.QUADS);
            if(tex != null) parent.texture(tex);
                parent.vertex(x, 0.f, z,0,0);
                parent.vertex(x, -5.f, z,0,1);
                t += res;
                x = r*(float)Math.cos(t);
                z = r*(float)Math.sin(t);
                parent.vertex(x, -5.f, z,1,1);
                parent.vertex(x, 0.f, z,0,1);
            parent.endShape();
        }while(t <= 2*(float)Math.PI);
        parent.translate(0.f, -5.125f, 0.f);
        parent.fill(235, 235, 235);
        // Create sphere at top of flag pole
        parent.sphere(0.15f);
    }

	private void drawFlag() {
		parent.beginShape(Scene.QUADS);
		parent.fill(255, 255, 23);

			if(tex != null) parent.texture(tex1);
			float x = 1.25f;
			float y = 1.0f;
			float z = 0.025f;
			parent.normal(0.f, 0.f, 1.f);

			parent.vertex(-x, -y, z, 0.f, 1.f);
			parent.vertex(-x, y, z, 0.f, 0.f);
			parent.vertex(x, y, z, 1.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 1.f);

			parent.normal(0.0f, 0.0f, -1.0f);

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);
		parent.endShape();
		parent.texture(null);
	}

	private void drawTargetFlag() {
		parent.beginShape(Scene.QUADS);
		parent.fill(255, 255, 23);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex2);
			float x = 4.0f;
			float y = 4.0f;
			float z = 0.025f;
			parent.vertex(-x, -y, z, 0.f, 1.f);
			parent.vertex(-x, y, z, 0.f, 0.f);
			parent.vertex(x, y, z, 1.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 1.f);

			parent.normal(0.0f, 0.0f, -1.0f);

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);
		parent.endShape();
		parent.texture(null);
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

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);

		parent.endShape();
		parent.texture(null);
	}

	private void drawString() {

		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex);

			float x = 5.f;
			float y = 0.1f;
			float z = 0.1f;

			parent.vertex(-x, -y, z, 0.f, 1.f);
			parent.vertex(-x, y, z, 0.f, 0.f);
			parent.vertex(x, y, z, 1.f, 0.f);
			parent.vertex(x, -y, z, 1.f, 1.f);

			parent.normal(0.0f, 0.0f, -1.0f);

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);

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

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);

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

			parent.vertex(x, -y, -z, 0, 1);
			parent.vertex(x, y, -z, 0, 0);
			parent.vertex(-x, y, -z, 1, 0);
			parent.vertex(-x, -y, -z, 1, 1);

			parent.normal(-1.0f, 0.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(-x, y, z, 1, 0);
			parent.vertex(-x, -y, z, 1, 1);

			parent.normal(1.0f, 0.0f, 0.0f);

			parent.vertex(x, -y, z, 0, 1);
			parent.vertex(x, y, z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, -1.0f, 0.0f);

			parent.vertex(-x, -y, -z, 0, 1);
			parent.vertex(-x, -y, z, 0, 0);
			parent.vertex(x, -y, z, 1, 0);
			parent.vertex(x, -y, -z, 1, 1);

			parent.normal(0.0f, 1.0f, 0.0f);

			parent.vertex(-x, y, z, 0, 1);
			parent.vertex(-x, y, -z, 0, 0);
			parent.vertex(x, y, -z, 1, 0);
			parent.vertex(x, y, z, 1, 1);

		parent.endShape();
		parent.texture(null);
	}
}

