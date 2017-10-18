package uffmailgenerator;

/**
 *
 * @author Marlon
 */
public class Main {
    
    
    public static void main(String[] args) {
        
        DatabaseManager dbManager = new DatabaseManagerCSV();
        
        dbManager.fillStudentList("alunos.csv");        
        UserInterface ui = new UserInterface();
        ui.start(dbManager);
    }
    
}
