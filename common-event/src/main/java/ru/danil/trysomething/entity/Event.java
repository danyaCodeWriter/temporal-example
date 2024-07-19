package ru.danil.trysomething.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.danil.trysomething.statuses.EventUserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "event")
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID uuid;

    @Column(name = "user_id")
    long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    EventUserStatus status;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    protected LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    protected LocalDateTime updatedDate;

    @Column(name = "is_proceed")
    Boolean Isproceed = false;
}
