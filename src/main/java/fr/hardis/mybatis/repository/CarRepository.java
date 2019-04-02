package fr.hardis.mybatis.repository;

import fr.hardis.mybatis.model.Car;
import fr.hardis.mybatis.model.User;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MybatisRepository<Car, Long> {

    @Query()
    List<Car> findAllByIdUser(Long idUser);
}
