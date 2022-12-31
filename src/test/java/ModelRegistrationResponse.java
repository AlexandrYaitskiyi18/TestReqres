public class ModelRegistrationResponse {

    public Integer id;
    public String token;
    public String error;

    public ModelRegistrationResponse() {
    }


    public ModelRegistrationResponse(String error) {
        this.error = error;
    }

    public ModelRegistrationResponse(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
