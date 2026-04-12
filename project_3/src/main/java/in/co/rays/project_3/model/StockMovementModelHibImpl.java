package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.StockMovementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class StockMovementModelHibImpl implements StockMovementModelInt {

	@Override
	public long add(StockMovementDTO dto) throws ApplicationException, DuplicateRecordException {

		StockMovementDTO existDto = findByMovementCode(dto.getMovementCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Movement Code already exists");
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
			throw new ApplicationException("Exception in Stock Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(StockMovementDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Stock Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(StockMovementDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		StockMovementDTO existDto = findByMovementCode(dto.getMovementCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Movement Code already exists");
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
			throw new ApplicationException("Exception in Stock Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public StockMovementDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		StockMovementDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (StockMovementDTO) session.get(StockMovementDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Stock by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public StockMovementDTO findByMovementCode(String movementCode) throws ApplicationException {

		Session session = null;
		StockMovementDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StockMovementDTO.class);
			criteria.add(Restrictions.eq("movementCode", movementCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (StockMovementDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Stock by MovementCode");
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
			Criteria criteria = session.createCriteria(StockMovementDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Stock list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(StockMovementDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	
	@Override
	public List search(StockMovementDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<StockMovementDTO> list = null;
		System.out.println("in search method");

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StockMovementDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getMovementCode() != null && dto.getMovementCode().length() > 0) {
					criteria.add(Restrictions.like("movementCode", dto.getMovementCode() + "%"));
				}

				if (dto.getProductName() != null && dto.getProductName().length() > 0) {
					criteria.add(Restrictions.like("productName", dto.getProductName() + "%"));
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

			list = (ArrayList<StockMovementDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Stock search");
		} finally {
			session.close();
		}
		System.out.println(list);

		return list;
	}
}