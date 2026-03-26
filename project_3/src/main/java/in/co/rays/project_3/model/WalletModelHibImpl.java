package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.WalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Wallet model
 * @author Neeraj Mewada
 *
 */
public class WalletModelHibImpl implements WalletModelInt {

    public long add(WalletDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;
        long pk = 0;

        WalletDTO existDto = findByWalletCode(dto.getWalletCode());

        if (existDto != null) {
            throw new DuplicateRecordException("Wallet already exist");
        }

        session = HibDataSource.getSession();

        try {
            tx = session.beginTransaction();
            session.save(dto);
            pk = dto.getWalletId();
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();

            if (tx != null) {
                tx.rollback();
            }

            throw new ApplicationException("Exception in Wallet Add " + e.getMessage());

        } finally {
            session.close();
        }

        return pk;
    }

    public void delete(WalletDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in Wallet delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    public void update(WalletDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in Wallet update " + e.getMessage());

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
            Criteria criteria = session.createCriteria(WalletDTO.class);

            if (pageSize > 0) {
                pageNo = ((pageNo - 1) * pageSize) + 1;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (JDBCConnectionException e) {

            throw e;

        } catch (HibernateException e) {

            throw new ApplicationException("Exception : Exception in wallet list");

        } finally {
            session.close();
        }

        return list;
    }

    public List search(WalletDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(WalletDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WalletDTO.class);

            if (dto.getWalletId() != null && dto.getWalletId() > 0) {
                criteria.add(Restrictions.eq("walletId", dto.getWalletId()));
            }

            if (dto.getWalletCode() != null && dto.getWalletCode().length() > 0) {
                criteria.add(Restrictions.like("walletCode", dto.getWalletCode() + "%"));
            }

            if (dto.getUserName() != null && dto.getUserName().length() > 0) {
                criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
            }

            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Wallet search");

        } finally {
            session.close();
        }

        return list;
    }

    public WalletDTO findByPK(long pk) throws ApplicationException {

        Session session = HibDataSource.getSession();

        try {
            WalletDTO dto = (WalletDTO) session.get(WalletDTO.class, pk);
            return dto;

        } catch (HibernateException e) {

            throw new ApplicationException("Exception : Exception in getting Wallet by pk");

        } finally {
            session.close();
        }
    }

    public WalletDTO findByWalletCode(String walletCode) throws ApplicationException {

        Session session = null;
        WalletDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WalletDTO.class);

            criteria.add(Restrictions.eq("walletCode", walletCode));

            List list = criteria.list();

            if (list.size() > 0) {
                dto = (WalletDTO) list.get(0);
            }

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in getting Wallet by Code " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }
}