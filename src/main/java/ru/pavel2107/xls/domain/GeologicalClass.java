package ru.pavel2107.xls.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "geoclass")
@Data @NoArgsConstructor
public class GeologicalClass implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @ToString.Exclude
    private Long id;

    @Column( name="code")
    private String code;

    @Column( name="name")
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @Column( name = "section_name")
    private String section_name;
}
