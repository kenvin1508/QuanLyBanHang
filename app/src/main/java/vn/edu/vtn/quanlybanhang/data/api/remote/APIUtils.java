package vn.edu.vtn.quanlybanhang.data.api.remote;

public class APIUtils {
    private static String baseURL = "http://3anhem.somee.com/api/";

    public static APIService getServer() {
        return APIClient.getClient(baseURL).create(APIService.class);
    }
}
