package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.WebHookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate Implementation of WebHook Model
 * 
 * @author Neeraj Mewada
 *
 */
public class WebHookModelHibImpl implements WebHookModelInt {

	@Override
	public long add(WebHookDTO dto) throws ApplicationException, DuplicateRecordException {

		WebHookDTO existDto = findByLogCode(dto.getLogCode());
		if (existDto != null) {
			throw new DuplicateRecordException("LogCode already exists");
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
			throw new ApplicationException("Exception in WebHook Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(WebHookDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in WebHook Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(WebHookDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		WebHookDTO existDto = findByLogCode(dto.getLogCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("LogCode already exists");
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
			throw new ApplicationException("Exception in WebHook Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public WebHookDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		WebHookDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (WebHookDTO) session.get(WebHookDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting WebHook by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public WebHookDTO findByLogCode(String logCode) throws ApplicationException {

		Session session = null;
		WebHookDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WebHookDTO.class);
			criteria.add(Restrictions.eq("logCode", logCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (WebHookDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting WebHook by LogCode");
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
			Criteria criteria = session.createCriteria(WebHookDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in WebHook list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(WebHookDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(WebHookDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<WebHookDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WebHookDTO.class);

			if (dto != null) {

			    if (dto.getId() != null && dto.getId() > 0) {
			        criteria.add(Restrictions.eq("id", dto.getId()));
			    }

			    if (dto.getLogCode() != null && dto.getLogCode().length() > 0) {
			        criteria.add(Restrictions.like("logCode", dto.getLogCode() + "%"));
			    }

			    if (dto.getWebhookId() != null && dto.getWebhookId() > 0) {
			        criteria.add(Restrictions.eq("webhookId", dto.getWebhookId()));
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

			list = (ArrayList<WebHookDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in WebHook search");
		} finally {
			session.close();
		}

		return list;
	}
}