package org.example.basic.domain.account.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.basic.domain.account.constant.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveAccountRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotNull(message = "권한을 선택해주세요.")
    private Role role;

    public void encodePassword(String password) {
        this.password = password;
    }
}
