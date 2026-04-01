package com.jovi.mapper;

import com.jovi.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentMapper {
    //用学号和密码换id信息
    @Select("SELECT id, student_num, name FROM student WHERE student_num = #{studentNum} AND password = #{password}")
    Student selectByStudentNumAndPassword(Student student) ;

    //用id换基本信息，查询回显
    @Select("SELECT * FROM student WHERE id = #{id} ")
    Student selectById(Integer id);

    @Update("UPDATE student SET name = #{name}, gender = #{gender} ,phone = #{phone}, dormitory_id = #{dormitoryId}, update_time = #{updateTime} WHERE id = #{id}")
    void update(Student student);

    @Insert("INSERT student(student_num , password ,create_time , update_time) VALUES (#{studentNum}, #{password} ,#{createTime} ,#{updateTime})")
    void insertNumAndPassword(Student student);

    @Select("SELECT COUNT(*) FROM student WHERE student_num = #{studentNum} ")
    int SelectByStudentNum(String studentNum);
}
