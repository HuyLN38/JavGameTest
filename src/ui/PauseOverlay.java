package ui;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.VolumeBar.*;
import static utilz.Constants.UI.PauseMenu.*;
import static gamestates.Gamestate.*;


import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
    
    private BufferedImage background;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private VolumeBar volumeBar;
    private Buttons resumeButton, menuButton;

    public PauseOverlay(){
;  
        loadBackground();
        createSoundButton();
        createVolumeBar();
        createMenuButton();
        

    }
    
    private void createMenuButton() {
        int xButton1 = (int)((Game.GAME_WIDTH/4  - BUTTON_WIDTH/1.8)*Game.SCALE);
        int xButton2 = (int)((xButton1) + 85*Game.SCALE);
        resumeButton = new Buttons(xButton1, 700, BUTTON_WIDTH, BUTTON_HEIGHT, 0);
        menuButton = new Buttons(xButton2, 700, BUTTON_WIDTH, BUTTON_HEIGHT, 1);

    }

    private void createVolumeBar() {
        int volumex = (int)((Game.GAME_WIDTH/4 + 50 - VOLUME_BAR_LENGTH/2)*Game.SCALE);
        int volumey = (int)(290*Game.SCALE);
        volumeBar = new VolumeBar(volumex, volumey, VOLUME_BAR_LENGTH, VOLUME_BAR_HEIGHT);
    }

    private void createSoundButton() {
        int soundX = (int)(430*Game.SCALE);
        int musicY = (int)(165*Game.SCALE);
        int sfxY = (int)(195*Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        background = LoadSave.GetSpriteAtlast(LoadSave.PAUSE_BACKGROUND);
        bgW = (int)(background.getWidth());
        bgH = (int)(background.getHeight());
        bgX = (Game.GAME_WIDTH/2) - (bgW/2);
        bgY = (int)( 25 * Game.SCALE );
   
    }

    public void update() {

        musicButton.update();
        sfxButton.update();
        volumeBar.update();
        resumeButton.update();
        menuButton.update();
        

    }

    public void draw(Graphics g) {
        g.drawImage(background, bgX, bgY, bgW, bgH, null);
        
        musicButton.draw(g);
        sfxButton.draw(g);
        volumeBar.draw(g);
        resumeButton.draw(g);
        menuButton.draw(g);

    }
    
    public void mouseDragged(MouseEvent e) {
        
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
      if (isIn(e, musicButton)){
          musicButton.setMousePressed(true);
        //   System.out.println("music button pressed");
        }
      else if (isIn(e, sfxButton)){
          sfxButton.setMousePressed(true);
            // System.out.println("sfx button pressed");
      }
      if (isIn(e, resumeButton)){
          resumeButton.setMousePressed(true);
        //   System.out.println("resume button pressed");
        }
      else if (isIn(e, menuButton)){
          menuButton.setMousePressed(true);
            // System.out.println("menu button pressed");
      }

      if (isIn(e, volumeBar)){
          volumeBar.setMousePressed(!volumeBar.isMousePressed());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)){
            if(musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
            }
        else if (isIn(e, sfxButton))
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        if (isIn(e, resumeButton)){
            if(resumeButton.isMousePressed()){
                Playing.setPaused(false);
            }
        }
        else if (isIn(e, menuButton))
            if(menuButton.isMousePressed()){
                Playing.setPaused(false);
                Gamestate.state = Gamestate.MENU;
            }
        
        resumeButton.reset();
        menuButton.reset();
        musicButton.reset();
        sfxButton.reset();
     
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);

        if (isIn(e, resumeButton))
            resumeButton.setMouseOver(true);
        else if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        
        if (volumeBar.isMousePressed()){
            int volumeX = e.getX();
            int minVolumeX = volumeBar.getX();
            int maxVolumeX = volumeBar.getX() + volumeBar.getWidth()-5;
            volumeBar.setVolumeX(Math.max(minVolumeX, Math.min(maxVolumeX, volumeX)));
        }
    }

    public boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }


}

