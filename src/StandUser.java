import java.io.Serializable;

public class StandUser implements Serializable {
    private static int count = 1;
    private String firstName, lastName, standName;
    private int age, fromPart, ID;


    //Constructors
    public StandUser(String firstName, String lastName, String standName, int age, int fromPart) {
        this.ID = count;
        this.firstName = firstName;
        this.lastName = lastName;
        this.standName = standName;
        this.age = age;
        this.fromPart = fromPart;

        count++;
    }

    public StandUser() {
        this.ID = count;

        count++;
    }


    //Instance Methods
    public String toString() {
        return String.format("\n   ID: %d\n   First Name: %s\n   Last Name: %s\n   Stand Name: %s\n   Age: %d\n   From Part: %d\n", this.ID, this.firstName, this.lastName, this.standName, this.age, this.fromPart);
    }


    //Getter Methods
    public int getID() {
        return this.ID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getStandName() {
        return this.standName;
    }

    public int getAge() {
        return this.age;
    }

    public int getFromPart() {
        return this.fromPart;
    }


    //Setter Methods
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFromPart(int fromPart) {
        this.fromPart = fromPart;
    }
}