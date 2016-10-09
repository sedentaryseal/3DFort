/**
 *
 */
package code.demos.solarsystem;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

/**
 * @author wcw
 *
 */
public class Planet extends DisplayableObject implements Animation {
	float distanceFromSun;
	float axisRotation,      orbitRotation;
	float axisRotationSpeed, orbitRotationSpeed;
	int colour;

	/**
	 * @param parent
	 */
	public Planet(Scene parent, float distanceFromSun, float axisRotationSpeed, float orbitRotationSpeed) {
		super(parent);

		this.distanceFromSun    = distanceFromSun;
		this.axisRotationSpeed  = axisRotationSpeed;
		this.orbitRotationSpeed = orbitRotationSpeed;
	}

	@Override
	public void update(float dT) {

		axisRotation += axisRotationSpeed*dT;
		if (axisRotation > Scene.TWO_PI)
			axisRotation -= Scene.TWO_PI;

		orbitRotation += orbitRotationSpeed*dT;
		if (orbitRotation > Scene.TWO_PI)
			axisRotation -= Scene.TWO_PI;
	}

	@Override
	public void display() {
		parent.pushMatrix();
		parent.pushStyle();
			parent.translate(pos.x,pos.y,pos.z);
			parent.scale(scale.x,scale.y,scale.z);			// Scale

			parent.rotateY(orbitRotation);
			parent.translate(0.f, 0.f, -distanceFromSun);
			parent.rotateY(axisRotation);

			parent.fill(colour);
			parent.noStroke();

			parent.sphereDetail(6, 6);
			parent.sphere(2);

		parent.popStyle();
		parent.popMatrix();
	}

	public void setDistanceFromSun(float d) { distanceFromSun = d; }

	public void setColour(int r, int g, int b) { colour = parent.color(r,g,b); }

}
