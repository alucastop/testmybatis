package fr.hardis.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotation.JdbcType;
import org.springframework.data.mybatis.domain.LongId;

import javax.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User extends LongId {

    private String nom;
    private String prenom;

    @OneToMany
     private List<Car> cars;
}
