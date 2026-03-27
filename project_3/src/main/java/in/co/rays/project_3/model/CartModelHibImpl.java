package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.CartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Cart model
 * 
 * @author Neeraj Mewada
 *
 */
public class CartModelHibImpl implements CartModelInt {

	public long add(CartDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		CartDTO existDto = findByCartCode(dto.getCartCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Cart already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getCartId();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Cart Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(CartDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Cart Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(CartDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (JDBCConnectionException e) {

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Cart Update " + e.getMessage());

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
			Criteria criteria = session.createCriteria(CartDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Cart List");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(CartDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(CartDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CartDTO.class);

			if (dto.getCartCode() != null && dto.getCartCode().length() > 0) {
				criteria.add(Restrictions.like("cartCode", dto.getCartCode() + "%"));
			}

			if (dto.getUserName() != null && dto.getUserName().length() > 0) {
				criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.eq("status", dto.getStatus()));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Cart Search");

		} finally {
			session.close();
		}

		return list;
	}

	public CartDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {
			CartDTO dto = (CartDTO) session.get(CartDTO.class, pk);
			return dto;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Cart by PK");

		} finally {
			session.close();
		}
	}

	public CartDTO findByCartCode(String cartCode) throws ApplicationException {

		Session session = null;
		CartDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CartDTO.class);
			criteria.add(Restrictions.eq("cartCode", cartCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (CartDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Cart by Code " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public CartDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}