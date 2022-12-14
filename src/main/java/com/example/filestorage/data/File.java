package com.example.filestorage.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Optional;

@Entity(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    private String id;

    private String name,type;

    @ManyToOne
    private User user;

    @Lob
    private byte[] data;

}
