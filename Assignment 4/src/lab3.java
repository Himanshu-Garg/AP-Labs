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
            Random r = new Random();
            float attack = -1;
            float upper_bound = getHp()/(float)4;
            while (attack<0 || attack>upper_bound ) {
                attack =  ((float)r.nextGaussian() * upper_bound/2) + upper_bound/2;
            }

            if(hero.isDefense_activated()) {
                attack -= hero.getDefense_attribute();
                hero.setDefense_activated(false);
            }
            if(attack<0) {
                attack=0;
            }

            //hero.hp -= attack;
            hero.setHp(hero.getHp()-attack);
            System.out.println("The monster attacked and inflicted " +
                    attack + " damage to you.");
        }

        public void attack_sidekick(sidekick s, float damage) {
            s.setHp(s.getHp()-damage);
        }

        public void revive() {
            //this.hp = 50*(getLevel()+1);
            this.setHp(50*(getLevel()+1));
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
                    hero.getHp()/(float)2 + " damage to you");

            //hero.hp = hero.hp/(float)2;
            hero.setHp(hero.getHp()/(float)2);
        }

    }


    public static abstract class sidekick implements Comparable<sidekick> {
        private float xp;
        private float hp;
        private float attack_attribute;
        private final String type;
        private final good_people hero;

        public sidekick(float attack_attribute, good_people hero, String type) {
            this.xp = 0;
            this.hp = 100;
            this.attack_attribute = attack_attribute;
            this.hero = hero;
            this.type = type;   // "minion" or "knight"

            System.out.println("XP of sidekick : 0.0" );
            System.out.println("Attack of sidekick : " + attack_attribute);
        }

        public boolean equals(Object o) {
            if(o==null) {return false;}
            if(o instanceof sidekick){
                sidekick oo = (sidekick)o;
                if(oo.xp==xp && oo.hp==hp && oo.attack_attribute==attack_attribute) {
                    return true;
                }
                else {return false;}
            }
            return false;
        }

        public String getType() {
            return type;
        }

        public float getXp() {
            return xp;
        }

        public void setXp(float xp) {
            this.xp = xp;
        }

        public float getHp() {
            return hp;
        }

        public void setHp(float hp) {
            this.hp = hp;
        }

        public float getAttack_attribute() {
            return attack_attribute;
        }

        public void setAttack_attribute(float attack_attribute) {
            this.attack_attribute = attack_attribute;
        }

        public good_people getHero() {
            return hero;
        }

        @Override
        public int compareTo(sidekick o) {

            if(o.getXp()==getXp()) { return 0; }
            else if(getXp() > o.getXp()) { return 1; }
            else { return -1;}

        }

        public void refill(float offered_xp) {
            setHp(100);
            setXp(getXp()+offered_xp);

        }


        public void attack(bad_people enemy) {
            float a = (int)(getXp()/5) + getAttack_attribute();
            System.out.println("Sidekick attacked and inflicted " +
                    a + " damage to the monster");

            enemy.setHp(enemy.getHp()-a);
        }


    }

    public static class minions extends sidekick implements Cloneable {

        private boolean can_clone;

        minions (float xp_to_buy, good_people hero) {
            super(1+((xp_to_buy-5)/2),hero,"minion");
            can_clone = true;
        }

        public boolean isCan_clone() {
            return can_clone;
        }

        public void setCan_clone(boolean can_clone) {
            this.can_clone = can_clone;
        }

        public List<sidekick> cloning() {
            List<sidekick> cloned = new ArrayList<>();
            if(can_clone) {
                try {
                    for(int i=0;i<3;i++) {
                        minions c = (minions) super.clone();
                        cloned.add(c);
                    }
                }
                catch (CloneNotSupportedException e) {
                    return cloned;
                }
            }

            setCan_clone(false);
            return cloned;
        }



    }

    public static class knight extends sidekick {

        knight (float xp_to_buy, good_people hero) {
            super(2+((xp_to_buy-5)/2),hero,"knight");
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
        private List<sidekick> sidekicks;

        public void refill() {
            setHp(50*(getLevel()+1));
            setMoves_to_activate_special(3);
            setDefense_activated(false);
        }


        good_people(int a, int d) {
            this.xp = 0;
            this.hp = 100;
            this.level = 1;
            this.defense_activated = false;
            this.moves_to_activate_special = 3;
            this.attack_attribute = a;
            this.defense_attribute = d;
            sidekicks = new ArrayList<>();
        }

        public List<sidekick> getSidekicks() {
            return sidekicks;
        }

        public void setSidekicks(List<sidekick> sidekicks) {
            this.sidekicks = sidekicks;
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

        public void add_sidekick(sidekick s) {

            if(sidekicks.size()>=4) {
                System.out.println("Warning - Already contains at least 4 sidekicks");
                System.out.println("But still added another one...");
            }
            sidekicks.add(s);
        }

        public void level_up() {
            if(getLevel()!=4) {
                this.setLevel(this.getLevel()+1);
                //this.attack_attribute +=1;
                setAttack_attribute(getAttack_attribute()+1);
                //this.defense_attribute += 1;
                setDefense_attribute(getDefense_attribute()+1);
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
            //enemy.hp -= a;
            enemy.setHp(enemy.getHp()-a);

            setMoves_to_activate_special(getMoves_to_activate_special()-1);

        }

        @Override
        public void defense(bad_people enemy) {

            if(getMoves_to_activate_special()>0) {
                float a = ((float)0.05)*enemy.hp;
                System.out.println(" 5% HP reduced of enemy due to special activated");
                //enemy.hp -= a;
                enemy.setHp(enemy.getHp()-a);
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
            //enemy.hp -= a;
            enemy.setHp(enemy.getHp()-a);

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

            float a = (float)0.3 * enemy.getHp();
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
            //enemy.hp -= a;
            enemy.setHp(enemy.getHp()-a);

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

            System.out.println("------------------------------------------------------------------");
            System.out.print(" Best path(-1=start | 39=boss) : ");
            print_hint(nodes);
            System.out.println("------------------------------------------------------------------");
        }

        public List<bad_people> getNodes() {
            return nodes;
        }


        // for Bonus ......................

        // Data structure to store graph edges
        class Edge
        {
            int source, dest, weight;

            public Edge(int source, int dest, int weight) {
                this.source = source;
                this.dest = dest;
                this.weight = weight;
            }
        }
        // Data structure to store heap nodes
        class Node
        {
            int vertex, weight;

            public Node(int vertex, int weight) {
                this.vertex = vertex;
                this.weight = weight;
            }
        }
        class Graph
        {
            // A List of Lists to represent an adjacency list
            List<List<Edge>> adjList = null;

            // Constructor
            Graph(List<Edge> edges, int N)
            {
                adjList = new ArrayList<>(N);

                for (int i = 0; i < N; i++) {
                    adjList.add(i, new ArrayList<>());
                }

                // add edges to the undirected graph
                for (Edge edge: edges) {
                    adjList.get(edge.source).add(edge);
                }
            }
        }
        private void printRoute(int prev[], int i)
        {
            if (i < 0)
                return;

            printRoute(prev, prev[i]);
            System.out.print(i-1 + " ");
        }

        // Run Dijkstra's algorithm on given graph
        private void shortestPath(Graph graph, int source, int N)
        {
            // create min heap and push source node having distance 0
            PriorityQueue<Node> minHeap = new PriorityQueue<>(
                    (lhs, rhs) -> lhs.weight - rhs.weight);

            minHeap.add(new Node(source, 0));

            // set infinite distance from source to v initially
            List<Integer> dist = new ArrayList<>(
                    Collections.nCopies(N, Integer.MAX_VALUE));

            // distance from source to itself is zero
            dist.set(source, 0);

            // boolean array to track vertices for which minimum
            // cost is already found
            boolean[] done = new boolean[N];
            done[0] = true;

            // stores predecessor of a vertex (to print path)
            int prev[] = new int[N];
            prev[0] = -1;

            // run till minHeap is not empty
            while (!minHeap.isEmpty())
            {
                // Remove and return best vertex
                Node node = minHeap.poll();

                // get vertex number
                int u = node.vertex;

                // do for each neighbor v of u
                for (Edge edge: graph.adjList.get(u))
                {
                    int v = edge.dest;
                    int weight = edge.weight;

                    // Relaxation step
                    if (!done[v] && (dist.get(u) + weight) < dist.get(v))
                    {
                        dist.set(v, dist.get(u) + weight);
                        prev[v] = u;
                        minHeap.add(new Node(v, dist.get(v)));
                    }
                }

                // marked vertex u as done so it will not get picked up again
                done[u] = true;
            }


            System.out.print("Best path : ");
            printRoute(prev, N-1);
            System.out.println("");

        }

        private void print_hint(List<bad_people> real_nodes) {
            List<Edge> edges = new ArrayList<>();

            for (int i=0;i<=12;i++) {
                int level = 0;

                if(i==0) {
                    level = 1;
                }
                else if(i<=3) {
                    level = 2;
                }
                else {
                    level = 3;
                }

                edges.add(new Edge(i,(i*3)+1, real_nodes.get(i*3).getLevel()-level+3) );
                edges.add(new Edge(i,(i*3)+2, real_nodes.get((i*3)+1).getLevel()-level+3) );
                edges.add(new Edge(i,(i*3)+3, real_nodes.get((i*3)+2).getLevel()-level+3) );
            }

            for(int i=13;i<=40;i++) {
                edges.add(new Edge(i,40,1) );
            }
            final int N = 41;
            Graph graph = new Graph(edges, N);
            shortestPath(graph, 0, N);
        }



        // bonus ends here .....................










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


        private void sidekick_menu(bad_people enemy) {
            Scanner scan = new Scanner(System.in);

            while(true) {
                System.out.println("yes - buy a sidekick");
                System.out.println("no - upgrade level");
                String in = scan.next();

                if(in.equals("yes")) {
                    System.out.println("Your current XP is :" + getAvatar().getXp());
                    int n;

                    while(true) {
                        System.out.println("If you want to buy a MINION, press 1");
                        System.out.println("If you want to buy a KNIGHT, press 2");

                        n = scan.nextInt();
                        if(n==1 || n==2) { break; }
                        else { System.out.println("Wrong input...");}
                    }

                    int xp;

                    while(true) {
                        System.out.print("XP to spend : ");
                        xp = scan.nextInt();
                        if(xp<= getAvatar().getXp()) {
                            if(n==1 && xp>=5) {break;}
                            else if(n==2 && xp>=8) {break;}
                            else {System.out.println("Entered XP is less than minimum required");}
                        }
                        else { System.out.println("Entered XP is more than available"); }
                    }

                    if(n==1) {
                        System.out.println("You bought a sidekick: minion");
                        getAvatar().add_sidekick(new minions(xp, getAvatar()));
                    }
                    else {
                        System.out.println("You bought a sidekick: knight");
                        getAvatar().add_sidekick(new knight(xp, getAvatar()));
                    }
                    Collections.sort(getAvatar().getSidekicks(), Collections.reverseOrder());
                    break;
                }
                else if(in.equals("no")) {
                    for(int i=0;i<enemy.getLevel();i++) { getAvatar().level_up(); }
                    System.out.println("Level UP: new level = " + getAvatar().getLevel());

                    break;
                }
                else {
                    System.out.println("Wrong input entered...");
                }
            }

        }



        private void display_action_menu() {
            System.out.println("Choose move");
            System.out.println("1) Attack");
            System.out.println("2) Defense");

            if(getAvatar().getMoves_to_activate_special()<=0) {
                System.out.println("3) Special Attack");
            }
        }

        private sidekick get_sidekick() {
            Scanner scan = new Scanner(System.in);
            sidekick s = null;

            if(getAvatar().getSidekicks().size()>0) {
                String n;
                while(true) {
                    System.out.println("type 'yes' if you wish to use a sidekick, else type 'no' ");
                    n = scan.next();
                    if(n.equals("yes") || n.equals("no")) {break;}
                    else {System.out.println("Wrong input...");}
                }
                if(n.equals("yes")) {
                    Collections.sort(getAvatar().getSidekicks(), Collections.reverseOrder());
                    s = getAvatar().getSidekicks().get(0);
                }
            }
            return s;
        }

        public boolean action(bad_people enemy) {
            // true -> hero killed enemy
            // false -> enemy killed hero

            Scanner scan = new Scanner(System.in);

            sidekick s = get_sidekick();
            List<sidekick> cloned = new ArrayList<>();

            if(s!=null) {
                System.out.println("You have a sidekick " + s.getType() + " with you." +
                        "Attack of sidekick is " + s.getAttack_attribute());

                if(s.getType().equals("minion") && ((minions)s).can_clone) {
                    while(true) {
                        System.out.println("Press 'c' to use cloning ability. ELSE press 'f' to move to the fight ");
                        String n = scan.next();
                        if(n.equals("c")) {
                            cloned = ((minions) s).cloning();
                            System.out.println("Cloning done");
                            break;
                        }
                        else if(n.equals("f")) {break;}
                        else {System.out.println("Wrong input...");}
                    }
                }

                if(s.getType().equals("knight") && enemy.getLevel()==2) {
                    getAvatar().setDefense_attribute(getAvatar().getDefense_attribute()+5);
                }
            }



            while(true) {
                display_action_menu();
                int move = scan.nextInt();

                if(move==1){
                    System.out.println("You choose to attack");
                    getAvatar().attack(enemy);


                    // sidekick
                    if(s!= null) {
                        s.attack(enemy);
                        for(int i=0;i<cloned.size();i++) {
                            cloned.get(i).attack(enemy);
                        }
                        System.out.println("Sidekick HP: " + s.getHp() + "/100");
                        for(int i=0;i<cloned.size();i++) {
                            System.out.println("Sidekick HP: " + cloned.get(i).getHp() + "/100");
                        }
                    }
                    // sidekick finished ------


                    System.out.println("Your HP: " + getAvatar().getHp() + "/" + 50*(getAvatar().getLevel()+1) +" | Monsters HP: " + enemy.getHp() + "/" + 50*(enemy.getLevel()+1));
                }
                else if(move==2) {
                    System.out.println("You choose to defend");
                    getAvatar().defense(enemy);
                    System.out.println("Your HP: " + getAvatar().getHp() + "/" + 50*(getAvatar().getLevel()+1) + " | Monsters HP: " + enemy.getHp() + "/" + 50*(enemy.getLevel()+1));
                }
                else if(move==3 && getAvatar().getMoves_to_activate_special()>0) {
                    System.out.println("Special move not allowed...");
                    continue;
                }
                else if(move==3) {
                    System.out.println("Special power activated");
                    getAvatar().special_power(enemy);
                    System.out.println("Your HP: " + getAvatar().getHp() + "/" + 50*(getAvatar().getLevel()+1) + " | Monsters HP: " + enemy.getHp() + "/" + 50*(enemy.getLevel()+1));
                }
                else {
                    System.out.println("Invalid query");
                }

                if(enemy.getHp()<=0) {
                    System.out.println("You killed monster");
                    if(s!=null && s.getType().equals("knight") && enemy.getLevel()==2) {
                        getAvatar().setDefense_attribute(getAvatar().getDefense_attribute()-5);
                    }

                    if(enemy.getLevel()==4) {
                        System.out.println("CONGRATULATIONS!!!  ---  You killed the BOSS");
                        System.out.println("Exiting... ");
                        // Terminate JVM
                        System.exit(0);
                    }
                    System.out.println( enemy.getLevel()*20 +" XP awarded to the hero");
                    enemy.revive();
                    getAvatar().refill();
                    if(s!=null) {s.refill(enemy.getLevel()*20/(float)10);}
                    System.out.println("Fight won proceed to the next location.");

                    for(int i=0;i<enemy.getLevel();i++) {
                        getAvatar().setXp(getAvatar().getXp()+20);
                    }

                    sidekick_menu(enemy);


                    return true;
                }

                System.out.println("Monster attack !!!");

                float damage_by_monster = getAvatar().getHp();
                enemy.attack(getAvatar());
                System.out.println("Your HP: " + getAvatar().getHp() + "/" + 50*(getAvatar().getLevel()+1)  + " | Monsters HP: " + enemy.getHp() + "/" + 50*(enemy.getLevel()+1));
                damage_by_monster -= getAvatar().getHp();


                // sidekick
                if(s!=null) {
                    enemy.attack_sidekick(s, damage_by_monster*(float)1.5);
                    System.out.println("Sidekick HP: " + s.getHp() + "/100");

                    for(int i=0;i<cloned.size();i++) {
                        enemy.attack_sidekick(cloned.get(i), damage_by_monster*(float)1.5);
                    }
                    
                    for(int i=0;i<cloned.size();i++) {
                        System.out.println("Sidekick HP: " + cloned.get(i).getHp() + "/100");
                    }

                    if(s.getHp()<=0) {
                        System.out.println("Sidekick killed");
                        getAvatar().getSidekicks().remove(0);
                        s=null;
                        for(int i=0;i<cloned.size();i++) {
                            System.out.println("Sidekick killed");
                            cloned.remove(0);
                        }
                    }

                }
                // sidekick end


                if(getAvatar().getHp()<=0) {
                    System.out.println("Monster killed you ...");
                    System.out.println("Going back to start position");
                    if(s!=null && s.getType().equals("knight") && enemy.getLevel()==2) {
                        getAvatar().setDefense_attribute(getAvatar().getDefense_attribute()-5);
                    }
                    enemy.revive();
                    getAvatar().refill();
                    if(s!=null) {s.refill(enemy.level*20/(float)10);}
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
            String name = "";

            while(true) {
                System.out.print("Enter Username : ");
                name = scan.next();
                boolean valid_name = true;

                for(int i=0;i<getPlayers().size();i++) {
                    if(getPlayers().get(i).getUsername().equals(name)) {
                        System.out.println("User already exist ... PLS enter another name");
                        valid_name = false;
                        break;
                    }
                }
                if(valid_name) {break;}
            }

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

            for(int i=0;i<getPlayers().size();i++) {
                if(getPlayers().get(i).getUsername().equals(name)) {
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
            hero current_player = getPlayers().get(current_player_index);
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
                        bad_people monster = getMaps().get(current_player_index).getNodes().get(0);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy
                            current_player.setCurrent_position(0);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==2) {
                        System.out.println("Moving to location 1");
                        bad_people monster = getMaps().get(current_player_index).getNodes().get(1);
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
                        bad_people monster = getMaps().get(current_player_index).getNodes().get(2);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

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
                    else {
                        System.out.println("Invalid input...");
                    }



                }
                else if(current_player.getCurrent_position()<12) {
                    System.out.println("You are at location " +
                            current_player.getCurrent_position()+ " Choose path:");
                    System.out.println("1) " + (int)( (current_player.getCurrent_position()*(float)3)+3 ));
                    System.out.println("2) " + (int)((current_player.getCurrent_position()*(float)3)+4));
                    System.out.println("3) " + (int)((current_player.getCurrent_position()*(float)3)+5));
                    System.out.println("4) Go Back");
                    System.out.println("Enter -1 to exit");

                    int n = scan.nextInt();

                    if(n==1) {
                        System.out.println("Moving to location " + (int)( (current_player.getCurrent_position()*(float)3)+3 ));
                        bad_people monster = getMaps().get(current_player_index).getNodes().get((int)( (current_player.getCurrent_position()*(float)3)+3 ));
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

                            current_player.setCurrent_position((int)( (current_player.getCurrent_position()*(float)3)+3 ));
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==2) {
                        System.out.println("Moving to location " + (int)( (current_player.getCurrent_position()*(float)3)+4 ));
                        bad_people monster = getMaps().get(current_player_index).getNodes().get((int)( (current_player.getCurrent_position()*(float)3)+4 ));
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

                            current_player.setCurrent_position((int)( (current_player.getCurrent_position()*(float)3)+4 ));
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==3) {
                        System.out.println("Moving to location " + (int)( (current_player.getCurrent_position()*(float)3)+5 ));
                        bad_people monster = getMaps().get(current_player_index).getNodes().get((int)( (current_player.getCurrent_position()*(float)3)+5 ));
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

                            current_player.setCurrent_position((int)( (current_player.getCurrent_position()*(float)3)+5 ));
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==4) {
                        if(current_player.getCurrent_position()<3) {
                            System.out.println("Moving to starting location");
                            current_player.setCurrent_position(-1);
                        }
                        else {
                            System.out.println("Moving to location " + (int)( (current_player.getCurrent_position()/(float)3)-1 ));
                            bad_people monster = getMaps().get(current_player_index).getNodes().get((int)( (current_player.getCurrent_position()/(float)3)-1 ));
                            System.out.println("Fight Started. You are fighting a " +
                                    "level " + monster.getLevel()  + " Monster.");

                            boolean ans = current_player.action(monster);
                            if(ans==true) {
                                // hero killed enemy

                                current_player.setCurrent_position((int)( (current_player.getCurrent_position()/(float)3)-1 ));
                            }
                            else {
                                current_player.setCurrent_position(-1);
                            }
                        }

                    }
                    else if(n==-1){
                        System.out.println("Going to main menu ...");
                        break;
                    }
                    else {
                        System.out.println("Invalid input...");
                    }

                }
                else {
                    System.out.println("You are at location " +
                            current_player.getCurrent_position()+ " Choose path:");
                    System.out.println("1) for boss");
                    System.out.println("2) Go back");
                    System.out.println("Enter -1 to exit");

                    int n = scan.nextInt();

                    if(n==1) {
                        System.out.println("Moving to location of boss");
                        bad_people monster = getMaps().get(current_player_index).getNodes().get(39);
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

                            current_player.setCurrent_position(-1);
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==2) {
                        System.out.println("Moving to location " + (int)( (current_player.getCurrent_position()/(float)3)-1 ));

                        bad_people monster = getMaps().get(current_player_index).getNodes().get((int)( (current_player.getCurrent_position()/(float)3)-1 ));
                        System.out.println("Fight Started. You are fighting a " +
                                "level " + monster.getLevel()  + " Monster.");

                        boolean ans = current_player.action(monster);
                        if(ans==true) {
                            // hero killed enemy

                            current_player.setCurrent_position((int)( (current_player.getCurrent_position()/(float)3)-1 ));
                        }
                        else {
                            current_player.setCurrent_position(-1);
                        }
                    }
                    else if(n==-1){
                        System.out.println("Going to main menu ...");
                        break;
                    }
                    else {
                        System.out.println("Invalid input...");
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
