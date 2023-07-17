package helpful;

//Review -> use Record
public class UserData {
    private String firstName;
    private String lastName;
    private String certType;
    private String certLevel;

    public UserData(String firstName, String lastName, String certType, String certLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.certType = certType;
        this.certLevel = certLevel;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertLevel() {
        return certLevel;
    }

    public void setCertLevel(String certLevel) {
        this.certLevel = certLevel;
    }
}
