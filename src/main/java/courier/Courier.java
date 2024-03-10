package courier;

public class Courier {
    private String login;
    private String password;

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Courier from(ModelCourier modelCourier) {
        return new Courier(modelCourier.getLogin(), modelCourier.getPassword());
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
}
