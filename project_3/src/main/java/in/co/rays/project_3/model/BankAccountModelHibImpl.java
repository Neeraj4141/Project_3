package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.BankAccountDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of BankAccount model
 * @author Neeraj Mewada
 *
 */
public class BankAccountModelHibImpl implements BankAccountModelInt {

    public long add(BankAccountDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;
        long pk = 0;

        BankAccountDTO existDto = findByAccountCode(dto.getAccountCode());

        if (existDto != null) {
            throw new DuplicateRecordException("BankAccount already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            pk = dto.getAccountId();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in BankAccount Add " + e.getMessage());

        } finally {
            session.close();
        }

        return pk;
    }

    public void delete(BankAccountDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in BankAccount Delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    public void update(BankAccountDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.update(dto);
            tx.commit();

        } catch (JDBCConnectionException e) {

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in BankAccount Update " + e.getMessage());

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
            Criteria criteria = session.createCriteria(BankAccountDTO.class);

            if (pageSize > 0) {
                pageNo = ((pageNo - 1) * pageSize) + 1;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in BankAccount List");

        } finally {
            session.close();
        }

        return list;
    }

    public List search(BankAccountDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(BankAccountDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BankAccountDTO.class);

            if (dto.getAccountCode() != null && dto.getAccountCode().length() > 0) {
                criteria.add(Restrictions.like("accountCode", dto.getAccountCode() + "%"));
            }

            if (dto.getAccountHolderName() != null && dto.getAccountHolderName().length() > 0) {
                criteria.add(Restrictions.like("accountHolderName", dto.getAccountHolderName() + "%"));
            }

            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.eq("status", dto.getStatus()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in BankAccount Search");

        } finally {
            session.close();
        }

        return list;
    }

    public BankAccountDTO findByPK(long pk) throws ApplicationException {

        Session session = HibDataSource.getSession();

        try {
            BankAccountDTO dto = (BankAccountDTO) session.get(BankAccountDTO.class, pk);
            return dto;

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting BankAccount by PK");

        } finally {
            session.close();
        }
    }

    public BankAccountDTO findByAccountCode(String accountCode) throws ApplicationException {

        Session session = null;
        BankAccountDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BankAccountDTO.class);
            criteria.add(Restrictions.eq("accountCode", accountCode));

            List list = criteria.list();

            if (list.size() > 0) {
                dto = (BankAccountDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting BankAccount by Code " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public BankAccountDTO findByName(String name) throws ApplicationException {
        return null;
    }
}