package code.demos.texturing;

import processing.core.PImage;
import framework.engine.*;
import framework.interfaces.*;

public class Link extends DisplayableObject implements Animation, Input {
	PImage tex;
	float width, height;
	
	float texCoords[];
	int spriteWidth;
	int spriteFrame;
	
	boolean runAnimate;
	double time;
	
	float dx;
	
	public Link(Scene parent, float width, float height, String filename) {
		super(parent);
		
		this.width  = width;
		this.height = height;
		
		tex = parent.loadImage(filename);
		texCoords   = new float[8];
		time        = 0.0;
		
		runAnimate  = true;
		dx          = 0.1f;
		
		spriteFrame = 0;
		spriteWidth = 16;		
	}

	@Override
	public void display() {
		parent.pushStyle();
		parent.noStroke();
		parent.pushMatrix();
		{
			parent.translate(pos.x, pos.y, pos.z);
			parent.scale(scale.x, scale.y, scale.z);
			parent.beginShape(Scene.QUADS);
			{
				if(tex != null) parent.texture(tex);
				
				parent.fill(255);
				parent.normal(0.f,0.f,0.f);
				
				parent.vertex(-width / 2.f, -height, 0.f, texCoords[0], texCoords[1]);    // Vertex coordinate of the top left of the quad
				parent.vertex(-width / 2.f, 0.f, 0.f, texCoords[2], texCoords[3]);       // Vertex coordinate of the bottom left of the quad
				parent.vertex(width / 2.f, 0.f, 0.f, texCoords[4], texCoords[5]);        // Vertex coordinate of the bottom right of the quad			
				parent.vertex(width / 2.f, -height, 0.f, texCoords[6], texCoords[7]);     // Vertex coordinate of the top right of the quad
			}			
			parent.endShape();
		}
		parent.popMatrix();		
		parent.popStyle();
		
		parent.texture(null);
	}

	@Override
	public void update(float dT) {
		float sCoord;
		time += dT;
		
		if (runAnimate) {
			pos.x += scale.x*dx;
			if (pos.x > scale.x*20.f || pos.x < -scale.x*20.f)
				dx = -dx;
			
			if (time > 0.1) {
				spriteFrame = spriteFrame > 6 ? 0 : spriteFrame + 1; // shift frame up by 1 (wrap at 7)
				time = 0.0; // reset frame counter
			}
		}
		
		if (dx > 0.f) // if he is running right, from spriteFrame to spriteFrame+1
			sCoord = (float)spriteFrame / (float)spriteWidth;
		else // if he is running left FLIP the coords to avoid needing more sprite sheets
			sCoord = (float)(spriteFrame + 1) / (float)spriteWidth;
		
		texCoords[0] = sCoord; // (s,t) texture coord at [0, 1]
		texCoords[1] = 0.f;

		texCoords[2] = sCoord; // (s,t) texture coord at [0, 0]
		texCoords[3] = 1.f;

		if (dx > 0.0) // if he is running right, from spriteFrame to spriteFrame+1
			sCoord = (float)(spriteFrame + 1) / (float)spriteWidth;
		else // if he is running left FLIP the coords to avoid needing more sprite sheets
			sCoord = (float)spriteFrame / (float)spriteWidth;

		texCoords[4] = sCoord; // (s,t) texture coord at [1, 0]
		texCoords[5] = 1.f;

		texCoords[6] = sCoord; // (s,t) texture coord at [1, 1]
		texCoords[7] = 0.f;
	}

	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if (key == 'r' && state == 1) {           // 'r' key pressed: pause/unpause animation
			runAnimate = !runAnimate;
			if (runAnimate) spriteFrame = 0;
		}
	}

	@Override
	public void handleMouse(int button, int state, int mX, int mY) {
		if (button == Scene.RIGHT || button == Scene.CENTER) {
			if(state == 0) {
				runAnimate = true;
				spriteFrame = 0;
			} else {
				spriteFrame = 8;
				runAnimate = false;
			}
		}
	}
	
	// NOT USED IN IMPLEMENTATION BUT MUST BE OVERRIDEN FROM INTERFACE DECLARATIONS
	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) { }
	@Override
	public void handleMouseDrag(int mX, int mY) { }
	@Override
	public void handleMouseMove(int mX, int mY) { }

}
