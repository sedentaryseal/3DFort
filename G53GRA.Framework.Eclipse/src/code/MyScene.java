package code;

import processing.core.*;		// import processing core
import framework.engine.*;		// import framework classes
import code.objects.*;			// import your objects package

/**
 * Your main Coursework class. Inherits {@code framework.}{@link Scene}. Edit this class.
 * @author
 * @version 1.0.0
 */

public class MyScene extends Scene {
	/**
	 * Setup your {@code Scene} in this method. Create any {@code DisplayableObject}s and {@code AnimatedObject}s
	 * and add them to the {@code Scene}.
	 * <p>
	 * Call {@code super.initialise()} to setup the framework's default  {@linkplain framework.utility.Camera Camera} and {@linkplain Scene#projection() projection} settings.
	 * @see DisplayableObject
	 * @see framework.interfaces.Animation Animation
	 * @see framework.utility.Camera Camera
	 * @see framework.interfaces.Input Input
	 */

	static int view = 0; // Used to change the view in the scene
	static int lighting = 0; // Used to change the lighting in the scene

	@Override
	public void initialise(){
		setBackgroundColour(0.f,0.f,0.f,1.f);

		// Creating and texturing the sky box:
		Stage stage = new Stage(this);
		stage.size(600.f);
		PImage skybox[] = new PImage[6];
		skybox[0] = loadImage("textures/sea_lf.JPG");
		skybox[1] = loadImage("textures/sea_rt.JPG");
		skybox[2] = loadImage("textures/sea_ft.JPG");
		skybox[3] = loadImage("textures/sea_bk.JPG");
		skybox[4] = loadImage("textures/sea_dn.JPG");
		skybox[5] = loadImage("textures/sea_up.JPG");
		stage.setTextures(skybox);

		// Creating the fort:
		Fort fort = new Fort(this, "textures/stone1.png", "textures/ukflag.png", "textures/wood5.png", "textures/cleanmetal.png");
		fort.size(20.f);

		// Creating the island the fort stands on:
		FortIsland fortIsland = new FortIsland(this, "textures/sand1.png");
		fortIsland.size(50.f);
		fortIsland.position(0.f,0.f,0.f);

		// Creating two raft objects with pirate flags
		TargetRaft targetRaft = new TargetRaft(this, "textures/cleanmetal.png", "textures/pirateFlag.png", "textures/wood.png");
		targetRaft.size(15.f);

		TargetRaft targetRaft2 = new TargetRaft(this, "textures/cleanmetal.png", "textures/pirateFlag.png", "textures/wood.png");
		targetRaft2.size(15.f);
		targetRaft2.position(0.f,0.f,800.f);

		// Creating the cannon which sits on the fort and fires cannon balls
		Cannon cannon = new Cannon(this, "textures/wood.png", "textures/blackfluffy.png");
		cannon.size(15.f);
		cannon.position(0.f,-65.f,0.f);

		// Creating the plane which tows a target
		PlaneTarget planeTarget = new PlaneTarget(this, "textures/wood.png", "textures/pirateFlag.png", "textures/target.PNG");
		planeTarget.size(15.f);
		planeTarget.position(-400.f,-70.f,-250.f);

		// Creating the soldier who lights the cannon
		Soldier soldier = new Soldier(this, "textures/blackfluffy.png", "textures/face.png");
		soldier.size(10.f);
		soldier.position(0.f,-300.f,0.f);

		// Creating four planes (without the targets or flags) which fly around the sky box
		Plane plane = new Plane(this, "textures/wood.png");
		plane.size(15.f);
		plane.position(-600.f,-60.f,250.f);

		Plane plane1 = new Plane(this, "textures/wood.png");
		plane1.size(15.f);
		plane1.position(-750.f,-100.f,250.f);

		Plane plane2 = new Plane(this, "textures/wood.png");
		plane2.size(15.f);
		plane2.position(100.f,-100.f,250.f);

		Plane plane3 = new Plane(this, "textures/wood.png");
		plane3.size(15.f);
		plane3.position(-50.f,-60.f,250.f);

		// Lighting the scene
		Sun sun = new Sun(this);
		sun.size(10.f);
		sun.position(0.f,-1111100.f,0.f);

		// Adding all the objects to the scene:
		addObjectToScene(stage,"stage");
		addObjectToScene(fort,"fort");
		addObjectToScene(fortIsland,"fort island");
		addObjectToScene(targetRaft,"target raft");
		addObjectToScene(targetRaft2,"target raft 2");
		addObjectToScene(cannon,"cannon");
		addObjectToScene(planeTarget,"plane target");
		addObjectToScene(soldier,"soldier");
		addObjectToScene(plane,"plane 0");
		addObjectToScene(plane1,"plane 1");
		addObjectToScene(plane2,"plane 2");
		addObjectToScene(plane3,"plane 3");
		addObjectToScene(sun,"sun");

		super.initialise();						// call default initialisation for camera and projection.
	}

	/**
	 * Override default global lighting.
	 * @see #lights()
	 */
	@Override
	protected void globalLighting(){
		//super.globalLighting();		// DISABLE GLOBAL LIGHTING WHEN IMPLEMENTING OWN

		float ambience[] = {51.f, 51.f, 51.f};

		// Used to handle the user input of the 'L' key press which changes the lighting settings
		if (lighting == 1){
			// Lighting is set to dusk mode
			ambience[0] = 31.f;
			ambience[1] = 31.f;
			ambience[2] = 31.f;
		} else if (lighting == 0){
			// Lighting is set to day time mode
			ambience[0] = 68.f;
			ambience[1] = 68.f;
			ambience[2] = 68.f;
		}

		float diffuse[] = {128.f, 128.f, 128.f};
	    float spec_col[] = {255.f, 255f, 255.f};
	    float direction[] = {-10.f, 20.f, -10.f, 0.f};

	    ambientLight(ambience[0], ambience[1], ambience[2]);
	    lightSpecular(spec_col[0], spec_col[1], spec_col[2]);
	    directionalLight(diffuse[0],diffuse[1],diffuse[2],direction[0], direction[1], direction[2]);
	}

	/**
	 * Override default reshape function. Called during every iteration of {@link #draw()}.
	 * Use this method to handle resizing objects based on your window size.
	 * @see #getObject(String)
	 * @see #projection()
	 */
	protected void reshape(){
		super.reshape();
	}

	/**
	 * Override default initial window size (600x400). Adjust variables in {@code super} class to change values.
	 */
	@Override
	protected void setInitWindowSize(){
		super.initWidth = 1200;	// must override variables in super class to affect size
		super.initHeight = 700;
	}

	/**
	 * Override projection properties here. Remove call to {@code super.projection()} and replace with
	 * perspective mode.
	 * @see #perspective(float, float, float, float)
	 * @see #ortho(float, float, float, float, float, float)
	 * @see #frustum(float, float, float, float, float, float)
	 */
	@Override
	protected void projection(){
		super.projection();	// calls default projection setup in Scene (orthographic)
		perspective(radians(60.f),(float)width/(float)height, 1.f, 4000.f);

		// When '2' is pressed, the view changes from perspective to orthographic
		if (view == 1){
			perspective(radians(60.f),(float)width/(float)height, 1.f, 4000.f);
		} else if (view == 2){
			ortho(-width/2.f,width/2.f,-height/2.f,height/2.f,1.f,1800*2.f);
		}
	}

	// Method to handle user changing the view
	public static void changeView(int number){
		if (number == 1){
			view = 1;
		} else if (number == 2){
			view = 2;
		}
	}

	// Method to handle user changing the lighting
	public static void changeLighting(boolean lightSetting){
		if (lightSetting){
			lighting = 1;
		} else if (!lightSetting){
			lighting = 0;
		}
	}

}
