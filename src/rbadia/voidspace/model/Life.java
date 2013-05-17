package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Life extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	Random randomSpeed = new Random();
	public int DEFAULT_SPEED = 2 + randomSpeed.nextInt(3);
			
	private int lifeWidth = 10;
	private int lifeHeight = 10;
	private int speed = DEFAULT_SPEED;
	private boolean isNewLife = false;
	// Here add the trajectory
	
	private Random rand = new Random();
	
	public  int DEFAULT_TRAJECTORY = -2 + rand.nextInt(3);
	private int trajectory = DEFAULT_TRAJECTORY;
	
	/**
	 * Crates a new Life at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Life(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - lifeWidth),0);
		this.setSize(lifeWidth, lifeHeight);
	}
	
	public int getLifeWidth() {
		return lifeWidth;
	}
	public int getLifeHeight() {
		return lifeHeight;
	}

	/**
	 * Returns the current Life speed
	 * @return the current Life speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current Life speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the current Life trajectory
	 * @return the current Life trajectory
	 */
	public int getTrajectory() {
		return trajectory;
	}
	
	
	/**
	 * Set the current Life trajectory
	 * @param trajectory the trajectory to set
	 */
	public void setTrajectory(int trajectory) {
		this.trajectory = trajectory;
	}
	
	/**
	 * Returns the default Life speed.
	 * @return the default Life speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	/**
	 * Returns the status isNewLife 
	 * @return the true or false value.
	 */
	public boolean getIsNewLife(){
		return isNewLife;
	}
	/**
	 * Sets the status isNewLife 
	 * @return the true or false value.
	 */
	public void setIsNewLife(boolean isNew){
		isNewLife = isNew;
	}
}
