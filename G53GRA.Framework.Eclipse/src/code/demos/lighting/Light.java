/**
 *
 */
package code.demos.lighting;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;
import framework.interfaces.Lighting;
import processing.core.PApplet;

/**
 * @author Wil
 *
 */
public class Light extends DisplayableObject implements Animation, Lighting {
	/* Lighting properties */
	float ambient[]  = new float[3];
	float diffuse[]  = new float[3];
	float specular[] = new float[3];
	boolean positional;
	/* Maintain runtime */
	float t;
	/* Light rotation radius */
	final float radius = 400.f;
	/**
	 * @param parent
	 */
	public Light(Scene parent) {
		super(parent);
//		ambient[0] = 38.f;
//		ambient[1] = 38.f;
//		ambient[2] = 26.f;
//
//		diffuse[0] = 204.f;
//		diffuse[1] = 0;
//		diffuse[2] = 0;
//
//		specular[0] = 255.f;
//		specular[1] = 255.f;
//		specular[2] = 255.f;

		ambient[0] = 255.f;
		ambient[1] = 255.f;
		ambient[2] = 255.f;

		diffuse[0] = 204.f;
		diffuse[1] = 101;
		diffuse[2] = 100;

		specular[0] = 0.f;
		specular[1] = 0.f;
		specular[2] = 0.f;

		positional = true;
		t = 0.f;
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Lighting#setupLighting()
	 */
	@Override
	public void setupLighting() {
		// Sets up lighting (called whenever lights is setup)

		//parent.lightFalloff(100,100,100);
		parent.lightFalloff(1.0f, 0.001f, 0.0f);
		/* Sets the specular property of lights created
		 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
		 * so they must be reset at the end of the call.
		 */
		parent.lightSpecular(specular[0],specular[1],specular[2]);

		// Create ambient light with ambient colour (position optional)
		parent.ambientLight(ambient[0],ambient[1],ambient[2],-pos.x,-pos.y,-pos.z);

		// Create positional or directional light
		if(positional)
			parent.pointLight(diffuse[0],diffuse[1],diffuse[2],-pos.x,-pos.y,-pos.z);
		else
			parent.directionalLight(diffuse[0],diffuse[1],diffuse[2],-pos.x,-pos.y,-pos.z);


				//float k0, float k1, float k2)
		parent.lightSpecular(0,0,0); // Reset specular light property!
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Animation#update(float)
	 */
	@Override
	public void update(float dT) {
		//t += dT;
		//pos.x = radius*Scene.cos(t);
		//pos.y = radius*Scene.sin(t);
	}

	/* (non-Javadoc)
	 * @see framework.engine.DisplayableObject#display()
	 */
	@Override
	public void display() {
		// TEST
//		parent.rotateX(PApplet.radians(-90.f));
		// END TEST

		// Some basic code to draw the position and direction of the light
		// Not suitable for a directional light source, as directional light has no positon.
		if (positional){
			// Disable lighting effects on this geometry
			parent.noLights();
				parent.pushMatrix();
				parent.pushStyle();
				// Style light source to match diffuse colour
					parent.fill((int)diffuse[0],(int)diffuse[1],(int)diffuse[2]);
					parent.noStroke();
					parent.translate(pos.x,pos.y,pos.z); // positon
					// Create sphere to represent light source
					parent.rotateX(PApplet.radians(-108.f)); // MY CODE
					parent.sphereDetail(10, 10);
					parent.sphere(10);
					// Create line indicating lighting direction
					parent.beginShape(Scene.LINES);
						parent.stroke((int)diffuse[0],(int)diffuse[1],(int)diffuse[2]);
						parent.vertex(0.f,0.f,0.f);
						parent.vertex(0.f,1000.f,0.f);

						//parent.vertex(-pos.x,-pos.y,-pos.z);
						//parent.vertex(-pos.x,-pos.y,-pos.z-100.f);
					parent.endShape();
				parent.popStyle();
				parent.popMatrix();
			parent.lights(); // !IMPORTANT! Renable lighting after this
		}
	}

}
