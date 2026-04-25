package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HotelModelHibImpl implements HotelModelInt {

	@Override
	public long add(HotelDTO dto) throws ApplicationException, DuplicateRecordException {

		HotelDTO existDto = findByRoomNo(dto.getRoomNo());
		if (existDto != null) {
			throw new DuplicateRecordException("Room No already exists");
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
			throw new ApplicationException("Exception in Hotel Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(HotelDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Hotel Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(HotelDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		HotelDTO existDto = findByRoomNo(dto.getRoomNo());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Room No already exists");
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
			throw new ApplicationException("Exception in Hotel Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public HotelDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		HotelDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (HotelDTO) session.get(HotelDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Hotel by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public HotelDTO findByRoomNo(Long roomNo) throws ApplicationException {

		Session session = null;
		HotelDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HotelDTO.class);
			criteria.add(Restrictions.eq("roomNo", roomNo));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (HotelDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Hotel by Room No");
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
			Criteria criteria = session.createCriteria(HotelDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Hotel list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(HotelDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(HotelDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<HotelDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HotelDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}

				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}

				if (dto.getRoomNo() != null && dto.getRoomNo() > 0) {
					criteria.add(Restrictions.eq("roomNo", dto.getRoomNo()));
				}
				
				if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
					criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<HotelDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Hotel search");
		} finally {
			session.close();
		}

		return list;
	}
}