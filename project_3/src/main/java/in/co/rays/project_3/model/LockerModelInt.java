package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LockerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * 
 * Interface of Locker Model
 *
 * @author Neeraj Mewada
 *
 * 
 */
public interface LockerModelInt {

	public long add(LockerDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(LockerDTO dto) throws ApplicationException;

	public void update(LockerDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(LockerDTO dto) throws ApplicationException;

	public List search(LockerDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public LockerDTO findByPK(long pk) throws ApplicationException;

	public LockerDTO findByName(String name) throws ApplicationException;

}
