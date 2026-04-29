package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EmployeeRoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of EmployeeRole model
 * @author Neeraj Mewada
 *
 */
public interface EmployeeRoleModelInt {

    public long add(EmployeeRoleDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(EmployeeRoleDTO dto) throws ApplicationException;

    public void update(EmployeeRoleDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(EmployeeRoleDTO dto) throws ApplicationException;

    public List search(EmployeeRoleDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public EmployeeRoleDTO findByPK(long pk) throws ApplicationException;

    public EmployeeRoleDTO findByName(String roleName) throws ApplicationException;
}