package code.objects;

import code.MyScene;
import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Input;
import framework.interfaces.Lighting;

public class Sun extends DisplayableObject implements Input, Lighting {
	float position0[], ambient0[], diffuse0[], specular0[];
	boolean show0;
	boolean lightSettings;
	boolean positional0;

	public Sun(Scene parent) {
		super(parent);

		position0 = new float[3];
		ambient0 = new float[3];
		diffuse0 = new float[3];
		specular0 = new float[3];

		ambient0[0] = 28.f;
		ambient0[1] = 28.f;
		ambient0[2] = 28.f;

		diffuse0[0] = 128.f;
		diffuse0[1] = 128.f;
		diffuse0[2] = 0.f;

		specular0[0] = 0.f;
		specular0[1] = 0.f;
		specular0[2] = 0.f;

		position0[0] = 1.f;
		position0[1] = -1.f;
		position0[2] = 1.f;

		positional0 = false;
		show0 = true;
		lightSettings = false;
	}

	@Override
	public void setupLighting() {
		if(show0){
			parent.lightSpecular(specular0[0],specular0[1],specular0[2]);
			parent.ambientLight(ambient0[0],ambient0[1],ambient0[2]);
			parent.directionalLight(diffuse0[0],diffuse0[1],diffuse0[2],-position0[0],-position0[2],-position0[2]);
		}
		parent.lightSpecular(0,0,0); // Resetting specular
	}

	@Override
	public void display() {
		parent.noLights();
		parent.pushStyle();
		parent.sphereDetail(10,10);
		parent.noStroke();
		parent.translate(500.f, -450.f,0.f);
		parent.popStyle();
		parent.lights();
	}

	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if (state != 1) return; // if key not pressed (i.e. ignore key release)

		// The lighting will change when 'L' or 'l' is pressed
		// The light light in this class will turn on or off and the global lighting
		// values in MyScene will change, to show dusk/daytime light settings.
		switch (key)
		{
			case 'l':
				show0 = !show0;
				lightSettings = !lightSettings;
				MyScene.changeLighting(lightSettings);
				break;
			case 'L':
				show0 = !show0;
				lightSettings = !lightSettings;
				MyScene.changeLighting(lightSettings);
				break;
		}
	}

	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleMouse(int button, int state, int mX, int mY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleMouseDrag(int mX, int mY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleMouseMove(int mX, int mY) {
		// TODO Auto-generated method stub
	}
}
