package com.example.filestorage.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "filestorage_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

        @Id
        private String userId;

        private String username;

        private String password;

}
