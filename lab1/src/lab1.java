import java.util.*;
import java.io.*;
import java.lang.*;

public class lab1 {

    public static boolean valid_course (String course) {
        if (course.equals("CSE") || course.equals("ECE") ||course.equals("CSAM") ||course.equals("CSD") ||course.equals("CSB") ||course.equals("CSSS") ) {
            return true;
        }
        return false;
    }

    public static class student {
        private final int rollno;
        // final - as considering no ambiguity in the data, and
        // data dont have to be changed or corrected in future ...
        private final float cgpa;
        private final String course;
        private String placement_status = "Not placed";
        private String company_placed = "";

        private List<Integer> scores = new ArrayList<>();

        // getter functions

        public List<Integer> getScores() {
            return scores;
        }

        public int getRollno() {
            return rollno;
        }

        public float getCgpa() {
            return cgpa;
        }

        public String getCourse() {
            return course;
        }

        public String getPlacement_status() {
            return placement_status;
        }

        public String getCompany_placed() {
            return company_placed;
        }

        // setter functions
        public void setScores(List<Integer> scores) {
            this.scores = scores;
        }

        public void setPlacement_status(String placement_status) {
            this.placement_status = placement_status;
        }

        public void setCompany_placed(String company_placed) {
            this.company_placed = company_placed;
        }

        // constructor
        student (int rollno, float cgpa, String course) {
            this.rollno = rollno;
            this.cgpa = cgpa;
            this.course = course;
            scores.add(0);      // score at index 0 is used during sorting...
        }

        // for adding marks to the student object
        public void add_score(int marks) {
            scores.add(marks);
        }

        public void print_details() {
            System.out.println(getRollno());
            System.out.println(getCgpa());
            System.out.println(getCourse());
            System.out.println("Placement Status : " + getPlacement_status());

            if(getPlacement_status().equals("Placed")) {
                System.out.println("Company at which placed : "+getCompany_placed());
            }

            //System.out.println("scores : "+ Arrays.toString(new List[]{scores}));
        }

        // for sorting (creating comparator)
        public static Comparator<student> StuSort = new Comparator<student>() {
            @Override
            public int compare(student s1, student s2) {
                int score1 = s1.scores.get(0);
                int score2 = s2.scores.get(0);

                if(score1==score2) {
                    return (int)(s2.cgpa - s1.cgpa);
                }
                return score2-score1;
            }
        };


    }

    public static class company {
        private final String name;
        private final int no_of_required_students;
        private final List<String> course_criteria;
        private String application_status = "OPEN";

        // getter functions
        public String getName() {
            return name;
        }

        public int getNo_of_required_students() {
            return no_of_required_students;
        }

        public List<String> getCourse_criteria() {
            return course_criteria;
        }

        public String getApplication_status() {
            return application_status;
        }

        // setter functions
        public void setApplication_status(String application_status) {
            this.application_status = application_status;
        }

        // constructor
        company (String name, int no_of_courses, int no_of_required_students, List<String> course_criteria) {
            this.name = name;
            this.no_of_required_students = no_of_required_students;

            this.course_criteria = new ArrayList<>();

            for (int i=0;i<no_of_courses;i++) {
                this.course_criteria.add(course_criteria.get(i));
            }

            // printing details
            print_details();
        }

        public void print_details() {
            System.out.println(getName());

            System.out.println("Course Criteria");
            for (int i=0;i<getCourse_criteria().size();i++) {
                System.out.println(getCourse_criteria().get(i));
            }

            System.out.println("Number of Required Students = " + getNo_of_required_students());
            System.out.println("Application Status = " + getApplication_status());
        }
    }

    public static class Application {
        private List<student> Students = new ArrayList<>();;
        private List<company> Companies_left = new ArrayList<>();
        private List<String> all_companies_visited = new ArrayList<>();
        private int no_of_students;

        Application() {
            no_of_students = 0;
            all_companies_visited.add("for sorting");       // since 0th index is reserved for sorting
        }

        // getter functions
        public List<student> getStudents() {
            return Students;
        }

        public List<company> getCompanies_left() {
            return Companies_left;
        }

        public List<String> getAll_companies_visited() {
            return all_companies_visited;
        }

        public int getNo_of_students() {
            return no_of_students;
        }

        // setter functions
        public void setStudents(List<student> students) {
            Students = students;
        }

        public void setCompanies_left(List<company> companies_left) {
            Companies_left = companies_left;
        }

        public void setAll_companies_visited(List<String> all_companies_visited) {
            this.all_companies_visited = all_companies_visited;
        }

        public void setNo_of_students(int no_of_students) {
            this.no_of_students = no_of_students;
        }

        public void add_student (float _cgpa, String _course ) {
            setNo_of_students(getNo_of_students()+1);
            Students.add(new student(getNo_of_students(),_cgpa, _course));
        }

        public void add_company(String name, int no_of_courses, int no_of_required_students, List<String> course_criteria) {
            Companies_left.add(new company(name,no_of_courses,no_of_required_students,course_criteria));
            all_companies_visited.add(name);

            System.out.println("Enter Scores for Technical round : ");
            boolean atleast_one_eligible = false;
            Scanner scan = new Scanner(System.in);
            for(int i=0;i<Students.size();i++) {
                if(course_criteria.contains(Students.get(i).course)) {
                    atleast_one_eligible = true;
                    System.out.println("Enter score for Roll no. "+Students.get(i).getRollno());
                    int s = scan.nextInt();
                    Students.get(i).scores.add(s);
                }
                else {
                    Students.get(i).scores.add(-1);
                }
            }
            if(atleast_one_eligible==false) {
                System.out.println("SORRY - no student eligible for technical round ...");
            }
        }

        public void print_open_companies_name() {
            for(int i=0;i<Companies_left.size();i++) {
                System.out.println(Companies_left.get(i).name);
            }
        }

        private List<student> find_eligible_students(String _company_name) {

            int company_index = 0;
            for (int i=1;i<all_companies_visited.size();i++) {
                if(all_companies_visited.get(i).equals(_company_name)) {
                    company_index = i;
                    break;
                }
            }

            List<String> courses = new ArrayList<>();
            for(int i=0;i<Companies_left.size();i++) {
                if(Companies_left.get(i).name.equals(_company_name)) {
                    courses = Companies_left.get(i).getCourse_criteria();
                    break;
                }
            }

            List<student> eligible_students = new ArrayList<>();
            for(int i=0;i<Students.size();i++) {
                student temp = Students.get(i);
                if(temp.placement_status=="Not placed" && courses.contains(temp.course) ) {
                    eligible_students.add(temp);
                }
            }

            for (int i=0;i<eligible_students.size();i++) {
                eligible_students.get(i).scores.set(0, eligible_students.get(i).scores.get(company_index));
            }

            return eligible_students;
        }

        private company return_company_by_name(String _company_name) {
            for (int i=0;i<Companies_left.size();i++) {
                if(Companies_left.get(i).name.equals(_company_name)) {
                    return Companies_left.get(i);
                }
            }
            return null;
        }


        private List<Integer> select_students(String _company_name) {
            List<student> eligible_students = find_eligible_students(_company_name);

            int requirement = 0;
            company C = return_company_by_name(_company_name);
            if(C==null) {
                System.out.println("Company not exist in Database");
                return null;
            }

            for(int i=0;i<Companies_left.size();i++) {
                if(Companies_left.get(i).name.equals(_company_name)) {
                    Companies_left.get(i).application_status = "CLOSED";
                }
            }

            requirement = C.getNo_of_required_students();
            if(requirement==0) {
                System.out.println("Requirement of the company = 0");
                return null;
            }

            List<Integer> selected_students = new ArrayList<>();

            if(eligible_students.size()<= requirement) {
                for(int i=0;i<eligible_students.size();i++) {
                    for(int j=0;j<Students.size();j++) {
                        if(Students.get(j).getRollno() == eligible_students.get(i).getRollno()) {
                            selected_students.add(Students.get(j).getRollno());
                            Students.get(j).placement_status = "Placed";
                            Students.get(j).company_placed = _company_name;
                            break;
                        }
                    }
                }
                return selected_students;
            }
            else {
                Collections.sort(eligible_students, student.StuSort);
                int accepted = 0;

                for(int i=0;i<eligible_students.size();i++) {
                    for(int j=0;j<Students.size();j++) {
                        if(Students.get(j).getRollno() == eligible_students.get(i).getRollno()) {
                            selected_students.add(Students.get(j).getRollno());
                            accepted++;
                            Students.get(j).placement_status = "Placed";
                            Students.get(j).company_placed = _company_name;
                            if(accepted==requirement) {
                                return selected_students;
                            }
                            break;
                        }
                    }
                }
            }
            return null;
        }

        public void display_select_students (String _company_name) {
            List<Integer> selected_students = select_students(_company_name);

            if(selected_students!=null) {
                if(selected_students.size()==0) {
                    System.out.println("NO student selected ...");
                }
                else {
                    System.out.println("Roll number of Selected Students : ");
                    for (int i=0;i<selected_students.size();i++) {
                        System.out.println(selected_students.get(i));
                    }
                }


            }

            if(Students.size()<=0) {
                System.out.println("Exiting ... ( all students placed )");
                System.exit(0);
            }
        }

        private List<Integer> remove_placed_students () {
            List<Integer> placed_students = new ArrayList<>();
            for(int i=Students.size()-1;i>=0;i--) {
                if(Students.get(i).placement_status=="Placed") {
                    placed_students.add(Students.get(i).rollno);
                    Students.remove(i);
                }
            }
            return placed_students;
        }

        public void display_remove_placed_students() {
            List<Integer> placed_students = remove_placed_students();
            if (placed_students.size()==0) {
                System.out.println("No student removed");
            }
            else {
                System.out.println("Accounts removed for : ");
                for(int i=0;i<placed_students.size();i++) {
                    System.out.println(placed_students.get(i));
                }
            }
        }

        private List<String> remove_closed_companies () {
            List<String> closed_companies = new ArrayList<>();
            for(int i=Companies_left.size()-1;i>=0;i--) {
                if(Companies_left.get(i).application_status=="CLOSED") {
                    closed_companies.add(Companies_left.get(i).name);
                    Companies_left.remove(i);
                }
            }
            return closed_companies;
        }

        public void display_remove_closed_companies() {
            List<String> closed_companies = remove_closed_companies();
            if (closed_companies.size()==0) {
                System.out.println("No company removed");
            }
            else {
                System.out.println("Accounts removed for : ");
                for(int i=0;i<closed_companies.size();i++) {
                    System.out.println(closed_companies.get(i));
                }
            }
        }

        public void no_of_unplaced_students() {
            int count = 0;
            for(int i=0;i<Students.size();i++) {
                if(Students.get(i).placement_status=="Not placed") {
                    count++;
                }
            }
            System.out.println(count + " students left.");
        }

        public void display_open_companies() {
            for(int i=0;i<Companies_left.size();i++) {
                if(Companies_left.get(i).application_status=="OPEN") {
                    System.out.println(Companies_left.get(i).name);
                }
            }
        }

        public void display_company_details(String _company_name) {
            company C = return_company_by_name(_company_name);
            if(C==null) {
                System.out.println("Company not in database ... ");
            }
            else {
                C.print_details();
            }
        }

        private student return_student_by_rollno(int _roll_no) {
            for (int i=0;i<Students.size();i++) {
                if (Students.get(i).rollno == _roll_no) {
                    return Students.get(i);
                }
            }
            return null;
        }

        public void display_student_details(int _roll_no) {
            student s = return_student_by_rollno(_roll_no);
            if(s==null) {
                System.out.println("Student not in database ... ");
            }
            else {
                s.print_details();
            }
        }

        public void companies_applied_by_student(int _roll_no) {
            student s = return_student_by_rollno(_roll_no);
            boolean atleast_one_test_given = false;
            if(s==null) {
                System.out.println("Student not in database ... ");
            }
            else {
                for(int i=1;i<s.scores.size();i++) {
                    if(s.scores.get(i)!=-1) {
                        System.out.println(all_companies_visited.get(i) +" " + s.scores.get(i));
                        atleast_one_test_given = true;
                    }
                }
                if(atleast_one_test_given == false) {
                    System.out.println();
                }
            }

        }






    }






    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int no_of_students = scan.nextInt();

            if(no_of_students<=0) {
                System.out.println("Exiting ... ");
                System.exit(0);
            }

            Application app = new Application();

            for(int i=0;i<no_of_students;i++) {
                String[] inp = br.readLine().trim().split("\\s+");
                if(valid_course(inp[1])) {
                    float cgpa = Float.parseFloat(inp[0]);
                    app.add_student(cgpa, inp[1]);
                }
                else {
                    System.out.println("Invalid course");
                }

            }

            while(true) {
                int query = scan.nextInt();
                switch(query) {
                    case 1:
                        String name = scan.next();
                        System.out.print("Number of eligible courses : ");
                        int no_of_courses = scan.nextInt();
                        List<String> course_criteria = new ArrayList<>();
                        boolean not_valid = false;
                        for(int j=0;j<no_of_courses;j++) {
                            course_criteria.add(scan.next());
                            if(!valid_course(course_criteria.get(j))) {
                                not_valid = true;
                                break;
                            }
                        }
                        if(not_valid==true) {
                            System.out.println("Invalid course entered - not registering this company ");
                            continue;
                        }
                        System.out.print("Number of Required Students : ");
                        int requirement = scan.nextInt();
                        app.add_company(name, no_of_courses, requirement, course_criteria);
                        break;


                    case 2:
                        app.display_remove_placed_students();
                        break;

                    case 3:
                        app.display_remove_closed_companies();
                        break;

                    case 4:
                        app.no_of_unplaced_students();
                        break;
                    case 5:
                        app.display_open_companies();
                        break;
                    case 6:
                        String comp_name = scan.next();
                        app.display_select_students(comp_name);
                        break;
                    case 7:
                        String company_name = scan.next();
                        app.display_company_details(company_name);
                        break;
                    case 8:
                        int roll = scan.nextInt();
                        app.display_student_details(roll);
                        break;
                    case 9:
                        int rollno = scan.nextInt();
                        app.companies_applied_by_student(rollno);
                        break;
                    default: System.out.println("Invalid query ...");
                }
            }

        }
        catch (Exception E){}

    }



}