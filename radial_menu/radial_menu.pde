RadialMenu myMenu;

void setup(){
	size(800, 800);
	myMenu = new RadialMenu(500, width/2, height/2);
	//if you want to change element colors access their attributes
	// myMenu.circleCol = color(255, 0, 0); //for a red circle
}

void draw(){
	background(0);
	myMenu.display();
}

void mouseDragged(){
	myMenu.moveSelector(new PVector(mouseX, mouseY));
}
