package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class LoginPostDTO {

    private String tokenId;
    private String emailId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
