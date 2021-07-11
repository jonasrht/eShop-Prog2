package eshop.valueobjects;
/**
 * Abstrakte Klasse zum Verwalten der Personen.
 *
 * @author Jonas, Jana, Dabin
 * @abstract
 */
public abstract class Person {
    // Attribute
    protected String email;
    protected String passwort;
    protected int personID = 0;
    protected String  name;
    protected static int num;

    /**
     * Konstruktor
     *
     * @param name der Person
     * @param email der Person
     * @param passwort der Person
     */
    public Person(String name, String email, String passwort){
        this.personID = num++;
        this.name = name;
        this.email = email;
        this.passwort = passwort;
    }

    /**
     * Accessor-Methoden
     */
    public Person(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPasswort (String passwort){
        this.passwort = passwort;
    }

    public String getPasswort(){
        return this.passwort;
    }

    public void setPersonID (int personID){
        this.personID = personID;
    }

    public int getPersonID(){
        return this.personID;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
