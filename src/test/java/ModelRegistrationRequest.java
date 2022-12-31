public class ModelRegistrationRequest {
    public String email;
    public String password;

    public ModelRegistrationRequest() {
    }

    public ModelRegistrationRequest(String email) {
        this.email = email;
    }

    public ModelRegistrationRequest(String email, String password) {
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
