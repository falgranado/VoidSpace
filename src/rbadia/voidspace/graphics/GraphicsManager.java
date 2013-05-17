package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.AsteroidCloud;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage bulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidImg1;
	private BufferedImage asteroidImg2;
	private BufferedImage asteroidImg3;
	private BufferedImage asteroidImg4;
	private BufferedImage asteroidImg5;
	private BufferedImage asteroidImg6;
	private BufferedImage asteroidImg7;
	private BufferedImage asteroidImg8;
	private BufferedImage asteroidImg9;
	private BufferedImage asteroidImg10;
	private BufferedImage asteroidImg11;
	private BufferedImage asteroidImg12;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage enemyShipImg;
	private BufferedImage enemyBulletImg;
	private BufferedImage lifeImg;
	
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
    	// load images
		try {
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidImg1 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide1.png"));
			this.asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide2.png"));
			this.asteroidImg3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide3.png"));
			this.asteroidImg4 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide4.png"));
			this.asteroidImg5 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide5.png"));
			this.asteroidImg6 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide6.png"));
			this.asteroidImg7 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide7.png"));
			this.asteroidImg8 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide8.png"));
			this.asteroidImg9 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide9.png"));
			this.asteroidImg10 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide10.png"));
			this.asteroidImg11 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide11.png"));
			this.asteroidImg12 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroide12.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.enemyBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShot.png"));
			this.enemyShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.lifeImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/life.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	/**
	 * Draws a mysterious Clouds
	 * @param asteroid
	 * @param g2d
	 * @param observer
	 */
	public void drawAsteroidCloud(AsteroidCloud asteroid, Graphics2D g2d, ImageObserver observer) {
		Random randomize = new Random();
		switch(randomize.nextInt(13)){
		case(1):
			g2d.drawImage(asteroidImg1, asteroid.x, asteroid.y, observer);
		break;
		case(2):
			g2d.drawImage(asteroidImg2, asteroid.x, asteroid.y, observer);
		break;
		case(3):
			g2d.drawImage(asteroidImg3, asteroid.x, asteroid.y, observer);
		break;
		case(4):
			g2d.drawImage(asteroidImg4, asteroid.x, asteroid.y, observer);
		break;
		case(5):
			g2d.drawImage(asteroidImg5, asteroid.x, asteroid.y, observer);
		break;
		case(6):
			g2d.drawImage(asteroidImg6, asteroid.x, asteroid.y, observer);
		break;
		case(7):
			g2d.drawImage(asteroidImg7, asteroid.x, asteroid.y, observer);
		break;
		case(8):
			g2d.drawImage(asteroidImg8, asteroid.x, asteroid.y, observer);
		break;
		case(9):
			g2d.drawImage(asteroidImg9, asteroid.x, asteroid.y, observer);
		break;
		case(10):
			g2d.drawImage(asteroidImg10, asteroid.x, asteroid.y, observer);
		break;
		case(11):
			g2d.drawImage(asteroidImg11, asteroid.x, asteroid.y, observer);
		break;
		case(12):
			g2d.drawImage(asteroidImg12, asteroid.x, asteroid.y, observer);
		break;
		}
		
		//g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	
	/**
	 * Draws an enemy ship image to the specified graphics canvas.
	 * @param enemyShip the enemy ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyShip(EnemyShip enemyShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyShipImg, enemyShip.x, enemyShip.y, observer);
	}
	/**
	 * Draws an enemy bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyBullet(Bullet enemyBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyBulletImg, enemyBullet.x, enemyBullet.y, observer);
	}

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}
	public void drawLife(Rectangle life, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(lifeImg, life.x, life.y, observer);
	}
}
