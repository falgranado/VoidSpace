package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

	private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/shipExplosion.wav"));
	private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laser.wav"));
	private AudioClip mainMusic = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/main.wav"));
	private AudioClip gameOverVoice = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/gameOver.wav"));
	private AudioClip gameOverLaugh = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/gameOverL.wav"));
	private AudioClip explosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/explosion.wav"));
	private AudioClip levelUpSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/levelUp.wav"));
	private AudioClip lifeUpSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/lifeUp.wav"));
	/**
	 * Plays sound for bullets fired by the ship.
	 */
	public void playBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for ship explosions.
	 */
	public void playShipExplosionSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					shipExplosionSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					explosionSound.play();
				}
			}).start();
		}
	}
	public void playGameSong(){
		//play the game main music
		new Thread(new Runnable(){
			public void run(){
				mainMusic.loop();
			}
		}).start();
	}
	public void stopGameSong(){
		//play the game main music
		new Thread(new Runnable(){
			public void run(){
				mainMusic.stop();
			}
		}).start();
	}
	public void playLevelUp(){
		//play the game main music
		new Thread(new Runnable(){
			public void run(){
				levelUpSound.play();
			}
		}).start();
	}
	
	public void playLifeUp(){
		//play the game main music
		new Thread(new Runnable(){
			public void run(){
				lifeUpSound.play();
			}
		}).start();
	}
	public void playGameOver(){
		//play the game main music
		new Thread(new Runnable(){
			public void run(){
				gameOverVoice.play();
				gameOverLaugh.play();
				 
				
			}
		}).start();
	}
	
}

