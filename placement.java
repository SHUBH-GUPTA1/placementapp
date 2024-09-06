import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Student {
    String name;
    String branch;
    double gpa;
    String skills;
    String USN;

    public Student(String name,String branch, double gpa, String skills, String USN) {
        this.name = name;
        this.branch=branch;
        this.gpa = gpa;
        this.skills = skills;
        this.USN = USN;
    }
}

class Company {
    String name;
    String reqbranch;
    double requiredGPA;

    public Company(String name,String reqbranch, double requiredGPA) {
        this.name = name;
        this.reqbranch=reqbranch;
        this.requiredGPA = requiredGPA;
    }
}

public class collegePlacementcell extends JFrame {
    private List<Student> students;
    private List<Company> companies;
    private JTextArea resultArea;

    public collegePlacementcell() {
        loadData();
        setTitle("College Placement Cell");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        students = new ArrayList<>();
        companies = new ArrayList<>();
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        JButton addCompanyButton = new JButton("Add Company");
        addCompanyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCompany();
            }
        });

        JButton sortStudentsButton = new JButton("Display all students");
        sortStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });
        JButton sortCompaniesForStudentButton = new JButton("Sort Companies for Student");
    sortCompaniesForStudentButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sortCompaniesForStudent();
        }
    });

        JButton displayStudentsButton = new JButton("Display Students for Company");
        displayStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStudentsForCompany();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addStudentButton);
        buttonPanel.add(addCompanyButton);
        buttonPanel.add(sortStudentsButton);
        buttonPanel.add(sortCompaniesForStudentButton);
        buttonPanel.add(displayStudentsButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void addStudent() {
       /* String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
        double gpa = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter GPA:"));
        String skills = JOptionPane.showInputDialog(this, "Enter Skills:");
        String resume = JOptionPane.showInputDialog(this, "Enter Resume:");

        Student student = new Student(name, gpa, skills, resume);
        students.add(student);
        saveStudentsToFile();
        updateResult("Student added: " + name);*/
       
            JTextField nameField = new JTextField(20);
            JTextField branchField=new JTextField(20);
            JTextField gpaField = new JTextField(5);
            JTextField skillsField = new JTextField(30);
            JTextField USNField = new JTextField(30);
    
            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Branch:"));
            inputPanel.add(branchField);
            inputPanel.add(new JLabel("GPA:"));
            inputPanel.add(gpaField);
            inputPanel.add(new JLabel("Skills:"));
            inputPanel.add(skillsField);
            inputPanel.add(new JLabel("USN:"));
            inputPanel.add(USNField);
    do{
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION || result== JOptionPane.CLOSED_OPTION) {
                return;
            }
        }while(nameField.getText().isEmpty() || branchField.getText().isEmpty() || gpaField.getText().isEmpty() || USNField.getText().isEmpty());
                String name = nameField.getText();
                String branch=branchField.getText();
                double gpa = Double.parseDouble(gpaField.getText());
                String skills = skillsField.getText();
                String USN = USNField.getText();
    
                Student student = new Student(name,branch,gpa, skills, USN);
                students.add(student);
                saveStudentsToFile();
                updateResult("Student added: " + name);
            }
        

    private void addCompany() {
        /*String name = JOptionPane.showInputDialog(this, "Enter Company Name:");
        double requiredGPA = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Required GPA:"));

        Company company = new Company(name, requiredGPA);
        companies.add(company);
        saveCompaniesToFile();
        updateResult("Company added: " + name);*/

        JTextField nameField = new JTextField(20);
        JTextField reqbranchField=new JTextField(10);
        JTextField requiredGPAField = new JTextField(5);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Company Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Branch:"));
        inputPanel.add(reqbranchField);
        inputPanel.add(new JLabel("Required GPA:"));
        inputPanel.add(requiredGPAField);
do{
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add Company", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            return;
        }
    }while(nameField.getText().isEmpty() || reqbranchField.getText().isEmpty());
            
            String name = nameField.getText();
            String reqbranch=reqbranchField.getText();
            double requiredGPA = Double.parseDouble(requiredGPAField.getText());

            Company company = new Company(name,reqbranch, requiredGPA);
            companies.add(company);
            saveCompaniesToFile();
            updateResult("Company added: " + name);
    }


    private void displayAllStudents() {
         /*Collections.sort(students, (s1,s2) -> Double.compare(s2.gpa, s1.gpa));
         saveStudentsToFile();
         displayStudents();*/
         resultArea.setText(""); // Clear the existing text in the JTextArea
         String csvFile = "students.csv";
         String line;
         String csvSplitBy = ",";
     
         try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
             // Skip the first line (header) of the CSV file
             br.readLine();
     
             while ((line = br.readLine()) != null) {
                 String[] data = line.split(csvSplitBy);
                 if (data.length == 5) {
                     String name = data[0].trim();
                     String branch = data[1].trim();
                     double gpa = Double.parseDouble(data[2].trim());
                     String skills = data[3].trim();
                     String USN = data[4].trim();
     
                     // Append the student data to the resultArea JTextArea
                     resultArea.append("Name: " + name + "\n");
                     resultArea.append("Branch: " + branch + "\n");
                     resultArea.append("GPA: " + gpa + "\n");
                     resultArea.append("Skills: " + skills + "\n");
                     resultArea.append("USN: " + USN + "\n\n");
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    
     /*private void displayStudentsForCompany() {
        String companyName = JOptionPane.showInputDialog(this, "Enter Company Name:");
    
        // Load company data from the file and search for the matching company
        boolean foundCompany = false;
        try (BufferedReader br = new BufferedReader(new FileReader("companies.csv"))) {
            // Skip the first line (header) of the CSV file
            br.readLine();
    
            String line;
            String csvSplitBy = ",";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String name = data[0].trim();
                    String reqbranch = data[1].trim();
                    double requiredGPA = Double.parseDouble(data[2].trim());
    
                    if (name.equalsIgnoreCase(companyName)) {
                        foundCompany = true;
                        List<Student> eligibleStudents = new ArrayList<>();
                        for (Student student : students) {
                            if (student.gpa >= requiredGPA && student.branch.equalsIgnoreCase(reqbranch)) {
                                eligibleStudents.add(student);
                            }
                        }
                        displayStudents(eligibleStudents);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (!foundCompany) {
            updateResult("Company not found!");
        }
    }*/
    

    private void displayStudentsForCompany() {
        String companyName = JOptionPane.showInputDialog(this, "Enter Company Name:");
        Company selectedCompany = null;

        for (Company company : companies) {
            if (company.name.equalsIgnoreCase(companyName)) {
                selectedCompany = company;
                break;
            }
        }

        if (selectedCompany != null) {
            List<Student> eligibleStudents = new ArrayList<>();
            for (Student student : students) {
                if (student.gpa >= selectedCompany.requiredGPA && student.branch.equalsIgnoreCase(selectedCompany.reqbranch))  {
                    eligibleStudents.add(student);
                }
            }
            displayStudents(eligibleStudents);
        } else {
            updateResult("Company not found!");
        }
    }

    private void displayStudents() {
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append("Name: ").append(student.name).append("\n");
            sb.append("Branch:").append(student.branch).append("\n");
            sb.append("GPA: ").append(student.gpa).append("\n");
            sb.append("Skills: ").append(student.skills).append("\n");
            sb.append("USN: ").append(student.USN).append("\n\n");
        }
        updateResult(sb.toString());
    }
    private void sortCompaniesForStudent() {
      
        JTextField studentNameField = new JTextField(20);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(studentNameField);
    
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Sort Companies for Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String studentName = studentNameField.getText();
            Student selectedStudent = null;
    
            for (Student student : students) {
                if (student.name.equalsIgnoreCase(studentName)) {
                    selectedStudent = student;
                    break;
                }
            }
    
            if (selectedStudent != null) {
                List<Company> eligibleCompanies = new ArrayList<>();
                for (Company company : companies) {
                    if (company.reqbranch.equalsIgnoreCase(selectedStudent.branch) && selectedStudent.gpa >= company.requiredGPA) {
                        eligibleCompanies.add(company);
                    }
                }
    
                if (!eligibleCompanies.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Eligible Companies for ").append(studentName).append(":").append("\n");
                    for (Company company : eligibleCompanies) {
                        sb.append("Company Name: ").append(company.name).append("\n");
                        sb.append("Required GPA: ").append(company.requiredGPA).append("\n");
                        sb.append("\n");
                    }
                    updateResult(sb.toString());
                } else {
                    updateResult("No companies found for the given criteria.");
                }
            } else {
                updateResult("Student not found!");
            }
        }
    }
        
    
    private void displayStudents(List<Student> studentList) {
        StringBuilder sb = new StringBuilder();
        for (Student student : studentList) {
            sb.append("Name: ").append(student.name).append("\n");
            sb.append("BranchL:").append(student.branch).append("\n");
            sb.append("GPA: ").append(student.gpa).append("\n");
            sb.append("Skills: ").append(student.skills).append("\n");
            sb.append("USN: ").append(student.USN).append("\n\n");
        }
        updateResult(sb.toString());
    }

    private void updateResult(String text) {
        resultArea.setText(text);
    }

    private void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.csv", true))) {
            // Write column headers only if the file is empty
            if (new File("students.csv").length() == 0) {
                writer.write("Name,Branch,GPA,Skills,USN");
                writer.newLine();
            }
    
            for (Student student : students) {
                writer.write(student.name + "," + student.branch + "," + student.gpa + "," + student.skills + "," + student.USN);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void saveCompaniesToFile() {//save company name file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("companies.csv"))) {
            writer.write("Name,RequiredBranch,RequiredGPA");
            writer.newLine();
    
            for (Company company : companies) {
                writer.write(company.name + "," + company.reqbranch + "," + company.requiredGPA);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private void loadStudentsFromFile() {
        try (Scanner scanner = new Scanner(new File("students.csv"))) {
            // Skip the first line (header) of the CSV file
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0].trim();
                    String branch = parts[1].trim();
                    double gpa = Double.parseDouble(parts[2].trim());
                    String skills = parts[3].trim();
                    String USN = parts[4].trim();
                    students.add(new Student(name, branch, gpa, skills, USN));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void loadCompaniesFromFile() {
        try (Scanner scanner = new Scanner(new File("companies.csv"))) {
            // Skip the first line (header) of the CSV file
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String reqbranch = parts[1].trim();
                    double requiredGPA = Double.parseDouble(parts[2].trim());
                    companies.add(new Company(name, reqbranch, requiredGPA));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadData() {
        students = new ArrayList<>();
        companies = new ArrayList<>();
        loadStudentsFromFile();
        loadCompaniesFromFile();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                collegePlacementcell app = new collegePlacementcell();
                app.setVisible(true);
            }
        });
    }
}