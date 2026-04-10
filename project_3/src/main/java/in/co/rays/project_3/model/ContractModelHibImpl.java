package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ContractDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * @author Win10 Pro
 *
 */
public class ContractModelHibImpl implements ContractModelInt {

	@Override
	public long add(ContractDTO dto) throws ApplicationException, DuplicateRecordException {

		ContractDTO existDto = findByContractName(dto.getContractName());
		if (existDto != null) {
			throw new DuplicateRecordException("Contract Name already exists");
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
			throw new ApplicationException("Exception in Contract Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ContractDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Contract Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ContractDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		ContractDTO existDto = findByContractName(dto.getContractName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Contract Name already exists");
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
			throw new ApplicationException("Exception in Contract Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ContractDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ContractDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ContractDTO) session.get(ContractDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Contract by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	/**
	 *
	 */
	public ContractDTO findByContractName(String contractName) throws ApplicationException {

		Session session = null;
		ContractDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ContractDTO.class);
			criteria.add(Restrictions.eq("contractName", contractName));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ContractDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Contract by Name");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 *
	 */
	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ContractDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Contract list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ContractDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 *
	 */
	@Override
	public List search(ContractDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<ContractDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ContractDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getContractName() != null && dto.getContractName().length() > 0) {
					criteria.add(Restrictions.like("contractName", dto.getContractName() + "%"));
				}

				if (dto.getContractCode() != null && dto.getContractCode().length() > 0) {
					criteria.add(Restrictions.like("contractCode", dto.getContractCode() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<ContractDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Contract search");
		} finally {
			session.close();
		}

		return list;
	}
}