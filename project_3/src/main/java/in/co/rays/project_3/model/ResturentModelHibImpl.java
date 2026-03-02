package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.ResturentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Restaurant model
 * 
 * @author Neeraj Mewada
 *
 */
public class ResturentModelHibImpl implements ResturentModelInt {

	public long add(ResturentDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		ResturentDTO existDto = findByName(dto.getRestaurantName());

		if (existDto != null) {
			throw new DuplicateRecordException("Restaurant already exist");
		}

		session = HibDataSource.getSession();
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Restaurant Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(ResturentDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Restaurant delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(ResturentDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (JDBCConnectionException e) {

		} catch (HibernateException e) {

			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Restaurant update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResturentDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in restaurant list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(ResturentDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(ResturentDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResturentDTO.class);
			System.out.println(dto.getRestaurantName());

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getRestaurantName() != null && dto.getRestaurantName().length() > 0) {
				System.out.println(dto.getRestaurantName());
				criteria.add(Restrictions.like("restaurantName", dto.getRestaurantName() + "%"));
			}
			if (dto.getLocation() != null && dto.getLocation().length() > 0) {
				criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
			}
			if (dto.getCuisineType() != null && dto.getCuisineType().length() > 0) {
				criteria.add(Restrictions.like("cuisineType", dto.getCuisineType() + "%"));
			}
			if (dto.getRate() > 0) {
				criteria.add(Restrictions.eq("rate", dto.getRate()));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			System.out.println(criteria);

			list = criteria.list();
			System.out.println(list.size());

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in restaurant search");
		} finally {
			session.close();
		}
		return list;
	}

	public ResturentDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		try {
			ResturentDTO dto = (ResturentDTO) session.get(ResturentDTO.class, pk);
			return dto;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Restaurant by pk");
		} finally {
			session.close();
		}
	}

	public ResturentDTO findByName(String name) throws ApplicationException {

		Session session = null;
		ResturentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResturentDTO.class);
			criteria.add(Restrictions.eq("restaurantName", name));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (ResturentDTO) list.get(0);
			}
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Restaurant by Name " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}
}