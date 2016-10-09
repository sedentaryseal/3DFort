/**
 * 
 */
package code.demos.solarsystem;

import framework.engine.Scene;

/**
 * @author wcw
 *
 */
public class Planet2 extends Planet {
	float moonSize;
	float distMoonFromPlanet;
	float moonAxisRotation, moonOrbitRotation;
	float moonAxisSpeed,    moonOrbitSpeed;
	
	/**
	 * @param parent
	 * @param distanceFromSun
	 * @param axisRotationSpeed
	 * @param orbitRotationSpeed
	 */
	public Planet2(Scene parent, float distanceFromSun, float axisRotationSpeed, float orbitRotationSpeed,
			float moonSize, float distMoonFromPlanet, float moonAxisSpeed, float moonOrbitSpeed ) {
		
		super(parent, distanceFromSun, axisRotationSpeed, orbitRotationSpeed);
		this.moonSize           = moonSize;
		this.distMoonFromPlanet = distMoonFromPlanet;
		this.moonAxisSpeed      = moonAxisSpeed;
		this.moonOrbitSpeed     = moonOrbitSpeed;		
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
			
			parent.pushMatrix();
				parent.rotateY(moonOrbitRotation);
				parent.translate(0.f, 0.f, -distMoonFromPlanet);
				parent.rotateY(moonAxisRotation);
				parent.scale(moonSize);
				
				parent.fill(137,137,137);
				parent.sphereDetail(5,5);
				parent.sphere(2);
			parent.popMatrix();
			
			parent.fill(colour);
			parent.noStroke();
			
			parent.sphereDetail(6, 6);
			parent.sphere(2);
		parent.popStyle();
		parent.popMatrix();		
	}
	
	@Override
	public void update(float dT) {
		super.update(dT);
		
		moonAxisRotation += moonAxisSpeed*dT;
		if (moonAxisRotation > Scene.TWO_PI)
			moonAxisRotation -= Scene.TWO_PI;
		
		moonOrbitRotation += moonOrbitSpeed*dT;
		if (moonOrbitRotation > Scene.TWO_PI)
			moonOrbitRotation -= Scene.TWO_PI;		
	}
	
}
