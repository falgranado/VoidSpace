package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.AsteroidCloud;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Life;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastEnemyShipTime;

	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;
	private int level = 1 ;
	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();		
		EnemyShip enemyShip = gameLogic.getEnemyShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		AsteroidCloud asteroidCloud = gameLogic.getAsteroidCloud();
		Life lifePowerUp = gameLogic.getLife();
	
		//	Asteroid asteroid2 = gameLogic.getAsteroid();
		List<Bullet> bullets = gameLogic.getBullets();
		List<Bullet> enemyBullets = gameLogic.getEnemyBullets();
		List<EnemyShip> enemyShips = gameLogic.getEnemyShips();
		List<Asteroid> asteroids = gameLogic.getAsteroids();
		List<AsteroidCloud> asteroidClouds = gameLogic.getAsteroidClouds();		
		List<Life> lifes = gameLogic.getLifes();
//		gameLogic.createAsteroids(level +2);
//		gameLogic.createEnemyShips(level);
		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(50);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			asteroids.clear();
			enemyShips.clear();
			asteroidClouds.clear();
			lifes.clear();
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}
		//draw enemy ship

		for(EnemyShip enemy: enemyShips)
		{

			if(!enemy.getIsNewEnemy()){


				// draw the enemy until it reaches the bottom of the screen
				if(enemy.getY() + enemy.getSpeed() < this.getHeight()){
					enemy.translate( enemy.getTrajectory(), enemy.getSpeed());
					graphicsMan.drawEnemyShip(enemy, g2d, this);
					if(enemy.getY() < 3 ){
						gameLogic.fireEnemyBullet();
					}
					
					else if (enemy.getY() > 50 && enemy.getY() < 53 ){
						gameLogic.fireEnemyBullet();
					}
				}
				else{
					enemy.setTrajectory(( -2 + rand.nextInt(4)));
					enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);

				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastEnemyShipTime) > NEW_ASTEROID_DELAY){
					// draw a new asteroid

					lastEnemyShipTime = currentTime;
					enemy.setIsNewEnemy(false);
					enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);

				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}


		}
		// draw asteroid

		for(Asteroid e: asteroids)
		{

			if(!e.getIsNewAsteroid()){


				// draw the asteroid until it reaches the bottom of the screen
				if(e.getY() + e.getSpeed() < this.getHeight()){
					e.translate( e.getTrajectory(), e.getSpeed());
					graphicsMan.drawAsteroid(e, g2d, this);
				}
				else{
					e.setTrajectory(-2 + rand.nextInt(4));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);

				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					// draw a new asteroid

					lastAsteroidTime = currentTime;
					e.setIsNewAsteroid(false);
					//e.setTrajectory(( -2 + rand.nextInt(4)));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);

				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}


		}
		//Draw Asteroid Clouds
		for(AsteroidCloud e: asteroidClouds)
		{

			if(!e.getIsNewAsteroidCloud()){


				// draw the asteroid until it reaches the bottom of the screen
				if(e.getY() + e.getSpeed() < this.getHeight()){
					e.translate( e.getTrajectory(), e.getSpeed());
					graphicsMan.drawAsteroidCloud(e, g2d, this);
				}
				else{
					e.setTrajectory(-2 + rand.nextInt(4));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);

				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					// draw a new asteroid

					lastAsteroidTime = currentTime;
					e.setIsNewAsteroidCloud(false);
					//e.setTrajectory(( -2 + rand.nextInt(4)));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);

				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}


		}
		//Draw Life powerUp
		for(Life e: lifes)
		{

			if(!e.getIsNewLife()){


				// draw the asteroid until it reaches the bottom of the screen
				if(e.getY() + e.getSpeed() < this.getHeight()){
					e.translate( e.getTrajectory(), e.getSpeed());
					graphicsMan.drawLife(e, g2d, this);
				}
				else{
					e.setTrajectory(-2 + rand.nextInt(4));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);

				}
			}
		}


		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
			//draw enemy bullets
			for(int j=0; j<enemyBullets.size();j++){
				Bullet enemyBullet = enemyBullets.get(j);
				graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);

				boolean removeEnemyBullet = gameLogic.moveEnemyBullet(enemyBullet);
				if(removeEnemyBullet){
					enemyBullets.remove(j);
					j++;
				}
			}

		}
		//Check for Enemy Ship and Bullet Collision
		for(int j=0;j<enemyShips.size();j++){
			EnemyShip enemyShipsElement = enemyShips.get(j);
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(enemyShipsElement.intersects(bullet)){
					// increase Score count
				 status.setScore(status.getScore() + 100);

					// "remove" Enemy destroid
					asteroidExplosion = new Rectangle(
							enemyShipsElement.x,
							enemyShipsElement.y,
							enemyShipsElement.width,
							enemyShipsElement.height);
					enemyShipsElement.setLocation(-enemyShipsElement.width, -enemyShipsElement.height);
					enemyShipsElement.setIsNewEnemy(true);
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					lastAsteroidTime = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();
					//status.increaseScore("EnemyShip", status.getScore());
					

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}
		
		//Check for Ship and Enemy Bullet Collision
		
		for(int i=0; i<enemyBullets.size(); i++){
		Bullet bullet = enemyBullets.get(i);
		if(bullet.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			
			status.setScore(status.getScore()- 5);

			// "remove" ship
	        shipExplosion = new Rectangle(
	        		ship.x,
	        		ship.y,
	        		ship.width,
	        		ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();
			
			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
			
			// remove bullet
			enemyBullets.remove(i);
			break;
		}
	}
		//Check for life and ship collision
		for(int i=0; i<lifes.size(); i++){
			Life life = lifes.get(i);
			if(life.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() + 1);
			
				soundMan.playLifeUp();
				
				// remove life
				lifes.remove(i);
				break;
			}
		}
		
		// check bullet-asteroid collisions
		for(int j =0 ; j<asteroids.size();j++){

			Asteroid asteroidsElement = asteroids.get(j);
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(asteroidsElement.intersects(bullet)){
					// increase asteroids destroyed count
					status.setScore(status.getScore() + 50);

					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							asteroidsElement.x,
							asteroidsElement.y,
							asteroidsElement.width,
							asteroidsElement.height);
					asteroidsElement.setLocation(-asteroidsElement.width, -asteroidsElement.height);
					asteroidsElement.setIsNewAsteroid(true);
					lastAsteroidTime = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}
		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}
		
		//Check for Asteroid Cloud-bullet collision

		for(int j =0 ; j<asteroidClouds.size();j++){

			AsteroidCloud asteroidCloudsElement = asteroidClouds.get(j);
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(asteroidCloudsElement.intersects(bullet)){
					// increase asteroids destroyed count
					status.setScore(status.getScore() + 75	);

					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							asteroidCloudsElement.x,
							asteroidCloudsElement.y,
							asteroidCloudsElement.width,
							asteroidCloudsElement.height);
					asteroidCloudsElement.setLocation(-asteroidCloudsElement.width, -asteroidCloudsElement.height);
					asteroidCloudsElement.setIsNewAsteroidCloud(true);
					lastAsteroidTime = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}
		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}
		
		// check ship-asteroid collisions
		for(int j = 0; j<asteroids.size(); j++){

			Asteroid asteroidsElement =  asteroids.get(j);
			if(asteroidsElement.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				status.setScore(status.getScore()- 10);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroidsElement.x,
						asteroidsElement.y,
						asteroidsElement.width,
						asteroidsElement.height);
				asteroidsElement.setLocation(-asteroidsElement.width, -asteroidsElement.height);
				asteroidsElement.setIsNewAsteroid(true);
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				lastAsteroidTime = System.currentTimeMillis();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}
		// check ship-asteroidCloud collisions
				for(int j = 0; j<asteroidClouds.size(); j++){

					AsteroidCloud asteroidCloudsElement =  asteroidClouds.get(j);
					if(asteroidCloudsElement.intersects(ship)){
						// decrease number of ships left
						status.setShipsLeft(status.getShipsLeft() - 1);

						status.setScore(status.getScore()- 10);

						// "remove" asteroid
						asteroidExplosion = new Rectangle(
								asteroidCloudsElement.x,
								asteroidCloudsElement.y,
								asteroidCloudsElement.width,
								asteroidCloudsElement.height);
						asteroidCloudsElement.setLocation(-asteroidCloudsElement.width, -asteroidCloudsElement.height);
						asteroidCloudsElement.setIsNewAsteroidCloud(true);
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
						lastAsteroidTime = System.currentTimeMillis();

						// "remove" ship
						shipExplosion = new Rectangle(
								ship.x,
								ship.y,
								ship.width,
								ship.height);
						ship.setLocation(this.getWidth() + ship.width, -ship.height);
						status.setNewShip(true);
						lastShipTime = System.currentTimeMillis();

						// play ship explosion sound
						soundMan.playShipExplosionSound();
						// play asteroid explosion sound
						soundMan.playAsteroidExplosionSound();
					}
				}
		// Check for ENEMY AND SHIP COLLISION
		for(int j=0; j<enemyShips.size();j++){
			EnemyShip enemyShipsElement = enemyShips.get(j);
			if(enemyShipsElement.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				status.setScore(status.getScore()- 5);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						enemyShipsElement.x,
						enemyShipsElement.y,
						enemyShipsElement.width,
						enemyShipsElement.height);
				enemyShipsElement.setLocation(-enemyShipsElement.width, -enemyShipsElement.height);
				enemyShipsElement.setIsNewEnemy(true);
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				lastAsteroidTime = System.currentTimeMillis();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}

			// update asteroids destroyed label
			destroyedValueLabel.setText(Long.toString(status.getScore()));

			// update ships left label
			shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		}
		
		if(status.getScore() >= 0 && status.getScore()<500 && enemyShips.size()<1 && asteroids.size()<2){
			
			
			gameLogic.createAsteroids(3);
			gameLogic.createEnemyShips(0);
			
			
			
		}
		else if(status.getScore() >= 500 && status.getScore()< 3000 && enemyShips.size()<2 && asteroids.size()<4){
			gameLogic.createAsteroids(4);
			gameLogic.createEnemyShips(1);
			gameLogic.createLife(2);
			soundMan.playLevelUp();
			
		}
		else if(status.getScore() >= 3000 && status.getScore()< 7500 && enemyShips.size()<6 && asteroids.size()<8){
			gameLogic.createAsteroids(5);
			gameLogic.createEnemyShips(3);
			gameLogic.createAsteroidClouds(2);
			gameLogic.createLife(5);
			
			soundMan.playLevelUp();
			
		}
		else if(status.getScore() >= 7500 && status.getScore()<10000 && enemyShips.size()<12 && asteroids.size()<21){
			gameLogic.createAsteroids(7);
			gameLogic.createEnemyShips(5);
			gameLogic.createAsteroidClouds(3);
			gameLogic.createLife(1000000);
			
			soundMan.playLevelUp();
			
		}
		else if(status.getScore() >=10000 && status.getScore()<25000 && enemyShips.size()<25 && asteroids.size()<41){
			gameLogic.createAsteroids(13);
			gameLogic.createEnemyShips(7);
			gameLogic.createAsteroidClouds(4);
			gameLogic.createLife(1000000);
			soundMan.playLevelUp();
			
		}
		else if(status.getScore() >=25000 && status.getScore()<50000 && enemyShips.size()<81 && asteroids.size()<49){
			gameLogic.createAsteroids(30);
			gameLogic.createEnemyShips(17);
			gameLogic.createAsteroidClouds(7);
			gameLogic.createLife(10000000);
			soundMan.playLevelUp();
			
		}
		else{
			
			status.isGameOver();
		}
		
	}
	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}
	public void drawPaused() {
		String readyStr = "Paused!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Space Wars";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
		soundMan.stopGameSong();
		soundMan.playGameOver();

	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getScore()));
		
	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}
}
