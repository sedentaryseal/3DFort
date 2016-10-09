/**
 *
 */
package code.demos.lighting;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;
import framework.interfaces.Input;
import framework.interfaces.Lighting;

/**
 * @author Wil
 *
 */
public class MultiLight extends DisplayableObject implements Animation, Input, Lighting {
	float position0[], ambient0[], diffuse0[], specular0[];
	float position1[], ambient1[], diffuse1[], specular1[];
	float position2[], ambient2[], diffuse2[], specular2[];
	float position3[], ambient3[], diffuse3[], specular3[];

	boolean show0, show1, show2, show3;
	boolean positional0, positional1, positional2, positional3;

	float t;

	final float radius = 400.f;
	/**
	 * @param parent
	 */
	public MultiLight(Scene parent) {
		super(parent);
/*==============================================================================================================
 * LIGHT 0 PARAMETERS (yellow directional light)
 *==============================================================================================================*/
		position0 = new float[3];
		ambient0 = new float[3];
		diffuse0 = new float[3];
		specular0 = new float[3];

		ambient0[0] = 128.f;  // Set the ambient colour of light 0 to dull yellow
		ambient0[1] = 128.f;
		ambient0[2] = 0.f;

		diffuse0[0] = 128.f;   // Set the diffuse colour of light 0 to pale yellow
		diffuse0[1] = 128.f;
		diffuse0[2] = 0.f;

		specular0[0] = 0.f;  // Set no specular colour on light 0
		specular0[1] = 0.f;
		specular0[2] = 0.f;

		position0[0] = 1.f;   // Set the direction of light 0
		position0[1] = -1.f;
		position0[2] = 1.f;

		positional0 = false;

/*==============================================================================================================
* LIGHT 1 PARAMETERS (red positional light)
*==============================================================================================================*/
		position1 = new float[3];
		ambient1 = new float[3];
		diffuse1 = new float[3];
		specular1 = new float[3];

		ambient1[0] = 38.f;  // Set the ambient colour of light 1 to pale red
		ambient1[1] = 0.f;
		ambient1[2] = 0.f;

		diffuse1[0] = 255.f;   // Set the diffuse colour of light 1 to red
		diffuse1[1] = 0.f;
		diffuse1[2] = 0.f;

		specular1[0] = 255.f;  // Set the specular colour of light 1 to red
		specular1[1] = 0.f;
		specular1[2] = 0.f;

		position1[0] = (float)radius; // Set the position of light 1
		position1[1] = 0.f;
		position1[2] = 0.f;

		positional1 = true;    // Mark light 1 as a positional light source

/*==============================================================================================================
 * LIGHT2 PARAMETERS (green positional light)
 *==============================================================================================================*/
		position2 = new float[3];
		ambient2 = new float[3];
		diffuse2 = new float[3];
		specular2 = new float[3];

		ambient2[0] = 0.f;  // Set the ambient colour of light 2 to pale green
		ambient2[1] = 38.f;
		ambient2[2] = 0.f;

		diffuse2[0] = 0.f;   // Set the diffuse colour of light 2 to green
		diffuse2[1] = 204.f;
		diffuse2[2] = 0.f;

		specular2[0] = 0.f;  // Set the specular colour of light 2 to green
		specular2[1] = 255.f;
		specular2[2] = 0.f;

		position2[0] = -(float)radius;  // Set the position of light 2
		position2[1] = 0.f;
		position2[2] = 0.f;

		positional2 = true;      // Mark light 2 as a positional light source

/*==============================================================================================================
 * LIGHT3 PARAMETERS (blue positional light)
 *==============================================================================================================*/
		position3 = new float[3];
		ambient3 = new float[3];
		diffuse3 = new float[3];
		specular3 = new float[3];

		ambient3[0] = 0.f;  // Set the ambient colour of light 3 to light blue
		ambient3[1] = 0.f;
		ambient3[2] = 64.f;

		diffuse3[0] = 0.f;  // Set the diffuse color of light 3 to blue
		diffuse3[1] = 0.f;
		diffuse3[2] = 240.f;

		specular3[0] = 0.f;  // Set the specular color of light 3 to blue
		specular3[1] = 0.f;
		specular3[2] = 255.f;

		position3[0] = (float)radius;  // Set the position of light 3
		position3[1] = 0.f;
		position3[2] = 0.f;

		positional3 = true;     // Mark light 3 as a positional light source

		t = 0;
		show0 = show1 = show2 = show3 = true;
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Lighting#setupLighting()
	 */
	@Override
	public void setupLighting() {
		// Sets up lighting (called whenever lights is setup)

/*==============================================================================================================
 * LIGHT 0 SETUP (yellow directional light)
 *==============================================================================================================*/
		if(show0){
			/* Sets the specular property of lights created
			 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
			 * so they must be reset at the end of the call.
			 */
			parent.lightSpecular(specular0[0],specular0[1],specular0[2]);
			// Create ambient light with ambient colour (position optional)
			parent.ambientLight(ambient0[0],ambient0[1],ambient0[2]);
			// Directional light
			parent.directionalLight(diffuse0[0],diffuse0[1],diffuse0[2],
					                -position0[0],-position0[2],-position0[2]);
		}

/*==============================================================================================================
 * LIGHT 1 SETUP (red positional light)
 *==============================================================================================================*/
		if(show1){
			/* Sets the specular property of lights created
			 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
			 * so they must be reset at the end of the call.
			 */
			parent.lightSpecular(specular1[0],specular1[1],specular1[2]);
			// Create ambient light with ambient colour (position optional)
			parent.ambientLight(ambient1[0],ambient1[1],ambient1[2]);
			 //Positional light
			parent.pointLight(diffuse1[0],diffuse1[1],diffuse1[2],
					               -position1[0],-position1[1],-position1[2]);

		}

/*==============================================================================================================
 * LIGHT 2 SETUP (green positional light)
 *==============================================================================================================*/
		if(show2){
			/* Sets the specular property of lights created
			 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
			 * so they must be reset at the end of the call.
			 */
			parent.lightSpecular(specular2[0],specular2[1],specular2[2]);
			// Create ambient light with ambient colour (position optional)
			parent.ambientLight(ambient2[0],ambient2[1],ambient2[2]);
			// Positional light
			parent.pointLight(diffuse2[0],diffuse2[1],diffuse2[2],
					               -position2[0],-position2[1],-position2[2]);
		}

/*==============================================================================================================
 * LIGHT 3 SETUP (blue positional light)
 *==============================================================================================================*/
		if(show3){
			/* Sets the specular property of lights created
			 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
			 * so they must be reset at the end of the call.
			 */
			parent.lightSpecular(specular3[0],specular3[1],specular3[2]);
			// Create ambient light with ambient colour (position optional)
			parent.ambientLight(ambient3[0],ambient3[1],ambient3[2]);
			// Positional light
			parent.pointLight(diffuse3[0],diffuse3[1],diffuse3[2],
					               -position3[0],-position3[1],-position3[2]);
		}

		parent.lightSpecular(0,0,0); // Reset specular light property!
	}


	/* (non-Javadoc)
	 * @see framework.interfaces.Animation#update(float)
	 */
	@Override
	public void update(float dT) {
		t +=dT;

		position1[0] = radius*Scene.cos(t - 1.5f);
		position1[1] = radius*Scene.sin(t - 1.5f);
		position2[0] = -radius*Scene.cos(t);
		position2[1] = radius*Scene.sin(t);
		position3[0] = radius*Scene.cos(t);
		position3[1] = -radius*Scene.sin(t);

	}

	/* (non-Javadoc)
	 * @see framework.engine.DisplayableObject#display()
	 */
	@Override
	public void display() {
	// Some basic code to draw the position and direction of the light,
	// Note: we cannot draw the directional light source, as it has no position, just direction.

	// Disable lighting on this geometry
		parent.noLights();
		parent.pushStyle();
		parent.sphereDetail(10,10);
		parent.noStroke();

		if (show1){
			parent.pushMatrix();
				parent.fill(diffuse1[0],diffuse1[1],diffuse1[2]);
				parent.translate(position1[0],position1[1],position1[2]);
				parent.sphere(10);
			parent.popMatrix();
		}

		if (show2){
			parent.pushMatrix();
				parent.fill(diffuse2[0],diffuse2[1],diffuse2[2]);
				parent.translate(position2[0],position2[1],position2[2]);
				parent.sphere(10);
			parent.popMatrix();
		}

		if (show3){
			parent.pushMatrix();
				parent.fill(diffuse3[0],diffuse3[1],diffuse3[2]);
				parent.translate(position3[0],position3[1],position3[2]);
				parent.sphere(10);
			parent.popMatrix();
		}

		parent.popStyle();
		parent.lights();
	}

	/* (non-Javadoc)
	 * @see framework.interfaces.Input#handleKey(char, int, int, int)
	 */
	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if (state != 1) return; // if key not pressed (i.e. ignore key release)

		switch (key)
		{
			case '0':
				show0 = show1 = show2 = show3 = false;
				break;
			case '1':
				show0 = !show0;
				break;
			case '2':
				show1 = !show1;
				break;
			case '3':
				show2 = !show2;
				break;
			case '4':
				show3 = !show3;
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
