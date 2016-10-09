/**
 * 
 */
package code.demos.viewing;

import framework.engine.DisplayableObject;
import framework.engine.Scene;

/**
 * @author wil
 *
 */
public class Floor extends DisplayableObject {

	/**
	 * Floor constructor (calls super(parent))
	 * @param parent
	 */
	public Floor(Scene parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see framework.engine.DisplayableObject#display()
	 */
	@Override
	public void display() {
		// Draws an exciting chequered floor
		parent.pushMatrix();
		parent.pushStyle();
			parent.noStroke();
			//parent.shininess(128);
		//	parent.specular(255,255,255);
			parent.beginShape(Scene.QUADS);
				for (int i = -10; i < 10; i++) {
					for (int j = -10; j < 10; j++) {
						if (i % 2 == 0) { // i is even
							if (j % 2 == 0) { // j is even
								parent.fill(0,0,0);	// black
							} else { // j odd
								parent.fill(255,255,255); // white
							}
						} else { // i odd
							if (j % 2 == 0) { // j is even
								parent.fill(255,255,255); // white
							} else { // j odd
								parent.fill(0,0,0);	// black
							}
						}
						
						parent.vertex(scale.x*(float)i + scale.x, pos.y, scale.z*(float)j + scale.z);
						parent.vertex(scale.x*(float)i + scale.x, pos.y, scale.z*(float)j);
						parent.vertex(scale.x*(float)i, pos.y, scale.z*(float)j);
						parent.vertex(scale.x*(float)i, pos.y, scale.z*(float)j + scale.z);
					}
					
				}
			parent.endShape();		
		parent.popStyle();
		parent.popMatrix();

	}

}
