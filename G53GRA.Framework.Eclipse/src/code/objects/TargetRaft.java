package code.objects;

import processing.core.PApplet;
import processing.core.PImage;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

public class TargetRaft extends DisplayableObject implements Animation {
	PImage tex;
	PImage tex1;
	PImage tex2;

	float pi = PApplet.PI;
    float angle = pi/2.f;
    float aS = 0.f, aT = 0.f;
    float animationTime = 6.f;
    float xAmount = 0.f;
    float zAmount = 0.f;

	public TargetRaft(Scene parent, String filename, String filename1, String filename2) {
		super(parent);
		tex = parent.loadImage(filename);
		tex1 = parent.loadImage(filename1);
		tex2 = parent.loadImage(filename2);
	}

	@Override
	public void update(float dT) {
		// Moving the raft
		xAmount = xAmount + 0.04f;

		// If raft goes off edge of sky box, reset position to the other side
	    if (xAmount > 70.f){
	    	xAmount = -30.f;
	    	xAmount = xAmount + 0.04f;
	    }

	    // Flag rotation stages
	    aT = (aT + dT) % animationTime;
	    aS = 14.f*aT/animationTime;
	    if (aS < 1 || aS > 13){
	    	angle += (float)dT*Scene.radians(20.f);
	    }else if (aS < 2 || aS > 12){
	    	angle += (float)dT*Scene.radians(20.f);
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

			parent.translate(0.0f, -5.0f,0.0f);
			parent.translate(2.0f, 4.99f,2.0f);
			parent.translate(-8.f,0.f,-8.f);
			parent.translate(-15.f,0.f,-20.f);

			// Translation to move the raft
			parent.translate(xAmount,0.f,zAmount);

			// Drawing the next 12 longer planks, rotating them by 180 degrees each time
			// to change the wood texture direction
			parent.pushMatrix();
				for (int i=0; i < 12; i++){
					parent.rotateZ(PApplet.radians(180.f));
					parent.translate(0.f,0.f,-0.5f);
					drawPlank(2);
				}
			parent.popMatrix();

			parent.translate(-2.f, 0.f,-3.f);
			parent.pushMatrix();
				drawFlagPole();
			parent.popMatrix();

			parent.pushMatrix();
				parent.translate(0.f,-4.0f,0.f);
				parent.rotateY(angle); // Rotate the flag
				parent.translate(-1.25f,0.f,0.f);
				drawFlag();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	private void drawPlank(int length) {
		// This method takes in an int, lengthOfPlank which will create planks of varying lengths
		parent.ambient(255,255,255);
		parent.specular(100, 100, 100);
		float x = 0.f;
		float y = 0.f;
		float z = 0.f;

		if (length == 1){
			x = 3.f;
			y = 0.25f;
			z = 0.25f;
		} else if (length == 2){
			x = 4.f;
			y = 0.25f;
			z = 0.25f;
		}
		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex2);
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

	public void drawFlagPole(){
		parent.ambient(255,255,255);
		parent.specular(0, 0, 0);

        float res = (float)Math.PI*0.1f;
        float r = 0.1f;
        float x = r;
        float z = 0.f;
        float t = 0.f;

        do{
            parent.beginShape(Scene.QUADS);
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
        parent.sphere(0.15f);
    }

	private void drawFlag() {
		parent.ambient(255,255,255);
		parent.specular(0, 0, 0);

		parent.beginShape(Scene.QUADS);
		parent.fill(255, 255, 23);
			parent.normal(0.f, 0.f, 1.f);
			if(tex != null) parent.texture(tex1);
			float x = 1.25f;
			float y = 1.0f;
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
}