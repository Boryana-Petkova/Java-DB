package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "volcanologists")
public class Volcanologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //•	first name - accepts char sequence (between 2 to 30 both inclusive). Unique, it can not be nullable.
    //•	last name - accepts char sequence (between 2 to 30 both inclusive). Unique, it can not be nullable.
    //•	salary - accepts positive number values. It can not be nullable.
    //•	age - accepts number values that are between 18 and 80 both inclusive. It can not be nullable.
    //•	exploring from - a date in the "yyyy-MM-dd" format. It can be nullable.

    //•	Constraint: The volcanologists table has a relation with volcanoes table. It can be nullable.

    @Column(name = "first_name", unique = true, nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = true, nullable = false)
    private String lastName;

    @Column(name = "salary",nullable = false)
    private double salary;

    @Column(name = "age",nullable = false)
    private int age;

    @Column(name = "exploring_from")
    private LocalDate exploringFrom;

    @ManyToOne
    @JoinColumn(name = "exploring_volcano_id", referencedColumnName = "id")
    private Volcano exploringVolcano;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(LocalDate exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Volcano getExploringVolcano() {
        return exploringVolcano;
    }

    public void setExploringVolcano(Volcano exploringVolcano) {
        this.exploringVolcano = exploringVolcano;
    }
}
