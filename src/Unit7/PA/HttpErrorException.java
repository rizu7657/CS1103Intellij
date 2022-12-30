package Unit7.PA;

public class HttpErrorException extends Throwable {
    private int httpErrorCode;

    public HttpErrorException(int errorCode, String errorMessage) {

        super(errorMessage);

        httpErrorCode = errorCode;

    }

    public int getHttpErrorCode() {

        return this.httpErrorCode;

    }

    public String getGenericErrorDescription() {

        switch (httpErrorCode) {

            case 400:

                return "Bad Request";

            case 403:

                return "Forbidden";

            case 404:

                return "Not Found";

            case 405:

                return "Method Not Allowed";

            case 500:

                return "Internal Server Error";

            default:

                return "Unknown Error";

        }

    }
}
