package ui;

import java.awt.image.BufferedImage;

import java.awt.Graphics;

import utilz.LoadSave;

import static utilz.Constants.UI.PauseMenu.*;

public class Buttons extends PauseButton {

    private BufferedImage[][] imgs;
    private int rowIndex, colIndex;
    private boolean mouseOver, mousePressed;

    public Buttons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);

        this.rowIndex = rowIndex;
        
        loadSoundImages();
        
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlast(LoadSave.BUTTON_MENU);
        imgs = new BufferedImage[2][3];
        for (int j = 0; j < 2; j++) 
            for (int i = 0; i < 3; i++) {
                imgs[j][i] = temp.getSubimage(i * BUTTON_WIDTH_DEFAULT, j * BUTTON_HEIGHT_DEFAULT,  BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
            }
    }

    public void update(){
        colIndex = 0;
        if (mouseOver)
            colIndex = 1;
        if (mousePressed)
            colIndex = 2;
    }

    public void draw(Graphics g){
        g.drawImage(imgs[rowIndex][colIndex], x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void reset(){
        mouseOver = false;
        mousePressed = false;
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
