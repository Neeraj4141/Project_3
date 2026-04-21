package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.RuleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Rule model
 * 
 * @author Neeraj Mewada
 *
 */
public class RuleModelHibImpl implements RuleModelInt {

	@Override
	public long add(RuleDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		RuleDTO existDto = findByRuleCode(dto.getRuleCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Rule already exist");
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
			throw new ApplicationException("Exception in Rule Add " + e.getMessage());

		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(RuleDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Rule delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(RuleDTO dto) throws ApplicationException, DuplicateRecordException {

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
			throw new ApplicationException("Exception in Rule update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public RuleDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		RuleDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (RuleDTO) session.get(RuleDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Rule by PK");

		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public RuleDTO findByRuleCode(String ruleCode) throws ApplicationException {

		Session session = null;
		RuleDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RuleDTO.class);

			criteria.add(Restrictions.eq("ruleCode", ruleCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (RuleDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Rule by Code " + e.getMessage());

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
			Criteria criteria = session.createCriteria(RuleDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Rule list");

		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(RuleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(RuleDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RuleDTO.class);

			if (dto.getId() != null) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getRuleCode() != null && dto.getRuleCode().length() > 0) {
				criteria.add(Restrictions.like("ruleCode", dto.getRuleCode() + "%"));
			}
			if (dto.getTriggerType() != null && dto.getTriggerType().length() > 0) {
				criteria.add(Restrictions.like("triggerType", dto.getTriggerType() + "%"));
			}
			if (dto.getEvent() != null && dto.getEvent().length() > 0) {
				criteria.add(Restrictions.like("event", dto.getEvent() + "%"));
			}
			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Rule search");

		} finally {
			session.close();
		}

		return list;
	}
}