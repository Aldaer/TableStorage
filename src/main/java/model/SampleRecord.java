package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.JsonPackable;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "RECORDS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleRecord implements Serializable, JsonPackable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "NAME")
    private String name;

    public SampleRecord(String name) {
        this.name = name;
    }
}
