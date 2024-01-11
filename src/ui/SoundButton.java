package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButton.*;

public class SoundButton extends PauseButton{

    private int rowIndex, colIndex;
    private boolean mouseOver, mousePressed;
    private boolean muted = false ;

    private BufferedImage[][] soundImages;


    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    
        loadSoundImages();
      
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlast(LoadSave.SOUND_BUTTON);
        soundImages = new BufferedImage[2][3];
        for (int j = 0; j < 2; j++) 
            for (int i = 0; i < 3; i++) {
                soundImages[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT,  SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    public void update(){
            if(muted){
                // System.out.println("muted");
                rowIndex = 1;
            }else{
                // System.out.println("not muted");
                rowIndex = 0;
            }

            colIndex = 0;
            if (mouseOver)
                colIndex = 1;
            if (mousePressed)
                colIndex = 2;
    }

    public void reset(){
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g){
        g.drawImage(soundImages[rowIndex][colIndex], x, y, SOUND_SIZE, SOUND_SIZE, null);
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
    
    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public BufferedImage[][] getSoundImages() {
        return soundImages;
    }

    public void setSoundImages(BufferedImage[][] soundImages) {
        this.soundImages = soundImages;
    }

    public boolean MouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

}
