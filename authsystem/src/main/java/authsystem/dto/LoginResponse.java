package authsystem.dto;

public class LoginResponse {

    private String status;
    private String approvalToken;

    public LoginResponse() {
    }

    public LoginResponse(String status, String approvalToken) {
        this.status = status;
        this.approvalToken = approvalToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalToken() {
        return approvalToken;
    }

    public void setApprovalToken(String approvalToken) {
        this.approvalToken = approvalToken;
    }
}