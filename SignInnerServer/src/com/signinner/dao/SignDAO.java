package com.signinner.dao;

import java.sql.Timestamp;
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
 * A data access object (DAO) providing persistence and search support for Sign
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.signinner.dao.Sign
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class SignDAO {
	private static final Logger log = LoggerFactory.getLogger(SignDAO.class);
	// property constants
	public static final String ACTIVITY_ID = "activityId";
	public static final String SIGNED_NUM = "signedNum";
	public static final String SIGNED_JSON = "signedJson";
	public static final String WIFI_JSON = "wifiJson";

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

	public int save(Sign transientInstance) {
		try {
			getCurrentSession().save(transientInstance);
			return transientInstance.getId();
		} catch (RuntimeException re) {
		}
		return 0;
	}

	public void delete(Sign persistentInstance) {
		log.debug("deleting Sign instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Sign findById(java.lang.Integer id) {
		log.debug("getting Sign instance with id: " + id);
		try {
			Sign instance = (Sign) getCurrentSession().get(
					"com.signinner.dao.Sign", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Sign> findByExample(Sign instance) {
		log.debug("finding Sign instance by example");
		try {
			List<Sign> results = (List<Sign>) getCurrentSession()
					.createCriteria("com.signinner.dao.Sign")
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
		log.debug("finding Sign instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Sign as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Sign> findByActivityId(Object activityId) {
		return findByProperty(ACTIVITY_ID, activityId);
	}

	public List<Sign> findBySignedNum(Object signedNum) {
		return findByProperty(SIGNED_NUM, signedNum);
	}

	public List<Sign> findBySignedJson(Object signedJson) {
		return findByProperty(SIGNED_JSON, signedJson);
	}

	public List<Sign> findByWifiJson(Object wifiJson) {
		return findByProperty(WIFI_JSON, wifiJson);
	}

	public List findAll() {
		log.debug("finding all Sign instances");
		try {
			String queryString = "from Sign";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Sign merge(Sign detachedInstance) {
		log.debug("merging Sign instance");
		try {
			Sign result = (Sign) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Sign instance) {
		log.debug("attaching dirty Sign instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Sign instance) {
		log.debug("attaching clean Sign instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SignDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SignDAO) ctx.getBean("SignDAO");
	}
	
	public boolean updateSigned(int signId,int userId){
		boolean flag=false;
		try {
			String sql="update sign set signed_json=concat(signed_json,'["+userId+"]') , signed_num=signed_num+1 where id="+signId;
			getCurrentSession().createSQLQuery(sql).executeUpdate();
			System.out.println("½øÈë");
			flag=true;
		} catch (RuntimeException re) {
			throw re;
		}
		return flag;
	}

	
	
}