package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.*;

import gamestates.Gamestate;
import utilz.LoadSave;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private Gamestate state;
    private BufferedImage[] imgs;
    private int xOffsetCenter = MENU_WIDTH/2;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, MENU_WIDTH , MENU_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlast(LoadSave.MENU_BUTTON);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT * rowIndex, MENU_BUTTON_WIDTH,MENU_BUTTON_HEIGHT);
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter , yPos, MENU_WIDTH, MENU_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver){
            index = 1;
            if (mousePressed)
                index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }
}
