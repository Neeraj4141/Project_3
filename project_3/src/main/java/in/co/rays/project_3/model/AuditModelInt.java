package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AuditDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Audit model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface AuditModelInt {

	public long add(AuditDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(AuditDTO dto) throws ApplicationException;

	public void update(AuditDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(AuditDTO dto) throws ApplicationException;

	public List search(AuditDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public AuditDTO findByPK(long pk) throws ApplicationException;

	public AuditDTO findByAuditCode(String auditCode) throws ApplicationException;
}