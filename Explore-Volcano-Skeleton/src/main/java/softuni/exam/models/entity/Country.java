package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //•	name – accepts char sequence (between 3 to 30 both inclusive). The values are unique in the database. It cannot be nullable.
    //•	capital - accepts char sequence (between 3 to 30 both inclusive). It can be nullable.

    //•	Constraint: The countries table has a relation with the volcanoes table. It can be nullable.

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String capital;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private Set<Volcano> volcanoes;





    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Set<Volcano> getVolcanoes() {
        return volcanoes;
    }

    public void setVolcanoes(Set<Volcano> volcanoes) {
        this.volcanoes = volcanoes;
    }
}
