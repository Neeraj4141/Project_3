package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EnvironmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Environment model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface EnvironmentModelInt {

	public long add(EnvironmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(EnvironmentDTO dto) throws ApplicationException;

	public void update(EnvironmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(EnvironmentDTO dto) throws ApplicationException;

	public List search(EnvironmentDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public EnvironmentDTO findByPK(long pk) throws ApplicationException;

	// HBM ke hisaab se unique field (environmentName)
	public EnvironmentDTO findByEnvironmentName(String environmentName) throws ApplicationException;
}