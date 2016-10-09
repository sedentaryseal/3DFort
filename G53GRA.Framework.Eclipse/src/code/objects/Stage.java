package code.objects;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import processing.core.PApplet;
import processing.core.PImage;

public class Stage extends DisplayableObject  {

	private boolean toTexture = false;          // Flag to check if textures loaded correctly
	private PImage tex[] = new PImage[6];       // Store references to the loaded textures

	public Stage(Scene parent) {
        super(parent);
    }

	public void setTextures(PImage tex[]){
	    this.tex = tex;                         // Store texture references in array
	    toTexture = true;                       // Assume all loaded correctly
	    for(int i = 0; i < 6; i++)              // Check if any textures failed to load
	        if(tex[i] == null) toTexture = false;   // If one texture failed, do not display any
	}

	public void display() {
        parent.pushMatrix();						// Save state
        parent.pushStyle();							// Save style attributes
            // Project from Object Space to World Space
            parent.translate(pos.x,pos.y,pos.z);    // Position
            parent.scale(scale.x,scale.y,scale.z);  // Scale
            parent.rotateY(rotation.y);             // Set orientation (Y - roll)
            parent.rotateZ(rotation.z);             // Set orientation (Z - yaw)
            parent.rotateX(rotation.x);             // Set orientation (X - pitch)

            parent.noFill();                    // Transparent fill
            parent.stroke(0.f);                 // Create black outline
            parent.strokeWeight(2/scale.x);     // Reduce effects of scaling edges
            drawStage();                        // Draw 2x2x2 stage in Object Space
        parent.popStyle();							// Restore style attributes
        parent.popMatrix();							// Restore state
    }

	private void drawStage(){
	    if(toTexture) parent.noStroke();    // remove border when texturing
	    parent.noLights();                  // DISABLE LIGHTING for SKYBOX
	    parent.beginShape(PApplet.QUADS);
	    // LEFT SIDE
	        if(toTexture) parent.texture(tex[0]);   // skybox_left.png
	        parent.vertex(-1.f, -1.f, -1.f, 1, 0);  // (u,v) = (1,0)
	        parent.vertex(-1.f, -1.f, 1.f, 0, 0);   // (u,v) = (0,0)
	        parent.vertex(-1.f, 0.f, 1.f, 0, 1);    // (u,v) = (0,1)
	        parent.vertex(-1.f, 0.f, -1.f, 1, 1);   // (u,v) = (1,1)
	    parent.endShape();
	    parent.beginShape(PApplet.QUAD);
	    //	RIGHT SIDE
	        if(toTexture) parent.texture(tex[1]);	// skybox_right.png
	        parent.vertex(1.f, -1.f, 1.f, 1, 0);    // (u,v) = (1,0)
	        parent.vertex(1.f, -1.f, -1.f, 0, 0);   // (u,v) = (0,0)
	        parent.vertex(1.f, 0.f, -1.f, 0, 1);    // (u,v) = (0,1)
	        parent.vertex(1.f, 0.f, 1.f, 1, 1);     // (u,v) = (1,1)
	    parent.endShape();
	    parent.beginShape(PApplet.QUAD);
	    // FAR SIDE
	        if(toTexture) parent.texture(tex[2]);	// skybox_front.png
	        parent.vertex(1.f, -1.f, -1.f, 1, 0);   // (u,v) = (1,0)
	        parent.vertex(-1.f, -1.f, -1.f, 0, 0);  // (u,v) = (0,0)
	        parent.vertex(-1.f, 0.f, -1.f, 0, 1);   // (u,v) = (0,1)
	        parent.vertex(1.f, 0.f, -1.f, 1, 1);    // (u,v) = (1,1)
	    parent.endShape();
	    parent.beginShape(PApplet.QUAD);
	    // NEAR SIDE
	        if(toTexture) parent.texture(tex[3]);	// skybox_back.png
	        parent.vertex(-1.f, -1.f, 1.f, 1, 0);   // (u,v) = (1,0)
	        parent.vertex(1.f, -1.f, 1.f, 0, 0);    // (u,v) = (0,0)
	        parent.vertex(1.f, 0.f, 1.f, 0, 1);     // (u,v) = (0,1)
	        parent.vertex(-1.f, 0.f, 1.f, 1, 1);    // (u,v) = (1,1)
	    parent.endShape();
	    parent.beginShape(PApplet.QUAD);
	    // BOTTOM
	        if(toTexture) parent.texture(tex[4]);	// skybox_down.png
	        parent.vertex(1.f,0.f,-1.f, 1, 0);      // (u,v) = (1,0)
	        parent.vertex(-1.f,0.f,-1.f, 0, 0);     // (u,v) = (0,0)
	        parent.vertex(-1.f,0.f,1.f, 0, 1);      // (u,v) = (0,1)
	        parent.vertex(1.f,0.f,1.f, 1, 1);       // (u,v) = (1,1)
	    parent.endShape();
	    parent.beginShape(PApplet.QUAD);
	    // TOP
	        if(toTexture) parent.texture(tex[5]);	// skybox_up.png
	        parent.vertex(-1.f,-1.f,-1.f, 0, 1);    // (u,v) = (0,1)
	        parent.vertex(1.f,-1.f,-1.f, 1, 1);     // (u,v) = (1,1)
	        parent.vertex(1.f,-1.f,1.f, 1, 0);      // (u,v) = (1,0)
	        parent.vertex(-1.f,-1.f,1.f, 0, 0);	    // (u,v) = (0,0)
	    parent.endShape();
	    parent.lights();             // REENABLE LIGHTING after drawing SKYBOX
	}
}
