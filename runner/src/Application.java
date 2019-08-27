import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// creating Runner class
class Runner {

    private String name;
    private int time;
    private String category;
    public Runner next;

    // constructor
    public Runner(String name, int time, String category) {
        this.name = name;
        this.time = time;
        this.category = category;
        this.next = null;
    }

    // get functions
    public String getName() {
        return name;
    }
    public int getTime() {
        return time;
    }
    public String getCategory() {
        return category;
    }

    // set functions
    public void setName(String name) {
        this.name = name;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setTime(int time) {
        this.time = time;
    }
}

// creating linked list
class LinkedList {
    Runner head; // head of the linked list

    // to insert a Runner to the linked list
    void insert(Runner runner)
    {
        // If the Linked List is empty,
        // then make the new node as head
        if (this.head == null) {
            this.head = runner;
        }
        else {
            // Else traverse till the last node
            // and insert the new_node there
            Runner last = this.head;
            while (last.next != null) {
                last = last.next;
            }
            // Insert the new_node at last node
            last.next = runner;
        }
    }

    // Method to return the ans
    String printAns()
    {
        Runner currNode = this.head;
        // initializing
        System.out.print("LinkedList: ");
        String hm_first = "NaN";
        String hm_second = "NaN";
        String gdr_first = "NaN";
        String gdr_second = "NaN";
        String o1r_first = "NaN";
        String o1r_second = "NaN";

        // maximum int value
        int hm_first_time = 2147483647;
        int hm_second_time = 2147483647;
        int gdr_first_time = 2147483647;
        int gdr_second_time = 2147483647;
        int o1r_first_time = 2147483647;
        int o1r_second_time = 2147483647;


        // Traverse through the LinkedList
        while (currNode != null) {

            // setting the winners...  for each category...
            if(currNode.getCategory()=="Great Delhi Run") {
                if(currNode.getTime()<gdr_first_time) {
                    gdr_second_time = gdr_first_time;
                    gdr_second = gdr_first;
                    gdr_first_time = currNode.getTime();
                    gdr_first = currNode.getName();
                }
                else if(currNode.getTime()==gdr_first_time) {
                    gdr_first = gdr_first + ", " + currNode.getName();
                }
                else if(currNode.getTime()<gdr_second_time) {
                    gdr_second_time = currNode.getTime();
                    gdr_second = currNode.getName();
                }
                else if(currNode.getTime()==gdr_second_time) {
                    gdr_second = gdr_second + ", " + currNode.getName();
                }
            }

            else if(currNode.getCategory()=="Open 10K Run") {
                if(currNode.getTime()<o1r_first_time) {
                    o1r_second_time = o1r_first_time;
                    o1r_second = o1r_first;
                    o1r_first_time = currNode.getTime();
                    o1r_first = currNode.getName();
                }
                else if(currNode.getTime()==o1r_first_time) {
                    o1r_first = o1r_first + ", " + currNode.getName();
                }
                else if(currNode.getTime()<o1r_second_time) {
                    o1r_second_time = currNode.getTime();
                    o1r_second = currNode.getName();
                }
                else if(currNode.getTime()==o1r_second_time) {
                    o1r_second = o1r_second + ", " + currNode.getName();
                }
            }

            else if(currNode.getCategory()=="Half Marathon") {
                if(currNode.getTime()<hm_first_time) {
                    hm_second_time = hm_first_time;
                    hm_second = hm_first;
                    hm_first_time = currNode.getTime();
                    hm_first = currNode.getName();
                }
                else if(currNode.getTime()==hm_first_time) {
                    hm_first = hm_first + ", " + currNode.getName();
                }
                else if(currNode.getTime()<hm_second_time) {
                    hm_second_time = currNode.getTime();
                    hm_second = currNode.getName();
                }
                else if(currNode.getTime()==hm_second_time) {
                    hm_second = hm_second + ", " + currNode.getName();
                }
            }

            // Go to next node
            currNode = currNode.next;
        }

        // creating the final ans to return
        String final_ans = "";
        final_ans += "Great Delhi Run (1st) (Rs.1,35,000) - " + gdr_first;
        final_ans += "\nGreat Delhi Run (2nd) (Rs.1,15,000) - " + gdr_second;
        final_ans += "\nHalf Marathon (1st) (Rs.2,80,000) - " + hm_first;
        final_ans += "\nHalf Marathon (2nd) (Rs.2,10,000) - " + hm_second;
        final_ans += "\nOpen 10K Run (1st) (Rs.1,90,000) - " + o1r_first;
        final_ans += "\nOpen 10K Run (2nd) (Rs.1,50,000) - " + o1r_second;

        System.out.println(final_ans);

        return final_ans;

    }
}


public class Application {

    public static JPanel p_name;

    public static JLabel l_name,l_time;
    public static JTextField tf_name,tf_time;

    public static void main(String[] args) {

        LinkedList list = new LinkedList();

        JFrame frame = new JFrame("Winner Winner Chicken Dinner");


        JPanel p_main = new JPanel();
        p_main.setLayout(new BoxLayout(p_main,BoxLayout.Y_AXIS));

        p_name = new JPanel();
        p_name.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_main.add(p_name);

        l_name = new JLabel("Name");
        tf_name = new JTextField();
        tf_name.setPreferredSize(new Dimension(150,50));
        p_name.add(l_name);
        p_name.add(tf_name);

        l_time = new JLabel("Time (minutes) ");
        tf_time = new JTextField();
        tf_time.setPreferredSize(new Dimension(150,50));
        p_name.add(l_time);
        p_name.add(tf_time);


        JPanel p_joined=  new JPanel();
        p_joined.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel l_joined = new JLabel("Marathon Category : ");

        p_joined.add(l_joined);

        p_main.add(p_joined);

        ButtonGroup bg_joined = new ButtonGroup();

        JRadioButton rb_gdr = new JRadioButton("Great Delhi Run");
        JRadioButton rb_o1r = new JRadioButton("Open 10K Run");
        JRadioButton rb_hm = new JRadioButton("Half Marathon");

        bg_joined.add(rb_gdr);
        bg_joined.add(rb_o1r);
        bg_joined.add(rb_hm);

        p_joined.add(rb_gdr);
        p_joined.add(rb_o1r);
        p_joined.add(rb_hm);

////////////////////////////////////



        JPanel p_message = new JPanel();
        p_message.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel l_message = new JLabel("Winners : ");
        JTextArea tf_message = new JTextArea();
        tf_message.setPreferredSize(new Dimension(500,100));

        p_message.add(l_message);
        p_message.add(tf_message);

        p_main.add(p_message);
////////////////////////////////////


        JPanel p_buttons = new JPanel();
        p_buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton b_next = new JButton("Next");
        JButton b_cancel = new JButton("Cancel (exit)");
        JButton b_print = new JButton("Find WINNERS");



        b_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String n = tf_name.getText();
                if(n.equals("")) {
                    n = "No name";
                }
                int time = Integer.parseInt(tf_time.getText());
                boolean j = rb_gdr.isSelected();
                boolean k = rb_o1r.isSelected();
                boolean l = rb_hm.isSelected();

                String cat = "";

                if (j==true) {
                    cat = "Great Delhi Run";
                }
                else if (k==true) {
                    cat = "Open 10K Run";
                }
                else if (l==true) {
                    cat = "Half Marathon";
                }

                Runner s = new Runner(n,time,cat);
                list.insert(new Runner(n,time,cat));

                // hiding the displayed data
                tf_name.setText("");
                tf_time.setText("");
                tf_message.setText("");


            }
        });

        b_print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                tf_message.setText(list.printAns());
            }
        });

        b_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // to erase everything and
                // reset the linked list
                list.head = null;

                // hiding the displayed data
                tf_name.setText("");
                tf_time.setText("");
                tf_message.setText("");
            }
        });

        p_buttons.add(b_next);
        p_buttons.add(b_cancel);
        p_buttons.add(b_print);

        p_main.add(p_buttons);

////////////////////////////////////

        frame.add(p_main);
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


////////////////////////////////////

    }
}