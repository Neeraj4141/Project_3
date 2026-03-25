package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.OTPVerificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class OTPVerificationModelHibImpl implements OTPVerificationModelInt {

	// ================== ADD ==================
	@Override
	public long add(OTPVerificationDTO dto) throws ApplicationException, DuplicateRecordException {

		OTPVerificationDTO existDto = findByOtpCode(dto.getOtpCode());

		if (existDto != null) {
			throw new DuplicateRecordException("OTP Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in OTP Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	// ================== DELETE ==================
	@Override
	public void delete(OTPVerificationDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in OTP Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	// ================== UPDATE ==================
	@Override
	public void update(OTPVerificationDTO dto) throws ApplicationException, DuplicateRecordException {

		OTPVerificationDTO existDto = findByOtpCode(dto.getOtpCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("OTP Code already exists");
		}

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
			throw new ApplicationException("Exception in OTP Update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	// ================== FIND BY PK ==================
	@Override
	public OTPVerificationDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		OTPVerificationDTO dto = null;

		try {
			dto = (OTPVerificationDTO) session.get(OTPVerificationDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting OTP by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	// ================== FIND BY OTP CODE ==================
	public OTPVerificationDTO findByOtpCode(String otpCode) throws ApplicationException {

		Session session = HibDataSource.getSession();
		OTPVerificationDTO dto = null;

		try {
			Criteria criteria = session.createCriteria(OTPVerificationDTO.class);
			criteria.add(Restrictions.eq("otpCode", otpCode));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (OTPVerificationDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting OTP by Code " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	// ================== FIND BY USERNAME ==================
	@Override
	public OTPVerificationDTO findByUserName(String userName) throws ApplicationException {

		Session session = HibDataSource.getSession();
		OTPVerificationDTO dto = null;

		try {
			Criteria criteria = session.createCriteria(OTPVerificationDTO.class);
			criteria.add(Restrictions.eq("userName", userName));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (OTPVerificationDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting OTP by username " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	// ================== LIST ==================
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
			Criteria criteria = session.createCriteria(OTPVerificationDTO.class);

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in OTP list");

		} finally {
			session.close();
		}

		return list;
	}

	// ================== SEARCH ==================
	@Override
	public List search(OTPVerificationDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(OTPVerificationDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(OTPVerificationDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {

				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getOtpId() != null && dto.getOtpId() > 0) {
					criteria.add(Restrictions.eq("otpId", dto.getOtpId()));
				}

				if (dto.getOtpCode() != null && dto.getOtpCode().length() > 0) {
					criteria.add(Restrictions.like("otpCode", dto.getOtpCode() + "%"));
				}

				if (dto.getOtpValue() != null && dto.getOtpValue().length() > 0) {
					criteria.add(Restrictions.like("otpValue", dto.getOtpValue() + "%"));
				}

				if (dto.getOtpStatus() != null && dto.getOtpStatus().length() > 0) {
					criteria.add(Restrictions.like("otpStatus", dto.getOtpStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in OTP search " + e.getMessage());

		} finally {
			session.close();
		}

		return list;
	}
}