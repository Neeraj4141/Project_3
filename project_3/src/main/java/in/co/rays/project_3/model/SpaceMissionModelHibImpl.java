package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SpaceMissionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SpaceMissionModelHibImpl implements SpaceMissionModelInt {

	@Override
	public long add(SpaceMissionDTO dto) throws ApplicationException, DuplicateRecordException {

		SpaceMissionDTO existDto = findByMissionName(dto.getMissionName());
		if (existDto != null) {
			throw new DuplicateRecordException("Mission Name already exists");
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
			throw new ApplicationException("Exception in SpaceMission Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(SpaceMissionDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in SpaceMission Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(SpaceMissionDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		SpaceMissionDTO existDto = findByMissionName(dto.getMissionName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Mission Name already exists");
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
			throw new ApplicationException("Exception in SpaceMission Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public SpaceMissionDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		SpaceMissionDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (SpaceMissionDTO) session.get(SpaceMissionDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting SpaceMission by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public SpaceMissionDTO findByMissionName(String missionName) throws ApplicationException {

		Session session = null;
		SpaceMissionDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SpaceMissionDTO.class);
			criteria.add(Restrictions.eq("missionName", missionName));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (SpaceMissionDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting SpaceMission by Name");
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
			Criteria criteria = session.createCriteria(SpaceMissionDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in SpaceMission list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(SpaceMissionDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(SpaceMissionDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<SpaceMissionDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SpaceMissionDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getMissionName() != null && dto.getMissionName().length() > 0) {
					criteria.add(Restrictions.like("missionName", dto.getMissionName() + "%"));
				}

				if (dto.getLaunchVehical() != null && dto.getLaunchVehical().length() > 0) {
					criteria.add(Restrictions.like("launchVehical", dto.getLaunchVehical() + "%"));
				}

				if (dto.getDestination() != null && dto.getDestination().length() > 0) {
					criteria.add(Restrictions.like("destination", dto.getDestination() + "%"));
				}

				if (dto.getMissionStatus() != null && dto.getMissionStatus().length() > 0) {
					criteria.add(Restrictions.like("missionStatus", dto.getMissionStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<SpaceMissionDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in SpaceMission search");
		} finally {
			session.close();
		}

		return list;
	}
}