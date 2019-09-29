import static org.junit.Assert.*;

import com.sun.source.tree.AssertTree;
import org.junit.*;
import java.io.*;

import java.io.IOException;


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

    public static class testSerialization {

        public static lab5.user u;
        public static lab5.computer c;

        @Before
        public void make_user() {
            u = new lab5.user("demo_user", new lab5.race_track(100));
            u.setTrampoline(5);
            u.setCricket_bites(6);
            u.setSnake_bites(0);
            u.setVulture_bites(10);
            u.setCurrent_position(25);
            u.setTotal_moves(28);

            c = new lab5.computer();
        }

        @Test(timeout = 3000)
        public void testSerialize() throws IOException {
            //check that serialization NOT causing any error (i.e. file creating properly)
            c.serialize(u);
        }

        @Test(timeout = 6000)
        public void testProperSerialization() throws IOException {
            //check that serialization and deserialized file is same as "u"...

            c.serialize(u);
            lab5.user extracted_user = c.deserialize("demo_user");
            Assert.assertTrue("Deserialized not instance of user",extracted_user instanceof lab5.user);
            Assert.assertEquals("Both sent and extracted NOT equals",extracted_user, u);
        }

        @After
        public void del_file() {
            File file = new File("saved_objects/demo_user.txt");
            file.delete();
        }



    }

}






