package ru.pavel2107.xls.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
@Data @NoArgsConstructor
public class Section implements Serializable {

    @Id
    @Column( name = "name")
    private String name;

    @OneToMany( fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn( name="section_name", referencedColumnName = "name", nullable = false, insertable = false, updatable = false)
    private Set<GeologicalClass> geologicalClasses = new HashSet<>();
}
