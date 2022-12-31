public class ModelCreateResponse extends ModelCREATE{

    public String id;
    public String createdAt;
    public String updatedAt;

    public ModelCreateResponse() {
    }

    public ModelCreateResponse(String name, String job, String id, String createdAt ) {
        super(name, job);
        this.id=id;
        this.createdAt=createdAt;

    }

    public ModelCreateResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
