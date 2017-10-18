package uffmailgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Marlon
 */


public class DatabaseManagerCSV implements DatabaseManager {
    private List<Student> studentList;
    private HashSet<String> mailList;

    
    public DatabaseManagerCSV(){
        this.studentList = new ArrayList<>();
        this.mailList = null;
    }
    
    /*Fills the List studentList with the students contained in the csv file
      named fileName in the root directory
    */
    @Override
    public void fillStudentList(String fileName){
        try(Scanner scanner =  new Scanner(new File(fileName))){
            scanner.nextLine();  //jumps the first line that has the header row
            
            Scanner studentInfo;
            
            while(scanner.hasNextLine()){
                studentInfo = new Scanner(scanner.nextLine());
                studentInfo.useDelimiter(",");
                String name = studentInfo.next();
                String registrationNumber = studentInfo.next();
                String phoneNumber = studentInfo.next();
                String email = studentInfo.next();
                String uffmail = studentInfo.next();
                Boolean status = (studentInfo.next().equals("Ativo"));
                Student student = new Student(name, registrationNumber,
                        phoneNumber, email, uffmail, status);
                studentList.add(student);
            }
            
        } catch(IOException err){
            System.out.println("Não foi possível abrir o arquivo.");
            System.out.println(err.getMessage());
        }
        
        /* Fills the mailList with a HashSet so we're able to search for 
           e-mail addresses in O(1). Specially useful for the generation
           of e-mail alternatives, that needs to perform a lot of 
           searches. */
        mailList = createHashSet(studentList);          
    }
    
    @Override
    public List<String> generateOptions(Student student){
        
        String[] names = student.getName().toLowerCase().split(" ");
        List<String> options = new ArrayList<>();        
        String domain = "@id.uff.br";
        String option;
                
        for (int i = 1; i < names.length; i++) {
            option = names[0] + names[i] + domain;
            
            if (!mailList.contains(option)){
                options.add(option);
            }
            
            option = names[0].substring(0,1) + names[i] + domain;
            
            if (!mailList.contains(option)){
                options.add(option);
            }
            
            for (int j = i + 1; j < names.length; j++) {
                option = names[0].substring(0,1) + names[i] + names[j] +
                        domain;
                
                if (!mailList.contains(option)){
                    options.add(option);
                }
                option = names[0].substring(0,1) + names[i] + 
                        names[j].substring(0,1) + domain;
                
                if (!mailList.contains(option)){
                    options.add(option);
                }
                option = names[0].substring(0,1) + names[i].substring(0,1) 
                        + names[j] + domain;
                
                if (!mailList.contains(option)){
                    options.add(option);
                }                
            }
        }
        
        return options;                   
    }
    
    public HashSet<String> createHashSet(List<Student> studentList){
        HashSet<String> result = new HashSet<>();
        String uffmail;
        for (int i = 0; i < studentList.size(); i++) {
            uffmail = studentList.get(i).getUffmail();
            if (!uffmail.isEmpty()) {
                result.add(uffmail);
            }            
        }
        return result;
    }
    
    public int databaseSize(){
        return studentList.size();
    }
    
    public Student findStudent(String id){
        Student student = null;
        int i = 0;
        
        while (i < studentList.size()){
            if(studentList.get(i).getId().equals(id)){
                student = studentList.get(i);
                break;
            }
            ++i;
        }
        
        return student;
    }
    
}
