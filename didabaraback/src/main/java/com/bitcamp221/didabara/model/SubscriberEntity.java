package com.bitcamp221.didabara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriber")
public class SubscriberEntity extends BaseTimeEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToMany(mappedBy = "writer")
  private List<ReplyEntity> replyEntities = new ArrayList<ReplyEntity>();

  @OneToMany(mappedBy = "user")
  private List<CheckedEntity> checkedEntities =new ArrayList<CheckedEntity>();
}
