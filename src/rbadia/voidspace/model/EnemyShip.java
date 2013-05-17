package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;
	Random randomSpeed = new Random();
	public int DEFAULT_SPEED = 1 + randomSpeed.nextInt(3);
			
	private int shipWidth = 40;
	private int shipHeight = 23;
	private int speed = DEFAULT_SPEED;
	private boolean isNewEnemy = false;
	// Here add the trajectory
	
	private Random rand = new Random();
	
	public  int DEFAULT_TRAJECTORY = -2 + rand.nextInt(4);
	private int trajectory = DEFAULT_TRAJECTORY;
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - shipWidth),0);
		this.setSize(shipWidth, shipHeight);
	}
	
	public int getEnemyShipWidth() {
		return shipWidth;
	}
	public int getEnemyShipHeight() {
		return shipHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the current asteroid trajectory
	 * @return the current asteroid trajectory
	 */
	public int getTrajectory() {
		return trajectory;
	}
	
	
	/**
	 * Set the current asteroid trajectory
	 * @param trajectory the trajectory to set
	 */
	public void setTrajectory(int trajectory) {
		this.trajectory = trajectory;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	/**
	 * Returns the status isNewEnemy
	 * @return the true or false value.
	 */
	public boolean getIsNewEnemy(){
		return isNewEnemy;
	}
	/**
	 * Sets the status isNewEnemy
	 * @return the true or false value.
	 */
	public void setIsNewEnemy(boolean isNew){
		isNewEnemy = isNew;
	}

	 
 } 
