package utilz;

import main.Game;

public class Constants {
    public static class GRASSLAND{
        public static final int GRASSLAND_WIDTH_DEFAULT = 288;
        public static final int GRASSLAND_WIDTH = (int)(GRASSLAND_WIDTH_DEFAULT * Game.SCALE*1.2);
        public static final int GRASSLAND_HEIGHT_DEFAULT = 208;
        public static final int GRASSLAND_HEIGHT = (int)(GRASSLAND_HEIGHT_DEFAULT * Game.SCALE*1.2);

    }

    public static class Direction {

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }

    public static class UI{

        public static final int MENU_BUTTON_WIDTH = 31;
        public static final int MENU_BUTTON_HEIGHT = 15;
        public static final int MENU_WIDTH = (int)(MENU_BUTTON_WIDTH * Game.SCALE*4);
        public static final int MENU_HEIGHT = (int)(MENU_BUTTON_HEIGHT * Game.SCALE*4);

        public static final int MENU_BUTTON = 0;
        public static final int MENU_BUTTON_HOVER = 1;
        public static final int MENU_BUTTON_PRESSED = 2;
    
            public static class PauseButton{
        public static final int SOUND_SIZE_DEFAULT = 14;
        public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE*2);
    }
            public static class VolumeBar{
        public static final int VOLUME_BAR_DEFAULT_LENGTH = 50;
        public static final int VOLUME_BAR_DEFAULT_HEIGHT = 5;
        public static final int VOLUME_BAR_LENGTH = (int)(VOLUME_BAR_DEFAULT_LENGTH * Game.SCALE*2);
        public static final int VOLUME_BAR_HEIGHT = (int)(VOLUME_BAR_DEFAULT_HEIGHT * Game.SCALE*2);
        public static final int VOLUME_SET_LENGTH = (int)(VOLUME_BAR_LENGTH/25);
        public static final int VOLUME_SET_HEIGHT = (int)(VOLUME_BAR_HEIGHT*1.5);
    }
        public static class PauseMenu{
        public static final int BUTTON_WIDTH_DEFAULT = 46;
        public static final int BUTTON_WIDTH = (int)(BUTTON_WIDTH_DEFAULT * Game.SCALE*1.5);
        public static final int BUTTON_HEIGHT_DEFAULT = 14;
        public static final int BUTTON_HEIGHT = (int)(BUTTON_HEIGHT_DEFAULT * Game.SCALE*1.5);
        }
    }

    public static class PlayerConstants {

        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int RUNNING = 2;
        public static final int RUNNING2 = 3;
        public static final int JUMPING = 4;
        public static final int ATTACK[] = {5,6,7,8,10};
        public static final int SHOOT = 12;
        public static final int DEFENSE = 13;
        public static final int CROUCH = 17;
        public static final int VICTORY = 18;
        public static final int HURT = 19;
        public static final int DEATH = 20;

        public static int GetSpiritAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 6;
                case JUMPING:
                    return 2;
                case 5:
                case 6:
                case 7:
                case 8:
                    return 5;
                case 10:
                    return 8;
                case SHOOT:
                    return 4;
                case HURT:
                    return 4;
                case CROUCH:
                    return 6;
                case VICTORY:
                    return 6;
                default:
                    return 1;
            }
        }

    }

}
