package com.sealab.webservice.api.resourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Activationtoken")
@Data
@AllArgsConstructor
public class Activationtoken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Activationtoken() {}

    public Activationtoken(User userEntity) {
        this.user = userEntity;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
