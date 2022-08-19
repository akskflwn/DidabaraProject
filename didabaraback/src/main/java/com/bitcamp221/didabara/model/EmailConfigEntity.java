package com.bitcamp221.didabara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emailconfig")
public class EmailConfigEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    //  외래키를 가져와서 기본키로 설정하기 위한 설정.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "auth_code", length = 30)
    private String authCode;
}
