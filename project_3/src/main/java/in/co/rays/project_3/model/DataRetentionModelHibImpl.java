package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DataRetentionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DataRetentionModelHibImpl implements DataRetentionModelInt {

    @Override
    public long add(DataRetentionDTO dto) throws ApplicationException, DuplicateRecordException {

        DataRetentionDTO existDto = findByRetentionCode(dto.getRetentionCode());

        if (existDto != null) {
            throw new DuplicateRecordException("Retention Code already exists");
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
            throw new ApplicationException("Exception in DataRetention Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(DataRetentionDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in DataRetention Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DataRetentionDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        DataRetentionDTO existDto = findByRetentionCode(dto.getRetentionCode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Retention Code already exists");
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
            throw new ApplicationException("Exception in DataRetention Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public DataRetentionDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        DataRetentionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (DataRetentionDTO) session.get(DataRetentionDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting DataRetention by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public DataRetentionDTO findByRetentionCode(String retentionCode) throws ApplicationException {

        Session session = null;
        DataRetentionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DataRetentionDTO.class);
            criteria.add(Restrictions.eq("retentionCode", retentionCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (DataRetentionDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting DataRetention by Code");
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
            Criteria criteria = session.createCriteria(DataRetentionDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in DataRetention list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(DataRetentionDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(DataRetentionDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<DataRetentionDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DataRetentionDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getRetentionCode() != null && dto.getRetentionCode().length() > 0) {
                    criteria.add(Restrictions.like("retentionCode", dto.getRetentionCode() + "%"));
                }

                if (dto.getDataType() != null && dto.getDataType().length() > 0) {
                    criteria.add(Restrictions.like("dataType", dto.getDataType() + "%"));
                }

                if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                    criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
                }
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<DataRetentionDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in DataRetention search");
        } finally {
            session.close();
        }

        return list;
    }
}