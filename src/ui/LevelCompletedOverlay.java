package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

import gamestates.Playing;
import main.Game;

public class LevelCompletedOverlay {

    private Playing Playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int x, y, width, height;

    public LevelCompletedOverlay(Playing playing) {
        this.Playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        img = LoadSave.GetSpriteAtlast(LoadSave.URM_BUTTONS);
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 2);

    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlast(LoadSave.LEVEL_COMPLETED);
        width = (int) (img.getWidth() * Game.SCALE);
        height = (int) (img.getHeight() * Game.SCALE);
        x = (Game.GAME_WIDTH / 2) - (width / 2);
        y = (int) (75 * Game.SCALE);

    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
        menu.draw(g);
        next.draw(g);
    }

    public void update() {
        menu.update();
        next.update();
    }

    public boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMouseOver(true);
        else
            menu.setMouseOver(false);
        if (isIn(next, e))
            next.setMouseOver(true);
        else
            next.setMouseOver(false);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        if (isIn(next, e))
            next.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e))
            if (menu.isMousePressed())
                System.out.println("Menu");
        if (isIn(next, e))
            if (next.isMousePressed()) {
                System.out.println("Next");
            }
        menu.resetBools();
        next.resetBools();
    }

}
