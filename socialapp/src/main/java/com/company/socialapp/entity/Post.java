package com.company.socialapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)   //bir user silindikde postlarini da sil.
    @JsonIgnore
    private User user;

    private String title;

    @Lob                                // LOB or Large OBject refers to a variable-length datatype for storing large objects
    @Column(columnDefinition = "text")  //hibernate in text-i  string olaraq basa dusmesi ucun yazdiq.eks halda varchar olaraq basa dusur.
    private String text;
}
