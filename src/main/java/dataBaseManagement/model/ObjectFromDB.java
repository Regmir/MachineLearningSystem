package dataBaseManagement.model;

import java.math.BigInteger;
import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "objects", schema = "public", catalog = "postgres")
public class ObjectFromDB {
    @Id
    @Column(name = "id")
    @SequenceGenerator( name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1,
            initialValue = 2, schema = "public", catalog = "postgres" )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
    private BigInteger id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "parameters")
    private byte[] parameters;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getParameters() {
        return parameters;
    }

    public void setParameters(byte[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ObjectFromDB{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
