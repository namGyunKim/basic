package org.example.basic.domain.account.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.basic.domain.account.constant.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveAccountRequest {
    private String name;
    private String loginId;
    private String password;
    private Role role;

    public void encodePassword(String password) {
        this.password = password;
    }
}
