package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.WalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Wallet model
 * @author Neeraj Mewada
 *
 */
public interface WalletModelInt  {

    public long add(WalletDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(WalletDTO dto) throws ApplicationException;

    public void update(WalletDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(WalletDTO dto) throws ApplicationException;

    public List search(WalletDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public WalletDTO findByPK(long pk) throws ApplicationException;

    public WalletDTO findByWalletCode(String walletCode) throws ApplicationException;

}