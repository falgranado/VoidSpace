package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.AsteroidCloud;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Life;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;
	
	private Ship ship;
	private Asteroid asteroid;
	private AsteroidCloud asteroidCloud;
	private List<Asteroid> asteroids;
	private List<AsteroidCloud> asteroidClouds;
	private List<Bullet> bullets;
	private EnemyShip enemyShip;
	private List<EnemyShip> enemyShips;
	private List<Bullet> enemyBullets;
	private Life life;
	private List<Life> lifes;
	
	
	
	/**
	 * Craete a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();
		
		// init some variables
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		asteroidClouds = new ArrayList<AsteroidCloud>();
		enemyShips = new ArrayList<EnemyShip>();
		lifes = new ArrayList<Life>();
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	
	public void newGame(){
		status.setGameStarting(true);
		// Initiate main song
		soundMan.playGameSong();
		// init game variables
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setScore(0);
		status.setNewAsteroid(false);
		status.setNewAsteroidCloud(false);
		status.setNewLife(false);
				
		// init the ship and the asteroid
        newShip(gameScreen);
        newAsteroid(gameScreen);
        newEnemyShip(gameScreen);
        newAsteroidCloud(gameScreen);
        newLife(gameScreen);
        
    //  createAsteroids(10);
    //  createEnemyShips(10);
        
        // prepare game screen
        gameScreen.doNewGame();
        
        // delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}
	
	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();
		 
		
        // delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}
	public void fireEnemyBullet(){
		Bullet enemyBullet = new Bullet(enemyShip);
		enemyBullets.add(enemyBullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	public boolean moveEnemyBullet(Bullet enemyBullet){
		if(enemyBullet.getY() - enemyBullet.getSpeed() >= 0){

			enemyBullet.translate(0, enemyBullet.getSpeed());
			
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}
	public EnemyShip newEnemyShip(GameScreen screen){
		this.enemyShip = new EnemyShip(screen);
		return enemyShip;
	}
	public void createEnemyShips(int level){
		for(int i=0; i<level;i++){
			addEnemyShips(gameScreen);
		}
	}
	public void addEnemyShips(GameScreen screen){
		this.enemyShip = newEnemyShip(screen);
		enemyShips.add(enemyShip);
	}
	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}
	
	public void createAsteroids(int level)	{
		
		for(int i=0;i<level;i++) 	{
		addAsteroid(gameScreen);
		}
	}

	public void addAsteroid(GameScreen screen)
	{
	this.asteroid = newAsteroid(screen);
	asteroids.add(asteroid);
	}
	
	public AsteroidCloud newAsteroidCloud(GameScreen screen){
		this.asteroidCloud = new AsteroidCloud(screen);
		return asteroidCloud;
	}
	
	public void createAsteroidClouds(int level)	{
		
		for(int i=0;i<level;i++) 	{
		addAsteroidCloud(gameScreen);
		}
	}

	public void addAsteroidCloud(GameScreen screen)
	{
	this.asteroidCloud = new AsteroidCloud(screen);
	asteroidClouds.add(asteroidCloud);
	}

	public AsteroidCloud getAsteroidCloud() {
		return asteroidCloud;
	}
	
	public List<AsteroidCloud> getAsteroidClouds(){
		return asteroidClouds;
	}
	
	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}
	
	public List<Asteroid> getAsteroids(){
		return asteroids;
	}
	/**
	 * Returns the enemyShip.
	 * @return the enemyShip
	 */
	public EnemyShip getEnemyShip(){
		return enemyShip;
	}
	public List<EnemyShip> getEnemyShips(){
		return enemyShips;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}
	public List<Bullet>getEnemyBullets(){
		return enemyBullets;
	}
	public Life newLife(GameScreen screen){
		this.life = new Life(screen);
		return life;
	}
	
	public void createLife(int level)	{
		
		for(int i=0;i<level;i++) 	{
		addLife(gameScreen);
		}
	}

	public void addLife(GameScreen screen)
	{
	this.life = newLife(screen);
	lifes.add(life);
	}
	public Life getLife() {
		return life;
	}
	
	public List<Life> getLifes(){
		return lifes;
	}
}
