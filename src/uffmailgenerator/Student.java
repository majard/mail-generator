/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uffmailgenerator;

/**
 *
 * @author Marlon
 */
public class Student {
    private String name;
    private String registrationNumber;
    private String phoneNumber;
    private String email;
    private String uffmail;
    private Boolean status;
    
    public Student(String name, String registrationNumber, String phoneNumber,
            String email,String uffmail, Boolean status){
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.uffmail = uffmail;
        this.status = status;
    }
    
    public Boolean isActive(){
        return this.status;
    }
    
    public String getRegistrationNumber(){
        return this.registrationNumber;
    }
    
    public String getUffmail(){
        return this.uffmail;
    }   
    public String getName(){
        return this.name;
    }   
    
    public String getFirstName(){
        return this.name.split(" ")[0];
    }
    
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public Boolean setUffmail(String uffmail){
        if (this.uffmail.isEmpty()){
            this.uffmail = uffmail;
            return true;
        } else {
            return false;
        }
    }   
    
    @Override
    public String toString(){
        String studentInfo = "";
        studentInfo += "Nome: " + name + "\n";        
        studentInfo += "Matrícula: " + registrationNumber + "\n";
        studentInfo += "Telefone: " + phoneNumber + "\n";
        studentInfo += "E-mail: " + email + "\n";
        studentInfo += "Uffmail: " + uffmail + "\n";
        studentInfo += "Status: " + (status? "Ativo": "Inativo") + "\n";
        return studentInfo;
    }
}
