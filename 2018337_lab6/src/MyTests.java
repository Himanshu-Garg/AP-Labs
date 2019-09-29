import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MyTests {

    public static class testSnake {

        private lab5.snake s;

        @Test (expected = lab5.SnakeBiteException.class, timeout = 1000)
        public void testShake_neg_input() {
            // for negative moves_allowed
            s = new lab5.snake(-1);
            s.shake();
        }

        @Test (expected = lab5.SnakeBiteException.class, timeout = 1000)
        public void testShake_pos_input() {
            // for negative moves_allowed
            s = new lab5.snake(1);
            s.shake();
        }

        @Test (expected = lab5.SnakeBiteException.class, timeout = 1000)
        public void testShake_0_input() {
            // for negative moves_allowed
            s = new lab5.snake(0);
            s.shake();
        }
    }
    public static class testVulture {

        private lab5.vulture v;

        @Test (expected = lab5.VultureBiteException.class, timeout = 1000)
        public void testShake_neg_input() {
            // for negative moves_allowed
            v = new lab5.vulture(-1);
            v.shake();
        }

        @Test (expected = lab5.VultureBiteException.class, timeout = 1000)
        public void testShake_pos_input() {
            // for negative moves_allowed
            v = new lab5.vulture(1);
            v.shake();
        }

        @Test (expected = lab5.VultureBiteException.class, timeout = 1000)
        public void testShake_0_input() {
            // for negative moves_allowed
            v = new lab5.vulture(0);
            v.shake();
        }
    }

    public static class testCricket {

        private lab5.cricket c;

        @Test (expected = lab5.CricketBiteException.class, timeout = 1000)
        public void testShake_neg_input() {
            // for negative moves_allowed
            c = new lab5.cricket(-1);
            c.shake();
        }

        @Test (expected = lab5.CricketBiteException.class, timeout = 1000)
        public void testShake_pos_input() {
            // for negative moves_allowed
            c = new lab5.cricket(1);
            c.shake();
        }

        @Test (expected = lab5.CricketBiteException.class, timeout = 1000)
        public void testShake_0_input() {
            // for negative moves_allowed
            c = new lab5.cricket(0);
            c.shake();
        }
    }

    public static class testTrampoline {

        private lab5.trampoline t;

        @Test (expected = lab5.TrampolineException.class, timeout = 1000)
        public void testShake_neg_input() {
            // for negative moves_allowed
            t = new lab5.trampoline(-1);
            t.shake();
        }

        @Test (expected = lab5.TrampolineException.class, timeout = 1000)
        public void testShake_pos_input() {
            // for negative moves_allowed
            t = new lab5.trampoline(1);
            t.shake();
        }

        @Test (expected = lab5.TrampolineException.class, timeout = 1000)
        public void testShake_0_input() {
            // for negative moves_allowed
            t = new lab5.trampoline(0);
            t.shake();
        }
    }

    public static class testGameWinner {

        @Test (expected = lab5.GameWinnerException.class, timeout = 1000)
        public void testGameWon() {
            lab5.computer c = new lab5.computer();
            c.gameWon();
        }
    }

}






