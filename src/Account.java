public class Account {

    private String name;
    private String website;
    private String username;
    private String password;

    public Account(String name, String website, String username, String password) {
        this.name = name;
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account: " + name + "\n" +
                "\t" + "Website: " + this.website + "\n" +
                "\t" + "Username: " + this.username + "\n" +
                "\t" + "Password: " + this.password + "\n";
    }

}
