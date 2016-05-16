package database;

public class Users {

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
    private String email;

    public Users(int id, String login, String password, String firstName, String middleName,
                 String lastName, String status, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.status = status;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }
}
