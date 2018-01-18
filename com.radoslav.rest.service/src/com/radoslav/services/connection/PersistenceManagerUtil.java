package com.radoslav.services.connection;

import com.google.gson.Gson;
import com.radoslav.entity.holder.AdminData;
import com.radoslav.entity.holder.Entities;
import com.radoslav.entity.holder.EntityData;
import com.radoslav.entity.holder.UserData;
import com.radoslav.services.entity.Admin;
import com.radoslav.services.entity.PasswordStorage;
import com.radoslav.services.entity.User;
import com.radoslav.services.entity.access.AdminDAO;
import com.radoslav.services.entity.access.PasswordStorageDAO;
import com.radoslav.services.entity.access.UserDAO;

public class PersistenceManagerUtil {
	
	public static <T> void persistEntity(Entities entity, String jsonString, Class<T> clazz) {
		EntityFactory.createEntity(entity, convertJsonString(jsonString, clazz));
	}
	
	private static <T> EntityData convertJsonString(String data, Class<T> clazz) {
		return (EntityData) new Gson().fromJson(data, clazz);
	}
	
	private static class EntityFactory {
		
		private static void createEntity(Entities entity, EntityData data) {
			if (entity == Entities.User) {
				createUserEntity((UserData) data);
				createPasswordStorageEntity((UserData) data);
			} else if (entity == Entities.Admin) {
				createAdminEntity((AdminData) data);
			} else {
				throw new RuntimeException();
			}
		}
		
		private static void createAdminEntity(AdminData adminData) {
			Admin adminEntity = new Admin();
			
			adminEntity.setAdminName(adminData.userName);
			adminEntity.setPassword(adminData.password);
			
			AdminDAO.getInstance().persistEntity(adminEntity);
		}
		
		private static void createUserEntity(UserData userData) {
			User userEntity = new User();
			
			userEntity.setUserName(userData.userName);
			userEntity.setRealName(userData.realName);
			userEntity.setFaculty(userData.faculty);
			userEntity.setSpeciality(userData.speciality);
			userEntity.setCourse(userData.course);
			userEntity.setGroup(userData.group);
			userEntity.setFacultyNumber(userData.facultyNumber);
			
			UserDAO.getInstance().persistEntity(userEntity);
		}
		
		private static void createPasswordStorageEntity(UserData userData) {
			PasswordStorage passwordEntity = new PasswordStorage();
			
			passwordEntity.setUserName(userData.userName);
			passwordEntity.setPassword(userData.password);
			
			PasswordStorageDAO.getInstance().persistEntity(passwordEntity);
		}
	}
}
