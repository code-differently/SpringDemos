package com.codedifferently.watertrackerapi.domain.userProfiles.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserProfile {
    @Id
    private String id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "following" )
    private Set<UserProfile> followers = new HashSet<>();

    @JsonIgnore
    @JoinTable(name= "followers",
            joinColumns = {@JoinColumn(name ="user_id")},
            inverseJoinColumns = {@JoinColumn(name="follower_id")})
    @ManyToMany(cascade =  CascadeType.ALL)
    private Set<UserProfile> following = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public void addFollower(UserProfile toFollow){
        following.add(toFollow);
        toFollow.getFollowers().add(this);
    }

    public void removeFollower(UserProfile toFollow){
        following.remove(toFollow);
        toFollow.getFollowers().remove(this);
    }

    @PrePersist
    protected void onCreate() {
        Date date = new Date();
        created = date;
        updated = date;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

}
