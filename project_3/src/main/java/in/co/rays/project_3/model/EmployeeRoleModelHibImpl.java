package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.EmployeeRoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of EmployeeRole model
 * 
 * @author NeerajMewada
 *
 */
public class EmployeeRoleModelHibImpl implements EmployeeRoleModelInt {

	/**
	 *
	 */
	public long add(EmployeeRoleDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		EmployeeRoleDTO existDto = findByName(dto.getRoleName());

		if (existDto != null) {
			throw new DuplicateRecordException("Employee Role already exist");
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
			throw new ApplicationException("Exception in EmployeeRole Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(EmployeeRoleDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in EmployeeRole delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(EmployeeRoleDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in EmployeeRole update " + e.getMessage());

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
			Criteria criteria = session.createCriteria(EmployeeRoleDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in EmployeeRole list");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(EmployeeRoleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(EmployeeRoleDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EmployeeRoleDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getRoleName() != null && dto.getRoleName().length() > 0) {
				criteria.add(Restrictions.like("roleName", dto.getRoleName() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in EmployeeRole search");

		} finally {
			session.close();
		}

		return list;
	}

	public EmployeeRoleDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {
			EmployeeRoleDTO dto = (EmployeeRoleDTO) session.get(EmployeeRoleDTO.class, pk);
			return dto;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting EmployeeRole by pk");

		} finally {
			session.close();
		}
	}

	public EmployeeRoleDTO findByName(String roleName) throws ApplicationException {

		Session session = null;
		EmployeeRoleDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(EmployeeRoleDTO.class);
			criteria.add(Restrictions.eq("roleName", roleName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (EmployeeRoleDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting EmployeeRole by name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}
}