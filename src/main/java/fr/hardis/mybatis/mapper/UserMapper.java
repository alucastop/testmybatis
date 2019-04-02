package fr.hardis.mybatis.mapper;

import fr.hardis.mybatis.model.Car;
import fr.hardis.mybatis.model.User;
import org.apache.ibatis.annotations.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface UserMapper {


    @Select("select id, nom, prenom from user")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nom", column = "nom"),
            @Result(property = "prenom", column = "prenom"),
            @Result(property = "cars", javaType = List.class, column = "id",
                    many = @Many(select = "getCars"))
    })
    List<User> findAll();

    @Insert("insert into user(nom, prenom) values (#{nom}, #{prenom})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before= false, resultType = Long.class)
    Boolean insertUser(User user);

    @Update("update user set nom = #{nom}, prenom=#{prenom} where id=#{id}")
    Boolean updateUser(User user);

    @Select("select id, nom, prenom from user where id=#{id}")
    User findOne(@PathParam("id") final Long id);


    /*    */


    @Select("SELECT id, model, color FROM car WHERE car.id_user = #{idUser}")
    List<Car> getCars(Long idUser);

    @Delete("delete from user where id=#{id}")
    void deleteOne(@PathParam("id") final Long id);


}
