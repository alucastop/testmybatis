package fr.hardis.mybatis.repository;

import fr.hardis.mybatis.model.User;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MybatisRepository<User, Long> {
}
