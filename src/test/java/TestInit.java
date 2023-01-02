public class TestInit {

    static final String URL = "https://reqres.in/";
    static final Integer STATUSOK = 200;
    static final Integer STATUSBAD = 404;

    public static void SPEC200() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
    }

    public static void SPEC201() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification201());
    }

    public static void SPEC204() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification204());
    }

    public static void SPEC400() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification400());
    }

    public static void SPEC404() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification404());
    }
}
