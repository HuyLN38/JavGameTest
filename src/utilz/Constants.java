package utilz;

public class Constants {

    public static class Direction {

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }

    public static class PlayerConstants {

        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int RUNNING = 2;
        public static final int RUNNING2 = 3;
        public static final int JUMPING = 4;
        public static final int ATTACK = 5;
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
                case ATTACK:
                    return 5;
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
