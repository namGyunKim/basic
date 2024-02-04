package org.example.basic.domain.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "persistent_logins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersistentLogins {

    @Id
    private String series;

    private String username;
    private String token;
    private LocalDateTime lastUsed;

}
