package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.InvestorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * @author NeerajMewada
 *
 */
public class InvestorModelHibImpl implements InvestorModelInt {

    // ================== ADD ==================
    @Override
    public long add(InvestorDTO dto)
            throws ApplicationException, DuplicateRecordException {

        InvestorDTO existDto = findByInvestorcode(dto.getInvestorcode());

        if (existDto != null) {
            throw new DuplicateRecordException("Investor Code already exists");
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
            throw new ApplicationException("Exception in Investor Add "
                    + e.getMessage());

        } finally {
            session.close();
        }

        return pk;
    }

    // ================== DELETE ==================
    @Override
    public void delete(InvestorDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Investor Delete "
                    + e.getMessage());

        } finally {
            session.close();
        }
    }

    // ================== UPDATE ==================
    @Override
    public void update(InvestorDTO dto)
            throws ApplicationException, DuplicateRecordException {

        InvestorDTO existDto = findByInvestorcode(dto.getInvestorcode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Investor Code already exists");
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
            throw new ApplicationException("Exception in Investor Update "
                    + e.getMessage());

        } finally {
            session.close();
        }
    }

    // ================== FIND BY PK ==================
    @Override
    public InvestorDTO findByPK(long pk) throws ApplicationException {

        Session session = HibDataSource.getSession();
        InvestorDTO dto = null;

        try {
            dto = (InvestorDTO) session.get(InvestorDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Investor by PK");

        } finally {
            session.close();
        }

        return dto;
    }

    // ================== FIND BY INVESTOR CODE ==================
    public InvestorDTO findByInvestorcode(String investorcode)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        InvestorDTO dto = null;

        try {
            Criteria criteria = session.createCriteria(InvestorDTO.class);
            criteria.add(Restrictions.eq("investorcode", investorcode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (InvestorDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException(
                    "Exception in getting Investor by Code " + e.getMessage());

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
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(InvestorDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Investor list");

        } finally {
            session.close();
        }

        return list;
    }

    // ================== SEARCH ==================
    @Override
    public List search(InvestorDTO dto)
            throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(InvestorDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(InvestorDTO.class);

            if (dto != null) {

                // ✅ IMPORTANT FIX
                if (dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getInvestorName() != null
                        && dto.getInvestorName().length() > 0) {
                    criteria.add(Restrictions.like("investorName",
                            dto.getInvestorName() + "%"));
                }

                if (dto.getInvestorcode() != null
                        && dto.getInvestorcode().length() > 0) {
                    criteria.add(Restrictions.like("investorcode",
                            dto.getInvestorcode() + "%"));
                }

                if (dto.getInvestmentAmount() != null
                        && dto.getInvestmentAmount().length() > 0) {
                    criteria.add(Restrictions.like("investmentAmount",
                            dto.getInvestmentAmount() + "%"));
                }

                if (dto.getInvestmentType() != null
                        && dto.getInvestmentType().length() > 0) {
                    criteria.add(Restrictions.like("investmentType",
                            dto.getInvestmentType() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Investor search");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public InvestorDTO findByName(String name)
            throws ApplicationException {
        return null;
    }
}