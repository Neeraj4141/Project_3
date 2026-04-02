package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PrescriptionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PrescriptionModelHibImpl implements PrescriptionModelInt {

	@Override
	public long add(PrescriptionDTO dto) throws ApplicationException, DuplicateRecordException {

		PrescriptionDTO existDto = findByPatientName(dto.getPatientName());

		if (existDto != null) {
			throw new DuplicateRecordException("Patient Name already exists");
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
			throw new ApplicationException("Exception in Prescription Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(PrescriptionDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Prescription Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(PrescriptionDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		PrescriptionDTO existDto = findByPatientName(dto.getPatientName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Patient Name already exists");
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
			throw new ApplicationException("Exception in Prescription Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public PrescriptionDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		PrescriptionDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (PrescriptionDTO) session.get(PrescriptionDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Prescription by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public PrescriptionDTO findByPatientName(String patientName) throws ApplicationException {

		Session session = null;
		PrescriptionDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PrescriptionDTO.class);
			criteria.add(Restrictions.eq("patientName", patientName));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (PrescriptionDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Prescription by Name");
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
			Criteria criteria = session.createCriteria(PrescriptionDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Prescription list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(PrescriptionDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(PrescriptionDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<PrescriptionDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PrescriptionDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getPatientName() != null && dto.getPatientName().length() > 0) {
					criteria.add(Restrictions.like("patientName", dto.getPatientName() + "%"));
				}

				if (dto.getPrescriptionNo() != null) {
					criteria.add(Restrictions.eq("prescriptionNo", dto.getPrescriptionNo()));
				}

				if (dto.getDoctorName() != null && dto.getDoctorName().length() > 0) {
					criteria.add(Restrictions.like("doctorName", dto.getDoctorName() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<PrescriptionDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Prescription search");
		} finally {
			session.close();
		}

		return list;
	}
}