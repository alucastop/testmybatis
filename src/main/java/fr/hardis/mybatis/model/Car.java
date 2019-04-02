package fr.hardis.mybatis.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mybatis.domain.LongId;


import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="car")
public class Car extends LongId {

    private String model;
    private String color;


    @Column(name = "id_user")
    private Long idUser;
}
