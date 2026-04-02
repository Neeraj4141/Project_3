package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ReportModelHibImpl implements ReportModelInt {

	@Override
	public long add(ReportDTO dto) throws ApplicationException, DuplicateRecordException {

		ReportDTO existDto = findByRepairNo(dto.getRepairNo());

		if (existDto != null) {
			throw new DuplicateRecordException("Repair No already exists");
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
			throw new ApplicationException("Exception in Report Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ReportDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Report Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ReportDTO dto) throws ApplicationException, DuplicateRecordException {

		ReportDTO existDto = findByRepairNo(dto.getRepairNo());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Repair No already exists");
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
			throw new ApplicationException("Exception in Report Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ReportDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ReportDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ReportDTO) session.get(ReportDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Report by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public ReportDTO findByRepairNo(Long repairNo) throws ApplicationException {

		Session session = null;
		ReportDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ReportDTO.class);
			criteria.add(Restrictions.eq("repairNo", repairNo));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (ReportDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Report by RepairNo");
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
			Criteria criteria = session.createCriteria(ReportDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Report list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ReportDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(ReportDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<ReportDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ReportDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getRepairNo() != null) {
					criteria.add(Restrictions.eq("repairNo", dto.getRepairNo()));
				}

				if (dto.getDeviceName() != null && dto.getDeviceName().length() > 0) {
					criteria.add(Restrictions.like("deviceName", dto.getDeviceName() + "%"));
				}

				if (dto.getCost() != null && dto.getCost().length() > 0) {
					criteria.add(Restrictions.like("cost", dto.getCost() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<ReportDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Report search");
		} finally {
			session.close();
		}

		return list;
	}
}