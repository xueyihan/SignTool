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
 * Unsigned entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.signinner.dao.Unsigned
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UnsignedDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UnsignedDAO.class);
	// property constants
	public static final String SIGN_ID = "signId";
	public static final String USER_ID = "userId";

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

	public void save(Unsigned transientInstance) {
		log.debug("saving Unsigned instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Unsigned persistentInstance) {
		log.debug("deleting Unsigned instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Unsigned findById(java.lang.Integer id) {
		log.debug("getting Unsigned instance with id: " + id);
		try {
			Unsigned instance = (Unsigned) getCurrentSession().get(
					"com.signinner.dao.Unsigned", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Unsigned> findByExample(Unsigned instance) {
		log.debug("finding Unsigned instance by example");
		try {
			List<Unsigned> results = (List<Unsigned>) getCurrentSession()
					.createCriteria("com.signinner.dao.Unsigned")
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
		log.debug("finding Unsigned instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Unsigned as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Unsigned> findBySignId(Object signId) {
		return findByProperty(SIGN_ID, signId);
	}

	public List<Unsigned> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findAll() {
		log.debug("finding all Unsigned instances");
		try {
			String queryString = "from Unsigned";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Unsigned merge(Unsigned detachedInstance) {
		log.debug("merging Unsigned instance");
		try {
			Unsigned result = (Unsigned) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Unsigned instance) {
		log.debug("attaching dirty Unsigned instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Unsigned instance) {
		log.debug("attaching clean Unsigned instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UnsignedDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UnsignedDAO) ctx.getBean("UnsignedDAO");
	}
}