/**
 * 
 */
package code.demos.lighting;

import processing.core.PShape;
import processing.opengl.PGL;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

/**
 * @author Wil
 *
 */
public class Teapot extends DisplayableObject implements Animation {

	PShape  teapot;
	
	/**
	 * @param parent
	 */
	public Teapot(Scene parent) {
		super(parent);
		
		teapot = parent.loadShape("./code/demos/lighting/teapot.obj");
		System.out.println("Teapot loaded: " + teapot.getTessellation().getVertexCount() + " vertices");
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Animation#update(float)
	 */
	@Override
	public void update(float dT) {
		rotation.y += (Scene.PI/18.f)*dT; // Update rotation around y-axis over time
		
		if (rotation.y > Scene.TWO_PI)	  // If greater than full circle, start from 0 again
			rotation.y -= Scene.TWO_PI;   //     (to prevent eventual overflow)
	}

	/* (non-Javadoc)
	 * @see framework.engine.DisplayableObject#display()
	 */
	@Override
	public void display() {
		if (teapot == null) return; // Cannot display teapot, load was unsuccessful
		
		parent.pushMatrix();
		parent.pushStyle();
		
			parent.translate(pos.x,pos.y,pos.z);
			parent.scale(scale.x,scale.y,scale.z);
			
//			parent.noStroke();
			
			// Set material properties
			teapot.setAmbient(parent.color(128,128,230)); // Ambient material property K_a (set to blue)
			teapot.setFill(parent.color(230,128,128));    // Diffuse material property K_d (set to red)
			teapot.setSpecular(255); // Specular material property K_s (set to white)
			teapot.setShininess(100.f);      // Shininess/specular exponent factor n
		
			
			parent.pushMatrix();
				parent.rotateY(rotation.y);
				parent.shape(teapot);
			parent.popMatrix();
			
		parent.popStyle();
		parent.popMatrix();
	}
}
