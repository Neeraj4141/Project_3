package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Employee model
 * 
 * @author Neeraj
 */
public class EmployeeModelHibImpl implements EmployeeModelInt {

	@Override
	public long add(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException {

		EmployeeDTO existDto = findByEmployeeCode(dto.getEmployeeCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Employee Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Employee Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(EmployeeDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Employee Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException {

		EmployeeDTO existDto = findByEmployeeCode(dto.getEmployeeCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Employee Code already exists");
		}

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Employee Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public EmployeeDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		EmployeeDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (EmployeeDTO) session.get(EmployeeDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Employee by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public EmployeeDTO findByEmail(String email) throws ApplicationException {

		Session session = null;
		EmployeeDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EmployeeDTO.class);
			criteria.add(Restrictions.eq("email", email));
			List list = criteria.list();

			if (list.size() == 1) {
				dto = (EmployeeDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Employee by Email");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public EmployeeDTO findByEmployeeCode(String employeeCode) throws ApplicationException {

		Session session = null;
		EmployeeDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EmployeeDTO.class);
			criteria.add(Restrictions.eq("employeeCode", employeeCode));
			List list = criteria.list();

			if (list.size() == 1) {
				dto = (EmployeeDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Employee by Code");
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
			Criteria criteria = session.createCriteria(EmployeeDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Employee List");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(EmployeeDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(EmployeeDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<EmployeeDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EmployeeDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getEmployeeName() != null && dto.getEmployeeName().length() > 0) {
					criteria.add(Restrictions.like("employeeName", dto.getEmployeeName() + "%"));
				}

				if (dto.getEmail() != null && dto.getEmail().length() > 0) {
					criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
				}

				if (dto.getRole() != null && dto.getRole() > 0) {
					criteria.add(Restrictions.eq("role", dto.getRole() + "%"));
				}

				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}

				if (dto.getEmployeeCode() != null && dto.getEmployeeCode().length() > 0) {
					criteria.add(Restrictions.eq("employeeCode", dto.getEmployeeCode()));
				}

				if (dto.getJoiningDate() != null) {
					criteria.add(Restrictions.eq("joiningDate", dto.getJoiningDate()));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<EmployeeDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Employee Search");
		} finally {
			session.close();
		}

		return list;
	}
}