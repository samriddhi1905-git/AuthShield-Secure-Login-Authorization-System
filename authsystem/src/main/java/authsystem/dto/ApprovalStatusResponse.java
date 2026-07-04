package authsystem.dto;

public class ApprovalStatusResponse {

    private boolean approved;
    private String jwt;

    public ApprovalStatusResponse() {
    }

    public ApprovalStatusResponse(boolean approved, String jwt) {
        this.approved = approved;
        this.jwt = jwt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}