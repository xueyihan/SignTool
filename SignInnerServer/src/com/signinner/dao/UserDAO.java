package com.signinner.dao;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.signinner.dao.User
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String SCHOOL = "school";
	public static final String MAJOR = "major";
	public static final String NUMBER = "number";
	public static final String YEAR = "year";
	public static final String VERTIFICATION = "vertification";
	public static final String PERSON_ID = "personId";
	public static final String IDENTITY = "identity";

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

	
	public int save(User transientInstance){
		log.debug("saving User instance");
		try {
			String queryString = "from User where username='"+transientInstance.getUsername()+"'";
			System.out.println("½øÈësave");
			List<User> l=getCurrentSession().createQuery(queryString).list();
			if(l.size()>0){return -1;}
			getCurrentSession().save(transientInstance);//.save(transientInstance);
			RuntimeException e=new RuntimeException();
			System.out.println("htdhdht");
			throw e;
			//return transientInstance.getId();
		} catch (RuntimeException re) {
			log.error("save failed", re);
			System.out.println("catch");
			throw re; 
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getCurrentSession().get(
					"com.signinner.dao.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = (List<User>) getCurrentSession()
					.createCriteria("com.signinner.dao.User")
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
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findBySchool(Object school) {
		return findByProperty(SCHOOL, school);
	}

	public List<User> findByMajor(Object major) {
		return findByProperty(MAJOR, major);
	}

	public List<User> findByNumber(Object number) {
		return findByProperty(NUMBER, number);
	}

	public List<User> findByYear(Object year) {
		return findByProperty(YEAR, year);
	}

	public List<User> findByVertification(Object vertification) {
		return findByProperty(VERTIFICATION, vertification);
	}

	public List<User> findByPersonId(Object personId) {
		return findByProperty(PERSON_ID, personId);
	}

	public List<User> findByIdentity(Object identity) {
		return findByProperty(IDENTITY, identity);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}
	
	
	public int setUserInfo(int userKey,String name){
		try {
			System.out.println("fwf"+name);
			String queryString = "from User where id="+userKey+"";
			List<User> l=getCurrentSession().createQuery(queryString).list();
			System.out.println(l.size());
			if(l.size()==1){
				User user=l.get(0);
				user.setName(name);
				getCurrentSession().update(user);
				return 1;
			}else{
				return 0;
			}
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public int setPersonId(Integer userKey, String person_id) {
		try {
			String queryString = "from User where id="+userKey+"";
			List<User> l=getCurrentSession().createQuery(queryString).list();
			System.out.println(l.size());
			if(l.size()==1){
				User user=l.get(0);
				user.setPersonId(person_id);
				getCurrentSession().update(user);
				return 1;
			}else{
				return 0;
			}
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public User login(String username,String password) {
		try {
			String queryString = "from User where username='"+username+"' and password='"+password+"'";
			List<User> l=getCurrentSession().createQuery(queryString).list();
			System.out.println(l.size());
			if(l.size()==1){
				return l.get(0);
			}else{
				return null;
			}
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
}

class MyException extends RuntimeException{
	
}