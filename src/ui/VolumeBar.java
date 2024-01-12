package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utilz.LoadSave;
import java.awt.event.MouseEvent;

import static utilz.Constants.UI.VolumeBar.*;

public class VolumeBar extends PauseButton {

    private BufferedImage volumebar, volumeset;
    private final int volumemin = 0, volumemax = 100;
    private int volumex, volume;
    private boolean mousePressed;

    public VolumeBar(int x, int y, int width, int height) {
        super(x, y, width, height);

        volumex = x + VOLUME_BAR_LENGTH - 5;

        loadVolumeImages();
    }

    private void loadVolumeImages() {
        volumebar = LoadSave.GetSpriteAtlast(LoadSave.VOLUME_BAR);
        volumeset = LoadSave.GetSpriteAtlast(LoadSave.VOLUME_SET);
    }

    public void update() {
        volume = (int) (((double)(volumex - x) / VOLUME_BAR_LENGTH) * 100);
        if (volume < volumemin) {
            volume = volumemin;
            volumex = (int) (x + (volume / 100.0 * VOLUME_BAR_LENGTH));
        } else if (volume > volumemax) {
            volume = volumemax;
            volumex = (int) (x + (volume / 100.0 * VOLUME_BAR_LENGTH));
        } else {
            volumex = (int) (x + (volume / 100.0 * VOLUME_BAR_LENGTH));
        }
    }

    public void draw(Graphics g) {
        g.drawImage(volumebar, x, y, width, height, null);
        g.drawImage(volumeset, volumex, y - (int) (y * 1 / 100), VOLUME_SET_LENGTH, VOLUME_SET_HEIGHT, null);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolumeX(int volumex) {
        this.volumex = volumex;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public int getX(){
        return x;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

}
