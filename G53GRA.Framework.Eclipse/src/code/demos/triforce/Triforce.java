/**
 * 
 */
package code.demos.triforce;

import processing.opengl.PGL;
import framework.engine.*;
import framework.interfaces.Animation;

/**
 * @author wil
 *
 */
public class Triforce extends DisplayableObject implements Animation {

	/**
	 * @param parent
	 */
	public Triforce(Scene parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		parent.pushMatrix();
			parent.pushStyle();
			parent.noStroke();
			
			parent.translate(pos.x,pos.y,pos.z);			// Position
			parent.scale(scale.x,scale.y,scale.z);			// Scale
			parent.rotateY(rotation.y);						// Set orientation (Y - roll)
			parent.rotateZ(rotation.z);						// Set orientation (Z - yaw)
			parent.rotateX(rotation.x);						// Set orientation (X - pitch)
			

			//parent.gl.disable(PGL.CULL_FACE);
			//parent.fill(127,127,0);
			//drawBasicTriforce();
			
			parent.fill(0,0,255);
			draw3DTriangle(-1.f,1.f,0.f);
			
			parent.fill(0,255,0);
			draw3DTriangle(1.f,1.f,0.f);
			
			parent.fill(255,0,0);
			draw3DTriangle(0.f,-1.f,0.f);
			
			parent.popStyle();
		parent.popMatrix();
	}

	@Override
	public void update(float dT) {
		// A simple update function just so we can see what we're doing
		rotation.x += Scene.radians(5.f*dT);
		rotation.y += Scene.radians(12.f*dT);
		rotation.z += Scene.radians(14.f*dT);
	}
	
	private void drawBasicTriforce() {

		// Draw the Triforce in Object Space Coordinates
		/*
		Triangle coodinate: O is the origin/center of object space
		Polygons must be wond anti-clockwise so they are not culled

		     A
		   /   \
		  B -O- C
		 /  \ /  \
		D--- E ---F
		*/
		
		// Enter triangle primitives
		parent.beginShape(Scene.TRIANGLES);

		// Draw top center face
		parent.vertex(0.f, -2.f, 0.f);    // A
		parent.vertex(-1.f, 0.f, 0.f);    // B
		parent.vertex(1.f, 0.f, 0.f);    // C

		// Draw bottom left face
		parent.vertex(-1.f, 0.f, 0.f);    // B
		parent.vertex(-2.f, 2.f, -0.f);    // D
		parent.vertex(0.f, 2.f, 0.f);    // E

		// Draw bottom right face
		parent.vertex(1.f, 0.f, 0.f);    // C
		parent.vertex(0.f, 2.f, 0.f);    // E
		parent.vertex(2.f, 2.f, 0.f);    // F

		// Stop triangle primitives
		parent.endShape();
	}
	
	private void draw3DTriangle(float cx, float cy, float cz) {
		// Draw a section of the Triforce in Object Space Coordinates
		/*
		Triangle coodinate: c' is the center of triangle (cx, cy, cz)
		Polygons must be wound anti-clockwise so they are not culled

		   A---------B
		  / \       / \
		 /   \  c' /   \
		C-----D---E-----F
		*/

		// Enter triangle primitives
		parent.beginShape(Scene.TRIANGLES);

		// Draw the front triangle
		// Draw the front face wound anti-clockwise
		parent.vertex(cx, cy - 1.f, cz + 0.5f);    // A
		parent.vertex(cx - 1.f, cy + 1.f, cz + 0.5f); // C
		parent.vertex(cx + 1.f, cy + 1.f, cz + 0.5f); // D

		// Draw the back triangle
		// Draw the back face wond clockwise
		parent.vertex(cx, cy - 1.f, cz - 0.5f);    // B
		parent.vertex(cx + 1.f, cy + 1.f, cz - 0.5f); // F
		parent.vertex(cx - 1.f, cy + 1.f, cz - 0.5f); // E

		// Stop triangle primitive mode
		parent.endShape();

		// Enter quads primitives
		parent.beginShape(Scene.QUADS);

		// Draw the bottom plane
		// Draw the bottom plane wound anti-clockwise
		parent.vertex(cx - 1.f, cy + 1.f, cz + 0.5f); // C
		parent.vertex(cx - 1.f, cy + 1.f, cz - 0.5f); // E
		parent.vertex(cx + 1.f, cy + 1.f, cz - 0.5f); // F
		parent.vertex(cx + 1.f, cy + 1.f, cz + 0.5f); // D

		// Draw the left plane wound anti-clockwise
		parent.vertex(cx, cy - 1.f, cz - 0.5f);     // B
		parent.vertex(cx - 1.f, cy + 1.f, cz - 0.5f); // E
		parent.vertex(cx - 1.f, cy + 1.f, cz + 0.5f); // C
		parent.vertex(cx, cy - 1.f, cz + 0.5f);     // A

		// Draw the right plane wound anti-clockwise
		parent.vertex(cx, cy - 1.f, cz + 0.5f);     // A
		parent.vertex(cx + 1.f, cy + 1.f, cz + 0.5f); // D
		parent.vertex(cx + 1.f, cy + 1.f, cz - 0.5f); // F
		parent.vertex(cx, cy - 1.f, cz - 0.5f);     // B

		parent.endShape();
	}

}
