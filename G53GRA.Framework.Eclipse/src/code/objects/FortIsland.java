package code.objects;

import processing.core.PApplet;
import processing.core.PImage;

import framework.engine.DisplayableObject;
import framework.engine.Scene;

public class FortIsland extends DisplayableObject {
	PImage tex;

	public FortIsland(Scene parent, String filename) {
		super(parent);
		tex = parent.loadImage(filename);
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

			parent.pushMatrix();
				parent.fill(76,70,50);
				parent.translate(0.f, -0.01f,0.f); // Lift island slightly off the floor
				parent.rotateX(PApplet.radians(90.f));
				drawIsland();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	// Creating the circle with a triangle fan
	private void drawIsland(){
		parent.ambient(45,45,45);
		parent.specular(100, 100, 100);
		float x;
		float y;
		float x1;
		float y1;
		float a;
		double r = 4.0;
		x = 0.5f;
		y = 0.6f;
		parent.beginShape(PApplet.TRIANGLE_FAN);
		if(tex != null) parent.texture(tex);
		parent.vertex(x,y,0,0);
		for (a = 1.0f; a < 8.0f; a += 0.2)
		{
		    x1 = x+PApplet.sin(a)*(float)r;
		    y1 = y+PApplet.cos(a)*(float)r;
		    parent.vertex(x1,y1,1,0);
		}
		parent.normal(1.0f, 0.0f, 0.0f);
		parent.endShape();
	}
}

