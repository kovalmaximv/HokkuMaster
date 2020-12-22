package ru.koval.HokkuMaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hokku_id")
    private Hokku hokku;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column
    private LocalDateTime publishedDate;
    @Column
    private String content;
}
