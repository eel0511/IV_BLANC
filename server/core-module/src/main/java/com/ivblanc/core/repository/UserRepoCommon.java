package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.User;

@Repository
public interface UserRepoCommon {

	@Query("insert into user(email, password, name, age, gender, phone, social) "
		+ "values(:email, :password, :name, :age, :gender, :phone, :social)")
	User insertUser(String email, String password, String name, int age, int gender, String phone, int social);


	@Query("select * from user where user_id = :user_id")
	User getUserDetailById(int user_id);

	@Query("select * from user where email = :email")
	User getUserDetailByEmail(String email);


	@Query("update user set password = :password where user_id = :user_id")
	User updatePasswordById(int user_id, String password);

	@Query("update user set password = :password where email = :email")
	User updatePasswordByEmail(int email, String password);

	@Query("update user set age = :age where user_id = :user_id")
	User updateAge(int user_id, int age);

	@Query("update user set gender = :gender where user_id = :user_id")
	User updateGender(int user_id, int gender);

	@Query("update user set phone = :phone where user_id = :user_id")
	User updatePhone(int user_id, String phone);


	@Query("Delete from user where user_id = :user_id")
	int deleteUser(int user_id);

}






































