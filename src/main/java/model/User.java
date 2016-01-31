package model;

import java.util.Date;

/**
 * Class for user description.
 */
public class User {
    /**
     * User id - primary key in database.
     */
    private Long id;
    /**
     * Login of user.
     */
    private String login;
    /**
     * User password.
     */
    private String password;
    /**
     * User phone.
     */
    private String phone;
    /**
     * User email.
     */
    private String email;
    /**
     * User registration date.
     */
    private Date registrationDate;
    /**
     * Date of last password change by the user.
     */
    private Date changePasswordDate;
    /**
     * The number of invalid login attempts.
     */
    private Integer countIncorrectAttemptsToLogin;
    /**
     * User role.
     */
    private Role role;
    /**
     * User blocked or not.
     */
    private Boolean isBlocked;

    public User() {
    }

    public User(String login, String password, String phone, String email, Role role) {
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.registrationDate = new Date(System.currentTimeMillis());
        this.changePasswordDate = new Date(System.currentTimeMillis());
        this.countIncorrectAttemptsToLogin = 0;
        this.role = role;
        this.isBlocked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getChangePasswordDate() {
        return changePasswordDate;
    }

    public void setChangePasswordDate(Date changePasswordDate) {
        this.changePasswordDate = changePasswordDate;
    }

    public Integer getCountIncorrectAttemptsToLogin() {
        return countIncorrectAttemptsToLogin;
    }

    public void setCountIncorrectAttemptsToLogin(Integer countIncorrectAttemptsToLogin) {
        this.countIncorrectAttemptsToLogin = countIncorrectAttemptsToLogin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", changePasswordDate=" + changePasswordDate +
                ", countIncorrectAttemptsToLogin=" + countIncorrectAttemptsToLogin +
                ", role=" + role +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
