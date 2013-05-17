package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class AsteroidCloud extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
			
	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed = DEFAULT_SPEED;
	private boolean isNewAsteroid = false;
	// Here add the trajectory
	
	public static final int DEFAULT_TRAJECTORY = 0;
	private int trajectory = DEFAULT_TRAJECTORY;
	
	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public AsteroidCloud(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - asteroidWidth),0);
		this.setSize(asteroidWidth, asteroidHeight);
	}
	
	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
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
	 * Returns the status isNewAsteroid 
	 * @return the true or false value.
	 */
	public boolean getIsNewAsteroidCloud(){
		return isNewAsteroid;
	}
	/**
	 * Sets the status isNewAsteroid 
	 * @return the true or false value.
	 */
	public void setIsNewAsteroidCloud(boolean isNew){
		isNewAsteroid = isNew;
	}
}
