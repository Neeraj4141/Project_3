package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BankAccountDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of BankAccount model
 * 
 * @author Neeraj Mewada
 *
 */
public interface BankAccountModelInt {

	public long add(BankAccountDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(BankAccountDTO dto) throws ApplicationException;

	public void update(BankAccountDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(BankAccountDTO dto) throws ApplicationException;

	public List search(BankAccountDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public BankAccountDTO findByPK(long pk) throws ApplicationException;

	public BankAccountDTO findByName(String name) throws ApplicationException;
}