package uffmailgenerator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Marlon
 */
public class UserInterface {
    private Scanner input;
    
    public UserInterface(){        
        this.input = new Scanner(System.in);        
    }
    
    public void start(DatabaseManager dbManager){
        
        System.out.println("Digite sua matrícula:");
        String id = input.nextLine();
        
        Student student = dbManager.findStudent(id);
        
        if (student == null){            
            System.out.println("Não encontramos esse número de matrícula.");
            
        } else if (!student.getUffmail().isEmpty()){
            System.out.format("%s, você já tem um uffmail: %s\n",
                                student.getFirstName(), student.getUffmail());
            
        } else if(!student.isActive()){
            System.out.format("%s, sua matrícula não está ativa e portanto "
                               + "você não pode criar um uffmail.\n", 
                                student.getFirstName());
            
        } else{
            ArrayList<String> options;
            options = (ArrayList<String>) dbManager.generateOptions(student);
            
            System.out.format("%s, por favor escolha uma das opções abaixo"
                    + " para o seu UFFMail\n", student.getFirstName());
            
            int option = 0;
            
            printOptions(options);
            chooseOption(option, options.size());
            
            System.out.format("A criação de seu email (%s) será feita nos "
            + "próximos minutos.\nUm SMS foi enviado para %s com a sua senha "
            + "de acesso.\n", options.get(option), student.getPhoneNumber());            
        }
    }
    
    /* This function keeps the user in a loop until he/she chooses a valid
       option from the list (a number between 1 and size)
    */    
    public void chooseOption(int option, int size){
        if (size == 0) return;
        
        do{
                if(input.hasNextInt()){
                    option = input.nextInt();
                    if (option < 1 || option > size){
                        option = 0;
                        System.out.format("Selecione um número entre 1 e "
                                + "%d.\n", size);
                    }
                } else {
                    input.next();
                    System.out.format("Selecione um número entre 1 e "
                                + "%d.\n", size);
                }
            } while(option == 0);
            
            --option;
    }
    
    public void printOptions(ArrayList<String> options){
        for (int j = 0; j < options.size(); j++) {
                System.out.format("%d - %s \n", j + 1, options.get(j));
            }
    }
    
}
