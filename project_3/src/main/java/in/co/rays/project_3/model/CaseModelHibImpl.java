package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CaseModelHibImpl implements CaseModelInt {

	@Override
	public long add(CaseDTO dto) throws ApplicationException, DuplicateRecordException {

		CaseDTO existDto = findByTestCaseCode(dto.getTestCaseCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Test Case Code already exists");
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
			throw new ApplicationException("Exception in Case Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(CaseDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Case Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(CaseDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		CaseDTO existDto = findByTestCaseCode(dto.getTestCaseCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Test Case Code already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Case Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public CaseDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		CaseDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (CaseDTO) session.get(CaseDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Case by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	public CaseDTO findByTestCaseCode(String testCaseCode) throws ApplicationException {

		Session session = null;
		CaseDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CaseDTO.class);
			criteria.add(Restrictions.eq("testCaseCode", testCaseCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CaseDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Case by TestCaseCode");
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
			Criteria criteria = session.createCriteria(CaseDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Case list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(CaseDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(CaseDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<CaseDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CaseDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getTestCaseCode() != null && dto.getTestCaseCode().length() > 0) {
					criteria.add(Restrictions.like("testCaseCode", dto.getTestCaseCode() + "%"));
				}

				if (dto.getTitle() != null && dto.getTitle().length() > 0) {
					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.eq("status", dto.getStatus()));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<CaseDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Case search");
		} finally {
			session.close();
		}

		return list;
	}
}