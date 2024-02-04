package org.example.basic.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.basic.domain.account.constant.Role;
import org.example.basic.domain.account.payload.request.SaveAccountRequest;
import org.example.basic.global.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String name;
    @Column(unique = true)
    private String loginId;
    private String password;

    private boolean accountUnLocked;
    private LocalDateTime accountUnLockedAt;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    public Account(SaveAccountRequest saveAccountRequest) {
        this.name = saveAccountRequest.getName();
        this.loginId = saveAccountRequest.getLoginId();
        this.password = saveAccountRequest.getPassword();
        this.role = saveAccountRequest.getRole();
    }

    public void unlockAccount() {
        this.accountUnLocked = true;
        this.accountUnLockedAt = LocalDateTime.now();
    }
}
