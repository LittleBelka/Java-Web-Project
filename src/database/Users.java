package database;

/**
 * This is the class that contains all the fields of the table Users from the database.
 */
public class Users {

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
    private String email;

    /**
     * This is the constructor to initialize the fields.
     * @param id user id
     * @param login user login
     * @param password user password
     * @param firstName user first name
     * @param middleName user middle name
     * @param lastName user last name
     * @param status user status (student or tutor)
     * @param email user email
     */
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
