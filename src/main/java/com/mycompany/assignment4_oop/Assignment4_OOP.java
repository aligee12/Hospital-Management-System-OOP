/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment4_oop;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

class Person implements Serializable{
    
    protected int ID;
    protected String name;
    protected int age;
    
    public Person() {
        this.ID = 0;
        this.name = null;
        this.age = 0;
    }

    public Person(int ID, String name, int age) {
        this.ID = ID;
        this.name = name;
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "ID=" + ID + ", name=" + name + ", age=" + age + '}';
    }
    public void display(){
        System.out.println(this.toString());
    }
    
}
class Doctor extends Person implements Serializable{
    private String specialization;
    private static final String doctorFile = "doctorFile";

    public Doctor() {
        super();
        this.specialization = null;
    }

    public Doctor(int ID, String name, int age, String specialization) {
        super(ID, name, age);
        this.specialization = specialization;
    }
    public void saveDoctor(){
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(doctorFile,true));
            writer.writeObject(this);
            System.out.println("Doctor Data saved to file Successfully");
        }catch (Exception e){
            System.out.println("Error saving data to file");
        }
    }
    public static ArrayList<Doctor> readAllDoctors(){

        ArrayList<Doctor> ar = new ArrayList<>();
        while (true) {
            try {
                ObjectInputStream reader = new ObjectInputStream(new FileInputStream(doctorFile));
                Doctor d = (Doctor) reader.readObject();
                ar.add(d);
            } catch (Exception e) {
                System.out.println("Error saving data to file");
                break;
            }
        }
        return ar;
    }
    public void displayAllDoctors(){
        ArrayList<Doctor> ar = readAllDoctors();
        int i = 1;
        for(Doctor d: ar){
            System.out.println("\nDoctor # "+i+"\n");
            d.display();
            i++;
        }
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "{" +
                "specialization='" + specialization + '\'' +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    public void display(){
        System.out.println(this.toString());
    }
}
class Patient extends Person implements Serializable{
    private String illness;
    private static final String patientFile = "patientFile";

    public Patient() {
        this.illness = null;
    }

    public Patient(int ID, String name, int age, String illness) {
        super(ID, name, age);
        this.illness = illness;
    }
    public void savePatient(){
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(patientFile,true));
            writer.writeObject(this);
            System.out.println("Patient data saved to file Successfully");
        }catch (Exception e){
            System.out.println("Error saving data to file");
        }
    }
    public static ArrayList<Patient> readAllPatients(){

        ArrayList<Patient> ar = new ArrayList<>();
        while (true) {
            try {
                ObjectInputStream reader = new ObjectInputStream(new FileInputStream(patientFile));
                Patient p = (Patient) reader.readObject();
                ar.add(p);
            } catch (Exception e) {
                System.out.println("Error saving data to file");
                break;
            }
        }
        return ar;
    }
    public void displayAllPatients(){
        ArrayList<Patient> ar = readAllPatients();
        int i = 1;
        for(Patient p: ar){
            System.out.println("\nPatient # "+i+"\n");
            p.display();
            i++;
        }
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getIllness() {
        return illness;
    }

    @Override
    public String toString() {
        return "{" +
                "illness='" + illness + '\'' +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    public void display(){
        System.out.println(this.toString());
    }

}
class Appointment implements Serializable{
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private static final String appointmentFile = "appointmentFile";
    public Appointment() {
        this.doctor = null;
        this.patient = null;
        this.date = null;
    }

    public Appointment(Doctor doctor, Patient patient, LocalDate date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }
    public void saveAppointment(){
        ArrayList<Appointment> ar = readAllAppointments();
        ar.add(this);
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(appointmentFile,false));          //no append required as writing whole list
            writer.writeObject(ar);
            System.out.println("Apointment saved to file Successfully");
        }catch (Exception e){
            System.out.println("Error saving data to file");
        }
        updateAppointmentsFile(ar);
    }
    public static  ArrayList<Appointment> readAllAppointments(){
        ArrayList<Appointment> ar = new ArrayList<>();
        File file = new File(appointmentFile);
        if (file.exists()){
            try {
                ObjectInputStream reader = new ObjectInputStream(new FileInputStream(appointmentFile));
                ar = (ArrayList<Appointment>) reader.readObject();
            } catch (Exception e) {
                System.out.println("Error reading data from file");
            }
        }
        return ar;
    }
    public static void displayAllAppointments(){
        ArrayList<Appointment> ar = readAllAppointments();
        int i = 1;
        for(Appointment ap: ar){
            System.out.println("\nAppointment # "+i+"\n");
            ap.display();
            i++;
        }
    }
    public void deleteAppointment(){
        ArrayList<Appointment> ar = readAllAppointments();
        ar.remove(this);
        updateAppointmentsFile(ar);
    }
    public void updateAppointment(Appointment newApp){
        ArrayList<Appointment> ar = readAllAppointments();
        int i = ar.indexOf(this);
        if (i >= 0){
            ar.set(1,newApp);
        }
        updateAppointmentsFile(ar);
    }
    public static void updateAppointmentsFile(ArrayList<Appointment> ar){
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(appointmentFile,false));
            writer.writeObject(ar);
            System.out.println("Files updated Successfully");
        }catch (Exception e){
            System.out.println("Error updating file");
        }
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "doctor=" + doctor.toString() +
                "\npatient=" + patient.toString() +
                "\ndate=" + date.toString() ;
    }
    public void display(){
        System.out.println(this.toString());
        System.out.println();
    }
}
public class Assignment4_OOP {

    public static void main(String[] args) {

        Doctor doctor = new Doctor(1,"Qasim Ali",35,"Cardiologist");
        doctor.saveDoctor();

        Patient patient1 = new Patient(2,"Nafees",22,"Heart Failure");
        Patient patient2 = new Patient(2,"Raza",22,"High Blood Pressure");
        patient1.savePatient();
        patient2.savePatient();

        Appointment appointment1 = new Appointment(doctor,patient1, LocalDate.of(2023,12,18));
        Appointment appointment2 = new Appointment(doctor,patient2,LocalDate.of(2024,1,5));
        appointment1.saveAppointment();
        appointment2.saveAppointment();

        Appointment.displayAllAppointments();

        ArrayList<Appointment> ar = Appointment.readAllAppointments();
        ArrayList<Appointment> todayAppoin = new ArrayList<Appointment>();      // arraylist for saving today appointments

        for(Appointment ap : ar){
            if(ap.getDate().equals(LocalDate.now())){
                todayAppoin.add(ap);        // adding to arraylist of today appointments
                System.out.println("Today Appointment: ");
                ap.display();
            }
        }
        System.out.println("Moving dates one day ahead!");
        for(Appointment ap : todayAppoin){
            int i = ar.indexOf(ap);         // checking index of today appointment in total appointments
            ap.setDate(ap.getDate().plusDays(1));
            ar.set(i,ap);
            System.out.println("After changind Date of  Appointment: ");
            ap.display();
        }

        //updating file after moving date one day ahead
        Appointment.updateAppointmentsFile(ar);






    }
}
