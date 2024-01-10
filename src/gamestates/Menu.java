package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundGUI;
    private int MenuX, MenuY, MenuWidth, MenuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundGUI = LoadSave.GetSpriteAtlast(LoadSave.MENU_BACKGROUND);
        MenuWidth = (int)(backgroundGUI.getWidth() * Game.SCALE*5);
        MenuHeight = (int)(backgroundGUI.getHeight() * Game.SCALE*5);
        MenuX = (Game.GAME_WIDTH - MenuWidth)/2;
        MenuY = (Game.GAME_HEIGHT - MenuHeight)/2;
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2, (int) ( 130 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int) ( 200 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int) ( 270 * Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {

        for(MenuButton button : buttons)
            button.update();

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundGUI, MenuX, MenuY, MenuWidth, MenuHeight, null);

        for (MenuButton button : buttons)
            button.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
            
            for (MenuButton button : buttons )
                if (isIn(e, button)) {
                    button.setMousePressed(true);
                    break;
                }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
         for (MenuButton button : buttons)
                if (isIn(e, button)) {
                    if(button.isMousePressed())
                        button.applyGamestate();
                    break;
                }
                resetButtons();

    }

    private void resetButtons() {
        for (MenuButton button : buttons)
            button.setMousePressed(false);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons)
            if (isIn(e, button)) {
                button.setMouseOver(true);
                break;
            } else
                button.setMouseOver(false);


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
    
    
}
