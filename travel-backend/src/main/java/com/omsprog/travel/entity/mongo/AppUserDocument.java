package com.omsprog.travel.entity.mongo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppUserDocument {
    @Id
    private String id;
    private String dni;
    private String username;
    private boolean enabled;
    private String password;
    private Role role;
}