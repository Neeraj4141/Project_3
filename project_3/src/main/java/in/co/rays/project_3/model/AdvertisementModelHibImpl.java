package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.AdvertisementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Advertisement model
 * 
 * @author Neeraj Mewada
 *
 */
public class AdvertisementModelHibImpl implements AdvertisementModelInt {

	public long add(AdvertisementDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		AdvertisementDTO duplicate = findByAdName(dto.getAdName());
		if (duplicate != null) {
			throw new DuplicateRecordException("Advertisement name already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Advertisement Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(AdvertisementDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Advertisement Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(AdvertisementDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		AdvertisementDTO dtoExist = findByAdName(dto.getAdName());

		// duplicate check
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Advertisement already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(dto);

			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Advertisement Update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(AdvertisementDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Advertisement list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(AdvertisementDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(AdvertisementDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AdvertisementDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getAdCode() != null && dto.getAdCode().length() > 0) {
				criteria.add(Restrictions.like("adCode", dto.getAdCode() + "%"));
			}

			if (dto.getAdName() != null && dto.getAdName().length() > 0) {
				criteria.add(Restrictions.like("adName", dto.getAdName() + "%"));
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Advertisement search");
		} finally {
			session.close();
		}

		return list;
	}

	public AdvertisementDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		AdvertisementDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (AdvertisementDTO) session.get(AdvertisementDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in finding Advertisement by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	public AdvertisementDTO findByAdName(String adName) throws ApplicationException {
		Session session = null;
		AdvertisementDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AdvertisementDTO.class);
			criteria.add(Restrictions.eq("adName", adName));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (AdvertisementDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in finding Advertisement by Name " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}
}