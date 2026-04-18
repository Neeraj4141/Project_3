package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DataRetentionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of DataRetention model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface DataRetentionModelInt {

    public long add(DataRetentionDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(DataRetentionDTO dto) throws ApplicationException;

    public void update(DataRetentionDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(DataRetentionDTO dto) throws ApplicationException;

    public List search(DataRetentionDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public DataRetentionDTO findByPK(long pk) throws ApplicationException;

    public DataRetentionDTO findByRetentionCode(String retentionCode) throws ApplicationException;
}