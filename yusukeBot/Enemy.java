package yusukeBot;

class Enemy {
	String name;
	public double bearing;
	public double head;
	public long checkTime; 	
	public double speed;
	public double x,y;
	public double distance;
	public double energy, previousEnergy;
	public double nextX, nextY;

	Enemy() {
		distance = 100000;
		previousEnergy = 100;
	}

	public void setNextXY() {
		nextX = x;
		nextY = y;
	}

	public void setEnergy(double engy) {
		previousEnergy = energy;
		energy = engy;
	}
}

