package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * @author Neeraj Mewada
 *
 */
public class NotificationModelHibImpl implements NotificationModelInt {

	@Override
	public long add(NotificationDTO dto) throws ApplicationException, DuplicateRecordException {

		NotificationDTO existDto = findByHistoryCode(dto.getHistoryCode());
		if (existDto != null) {
			throw new DuplicateRecordException("History Code already exists");
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
			throw new ApplicationException("Exception in Notification Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(NotificationDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Notification Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(NotificationDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		NotificationDTO existDto = findByHistoryCode(dto.getHistoryCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("History Code already exists");
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
			throw new ApplicationException("Exception in Notification Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public NotificationDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		NotificationDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (NotificationDTO) session.get(NotificationDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Notification by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public NotificationDTO findByHistoryCode(String historyCode) throws ApplicationException {

		Session session = null;
		NotificationDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(NotificationDTO.class);
			criteria.add(Restrictions.eq("historyCode", historyCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (NotificationDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Notification by HistoryCode");
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
			Criteria criteria = session.createCriteria(NotificationDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Notification list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(NotificationDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(NotificationDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<NotificationDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(NotificationDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getHistoryCode() != null && dto.getHistoryCode().length() > 0) {
					criteria.add(Restrictions.like("historyCode", dto.getHistoryCode() + "%"));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
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

			list = (ArrayList<NotificationDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Notification search");
		} finally {
			session.close();
		}

		return list;
	}
}