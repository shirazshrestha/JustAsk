package controllers;

public class User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Integer id;



    User(String username, String password, Integer id, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

}
