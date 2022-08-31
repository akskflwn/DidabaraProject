package com.bitcamp221.didabara.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emailconfig")
public class EmailConfigEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auth_code", length = 30)
    private String authCode;

    @Column(name = "check_user")
    private boolean checkUser;
}