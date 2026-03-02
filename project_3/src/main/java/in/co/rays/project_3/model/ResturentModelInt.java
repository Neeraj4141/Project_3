package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ResturentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Restaurant model
 * 
 * @author Neeraj Mewada
 *
 */
public interface ResturentModelInt {

	public long add(ResturentDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(ResturentDTO dto) throws ApplicationException;

	public void update(ResturentDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(ResturentDTO dto) throws ApplicationException;

	public List search(ResturentDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public ResturentDTO findByPK(long pk) throws ApplicationException;

	public ResturentDTO findByName(String name) throws ApplicationException;
}