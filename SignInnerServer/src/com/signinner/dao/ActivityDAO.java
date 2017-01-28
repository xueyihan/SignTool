package com.signinner.dao;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.signinner.entity.ActivityEntity;

/**
 * A data access object (DAO) providing persistence and search support for
 * Activity entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.signinner.dao.Activity
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ActivityDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ActivityDAO.class);
	// property constants
	public static final String PUBLISHER_ID = "publisherId";
	public static final String PUBLISHER_NAME = "publisherName";
	public static final String NAME = "name";
	public static final String TIME = "time";
	public static final String POSITION = "position";
	public static final String SCHOOL = "school";
	public static final String MAJOR = "major";
	public static final String SIGN_ID = "signId";

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	public boolean save(Activity transientInstance) {
		boolean flag;
		try {
			getCurrentSession().save(transientInstance);
			flag=true;
		} catch (RuntimeException re) {
			flag=false;
			throw re;
		}
		return flag;
	}

	public void delete(Activity persistentInstance) {
		log.debug("deleting Activity instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Activity findById(java.lang.Integer id) {
		log.debug("getting Activity instance with id: " + id);
		try {
			Activity instance = (Activity) getCurrentSession().get(
					"com.signinner.dao.Activity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Activity> findByExample(Activity instance) {
		log.debug("finding Activity instance by example");
		try {
			List<Activity> results = (List<Activity>) getCurrentSession()
					.createCriteria("com.signinner.dao.Activity")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Activity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Activity as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Activity> findByPublisherId(Object publisherId) {
		return findByProperty(PUBLISHER_ID, publisherId);
	}

	public List<Activity> findByPublisherName(Object publisherName) {
		return findByProperty(PUBLISHER_NAME, publisherName);
	}

	public List<Activity> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Activity> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<Activity> findByPosition(Object position) {
		return findByProperty(POSITION, position);
	}

	public List<Activity> findBySchool(Object school) {
		return findByProperty(SCHOOL, school);
	}

	public List<Activity> findByMajor(Object major) {
		return findByProperty(MAJOR, major);
	}

	public List<Activity> findBySignId(Object signId) {
		return findByProperty(SIGN_ID, signId);
	}

	public List findAll() {
		log.debug("finding all Activity instances");
		try {
			String queryString = "from Activity";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Activity merge(Activity detachedInstance) {
		log.debug("merging Activity instance");
		try {
			Activity result = (Activity) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Activity instance) {
		log.debug("attaching dirty Activity instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Activity instance) {
		log.debug("attaching clean Activity instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ActivityDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ActivityDAO) ctx.getBean("ActivityDAO");
	}
	
	
	public List<ActivityEntity> getCanyuActivity(String userId){
		try {
			String queryString = "select new com.signinner.entity.ActivityEntity(a.id,a.name,a.position,a.time,s.startTime,s.endTime,s.signedJson,a.signId) "
					+ "from UserActivity as ua,Activity as a,Sign as s "
					+ "where ua.userId="+userId+" and ua.activityId=a.id and a.signId=s.id";
			
			
			List<ActivityEntity> l=getCurrentSession().createQuery(queryString).list();
			
			if(l.size()>0){
				return l;
			}
			return l;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<ActivityEntity> getFaqiActivity(String userKey) {
		try {
			String queryString = "select new com.signinner.entity.ActivityEntity(a.id,a.name,a.position,a.time,s.startTime,s.endTime) "
					+ "from Activity as a,Sign as s "
					+ "where a.publisherId="+userKey+" and a.signId=s.id";
			
			
			List<ActivityEntity> l=getCurrentSession().createQuery(queryString).list();
			
			if(l.size()>0){
				return l;
			}
			return l;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public boolean updateSignId(String activityId, int signId) {
		boolean flag=false;
		try {
			String sql="update activity set sign_id="+signId+" where id="+activityId;
			getCurrentSession().createSQLQuery(sql).executeUpdate();
			System.out.println("½øÈë");
			flag=true;
		} catch (RuntimeException re) {
			flag=false;
		}
		return flag;
	}
	
	
	
}