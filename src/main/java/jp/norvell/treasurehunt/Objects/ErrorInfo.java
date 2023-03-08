package jp.norvell.treasurehunt.Objects;

public class ErrorInfo {

    private final boolean _isError;
    private final String _errMsg;

    public ErrorInfo(boolean isError) {
        this(isError, "");
    }

    public ErrorInfo(boolean isError, String errMsg) {
        _isError = isError;
        _errMsg = errMsg;
    }

    public boolean GetIsError() {
        return _isError;
    }

    public String GetErrorMessage() {
        return  _errMsg;
    }
}
