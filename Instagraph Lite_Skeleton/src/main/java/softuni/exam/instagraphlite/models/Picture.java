package softuni.exam.instagraphlite.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //•	path – a char sequence. Cannot be null. The path is unique.
    //•	size – a floating point number. Cannot be null. Must be between 500 and 60000 (both numbers are INCLUSIVE)

    @Column(nullable = false, unique = true)
    private String path;
    @Column(nullable = false)
    private double size;

    @OneToMany(mappedBy = "profilePicture")
    private Set<User> users;

    @OneToMany(mappedBy = "picture")
    private Set<Post> postsSet;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Post> getPostsSet() {
        return postsSet;
    }

    public void setPostsSet(Set<Post> postsSet) {
        this.postsSet = postsSet;
    }
}
