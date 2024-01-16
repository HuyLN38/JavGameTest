package gamestates;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.GRASSLAND.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

import entities.Enemy;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager LevelManager;
    private EnemyManager EnemyManager;
    private PauseOverlay pauseOverlay;
    public static boolean paused = false;
    private GameOverOverlay gameOverOverlay;
    private boolean GameOver = false;

    private int xLevelOffSet;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int LevelTitesWide = LoadSave.GetLevelData().length;
    private int maxTitlesOffSet = LevelTitesWide - Game.TILE_IN_WIDTH;
    private int maxLevelOffSetX = maxTitlesOffSet * Game.TILES_SIZE;

    private BufferedImage Sky, BigCloud, SmallCloud, Ground, Hills;
    private int[] SmallCloudPos;

    public Playing(Game game) {

        super(game);
        initClasses();

        Sky = LoadSave.GetSpriteAtlast(LoadSave.GRASSLAND_BACKGROUND);
        BigCloud = LoadSave.GetSpriteAtlast(LoadSave.GRASSLAND_BIGCLOUD);
        Ground = LoadSave.GetSpriteAtlast(LoadSave.GRASSLAND_GROUND);
        Hills = LoadSave.GetSpriteAtlast(LoadSave.GRASSLAND_HILLS);
        SmallCloud = LoadSave.GetSpriteAtlast(LoadSave.GRASSLAND_SMALLCLOUD);
        SmallCloudPos = new int[8];
        for (int i = 0; i < SmallCloudPos.length; i++) {
            SmallCloudPos[i] = (int) (Math.random() * (200 - 100) + 100);
        }

    }

    private void initClasses() {
        LevelManager = new LevelManager(game);
        EnemyManager = new EnemyManager(this);

        player = new Player(250, 250, (int) (68 * Game.SCALE), (int) (68 * Game.SCALE), this);
        player.loadLvData(LevelManager.getLevel().getLevelData());
        pauseOverlay = new PauseOverlay();
        gameOverOverlay = new GameOverOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            LevelManager.update();
            EnemyManager.update(LevelManager.getLevel().getLevelData(), player);
            player.update();
            checkBorder();
        }
        pauseOverlay.update();

    }

    private void checkBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffSet;

        if (diff > rightBorder) {
            xLevelOffSet += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffSet += diff - leftBorder;
        }

        if (xLevelOffSet > maxLevelOffSetX) {
            xLevelOffSet = maxLevelOffSetX;
        } else if (xLevelOffSet < 0) {
            xLevelOffSet = 0;
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(Sky, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawSmallCloud(g);
        drawEnvironment(g);

        LevelManager.draw(g, xLevelOffSet);

        EnemyManager.draw(g, xLevelOffSet);

        player.render(g, xLevelOffSet);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (GameOver) {
            gameOverOverlay.draw(g);
        }

    }

    private void drawSmallCloud(Graphics g) {
        for (int i = 0; i < SmallCloudPos.length; i++) {
            g.drawImage(SmallCloud, 0 + i * GRASSLAND_WIDTH - (int) (xLevelOffSet * 0.3), SmallCloudPos[i],
                    GRASSLAND_WIDTH, GRASSLAND_HEIGHT,
                    null);
        }
    }

    private void drawEnvironment(Graphics g) {

        for (int i = 0; i < 4; i++) {
            g.drawImage(BigCloud, 0 + i * GRASSLAND_WIDTH - (int) (xLevelOffSet * 0.3), (int) (130 * Game.SCALE),
                    GRASSLAND_WIDTH, GRASSLAND_HEIGHT,
                    null);
            g.drawImage(Hills, 0 + i * GRASSLAND_WIDTH - (int) (xLevelOffSet * 0.3), (int) (160 * Game.SCALE),
                    GRASSLAND_WIDTH, GRASSLAND_HEIGHT,
                    null);
            g.drawImage(Ground, 0 + i * GRASSLAND_WIDTH - (int) (xLevelOffSet * 0.3), (int) (160 * Game.SCALE),
                    GRASSLAND_WIDTH, GRASSLAND_HEIGHT,
                    null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!GameOver)
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.SetAttack(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!GameOver)
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!GameOver)
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!GameOver)
        if (paused)
            pauseOverlay.mouseMoved(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!GameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    player.setDir(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    player.setDir(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!GameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
                case KeyEvent.VK_P:
                    setPaused(true);
                    break;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public static Gamestate getState() {
        return Gamestate.state;
    }

    public static void setPaused(boolean pause) {
        paused = pause;
    }

    public boolean isPaused() {
        return paused;
    }

    public void windowFocusLost() {
        player.resetDirBoolean();
    }

    public void resetAll() {

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        EnemyManager.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean GameOver) {
        this.GameOver = GameOver;

    }

}
