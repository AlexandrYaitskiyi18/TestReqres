public class ModelCreateResponse extends ModelCREATE{

    public String id;
    public String createdAt;

    public ModelCreateResponse() {
    }

    public ModelCreateResponse(String name, String job, String id, String createdAt ) {
        super(name, job);
        this.id=id;
        this.createdAt=createdAt;

    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
