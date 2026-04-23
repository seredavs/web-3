package lab4.server.dto;

public class ApiResponse<T> {
    private boolean error;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(boolean error, T data) {
        this.error = error;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(false, data);
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>(true, data);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
