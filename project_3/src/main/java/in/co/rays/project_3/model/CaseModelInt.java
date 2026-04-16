package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Case model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface CaseModelInt {

	public long add(CaseDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(CaseDTO dto) throws ApplicationException;

	public void update(CaseDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(CaseDTO dto) throws ApplicationException;

	public List search(CaseDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public CaseDTO findByPK(long pk) throws ApplicationException;

	public CaseDTO findByTestCaseCode(String testCaseCode) throws ApplicationException;

}