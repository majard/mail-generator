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
                                student.getFirstName(), student.getUffmail() );
        } else if(!student.isActive()){
            System.out.format("%s, sua matrícula não está ativa e portanto "
                    + "você não pode criar um uffmail.\n", student.getFirstName());
        } else{
            System.out.println("BORA CRIAR UM UFFMAIL AGORA\n");
            ArrayList<String> options = generateOptions(student, studentList);
            System.out.format("%s, por favor escolha uma das opções abaixo"
                    + " para o seu UFFMail\n", student.getFirstName());
            
            for (int j = 0; j < options.size(); j++) {
                System.out.format("%d - %s \n", j + 1, options.get(j));
            }
            
            int option = 0;
            
            do{
                if(input.hasNextInt()){
                    option = input.nextInt();
                    if (option < 1 || option > options.size()){
                        option = 0;
                        System.out.format("Selecione um número entre 1 e "
                                + "%d.\n", options.size());
                    }
                } else {
                    input.next();
                    System.out.format("Selecione um número entre 1 e "
                                + "%d.\n", options.size());
                }
            } while(option == 0);
            
            --option;
            
            System.out.format("A criação de seu email (%s) será feita nos "
            + "próximos minutos.\nUm SMS foi enviado para %s com a sua senha "
            + "de acesso.\n", options.get(option), student.getPhoneNumber());
            
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
    
    public static ArrayList<String> generateOptions(Student student, 
            List<Student> studentList){
        
        String[] names = student.getName().toLowerCase().split(" ");
        List<String> options = new ArrayList<>();
        HashSet<String> mailList = createHashSet(studentList);
        
        String option;
                
        for (int i = 1; i < names.length; i++) {
            option = names[0] + names[i] + "@id.uff.br";
            if (!mailList.contains(option)){
                options.add(option);
            }
        }
        
        return (ArrayList<String>) options;                
    }
    
    public static HashSet<String> createHashSet(List<Student> studentList){
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
}
