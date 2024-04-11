package practicum.request;

public class authorizationReq {
    private final String email;
    private final String password;

    public authorizationReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
