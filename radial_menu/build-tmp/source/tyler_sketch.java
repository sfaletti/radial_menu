import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class tyler_sketch extends PApplet {

RadialMenu myMenu;

public void setup(){
	size(800, 800);
	myMenu = new RadialMenu(500, width/2, height/2);
	// myMenu.circleCol = color(255, 0, 0);
	//if you wan to change element colors 
}

public void draw(){
	background(0);
	myMenu.display();
}

public void mouseDragged(){
	myMenu.moveSelector(new PVector(mouseX, mouseY));
}
class RadialMenu{
	float dia, rad, xPos, yPos; //variables for basic parameters
	PVector pos; //position as a vector will help calculations
	float selAng; //angular location for selector center
	float selLength;
	float value, maxVal, minVal; //output value and range
	public int circleCol, selectorCol, valTextCol, incsCol;
	float sPos; //rotational position of the selector

	RadialMenu(float _d, float _xPos, float _yPos){
		dia = _d; //diameter of inner ellipse of menu. The overall size will be bigger
		rad = dia/2; //radius convenience variable
		xPos = _xPos; //positioned relative to center
		yPos = _yPos; //positioned relative to center
		pos = new PVector(xPos, yPos);
		
		//selector values
		minVal = 0;
		maxVal = 10;
		value = minVal;

		//selector location values
		selAng = map(value, minVal, maxVal, -PI, PI);
		selLength = .6f*TWO_PI/maxVal;

		//default color scheme. Change these values to change colors.
		//Access them using attributes, eg for red. myMenu.circleCol = color(255, 0, 0);
		circleCol = color(220);
		selectorCol = color(150, 100);
		valTextCol = color(50);
		incsCol = color(220);

	}

	public void setValues(float _max, float _min) {
		maxVal = _max;
		minVal = _min;
	}

	public void displayIndex() {
		pushStyle();
		noStroke();
		fill(circleCol);
		ellipseMode(CENTER);
		ellipse(xPos, yPos, dia, dia);
		textAlign(CENTER, CENTER);
		fill(incsCol);
		textSize(dia * .05f);
		for (int i = 0; i<maxVal; i++){
			pushMatrix();
			translate(xPos, yPos);
			rotate(i*TWO_PI/maxVal);
			translate((-1*rad)-(rad*.18f), 0);
			PVector numPos = new PVector(screenX(0,0), screenY(0,0));
			popMatrix();
			text(i, numPos.x, numPos.y);
		}
		fill(valTextCol);
		textSize(dia * .35f);
		text(nf(value, 0, 1), xPos, yPos-(dia*.05f));
		popStyle();

	}

	public void displaySelector(){
		pushStyle();
		noFill();
		stroke(selectorCol);
		strokeWeight(dia*.08f);
		strokeCap(ROUND);
		pushMatrix();
		translate(xPos, yPos);
		rotate(selAng);
		arc(0, 0, dia+(dia*.18f), dia+(dia*.18f), -1*selLength*.5f, selLength*.5f);
		popMatrix();
		popStyle();
	}

	public void display() {
		displayIndex();
		displaySelector();
	}
	
	public void moveSelector(PVector _mPos) {
		float d = _mPos.dist(pos);
		if (d <= rad*1.3f && d > rad){
			selAng = atan2(_mPos.y - yPos, _mPos.x - xPos);
			value = map(selAng, -PI, PI, minVal, maxVal);

		}
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--hide-stop", "tyler_sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
