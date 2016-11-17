package model;

import controller.JsonPackable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "RECORDS")
@Data
@NoArgsConstructor
public class SampleRecord implements Serializable, JsonPackable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NAME")
    private String name;

    public SampleRecord(String name) {
        this.name = name;
    }
}
