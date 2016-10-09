/**
 * 
 */
package code.demos.animatedlamp;

import processing.core.PShape;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

/**
 * @author wcw
 *
 */
public class Lamp extends DisplayableObject implements Animation {
	int keyframe             = -1; 
	float animateTime        = 0.f;
	float animateRotation    = 0.f;
	float animateTranslation = 0.f;
	float interpA, interpB, interpTime;
	
	PShape headmesh;
	
	final float g = 9.8f;
	
	public Lamp(Scene parent) {
		super(parent);
		interpA = interpB = interpTime = 0;
		headmesh = parent.loadShape("../code/demos/animatedlamp/LampMesh.obj");
	}

	@Override
	public void update(float dT) {
		animateTime     += dT;
		animateRotation += dT;
		
		if (animateTime >= 3.f) {
			animateTime =  0.f;
			keyframe    = -1;
		}
		
		if (animateTime < 1.f) {
			if (keyframe != 0) {
				animateTime        = 0.f;
				animateRotation    = 0.f;
				animateTranslation = 0.f;
				keyframe           = 0;
				interpA            = -45.f;
				interpB            = -45.f;
				interpTime         = 1.f;
			}
		} else if (animateTime < 1.25) {
			if (keyframe != 1) {
				keyframe        = 1;
				animateRotation = 0.f;
				interpA         = -60.f;
				interpB         = -15.f;
				interpTime      = 0.25f;
			}		
			animateTranslation = 4.f*g*(animateTime - 1.f) + 0.5f*(animateTime - 1.f)*(animateTime - 1.f)*4*-g;
		} else {
			if (keyframe != 2) {
				keyframe        = 2;
				animateRotation = 0.f;
				interpA         = -15.f;
				interpB         = -45.f;
				interpTime      = 1.75f;
			}
			animateTranslation = 4.f*g*(animateTime - 1.f) + 0.5f*(animateTime - 1.f)*(animateTime - 1.f)*4*-g;
		}
	}

	@Override
	public void display() {
		parent.pushMatrix();
		parent.pushStyle();
			parent.fill(250);
			parent.ambient(250);
			parent.specular(0,0,0);
			parent.noStroke();
		
			parent.translate(pos.x,pos.y,pos.z);
			parent.scale(scale.x,scale.y,scale.z);
			
			parent.translate(0.f, 25.f - animateTranslation, 0.f);
			drawBase();
			
			parent.rotateZ(Scene.radians(interpA + animateRotation*((interpB-interpA)/interpTime)));
			parent.translate(0.f,-8.f,0.f);
			drawArm();
			
			parent.translate(0.f,-7.f,0.f);
			parent.rotateZ(Scene.radians(2.f*(-interpA - animateRotation*((interpB-interpA)/interpTime))));
			parent.translate(0.f,-7.f,0.f);
			drawArm();
			
			parent.translate(0.f,-5.f,0.f);
			parent.rotateY(Scene.radians(25.f));
			drawHead();			
			
		parent.popMatrix();
		parent.popStyle();		
	}
	
	public void drawBase() {
		parent.pushMatrix();
			parent.scale(10.f,1.f,10.f);
			drawCube();
		parent.popMatrix();
	}
	
	public void drawArm() {
		parent.pushMatrix();
			parent.scale(2.f,15.f,2.f);
			drawCube();
		parent.popMatrix();
	}
	
	public void drawHead() {
		parent.pushMatrix();
			parent.scale(-8.f);
			parent.rotateY(Scene.PI);
			parent.shape(headmesh,0,0);
		parent.popMatrix();
	}
	
	public void drawCube() {
		float r = 0.5f;
		parent.beginShape(Scene.QUADS);
		// Near face
			parent.normal(0.f,0.f,1.f);
		    parent.vertex(-r,-r,r);
		    parent.vertex(-r,r,r);
		    parent.vertex(r,r,r);
		    parent.vertex(r,-r,r);
		// Right face
			parent.normal(1.f,0.f,0.f);
		    parent.vertex(r,-r,r);
		    parent.vertex(r,r,r);
		    parent.vertex(r,r,-r);
		    parent.vertex(r,-r,-r);
		// Back face
			parent.normal(0.f,0.f,-1.f);
		    parent.vertex(-r,r,-r);
		    parent.vertex(-r,-r,-r);
		    parent.vertex(r,-r,-r);
		    parent.vertex(r,r,-r);
		// Left face
			parent.normal(-1.f,0.f,0.f);
		    parent.vertex(-r,-r,-r);
		    parent.vertex(-r,r,-r);
		    parent.vertex(-r,r,r);
		    parent.vertex(-r,-r,r);
		// Top face
			parent.normal(0.f,-1.f,0.f);
		    parent.vertex(-r,-r,-r);
		    parent.vertex(-r,-r,r);
		    parent.vertex(r,-r,r);
		    parent.vertex(r,-r,-r);
		// Bottom face
			parent.normal(0.f,1.f,0.f);
		    parent.vertex(-r,r,r);
		    parent.vertex(-r,r,-r);
		    parent.vertex(r,r,-r);
		    parent.vertex(r,r,r);		
		parent.endShape();
	}
}

