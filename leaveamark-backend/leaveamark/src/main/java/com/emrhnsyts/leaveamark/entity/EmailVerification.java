package com.emrhnsyts.leaveamark.entity;

import com.emrhnsyts.leaveamark.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerification extends BaseEntity {
    @Column(nullable = false, unique = true, updatable = false)
    private String token;
    @OneToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private AppUser appUser;
}
