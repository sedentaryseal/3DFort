package code.demos.texturing;

import processing.core.PImage;
import framework.engine.*;
import framework.interfaces.*;

public class Water extends DisplayableObject implements Animation, Input {
	PImage tex;

	int xGridDims, zGridDims;
	float texCoords[];

	double time;
	boolean frozen;

	final float matAmbient[]  = new float[3];
	final float matDiffuse[]  = new float[3];
	final float matSpecular[] = new float[3];
	int matShininess;

	public Water(Scene parent, int gridX, int gridZ, String filename) {
		super(parent);

		xGridDims = gridX;
		zGridDims = gridZ;

		frozen = false;
		time   = 0.0;

		tex       = parent.loadImage(filename);
		texCoords = new float[(xGridDims + 1) * (zGridDims + 1) * 2];

		matAmbient[0] = 240.f;   // set the material properties of the grid
		matAmbient[1] = 248.f;
		matAmbient[2] = 240.f;

		matDiffuse[0] = 255.f;
		matDiffuse[1] = 255.f;
		matDiffuse[2] = 255.f;

		matSpecular[0] = 255.f;
		matSpecular[1] = 255.f;
		matSpecular[2] = 255.f;

		matShininess = 128;
	}


	@Override
	public void display() {
		float x, y, z;
		int ti; // texture coordinate index
		parent.pushStyle();
			parent.noStroke();
			parent.ambient(matAmbient[0], matAmbient[1], matAmbient[2]);
			parent.fill(matDiffuse[0], matDiffuse[1], matDiffuse[2], 240.f);
			parent.specular(matSpecular[0], matSpecular[1], matSpecular[2]);
			parent.shininess(matShininess);

			if (tex != null) parent.texture(tex); // bind texture

			parent.translate(0.f,-150.f,0.f);


			parent.pushMatrix();
			{
				parent.translate(pos.x, pos.y, pos.z);
				parent.scale(scale.x, scale.y, scale.z);

				y = 0.f; z = -0.5f;
				parent.normal(0.f, -1.f, 0.f);
				parent.beginShape(Scene.QUADS);
				for (int j = 0; j < zGridDims; j++) {
					x = -0.5f;
					for (int i = 0; i < xGridDims; i++) {
						ti = (i+j*(xGridDims+1))*2;
						parent.vertex(x, y, z, texCoords[ti], texCoords[ti+1]);

						ti = (i + (j + 1)*(xGridDims + 1)) * 2;
						parent.vertex(x, y, z + 1.f/(float)zGridDims, texCoords[ti], texCoords[ti+1]);

						ti = (i + 1 + (j + 1)*(xGridDims + 1)) * 2;
						parent.vertex(x + 1.f/(float)xGridDims, y, z+ 1.f/(float)zGridDims, texCoords[ti], texCoords[ti+1]);

						ti = ((i + 1) + j*(xGridDims + 1)) * 2;
						parent.vertex(x + 1.f/(float)xGridDims, y, z, texCoords[ti], texCoords[ti+1]);

						x += 1.f / (float)xGridDims;
					}
					z += 1.f / (float)zGridDims;
				}
				parent.endShape();
			}
			parent.popMatrix();
		parent.popStyle();
		parent.texture(null);
	}

	@Override
	public void update(float dT) {
		float radius;
		time += dT; // overall run time

		radius = (float)Math.sqrt((1.f / xGridDims)*(1.f/xGridDims) + (1.f/zGridDims)*(1.f/zGridDims));
		radius /= 4.f;

		if (frozen) {
			for (int j = 0; j <= zGridDims; j++) {
				for (int i = 0; i <= xGridDims; i++) {
					// if the water is frozen then calculate texCoord based on the sample position
					// i+(xGridDims+1) * j gives the texture position of the ith sample on the jth row
					texCoords[(i + (xGridDims + 1) * j) * 2 + 0] = (float)i / (float)xGridDims;
					texCoords[(i + (xGridDims + 1) * j) * 2 + 1] = (float)j / (float)zGridDims;
				}
			}
		} else {
			for (int j = 0; j <= zGridDims; j++) {
				for (int i = 0; i <= xGridDims; i++) {
					// if the water is not frozen then calculate texCoord based on the sample position + some amount offset by a spherical function
					texCoords[(i + (xGridDims + 1) * j) * 2 + 0] = (float)Math.sin(time + (double)j) * radius + (float)i / (float)xGridDims;
					texCoords[(i + (xGridDims + 1) * j) * 2 + 1] = (float)Math.cos(time + (double)i) * radius + (float)j / (float)zGridDims;
				}
			}
		}


	}

	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if(key == 'm' && state == 1)
			frozen = !frozen;
	}

	// NOT USED IN IMPLEMENTATION BUT MUST BE OVERRIDEN FROM INTERFACE DECLARATIONS
	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) { }
	@Override
	public void handleMouse(int button, int state, int mX, int mY) { }
	@Override
	public void handleMouseDrag(int mX, int mY) { }
	@Override
	public void handleMouseMove(int mX, int mY) { }

}
