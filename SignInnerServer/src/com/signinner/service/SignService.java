package com.signinner.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;


import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signinner.dao.ActivityDAO;
import com.signinner.dao.Sign;
import com.signinner.dao.SignDAO;
import com.signinner.dao.User;
import com.signinner.dao.UserDAO;
import com.signinner.entity.ActivityEntity;

@Transactional
@Service("SignService")
public class SignService {
	@Autowired
	private SignDAO signDao;
	@Autowired
	private ActivityDAO activityDAO;
	
	
	public boolean sign(String userKey,String wifiJson,String signId){
		Sign sign=signDao.findById(Integer.valueOf(signId));
		Timestamp currentTime=new Timestamp(System.currentTimeMillis());
		if(currentTime.compareTo(sign.getEndTime())<0&&currentTime.compareTo(sign.getStartTime())>0){
			//签到时间中
			String[] usersWifi=wifiJson.substring(1, wifiJson.length()-1).split(",");
			String[] signWifi=sign.getWifiJson().substring(1, sign.getWifiJson().length()-1).split(",");
			boolean a=false;
			for(int i=0;i<usersWifi.length;i++){
				for(int j=0;j<signWifi.length;j++){
					if(usersWifi[i].equals(signWifi[j])){
						a=true;
						break;
					}
				}
				if(a) break;
			}
			if(a){
				//位置确认
				if(signDao.updateSigned(Integer.valueOf(signId), Integer.valueOf(userKey)))
					return true;
				else
					return false;
			}else{
				return false;
			}
			
			
		}else{
			//最近一次签到已结束，或签到未开始
			return false;
		}
	}



	public boolean faqi(String userKey, String activityId, String startTime,
			String endTime, String wifiJson) {
		// TODO Auto-generated method stub
		Sign newSign=new Sign();
		newSign.setActivityId(Integer.valueOf(activityId));
		newSign.setStartTime(Timestamp.valueOf(startTime));
		newSign.setEndTime(Timestamp.valueOf(endTime));
		newSign.setWifiJson(wifiJson);
		newSign.setSignedJson("[1]");
		newSign.setSignedNum(0);
		int id=signDao.save(newSign);
		System.out.println("id:"+id);
		if(id!=0){
			//签到信息保存成功,修改对应课程的signID
			boolean f=activityDAO.updateSignId(activityId,id);
			System.out.println("f:"+f);
			if(f) return true;
		}
		return false;
	}

}
