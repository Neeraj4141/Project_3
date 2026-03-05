package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LockerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LockerModelHibImpl implements LockerModelInt {

	// ================== ADD ==================
	@Override
	public long add(LockerDTO dto) throws ApplicationException, DuplicateRecordException {

		LockerDTO existDto = findByLockerNumber(dto.getLockerNumber());

		if (existDto != null) {
			throw new DuplicateRecordException("Locker Number already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getLockerId();
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Locker Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	// ================== DELETE ==================
	@Override
	public void delete(LockerDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Locker Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	// ================== UPDATE ==================
	@Override
	public void update(LockerDTO dto) throws ApplicationException, DuplicateRecordException {

		LockerDTO existDto = findByLockerNumber(dto.getLockerNumber());

		if (existDto != null && existDto.getLockerId() != dto.getLockerId()) {
			throw new DuplicateRecordException("Locker Number already exists");
		}

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

			throw new ApplicationException("Exception in Locker Update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	// ================== FIND BY PK ==================
	@Override
	public LockerDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		LockerDTO dto = null;

		try {

			dto = (LockerDTO) session.get(LockerDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Locker by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	// ================== FIND BY LOCKER NUMBER ==================
	public LockerDTO findByLockerNumber(String lockerNumber) throws ApplicationException {

		Session session = HibDataSource.getSession();
		LockerDTO dto = null;

		try {

			Criteria criteria = session.createCriteria(LockerDTO.class);
			criteria.add(Restrictions.eq("lockerNumber", lockerNumber));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (LockerDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Locker by Number " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	// ================== LIST ==================
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
			Criteria criteria = session.createCriteria(LockerDTO.class);

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Locker list");

		} finally {
			session.close();
		}

		return list;
	}

	// ================== SEARCH ==================
	@Override
	public List search(LockerDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(LockerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LockerDTO.class);

			if (dto != null) {

				if (dto.getLockerId() != null && dto.getLockerId() > 0) {
				    criteria.add(Restrictions.eq("lockerId", dto.getLockerId()));
				}

				if (dto.getLockerNumber() != null && dto.getLockerNumber().length() > 0) {
					criteria.add(Restrictions.like("lockerNumber", dto.getLockerNumber() + "%"));
				}

				if (dto.getLockerType() != null && dto.getLockerType().length() > 0) {
					criteria.add(Restrictions.like("lockerType", dto.getLockerType() + "%"));
				}

				if (dto.getAnnualFee() != null && dto.getAnnualFee().length() > 0) {
					criteria.add(Restrictions.like("annualFee", dto.getAnnualFee() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Locker search");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public LockerDTO findByName(String name) throws ApplicationException {
		return null;
	}
}