package cz.muni.fi.pa165.plpm.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 *
 * @author Radoslav Cerhak
 */
public class TrainerAuthenticateDTO {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
