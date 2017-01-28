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

/**
 * A data access object (DAO) providing persistence and search support for
 * UserActivity entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.signinner.dao.UserActivity
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserActivityDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserActivityDAO.class);
	// property constants
	public static final String ACTIVITY_ID = "activityId";
	public static final String USER_ID = "userId";
	public static final String STATE = "state";
	public static final String NUMBER = "number";

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

	public void save(UserActivity transientInstance) {
		log.debug("saving UserActivity instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserActivity persistentInstance) {
		log.debug("deleting UserActivity instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserActivity findById(java.lang.Integer id) {
		log.debug("getting UserActivity instance with id: " + id);
		try {
			UserActivity instance = (UserActivity) getCurrentSession().get(
					"com.signinner.dao.UserActivity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserActivity> findByExample(UserActivity instance) {
		log.debug("finding UserActivity instance by example");
		try {
			List<UserActivity> results = (List<UserActivity>) getCurrentSession()
					.createCriteria("com.signinner.dao.UserActivity")
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
		log.debug("finding UserActivity instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserActivity as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserActivity> findByActivityId(Object activityId) {
		return findByProperty(ACTIVITY_ID, activityId);
	}

	public List<UserActivity> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<UserActivity> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<UserActivity> findByNumber(Object number) {
		return findByProperty(NUMBER, number);
	}

	public List findAll() {
		log.debug("finding all UserActivity instances");
		try {
			String queryString = "from UserActivity";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserActivity merge(UserActivity detachedInstance) {
		log.debug("merging UserActivity instance");
		try {
			UserActivity result = (UserActivity) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserActivity instance) {
		log.debug("attaching dirty UserActivity instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserActivity instance) {
		log.debug("attaching clean UserActivity instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserActivityDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (UserActivityDAO) ctx.getBean("UserActivityDAO");
	}
}