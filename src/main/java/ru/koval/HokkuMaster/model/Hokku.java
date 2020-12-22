package ru.koval.HokkuMaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "hokku")
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Hokku {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column
    private String content;
    @Column(nullable = false)
    private Integer likes;
    @Column(nullable = false)
    private Integer dislikes;
    @Column(nullable = false)
    private LocalDateTime publishedDate;
}
