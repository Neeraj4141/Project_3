package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.PortfolioDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PortfolioModelHibImp implements PortfolioModelInt {

	public long add(PortfolioDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		PortfolioDTO existDto = findByName(dto.getPortfolioName());

		if (existDto != null) {
			throw new DuplicateRecordException("Portfolio already exist");
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
			throw new ApplicationException("Exception in Portfolio Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(PortfolioDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Portfolio delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(PortfolioDTO dto) throws ApplicationException, DuplicateRecordException {

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
			throw new ApplicationException("Exception in Portfolio update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(PortfolioDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in portfolio list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(PortfolioDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(PortfolioDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PortfolioDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getPortfolioName() != null && dto.getPortfolioName().length() > 0) {
				criteria.add(Restrictions.like("portfolioName", dto.getPortfolioName() + "%"));
			}
			if (dto.getPortfolioNo() != null && dto.getPortfolioNo().length() > 0) {
				criteria.add(Restrictions.like("portfolioNo", dto.getPortfolioNo() + "%"));
			}
			if (dto.getTotalValue() != null && dto.getTotalValue().length() > 0) {
				criteria.add(Restrictions.like("totalValue", dto.getTotalValue() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in portfolio search");
		} finally {
			session.close();
		}
		return list;
	}

	public PortfolioDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		try {
			return (PortfolioDTO) session.get(PortfolioDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Portfolio by PK");
		} finally {
			session.close();
		}
	}

	public PortfolioDTO findByName(String name) throws ApplicationException {

		Session session = null;
		PortfolioDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PortfolioDTO.class);
			criteria.add(Restrictions.eq("portfolioName", name));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PortfolioDTO) list.get(0);
			}
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Portfolio by Name " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}
}