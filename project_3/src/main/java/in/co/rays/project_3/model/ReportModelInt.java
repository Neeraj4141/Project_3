package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Report model
 * 
 * @author Neeraj Mewada
 *
 */
public interface ReportModelInt {

    public long add(ReportDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(ReportDTO dto) throws ApplicationException;

    public void update(ReportDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(ReportDTO dto) throws ApplicationException;

    public List search(ReportDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public ReportDTO findByPK(long pk) throws ApplicationException;

    public ReportDTO findByRepairNo(Long repairNo) throws ApplicationException;
}