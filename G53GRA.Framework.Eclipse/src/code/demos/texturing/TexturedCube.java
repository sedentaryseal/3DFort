/**
 *
 */
package code.demos.texturing;

import processing.core.PImage;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

/**
 * @author wcw
 *
 */
public class TexturedCube extends DisplayableObject implements Animation {
	PImage tex;
	/**
	 * @param parent
	 */
	public TexturedCube(Scene parent, String filename) {
		super(parent);

		tex = parent.loadImage(filename);
	}

	@Override
	public void update(float dT) {
		rotation.x += (float)dT*Scene.radians(20.f);  // Rotate around x axis at 20degrees per second
		rotation.y += (float)dT*Scene.radians(10.f);  // Rotate around y axis at 10degrees per second
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

			drawCube();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	private void drawCube() {

		parent.beginShape(Scene.QUADS);
			parent.fill(255, 0, 0);
			parent.normal(0.f, 0.f, 1.f);

			if(tex != null) parent.texture(tex); // bind texture to shape

			parent.vertex(-0.5f, -0.5f, 0.5f, 0.f, 1.f);
			parent.vertex(-0.5f, 0.5f, 0.5f, 0.f, 0.f);
			parent.vertex(0.5f, 0.5f, 0.5f, 1.f, 0.f);
			parent.vertex(0.5f, -0.5f, 0.5f, 1.f, 1.f);

			// draw the back face

			// set the colour of the front face
			parent.fill(0, 255, 0);

			// set the normal of the front face
			parent.normal(0.0f, 0.0f, -1.0f);

			// define texture coordinates for the 4 vertices
			parent.vertex(0.5f, -0.5f, -0.5f, 0.f, 1.f);
			parent.vertex(0.5f, 0.5f, -0.5f, 0.f, 0.f);
			parent.vertex(-0.5f, 0.5f, -0.5f, 1.f, 0.f);
			parent.vertex(-0.5f, -0.5f, -0.5f, 1.f, 1.f);
			// draw the left face

			// set the colour of the front face
			parent.fill(0, 0, 255);

			// set the normal of the front face
			parent.normal(-1.0f, 0.0f, 0.0f);

			// define texture coordinates for the 4 vertices
			parent.vertex(-0.5f, -0.5f, -0.5f, 0.f, 1.f);
			parent.vertex(-0.5f, 0.5f, -0.5f, 0.f, 0.f);
			parent.vertex(-0.5f, 0.5f, 0.5f, 1.f, 0.f);
			parent.vertex(-0.5f, -0.5f, 0.5f, 1.f, 1.f);

			// draw the right face

			// set the colour of the front face
			parent.fill(0, 255, 255);

			// set the normal of the front face
			parent.normal(1.0f, 0.0f, 0.0f);

			// define texture coordinates for the 4 vertices
			parent.vertex(0.5f, -0.5f, 0.5f, 0.f, 1.f);
			parent.vertex(0.5f, 0.5f, 0.5f, 0.f, 0.f);
			parent.vertex(0.5f, 0.5f, -0.5f, 1.f, 0.f);
			parent.vertex(0.5f, -0.5f, -0.5f, 1.f, 1.f);

			// draw the top face

			// set the colour of the front face
			parent.fill(255, 255, 0);

			// set the normal of the front face
			parent.normal(0.0f, -1.0f, 0.0f);

			// define texture coordinates for the 4 vertices
			parent.vertex(-0.5f, -0.5f, -0.5f, 0.f, 1.f);
			parent.vertex(-0.5f, -0.5f, 0.5f, 0.f, 0.f);
			parent.vertex(0.5f, -0.5f, 0.5f, 1.f, 0.f);
			parent.vertex(0.5f, -0.5f, -0.5f, 1.f, 1.f);

			// draw the bottom face

			// set the colour of the front face
			parent.fill(255, 0.0f, 255);

			// set the normal of the front face
			parent.normal(0.0f, 1.0f, 0.0f);

			// define texture coordinates for the 4 vertices
			parent.vertex(-0.5f, 0.5f, 0.5f, 0.f, 1.f);
			parent.vertex(-0.5f, 0.5f, -0.5f, 0.f, 0.f);
			parent.vertex(0.5f, 0.5f, -0.5f, 1.f, 0.f);
			parent.vertex(0.5f, 0.5f, 0.5f, 1.f, 1.f);

		parent.endShape();
		parent.texture(null);
	}

}
