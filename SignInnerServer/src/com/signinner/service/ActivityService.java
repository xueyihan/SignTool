package com.signinner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signinner.dao.Activity;
import com.signinner.dao.ActivityDAO;
import com.signinner.dao.User;
import com.signinner.dao.UserActivity;
import com.signinner.dao.UserActivityDAO;
import com.signinner.dao.UserDAO;
import com.signinner.entity.ActivityEntity;

@Transactional
@Service("ActivityService")
public class ActivityService {
	@Autowired
	private ActivityDAO activityDao;
	@Autowired
	private UserActivityDAO userActivityDAO;
	
	
	public List<ActivityEntity> getCanyuActivity(String userKey){
		return activityDao.getCanyuActivity(userKey);
	}



	public List<ActivityEntity> getFaqiActivity(String userKey) {
		// TODO Auto-generated method stub
		return activityDao.getFaqiActivity(userKey);
	}



	public boolean addFaqiActivity(String publisherId, String publisherName,String name, String time,
			String position, String school, String major) {
		// TODO Auto-generated method stub
		Activity newActivity=new Activity();
		newActivity.setPublisherId(Integer.valueOf(publisherId));
		newActivity.setPublisherName(publisherName);
		newActivity.setName(name);
		newActivity.setTime(time);
		newActivity.setPosition(position);
		newActivity.setSchool(Integer.valueOf(school));
		newActivity.setMajor(Integer.valueOf(major));
		newActivity.setSignId(0);
		return activityDao.save(newActivity);
			
	}



	public List<ActivityEntity> getAll() {
		// TODO Auto-generated method stub
		return activityDao.findAll();
	}



	public boolean addCanyuActivity(String userKey, String idArray) {
		// TODO Auto-generated method stub
		String[] id=idArray.substring(1, idArray.length()-1).split(",");
		for(int i=0;i<id.length;i++){
			userActivityDAO.save(new UserActivity(userKey,id[i].substring(1, id[i].length()-1)));
		}
		return true;
	}

}
