package uffmailgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Marlon
 */


public class UffMailGenerator {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Student> studentList = new ArrayList<>();
        fillStudentList(studentList, "alunos.csv");        
        
        Scanner input = new Scanner(System.in);
        System.out.println("Digite sua matrícula:");
        String mat = input.nextLine();
        Student student = null;
        
        int i = 0;
        while (i < studentList.size()){
            if(studentList.get(i).getRegistrationNumber().equals(mat)){
                student = studentList.get(i);
                break;
            }
            ++i;
        }
        
        if (i == studentList.size()){
            System.out.println("Não encontramos esse número de matrícula.");
        } else if (!student.getUffmail().isEmpty()){
            System.out.format("%s, você já tem um uffmail: %s\n", 
                                student.getName(), student.getUffmail() );
        } else if(!student.isActive()){
            System.out.format("%s, sua matrícula não está ativa e portanto "
                    + "você não pode criar um uffmail.\n", student.getName());
        } else{
            System.out.println("BORA CRIAR UM UFFMAIL AGORA\n");
        }
    }
    
    /*Fills the List studentList with the students contained in the csv file
      named fileName in the root directory
    */
    public static List<Student> fillStudentList(List<Student> studentList, String fileName){
        
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
        
        return studentList;
    }
    
}
