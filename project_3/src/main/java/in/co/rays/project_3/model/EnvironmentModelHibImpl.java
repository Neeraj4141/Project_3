package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.EnvironmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Environment model
 * 
 * @author Neeraj Mewada
 *
 */
public class EnvironmentModelHibImpl implements EnvironmentModelInt {

	@Override
	public long add(EnvironmentDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in add");
		Session session = null;
		Transaction tx = null;
		long pk = 0;

		EnvironmentDTO existDto = findByEnvironmentName(dto.getEnvironmentName());
		if (existDto != null) {
			throw new DuplicateRecordException("Environment already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Environment Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(EnvironmentDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Environment Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(EnvironmentDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Environment Update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public EnvironmentDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		EnvironmentDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (EnvironmentDTO) session.get(EnvironmentDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Environment by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public EnvironmentDTO findByEnvironmentName(String name) throws ApplicationException {

		Session session = null;
		EnvironmentDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EnvironmentDTO.class);
			criteria.add(Restrictions.eq("environmentName", name));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (EnvironmentDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Environment by Name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EnvironmentDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Environment list");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(EnvironmentDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(EnvironmentDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EnvironmentDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getEnvironmentName() != null && dto.getEnvironmentName().length() > 0) {
				criteria.add(Restrictions.like("environmentName", dto.getEnvironmentName() + "%"));
			}

			if (dto.getEnvironmentCode() != null && dto.getEnvironmentCode().length() > 0) {
				criteria.add(Restrictions.like("environmentCode", dto.getEnvironmentCode() + "%"));
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Environment search");

		} finally {
			session.close();
		}

		return list;
	}
}