package yusukeBot;

class CircularEnemyRad extends Enemy {
	final double PI = Math.PI;		
	public double changehead;

	public void setNextXY(long when, double x0, double y0, double balletSpeed) {
		double diff = when - checkTime;
		if (Math.abs(changehead) > 0.00001) {
			circular(diff);
		}
		else {
			liner(diff, x0, y0, balletSpeed);
		}
	}

	
	private void circular(double diff) {
		double radius = speed/changehead;
		double tothead = diff * changehead;
		nextY = y + (Math.sin(head + tothead) * radius) - (Math.sin(head) * radius);
		nextX = x + (Math.cos(head) * radius) - (Math.cos(head + tothead) * radius);
	}
	
	
	private void liner(double diff, double x0, double y0, double balletSpeed) {
		double dX = x - x0;
		double dY = y - y0;

		double targetOwnHeading = PI/2 - head;
		double vX = Math.cos(targetOwnHeading) * speed;		
		double vY = Math.sin(targetOwnHeading) * speed;	

		double A = vX * vX + vY * vY - balletSpeed * balletSpeed;			
		double B = 2 * vX * dX + 2 * vY * dY;
		double C = dX * dX + dY * dY;

		double t1,t2;
		if ( B * B > 4 * A * C) {
			t1 = (- B - Math.sqrt(B * B - 4 * A * C)) / (2 * A); 
			t2 = (- B + Math.sqrt(B * B - 4 * A * C)) / (2 * A);
			if (t1 < 0) {t1 = t2 + 1;}
			if (t2 < 0) {t2 = t1 + 1;}
			if (t1 > t2) {t1 = t2;} 	

			nextX = x + vX * t1;
			nextY = y + vY * t1;		
		} else {
			nextX = x + vX * diff;
			nextY = y + vY * diff;
		}
	}										
}

