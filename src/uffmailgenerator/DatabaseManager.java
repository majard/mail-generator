package uffmailgenerator;

import java.util.List;

/**
 *
 * @author Marlon
 */
public interface DatabaseManager {

    /* Fills the List studentList with the students contained in the csv file
       named fileName in the root directory */    
    public void fillStudentList(String fileName);
    
    /* Generate e-mail options based on a permutation of the student names while
       avoiding conflicts with the e-mails already present in the database */
    public List<String> generateOptions(Student student);
    
    /* Returns the size of the database i.e. the number of students it has */
    public int databaseSize();
    
    /* Find a student based on the id. Returns null if no student with the
       corresponding id was found */
    public Student findStudent(String id);
    
}
