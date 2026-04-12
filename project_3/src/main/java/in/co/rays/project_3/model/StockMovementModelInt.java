package in.co.rays.project_3.model;

import java.util.List;


import in.co.rays.project_3.dto.StockMovementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Stock Movement model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface StockMovementModelInt {

	public long add(StockMovementDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(StockMovementDTO dto) throws ApplicationException;

	public void update(StockMovementDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(StockMovementDTO dto) throws ApplicationException;

	public List search(StockMovementDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public StockMovementDTO findByPK(long pk) throws ApplicationException;

	public StockMovementDTO findByMovementCode(String movementCode) throws ApplicationException;
}