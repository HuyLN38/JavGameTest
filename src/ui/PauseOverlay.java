package ui;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.VolumeBar.*;


import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
    
    private BufferedImage background;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private VolumeBar volumeBar;

    public PauseOverlay(){
;  
        loadBackground();
        createSoundButton();
        

    }
    
    private void createSoundButton() {
        int soundX = (int)(430*Game.SCALE);
        int musicY = (int)(165*Game.SCALE);
        int sfxY = (int)(195*Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
        int volumex = (int)((Game.GAME_WIDTH/4 + 50 - VOLUME_BAR_LENGTH/2)*Game.SCALE);
        int volumey = (int)(290*Game.SCALE);
        volumeBar = new VolumeBar(volumex, volumey, VOLUME_BAR_LENGTH, VOLUME_BAR_HEIGHT);
        
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
        

    }

    public void draw(Graphics g) {
        g.drawImage(background, bgX, bgY, bgW, bgH, null);
        
        musicButton.draw(g);
        sfxButton.draw(g);
        volumeBar.draw(g);

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

