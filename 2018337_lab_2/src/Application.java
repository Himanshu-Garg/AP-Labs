import java.util.*;
import java.io.*;
import java.lang.*;


public class Application {

    public interface website_user {
        public void print_reward();
        public void search(String category, List<item> all_items);
        public void give_reward();
        public void print_details();
    }

    public static class item {
        private final int id;
        private final String name;
        private final String category;
        private final merchant merch;
        private float price;
        private int quantity;
        private String offer;

        // constructor
        item(int id, String name, String category, merchant merch, float price, int quantity) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.merch = merch;
            this.price = price;
            this.quantity = quantity;
            this.offer = "None";  // "Buy one get one free" OR "25% off"
        }

        // getter and setter functions
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public merchant getMerch() {
            return merch;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        @Override
        public String toString() {
            return getId() + " " +
                    getName() + " " +
                    (int)getPrice() + " " +
                    getQuantity() + " " +
                    getOffer() + " " +
                    getCategory();
        }
    }

    public static class merchant implements website_user {
        private final String name;
        private final String address;
        private final int user_id;
        private float contribution_to_company;
        private List<item> merchant_items;
        private int no_of_slots;

        // constructor
        merchant(int user_id, String name, String address) {
            this.name = name;
            this.address = address;
            this.user_id = user_id;
            this.contribution_to_company = 0;
            this.merchant_items = new ArrayList<>();
            this.no_of_slots = 10;
        }

        // getter and setter functions

        public int getNo_of_slots() {
            return no_of_slots;
        }

        public void setNo_of_slots(int no_of_slots) {
            this.no_of_slots = no_of_slots;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public int getUser_id() {
            return user_id;
        }

        public float getContribution_to_company() {
            return contribution_to_company;
        }

        public void setContribution_to_company(float contribution_to_company) {
            this.contribution_to_company = contribution_to_company;
        }

        public List<item> getMerchant_items() {
            return merchant_items;
        }

        public void setMerchant_items(List<item> merchant_items) {
            this.merchant_items = merchant_items;
        }


        @Override
        public void print_reward() {
            System.out.println(getNo_of_slots()-10);
        }

        @Override
        public void print_details() {
            System.out.println(getName() + " " +
                    getAddress() + " " +
                    getContribution_to_company());
        }

        @Override
        public void search(String category, List<item> all_items) {
            for(int i=0;i<all_items.size();i++) {
                if(all_items.get(i).getCategory().equals(category)) {
                    System.out.println(all_items.get(i).toString());
                }
            }
        }

        @Override
        public void give_reward() {
            setNo_of_slots(getNo_of_slots() + 1);
        }

        public boolean can_add_item() {
            if(getMerchant_items().size()>=getNo_of_slots()) {
                return false;
            }
            return true;
        }

        public void add_item(item i) {
            getMerchant_items().add(i);
            System.out.println(i.toString());
        }

        public void edit_item(int item_id, float item_price, int item_quantity ) {
            int index = -1;
            for(int i=0;i<getMerchant_items().size();i++) {
                if(getMerchant_items().get(i).getId()==item_id) {
                    index = i;
                    break;
                }
            }
            if(index==-1) { System.out.println("Item code NOT found"); }
            else {
                getMerchant_items().get(index).setPrice(item_price);
                getMerchant_items().get(index).setQuantity(item_quantity);
                System.out.println(getMerchant_items().get(index).toString());
            }
        }

        public void edit_item(int item_id) {
            int index = -1;
            for(int i=0;i<getMerchant_items().size();i++) {
                if(getMerchant_items().get(i).getId()==item_id) {
                    index = i;
                    break;
                }
            }
            if(index==-1) { System.out.println("Item code NOT found"); }
            else {
                System.out.println("Choose offer");  //"Buy one get one free" OR "25% off"
                System.out.println("1) Buy one get one free");
                System.out.println("2) 25% off");

                Scanner scan = new Scanner(System.in);
                int n = scan.nextInt();
                if(n==1) { getMerchant_items().get(index).setOffer("Buy one get one free"); }
                else if(n==2) { getMerchant_items().get(index).setOffer("25% off"); }
                else { System.out.println("Wrong input entered"); }

                System.out.println(getMerchant_items().get(index).toString());
            }
        }

        public void display_all_items() {
            for(int i=0;i<getMerchant_items().size();i++) {
                System.out.println(getMerchant_items().get(i).toString());
            }
        }



    }

    public static class account {
        private float balance;

        public account(float balance) {
            this.balance = balance;
        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }
    }

    public static class customer implements website_user {
        private final int user_id;
        private final String name;
        private final String address;
        private List<item> orders_placed;
        private List<item> cart;
        private account main_account;
        private account reward_account;

        // constructor
        customer(int user_id, String name, String address) {
            this.user_id = user_id;
            this.name = name;
            this.address = address;
            this.orders_placed = new ArrayList<>();
            this.cart = new ArrayList<>();
            this.main_account = new account(100);
            this.reward_account = new account(0);

        }

        // getter and setter functions
        public int getUser_id() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public List<item> getOrders_placed() {
            return orders_placed;
        }

        public void setOrders_placed(List<item> orders_placed) {
            this.orders_placed = orders_placed;
        }

        public List<item> getCart() {
            return cart;
        }

        public void setCart(List<item> cart) {
            this.cart = cart;
        }

        public account getMain_account() {
            return main_account;
        }

        public void setMain_account(account main_account) {
            this.main_account = main_account;
        }

        public account getReward_account() {
            return reward_account;
        }

        public void setReward_account(account reward_account) {
            this.reward_account = reward_account;
        }


        @Override
        public void print_details() {
            System.out.println(getName() + " " +
                    getAddress() + " " +
                    getOrders_placed().size());
        }


        @Override
        public void print_reward() {
            int total_reward = (int)(getOrders_placed().size()/5) *10;
            System.out.println("Total reward gained = "+total_reward);
            System.out.println("Reward account balance " + getReward_account().getBalance());
        }

        private float amount_needed_to_buy(item i, int quantity) {
            if(i.getOffer().equals("Buy one get one free")) {
                if(quantity%2==0) {
                    return ((float)quantity/2)*i.getPrice();
                }
                else {
                    return ((float)((quantity-1)/2)*i.getPrice()) + i.getPrice();
                }
            }
            else if(i.getOffer().equals("25% off")) {
                return (i.getPrice()*quantity)*3/4;
            }
            else {
                return i.getPrice()*quantity;
            }
        }



        private boolean buy_item(item i, int quantity) {
            if(quantity<=0) {
                System.out.println("quantity <= 0");
                return false;
            }
            if(quantity>i.getQuantity()) {
                System.out.println("Not enough quantity available");
                return false;
            }
            else {
                float amount_needed = amount_needed_to_buy(i,quantity);
                if(amount_needed>getMain_account().getBalance()+getReward_account().getBalance()) {
                    System.out.println("Not enough balance");
                    return false;
                }

                if(getMain_account().getBalance()>= amount_needed) {
                    //main_account.balance -=  amount_needed;
                    getMain_account().setBalance(getMain_account().getBalance() - amount_needed);
                }
                else {
                    getReward_account().balance -= amount_needed - getMain_account().getBalance();
                    getMain_account().setBalance(0);
                }
                //i.quantity -= quantity;
                i.setQuantity(i.getQuantity() - quantity);
                getOrders_placed().add(new item(i.getId(), i.getName(), i.getCategory(), i.getMerch(), amount_needed, quantity));

                if(getOrders_placed().size()%5==0) {
                    give_reward();
                }

                merchant merch = i.getMerch();
                float upper_hundred = merch.getContribution_to_company() - (merch.getContribution_to_company()%100) + 100;
                //System.out.println("Upper hundred = " + upper_hundred);
                merch.contribution_to_company += (float)0.005*amount_needed;
                if(merch.getContribution_to_company()>= upper_hundred) {
                    merch.give_reward();
                }
            }
            return true;
        }



        @Override
        public void search(String category, List<item> all_items) {

            for(int i=0;i<all_items.size();i++) {
                if(all_items.get(i).getCategory().equals(category)) {
                    System.out.println(all_items.get(i).toString());
                }
            }

            int error_type = 1;
            int i=0;
            // 0 = no error
            // 1 = item_code not found


            Scanner scan = new Scanner(System.in);

            System.out.println("Enter item code");
            int item_code = scan.nextInt();
            System.out.println("Enter item quantity");
            int item_quantity = scan.nextInt();

            for(i=0;i<all_items.size();i++) {
                if(all_items.get(i).getId()==item_code) {
                    error_type = 0;
                    break;
                }
            }

            if(error_type==1) {
                System.out.println("Item code not found");
            }
            else {
                System.out.println("Choose method of transaction");
                System.out.println("1) buy item");
                System.out.println("2) add item to cart");
                System.out.println("3) exit");

                int trans_method = scan.nextInt();
                if(trans_method==1) {
                    if(buy_item(all_items.get(i), item_quantity)){
                        System.out.println("Item successfully bought");
                    }
                }
                else if(trans_method==2) {
                    item _item = all_items.get(i);
                    cart.add(new item(_item.getId(), _item.getName(), _item.getCategory(), _item.getMerch(), _item.getPrice(), item_quantity));
                }
            }
        }

        public void check_out_cart(List<item> all_items) {
            while(getCart().size()!=0) {
                int quantity = getCart().get(0).getQuantity();
                int i=0;
                for(i=0;i<all_items.size();i++) {
                    if(all_items.get(i).getId()==getCart().get(0).getId()) {
                        break;
                    }
                }
                if(!buy_item(all_items.get(i), quantity)) {
                    break;
                }
                else {
                    System.out.println("Id-"+all_items.get(i).getId() + " successfully bought");
                    cart.remove(0);
                }
            }
        }

        @Override
        public void give_reward() {
            account reward_acc = getReward_account();
            reward_acc.setBalance(reward_acc.getBalance() + 10);
        }

        public void display_recent_orders() {
            System.out.println("... Latest order on TOP ...");
            if(getOrders_placed().size()<=10) {
                for(int i=getOrders_placed().size()-1;i>=0;i--) {

                    System.out.println("Bought item " + getOrders_placed().get(i).getName() +
                            " quantity: " + getOrders_placed().get(i).getQuantity() +
                            " for Rs. " + getOrders_placed().get(i).getPrice() +
                            " from Merchant " + getOrders_placed().get(i).getMerch().getName());
                }
            }
            else {
                int done = 0;
                for(int i=getOrders_placed().size()-1;i>=0;i--) {

                    System.out.println("Bought item " + getOrders_placed().get(i).getName() +
                            " quantity: " + getOrders_placed().get(i).getQuantity() +
                            " for Rs. " + getOrders_placed().get(i).getPrice() +
                            " from Merchant " + getOrders_placed().get(i).getMerch().getName());
                    done++;
                    if(done>=10) {break;}
                }
            }
        }



    }

    public static class company {
        private final String name;
        private List<merchant> Merchants;
        private List<item> all_items;
        private List<customer> Customers;
        private int no_of_registered_items;

        // constructor
        public company(String  name) {
            this.name = name;
            Merchants = new ArrayList<>();
            Customers = new ArrayList<>();
            all_items = new ArrayList<>();
            this.no_of_registered_items = 0;
        }

        //getter and setter functions

        public List<item> getAll_items() {
            return all_items;
        }

        public void setAll_items(List<item> all_items) {
            this.all_items = all_items;
        }

        public String getName() {
            return name;
        }

        public List<merchant> getMerchants() {
            return Merchants;
        }

        public void setMerchants(List<merchant> merchants) {
            Merchants = merchants;
        }

        public List<customer> getCustomers() {
            return Customers;
        }

        public void setCustomers(List<customer> customers) {
            Customers = customers;
        }

        public int getNo_of_registered_items() {
            return no_of_registered_items;
        }

        public void setNo_of_registered_items(int no_of_registered_items) {
            this.no_of_registered_items = no_of_registered_items;
        }

        // functions start from here ...

        public void add_customer(String name, String address) {
            getCustomers().add(new customer(getCustomers().size()+1, name, address));
        }
        public void add_merchant(String name, String address) {
            getMerchants().add(new merchant(getMerchants().size()+1, name, address));
        }

        private List<String> return_all_categories() {
            List<String> categories = new ArrayList<>();
            for(int i=0;i<getAll_items().size();i++) {
                if(!categories.contains(getAll_items().get(i).getCategory())) {
                    categories.add(getAll_items().get(i).getCategory());
                }
            }
            return categories;
        }

        private void display_all_categories(List<String> categories) {
            if(categories.size()==0) {
                System.out.println("NO category exists");
            }
            else {
                for (int i=0;i<categories.size();i++) {
                    System.out.println(i+1 + ") " + categories.get(i));
                }
            }
        }









        private void display_app_initial_menu() {
            System.out.println("Welcome to " + getName());
            System.out.println("1) Enter as Merchant");
            System.out.println("2) Enter as Customer");
            System.out.println("3) See user details");
            System.out.println("4) Company account balance");
            System.out.println("5) Exit");
        }

        private float return_account_balance() {
            float balance = 0;
            for (int i=0;i<getMerchants().size();i++) {
                balance += getMerchants().get(i).getContribution_to_company()*(float)2;
            }
            return balance;
        }

        public void app_query_selector() {
            boolean do_exit = false;
            Scanner scan = new Scanner(System.in);

            while(do_exit==false) {
                display_app_initial_menu();
                int query_no = scan.nextInt();
                if(query_no==1) {
                    System.out.println("Choose merchant");
                    for(int i =0;i<getMerchants().size();i++) {
                        System.out.println(getMerchants().get(i).getUser_id() + " " + getMerchants().get(i).getName());
                    }
                    int merch_id = scan.nextInt();
                    merchant_query_selector(getMerchants().get(merch_id-1));
                }

                else if(query_no==2) {
                    System.out.println("Choose customer");
                    for(int i =0;i<getCustomers().size();i++) {
                        System.out.println(getCustomers().get(i).getUser_id() + " " + getCustomers().get(i).getName());
                    }
                    int cust_id = scan.nextInt();
                    customer_query_selector(getCustomers().get(cust_id-1));
                }

                else if(query_no==3) {
                    System.out.println("Merchants account");
                    for(int i =0;i<getMerchants().size();i++) {
                        System.out.println(getMerchants().get(i).getUser_id() + " " + getMerchants().get(i).getName());
                    }
                    System.out.println("");
                    System.out.println("Customers account");
                    for(int i =0;i<getCustomers().size();i++) {
                        System.out.println(getCustomers().get(i).getUser_id() + " " + getCustomers().get(i).getName());
                    }

                    System.out.print("Enter (M or C) : ");
                    String type = scan.next();
                    System.out.print("Enter ID : ");
                    int id = scan.nextInt();
                    if(type.equals("M")) {
                        getMerchants().get(id-1).print_details();
                    }
                    else if(type.equals("C")) {
                        getCustomers().get(id-1).print_details();
                    }
                }

                else if(query_no==4) {
                    System.out.println("Account balance: " + return_account_balance());
                }

                else if(query_no==5) {
                    do_exit = true;
                    System.out.println("Exiting the company ... ");
                }
                else {
                    System.out.println("Invalid query entered... ");
                }
            }


        }






        private void display_customer_initial_menu() {
            System.out.println("Customer MENU");
            System.out.println("1) Search item");
            System.out.println("2) Checkout cart");
            System.out.println("3) Reward won");
            System.out.println("4) Print latest orders");
            System.out.println("5) Exit");
        }

        private void customer_query_selector(customer selected_customer) {
            boolean do_exit = false;
            Scanner scan = new Scanner(System.in);

            while(do_exit==false) {
                System.out.println("Welcome " + selected_customer.getName());
                display_customer_initial_menu();
                int query_no = scan.nextInt();

                if(query_no==1) {
                    System.out.println("Choose a category");
                    List<String> categories = return_all_categories();
                    display_all_categories(categories);

                    int category_no = scan.nextInt();
                    selected_customer.search(categories.get(category_no-1), getAll_items());
                }

                else if(query_no==2) {
                    selected_customer.check_out_cart(getAll_items());
                }

                else if(query_no==3) {
                    selected_customer.print_reward();
                }

                else if(query_no==4) {
                    selected_customer.display_recent_orders();
                }

                else if(query_no==5) {
                    do_exit = true;
                    System.out.println("Exiting the Customer MENU ... ");
                }
                else {
                    System.out.println("Invalid query entered... ");
                }
            }

        }






        private void display_merchant_initial_menu() {
            System.out.println("Merchant MENU");
            System.out.println("1) Add item");
            System.out.println("2) Edit item");
            System.out.println("3) Search by category");
            System.out.println("4) Add offer");
            System.out.println("5) Rewards won");
            System.out.println("6) Exit");
        }

        private void merchant_query_selector(merchant selected_merchant) {
            boolean do_exit = false;
            Scanner scan = new Scanner(System.in);

            while(do_exit==false) {
                System.out.println("Welcome " + selected_merchant.getName());
                display_merchant_initial_menu();
                int query_no = scan.nextInt();

                if(query_no==1) {
                    if(selected_merchant.can_add_item()) {

                        System.out.println("Enter item details");
                        System.out.print("item name: ");
                        String item_name = scan.next();
                        System.out.print("item price: ");
                        float item_price = scan.nextFloat();
                        System.out.print("item quantity: ");
                        int quantity = scan.nextInt();
                        System.out.print("item category: ");
                        String categ = scan.next();

                        item i = new item(getNo_of_registered_items()+1, item_name, categ, selected_merchant, item_price, quantity );

                        selected_merchant.add_item(i);
                        setNo_of_registered_items(getNo_of_registered_items()+1);
                        getAll_items().add(i);
                    }
                    else {
                        System.out.println("Cannot add item (all slots filled)");
                    }
                }

                else if(query_no==2) {
                    System.out.println("Choose item by code");
                    selected_merchant.display_all_items();

                    int id = scan.nextInt();
                    System.out.println("Enter edit details");
                    System.out.print("item price: ");
                    float item_price = scan.nextFloat();
                    System.out.print("item quantity: ");
                    int quantity = scan.nextInt();

                    selected_merchant.edit_item(id, item_price, quantity);
                }

                else if(query_no==3) {
                    System.out.println("Choose a category");
                    List<String> categories = return_all_categories();
                    display_all_categories(categories);

                    int category_no = scan.nextInt();

                    selected_merchant.search(categories.get(category_no-1), getAll_items());
                }

                else if(query_no==4) {
                    System.out.println("Choose item by code");
                    selected_merchant.display_all_items();

                    int item_id = scan.nextInt();
                    selected_merchant.edit_item(item_id);
                }

                else if(query_no==5) {
                    selected_merchant.print_reward();
                }

                else if(query_no==6) {
                    do_exit = true;
                    System.out.println("Exiting the Merchant MENU ... ");
                }
                else {
                    System.out.println("Invalid query entered... ");
                }
            }
        }


    }

    public static void main(String[] args) {
        company c1 = new company("Mercury Inc.");

        // initially creating 5 objects of each
        c1.add_customer("ali", "Anand-Vihar");
        c1.add_customer("nobby", "Anand-Vihar");
        c1.add_customer("bruno", "Anand-Vihar");
        c1.add_customer("borat", "Anand-Vihar");
        c1.add_customer("aladeen", "Anand-Vihar");
        c1.add_merchant("jack", "Anand-Vihar");
        c1.add_merchant("john", "Anand-Vihar");
        c1.add_merchant("james", "Anand-Vihar");
        c1.add_merchant("jeff", "Anand-Vihar");
        c1.add_merchant("joseph", "Anand-Vihar");

        c1.app_query_selector();

    }








}
