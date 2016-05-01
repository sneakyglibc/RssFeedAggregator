package rss_feed.rssreader.Data;

/**
 * Created by Phil on 24/04/2016.
 */
public class User {
    private String password;
    private String email;

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return  password + " - " + email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
