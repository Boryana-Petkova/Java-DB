package softuni.exam.instagraphlite.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //•	username – a char sequence. Cannot be null. The username is unique. Must be between 2 and 18 (both numbers are INCLUSIVE)
    //•	password – a char sequence. Cannot be null. Must be at least 4 characters long, inclusive.
    //•	profilePicture – a Picture. Cannot be null.

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;


    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
    private Picture profilePicture;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
