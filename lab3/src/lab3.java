import java.awt.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.List;

public class lab3 {

    public static class bad_people {
        private float hp;
        private int level;

        public bad_people(float hp, int level) {
            this.hp = hp;
            this.level = level;
        }

        public float getHp() {
            return hp;
        }

        public void setHp(float hp) {
            this.hp = hp;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void attack(good_people hero) {
            hero.hp -= 10;




        }

        public void revive() {
            this.hp = 50*(level+1);
        }
    }

    public static class goblin extends bad_people{
        goblin() {
            super(100,1);
        }
    }

    public static class zombies extends bad_people{
        zombies() {
            super(150,2);
        }
    }

    public static class fiends extends bad_people{
        fiends() {
            super(200,3);
        }
    }

    public static class lionfang extends bad_people{
        lionfang() {
            super(250,4);
        }

        @Override
        public void attack(good_people hero) {
            Random rand = new Random();
            int randomInteger = rand.nextInt(10);

            if(randomInteger==1) {
                special_attack(hero);
            }
            else {
                super.attack(hero);
            }

        }

        private void special_attack(good_people hero) {
            System.out.println("The monster attacked (SPECIAL) and inflicted " +
                    hero.hp/(float)2 + " damage to you");

            hero.hp = hero.hp/(float)2;
        }

    }



    public static abstract class good_people {
        private int xp;
        private float hp;
        private int level;
        private int moves_to_activate_special;
        private boolean defense_activated;
        private int attack_attribute;
        private int defense_attribute;

        public void refill() {
            hp = 50*(level+1);
            moves_to_activate_special = 3;
            defense_activated = false;
        }


        good_people(int a, int d) {
            this.xp = 0;
            this.hp = 100;
            this.level = 1;
            this.defense_activated = false;
            this.moves_to_activate_special = 3;
            this.attack_attribute = a;
            this.defense_attribute = d;
        }


        public boolean isDefense_activated() {
            return defense_activated;
        }

        public void setDefense_activated(boolean defense_activated) {
            this.defense_activated = defense_activated;
        }

        public int getAttack_attribute() {
            return attack_attribute;
        }

        public void setAttack_attribute(int attack_attribute) {
            this.attack_attribute = attack_attribute;
        }

        public int getDefense_attribute() {
            return defense_attribute;
        }

        public void setDefense_attribute(int defense_attribute) {
            this.defense_attribute = defense_attribute;
        }

        public int getXp() {
            return xp;
        }

        public void setXp(int xp) {
            this.xp = xp;
        }

        public float getHp() {
            return hp;
        }

        public void setHp(float hp) {
            this.hp = hp;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getMoves_to_activate_special() {
            return moves_to_activate_special;
        }

        public void setMoves_to_activate_special(int moves_to_activate_special) {
            this.moves_to_activate_special = moves_to_activate_special;
        }

        public void level_up() {
            if(getLevel()!=4) {
                this.setLevel(this.getLevel()+1);
                this.attack_attribute +=1;
                this.defense_attribute += 1;
            }
            setHp(50*(getLevel()+1));
        }

        public abstract void attack(bad_people enemy);
        public abstract void defense(bad_people enemy);
        public abstract void special_power(bad_people enemy);
    }

    public static class warrior extends good_people {

        warrior() {
            super(10,3);
        }

        @Override
        public void attack(bad_people enemy) {
            int a = getAttack_attribute();
            if(getMoves_to_activate_special()>0) {
                a += 5;
            }

            System.out.println("You attacked and inflicted " +
                    a + " damage to the monster");
            enemy.hp -= a;

            setMoves_to_activate_special(getMoves_to_activate_special()-1);
            if(getMoves_to_activate_special()==0) {
                setDefense_attribute(getDefense_attribute()-5);
            }

        }

        @Override
        public void defense(bad_people enemy) {

            System.out.println("Monster attack will reduced by " + getDefense_attribute() + " in next attack...");

            setDefense_activated(true);
            setMoves_to_activate_special(getMoves_to_activate_special()-1);
            if(getMoves_to_activate_special()==0) {
                setDefense_attribute(getDefense_attribute()-5);
            }
        }

        @Override
        public void special_power(bad_people enemy) {

            System.out.println("Performing special attack...");
            System.out.println("Attack and defense raised by 5");
            setMoves_to_activate_special(3);
            setDefense_attribute(getDefense_attribute()+5);
        }
    }

    public static class mage extends good_people {

        mage() {
            super(5,5);
        }

        @Override
        public void attack(bad_people enemy) {
            float a = getAttack_attribute();
            if(getMoves_to_activate_special()>0) {
                a += ((float)0.05)*enemy.hp;
            }

            System.out.println("You attacked and inflicted " +
                    a + " damage to the monster");
            enemy.hp -= a;

            setMoves_to_activate_special(getMoves_to_activate_special()-1);

        }

        @Override
        public void defense(bad_people enemy) {

            if(getMoves_to_activate_special()>0) {
                float a = ((float)0.05)*enemy.hp;
                System.out.println(" 5% HP reduced of enemy due to special activated");
                enemy.hp -= a;
            }

            System.out.println("Monster attack will reduced by " + getDefense_attribute() + " in next attack...");
            setDefense_activated(true);

            setMoves_to_activate_special(getMoves_to_activate_special()-1);
        }

        @Override
        public void special_power(bad_people enemy) {
            System.out.println("Performing special attack...");
            setMoves_to_activate_special(3);

        }
    }

    public static class thief extends good_people {

        thief() {
            super(6,4);
        }

        @Override
        public void attack(bad_people enemy) {
            int a = getAttack_attribute();

            System.out.println("You attacked and inflicted " +
                    a + " damage to the monster");
            enemy.hp -= a;

            setMoves_to_activate_special(getMoves_to_activate_special()-1);

        }

        @Override
        public void defense(bad_people enemy) {

            System.out.println("Monster attack will reduced by " + getDefense_attribute() + " in next attack...");
            setDefense_activated(true);

            setMoves_to_activate_special(getMoves_to_activate_special()-1);

        }

        @Override
        public void special_power(bad_people enemy) {
            System.out.println("Performing special attack...");
            System.out.println("stealing 30% of opponents HP");
            setMoves_to_activate_special(3);

            float a = (float)0.3 * enemy.hp;
            enemy.setHp(enemy.getHp()-a);
            setHp(getHp()+a);

        }

    }

    public static class healer extends good_people {

        healer() {
            super(4,8);
        }

        @Override
        public void attack(bad_people enemy) {
            float a = getAttack_attribute();

            if(getMoves_to_activate_special()>0) {
                System.out.println("due to special activated, HP increased by 5%");
                setHp( getHp() + (getHp()*(float)0.05) );
            }

            System.out.println("You attacked and inflicted " +
                    a + " damage to the monster");
            enemy.hp -= a;

            setMoves_to_activate_special(getMoves_to_activate_special()-1);
        }

        @Override
        public void defense(bad_people enemy) {
            if(getMoves_to_activate_special()>0) {
                System.out.println("due to special activated, HP increased by 5%");
                setHp( getHp() + (getHp()*(float)0.05) );
            }

            System.out.println("Monster attack will reduced by " + getDefense_attribute() + " in next attack...");
            setDefense_activated(true);

            setMoves_to_activate_special(getMoves_to_activate_special()-1);
        }

        @Override
        public void special_power(bad_people enemy) {
            System.out.println("Performing special attack...");
            setMoves_to_activate_special(3);
        }
    }


    public static class game_map {
        private final List<bad_people> nodes;

        game_map() {
            Random rand = new Random();
            nodes = new ArrayList<>();
            for(int i=0;i<39;i++) {
                int randomInteger = rand.nextInt(3);
                if(randomInteger==0) {
                    nodes.add(new goblin());
                }
                else if(randomInteger==1){
                    nodes.add(new zombies());
                }
                else {
                    nodes.add(new fiends());
                }
            }
            nodes.add(new lionfang());
        }

        public List<bad_people> getNodes() {
            return nodes;
        }
    }

    public static class hero {
        private final String username;
        private final good_people avatar;
        private int current_position;

        public hero(String username, good_people avatar) {
            this.username = username;
            this.avatar = avatar;
            this.current_position = -1;
        }

        public String getUsername() {
            return username;
        }

        public good_people getAvatar() {
            return avatar;
        }

        public int getCurrent_position() {
            return current_position;
        }

        public void setCurrent_position(int current_position) {
            this.current_position = current_position;
        }

        public void display_action_menu() {
            System.out.println("Choose move");
            System.out.println("1) Attack");
            System.out.println("2) Defense");

            if(avatar.moves_to_activate_special<=0) {
                System.out.println("3) Special Attack");
            }
        }

        public boolean action(bad_people enemy) {
            // true -> hero killed enemy
            // false -> enemy killed hero

            Scanner scan = new Scanner(System.in);

            while(true) {
                display_action_menu();
                int move = scan.nextInt();

                if(move==1){
                    System.out.println("You choose to attack");
                    avatar.attack(enemy);
                    System.out.println("Your HP: " + avatar.hp + "/" + 50*(avatar.getLevel()+1) +" | Monsters HP: " + enemy.hp + "/" + 50*(enemy.getLevel()+1));
                }
                else if(move==2) {
                    System.out.println("You choose to defend");
                    avatar.defense(enemy);
                    System.out.println("Your HP: " + avatar.hp + "/" + 50*(avatar.getLevel()+1) + " | Monsters HP: " + enemy.hp + "/" + 50*(enemy.getLevel()+1));
                }
                else if(move==3) {
                    System.out.println("Special power activated");
                    avatar.special_power(enemy);
                    System.out.println("Your HP: " + avatar.hp + "/" + 50*(avatar.getLevel()+1) + " | Monsters HP: " + enemy.hp + "/" + 50*(enemy.getLevel()+1));
                }

                if(enemy.hp<=0) {
                    System.out.println("You killed monster");
                    System.out.println( enemy.getLevel()*20 +" XP awarded");
                    for(int i=0;i<enemy.getLevel();i++) {
                        avatar.level_up();
                    }
                    enemy.revive();
                    avatar.refill();
                    System.out.println("Level UP: new level = " + avatar.getLevel());
                    return true;
                }

                System.out.println("Monster attack !!!");
                enemy.attack(avatar);
                System.out.println("Your HP: " + avatar.hp + "/" + 50*(avatar.getLevel()+1)  + " | Monsters HP: " + enemy.hp + "/" + 50*(enemy.getLevel()+1));

                if(avatar.hp<=0) {
                    System.out.println("Monster killed you ...");
                    System.out.println("Going back to start position");
                    enemy.revive();
                    avatar.refill();
                    return false;
                }
            }

        }

    }

    public static class game {
        List<hero> players;
        List<game_map> maps;

        public game() {
            this.players = new ArrayList<>();
            this.maps = new ArrayList<>();
        }

        public List<hero> getPlayers() {
            return players;
        }

        public void setPlayers(List<hero> players) {
            this.players = players;
        }

        public List<game_map> getMaps() {
            return maps;
        }

        public void setMaps(List<game_map> maps) {
            this.maps = maps;
        }

        private void new_user() {
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter Username : ");
            String name = scan.next();
            String hero_type = "";

            System.out.println("Choose a hero");
            System.out.println("1) Warrior");
            System.out.println("2) Thief");
            System.out.println("3) Mage");
            System.out.println("4) Healer");

            int in = scan.nextInt();

            if(in==1) {
                players.add(new hero(name, new warrior()));
                hero_type = "Warrior";
            }
            else if(in==2) {
                players.add(new hero(name, new thief()));
                hero_type = "thief";
            }
            else if(in==3){
                players.add(new hero(name, new mage()));
                hero_type = "mage";
            }
            else if(in==4) {
                players.add(new hero(name, new healer()));
                hero_type = "healer";
            }

            maps.add(new game_map());
            System.out.println("User Creation done. Username:" + name +
                    ". Hero type: " + hero_type +
                    ". Log in to play the game . Exiting");
        }



        private int existing_user() {
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter Username : ");
            String name = scan.next();

            for(int i=0;i<players.size();i++) {
                if(players.get(i).getUsername().equals(name)) {
                    System.out.println("User Found... logging in ");
                    return i;
                }
            }
            System.out.println("NO user found with this name ...");
            return -1;
        }

        private void display_main_menu() {
            System.out.println("Welcome to ArchLegends");
            System.out.println("Choose your option");
            System.out.println("1) New User");
            System.out.println("2) Existing User");
            System.out.println("3) Exit");
        }

        public void main_menu() {
            Scanner scan = new Scanner(System.in);
            while(true) {
                display_main_menu();
                int i = scan.nextInt();

                if(i==1) {
                    new_user();
                }
                else if(i==2) {
                    int current_player_index = existing_user();
                    if(current_player_index!=-1) {
                        hero_location_menu(current_player_index);
                    }
                }
                else if(i==3) {
                    System.out.println("Exiting ...");
                    break;
                }

            }
        }

        private void hero_location_menu(int current_player_index) {
            hero current_player = players.get(current_player_index);
            System.out.println("Welcome " + current_player.getUsername());
            Scanner scan = new Scanner(System.in);

            while(true)
            {
                if(current_player.getCurrent_position()==-1) {
                    System.out.println("You are at the starting location. " +
                            "Choose path:");
                    System.out.println("1) 0");
                    System.out.println("2) 1");
                    System.out.println("3) 2");
                    System.out.println("Enter -1 to exit");

                    int n = scan.nextInt();

                    if(n==1) {
                        System.out.println("Moving to location 0");
                        bad_people monster = maps.get(current_player_index).nodes.get(0);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position(0);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==2) {
                        System.out.println("Moving to location 1");
                        bad_people monster = maps.get(current_player_index).nodes.get(1);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won, proceed to the next location.");
                            current_player.setCurrent_position(1);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }

                    }
                    else if(n==3) {
                        System.out.println("Moving to location 2");
                        bad_people monster = maps.get(current_player_index).nodes.get(2);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position(2);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }

                    }
                    else if(n==-1) {
                        System.out.println("Going to main menu ...");
                        break;
                    }



                }
                else {
                    System.out.println("You are at location " +
                            current_player.getCurrent_position()+ " Choose path:");
                    System.out.println("1) " + (current_player.getCurrent_position()*3)+3);
                    System.out.println("2) " + (current_player.getCurrent_position()*3)+4);
                    System.out.println("3) " + (current_player.getCurrent_position()*3)+5);
                    System.out.println("4) Go Back");
                    System.out.println("Enter -1 to exit");

                    int n = scan.nextInt();

                    if(n==1) {
                        System.out.println("Moving to location " + (current_player.getCurrent_position()*3)+3);
                        bad_people monster = maps.get(current_player_index).nodes.get((current_player.getCurrent_position()*3)+3);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position((current_player.getCurrent_position()*3)+3);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==2) {
                        System.out.println("Moving to location " + (current_player.getCurrent_position()*3)+4);
                        bad_people monster = maps.get(current_player_index).nodes.get((current_player.getCurrent_position()*3)+4);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position((current_player.getCurrent_position()*3)+4);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==3) {
                        System.out.println("Moving to location " + (current_player.getCurrent_position()*3)+5);
                        bad_people monster = maps.get(current_player_index).nodes.get((current_player.getCurrent_position()*3)+5);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position((current_player.getCurrent_position()*3)+5);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==4) {
                        System.out.println("Moving to location " + ((int)(current_player.getCurrent_position()/3)-1));
                        bad_people monster = maps.get(current_player_index).nodes.get(((int)(current_player.getCurrent_position()/3)-1));
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            System.out.println("Fight won proceed to the next location.");
                            current_player.setCurrent_position(((int)(current_player.getCurrent_position()/3)-1));
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==-1){
                        System.out.println("Going to main menu ...");
                        break;
                    }

                }
            }

        }
    }

    public static void main(String[] args ) {
        game g = new game();
        g.main_menu();




    }












}