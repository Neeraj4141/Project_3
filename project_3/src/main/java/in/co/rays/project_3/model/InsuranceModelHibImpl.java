package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.InsuranceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class InsuranceModelHibImpl implements InsuranceModelInt {

	@Override
	public long add(InsuranceDTO dto) throws ApplicationException, DuplicateRecordException {

		InsuranceDTO existDto = findByInsuranceCode(dto.getInsuranceCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Insurance Code already exists");
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
			throw new ApplicationException("Exception in Insurance Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(InsuranceDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Insurance Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(InsuranceDTO dto) throws ApplicationException, DuplicateRecordException {

		InsuranceDTO existDto = findByInsuranceCode(dto.getInsuranceCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Insurance Code already exists");
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
			throw new ApplicationException("Exception in Insurance Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public InsuranceDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		InsuranceDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (InsuranceDTO) session.get(InsuranceDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Insurance by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public InsuranceDTO findByInsuranceCode(String insuranceCode) throws ApplicationException {

		Session session = null;
		InsuranceDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(InsuranceDTO.class);
			criteria.add(Restrictions.eq("insuranceCode", insuranceCode));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (InsuranceDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Insurance by Code");
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
			Criteria criteria = session.createCriteria(InsuranceDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Insurance list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(InsuranceDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(InsuranceDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<InsuranceDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(InsuranceDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getInsuranceCode() != null && dto.getInsuranceCode().length() > 0) {
					criteria.add(Restrictions.like("insuranceCode", dto.getInsuranceCode() + "%"));
				}

				if (dto.getPolicyHolder() != null && dto.getPolicyHolder().length() > 0) {
					criteria.add(Restrictions.like("policyHolder", dto.getPolicyHolder() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<InsuranceDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Insurance search");
		} finally {
			session.close();
		}

		return list;
	}
}