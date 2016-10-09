package code.demos;

import framework.engine.*;
import framework.interfaces.*;
import processing.opengl.*;

public class Triangle extends DisplayableObject implements Animation, Input {

	public Triangle(Scene parent){
		super(parent);
	}
	
	@Override
	public void update(float dT) {
		// animation: rotate around z-axis
		rotation.z += 0.1f*dT;
	}

	@Override
	public void display() {
		// remember the current state of the model-view matrix before we add the triangle transforms too it
		parent.pushMatrix();
			parent.pushStyle(); // remember current style attributes
			parent.noStroke(); // remove current 
			
			// Project from Object Space to World Space
			parent.translate(pos.x,pos.y,pos.z);			// Position
			parent.scale(scale.x,scale.y,scale.z);			// Scale
			parent.rotateY(rotation.y);						// Set orientation (Y - roll)
			parent.rotateZ(rotation.z);						// Set orientation (Z - yaw)
			parent.rotateX(rotation.x);						// Set orientation (X - pitch)
			
			parent.gl.disable(PGL.CULL_FACE);
			
			parent.beginShape(TRIANGLES);
				// set the colour for the first vertex
				parent.fill(255,0,0);
				// set the position of the first vertex
				parent.vertex(-(float)parent.width/3.f,-(float)parent.height/4.f,-2.f);
				// set the colour for the second vertex
				parent.fill(0,255,0);
				// set the position of the second vertex
				parent.vertex((float)parent.width/3.f,-(float)parent.height/4.f,-2.f);
				// set the colour for the third vertex
				parent.fill(0,0,255);
				// set the position of the third vertex
				parent.vertex(0.f,(float)parent.height/3.f,-2.f);
			parent.endShape();
			
			// re-enable face culling for other objects in the scene
			parent.gl.disable(PGL.CULL_FACE);
			// revert back to original style attributes
			parent.popStyle();
		// revert back to the matrix we had before we added the triangle's transformations so that the other objects
			// in the scene are not affected by the triangle's transformations
		parent.popMatrix();
	}
////// Handle ASCII keys ////////////////////////////////////////////
	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		// adjust scale of y-axis based on key presses for 'i' and 'k'
		switch(key){
		case 'i':
			scale.y += 0.1f;
			break;
		case 'k':
			scale.y -= 0.1f;
			break;
		}
	}
////// Unused input handling functions (implemented from interface Input) ///////////////////////// 
	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) { }
	@Override
	public void handleMouse(int button, int state, int mX, int mY) { }
	@Override
	public void handleMouseDrag(int mX, int mY) { }
	@Override
	public void handleMouseMove(int mX, int mY) { }
}
