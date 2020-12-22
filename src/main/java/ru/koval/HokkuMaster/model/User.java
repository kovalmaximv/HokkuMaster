package ru.koval.HokkuMaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "usr")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    @Column(length = 30, unique = true)
    private String username;
    @Column
    private String password;
    @Column(length = 30)
    private String firstName;
    @Column(length = 30)
    private String familyName;
    @Column
    private Integer rank;
    @Column
    private LocalDate registerDate;
}
