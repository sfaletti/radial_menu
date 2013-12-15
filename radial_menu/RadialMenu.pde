class RadialMenu{
	float dia, rad, xPos, yPos; //variables for basic parameters
	PVector pos; //position as a vector will help calculations
	float selAng; //angular location for selector center
	float selLength;
	float value, maxVal, minVal; //output value and range
	public color circleCol, selectorCol, valTextCol, incsCol;
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
		selLength = .6*TWO_PI/maxVal;

		//default color scheme. Change these values to change colors.
		//Access them using attributes, eg for red. myMenu.circleCol = color(255, 0, 0);
		circleCol = color(220);
		selectorCol = color(150, 100);
		valTextCol = color(50);
		incsCol = color(220);

	}

	void setValues(float _max, float _min) {
		maxVal = _max;
		minVal = _min;
	}

	void displayIndex() {
		pushStyle();
		noStroke();
		fill(circleCol);
		ellipseMode(CENTER);
		ellipse(xPos, yPos, dia, dia);
		textAlign(CENTER, CENTER);
		fill(incsCol);
		textSize(dia * .05);
		for (int i = 0; i<maxVal; i++){
			pushMatrix();
			translate(xPos, yPos);
			rotate(i*TWO_PI/maxVal);
			translate((-1*rad)-(rad*.18), 0);
			PVector numPos = new PVector(screenX(0,0), screenY(0,0));
			popMatrix();
			text(i, numPos.x, numPos.y);
		}
		fill(valTextCol);
		textSize(dia * .35);
		text(nf(value, 0, 1), xPos, yPos-(dia*.05));
		popStyle();

	}

	void displaySelector(){
		pushStyle();
		noFill();
		stroke(selectorCol);
		strokeWeight(dia*.08);
		strokeCap(ROUND);
		pushMatrix();
		translate(xPos, yPos);
		rotate(selAng);
		arc(0, 0, dia+(dia*.18), dia+(dia*.18), -1*selLength*.5, selLength*.5);
		popMatrix();
		popStyle();
	}

	void display() {
		displayIndex();
		displaySelector();
	}
	
	void moveSelector(PVector _mPos) {
		float d = _mPos.dist(pos);
		if (d <= rad*1.3 && d > rad){
			selAng = atan2(_mPos.y - yPos, _mPos.x - xPos);
			value = map(selAng, -PI, PI, minVal, maxVal);

		}
	}
}
