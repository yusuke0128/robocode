package yusukeBot;
import robocode.*;

import java.awt.Color;
import java.util.Random;



public class Thalsis3 extends AdvancedRobot
{

	final double PI = Math.PI;			
	int a = 0;
	public double myEnergy, previousMyEnergy;
	Random rnd = new Random();
	CircularEnemyRad target = new CircularEnemyRad();	
	Direction movDir = new Direction();		
	Direction sweepDir = new Direction();			
	Lib help = new Lib();						

	double firePower;       			
	double sweepKaku = PI;				
	private double dando;

	public void run() {

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		if(getTime() % 30 == 0) {
			setBodyColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setGunColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setRadarColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setBulletColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setScanColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			switch(a){
			case 0: move1();
			case 1: move2();
			case 2: move3();
			case 3: move4();
			}
			turnRadarRightRadians(2*PI);
		}
		turnRadarRightRadians(2*PI);			
		while(true) {
			doMovement();				
			doFirePower();
			doScanner();				
			doGun();					
			if (target.energy<=0){
				turnRadarRightRadians(2*PI);
			}
			if ( firePower != 0 ) {
			if(getEnergy()>15){	
				fire(firePower);
			}
			    else
				fire(firePower-1.5);
			}
			execute();
		}
	}

	void doMovement() {
		double changeInEnergy = target.previousEnergy - target.energy;
		if (changeInEnergy>0&&changeInEnergy<3.0) {
			switch(a){
			case 0: avoid1();
			case 1: avoid2();
			case 2: avoid3();
			case 3: avoid4();
			}
		}
	}

	void doFirePower() {
		if(target.distance<100)
			firePower = 3;	//selects a bullet power based on our distance away from the target
		if(target.distance>=100&&target.distance<200)
			firePower = 2.5;
		if(target.distance>=200)
			firePower = 2.0;
		
	}

	void doScanner() {
		
		if (sweepKaku > PI/2) {
			sweepKaku = PI*2;
		} else {		
			sweepKaku += PI/8;
			sweepDir.flip();
		}
		setTurnRadarRightRadians(sweepKaku * sweepDir.getDir());
	}

	void doGun() {

		double balletSpeed = 20-(3*firePower);
		long nextTime = (int)Math.round(target.distance/balletSpeed);
		long time = getTime() + nextTime;
		target.setNextXY(time, getX(), getY(), balletSpeed);
		double nextX = target.nextX - getX();
		double nextY = target.nextY - getY();
		double kaiten = PI/2 - Math.atan2(nextY, nextX);
		setTurnGunRightRadians(help.normalAngle(kaiten - getGunHeadingRadians()));

			
		if (target.nextX < 0 
				|| target.nextY < 0 
				|| target.nextX > getBattleFieldWidth() 
				|| target.nextY > getBattleFieldHeight()) {
			firePower = 0;
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		if(e.getTime() % 32 == 0) {
			setBodyColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setGunColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setRadarColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setBulletColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			setScanColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			switch(a){
			case 0: move1();
			case 1: move2();
			case 2: move3();
			case 3: move4();
			}
		}
		double absbearing_rad = (getHeadingRadians()+e.getBearingRadians())%(2*PI);
		dando = (getHeadingRadians()+e.getBearingRadians())%(2*PI);
		target.name = e.getName();
		double h = help.normalAngle(e.getHeadingRadians() - target.head);
		h = h/(getTime() - target.checkTime);
		target.changehead = h;
		target.x = getX()+Math.sin(absbearing_rad)*e.getDistance(); 
		target.y = getY()+Math.cos(absbearing_rad)*e.getDistance(); 
		target.bearing = e.getBearingRadians();
		target.head = e.getHeadingRadians();
		target.checkTime = getTime();				
		target.speed = e.getVelocity();
		target.distance = e.getDistance();
		target.setEnergy(e.getEnergy());			
		sweepKaku = PI/10;						
	}

	public void onHitByBullet(HitByBulletEvent e) {
		turnRadarRight(getHeading() + e.getBearing() - getRadarHeading());		
		switch(a){
		case 0: move1();
		case 1: move2();
		case 2: move3();
		case 3: move4();
		}
	}
	public void onWin(WinEvent e) {
		clearAllEvents();
		turnGunLeft(getGunHeading());
		setTurnRight(1800);
		setTurnRadarRight(1800);
		execute();
	}

	public void move1(){
		setTurnRight(100);
		setAhead(200);
		a = rnd.nextInt(4);
	}

	public void move2(){
		setTurnRight(100);
		setBack(200);
		a = rnd.nextInt(4);
	}

	public void move3(){
		setTurnLeft(100);
		setAhead(200);
		a = rnd.nextInt(4);
	}

	public void move4( ){
		setTurnLeft(100);
		setBack(200);
		a = rnd.nextInt(4);
	}
	public void avoid1(){
		setTurnRight(100+dando);
		setAhead(150);
		a = rnd.nextInt(4);
	}

	public void avoid2(){
		turnRight(100+dando);
		setBack(150);
		a = rnd.nextInt(4);
	}

	public void avoid3(){
		setTurnLeft(100+dando);
		setAhead(150);
		a = rnd.nextInt(4);
	}

	public void avoid4(){
		setTurnLeft(100+dando);
		setBack(150);
		a = rnd.nextInt(4);
	}
}


