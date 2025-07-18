import java.io.Serializable;

public class Villain extends StandUser implements Serializable {
    private String occupation, group, reason;


    //Constructors
    public Villain(String firstName, String lastName, String standName, int age, int fromPart, String occupation, String group, String reason) {
        super(firstName, lastName, standName, age, fromPart);
        this.occupation = occupation;
        this.group = group;
        this.reason = reason;
    }

    public Villain() {
        super();
        this.occupation = "Unemployed";
        this.group = "Unaffiliated";
        this.reason = "Unknown";
    }


    //Instance Methods
    public String toString() {
        return String.format("[Villain]%s   Occupation: %s\n   Group: %s\n   Reason: \"%s\"\n", super.toString(), this.occupation, this.group, this.reason);
    }


    //Getter Methods
    public String getOccupation() {
        return this.occupation;
    }

    public String getGroup() {
        return this.group;
    }

    public String getReason() {
        return this.reason;
    }


    //Setter Methods
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}