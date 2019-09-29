import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.*;

public class lab5 {

    // Declaring EXCEPTIONS

    public static abstract class negative_exception extends RuntimeException implements Serializable {
        negative_exception(String sound, String type, int no_of_tiles) {
            super(sound + "...! I am a " + type +
                    ", you go back " + -1*no_of_tiles + " tiles");
        }
    }

    public static class NotOutofCage extends RuntimeException implements Serializable {
        NotOutofCage() {
            super("OOPs you need 6 to start... still in cage");
        }
    }

    public static class SnakeBiteException extends negative_exception {
        SnakeBiteException (int no_of_tiles) {
            super("Hiss","Snake",no_of_tiles);
        }
    }

    public static class CricketBiteException extends negative_exception {
        CricketBiteException (int no_of_tiles) {
            super("Chirp","Cricket",no_of_tiles);
        }
    }

    public static class VultureBiteException extends negative_exception {
        VultureBiteException (int no_of_tiles) {
            super("Yapping","Vulture",no_of_tiles);
        }
    }

    public static class TrampolineException extends RuntimeException implements Serializable{
        TrampolineException(int no_of_tiles) {
            super("Ping Pong! I am a Trampoline, you advance " +
                    no_of_tiles + " tiles");
        }
    }

    public static class GameWinnerException extends RuntimeException implements Serializable{
        GameWinnerException() {
            super("Game won...");
        }
    }
    // ------------- exceptions over -----------------------

    public static abstract class tile implements Serializable {
        private final int moves_allowed;
        private final String type;

        public tile(int moves_allowed, String type) {
            this.moves_allowed = moves_allowed;
            this.type = type;

        }

        public int getMoves_allowed() {
            return moves_allowed;
        }

        public String getType() {
            return type;
        }

        public abstract void shake() throws RuntimeException;
    }

    public static abstract class sound_tile extends tile {
        private final String sound;

        sound_tile(int moves_allowed, String type, String sound) {
            super(moves_allowed,type);
            this.sound = sound;
        }

        public String getSound() {
            return sound;
        }
    }

    public static class snake extends sound_tile {

        snake(int moves_allowed) {
            super(-1*moves_allowed,"Snake","Hiss");
        }

        @Override
        public void shake() throws SnakeBiteException {
            throw new SnakeBiteException(getMoves_allowed());
        }
    }
    public static class vulture extends sound_tile {
        vulture(int moves_allowed) {
            super(-1*moves_allowed, "Vulture", "Yapping");
        }

        @Override
        public void shake() throws VultureBiteException {
            throw new VultureBiteException(getMoves_allowed());
        }
    }

    public static class cricket extends sound_tile {
        cricket(int moves_allowed) {
            super(-1*moves_allowed, "Cricket", "Chirp");
        }

        @Override
        public void shake() throws CricketBiteException {
            throw new CricketBiteException(getMoves_allowed());
        }
    }

    public static class trampoline extends sound_tile {
        trampoline(int moves_allowed) {
            super(moves_allowed,"Trampoline","PingPong");
        }

        @Override
        public void shake() throws TrampolineException {
            throw new TrampolineException(getMoves_allowed());
        }

    }

    public static class white extends tile {
        white() {
            super(0,"White");
        }

        @Override
        public void shake() {
            System.out.println("I am a white tile!");
        }
    }


    // creating race_track class

    public static class race_track implements Serializable {
        private final int total_tiles;
        private  final List<tile> tiles;

        private static final long serialVersionUID = 1L;

        // tiles which will be used in the race_track
        private final snake snake_tile;
        private final vulture vulture_tile;
        private final trampoline trampoline_tile;
        private final cricket cricket_tile;
        private static transient white w;

        private int no_of_snake_tiles;
        private int no_of_cricket_tiles;
        private int no_of_vulture_tiles;
        private int no_of_trampoline_tiles;

        @Override
        public String toString() {
            return "race_track{" +
                    "no_of_snake_tiles=" + no_of_snake_tiles +
                    ", no_of_cricket_tiles=" + no_of_cricket_tiles +
                    ", no_of_vulture_tiles=" + no_of_vulture_tiles +
                    ", no_of_trampoline_tiles=" + no_of_trampoline_tiles +
                    '}';
        }

        race_track(int total_tiles) {
            this.total_tiles = total_tiles;

            //creating initial tiles
            Random r = new Random();
            int randomInteger;

            randomInteger = r.nextInt(10);
            snake_tile = new snake(randomInteger+1);

            randomInteger = r.nextInt(10);
            vulture_tile = new vulture(randomInteger+1);

            randomInteger = r.nextInt(10);
            trampoline_tile = new trampoline(randomInteger+1);

            randomInteger = r.nextInt(10);
            cricket_tile = new cricket(randomInteger+1);

            this.w = new white();

            // adding tiles to  "tiles array-list"
            this.tiles = new ArrayList<>();
            for(int i=0;i<total_tiles;i++) {
                randomInteger = r.nextInt(5);
                if(randomInteger==0) {
                    tiles.add(w);
                }
                else if(randomInteger==1) {
                    tiles.add(snake_tile);
                    no_of_snake_tiles++;
                }
                else if(randomInteger==2) {
                    tiles.add(vulture_tile);
                    no_of_vulture_tiles++;
                }
                else if(randomInteger==3) {
                    tiles.add(trampoline_tile);
                    no_of_trampoline_tiles++;
                }
                else if(randomInteger==4) {
                    tiles.add(cricket_tile);
                    no_of_cricket_tiles++;
                }
            }
        }



        public int getNo_of_snake_tiles() { return no_of_snake_tiles; }

        public void setNo_of_snake_tiles(int no_of_snake_tiles) { this.no_of_snake_tiles = no_of_snake_tiles; }

        public int getNo_of_cricket_tiles() { return no_of_cricket_tiles; }

        public void setNo_of_cricket_tiles(int no_of_cricket_tiles) { this.no_of_cricket_tiles = no_of_cricket_tiles; }

        public int getNo_of_vulture_tiles() { return no_of_vulture_tiles; }

        public void setNo_of_vulture_tiles(int no_of_vulture_tiles) { this.no_of_vulture_tiles = no_of_vulture_tiles; }

        public int getNo_of_trampoline_tiles() { return no_of_trampoline_tiles; }

        public void setNo_of_trampoline_tiles(int no_of_trampoline_tiles) { this.no_of_trampoline_tiles = no_of_trampoline_tiles; }

        public int getTotal_tiles() { return total_tiles; }

        public List<tile> getTiles() { return tiles; }

        public snake getSnake_tile() { return snake_tile; }

        public vulture getVulture_tile() { return vulture_tile; }

        public trampoline getTrampoline_tile() { return trampoline_tile; }

        public cricket getCricket_tile() { return cricket_tile; }
    }



    //creating computer

    public static class computer {
        private static dice d;

        computer() {
            d = new dice();
        }

        public dice getD() {
            return d;
        }

        private void shake_tile(tile t, int tile_no) throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException {
            System.out.println("Trying to shake the Tile-" + tile_no);
            t.shake();
        }

        private void display_start_roll(user u, int value_on_dice)  throws NotOutofCage {
            System.out.print("[Roll-" + u.getTotal_moves() + "]: " +
                    u.getName() + " rolled " + value_on_dice +
                    " at Tile-" + u.getCurrent_position() + ", ");

            if(value_on_dice!=6) {
                throw new NotOutofCage();
            }
            else {
                System.out.println("You are out of cage, You get a free roll");
            }
        }


        public void gameWon() throws GameWinnerException {
            throw new GameWinnerException();
        }


        private int display_roll(user u, int value_on_dice) {
            // return 1 = moving ahead
            // return 0 = NOT moving ahead
            int to_return = 0;
            System.out.print("[Roll-" + u.getTotal_moves() + "]: " +
                    u.getName() + " rolled " + value_on_dice +
                    " at Tile-" + u.getCurrent_position() + ",landed on Tile ");

            if(u.getCurrent_position()+value_on_dice<=u.getTrack().getTotal_tiles()) {

                u.setCurrent_position(u.getCurrent_position()+value_on_dice);
                to_return = 1;
            }
            System.out.println(u.getCurrent_position());
            return to_return;
        }


        public void play(user u) throws GameWinnerException {
            int value_on_dice;
            int before_roll_position;


            while(true) {
                value_on_dice = getD().roll_dice(u);
                before_roll_position = u.getCurrent_position();

                if(u.getCurrent_position()==1) {
                    try {
                        display_start_roll(u, value_on_dice);

                        // no error => got 6
                        value_on_dice = getD().roll_dice(u);
                        display_roll(u, value_on_dice);

                        tile t = u.getTrack().getTiles().get(u.getCurrent_position()-1);
                        int to_move = 0;

                        try {
                            shake_tile(t,u.getCurrent_position());

                            // no error => new location is white tile
                            to_move = 0;
                        }
                        catch (SnakeBiteException e) {
                            System.out.println(e.getMessage());

                            //u.snake_bites++;
                            u.setSnake_bites(u.getSnake_bites()+1);

                            to_move = u.getTrack().getSnake_tile().getMoves_allowed();
                        }
                        catch (VultureBiteException e) {
                            System.out.println(e.getMessage());
                            //u.vulture_bites++;
                            u.setVulture_bites(u.getVulture_bites()+1);
                            to_move = u.getTrack().getVulture_tile().getMoves_allowed();
                        }
                        catch (CricketBiteException e) {
                            System.out.println(e.getMessage());
                            //u.cricket_bites++;
                            u.setCricket_bites(u.getCricket_bites()+1);
                            to_move = u.getTrack().getCricket_tile().getMoves_allowed();
                        }
                        catch (TrampolineException e) {
                            System.out.println(e.getMessage());
                            //u.trampoline++;
                            u.setTrampoline(u.getTrampoline()+1);
                            to_move = u.getTrack().getTrampoline_tile().getMoves_allowed();
                        }
                        catch (Exception e) {
                            System.out.println("any other error");
                        }
                        finally {
                            u.move(to_move);
                        }
                    }
                    catch (NotOutofCage e) {
                        System.out.println(e.getMessage());
                    }
                    finally { continue; }
                }

                if(value_on_dice+u.getCurrent_position()==u.getTrack().getTotal_tiles()) {
                    display_roll(u, value_on_dice);
                    // won the game
                    gameWon();
                }

                // normal rolling .... in intermediate steps...

                int rolled = display_roll(u, value_on_dice);
                if(rolled!=1) {
                    continue;
                }
                tile t = u.getTrack().getTiles().get(u.getCurrent_position()-1);
                int to_move = 0;

                try {
                    shake_tile(t,u.getCurrent_position());

                    // no error => new location is white tile
                    to_move = 0;
                }
                catch (SnakeBiteException e) {
                    System.out.println(e.getMessage());
                    //u.snake_bites++;
                    u.setSnake_bites(u.getSnake_bites()+1);
                    to_move = u.getTrack().getSnake_tile().getMoves_allowed();
                }
                catch (VultureBiteException e) {
                    System.out.println(e.getMessage());
                    //u.vulture_bites++;
                    u.setVulture_bites(u.getVulture_bites()+1);
                    to_move = u.getTrack().getVulture_tile().getMoves_allowed();
                }
                catch (CricketBiteException e) {
                    System.out.println(e.getMessage());
                    //u.cricket_bites++;
                    u.setCricket_bites(u.getCricket_bites()+1);
                    to_move = u.getTrack().getCricket_tile().getMoves_allowed();
                }
                catch (TrampolineException e) {
                    System.out.println(e.getMessage());
                    //u.trampoline++;
                    u.setTrampoline(u.getTrampoline()+1);
                    to_move = u.getTrack().getTrampoline_tile().getMoves_allowed();
                }
                catch (Exception e) {
                    System.out.println("any other error");
                }
                finally {
                    u.move(to_move);

                    // for displaying save/continue option

                    int seventy_five_perc = (int)(0.75*u.getTrack().getTotal_tiles());
                    int fifty_perc = (int)(0.50*u.getTrack().getTotal_tiles());
                    int twenty_five_perc = (int)(0.25*u.getTrack().getTotal_tiles());

                    if(before_roll_position<seventy_five_perc
                            && u.getCurrent_position()>=seventy_five_perc) {
                        save_user_menu(u);
                    }
                    else if(before_roll_position<fifty_perc
                            && u.getCurrent_position()>=fifty_perc) {
                        save_user_menu(u);
                    }
                    else if(before_roll_position<twenty_five_perc
                            && u.getCurrent_position()>=twenty_five_perc) {
                        save_user_menu(u);
                    }
                    // displaying menu ends --------------
                }
            }
        }

        private void save_user_menu(user u) {
            System.out.println("choose one of the following option...");
            System.out.println("1) SAVE");
            System.out.println("2) Continue");

            // to take input from user...
            int in = 0;
            while (true) {
                try {
                    System.out.println("Please enter 1 or 2 ONLY!!!");
                    Scanner s = new Scanner(System.in);
                    in = s.nextInt();
                    if(in==1 || in==2) { break; }
                }
                catch (InputMismatchException e){
                    System.out.println("Wrong Input type...(only INTEGER allowed (1 and 2))");
                    System.out.println("Try again");
                }
            }

            if(in==1) {
                try {
                    serialize(u);
                    System.exit(0);
                }
                catch (Exception e) {
                    System.out.println("Cannot save game (DUE to some error)");
                    System.out.println(e.getMessage());
                }
            }



        }

        public static void serialize(user u) throws IOException {
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream("./saved_objects/" + u.getName() + ".txt"));
                out.writeObject(u);
            }

            finally { out.close(); }
        }

        public static user deserialize(String name) throws IOException{
            ObjectInputStream in = null;
            user u = null;
            try {
                in = new ObjectInputStream(new FileInputStream("./saved_objects/" + name + ".txt"));
                u = (user) in.readObject();
            }
            finally {
                in.close();
                return u;
            }
        }

    }

    public static class dice {
        private final Random r;
        dice() {
            r = new Random();
        }

        public Random getR() {
            return r;
        }

        public int roll_dice(user u) {
            u.setTotal_moves(u.getTotal_moves()+1);
            return getR().nextInt(6) +1;
        }

    }



    // creating user/player class

    public static class user implements Serializable {
        private final String name;
        private final race_track track;
        private int current_position;

        private static final long serialVersionUID = 2L;

        private int total_moves;
        private int last_saved;     // 0 - not saved once
                                    // 25 - saved at 25%
                                    // ..... so on......
        private int snake_bites;
        private int vulture_bites;
        private int cricket_bites;
        private int trampoline;

        public user(String name, race_track track) {
            this.name = name;
            this.track = track;
            this.current_position = 1;
            this.last_saved = 0;
        }

        @Override
        public String toString() {
            return "user{" +
                    "name='" + name + '\'' +
                    ", track=" + track +
                    ", current_position=" + current_position +
                    ", total_moves=" + total_moves +
                    ", last_saved=" + last_saved +
                    ", snake_bites=" + snake_bites +
                    ", vulture_bites=" + vulture_bites +
                    ", cricket_bites=" + cricket_bites +
                    ", trampoline=" + trampoline +
                    '}';
        }

        public boolean equals(Object o) {
            if(o==null) { return false; }
            if(o.getClass() == getClass()) {
                user u = (user)o;
                if(u.getName().equals(getName()) &&
                    u.getCurrent_position()==getCurrent_position() &&
                    u.getTotal_moves() == getTotal_moves() &&
                    u.getSnake_bites() == getSnake_bites() &&
                    u.getCricket_bites() == u.getCricket_bites() &&
                    u.getTrampoline() == getTrampoline() &&
                    u.getVulture_bites()==getVulture_bites())
                {
                    return true;
                }
            }
            return false;


        }

        public String getName() { return name; }

        public int getLast_saved() { return last_saved; }

        public void setLast_saved(int last_saved) { this.last_saved = last_saved; }

        public race_track getTrack() { return track; }

        public int getCurrent_position() { return current_position; }

        public void setCurrent_position(int current_position) { this.current_position = current_position; }

        public int getTotal_moves() { return total_moves; }

        public void setTotal_moves(int total_moves) { this.total_moves = total_moves; }

        public int getSnake_bites() { return snake_bites; }

        public void setSnake_bites(int snake_bites) { this.snake_bites = snake_bites; }

        public int getVulture_bites() { return vulture_bites; }

        public void setVulture_bites(int vulture_bites) { this.vulture_bites = vulture_bites; }

        public int getCricket_bites() { return cricket_bites; }

        public void setCricket_bites(int cricket_bites) { this.cricket_bites = cricket_bites; }

        public int getTrampoline() { return trampoline; }

        public void setTrampoline(int trampoline) { this.trampoline = trampoline; }

        public void move(int no_of_tiles_to_move) {

            if(getCurrent_position()+no_of_tiles_to_move<=0) {
                System.out.println(getName() + " moved to Tile-1, as can't GO further");
                setCurrent_position(1);
            }
            else if(getCurrent_position()+no_of_tiles_to_move>getTrack().getTotal_tiles()) {
                System.out.println(getName() + " cannot move to last tile");
            }
            else {
                setCurrent_position(getCurrent_position()+no_of_tiles_to_move);
                System.out.println(getName() + " moved to Tile-" + getCurrent_position());
            }
        }



        private void print_final_data() {
            System.out.println(getName() + " wins the race in " +
                    getTotal_moves() + " rolls");

            System.out.println("Total Snake bites = " + getSnake_bites());
            System.out.println("Total Vulture bites = " + getVulture_bites());
            System.out.println("Total Cricket bites = " + getCricket_bites());
            System.out.println("Total Trampolines = " + getTrampoline());
        }

        public void Game_start(computer c) {
            try {
                System.out.println("Game started ==============================>");
                c.play(this);
            }
            catch (GameWinnerException e) {
                print_final_data();
            }
            finally {
                System.out.println("game ends .... exiting game ....");
                System.exit(0);
            }
        }
    }

    public static class game_app {
        private final computer c;
        private user u;

        // constructor
        public game_app() {
            this.c = new computer();

            load_continue_menu();
        }


        private void new_user_login() {

            Scanner scan = new Scanner(System.in);
            int tiles = 100;
            System.out.println("Enter total no of tiles on the race track(length)");
            while (true) {
                System.out.println("PLS enter at-least " + tiles);
                try {
                    Scanner s = new Scanner(System.in);
                    tiles = s.nextInt();
                    if(tiles<100) { continue; }
                    else { break; }
                }
                catch (InputMismatchException e){
                    System.out.println("Wrong Input type...(only INTEGER allowed)");
                    System.out.println("Try again");
                }
            }


            System.out.println("Setting up the race track...");
            race_track r = new race_track(tiles);

            System.out.println("Danger: Each Snake, Cricket, & Vulture can throw you back by " +
                    r.getSnake_tile().getMoves_allowed()*-1 + " " +
                    r.getCricket_tile().getMoves_allowed()*-1 + " " +
                    r.getVulture_tile().getMoves_allowed()*-1 + " nos. of tiles respectively");

            System.out.println("Good  news: There are " +
                    r.getNo_of_trampoline_tiles() + " no of Trampolines on your track!");
            System.out.println("Good news: Each Trampoline can help you advance by " +
                    r.getTrampoline_tile().getMoves_allowed() + " no. of tiles.");


            String name = "";

            // to take input from user...
            while (true) {
                System.out.print("Enter player name : ");
                name = scan.next();
                if(notExists(Paths.get("./saved_objects/" + name + ".txt"))) { break; }
                else {
                    System.out.println("User already exists with this name... " +
                            "TRY AGAIN...");
                }
            }


            this.u = new user(name, r);

            System.out.println("Starting the game with " + u.getName() + " at Tile-1");
            System.out.println("Control transferred to Computer for rolling the dice for " + u.getName());

            scan.nextLine();
            while (true) {
                System.out.println("Hit enter to start the game...");
                String a = scan.nextLine();
                if(a.equals("")) { break; }
            }

            u.Game_start(c);
        }

        private void existing_user_login() {
            Scanner scan = new Scanner(System.in);
            String name = "";

            // to take input from user...
            while (true) {
                System.out.print("Enter player name : ");
                name = scan.next();
                if (exists(Paths.get("./saved_objects/" + name + ".txt"))) { break; }
                else {
                    System.out.println("NO user exists with this name... " +
                            "TRY AGAIN...");
                }
            }
            try {
                user u = deserialize(name);
                System.out.println("Starting from last saved game ...");
                u.Game_start(c);
            }
            catch (Exception e) {
                System.out.println("some major error occurred... Exiting the game");
                System.exit(0);
            }
        }

        private static user deserialize(String name) throws IOException, ClassNotFoundException{
            ObjectInputStream in = null;
            user u = null;
            try {
                in = new ObjectInputStream(new FileInputStream("./saved_objects/" + name + ".txt"));
                u = (user) in.readObject();
            }
            finally {
                in.close();
                return u;
            }
        }

        private void load_continue_menu() {
            Scanner scan = new Scanner(System.in);

            System.out.println("Choose one of the following option...");
            System.out.println("1) New user");
            System.out.println("2) Existing user");

            int in;

            // to take input from user...
            while (true) {
                try {
                    System.out.println("Please enter 1 or 2 ONLY!!!");
                    Scanner s = new Scanner(System.in);
                    in = s.nextInt();
                    if(in==1 || in==2) { break; }
                }
                catch (InputMismatchException e){
                    System.out.println("Wrong Input type...(only INTEGER allowed (1 and 2))");
                    System.out.println("Try again");
                }
            }

            //----

            if(in==1) { new_user_login(); }
            else { existing_user_login(); }

        }
    }



    public static void main(String[] args) {
        game_app g = new game_app();
    }


}
