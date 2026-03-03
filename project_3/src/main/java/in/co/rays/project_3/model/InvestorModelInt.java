package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.InvestorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Investor Model
 * 
 * @author Neeraj Mewada
 *
 */
public interface InvestorModelInt {

	public long add(InvestorDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(InvestorDTO dto) throws ApplicationException;

	public void update(InvestorDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(InvestorDTO dto) throws ApplicationException;

	public List search(InvestorDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public InvestorDTO findByPK(long pk) throws ApplicationException;

	public InvestorDTO findByName(String name) throws ApplicationException;
}